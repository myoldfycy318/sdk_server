package com.dome.sdkserver.bo;

import java.math.BigDecimal;

/**
 * 结算明细对象
 * 
 * @author Frank.Zhou
 *
 */
public class SettleDetail {

	/*商户编码*/
	private String merchantCode;
	
	/*商户名称*/
	private String merchantName;
	
	/*应用编码*/
	private String appCode;
	
	/*应用名称*/
	private String appName;
	
	/*应用平台*/
	private String appSource;
	
	/*交易时间*/
	private String transTime;
	
	/*应用金额*/
	private BigDecimal transAmount;
	
	/*状态*/
	private String status;
	
	/*返回信息*/
	private String responeMsg;
	
	/*钱宝流水号*/
	private String qbTransNum;
	
	/*商户流水号*/
	private String merchantTransNum;
	
	/*宝币金额*/
	private BigDecimal bbAmount;
	
	/*宝券金额*/
	private BigDecimal bqAmount;

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

	public String getAppSource() {
		return appSource;
	}

	public void setAppSource(String appSource) {
		this.appSource = appSource;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResponeMsg() {
		return responeMsg;
	}

	public void setResponeMsg(String responeMsg) {
		this.responeMsg = responeMsg;
	}

	public String getQbTransNum() {
		return qbTransNum;
	}

	public void setQbTransNum(String qbTransNum) {
		this.qbTransNum = qbTransNum;
	}

	public String getMerchantTransNum() {
		return merchantTransNum;
	}

	public void setMerchantTransNum(String merchantTransNum) {
		this.merchantTransNum = merchantTransNum;
	}

	public BigDecimal getBbAmount() {
		return bbAmount;
	}

	public void setBbAmount(BigDecimal bbAmount) {
		this.bbAmount = bbAmount;
	}

	public BigDecimal getBqAmount() {
		return bqAmount;
	}

	public void setBqAmount(BigDecimal bqAmount) {
		this.bqAmount = bqAmount;
	}
	
}
