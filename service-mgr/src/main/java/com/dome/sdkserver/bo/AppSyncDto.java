package com.dome.sdkserver.bo;

import java.io.Serializable;
import java.util.List;

/**
 * 和钱宝后台同步应用信息使用的实体类
 * App.java
 * @author li
 *
 */
public class AppSyncDto extends App implements Serializable{
	private static final long serialVersionUID = 6811148325911803116L;

	/**
     * 应用包名
     */
    private String packName;
    
    /**
     * 平台类型(0=ios,1=android).
     */
    private String platform;
    
    /**
     * 应用大小.
     */
    private String size;
    
    /**
     * 应用版本要求.
     */
    private String requirement;
    
    /**
     * 应用来源.
     */
    private String src;
    
    /**
     * appstore地址(ios用).
     */
    private String appStoreUrl;
    
    /**
     * 应用包地址.
     */
    private String packUrl;
    
    /**
     * 版本号.
     */
    private Integer rowVersion;
    
    /**
     * 应用版本
     */
    private String version;
    
    private List<String> picUrls;
    
	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getAppStoreUrl() {
		return appStoreUrl;
	}

	public void setAppStoreUrl(String appStoreUrl) {
		this.appStoreUrl = appStoreUrl;
	}

	public String getPackUrl() {
		return packUrl;
	}

	public void setPackUrl(String packUrl) {
		this.packUrl = packUrl;
	}

	public Integer getRowVersion() {
		return rowVersion;
	}

	public void setRowVersion(Integer rowVersion) {
		this.rowVersion = rowVersion;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<String> getPicUrls() {
		return picUrls;
	}

	public void setPicUrls(List<String> picUrls) {
		this.picUrls = picUrls;
	}
}
