package com.dome.sdkserver.biz.impl.pay;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.entity.DomeRequestEntity;
import com.dome.sdkserver.biz.enums.OrderStatusEnum;
import com.dome.sdkserver.biz.enums.PayTypeEnum;
import com.dome.sdkserver.biz.executor.Executor;
import com.dome.sdkserver.biz.pay.PayNotifyBiz;
import com.dome.sdkserver.biz.utils.alipay.AlipayNotifyUtils;
import com.dome.sdkserver.bq.enumeration.H5Game2PlatformEnum;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.metadata.entity.bq.turntable.TurntableSendTimesVO;
import com.dome.sdkserver.service.BqSdkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component("aliPayNotifyBiz")
public class AlipayNotifyBizImpl extends PayNotifyBiz {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private static Logger alipayLog = LoggerFactory.getLogger("aLiPay");
    @Resource(name = "executor")
    protected Executor executor;
    @Autowired
    private AmqpTemplate amqpTemplate;
    private static final String ALI_SUCCESS_NOTIFY = "success";
    private static final String ALI_FAIL_NOTIFY = "fail";

    @Override
    public String payBizProcess(Map<String, String[]> requestParams, JSONObject aliPayConfig) {
        Map<String, String> params = new HashMap<String, String>();
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
        String out_trade_no = params.get("out_trade_no");
        //支付宝交易号
        String trade_no = params.get("trade_no");

        //交易状态
        String trade_status = params.get("trade_status");

        String sellerId = params.get("seller_id");

        String totalFee = params.get("total_fee");
        String partner = aliPayConfig.getString(BqSdkConstants.aliPayPartner);
        if (AlipayNotifyUtils.verify(params, aliPayConfig)) {
            if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                log.info(">>>>>>>>>支付宝付款成功,进行下一步业务处理");
                if (!sellerId.equals(partner)) {
                    log.error("卖家ID不一致,sellerId = " + sellerId + "partner = " + partner);
                    return ALI_FAIL_NOTIFY;
                }
                String curMonth = PayUtil.getPayMonth(out_trade_no);
                OrderEntity order = orderService.queryOrderByOrderNo(out_trade_no, curMonth);
                if (order == null) {
                    log.error("无效的订单号 orderNo = " + out_trade_no);
                    return ALI_FAIL_NOTIFY;
                }
                double amount = order.getChargePointAmount();
                if (order.getChargePointAmount() != Double.parseDouble(totalFee)) {
                    log.error("订单金额不一致,totalFee = " + totalFee + "amount = " + amount + "out_trade_no =" + out_trade_no);
                    return ALI_FAIL_NOTIFY;
                }
                log.info("订单状态,orderNo:{},status:{}", order.getOrderNo(), order.getOrderStatus());
                if (order.getOrderStatus() == OrderStatusEnum.orderstatus_pay_sucess.code) {
                    return ALI_SUCCESS_NOTIFY;
                }
                order.setTradeNo(trade_no);
                order.setPayType(PayTypeEnum.支付宝支付.getCode());
                order.setOrderStatus(OrderStatusEnum.orderstatus_pay_sucess.code);
                order.setBuyerAccount(params.get("buyer_email"));
                boolean result = orderService.updateOrder(order, curMonth);
                if (!result) {
                    order.setOrderStatus(OrderStatusEnum.orderstatus_pay_faild.code);
                }
                //支付成功异步通知合作方
                payNotifyService.handleDomePayNotify(aliPayConfig, order, orderService);
                //冰趣大转盘活动,支付成功发mq增加次数
                //handleTurntableLotteryTimes(order); 冰趣大转盘活动已结束 2017-6-6
                return ALI_SUCCESS_NOTIFY;
            } else if (trade_status.equals("WAIT_BUYER_PAY")) {
                return ALI_SUCCESS_NOTIFY;
            }
        }
        return ALI_FAIL_NOTIFY;
    }


    @Override
    public String payBizProcess2(Map<String, String> params, JSONObject payConfig) {
        return null;
    }

    /**
     * 支付宝支付同步通知
     *
     * @param requestParams
     * @param aliPayConfig
     * @return
     */
    @Override
    public SdkOauthResult aliPcSyncNotifyProcess(Map<String, String[]> requestParams, JSONObject aliPayConfig) {
        SdkOauthResult result = null;
        Map<String, String> map = new HashMap<String, String>(1);
        try {
            Map<String, String> params = getAliNotifyParams(requestParams);
            String out_trade_no = params.get("out_trade_no");
            String trade_no = params.get("trade_no"); //交易状态
            String trade_status = params.get("trade_status");
            String is_success = params.get("is_success");
            String partner = aliPayConfig.getString(BqSdkConstants.aliPayPartner);
            if ("T".equals(is_success) && (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS"))) {
                String curMonth = PayUtil.getPayMonth(out_trade_no);
                OrderEntity order = orderService.queryOrderByOrderNo(out_trade_no, curMonth);//获取订单
                map.put("channelCode", order.getChannelCode());
                result = validateNotifyParams(params, partner, order);
                if (!result.isSuccess())//校验通知参数
                    return result;
                if (AlipayNotifyUtils.verify(params, aliPayConfig)) {
                    log.info("订单状态,orderNo:{},status:{}", order.getOrderNo(), order.getOrderStatus());
                    if (order.getOrderStatus() == OrderStatusEnum.orderstatus_pay_sucess.code) {
                        map.put("isPay", "true");
                        handlePayRet(map, order);//处理支付返回
                        return SdkOauthResult.success(map);
                    }
                    order.setTradeNo(trade_no);
                    order.setOrderStatus(OrderStatusEnum.orderstatus_pay_sucess.code);
                    if (!orderService.updateOrder(order, curMonth)) {
                        map.put("isPay", "false");
                        handlePayRet(map, order);//处理支付返回
                        return SdkOauthResult.success(map);
                    }
                    //支付成功异步通知合作方
                    payNotifyService.handleDomePayNotify(aliPayConfig, order, orderService);
                    map.put("isPay", "true");
                    handlePayRet(map, order);//处理支付返回
                    return SdkOauthResult.success(map);
                }
                JSONObject jsonObject = null;
                //异步通知早于同步通知，则支付宝notify_id就会失效，则需验证该订单是否已支付
                if ((jsonObject = orderService.isOrderPaid(out_trade_no)) != null && jsonObject.getBoolean("isPay")) {
                    map.put("isPay", "true");
                    handlePayRet(map, order);//处理支付返回
                    return SdkOauthResult.success(map);
                }
            }
        } catch (Exception e) {
            log.error("支付宝pc同步通知error:", e);
            return SdkOauthResult.failed("系统异常");
        }
        return SdkOauthResult.failed("系统异常");
    }

    /**
     * 支付宝同步通知返回值处理
     *
     * @param map
     * @param order
     */
    private void handlePayRet(Map<String, String> map, OrderEntity order) {
        map.put("appCode", order.getAppCode());
        //H5游戏wap支付且渠道是钱宝，userid给钱宝userId
        String userId = (H5Game2PlatformEnum.isQbaoChannel(order.getChannelCode())&& "wap".equalsIgnoreCase(order.getPayOrigin()))
                ? order.getBwUserId() : order.getBuyerId();
        map.put("userId", userId);
        JSONObject jsonObject = JSONObject.parseObject(order.getExtraField());
        //H5游戏支付宝支付同步通知添加游戏平台
        map.put("platformCode", order.getChannelCode());
    }


    /**
     * 获取支付宝请求参数
     *
     * @param requestParams
     * @return
     */
    private Map<String, String> getAliNotifyParams(Map<String, String[]> requestParams) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }


    class AsyncNoticeThread extends Thread {

        private OrderEntity order;
        private JSONObject payConfig;

        AsyncNoticeThread(OrderEntity order, JSONObject payConfig) {
            this.order = order;
            this.payConfig = payConfig;
        }

        public void run() {
            Map<String, String> data = new HashMap<String, String>();
            data.put("sdkflowId", order.getOrderNo());
            data.put("orderNo", order.getGameOrderNo());

            DomeRequestEntity requestEntity = new DomeRequestEntity();
            requestEntity.setResponseCode("1000");
            requestEntity.setErrorCode("");
            requestEntity.setErrorMsg("");
            requestEntity.setOrderNo(order.getGameOrderNo());
            requestEntity.setSdkflowId(order.getOrderNo());
            requestEntity.setPayNotifyUrl(order.getPayNotifyUrl());
            requestEntity.setData(data);
            requestEntity.setPrivateKey(payConfig.getString(BqSdkConstants.asyncPrivateKey));

            boolean result = asyncNotice(requestEntity, payConfig.getInteger(BqSdkConstants.asyncNoticeMaxTryTimes), payConfig.getInteger(BqSdkConstants.asyncNoticeThreadSleep));
            log.info(">>>>>>>>>>>>>>>发送异步通知给游戏服务端结果:" + result + " orderNo = " + order.getOrderNo());
        }

    }

    //冰趣大转盘活动,支付成功发mq增加次数
    private void handleTurntableLotteryTimes(OrderEntity order) {
        if (BqSdkConstants.bqChannelCodes.contains(order.getChannelCode())) {
            TurntableSendTimesVO vo = new TurntableSendTimesVO();
            vo.setType("pay");
            vo.setUserId(order.getBuyerId());
            vo.setMoney(String.valueOf(Double.valueOf(order.getChargePointAmount()).intValue()));
            try {
                amqpTemplate.convertAndSend("turntable_get_lottery_times_queue_key", vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        AlipayNotifyBizImpl ali = new AlipayNotifyBizImpl();
        ali.new AsyncNoticeThread(new OrderEntity(), new JSONObject()).start();
        OrderEntity order = new OrderEntity();
    }

}
