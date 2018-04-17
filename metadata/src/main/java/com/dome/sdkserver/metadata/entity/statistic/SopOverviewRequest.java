package com.dome.sdkserver.metadata.entity.statistic;

/**
 * 开放平台--统计概况请求
 * 
 * @author xuefeihu
 *
 */
public class SopOverviewRequest {

	/** 应用编码 */
	private String appCode;
	/** 查询月份 */
	private String month;
	/** 查询日期 */
	private String date;
	/** 页码 */
	private Integer pageNum;
	/** 页面大小 */
	private Integer pageSize;
	/** 查询类型：0 流水、1 下载量 */
	private Integer type;

	private int  childType;
	
	private String merchantCode;
	
	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getChildType() {
		return childType;
	}

	public void setChildType(int childType) {
		this.childType = childType;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

}
