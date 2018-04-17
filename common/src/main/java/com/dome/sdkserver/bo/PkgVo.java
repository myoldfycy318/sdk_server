package com.dome.sdkserver.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * 包体管理界面信息呈现
 * 相对于类Pkg，不显示包上传路径等敏感信息
 * @author lilongwei
 *
 */
public class PkgVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 包体加固记录的id
	 */
	private long id;
	
	private String name;
	
	private String fileSize;
	
	private Date uploadTime;
	
	private Date jiaguTime;
	
	private String sdkVersion;

	/**
	 * 第一次上传为空，后面上传标注为变更包体
	 */
	private String remark;
	
	/**
	 * 加固包文件大小
	 */
	private String jiaguFileSize;
	
	private String downloadUrl;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Date getJiaguTime() {
		return jiaguTime;
	}

	public void setJiaguTime(Date jiaguTime) {
		this.jiaguTime = jiaguTime;
	}

	public String getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getJiaguFileSize() {
		return jiaguFileSize;
	}

	public void setJiaguFileSize(String jiaguFileSize) {
		this.jiaguFileSize = jiaguFileSize;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	@Override
	public String toString() {
		return "PkgVo [id=" + id + ", name=" + name + ", fileSize=" + fileSize
				+ ", uploadTime=" + uploadTime + ", jiaguTime=" + jiaguTime
				+ ", sdkVersion=" + sdkVersion + ", remark=" + remark + "]";
	}


}
