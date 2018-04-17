package com.dome.sdkserver.listener.mqlistener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.entity.DomeRequestEntity;
import com.dome.sdkserver.bq.constants.PayConstant;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.metadata.entity.bq.pay.PublishOrderEntity;
import com.dome.sdkserver.service.pay.mycard.PublishOrderService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国内手游ios支付道具下发通知
 *
 * @author Zhang ShanMin
 * @date 2017/2/27
 * @time 11:29
 */
@Component("inlandMGameIosPayListener")
public class InlandMGameIosPayListener implements MessageListener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "payConfig")
    protected PropertiesUtil propertiesUtil;
    @Autowired
    private PublishOrderService publishOrderService;

    @Override
    public void onMessage(Message message) {
        try {
            String body = new String(message.getBody(), "utf-8");
            PublishOrderEntity orderEntity = JSONObject.parseObject(body, PublishOrderEntity.class);
            PayConstant.PAY_CH_TYPE pay_ch_type = PayConstant.PAY_CH_TYPE.getPayType(orderEntity.getPayType());
            PayConstant.TRADE_TYPE tradeType = PayConstant.TRADE_TYPE.getTradeType(orderEntity.getTradeType());
            if (tradeType == null || pay_ch_type == null) {
                logger.error("国内手游支付异步通知交易模式为空:{}" + JSONObject.toJSONString(orderEntity));
                logger.error("Rabbitmq->国内手游支付异步通知交易模式为空,sdk订单号:{}，支付来源:{},交易模式:{}", orderEntity.getOrderNo(), pay_ch_type == null ? "" : pay_ch_type.name(), orderEntity.getTradeType());
                return;
            }
            DomeRequestEntity entity = buildNotifyEntity(orderEntity);//构建支付成功后异步通知对象
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("responseCode", entity.getResponseCode()));
            pairs.add(new BasicNameValuePair("errorCode", entity.getErrorCode()));
            pairs.add(new BasicNameValuePair("errorMsg", entity.getErrorMsg()));
            pairs.add(new BasicNameValuePair("data", JSONObject.toJSONString(entity.getData())));
            pairs.add(new BasicNameValuePair("signCode", entity.getSignCode()));
            int maxTryTimes = entity.getMaxTryTimes();
            int sleep = entity.getSleepTime();
            String res = null;
            JSONObject json = null;
            for (int i = 0; i < maxTryTimes; i++) {
                res = ApiConnector.post(entity.getPayNotifyUrl(), pairs);
                logger.info("国内手游ios支付通知,请求url:{},请求参数:{},响应结果:{}", entity.getPayNotifyUrl(), pairs, res);
                if (!StringUtils.isBlank(res) && (json = JSONObject.parseObject(res)) != null && json.getBoolean("isSuccess") != null && json.getBoolean("isSuccess")) {
                    Map<String, String> extraField = new HashMap<>();
                    extraField.put("game", "paid");
                    PublishOrderEntity orderEntity2 = new PublishOrderEntity();
                    orderEntity2.setExtraField(JSON.toJSONString(extraField));
                    orderEntity2.setOrderNo(orderEntity.getOrderNo());
                    orderEntity2.setCurMonth(PayUtil.getPayMonth(orderEntity.getOrderNo()));
                    publishOrderService.updateOrderById(orderEntity2);
                    break;
                } else {
                    Thread.sleep(sleep);
                }
            }
        } catch (Exception e) {
            logger.error("rabbitmq国内手游异步通知游戏异常", e);
        }
    }


    /**
     * 构建支付成功后异步通知对象
     *
     * @param order
     * @param
     * @return
     */
    private DomeRequestEntity buildNotifyEntity(PublishOrderEntity order) {
        DomeRequestEntity requestEntity = new DomeRequestEntity();
        Map<String, String> data = new HashMap<String, String>();
        data.put("sdkflowId", order.getOrderNo());
        data.put("orderNo", order.getGameOrderNo());
        requestEntity.setResponseCode("1000");
        requestEntity.setErrorCode("");
        requestEntity.setErrorMsg("");
        requestEntity.setOrderNo(order.getGameOrderNo());
        requestEntity.setSdkflowId(order.getOrderNo());
        requestEntity.setPayNotifyUrl(order.getPayNotifyUrl());
        requestEntity.setData(data);
        requestEntity.setPrivateKey(propertiesUtil.getString("async.private.key"));
        requestEntity.setMaxTryTimes(propertiesUtil.getInt("async.notice.maxTryTimes", "3"));
        requestEntity.setSleepTime(propertiesUtil.getInt("async.notice.thread.sleep", "5000"));
        return requestEntity;
    }

}
