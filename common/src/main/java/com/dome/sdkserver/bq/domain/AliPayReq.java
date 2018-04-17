package com.dome.sdkserver.bq.domain;

/**
 * AliPayReq
 *
 * @author Zhang ShanMin
 * @date 2016/12/17
 * @time 17:08
 */
public class AliPayReq {

    //卖家支付宝用户号,seller_id是以2088开头的纯16位数字
    private String sellerId;
    //接口名称
    private String service;
    //支付类型,只支持取值为1
    private String paymentType;
    //签约的支付宝账号对应的支付宝唯一用户号
    //参数编码字符集
    private String inputCharset;
    //签名方式
    private String signType;
    //签名
    private String sign;
    private String partner;
    //商户网站唯一订单号
    private String outTradeNo;
    //交易金额  该笔订单的资金总额，单位为RMB-Yuan
    private Double totalFee;
    //商品名称
    private String subject;
    //商品描述
    private String body;
    //服务器异步通知页面路径
    private String notifyUrl;
    //页面跳转同步通知页面路径
    private String returnUrl;
    //商品类型：1表示实物类商品;0表示虚拟类商品
    private String goodsType;
    //rsa签名私钥key
    private String rsaPrivateKey;
    //公用回传参数
    private String extraCommonParam;

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getExtraCommonParam() {
        return extraCommonParam;
    }

    public void setExtraCommonParam(String extraCommonParam) {
        this.extraCommonParam = extraCommonParam;
    }
}
