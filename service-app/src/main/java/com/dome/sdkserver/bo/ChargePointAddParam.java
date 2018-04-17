package com.dome.sdkserver.bo;

import java.math.BigDecimal;

/**
 * 添加计费点信息参数
 * @author liuxingyue
 *
 */
public class ChargePointAddParam extends ChargePoint {
	/** 计费点id*/
	private Integer chargePointId;
	/** 应用编码*/
	private String appCode;
	/** 计费点编码*/
	private String chargePointCode;
	/** 计费点名称*/
	private String chargePointName;
	/** 计费点金额*/
	private BigDecimal chargePointAmount;
	/** 计费点状态 :待审核 变更申请 已生效 已驳回 草稿*/
	private Integer status;
	
	public Integer getChargePointId() {
		return chargePointId;
	}
	public void setChargePointId(Integer chargePointId) {
		this.chargePointId = chargePointId;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getChargePointCode() {
		return chargePointCode;
	}
	public void setChargePointCode(String chargePointCode) {
		this.chargePointCode = chargePointCode;
	}
	public String getChargePointName() {
		return chargePointName;
	}
	public void setChargePointName(String chargePointName) {
		this.chargePointName = chargePointName;
	}
	public BigDecimal getChargePointAmount() {
		return chargePointAmount;
	}
	public void setChargePointAmount(BigDecimal chargePointAmount) {
		this.chargePointAmount = chargePointAmount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}	
}
