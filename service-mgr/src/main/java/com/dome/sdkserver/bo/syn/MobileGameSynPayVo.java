package com.dome.sdkserver.bo.syn;

public class MobileGameSynPayVo extends GameSynPayBase {

	private String outPublicRsaKey;
	
	private String outPrivateRsaKey;
	
	private String testOutPublicRsaKey;
	
	private String testOutPrivateRsaKey;
	
	private String loginCallBackUrl;
	
	private String testLoginCallBackUrl;

	public String getOutPublicRsaKey() {
		return outPublicRsaKey;
	}

	public void setOutPublicRsaKey(String outPublicRsaKey) {
		this.outPublicRsaKey = outPublicRsaKey;
	}

	public String getOutPrivateRsaKey() {
		return outPrivateRsaKey;
	}

	public void setOutPrivateRsaKey(String outPrivateRsaKey) {
		this.outPrivateRsaKey = outPrivateRsaKey;
	}

	public String getTestOutPublicRsaKey() {
		return testOutPublicRsaKey;
	}

	public void setTestOutPublicRsaKey(String testOutPublicRsaKey) {
		this.testOutPublicRsaKey = testOutPublicRsaKey;
	}

	public String getTestOutPrivateRsaKey() {
		return testOutPrivateRsaKey;
	}

	public void setTestOutPrivateRsaKey(String testOutPrivateRsaKey) {
		this.testOutPrivateRsaKey = testOutPrivateRsaKey;
	}

	public String getLoginCallBackUrl() {
		return loginCallBackUrl;
	}

	public void setLoginCallBackUrl(String loginCallBackUrl) {
		this.loginCallBackUrl = loginCallBackUrl;
	}

	public String getTestLoginCallBackUrl() {
		return testLoginCallBackUrl;
	}

	public void setTestLoginCallBackUrl(String testLoginCallBackUrl) {
		this.testLoginCallBackUrl = testLoginCallBackUrl;
	}
	
	
}
