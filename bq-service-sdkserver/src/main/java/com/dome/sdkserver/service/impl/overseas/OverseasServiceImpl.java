package com.dome.sdkserver.service.impl.overseas;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.OrderStatusEnum;
import com.dome.sdkserver.bq.constants.PayConstant;
import com.dome.sdkserver.bq.constants.redis.RedisConstants;
import com.dome.sdkserver.bq.util.*;
import com.dome.sdkserver.bq.util.google.Security;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.dao.mapper.bq.pay.PublishOrderMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.BqChargePointInfo;
import com.dome.sdkserver.metadata.entity.bq.pay.PublishOrderEntity;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.chargePoint.ChargePointService;
import com.dome.sdkserver.service.game.GameService;
import com.dome.sdkserver.service.overseas.OverseasService;
import com.dome.sdkserver.service.rabbitmq.RabbitMqService;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * OverseasServiceImpl
 *
 * @author Zhang ShanMin
 * @date 2017/3/9
 * @time 14:20
 */
@Service("overseasService")
public class OverseasServiceImpl implements OverseasService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PublishOrderMapper publishOrderMapper;
    @Resource(name = "rabbitMqService")
    private RabbitMqService rabbitMqService;
    @Autowired
    protected PropertiesUtil domainConfig;
    @Autowired
    protected RedisUtil redisUtil;
    @Autowired
    private ChargePointService chargePointService;
    @Resource(name = "gameService")
    protected GameService gameService;

    /**
     * 创建订单
     *
     * @param orderEntity
     * @return
     */
    @Override
    public SdkOauthResult createOrder(PublishOrderEntity orderEntity) {
        AppInfoEntity appInfo = gameService.getAppInfo(orderEntity.getAppCode());
        if (appInfo == null) {
            return SdkOauthResult.failed("無效的appCode");
        }
        if ("1".equals(domainConfig.getString("sdk.notify.environment", "1"))) {
            orderEntity.setPayNotifyUrl(appInfo.getPayNotifyUrl());
        } else {
            orderEntity.setPayNotifyUrl(appInfo.getTestPayNotifyUrl());
        }
        BqChargePointInfo chargePointInfo = chargePointService.getChargePointInfoByCode(orderEntity.getChargePointCode(), orderEntity.getAppCode());
        if (chargePointInfo == null) {
            return SdkOauthResult.failed("無效的計費點");
        }
        orderEntity.setAppName(appInfo.getAppName());
        orderEntity.setChargePointName(chargePointInfo.getChargePointName());
        orderEntity.setChargePointAmount(chargePointInfo.getChargePointAmount());
        String sdkOrderNo = GenOrderCode.next();
        orderEntity.setOrderNo(sdkOrderNo);//订单
        orderEntity.setOrderStatus(OrderStatusEnum.orderstatus_no_pay.code);
        orderEntity.setPayOrigin(PayConstant.PAY_CH_TYPE.getPayType(orderEntity.getPayType()).name());
        orderEntity.setCurMonth(DateUtils.getCurDateFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT));
        orderEntity.setPayNotifyUrl(appInfo.getPayNotifyUrl());
        orderEntity.setTradeType(PayConstant.TRADE_TYPE.app.getCode());
        boolean flag = publishOrderMapper.insert(orderEntity);
        Map<String, Object> map = new HashMap<>(1);
        if (flag) {  //入库成功
            map.put("sdkOrderNo", sdkOrderNo);
            return SdkOauthResult.success(map);
        }
        return SdkOauthResult.failed();
    }

    /**
     * 支付通知处理
     *
     * @param orderEntity
     * @return
     */
    @Override
    public SdkOauthResult notify(PublishOrderEntity orderEntity) {
        Map<String, Object> map = new HashMap<>();
        String curMonth = PayUtil.getPayMonth(orderEntity.getOrderNo());
        orderEntity.setCurMonth(curMonth);
        //验证订单是否存在，订单是否支付
        PublishOrderEntity _orderEntity = publishOrderMapper.queryOrderByOrderNo(orderEntity.getOrderNo(), orderEntity.getCurMonth());
        if (_orderEntity == null)
            return SdkOauthResult.failed("無效訂單");
        if (_orderEntity.getOrderStatus() == OrderStatusEnum.orderstatus_pay_sucess.code) {
            map.put("payResult", true);
            return SdkOauthResult.success(map);
        }
        //到对应支付服务器验证支付是否成功
        PayConstant.PAY_CH_TYPE pay_ch_type = PayConstant.PAY_CH_TYPE.getPayType(_orderEntity.getPayType());
        if (pay_ch_type == null){
            return SdkOauthResult.failed("不支持該支付類型");
        }
        boolean payResult = false;
        switch (pay_ch_type) {
            case APPLEPAY: {
                _orderEntity.setReceiptData(orderEntity.getReceiptData());
                payResult = validateApplePay(_orderEntity);
                break;
            }
            case GOOGLEPAY: {
                _orderEntity.setPurchaseData(orderEntity.getPurchaseData());
                _orderEntity.setSignature(orderEntity.getSignature());
                payResult = validateGooglePay(_orderEntity);
                break;
            }
        }
        if (!payResult) {
            logger.error("订单号：{},支付通知来源:{},第三方服务支付器验证失败", orderEntity.getOrderNo(), pay_ch_type.name());
            return SdkOauthResult.failed("訂單狀態異常");
        }
        //更新订单状态
        PublishOrderEntity orderEntity2 = new PublishOrderEntity();
        orderEntity2.setOrderNo(_orderEntity.getOrderNo());
        orderEntity2.setCurMonth(PayUtil.getPayMonth(_orderEntity.getOrderNo()));
        orderEntity2.setOrderStatus(OrderStatusEnum.orderstatus_pay_sucess.code);
        orderEntity2.setFinishTime(new Date());
        orderEntity2.setTradeNo(_orderEntity.getTradeNo());
        orderEntity2.setPayType(_orderEntity.getPayType());
        orderEntity2.setOrderNo(_orderEntity.getOrderNo());
        if (publishOrderMapper.updateById(orderEntity2)) {
            map.put("payResult", true);
            //订单为已支付则下发游戏道具
            PayNotify(_orderEntity, pay_ch_type);
            return SdkOauthResult.success(map);
        }
        return SdkOauthResult.failed("系統異常，請稍後重試!");
    }

    /**
     * 订单为已支付则下发游戏道具
     * @param _orderEntity
     * @param pay_ch_type
     */
    private void PayNotify(PublishOrderEntity _orderEntity, PayConstant.PAY_CH_TYPE pay_ch_type) {
        //苹果支付&不是海外渠道
        if (pay_ch_type == PayConstant.PAY_CH_TYPE.APPLEPAY && !BqSdkConstants.channelCodeOverseas.equals(_orderEntity.getChannelCode()) ){
            rabbitMqService.inlandMGameIosPayQueue(_orderEntity);//国内道具下发支付通知
        }else {
            rabbitMqService.mGamePayQueue(_orderEntity);
        }
    }


    /**
     * 服务器端验证谷歌支付结果
     *
     * @param _orderEntity
     */
    private boolean validateGooglePay(PublishOrderEntity _orderEntity) {
        JSONObject jsonObject = null;
        try {
            String googlePublicKey = domainConfig.getString("google.pay.verify.public.key");
            //验证谷歌支付通知参数
            boolean result = Security.verify(Security.generatePublicKey(googlePublicKey), _orderEntity.getPurchaseData(), _orderEntity.getSignature());
            //订单的购买状态。可能的值为 0（已购买）、1（已取消）或者 2（已退款）。
            if (!result)
                return false;
            jsonObject = JSONObject.parseObject(_orderEntity.getPurchaseData());
            if (jsonObject == null || StringUtils.isBlank(jsonObject.getString("packageName"))
                    || !jsonObject.getString("packageName").equals(domainConfig.getString("longyanjiuguan.google.packagename"))
                    || !_orderEntity.getChargePointCode().equals(jsonObject.getString("productId").toUpperCase())
                    || !_orderEntity.getOrderNo().equals(jsonObject.getString("developerPayload"))
                    || 0 != jsonObject.getInteger("purchaseState")
                    || StringUtils.isBlank(jsonObject.getString("purchaseToken"))) {
                logger.info("谷歌支付通知结果,purchaseData={}", _orderEntity.getPurchaseData());
                return false;
            }
            //设置谷歌支付流水号
            if(StringUtils.isNotBlank(jsonObject.getString("orderId"))){
                _orderEntity.setTradeNo(jsonObject.getString("orderId"));
            }
            //到谷歌服务端去验证订单是否支付
            return validateBuyGoogleProduct(_orderEntity, jsonObject);
        } catch (Exception e) {
            logger.error("验证谷歌支付通知结果异常", e);
            return false;
        }
    }

    /**
     * 到谷歌Api验证谷歌商品状态
     *
     * @param _orderEntity
     * @param purchaseJson
     * @return
     */
    private boolean validateBuyGoogleProduct(PublishOrderEntity _orderEntity, JSONObject purchaseJson) {
        String url = domainConfig.getString("google.api.purchases.products.query");
        String packageName = domainConfig.getString("longyanjiuguan.google.packagename");
        String productId = purchaseJson.getString("productId");
        String purchaseToken = purchaseJson.getString("purchaseToken");
        String access_token = getGoogleAccessToken(RedisConstants.GOOGLE_API_ACCESS_TOKEN);
        StringBuffer validateUrl = new StringBuffer(url);
        validateUrl.append("/" + packageName);
        validateUrl.append("/purchases/products");
        validateUrl.append("/" + productId);
        validateUrl.append("/tokens/" + purchaseToken);
        validateUrl.append("?access_token=" + access_token);
        String response = HttpsUtil.requestGet(validateUrl.toString());
        logger.info("到谷歌Api验证谷歌商品支付状态，url:{},响应结果:{}", validateUrl.toString(), response);
        JSONObject jsonObject = null;
        if (StringUtils.isBlank(response) || (jsonObject = JSON.parseObject(response)) == null || StringUtils.isBlank(jsonObject.getString("developerPayload"))) {
            return false;
        }
        //developerPayload 是否是sdk订单号
        if (!_orderEntity.getOrderNo().equals(jsonObject.getString("developerPayload"))) {
            logger.error("到谷歌Api验证谷歌商品状态失败，谷歌透传订单号:{}，与sdk订单号:{}不符", jsonObject.getString("developerPayload"), _orderEntity.getOrderNo());
            return false;
        }
        //The purchase state of the order. Possible values are:0:Purchased,1:Cancelled
        return jsonObject.getInteger("purchaseState") == 0;
    }


    /**
     * 服务器端验证苹果支付结果
     *
     * @param _orderEntity
     */
    private boolean validateApplePay(PublishOrderEntity _orderEntity) {
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String url = "https://buy.itunes.apple.com/verifyReceipt";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("receipt-data", _orderEntity.getReceiptData());
        String response = HttpsUtil.requestPostJson(url, JSONObject.toJSONString(map), "UTF-8", "UTF-8");
        logger.info("海外苹果支付验证票根，sdkOrderNo:{},url:{},苹果验证响应结果:{}", _orderEntity.getOrderNo(), url, response);
        if (StringUtils.isBlank(response) || (jsonObject = JSONObject.parseObject(response)) == null) {
            return false;
        }
        if (21007 == jsonObject.getInteger("status")) { //status=21007，则表示当前的收据为沙盒环境下收据，需要调用到沙河环境验证
            url = "https://sandbox.itunes.apple.com/verifyReceipt";
            response = HttpsUtil.requestPostJson(url, JSONObject.toJSONString(map), "UTF-8", "UTF-8");
            logger.info("海外苹果支付沙河环境验证票根，sdkOrderNo:{},url:{},苹果验证响应结果:{}", _orderEntity.getOrderNo(), url, response);
        }
        if (StringUtils.isBlank(response) || (jsonObject = JSONObject.parseObject(response)) == null || 0 != jsonObject.getInteger("status")
                || StringUtils.isBlank(jsonObject.getString("receipt"))
                || (jsonObject = JSONObject.parseObject(jsonObject.getString("receipt"))) == null
                || StringUtils.isBlank(jsonObject.getString("in_app")) || JSON.parseArray(jsonObject.getString("in_app")) == null
                || JSON.parseArray(jsonObject.getString("in_app")).get(0) == null
                ) {
            return false;
        }
        JSONObject inApp = JSONObject.parseObject(JSON.parseArray(jsonObject.getString("in_app")).get(0).toString());
        _orderEntity.setTradeNo(inApp.getString("transaction_id"));//苹果交易号
        //验证计费点code
        return _orderEntity.getChargePointCode().equals(inApp.getString("product_id"));
    }


    /**
     * 获取谷歌api方位AccessToken
     *
     * @param key
     * @return
     */
    public String getGoogleAccessToken(String key) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        String value = null;
        JSONObject jsonObject = null;
        try {
            jedisPool = redisUtil.getPool();
            jedis = jedisPool.getResource();
            value = jedis.get(key);
            String keyMutex = key + "mutex";
            if (StringUtils.isBlank(value)) { //代表缓存值过期
                //设置lmin的超时，防止del操作失败的时候，下次缓存过期一直不能load db
                if (jedis.setnx(keyMutex, "1") == 1) {  //代表设置成功
                    jedis.expire(keyMutex, 60);
                    value = queryGoogleAccessToken();
                    if (StringUtils.isBlank(value) || (jsonObject = JSON.parseObject(value)) == null || StringUtils.isBlank(jsonObject.getString("accessToken"))) {
                        return null;
                    }
                    jedis.set(key, jsonObject.getString("accessToken"));
                    jedis.expire(key, (jsonObject.getInteger("expiresTime") - 30));
                    jedis.del(keyMutex);
                    value = jsonObject.getString("accessToken");
                } else {  //这个时候代表同时候的其他线程已经load db并回设到缓存了，这时候重试获取缓存值即可
                    Thread.sleep(50);//休眠50毫秒
                    getGoogleAccessToken(key);  //重试
                }
            } else {
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
        } finally {
            if (jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
        }
        return value;
    }


    /**
     * 获取谷歌api访问AccessToken
     *
     * @return
     */
    private String queryGoogleAccessToken() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            String url = domainConfig.getString("google.api.token.url");
            StringBuilder sb = new StringBuilder();
            sb.append("refresh_token=").append(domainConfig.getString("google.api.refresh.token")).append("&");
            sb.append("client_id=").append(domainConfig.getString("google.api.client.id")).append("&");
            sb.append("client_secret=").append(domainConfig.getString("google.api.client.secret")).append("&");
            sb.append("grant_type=refresh_token");

            String result = UrlUtil.post(url, sb.toString());
            logger.info("获取谷歌api访问AccessToken,url:{},请求参数:{},响应结果:{}", url, sb.toString(), result);
            JSONObject jsonObject = null;
            if (StringUtils.isNotBlank(result) && null != (jsonObject = JSON.parseObject(result)) || StringUtils.isNotBlank(jsonObject.getString("access_token"))) {
                map.clear();
                map.put("accessToken", jsonObject.getString("access_token"));
                map.put("expiresTime", jsonObject.getInteger("expires_in"));
                return JSONObject.toJSONString(map);
            }
        } catch (Exception e) {
            logger.error("获取谷歌AccessToken异常", e);
        }
        return null;
    }

}
