package com.dome.sdkserver.bo;

/**
 * 结算查询参数
 * 
 * @author Frank.Zhou
 *
 */
public class OpenQueyParam {

	/*公司名称*/
	private String companyName;
	
	/*商户ID*/
	private String merchantCode;
	
	/*结算状态*/
	private String settleStatus;
	
	/*开始日期*/
	private String startDate;
	
	/*结束日期*/
	private String endDate;
	
	/*当前页数*/
	private Integer currentPage;
	
	/*每页记录数*/
	private Integer pageSize;
	
	/*结算月份*/
	private String settleMonth;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSettleMonth() {
		return settleMonth;
	}

	public void setSettleMonth(String settleMonth) {
		this.settleMonth = settleMonth;
	}
	
}
