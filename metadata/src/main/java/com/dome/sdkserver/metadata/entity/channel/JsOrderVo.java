package com.dome.sdkserver.metadata.entity.channel;

import java.math.BigDecimal;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.dome.sdkserver.constants.channel.JsStatusEnum;

public class JsOrderVo {

	private String month;
	
	private BigDecimal amount;
	
	private String orderNo;
	
	/**
	 * 申请时间
	 */
	private String applyDate;
	
	private String contact;
	
	private String bankAccount;
	
	private int status;
	
	private String statusDesc;
    
	private String bankName;
	
	private String address;
	
	private String channelCode;
	
	/**
	 * 渠道名称
	 */
	private String name;
	
	private static final String MONTH_FORMAT="yyyy年MM月";
	public JsOrderVo(JsOrder order){
		this.orderNo=order.getOrderNo();
		// 金额显示财务打款金额
		this.amount=order.getJsAmount();
		this.applyDate=DateFormatUtils.format(order.getApplyDate(), "yyyy-MM-dd");
		this.status=order.getStatus();
		if (status!=0){
			this.statusDesc=JsStatusEnum.getStatusDesc(status);
		}
		this.contact=order.getContact();
		this.bankAccount=order.getBankAccount();
		// 地址、开户卡、渠道名称
		this.name=order.getName();
		this.bankName=order.getBankName();
		this.address=order.getAddress();
		String beginMonth=DateFormatUtils.format(order.getFromDate(), MONTH_FORMAT);
		String endMonth=DateFormatUtils.format(order.getToDate(), MONTH_FORMAT);
		this.month=beginMonth.equals(endMonth) ? beginMonth 
				: beginMonth +"-"+endMonth;
	}

	/**
	 * 查询结算金额时使用本构造器
	 * @param month 结算周期说明，若只有一个月，譬如“2016年7月”；多个月份，譬如2016年5月-2016年7月
	 * @param amount 当前结算周期计算的金额
	 */
	public JsOrderVo(String month, BigDecimal amount){
		this.month=month;
		this.amount=amount;
	}
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
