package com.dome.sdkserver.vo;

import java.util.Date;


/**
 * 应用列表
 * @author hexiaoyi
 *
 */
public class MerchantAppVo {

	private String merchantCode;
	
	private String merchantFullName;
	
	private String appCode;
	
	private String appName;
	
	private int status;
	
	private String statusDesc;
	
	private String appIcon;
	
	private String remark;
	
	private Date createTime;
	
	private Date updateTime;

	/**
	 * 是否可以包体管理。要求应用的计费点都审核完成
	 * 1 可以 0 不可以
	 */
	private int canPkgManage = 1;
	
	/**
	 * 是否可以测试环境联调申请。要求上传过包体
	 * 1 可以 0 不可以
	 */
	private int canTestAdjust = 1;
	
	
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantFullName() {
		return merchantFullName;
	}

	public void setMerchantFullName(String merchantFullName) {
		this.merchantFullName = merchantFullName;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	
	

	public int getCanPkgManage() {
		return canPkgManage;
	}

	public void setCanPkgManage(int canPkgManage) {
		this.canPkgManage = canPkgManage;
	}

	public int getCanTestAdjust() {
		return canTestAdjust;
	}

	public void setCanTestAdjust(int canTestAdjust) {
		this.canTestAdjust = canTestAdjust;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
