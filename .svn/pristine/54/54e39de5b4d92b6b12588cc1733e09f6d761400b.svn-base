package com.dome.sdkserver.biz.impl.pay;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.OrderStatusEnum;
import com.dome.sdkserver.biz.enums.PayTypeEnum;
import com.dome.sdkserver.biz.pay.PayNotifyBiz;
import com.dome.sdkserver.biz.utils.WechatPayUtil;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信支付 回调通知
 * Created by ym on 2017/8/22.
 */
@Component("weiXinPayNotifyBiz")
public class WeiXinPayNotifyBizImpl extends PayNotifyBiz {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String SUCCESS_NOTIFY = "SUCCESS";

    private static final String FAIL_NOTIFY = "fail";

    @Override
    public String payBizProcess(Map<String, String[]> params, JSONObject payConfig) {
        return null;
    }

    @Override
    public SdkOauthResult aliPcSyncNotifyProcess(Map<String, String[]> params, JSONObject payConfig) {
        return null;
    }

    @Override
    public String payBizProcess2(Map<String, String> params, JSONObject payConfig) {

        if (!"SUCCESS".equals(params.get("result_code"))) {
            log.error("微信支付失败,result_code:{},return_msg:{}", params.get("result_code"), params.get("return_msg"));
            return WechatPayUtil.setXml(FAIL_NOTIFY, "微信返回的交易状态不正确,result_code:{" + params.get("result_code") + ",return_msg:{" + params.get("return_msg") + "}");
        }
        //根据微信商户id获取微信支付签名key
        String mchApiKey = "wx.mch.apikey." + params.get("mch_id");
        if (StringUtils.isBlank(domainConfig.getString(mchApiKey))) {
            log.error("根据微信商户id:{},获取微信支付签名key为空", mchApiKey);
            return WechatPayUtil.setXml(FAIL_NOTIFY, "微信支付回调地址请求参数签名验证失败");
        }
        if (!WechatPayUtil.verifyNotifySign(params, domainConfig.getString(mchApiKey))) {
            log.error("微信支付回调地址请求参数签名验证失败");
            return WechatPayUtil.setXml(FAIL_NOTIFY, "微信支付回调地址请求参数签名验证失败");
        }
        String out_trade_no = params.get("out_trade_no");//商户订单号
        int total_fee = Integer.parseInt(params.get("total_fee"));// 获取订单金额
        String transaction_id = params.get("transaction_id");//微信支付订单号

        log.info("微信支付成功= 订单号:{},交易号:{}", out_trade_no, transaction_id);
        String curMonth = PayUtil.getPayMonth(out_trade_no);
        OrderEntity order = orderService.queryOrderByOrderNo(out_trade_no, curMonth);
        if (order == null) {
            log.error("无效的订单号,orderNo:{}", out_trade_no);
            return WechatPayUtil.setXml(FAIL_NOTIFY, "无效的订单号,orderNo:{" + out_trade_no + "}");
        }
        int amount = (int) (order.getChargePointAmount() * 100);
        if (amount != total_fee) {
            log.error("订单金额不一致,total_fee = {} ,amount ={},out_trade_no={}", total_fee, amount, out_trade_no);
            return WechatPayUtil.setXml(FAIL_NOTIFY, "订单金额不一致,totalFee = " + total_fee + "amount = " + amount + "out_trade_no =" + out_trade_no);
        }
        log.info("订单状态,orderNo:{},status:{}", order.getOrderNo(), order.getOrderStatus());
        if (order.getOrderStatus() == OrderStatusEnum.orderstatus_pay_sucess.code) {
            log.error("订单已支付,订单号是:{}", out_trade_no);
            return WechatPayUtil.setXml(SUCCESS_NOTIFY, "OK");
        }
        order.setTradeNo(transaction_id);
        order.setOrderStatus(OrderStatusEnum.orderstatus_pay_sucess.code);
        order.setBuyerAccount(params.get("openid"));////买家账户
        order.setPayType(PayTypeEnum.微信支付.getCode());
        boolean updateOrderResult = orderService.updateOrder(order, curMonth);
        if (!updateOrderResult) {
            order.setOrderStatus(OrderStatusEnum.orderstatus_pay_faild.code);
            return WechatPayUtil.setXml(FAIL_NOTIFY, "订单支付失败,订单号是:{" + out_trade_no + "}");
        }
        //支付成功异步通知合作方
        payNotifyService.handleDomePayNotify(payConfig,order,orderService);
        return WechatPayUtil.setXml(SUCCESS_NOTIFY, "OK");
    }


}
