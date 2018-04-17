package com.dome.sdkserver.shangsu;

import java.util.List;

/**
 * 商肃支付引擎个人类支付请求
 * TradeReqInfo
 *
 * @author Zhang ShanMin
 * @date 2016/5/23
 * @time 16:52
 */
public class PersonTradeReqInfo {

    //签名方式
    private String signType = "RSA";
    //签名信息
    private String signCode;
    //编码类型
    private String inputCharset = "UTF-8";
    //集团账号
    private String groupCode;
    //用户ID
    private String userId;
    //业务类型 商肃公司分配
    private String busiType;
    //交易请求时间 yyyyMMddHHmmss
    private String tradeTime;
    //异步通知URL(若需要异步通知的，该字段必填)
    private String notifyUrl;
    //外部流水号 业务请求流水号（确保在各自业务系统中唯一）
    private String outTradeNo;
    //手续费 人民币，单位：分
    private Long feeAmount;
    //交易描述 1、对一笔交易的具体描述信息。2、该参数需要做Base64(字符集UTF-8)转码。
    private String tradeDesc;
    //终端  1：PC，2：IOS，3：Android (选填)
    private String terminal;
    //行项Json数据 交易行项数据，包含支付方式、金额、行项业务类型
    private List<TradeItem> tradeItems;

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public String getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Long getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Long feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getTradeDesc() {
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public List<TradeItem> getTradeItems() {
        return tradeItems;
    }

    public void setTradeItems(List<TradeItem> tradeItems) {
        this.tradeItems = tradeItems;
    }
}
