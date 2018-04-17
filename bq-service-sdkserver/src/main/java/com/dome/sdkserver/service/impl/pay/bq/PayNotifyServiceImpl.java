package com.dome.sdkserver.service.impl.pay.bq;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.entity.DomeRequestEntity;
import com.dome.sdkserver.biz.enums.CBStatusEnum;
import com.dome.sdkserver.biz.enums.OrderStatusEnum;
import com.dome.sdkserver.biz.executor.*;
import com.dome.sdkserver.biz.utils.H5GameUtil;
import com.dome.sdkserver.bq.constants.PayResEnum;
import com.dome.sdkserver.bq.constants.TransStatusEnum;
import com.dome.sdkserver.bq.enumeration.PaySource2BiEnum;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.bq.util.PriceUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.metadata.entity.qbao.PayTransEntity;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.service.pay.bq.PayNotifyService;
import com.dome.sdkserver.service.pay.qbao.bo.SdkPayResponse;
import com.dome.sdkserver.service.rabbitmq.RabbitMqService;
import com.dome.sdkserver.util.MD5;
import com.dome.sdkserver.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付通知处理类
 *
 * @author Zhang ShanMin
 * @date 2016/11/11
 * @time 14:24
 */
@Service("payNotifyService")
public class PayNotifyServiceImpl implements PayNotifyService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PropertiesUtil domainConfig;
    @Autowired
    protected OrderService orderService;
    @Resource(name = "executor")
    private Executor executor;
    @Resource(name = "rabbitMqService")
    protected RabbitMqService rabbitMqService;
    @Autowired
    protected AmqpTemplate amqpTemplate;
    @Resource
    protected PropertiesUtil payConfig;
    @Value("${async.notice.maxTryTimes}")
    private int asyncNoticeMaxTryTimes;
    @Value("${async.notice.thread.sleep}")
    private int asyncNoticeSleep;

    /**
     * 处理第三方支付通知
     *
     * @param requestParams
     * @return
     */
    @Override
    public SdkOauthResult handleThirdPayNotify(Map<String, String[]> requestParams) throws Exception {
        Map<String, String> params = new HashMap<String, String>(requestParams.size());
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        if (!validateSign(params)) {
            logger.error("钱宝有票支付通知签名验证失败");
            return SdkOauthResult.failed("钱宝有票支付通知签名验证失败");
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNo(params.get("biz_order_no"));
        String curMonth = PayUtil.getThirdPayMonth(orderEntity.getOrderNo());
        orderEntity.setCurMonth(curMonth);
        orderEntity = orderService.queryThirdOrder(orderEntity);
        if (orderEntity == null) {
            logger.error("钱宝有票支付通知找不到对应订单号:{}", params.get("biz_order_no"));
            return SdkOauthResult.failed();
        }
        orderEntity.setCurMonth(curMonth);
        if (orderEntity.getOrderStatus() == OrderStatusEnum.orderstatus_pay_sucess.code) {
            logger.error("钱宝有票支付通知订单号:{}已支付", params.get("biz_order_no"));
            return SdkOauthResult.success();
        }
        Double chargePointAmount = orderEntity.getChargePointAmount();//库中记录支付金额单位为元，有票支付金额单位为分
        if (Long.valueOf(params.get("total_fee")).compareTo(PriceUtil.convertToFen(chargePointAmount.toString())) != 0) {
            logger.error("钱宝有票支付通知订单号:{}支付金额不一致,冰趣金额:{}分,有票金额:{}分", params.get("biz_order_no"), chargePointAmount.intValue() * 100, params.get("total_fee"));
            orderEntity.setOrderStatus(OrderStatusEnum.orderstatus_pay_faild.code);
            return SdkOauthResult.failed();
        }
        if (!"1".equals(params.get("trade_status"))) {   //有票支付状态,1：支付成功
            logger.error("钱宝有票支付通知订单号:{}支付失败,冰趣金额:{}分,有票金额:{}分", params.get("biz_order_no"), chargePointAmount.intValue() * 100, params.get("total_fee"));
            orderEntity.setOrderStatus(OrderStatusEnum.orderstatus_pay_faild.code);
            return SdkOauthResult.failed();
        }
        orderEntity.setOrderStatus(OrderStatusEnum.orderstatus_pay_sucess.code);
        orderEntity.setPayChannel(params.get("channel"));//支付渠道
        orderEntity.setTradeNo(params.get("pay_trade_no"));//有票订单号
        if (!orderService.updateThirdOrder(orderEntity)) {
            logger.error("钱宝有票支付通知订单号:{}更改订单状态失败", params.get("biz_order_no"));
            return SdkOauthResult.failed();
        }
        //异步通知cp
        executor.executor(new H5GameThridPartyPayNotifyThread(orderEntity, orderService));
        return SdkOauthResult.success();
    }

    /**
     * 处理钱宝支付cp通知
     *
     * @param entity
     * @param response
     */
    @Override
    public void handleQbaoPayNotify(PayTransEntity entity, SdkPayResponse response) {
        try {
            if ("pc".equals(entity.getPayOrigin()) && TransStatusEnum.PAY_TRANS_SUCCESS.getStatus().equals(entity.getStatus())) {
                executor.executor(new WebGameQbaoPayNotifyThread(entity));
            } else if ("app".equals(entity.getPayOrigin()) && TransStatusEnum.PAY_TRANS_SUCCESS.getStatus().equals(entity.getStatus())
                    && CBStatusEnum.RESP_SUCCESS.getCode() != entity.getCallbackStatus()) {
                executor.executor(new MerchantPayNotifyThread(buildNotifyEntity(response, entity)));
            } else if ("wap".equals(entity.getPayOrigin()) && PayResEnum.SUCCESS_CODE.getCode().equals(response.getResponseCode())
                    && CBStatusEnum.RESP_SUCCESS.getCode() != entity.getCallbackStatus()){ //支付成功才通知游戏
                executor.executor(new H5GameQbaoPayNotifyThread(entity, response));
            } else
            if ("NewBusi".equalsIgnoreCase(entity.getPayOrigin()) || "JB_AU_PAY".equalsIgnoreCase(entity.getPayOrigin())) {//新业务侧聚宝盆混合支付异步通知
                amqpTemplate.convertAndSend("jubaopen_pay_key", entity); //异步通知使用rabbitmq
            }
        } catch (Exception e) {
            logger.error("支付异步通知异常，", e);
        }
    }

    /**
     * 处理微信、支付宝支付cp通知
     *
     * @param aliPayConfig
     * @param order
     * @param object
     */
    @Override
    public void handleDomePayNotify(JSONObject aliPayConfig, OrderEntity order, Object object) {
        try {
            if ("app".equalsIgnoreCase(order.getPayOrigin())) {
                executor.executor(new MerchantPayNotifyThread(buildNotifyEntity(order, aliPayConfig)));
            } else if ("pc".equalsIgnoreCase(order.getPayOrigin())) {
                rabbitMqService.webPayNotifyQueue(order);
            } else if ("wap".equalsIgnoreCase(order.getPayOrigin())) {
                if (order.getAppCode().matches("^(D)\\d+")) {//手游支付宝wap支付
                    rabbitMqService.inlandMGamePayQueue(order);
                } else {//默认是H5游戏只有支付宝wap|微信wap支付
                    executor.executor(new H5GameAliWapPayNotifyThread(order, object));
                }
            } else if ("FULU".equalsIgnoreCase(order.getPayOrigin()) ||
                    "JBP".equalsIgnoreCase(order.getPayOrigin()) ||
                    "TBE".equalsIgnoreCase(order.getPayOrigin())||
                    "VR".equalsIgnoreCase(order.getPayOrigin())) {
                executor.executor(new FuLuAliPayNotifyThread(order, object));
            } else if ("bqRatio".equalsIgnoreCase(order.getPayOrigin())) {
                executor.executor(new BqPlatformRatioAliPayNotifyThread(order, object));
            } else if ("rc".equalsIgnoreCase(order.getPayOrigin())) { //游戏充值中心
                rabbitMqService.rechargeCentreQueue(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证支付通知签名
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private boolean validateSign(Map<String, String> params) throws Exception {
        String youPiaoSign = params.get("sign");
        params.remove("buyer_id");
        params.remove("platformCode");
        params.remove("sign");
        String paramSignStr = H5GameUtil.createLinkString(MapUtil.delValParams(params));
        //通讯密钥
        String secKey = domainConfig.getString("youpiao.md5.sign.key");
        secKey = "&md5Key=" + secKey;
        //参数尾部加上通讯密钥进行加密，生成签名。
        String targetSign = MD5.md5Encode(paramSignStr + secKey);
        return youPiaoSign.equals(targetSign);
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

    /**
     * 构建支付成功后异步通知对象
     *
     * @param response
     * @param entity
     * @return
     */
    private DomeRequestEntity buildNotifyEntity(SdkPayResponse response, PayTransEntity entity) {
        DomeRequestEntity requestEntity = new DomeRequestEntity();
        Map<String, String> data = new HashMap<String, String>();
        data.put("orderNo", entity.getBizCode());
        data.put("sdkflowId", String.valueOf(entity.getPayTransId()));
        requestEntity.setResponseCode(response.getResponseCode());
        requestEntity.setErrorCode(response.getErrorCode());
        requestEntity.setErrorMsg(response.getErrorMsg());
        requestEntity.setOrderNo(entity.getBizCode());
        requestEntity.setSdkflowId(String.valueOf(entity.getPayTransId()));
        requestEntity.setPayNotifyUrl(entity.getCallbackUrl());
        requestEntity.setData(data);
        requestEntity.setPrivateKey(payConfig.getString("async.private.key"));
        requestEntity.setMaxTryTimes(asyncNoticeMaxTryTimes);
        requestEntity.setSleepTime(asyncNoticeSleep);
        requestEntity.setPaySources(PaySource2BiEnum.qbaopay.name());
        requestEntity.setAppCode(entity.getAppCode());
        requestEntity.setOrderStatus(Integer.valueOf(entity.getStatus()));
        requestEntity.setAccountFlowId(entity.getAccountFlowId());
        return requestEntity;
    }
}
