package com.dome.sdkserver.shangsu;

/**
 * TradeResponse
 *
 * @author Zhang ShanMin
 * @date 2016/5/3
 * @time 17:28
 */
public class TradeResponse {
    //返回编码
    private String respCode;
    //返回信息
    private String respMsg;
    //集团账号
    private String groupCode;
    //买家ID
    private String sellerId;
    //买家ID
    private String buyerId;
    //外部流水号
    private String outTradeNo;
    //交易请求时间
    private String tradeTime;
    //交易总金额
    private Long payTotalAmount;
     //人民币金额
    private Long rmbAmount;
     //宝券金额
    private Long bqAmount;
    //商肃支付系统返回的交易流水号
    private String payTradeNo;
    //交易完成时间
    private String payTradeTime;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPayTradeNo() {
        return payTradeNo;
    }

    public void setPayTradeNo(String payTradeNo) {
        this.payTradeNo = payTradeNo;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Long getPayTotalAmount() {
        return payTotalAmount;
    }

    public void setPayTotalAmount(Long payTotalAmount) {
        this.payTotalAmount = payTotalAmount;
    }

    public Long getRmbAmount() {
        return rmbAmount;
    }

    public void setRmbAmount(Long rmbAmount) {
        this.rmbAmount = rmbAmount;
    }

    public Long getBqAmount() {
        return bqAmount;
    }

    public void setBqAmount(Long bqAmount) {
        this.bqAmount = bqAmount;
    }

    public String getPayTradeTime() {
        return payTradeTime;
    }

    public void setPayTradeTime(String payTradeTime) {
        this.payTradeTime = payTradeTime;
    }
}
