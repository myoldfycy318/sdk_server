package com.dome.sdkserver.bo;

/**
 * 用户中心userApp显示对象
 * 
 * @author Frank.Zhou
 *
 */
public class UserApp {

	/*应用名称*/
	private String appName;
	
	/*应用ID*/
	private String appId;
	
	/*安全认证key*/
	private String secretKey;
	
	/*RSA私钥*/
	private String privateKey;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
}
