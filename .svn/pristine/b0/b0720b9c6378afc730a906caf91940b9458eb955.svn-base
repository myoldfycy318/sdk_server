package com.dome.sdkserver.metadata.entity.bq.pay;

/**
 * WeChatEntify
 *
 * @author Zhang ShanMin
 * @date 2017/11/3
 * @time 13:45
 */
public class WeChatEntity {

    //订单号
    private String outTradeNo;
    //随机字符串
    private String nonceStr;
    //支付终端ip
    private String spBillCreateIp;
    //交易类型
    private String tradeType;
    //商品描述
    private String body;
    //总金额,单位金额:分
    private long totalFee;

    private String openId;
    //微信公众号支付需要使用微信支付分配的公众账号ID
    private String appId;
    //微信支付分配的商户号
    private String mchId;
    private String signKey;

    public WeChatEntity() {
    }

    private WeChatEntity(Builder b) {
        this.outTradeNo = b.outTradeNo;
        this.nonceStr = b.nonceStr;
        this.spBillCreateIp = b.spBillCreateIp;
        this.tradeType = b.tradeType;
        this.body = b.body;
        this.totalFee = b.totalFee;
        this.openId = b.openId;
        this.appId = b.appId;
        this.mchId = b.mchId;
        this.signKey = b.signKey;
    }

    public static class Builder {

        private String outTradeNo;
        private String nonceStr;
        private String spBillCreateIp;
        private String tradeType;
        private String body;
        private long totalFee;
        private String openId;
        private String appId;
        private String mchId;
        private String signKey;

        public Builder() {
        }

        public Builder outTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
            return this;
        }

        public Builder nonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
            return this;
        }

        public Builder spBillCreateIp(String spBillCreateIp) {
            this.spBillCreateIp = spBillCreateIp;
            return this;
        }

        public Builder tradeType(String tradeType) {
            this.tradeType = tradeType;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder totalFee(long totalFee) {
            this.totalFee = totalFee;
            return this;
        }
        public Builder openId(String openId) {
            this.openId = openId;
            return this;
        }

        public Builder appId(String appId){
            this.appId = appId;
            return this;
        }

        public Builder mchId(String mchId){
            this.mchId = mchId;
            return this;
        }

        public Builder signKey(String signKey){
            this.signKey = signKey;
            return this;
        }

        public WeChatEntity build() {
            return new WeChatEntity(this);
        }
    }


    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSpBillCreateIp() {
        return spBillCreateIp;
    }

    public void setSpBillCreateIp(String spBillCreateIp) {
        this.spBillCreateIp = spBillCreateIp;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }
}
