package com.dome.sdkserver.bo.appstatus;

import java.util.List;

public class AppVerStatusEntity {
	
	private long appId;
	private String appCode;
	private String appName;
	private String appVer;
	private int status;
	
	/**
	 * 将应用的多个版本状态合并到一条数据
	 */
	private List<VerStatusEntity> list;
	
	public long getAppId() {
		return appId;
	}
	public void setAppId(long appId) {
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
	public String getAppVer() {
		return appVer;
	}
	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public List<VerStatusEntity> getList() {
		return list;
	}
	public void setList(List<VerStatusEntity> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "AppVerStatusEntity [appId=" + appId + ", appCode=" + appCode
				+ ", appName=" + appName + ", appVer=" + appVer + ", status="
				+ status + "]";
	}
	
}
