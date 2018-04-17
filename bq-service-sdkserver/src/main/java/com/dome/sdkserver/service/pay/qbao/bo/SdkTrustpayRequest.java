/**
 * 
 */
package com.dome.sdkserver.service.pay.qbao.bo;

import java.math.BigDecimal;

/**
 * @author mazhongmin
 * sdk信任支付请求
 *
 */
public class SdkTrustpayRequest {

	/**交易类型**/
	private String transType;
	/**应用编码*/
	private String appCode;
	/**业务流水号*/
	private String orderNo;
	/**用户id*/
	private Long userId;
	/**用户名*/
	private String loginName;
	/**交易金额*/
	private BigDecimal transAmount;
	/**应用渠道  0-IOS 1-WEB  2-Android*/
	private int appSource;
	/**交易简介*/
	private String transIntro;
	/**支付方式  0-组合支付   1-宝币支付*/
	private String payType;
	/**异步通知url*/
	private String payNotifyUrl;
	/**签文*/
	private String signCode;
	/**加签类型  默认RSA*/
	private String signType;
	/**请求ip*/
	private String requestIp;
	
	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public int getAppSource() {
		return appSource;
	}

	public void setAppSource(int appSource) {
		this.appSource = appSource;
	}

	public String getTransIntro() {
		return transIntro;
	}

	public void setTransIntro(String transIntro) {
		this.transIntro = transIntro;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}

	public String getSignCode() {
		return signCode;
	}

	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
