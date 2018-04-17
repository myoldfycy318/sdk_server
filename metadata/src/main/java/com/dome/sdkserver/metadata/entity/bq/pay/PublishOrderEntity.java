package com.dome.sdkserver.metadata.entity.bq.pay;

import java.util.Date;

public class PublishOrderEntity {
	private String orderNo;
	
	private String appCode;
	
	private String appName;
	
	private String buyerId;
	
	private String chargePointCode;
	
	private Double chargePointAmount;

	private String chargePointName;
	
	private int payType;
	
	private Date createTime;
	
	private Date finishTime;
	
	private int orderStatus;
	
	private String tradeNo;
	
	private String gameOrderNo;
	
	private String payNotifyUrl;
	
	private String channelCode;

    private String payOrigin;
    //扩展字段
    private String extraField;

    private String buyerAccount;

    private String authCode;

    private String paymentType;

    private String myCardTradeNo;

    private String currency;
    
    private String tradeType;

    private String curMonth;

    //签名code
    private String signCode;

    //游戏角色id
    private String roleId;
    //游戏区服Id
    private String zoneId;
    //苹果支付票根
    private String receiptData;
    //谷歌支付通知数据
    private String purchaseData;
    //谷歌支付通知签名
    private String signature;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getChargePointCode() {
        return chargePointCode;
    }

    public void setChargePointCode(String chargePointCode) {
        this.chargePointCode = chargePointCode;
    }

    public Double getChargePointAmount() {
        return chargePointAmount;
    }

    public void setChargePointAmount(Double chargePointAmount) {
        this.chargePointAmount = chargePointAmount;
    }

    public String getChargePointName() {
        return chargePointName;
    }

    public void setChargePointName(String chargePointName) {
        this.chargePointName = chargePointName;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getGameOrderNo() {
        return gameOrderNo;
    }

    public void setGameOrderNo(String gameOrderNo) {
        this.gameOrderNo = gameOrderNo;
    }

    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getPayOrigin() {
        return payOrigin;
    }

    public void setPayOrigin(String payOrigin) {
        this.payOrigin = payOrigin;
    }

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getMyCardTradeNo() {
        return myCardTradeNo;
    }

    public void setMyCardTradeNo(String myCardTradeNo) {
        this.myCardTradeNo = myCardTradeNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurMonth() {
        return curMonth;
    }

    public void setCurMonth(String curMonth) {
        this.curMonth = curMonth;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getReceiptData() {
        return receiptData;
    }

    public void setReceiptData(String receiptData) {
        this.receiptData = receiptData;
    }

    public String getPurchaseData() {
        return purchaseData;
    }

    public void setPurchaseData(String purchaseData) {
        this.purchaseData = purchaseData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
