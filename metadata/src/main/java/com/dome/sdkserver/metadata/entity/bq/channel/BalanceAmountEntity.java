package com.dome.sdkserver.metadata.entity.bq.channel;

import java.io.Serializable;
import java.math.BigDecimal;

public class BalanceAmountEntity implements Serializable {
    /**
     * 统计记录id.
     */
    private Integer id;

    /**
     * 渠道id.
     */
    private Integer channelId;
    
    /**
     * 合作模式
     */
    private int cooperType;

    /**
     * .
     */
    private String channelName;
    
    /**
     * 激活单价
     */
    private BigDecimal activityUnitPrice; 

    /**
     * 应用码.
     */
    private String appCode;

    /**
     * 应用名称.
     */
    private String appName;

    /**
     * 激活用户数量.
     */
    private Integer activateUserCount;

    /**
     * .
     */
    private Integer deductActivateUserCount;

    /**
     * 支付用户数量.
     */
    private Integer payUserCount;

    /**
     * .
     */
    private Integer deductPayUserCount;

    /**
     * 重置总金额.
     */
    private BigDecimal chargeAmount;

    /**
     * .
     */
    private BigDecimal deductChargeAmount;

    /**
     * 结算金额.
     */
    private BigDecimal settleAmount;

    /**
     * .
     */
    private BigDecimal deductSettleAmount;

    /**
     * 统计日期.
     */
    private String date;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
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

    public Integer getActivateUserCount() {
        return activateUserCount;
    }

    public void setActivateUserCount(Integer activateUserCount) {
        this.activateUserCount = activateUserCount;
    }

    public Integer getDeductActivateUserCount() {
        return deductActivateUserCount;
    }

    public void setDeductActivateUserCount(Integer deductActivateUserCount) {
        this.deductActivateUserCount = deductActivateUserCount;
    }

    public Integer getPayUserCount() {
        return payUserCount;
    }

    public void setPayUserCount(Integer payUserCount) {
        this.payUserCount = payUserCount;
    }

    public Integer getDeductPayUserCount() {
        return deductPayUserCount;
    }

    public void setDeductPayUserCount(Integer deductPayUserCount) {
        this.deductPayUserCount = deductPayUserCount;
    }

    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public BigDecimal getDeductChargeAmount() {
		return deductChargeAmount;
	}

	public void setDeductChargeAmount(BigDecimal deductChargeAmount) {
		this.deductChargeAmount = deductChargeAmount;
	}

	public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    public BigDecimal getDeductSettleAmount() {
        return deductSettleAmount;
    }

    public void setDeductSettleAmount(BigDecimal deductSettleAmount) {
        this.deductSettleAmount = deductSettleAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    
    public BigDecimal getActivityUnitPrice()
    {
        return activityUnitPrice;
    }

    public void setActivityUnitPrice(BigDecimal activityUnitPrice)
    {
        this.activityUnitPrice = activityUnitPrice;
    }

	public int getCooperType() {
		return cooperType;
	}

	public void setCooperType(int cooperType) {
		this.cooperType = cooperType;
	}

    
}