package com.dome.sdkserver.listener.mqlistener;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.constants.PayConstant.PAY_CH_TYPE;
import com.dome.sdkserver.bq.constants.PayConstant.TRADE_TYPE;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.bq.util.PriceUtil;
import com.dome.sdkserver.metadata.entity.bq.pay.PublishOrderEntity;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RSACoder;
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
 * GamePayListener
 *
 * @author Zhang ShanMin
 * @date 2017/2/27
 * @time 11:29
 */
@Component("mobileGamePayListener")
public class MobileGamePayListener implements MessageListener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "payConfig")
    protected PropertiesUtil payConfig;

    @Override
    public void onMessage(Message message) {
        try {
            String body = new String(message.getBody(), "utf-8");
            PublishOrderEntity orderEntity = JSONObject.parseObject(body, PublishOrderEntity.class);
            PAY_CH_TYPE pay_ch_type = PAY_CH_TYPE.getPayType(orderEntity.getPayType());
            TRADE_TYPE tradeType = TRADE_TYPE.getTradeType(orderEntity.getTradeType());
            logger.info(">>>>>>>>>>>>>>>>>>>>>手游支付异步通知:{}" + JSONObject.toJSONString(orderEntity));
            if (tradeType == null || pay_ch_type == null) {
                logger.error("手游支付异步通知交易模式为空:{}" + JSONObject.toJSONString(orderEntity));
                logger.error("Rabbitmq->手游支付异步通知交易模式为空,sdk订单号:{}，支付来源:{},交易模式:{}", orderEntity.getOrderNo(), pay_ch_type == null ? "" : pay_ch_type.name(), orderEntity.getTradeType());
                return;
            }
            Map<String, String> data = new HashMap<String, String>();
            switch (tradeType) {
                case app: {
                    assembleAppBuyParams(orderEntity, data);
                    break;
                }
                case web: {
                    assembleWebBuyParams(orderEntity, data);
                    break;
                }
            }
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("responseCode", "1000"));
            pairs.add(new BasicNameValuePair("errorCode", ""));
            pairs.add(new BasicNameValuePair("errorMsg", ""));
            pairs.add(new BasicNameValuePair("data", JSONObject.toJSONString(data)));
            String url = payConfig.getString("app.game.pay.callback.url." + orderEntity.getAppCode());
            String res = ApiConnector.post(url, pairs);
            logger.info("Rabbitmq->手游支付异步通知,sdk订单号:{}，支付来源:{},请求url:{},请求参数:{},响应结果:{}",
                    orderEntity.getOrderNo(),
                    pay_ch_type == null ? "" : pay_ch_type.name(),
                    url,
                    pairs,
                    res);
        } catch (Exception e) {
            logger.error("Rabbitmq->手游支付异步通知异常", e);
        }
    }

    /**
     * 组装海外sdk客户端支付异步通知游戏cp参数
     *
     * @param orderEntity
     * @param data
     * @throws Exception
     */
    private void assembleAppBuyParams(PublishOrderEntity orderEntity, Map<String, String> data) throws Exception {
        data.put("sdkflowId", orderEntity.getOrderNo());
        data.put("orderNo", orderEntity.getGameOrderNo());
        data.put("userId", orderEntity.getBuyerId());
        data.put("chargePointCode", orderEntity.getChargePointCode());
        data.put("chargePointAmount", PriceUtil.convertToFen(orderEntity.getChargePointAmount().toString()) + "");
        data.put("payFrom", getPayFrom(orderEntity));
        data.put("signCode", getSignCode(data));//添加签名
    }


    /**
     * 组装海外sdk pc端支付异步通知游戏cp参数
     *
     * @param orderEntity
     * @param data
     * @throws Exception
     */
    private void assembleWebBuyParams(PublishOrderEntity orderEntity, Map<String, String> data) throws Exception {
        if (orderEntity.getAppCode().equals("D0000287")) { //仅针对龙焰酒馆
            data.put("orderNo", orderEntity.getRoleId() + "_" + orderEntity.getGameOrderNo());
        } else {
            data.put("roleId", orderEntity.getRoleId());
            data.put("zoneId", orderEntity.getZoneId());
        }
        data.put("sdkflowId", orderEntity.getOrderNo());
        data.put("userId", orderEntity.getBuyerId());
        data.put("chargePointCode", orderEntity.getChargePointCode());
        data.put("chargePointAmount", PriceUtil.convertToFen(orderEntity.getChargePointAmount().toString()) + "");
        data.put("payFrom", getPayFrom(orderEntity));
        data.put("signCode", getSignCode(data));//添加签名
    }

    /**
     * 获取支付来源
     *
     * @param orderEntity
     * @return
     */
    private String getPayFrom(PublishOrderEntity orderEntity) {
        PAY_CH_TYPE pay_ch_type = PAY_CH_TYPE.getPayType(orderEntity.getPayType());
        switch (pay_ch_type) {
            case GOOGLEPAY: {
                return "AD";
            }
            case APPLEPAY: {
                return "IOS";
            }
            case MAYCARD: {
                TRADE_TYPE tradeType = TRADE_TYPE.getTradeType(orderEntity.getTradeType());
                switch (tradeType) {
                    case app: {
                        return "AD";
                    }
                    case web: {
                        return "PC";
                    }
                }
            }
            default:
                return "NK";
        }
    }

    /**
     * 处理签名
     *
     * @param orderEntity
     * @return
     * @throws Exception
     */
    private String getSignCode(PublishOrderEntity orderEntity) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("responseCode=").append("1000").append(",")
                .append("errorCode=").append("\"\"")
                .append("sdkflowId=").append(orderEntity.getOrderNo()).append(",")
                .append("orderNo=").append(orderEntity.getGameOrderNo());
        return RSACoder.sign(sb.toString().getBytes(), payConfig.getString("async.private.key"));
    }

    /**
     * 处理签名
     *
     * @param data
     * @return
     * @throws Exception
     */
    private String getSignCode(Map<String, String> data) throws Exception {
        return RSACoder.sign(MapUtil.createLinkString(data).getBytes(), payConfig.getString("async.private.key"));
    }
}
