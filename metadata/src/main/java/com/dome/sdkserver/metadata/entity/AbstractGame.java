package com.dome.sdkserver.metadata.entity;

/**
 * 页游和H5抽象出来的父类
 * 字段需要保持与开放平台的应用字段保持一致
 * @author lilongwei
 *
 */
public abstract class AbstractGame extends Base {

	private int appId;
	
	private String appCode;
	
	/**
	 * 游戏名称
	 */
	private String appName;
	
	/**
	 * 游戏描述
	 */
	private String appDesc;
	
	/**
	 * 游戏icon
	 */
	private String appIcon;

	/**
	 * 驳回理由
	 */
	private String remark;
	
	/**
	 * 商家id
	 */
	private int merchantInfoId;
	
	private String merchantFullName;
	private int userId;

	private String payCallBackUrl;
	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
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

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getMerchantInfoId() {
		return merchantInfoId;
	}

	public void setMerchantInfoId(int merchantInfoId) {
		this.merchantInfoId = merchantInfoId;
	}

	public String getMerchantFullName() {
		return merchantFullName;
	}

	public void setMerchantFullName(String merchantFullName) {
		this.merchantFullName = merchantFullName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPayCallBackUrl() {
		return payCallBackUrl;
	}

	public void setPayCallBackUrl(String payCallBackUrl) {
		this.payCallBackUrl = payCallBackUrl;
	}

	
	
}
