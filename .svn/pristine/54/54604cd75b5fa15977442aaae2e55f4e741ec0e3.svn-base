package com.dome.sdkserver.biz.executor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.CBStatusEnum;
import com.dome.sdkserver.biz.enums.OrderStatusEnum;
import com.dome.sdkserver.bq.constants.PayConstant;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.MD5;
import com.dome.sdkserver.util.PriceUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 福禄支付宝即时到帐支付成功后异步通知
 *
 * @author Zhang ShanMin
 * @date 2016-12-19
 * @time 10:43
 */
public class FuLuAliPayNotifyThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(FuLuAliPayNotifyThread.class);
    private OrderEntity orderEntity;
    private OrderService orderService;


    public FuLuAliPayNotifyThread(OrderEntity orderEntity, Object object) {
        this.orderEntity = orderEntity;
        this.orderService = (OrderService) object;
    }

    private static Properties properties = new Properties();

    static {
        InputStream in = null;
        try {
            logger.error("外部调用即时到支付异步通知加载pay.properties.....");
            String filePath = "/pay.properties";
            in = WebpageGamePayNotifyThread.class.getResourceAsStream(filePath);
            if (in == null) {
                logger.info("domain.properties not found");
            } else {
                if (!(in instanceof BufferedInputStream)) {
                    in = new BufferedInputStream(in);
                }
                properties.load(in);
            }
        } catch (Exception e) {
            logger.error("外部调用支付宝即时到支付异步通知加载pay.properties异常", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    @Override
    public void run() {
        int maxTryTimes = 3;
        int sleepTime = 1000 * 60;
        try {
            String payResult = OrderStatusEnum.orderstatus_pay_sucess.code == orderEntity.getOrderStatus() ? "true" : "false";
            PayConstant.PAY_REQ_ORIGIN payReqOrigin = PayConstant.PAY_REQ_ORIGIN.getPayReqOrigin(orderEntity.getPayOrigin());
            if (payReqOrigin == null) {
                logger.error("外部:{}即时到帐异步通知异常，获取不到支付来源，订单号:{},用户:{},支付结果:{}",
                        orderEntity.getPayOrigin(), orderEntity.getOrderNo(), orderEntity.getBuyerId(), payResult);
            }
            Map<String, String> map = new HashMap<String, String>(9);
            map.put("outOrderNo", orderEntity.getGameOrderNo());
            map.put("buyerId", orderEntity.getBuyerId());
            map.put("totalFee", PriceUtil.convertToFen(new BigDecimal(orderEntity.getChargePointAmount())).longValue() + "");
            map.put("sdkOrderNo", orderEntity.getOrderNo());
            map.put("payResult", payResult);
            map.put("payTime", DateUtils.toDateText(new Date(), "yyyMMddHHmmss"));
            map.put("payOrigin", orderEntity.getPayOrigin());
            map.put("signCode", MD5.md5Encode(MapUtil.createLinkString(map) + "&key=" + properties.getProperty(payReqOrigin.getNotifyKey())));
            map.put("buyerAccount", orderEntity.getBuyerAccount());
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            JSONObject json = null;
            for (int i = 0; i < maxTryTimes; i++) {
                String res = ApiConnector.post(orderEntity.getPayNotifyUrl(), pairs);
                logger.info("外部:{}即时到支付异步通知,请求url:{},请求参数:{},响应结果:{}", orderEntity.getPayOrigin(), orderEntity.getPayNotifyUrl(), pairs, res);
                if (!StringUtils.isBlank(res) && "success".equals(res)) {
                    JSONObject extraField = JSONObject.parseObject(orderEntity.getExtraField());
                    extraField.put("order", "paid");
                    orderEntity.setExtraField(JSON.toJSONString(extraField));
                    orderEntity.setCallbackStatus(CBStatusEnum.RESP_SUCCESS.getCode());
                    orderService.updateOrderInfo(orderEntity, PayUtil.getPayMonth(orderEntity.getOrderNo()));
                    break;
                } else {
                    Thread.sleep(sleepTime);
                }
            }
        } catch (Exception e) {
            logger.error("外部支付宝即时到支付异步通知异常", e);
        }
    }

}
