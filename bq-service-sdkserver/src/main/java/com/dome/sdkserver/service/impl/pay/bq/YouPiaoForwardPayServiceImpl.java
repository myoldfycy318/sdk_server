package com.dome.sdkserver.service.impl.pay.bq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.CBStatusEnum;
import com.dome.sdkserver.biz.enums.OrderStatusEnum;
import com.dome.sdkserver.biz.utils.H5GameUtil;
import com.dome.sdkserver.bq.constants.ThirdPartyEnum;
import com.dome.sdkserver.bq.enumeration.H5Game2PlatformEnum;
import com.dome.sdkserver.bq.enumeration.PaySource2BiEnum;
import com.dome.sdkserver.bq.enumeration.PayType2BiEnum;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.util.GenOrderCode;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.h5game.H5GameEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.PayRecordSync;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.login.UserLoginService;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.service.pay.bq.ForwardPayService;
import com.dome.sdkserver.service.rabbitmq.RabbitMqService;
import com.dome.sdkserver.util.MD5;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 冰趣H5游戏跳有票支付页
 * YouPiaoForwardPayServiceImpl
 *
 * @author Zhang ShanMin
 * @date 2016/11/10
 * @time 15:55
 */
@Service("youPiaoForwardPay")
public class YouPiaoForwardPayServiceImpl implements ForwardPayService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;
    @Autowired
    private PropertiesUtil domainConfig;
    @Autowired
    private RedisUtil redisUtil;
    @Resource(name = "userLoginService")
    protected UserLoginService userLoginService;
    @Resource(name = "rabbitMqService")
    protected RabbitMqService rabbitMqService;

    //冰趣H5游戏宝玩渠道用户信息
    String NEWWAPGAME_BW_USER_USERID = "newwapgame:bw:user:userid:";

    /**
     * 跳转有票支付页
     *
     * @param h5GameEntity
     * @param appInfoEntity
     * @return
     */
    @Override
    public SdkOauthResult toPayPage(H5GameEntity h5GameEntity, AppInfoEntity appInfoEntity) throws Exception {

        String cacheResult = redisUtil.get(NEWWAPGAME_BW_USER_USERID + h5GameEntity.getUserId());
        if (StringUtils.isBlank(cacheResult) || null == JSONObject.parseObject(cacheResult))
            return SdkOauthResult.failed("找不到对应的用户信息");
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(h5GameEntity, orderEntity);
        orderEntity.setOrderNo(GenOrderCode.next(""));
        orderEntity.setPayNotifyUrl(appInfoEntity.getPayNotifyUrl());
        orderEntity.setCurMonth(DateUtils.getCurDateFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT));
        orderEntity.setAppCode(appInfoEntity.getAppCode());
        orderEntity.setAppName(appInfoEntity.getAppName());
        //sdk_third_party_order_$ 通过有票进入H5游戏，之前通知游戏cp用户userId都是钱宝userId，现因BI需求游戏转化表需要使用冰穹userId 2017-9-20 By_zhangshanmin
        //钱宝支付设置冰穹userId
        JSONObject jsonObject = userLoginService.getUserInfoByOpenId(ThirdPartyEnum.QBAO.getThirdId(), String.valueOf(h5GameEntity.getUserId()));
        if (jsonObject != null && StringUtils.isNotBlank(jsonObject.getString("userId"))) {
            orderEntity.setBuyerId(jsonObject.getString("userId"));
        }
        orderEntity.setChargePointAmount(Double.valueOf(h5GameEntity.getPrice()));
        orderEntity.setChargePointName(h5GameEntity.getItem());
        orderEntity.setChargePointCode(h5GameEntity.getItemCode());
        orderEntity.setOrderStatus(OrderStatusEnum.orderstatus_no_pay.code);
        orderEntity.setCallbackStatus(CBStatusEnum.NO_NOTIFY.getCode());
        orderEntity.setChannelCode(BqSdkConstants.channelCodeYouPiao);
        orderEntity.setPlatformCode(H5Game2PlatformEnum.YOUPIAO.name());
        orderEntity.setGameOrderNo(h5GameEntity.getOutOrderNo());
        handleExtraField(orderEntity, h5GameEntity);
        orderService.insertThirdOrder(orderEntity);
        youpiaoPaySyncBi(orderEntity);//有票支付数据同步到Bi报表_2017-9-28_by_zhangshanmin
        //处理有票签名
        Map<String, String> reqParamsMap = handleReqParams(h5GameEntity, appInfoEntity, cacheResult, orderEntity);
        String ypPayPageParamsLink = H5GameUtil.createLinkString(H5GameUtil.valURLEncode(reqParamsMap));
        logger.info("调有票支付页,请求参数:{}", ypPayPageParamsLink);
        return SdkOauthResult.success("/paysdkyoupiao.html?" + ypPayPageParamsLink);
    }

    /**
     * 有票支付数据同步到Bi报表
     *
     * @param orderEntity
     */
    void youpiaoPaySyncBi(OrderEntity orderEntity) {
        try {
            PayRecordSync syncEntity = new PayRecordSync();
            BeanUtils.copyProperties(orderEntity, syncEntity);
            syncEntity.setCpOrderNo(orderEntity.getGameOrderNo());
            syncEntity.setPaySources(PaySource2BiEnum.thirdpay.name());
            syncEntity.setPayType(PayType2BiEnum.yp.name());
            syncEntity.setUserId(orderEntity.getBuyerId());
            syncEntity.setChargePointAmount(new BigDecimal(orderEntity.getChargePointAmount()));
            rabbitMqService.syncInsertPayData2Bi(syncEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理有票签名
     *
     * @param h5GameEntity
     * @param appInfoEntity
     * @param cacheResult
     * @param orderEntity
     * @throws UnsupportedEncodingException
     */
    private Map<String, String> handleReqParams(H5GameEntity h5GameEntity, AppInfoEntity appInfoEntity, String cacheResult, OrderEntity orderEntity) throws Exception {
        Map<String, String> paramsMap = new HashMap<>(13);
        paramsMap.put("appId", domainConfig.getString("youpiao.appId"));//有票分配给第三方的应用唯一标识
        paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        paramsMap.put("outTradeNo", orderEntity.getOrderNo());//第三方订单号
        paramsMap.put("userCode", JSONObject.parseObject(cacheResult).getString("userCode"));//用户code
        paramsMap.put("totalFee", String.valueOf(Integer.valueOf(h5GameEntity.getPrice()) * 100));//支付金额 单位分 必须为正整数
        paramsMap.put("title", appInfoEntity.getAppName() + "_充值");//商品的标题/交易标题/订单标题/订单关键字
        StringBuilder detail = new StringBuilder();
        detail.append(appInfoEntity.getAppName()).append("_");
         /*
        if (StringUtils.isNotBlank(h5GameEntity.getZoneName()))
            detail.append(URLDecoder.decode(h5GameEntity.getZoneName(), "utf-8")).append("_");
        if (StringUtils.isNotBlank(h5GameEntity.getItem()))
            detail.append(URLDecoder.decode(h5GameEntity.getItem(), "utf-8")).append("_");
            */
        detail.append("充值");
        paramsMap.put("detail", detail.toString());//订单详细描述
        paramsMap.put("timeout", String.valueOf(300));//订单失效时间，单位为秒，必须为非零正整数
        StringBuilder jumpUrl = new StringBuilder();
        jumpUrl.append(domainConfig.getString("youpiao.pay.success.jump.url", "http://sdkserver.domestore.cn/h5game/entry"));
        jumpUrl.append("?").append("userId=").append(h5GameEntity.getUserId());
        jumpUrl.append("&").append("appCode=").append(appInfoEntity.getAppCode());
        jumpUrl.append("&").append("platformCode=").append(H5Game2PlatformEnum.YOUPIAO.name());
        paramsMap.put("jumpUrl", jumpUrl.toString());//支付成功跳转地址
        String paramSignStr = H5GameUtil.createLinkString(paramsMap);//将参数拼装
        //通讯密钥
        String secKey = domainConfig.getString("youpiao.md5.sign.key");
        secKey = "&md5Key=" + secKey;
        //参数尾部加上通讯密钥进行加密，生成签名。
        String targetSign = MD5.md5Encode(paramSignStr + secKey);
        paramsMap.put("sign", targetSign);
        return paramsMap;
    }


    /**
     * 处理H5支付拓展字段
     *
     * @param orderEntity
     * @param h5GameEntity
     */
    private void handleExtraField(OrderEntity orderEntity, H5GameEntity h5GameEntity) {
        Map<String, String> extraFieldMap = new HashMap<>(3);
        extraFieldMap.put("zoneId", h5GameEntity.getZoneId());
        if (StringUtils.isNotBlank(h5GameEntity.getP1())) {
            extraFieldMap.put("p1", h5GameEntity.getP1());
        }
        if (StringUtils.isNotBlank(h5GameEntity.getP2())) {
            extraFieldMap.put("p2", h5GameEntity.getP2());
        }
        orderEntity.setExtraField(JSON.toJSONString(extraFieldMap));
    }
}
