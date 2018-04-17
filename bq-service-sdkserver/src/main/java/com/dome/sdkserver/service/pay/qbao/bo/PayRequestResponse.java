package com.dome.sdkserver.service.pay.qbao.bo;
/**
 * 支付返回结果V10
 * @author liuxingyue
 *
 */
public class PayRequestResponse extends BaseResponse{
	/** 钱宝流水号*/
	private String qbTransNum;
	/** 交易类型*/
	private String transType;
	/** 商户编码*/
	private String appCode;
	/** 交易流水号*/
	private String orderNo;
	/** 交易时间*/
	private String transTime;
	/** 商品介绍*/
	private String transIntro;
	/** 交易金额*/
	private String transAmount;
	/** 交易关闭时间*/
	private String transCloseTime;
	/** 交易状态*/
	private String status;
	
	
	public PayRequestResponse() {}
	
	public PayRequestResponse(boolean isSuccess) {
		this.setSuccess(isSuccess);
	}

	public PayRequestResponse(boolean isSuccess, String code, String msg) {
		super(isSuccess, code, msg);
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getQbTransNum() {
		return qbTransNum;
	}
	public void setQbTransNum(String qbTransNum) {
		this.qbTransNum = qbTransNum;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getTransIntro() {
		return transIntro;
	}
	public void setTransIntro(String transIntro) {
		this.transIntro = transIntro;
	}
	public String getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
	public String getTransCloseTime() {
		return transCloseTime;
	}
	public void setTransCloseTime(String transCloseTime) {
		this.transCloseTime = transCloseTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
