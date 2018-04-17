package com.dome.sdkserver.listener.mqlistener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.CBStatusEnum;
import com.dome.sdkserver.biz.utils.BizUtil;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.bq.util.PriceUtil;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.util.ApiConnector;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页游游戏支付异步通知
 * WebGamePayNotifyListener
 *
 * @author Zhang ShanMin
 * @date 2017/11/8
 * @time 19:34
 */
@Component("webGamePayNotifyListener")
public class WebGamePayNotifyListener implements MessageListener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "orderService")
    protected OrderService orderService;

    @Override
    public void onMessage(Message message) {
        try {
            String body = new String(message.getBody(), "utf-8");
            OrderEntity orderEntity = JSONObject.parseObject(body, OrderEntity.class);
            logger.info("页游异步通知请求参数:{}", JSON.toJSONString(orderEntity));
            if (orderEntity.getOrderStatus() != 1) return;
            AppInfoEntity appInfoEntity = BizUtil.getAppInfo(orderEntity.getAppCode());
            if (appInfoEntity == null) {
                logger.info("页游通知失败,订单:{}，未获取应用appCode:{}的应用信息", orderEntity.getOrderNo(), orderEntity.getAppCode());
                return;
            }
            if (StringUtils.isBlank(orderEntity.getPayNotifyUrl())) {
                logger.info("页游通知失败,订单:{}，异步通知地址为空", orderEntity.getOrderNo());
                return;
            }
            final Map<String, String> params = new HashMap<String, String>();
            params.put("appCode", orderEntity.getAppCode());
            params.put("payResult", orderEntity.getOrderStatus() == 1 ? "true" : "false");
            params.put("cpTradeNo", orderEntity.getGameOrderNo());
            params.put("outOrderNo", orderEntity.getOrderNo());
            params.put("userId", orderEntity.getBuyerId());
            Double totalFee = (Double) orderEntity.getChargePointAmount();
            params.put("totalFee", PriceUtil.convertToFen(totalFee.toString()) + "");
            params.put("ts", System.currentTimeMillis() + "");
            String signBf = MapUtil.createLinkString(params) + "&" + appInfoEntity.getAppKey();
            params.put("sign", DigestUtils.md5Hex(signBf));
            String attach = "";
            JSONObject jsonObject = null;
            if (StringUtils.isNotBlank(orderEntity.getExtraField()) && (jsonObject = JSONObject.parseObject(orderEntity.getExtraField())) != null
                    && StringUtils.isNotBlank(jsonObject.getString("attach"))) {
                attach = jsonObject.getString("attach");
            }
            params.put("attach", attach);
            List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            int maxTryTimes = 3;
            int sleepTime = 1000 * 60;
            String res = null;
            for (int i = 0; i < maxTryTimes; i++) {
                try {
                    res = ApiConnector.post(orderEntity.getPayNotifyUrl(), paramsList);
                    logger.info("页游支付异步通知,请求url:{},请求参数:{},响应结果:{}", orderEntity.getPayNotifyUrl(), paramsList, res);
                    if (!StringUtils.isBlank(res) && res.equals("success")) {
                        orderEntity.setCallbackStatus(CBStatusEnum.RESP_SUCCESS.getCode());
                        if (orderEntity.getPayOrigin().equalsIgnoreCase("OGP")) {
                            orderEntity.setCurMonth(PayUtil.getThirdPayMonth(orderEntity.getOrderNo()));
                            orderService.updateThirdOrder(orderEntity);
                        }
                        if (orderEntity.getPayOrigin().equalsIgnoreCase("pc")) {
                            orderService.updateOrderInfo(orderEntity, PayUtil.getPayMonth(orderEntity.getOrderNo()));
                        }
                        break;
                    } else {
                        Thread.sleep(sleepTime);
                    }
                } catch (InterruptedException e) {
                    logger.error("页游支付异步通知异常，订单：{}", orderEntity.getOrderNo(), e);
                }
            }
        } catch (Exception ex) {
            logger.error("页游支付异步通知异常", ex);
        }

    }


}
