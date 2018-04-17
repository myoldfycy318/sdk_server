package com.dome.sdkserver.bo;

import java.math.BigDecimal;

/**
 * 计费点查询参数
 * @author liuxingyue
 *
 */
public class SearchChargePointBo {
	/** 应用编码*/
	private String appCode;
	/** 计费点起始金额*/
	private BigDecimal amountStart;
	/** 计费点结束金额*/
	private BigDecimal amountEnd;
	/** 计费点名称*/
	private String chargePointName;
	/** 计费点状态 :待审核 变更申请 已生效 已驳回 草稿*/
	private Integer status;
	/** 创建开始时间*/
	private String startTime;
	/** 创建结束时间*/
	private String endTime;
	/** 每页数据量 */
	private Integer size;
	/** 开始查询点 */
	private Integer start;
	/** 标记：1 后台  0 前台*/
	private String flag;

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public SearchChargePointBo() {
		super();
	}
	public String getChargePointName() {
		return chargePointName;
	}

	public void setChargePointName(String chargePointName) {
		this.chargePointName = chargePointName;
	}

	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	
	public BigDecimal getAmountStart() {
		return amountStart;
	}

	public void setAmountStart(BigDecimal amountStart) {
		this.amountStart = amountStart;
	}

	public BigDecimal getAmountEnd() {
		return amountEnd;
	}

	public void setAmountEnd(BigDecimal amountEnd) {
		this.amountEnd = amountEnd;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
