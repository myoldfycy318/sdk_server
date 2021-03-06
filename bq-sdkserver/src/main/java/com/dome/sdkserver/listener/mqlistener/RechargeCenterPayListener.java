package com.dome.sdkserver.listener.mqlistener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.CBStatusEnum;
import com.dome.sdkserver.bq.util.HttpsUtil;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.util.MD5;
import com.dome.sdkserver.util.PriceUtil;
import com.dome.sdkserver.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 游戏充值中心
 *
 * @author Zhang ShanMin
 * @date 2017/6/6
 * @time 11:29
 */
@Component("rechargeCentrePayListener")
public class RechargeCenterPayListener implements MessageListener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "payConfig")
    protected PropertiesUtil propertiesUtil;
    @Resource(name = "orderService")
    protected OrderService orderService;

    @Override
    public void onMessage(Message message) {
        try {
            String body = new String(message.getBody(), "utf-8");
            OrderEntity orderEntity = JSONObject.parseObject(body, OrderEntity.class);
            JSONObject extraField = JSONObject.parseObject(orderEntity.getExtraField());
            Map<String, String> map = new HashMap<String, String>(9);
            map.put("sdkflowId", orderEntity.getOrderNo());
            map.put("userId", extraField.getString("rechargeUserId"));
            map.put("zooCode", extraField.getString("zooCode"));
            map.put("serverCode", extraField.getString("serverCode"));
            map.put("chargePointCode", orderEntity.getChargePointCode());
            map.put("chargePointAmount", String.valueOf(PriceUtil.convertToFenL(new BigDecimal(orderEntity.getChargePointAmount())).longValue()));
            map.put("signCode", MD5.md5Encode(MapUtil.createLinkString(map) + "&" + propertiesUtil.getString("recharge.centre.notify.md5.sign.key")));
            String res = null;
            JSONObject json = null;
            for (int i = 0; i < 3; i++) {
                res = HttpsUtil.requestPost(orderEntity.getPayNotifyUrl(), map);
                logger.info("游戏充值中心支付通知,请求url:{},请求参数:{},响应结果:{}", orderEntity.getPayNotifyUrl(), map, res);
                if (!StringUtils.isBlank(res) && (json = JSONObject.parseObject(res)) != null && json.getBoolean("isSuccess") != null && json.getBoolean("isSuccess")) {
                    extraField.put("game", "paid");
                    orderEntity.setExtraField(JSON.toJSONString(extraField));
                    orderEntity.setCallbackStatus(CBStatusEnum.RESP_SUCCESS.getCode());
                    orderService.updateOrderInfo(orderEntity, PayUtil.getPayMonth(orderEntity.getOrderNo()));
                    break;
                } else {
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            logger.error("rabbitmq游戏充值中心异步通知异常", e);
        }
    }


}
