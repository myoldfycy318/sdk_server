package com.dome.sdkserver.metadata.entity.h5;

import com.dome.sdkserver.metadata.entity.AbstractGame;

public class H5Game extends AbstractGame {

	/**
	 * 游戏url
	 */
	private String appUrl;
	
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
	
	private String appKey;
	
	/**
	 * 开发商
	 */
	private String developers;
	
	/**
	 * 游戏类型，文本框输入
	 */
	private String gameType;
	
	/**
	 * 是否为冰趣
	 */
	private boolean isBq;
	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

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

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getDevelopers() {
		return developers;
	}

	public void setDevelopers(String developers) {
		this.developers = developers;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public boolean isBq() {
		return isBq;
	}

	public void setBq(boolean isBq) {
		this.isBq = isBq;
	}
	
	
}
