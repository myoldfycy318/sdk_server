package com.dome.sdkserver.biz.pay;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.entity.DomeRequestEntity;
import com.dome.sdkserver.biz.executor.*;
import com.dome.sdkserver.bq.enumeration.PaySource2BiEnum;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.service.pay.bq.PayNotifyService;
import com.dome.sdkserver.service.rabbitmq.RabbitMqService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PayNotifyBiz {

    protected static final Logger log = LoggerFactory.getLogger(PayNotifyBiz.class);

    @Autowired
    protected PropertiesUtil domainConfig;

    @Autowired
    protected OrderService orderService;
    @Resource(name = "rabbitMqService")
    protected RabbitMqService rabbitMqService;
    @Resource(name = "executor")
    protected Executor executor;
    @Resource(name = "payNotifyService")
    protected PayNotifyService payNotifyService;

    public abstract String payBizProcess(Map<String, String[]> params, JSONObject payConfig);

    public abstract String payBizProcess2(Map<String, String> params, JSONObject payConfig);

    public abstract SdkOauthResult aliPcSyncNotifyProcess(Map<String, String[]> params, JSONObject payConfig);

    /**
     * 异步通知
     */
    public boolean asyncNotice(DomeRequestEntity entity, int maxTryTimes, int sleep) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("responseCode", entity.getResponseCode()));
        pairs.add(new BasicNameValuePair("errorCode", entity.getErrorCode()));
        pairs.add(new BasicNameValuePair("errorMsg", entity.getErrorMsg()));
        pairs.add(new BasicNameValuePair("data", JSONObject.toJSONString(entity.getData())));

        Boolean isSuccess = false;
        try {
            pairs.add(new BasicNameValuePair("signCode", entity.getSignCode()));
        } catch (Exception e) {
            log.error("签名生成失败", e);
            return false;
        }
        try {
            String res;
            for (int i = 0; i < maxTryTimes; i++) {
                res = ApiConnector.post(entity.getPayNotifyUrl(), pairs);
                if (!StringUtils.isBlank(res)) {
                    JSONObject json = JSONObject.parseObject(res);
                    isSuccess = json.getBoolean("isSuccess");
                    break;
                } else {
                    Thread.sleep(sleep);
                }
            }

        } catch (Exception e) {
            log.error("请求游戏服务端失败", e);
            return false;
        } finally {

        }

        return isSuccess;
    }

    /**
     * 验证请求参数
     *
     * @param params
     * @param partner
     * @param order
     * @return
     */
    protected SdkOauthResult validateNotifyParams(Map<String, String> params, String partner, OrderEntity order) {
        String out_trade_no = params.get("out_trade_no");
        //支付宝交易号
        String trade_no = params.get("trade_no");
        //交易状态
        String trade_status = params.get("trade_status");
        String sellerId = params.get("seller_id");
        String totalFee = params.get("total_fee");
        if (!sellerId.equals(partner)) {
            log.error("卖家ID不一致,sellerId = " + sellerId + "partner = " + partner);
            SdkOauthResult.failed("卖家ID不一致");
        }
        if (order == null) {
            log.error("无效的订单号 orderNo = " + out_trade_no);
            SdkOauthResult.failed("无效的订单");

        }
        if (order.getChargePointAmount() != Double.parseDouble(totalFee)) {
            log.error("订单金额不一致,totalFee = " + totalFee + "amount = " + order.getChargePointAmount() + "out_trade_no =" + out_trade_no);
            return SdkOauthResult.failed("订单金额不一致");
        }
        return SdkOauthResult.success();
    }



    /**
     * 构建支付成功后异步通知对象
     *
     * @param order
     * @param payConfig
     * @return
     */
    private DomeRequestEntity buildNotifyEntity(OrderEntity order, JSONObject payConfig) {
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
        requestEntity.setPrivateKey(payConfig.getString(BqSdkConstants.asyncPrivateKey));
        requestEntity.setMaxTryTimes(payConfig.getInteger(BqSdkConstants.asyncNoticeMaxTryTimes));
        requestEntity.setSleepTime(payConfig.getInteger(BqSdkConstants.asyncNoticeThreadSleep));
        requestEntity.setPaySources(PaySource2BiEnum.domepay.name());
        requestEntity.setAppCode(order.getAppCode());
        requestEntity.setOrderStatus(order.getOrderStatus());
        return requestEntity;
    }
}
