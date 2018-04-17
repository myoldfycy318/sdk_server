/**
 * 
 */
package com.dome.sdkserver.service.pay.qbao.bo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mazhongmin
 * 宝券系统扣减返回值
 */
public class QuanResultData {
	
	/** 业务摘要**/
	private String bizDesc;
	
	/** 业务类型**/
	private String bizType;
	
	/** 操作宝券数量**/
	private BigDecimal couponAmount;
	
	/** 流水创建时间**/
	private Date createTime;
	
	/** 流水id**/
	private String id;
	
	/** 支付类型**/
	private String payType;
	
	/** 流水当前状态**/
	private String state;

	public String getBizDesc() {
		return bizDesc;
	}

	public void setBizDesc(String bizDesc) {
		this.bizDesc = bizDesc;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
