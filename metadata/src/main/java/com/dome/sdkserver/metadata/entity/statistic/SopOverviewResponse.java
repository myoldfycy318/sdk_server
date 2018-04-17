package com.dome.sdkserver.metadata.entity.statistic;

import java.math.BigDecimal;

/**
 * 开放平台--统计概况响应
 * 
 * @author xuefeihu
 *
 */
public class SopOverviewResponse {

	/** 合作伙伴ID */
	private String merchantCode;
	/** 合作伙伴名称 */
	private String merchantName;
	/** 业务ID */
	private String appCode;
	/** 业务名称 */
	private String appName;
	/** 业务类型 */
	private String appType;
	/** 业务来源 */
	private Integer appSource;
	/** 道具代码 */
	private String chargingPointCode;
	/** 道具名称 */
	private String chargingPointName;
	/** 付费用户数 */
	private Integer payUserCount;
	/** 付费次数 */
	private Integer payCount;
	/** 宝币流水 */
	private BigDecimal accountAmount;
	/** 宝券流水 */
	private BigDecimal bqAccountAmount;
	/** 宝币折现 */
	private BigDecimal bbTotalAmount;
	/** 宝券折现 */
	private BigDecimal bqTotaAmount;
	/** 总折现 */
	private BigDecimal totalAmount;
	/** 下载用户数 */
	private int downUserCount;
	/** 下载次数 */
	private int downCount;

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public Integer getAppSource() {
		return appSource;
	}

	public void setAppSource(Integer appSource) {
		this.appSource = appSource;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getChargingPointCode() {
		return chargingPointCode;
	}

	public void setChargingPointCode(String chargingPointCode) {
		this.chargingPointCode = chargingPointCode;
	}

	public String getChargingPointName() {
		return chargingPointName;
	}

	public void setChargingPointName(String chargingPointName) {
		this.chargingPointName = chargingPointName;
	}

	public Integer getPayUserCount() {
		return payUserCount;
	}

	public void setPayUserCount(Integer payUserCount) {
		this.payUserCount = payUserCount;
	}

	public Integer getPayCount() {
		return payCount;
	}

	public void setPayCount(Integer payCount) {
		this.payCount = payCount;
	}

	public BigDecimal getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	}

	public BigDecimal getBqAccountAmount() {
		return bqAccountAmount;
	}

	public void setBqAccountAmount(BigDecimal bqAccountAmount) {
		this.bqAccountAmount = bqAccountAmount;
	}

	public BigDecimal getBbTotalAmount() {
		return bbTotalAmount;
	}

	public void setBbTotalAmount(BigDecimal bbTotalAmount) {
		this.bbTotalAmount = bbTotalAmount;
	}

	public BigDecimal getBqTotaAmount() {
		return bqTotaAmount;
	}

	public void setBqTotaAmount(BigDecimal bqTotaAmount) {
		this.bqTotaAmount = bqTotaAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getDownUserCount() {
		return downUserCount;
	}

	public void setDownUserCount(int downUserCount) {
		this.downUserCount = downUserCount;
	}

	public int getDownCount() {
		return downCount;
	}

	public void setDownCount(int downCount) {
		this.downCount = downCount;
	}

}
