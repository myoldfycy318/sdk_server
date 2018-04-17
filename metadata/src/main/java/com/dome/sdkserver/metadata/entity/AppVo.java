package com.dome.sdkserver.metadata.entity;

import com.dome.sdkserver.bo.MerchantAppInfo;

/**
 * 合并开放平台老的应用手游和新增加的页游、H5
 * 审批页面合并在一个列表中
 * @author lilongwei
 *
 */
public class AppVo extends MerchantAppInfo {

	/**
	 * 游戏版权标识，1自研 2代理
	 */
	private int copyRightFlag;
	
	/**
	 * 软件著作权登记证书图片url
	 */
	private String compareSoftwareUrl;
	
	private String loginKey;
	
	private String payKey;
	
	/**
	 * H5游戏的app_key，区别与手游的appKey
	 */
	private String appKey2;

	/**
	 * 开发商
	 */
	private String developers;
	
	public int getCopyRightFlag() {
		return copyRightFlag;
	}

	public void setCopyRightFlag(int copyRightFlag) {
		this.copyRightFlag = copyRightFlag;
	}

	public String getCompareSoftwareUrl() {
		return compareSoftwareUrl;
	}

	public void setCompareSoftwareUrl(String compareSoftwareUrl) {
		this.compareSoftwareUrl = compareSoftwareUrl;
	}

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public String getPayKey() {
		return payKey;
	}

	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}

	public String getAppKey2() {
		return appKey2;
	}

	public void setAppKey2(String appKey2) {
		this.appKey2 = appKey2;
	}

	public String getDevelopers() {
		return developers;
	}

	public void setDevelopers(String developers) {
		this.developers = developers;
	}
	
	
}
