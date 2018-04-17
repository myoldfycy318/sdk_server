package com.dome.sdkserver.metadata.entity.statistic;


/**
 * 展示流水列表
 */
public class SdkTrans {

	/** 日期 */
	private String date;
	/** 宝币流水 */
	private Object accountAmount;
	/** 宝劵流水 */
	private Object bqAccountAmount;
	/** 宝币折现 */
	private Object bbTotalAmount;
	/** 宝劵折现 */
	private Object bqTotaAmount;
	/** 折现总额 */
	private Object totalAmount;
	/** 付费用户数 */
	private Object payUserCount;
	/** 付费转化率 */
	private Object payRate;
	/** ARPU */
	private Object ARPU;
	/** ARPPU */
	private Object ARPPU;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Object getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(Object accountAmount) {
		this.accountAmount = accountAmount;
	}

	public Object getBqAccountAmount() {
		return bqAccountAmount;
	}

	public void setBqAccountAmount(Object bqAccountAmount) {
		this.bqAccountAmount = bqAccountAmount;
	}

	public Object getBbTotalAmount() {
		return bbTotalAmount;
	}

	public void setBbTotalAmount(Object bbTotalAmount) {
		this.bbTotalAmount = bbTotalAmount;
	}

	public Object getBqTotaAmount() {
		return bqTotaAmount;
	}

	public void setBqTotaAmount(Object bqTotaAmount) {
		this.bqTotaAmount = bqTotaAmount;
	}

	public Object getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Object totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Object getPayUserCount() {
		return payUserCount;
	}

	public void setPayUserCount(Object payUserCount) {
		this.payUserCount = payUserCount;
	}

	public Object getPayRate() {
		return payRate;
	}

	public void setPayRate(Object payRate) {
		this.payRate = payRate;
	}

	public Object getARPU() {
		return ARPU;
	}

	public void setARPU(Object aRPU) {
		ARPU = aRPU;
	}

	public Object getARPPU() {
		return ARPPU;
	}

	public void setARPPU(Object aRPPU) {
		ARPPU = aRPPU;
	}


}
