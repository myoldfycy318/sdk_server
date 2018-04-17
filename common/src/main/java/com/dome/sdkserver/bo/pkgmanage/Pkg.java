package com.dome.sdkserver.bo.pkgmanage;

import java.util.Date;

public class Pkg {

	private long id;
	
	private String appCode;
	
	private String name;
	
	private String fileName;
	
	private String fileSize;

	
	private String uploadPath;
	
	private Date uploadTime;
	
	private int uploadStatus;
	
	private String jiaguPath;
	
	private Date jiaguTime;
	
	private int jiaguStatus;
	
	private Date updateTime;

	private String sdkVersion;

	private String packageName;
	
	private String version;
	
	
	/**
	 * 加固包文件大小
	 */
	private String jiaguFileSize;
	
	private transient String physicalFilePath;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public int getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(int uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getJiaguPath() {
		return jiaguPath;
	}

	public void setJiaguPath(String jiaguPath) {
		this.jiaguPath = jiaguPath;
	}

	public Date getJiaguTime() {
		return jiaguTime;
	}

	public void setJiaguTime(Date jiaguTime) {
		this.jiaguTime = jiaguTime;
	}

	public int getJiaguStatus() {
		return jiaguStatus;
	}

	public void setJiaguStatus(int jiaguStatus) {
		this.jiaguStatus = jiaguStatus;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public String getJiaguFileSize() {
		return jiaguFileSize;
	}

	public void setJiaguFileSize(String jiaguFileSize) {
		this.jiaguFileSize = jiaguFileSize;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPhysicalFilePath() {
		return physicalFilePath;
	}

	public void setPhysicalFilePath(String physicalFilePath) {
		this.physicalFilePath = physicalFilePath;
	}

	@Override
	public String toString() {
		return "Pkg [id=" + id + ", appCode=" + appCode + ", name=" + name
				+ ", fileName=" + fileName + ", fileSize=" + fileSize
				+ ", uploadPath=" + uploadPath + ", uploadTime=" + uploadTime
				+ ", uploadStatus=" + uploadStatus + ", jiaguPath=" + jiaguPath
				+ ", jiaguTime=" + jiaguTime + ", jiaguStatus=" + jiaguStatus
				+ ", updateTime=" + updateTime + ", sdkVersion=" + sdkVersion
				+ "]";
	}


	
}
