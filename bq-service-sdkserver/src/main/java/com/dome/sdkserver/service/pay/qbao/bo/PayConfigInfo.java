/**
 * 
 */
package com.dome.sdkserver.service.pay.qbao.bo;

import java.math.BigDecimal;

/**
 * @author mazhongmin
 *  支付配置信息
 */
public class PayConfigInfo {
	
	/** 配置id*/
	private long configId;
	
	/** 应用编码*/
	private String appCode;
	
	/** 宝币状态：1开启 0关闭*/
	private int qbbFlag;
	
	/** 宝券状态：1开启 0关闭*/
	private int bqFlag;
	
	/** 删除标识：0 未删除 1 已删除*/
	private int delFlag;
	
	/** 是否是认证商家：1 是  0否*/
	private int certifiedStatus;
	
	/** 结算周期：1:天 2:月 3:季 4:年*/
	private int settlePeriod;
	
	/** 宝币兑换比率*/
	private BigDecimal qbbRate;
	
	/** 宝券兑换比率*/
	private BigDecimal bqRate;
	
	/** 生效日期*/
	private String effectDate;
	
	/** 备注*/
	private String remark;
	
	public long getConfigId() {
		return configId;
	}

	public void setConfigId(long configId) {
		this.configId = configId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public int getQbbFlag() {
		return qbbFlag;
	}

	public void setQbbFlag(int qbbFlag) {
		this.qbbFlag = qbbFlag;
	}

	public int getBqFlag() {
		return bqFlag;
	}

	public void setBqFlag(int bqFlag) {
		this.bqFlag = bqFlag;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getCertifiedStatus() {
		return certifiedStatus;
	}

	public void setCertifiedStatus(int certifiedStatus) {
		this.certifiedStatus = certifiedStatus;
	}

	public int getSettlePeriod() {
		return settlePeriod;
	}

	public void setSettlePeriod(int settlePeriod) {
		this.settlePeriod = settlePeriod;
	}

	public BigDecimal getQbbRate() {
		return qbbRate;
	}

	public void setQbbRate(BigDecimal qbbRate) {
		this.qbbRate = qbbRate;
	}

	public BigDecimal getBqRate() {
		return bqRate;
	}

	public void setBqRate(BigDecimal bqRate) {
		this.bqRate = bqRate;
	}

	public String getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
