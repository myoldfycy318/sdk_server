package com.dome.sdkserver.metadata.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class AppOperRecord {

    private Integer id;
	/**
	 * 手游使用
	 */
    @JSONField(serialize = false)
	private int appId;
    @JSONField(serialize = false)
	private int chargePointId;
    @JSONField(serialize = false)
    
    /**
     * yeyou和h5记录appCode，要不同一个appId数字不能确定是否为手游
     */
    private String appCode;
    
	private int status;
	
	private String operDesc;
	
	private String remark;
    @JSONField(serialize = false)
	private long operUserId;
	
	private String operUser;
	
	private Date createTime;

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public int getChargePointId() {
		return chargePointId;
	}

	public void setChargePointId(int chargePointId) {
		this.chargePointId = chargePointId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOperDesc() {
		return operDesc;
	}

	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getOperUserId() {
		return operUserId;
	}

	public void setOperUserId(long operUserId) {
		this.operUserId = operUserId;
	}

	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "AppOperRecord [appId=" + appId + ", chargePointId="
				+ chargePointId + ", status=" + status + ", operDesc="
				+ operDesc + ", remark=" + remark + ", operUserId="
				+ operUserId + ", operUser=" + operUser + ", createTime="
				+ createTime + "]";
	}
	
}
