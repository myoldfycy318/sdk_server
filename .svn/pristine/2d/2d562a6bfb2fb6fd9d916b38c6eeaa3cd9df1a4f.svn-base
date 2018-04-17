package com.dome.sdkserver.metadata.entity.bq.pay;

import com.alibaba.fastjson.annotation.JSONField;
import com.dome.sdkserver.bq.constants.PayConstant;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by xuekuan on 2017/4/28.
 */
public class PayRecordEntity {
    private String orderNo;

    private double chargePointAmount;
    private Date createTime;

    private Date finishTime;

    private int orderStatus;
    @NotNull(message = "必须参数为空")
    private String buyerId;
    @JSONField(serialize = false)
    private String curMonth;
    @NotNull(message = "必须参数为空")
    private String channelCode;
    //支付月份
    @JSONField(serialize = false)
    private List<String> orderMonths;
    //应用名称
    private String appName;
    //充值账户
    private String passport;
    //账户类型
    private String passportType;
    //支付来源
//    @JSONField(serialize = false)
    private String payOrigin;

    private Integer payType;

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getChargePointAmount() {
        return chargePointAmount;
    }

    public void setChargePointAmount(double chargePointAmount) {
        this.chargePointAmount = chargePointAmount;
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

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getCurMonth() {
        return curMonth;
    }

    public void setCurMonth(String curMonth) {
        this.curMonth = curMonth;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public List<String> getOrderMonths() {
        return orderMonths;
    }

    public void setOrderMonths(List<String> orderMonths) {
        this.orderMonths = orderMonths;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPassportType() {
        return passportType;
    }

    public void setPassportType(String passportType) {
        this.passportType = passportType;
    }

    public String getPayOrigin() {
        return payOrigin;
    }

    public void setPayOrigin(String payOrigin) {
        this.payOrigin = payOrigin;
        PayConstant.ALI_PAY_ORIGIN aliPayOrigin = PayConstant.ALI_PAY_ORIGIN.getPayOrigin(payOrigin);
        this.passportType = aliPayOrigin == null ? "" : aliPayOrigin.getDesc();
    }
}
