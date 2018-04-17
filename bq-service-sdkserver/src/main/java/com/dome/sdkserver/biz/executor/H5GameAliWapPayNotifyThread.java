package com.dome.sdkserver.biz.executor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.CBStatusEnum;
import com.dome.sdkserver.biz.utils.H5GameUtil;
import com.dome.sdkserver.biz.utils.SpringBeanProxy;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.game.GameService;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.util.ApiConnector;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * H5阿里Wap支付成功后异步通知
 * H5GameQbaoPayNotifyThread
 *
 * @author Zhang ShanMin
 * @date 2016/8/26
 * @time 10:43
 */
public class H5GameAliWapPayNotifyThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(H5GameAliWapPayNotifyThread.class);

    private OrderEntity orderEntity;
    private OrderService orderService;


    public H5GameAliWapPayNotifyThread(OrderEntity orderEntity, Object object) {
        this.orderEntity = orderEntity;
        this.orderService = (OrderService) object;
    }

    @Override
    public void run() {
        JSONObject json = null;
        JSONObject extraField = null;
        int maxTryTimes = 3;
        int sleepTime = 1000*60;
        try {
            Map<String, String> map = new HashMap<String, String>(9);
            extraField = JSONObject.parseObject(orderEntity.getExtraField());
            if (StringUtils.isNotBlank(extraField.getString("p1")))
                map.put("p1", extraField.getString("p1"));
            if (StringUtils.isNotBlank(extraField.getString("p2")))
                map.put("p2", extraField.getString("p2"));
            //宝玩渠道支付cp通知给钱宝userId,其他渠道冰穹userId
            String userId = orderEntity.getChannelCode().equals(BqSdkConstants.channelCodeQbao)?orderEntity.getBwUserId():orderEntity.getBuyerId();
            map.put("userId", userId);
            map.put("zoneId", extraField.getString("zoneId"));
            map.put("orderId", String.valueOf(orderEntity.getOrderNo()));
            Double price =  orderEntity.getChargePointAmount();
            map.put("price", String.valueOf(price.intValue()));
            map.put("payTime", DateUtils.toDateText(new Date(), "yyy-MM-dd HH:mm:ss"));
            map.put("payResult", "true");
            AppInfoEntity appInfoEntity = getAppInfo();
            if (appInfoEntity == null) {
                logger.error("获取H5游戏{}应用信息为null", orderEntity.getAppCode());
                return;
            }
            String sign = H5GameUtil.generateSign(map, appInfoEntity.getAppKey());
            map.put("sign", sign);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            for (int i = 0; i < maxTryTimes; i++) {
                String res = ApiConnector.post(orderEntity.getPayNotifyUrl(), pairs);
                logger.info("H5支付宝支付通知,请求url:{},请求参数:{},响应结果:{}", orderEntity.getPayNotifyUrl(), pairs, res);
                if (!StringUtils.isBlank(res) && (json = JSONObject.parseObject(res)) != null && json.getBoolean("isSuccess") != null && json.getBoolean("isSuccess")) {
                    extraField.put("wapgame", "paid");
                    orderEntity.setExtraField(JSON.toJSONString(extraField));
                    orderEntity.setCallbackStatus(CBStatusEnum.RESP_SUCCESS.getCode());
                    orderService.updateOrderInfo(orderEntity, PayUtil.getPayMonth(orderEntity.getOrderNo()));
                    break;
                } else {
                    Thread.sleep(sleepTime);
                }
            }
        } catch (Exception e) {
            logger.error("H5钱宝支付异步通知异常", e);
        }
    }

    /**
     * 获取H5游戏应用信息
     *
     * @return
     */
    private AppInfoEntity getAppInfo() {
        GameService gameService = SpringBeanProxy.getBean(GameService.class, "gameService");
        if (gameService == null) {
            logger.error("获取GameService为null");
            return null;
        }
        AppInfoEntity appInfoEntity = gameService.getAppInfo(orderEntity.getAppCode());

        return appInfoEntity;
    }
}
