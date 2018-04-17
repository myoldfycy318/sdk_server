package com.dome.sdkserver.service.impl.pay;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.PayTypeEnum;
import com.dome.sdkserver.biz.utils.WechatPayUtil;
import com.dome.sdkserver.biz.utils.alipay.AliPayUtil;
import com.dome.sdkserver.bq.domain.AliPayReq;
import com.dome.sdkserver.bq.util.GenOrderCode;
import com.dome.sdkserver.bq.util.PriceUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.WeChatEntity;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.service.pay.PayService;
import com.dome.sdkserver.service.web.requestEntity.HttpRequestOrderInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;


/**
 * AliPayServiceImpl
 *
 * @author Zhang ShanMin
 * @date 2016/12/17
 * @time 14:49
 */
@Service("payService")
public class PayServiceImpl extends PayOrderManager implements PayService {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @param requestOrderInfo
     * @return
     */
    @Override
    public SdkOauthResult createOrder(HttpRequestOrderInfo requestOrderInfo) throws Exception {
        //处理支付请求
        SdkOauthResult result = handlePayReq(requestOrderInfo);
        if (!result.isSuccess()) return result;
        //转化订单入库参数
        OrderEntity orderEntity = convertOrderEntity(requestOrderInfo);
        orderService.createOrder(orderEntity);
        return result;
    }

    /**
     * 创建预支付订单
     *
     * @param requestOrderInfo
     * @return
     * @throws Exception
     */
    @Override
    public void createPreOrder(HttpRequestOrderInfo requestOrderInfo) throws Exception {
        preHandleReqOrder(requestOrderInfo);
        //转化订单入库参数
        OrderEntity orderEntity = convertOrderEntity(requestOrderInfo);
        orderService.createOrder(orderEntity);
    }

    /**
     * 预处理请求参数
     *
     * @param requestOrderInfo
     */
    protected void preHandleReqOrder(HttpRequestOrderInfo requestOrderInfo) {
        if (StringUtils.isBlank(requestOrderInfo.getChargePointCode())) {
            requestOrderInfo.setChargePointCode("C0000000");//默认
        }
        if (StringUtils.isBlank(requestOrderInfo.getAppCode())) {
            requestOrderInfo.setAppCode(BqSdkConstants.DEF_VAL);
        }
        if (StringUtils.isBlank(requestOrderInfo.getChannelCode())) {
            requestOrderInfo.setChannelCode("00");
        }
        requestOrderInfo.setOrderNo(GenOrderCode.next());
    }

    /**
     * 构建支付宝支付所有的请求参数
     *
     * @param requestOrderInfo
     * @return
     */
    @Override
    protected AliPayReq buildAliPayReq(HttpRequestOrderInfo requestOrderInfo, Map<String, String> payConfig) {
        AliPayReq aliPayReq = new AliPayReq();
        aliPayReq.setPartner(payConfig.get(BqSdkConstants.aliPayPartner));
        aliPayReq.setSellerId(payConfig.get(BqSdkConstants.aliPayPartner));
        aliPayReq.setInputCharset(payConfig.get(BqSdkConstants.aliPayCharset));
        aliPayReq.setPaymentType("1");//支付类型,只支持取值为1
        aliPayReq.setOutTradeNo(requestOrderInfo.getOrderNo());
        aliPayReq.setSubject(requestOrderInfo.getSubject());
        //该笔订单的资金总额，单位为RMB-Yuan
        aliPayReq.setTotalFee(PriceUtil.convert2YuanD(new BigDecimal(requestOrderInfo.getTotalFee())).doubleValue());
        aliPayReq.setBody(requestOrderInfo.getBody());
        aliPayReq.setSignType("RSA");
        aliPayReq.setRsaPrivateKey(payConfig.get(BqSdkConstants.sellerPrivateKey));
        aliPayReq.setReturnUrl(requestOrderInfo.getReturnUrl());
        aliPayReq.setNotifyUrl(payConfig.get(BqSdkConstants.aliPayNotifyUrl));
        //公用回传参数,默认返回外部调用sdk即时到帐支付订单号
        aliPayReq.setExtraCommonParam(StringUtils.isBlank(requestOrderInfo.getExtraCommonParam()) ? requestOrderInfo.getOutOrderNo() : requestOrderInfo.getExtraCommonParam());
        return aliPayReq;
    }


    /**
     * 构建支付宝请求数据
     *
     * @param orderEntity
     * @param payConfig
     * @return
     */
    protected AliPayReq buildAliPayReq(OrderEntity orderEntity, Map<String, String> payConfig) {
        JSONObject extData = null;
        if (StringUtils.isNotBlank(orderEntity.getExtraField()))
            extData = JSONObject.parseObject(orderEntity.getExtraField());
        AliPayReq aliPayReq = new AliPayReq();
        aliPayReq.setPartner(payConfig.get(BqSdkConstants.aliPayPartner));
        aliPayReq.setSellerId(payConfig.get(BqSdkConstants.aliPayPartner));
        aliPayReq.setInputCharset(payConfig.get(BqSdkConstants.aliPayCharset));
        aliPayReq.setPaymentType("1");//支付类型,只支持取值为1
        aliPayReq.setOutTradeNo(orderEntity.getOrderNo());
        aliPayReq.setSubject(orderEntity.getAppName());
        //该笔订单的资金总额，单位为RMB-Yuan
        aliPayReq.setTotalFee(orderEntity.getChargePointAmount());
        aliPayReq.setBody(orderEntity.getChargePointName());
        aliPayReq.setSignType("RSA");
        aliPayReq.setRsaPrivateKey(payConfig.get(BqSdkConstants.sellerPrivateKey));
        if (extData != null && StringUtils.isNotBlank(extData.getString("returnUrl")))
            aliPayReq.setReturnUrl(extData.getString("returnUrl"));
        aliPayReq.setNotifyUrl(payConfig.get(BqSdkConstants.aliPayNotifyUrl));
        //公用回传参数,默认返回外部调用sdk即时到帐支付订单号
        aliPayReq.setExtraCommonParam((extData == null || StringUtils.isBlank(extData.getString("extraCommonParam"))) ? orderEntity.getGameOrderNo() : extData.getString("extraCommonParam"));
        return aliPayReq;
    }

    /**
     * 构建支付请求数据
     */
    @Override
    public SdkOauthResult buildPayReqData(OrderEntity orderEntity, Map<String, String> payData) throws Exception {
        if (orderEntity.getPayType() == PayTypeEnum.支付宝支付.getCode()) {
            return SdkOauthResult.success(AliPayUtil.createPayDownInfo(buildAliPayReq(orderEntity, payData)));
        } else if (orderEntity.getPayType() == PayTypeEnum.微信支付.getCode()) {
            Double totalfee = orderEntity.getChargePointAmount() * 100;
            WeChatEntity weChatEntity = new WeChatEntity.Builder().nonceStr(WechatPayUtil.generateNonceStr()).spBillCreateIp(orderEntity.getIp()).outTradeNo(orderEntity.getOrderNo()).tradeType("NATIVE").body(orderEntity.getAppName()).totalFee(totalfee.longValue()).build();
            String code_url = WechatPayUtil.weixinPayUnifiedorder(weChatEntity, payData);
            return SdkOauthResult.success(code_url);
        }
        return SdkOauthResult.failed("未知支付类型");
    }
}
