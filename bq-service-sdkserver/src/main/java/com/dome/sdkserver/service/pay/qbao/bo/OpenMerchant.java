package com.dome.sdkserver.service.pay.qbao.bo;

import java.util.Date;

/**
 * 开放平台商户信息
 * 
 * @author Frank.Zhou
 *
 */
public class OpenMerchant {

	/*应用ID*/
	private Integer appId;
	
	/*商户编码*/
	private String merchantCode;
	
	/*商户名称*/
	private String merchantName;
	
	/*二级应用编码*/
	private String appCode;
	
	/*应用名称*/
	private String appName;
	
	/*RSA私钥*/
	private String privateKey;
	
	/*RSA公钥*/
	private String publicKey;
	
	/*白名单IP*/
	private String whiteIp;
	
	/*商户钱宝用户ID*/
	private Integer merchantUserId;
	
	/*状态*/
	private Integer status;
	
	/*创建时间*/
	private Date createDate;
	
	/*修改时间*/
	private Date updateDate;
	
	/** 登录appKey*/
	private String appKey;
	
	/** 登录回调地址**/
	private String loginRedirectUrl;

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

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

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getWhiteIp() {
		return whiteIp;
	}

	public void setWhiteIp(String whiteIp) {
		this.whiteIp = whiteIp;
	}

	public Integer getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(Integer merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getLoginRedirectUrl() {
		return loginRedirectUrl;
	}

	public void setLoginRedirectUrl(String loginRedirectUrl) {
		this.loginRedirectUrl = loginRedirectUrl;
	}
	
}