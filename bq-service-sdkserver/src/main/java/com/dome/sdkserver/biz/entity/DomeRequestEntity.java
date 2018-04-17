package com.dome.sdkserver.biz.entity;

import com.dome.sdkserver.util.RSACoder;
import org.apache.commons.lang3.StringUtils;

public class DomeRequestEntity {
    private String responseCode;

    private String errorCode = "";

    private String errorMsg = "";

    private Object data;
    //合作方订单Id
    private String orderNo;
    //冰穹支付Id
    private String sdkflowId;

    private String signCode;

    private String payNotifyUrl;

    private String privateKey;

    private Integer maxTryTimes;

    private Integer sleepTime;

    //支付数据来源,domepay|qbaopay|thirdpay,用于手游异步通知区服支付类型
    private String paySources;

    private String appCode;

    private int orderStatus;
    private String accountFlowId;

    public String getAccountFlowId() {
        return accountFlowId;
    }

    public void setAccountFlowId(String accountFlowId) {
        this.accountFlowId = accountFlowId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getPaySources() {
        return paySources;
    }

    public void setPaySources(String paySources) {
        this.paySources = paySources;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getSignCode() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("responseCode=").append(getResponseCode()).append(",")
                .append("errorCode=").append(StringUtils.isBlank(getErrorCode()) ? "\"\"" : getErrorCode()).append(",")
                .append("sdkflowId=").append(getSdkflowId()).append(",")
                .append("orderNo=").append(getOrderNo());
        signCode = RSACoder.sign(sb.toString().getBytes(), getPrivateKey());

        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSdkflowId() {
        return sdkflowId;
    }

    public void setSdkflowId(String sdkflowId) {
        this.sdkflowId = sdkflowId;
    }

    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }

    public Integer getMaxTryTimes() {
        return maxTryTimes;
    }

    public void setMaxTryTimes(Integer maxTryTimes) {
        this.maxTryTimes = maxTryTimes;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }
}
