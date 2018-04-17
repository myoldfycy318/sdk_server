package com.dome.sdkserver.bo;

import java.io.Serializable;
import java.util.Date;

public class AppPic implements Serializable {
    /**
     * .
     */
    private Integer id;

    /**
     * 应用ID.
     */
    private String serviceId;

    /**
     * 截图描述.
     */
    private String picDesc;

    /**
     * 截图URL.
     */
    private String picUrl;

    /**
     * 删除标识 0 未删除 1 已删除.
     */
    private Boolean delFlag;

    /**
     * .
     */
    private Date createTime;

    /**
     * .
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPicDesc() {
        return picDesc;
    }

    public void setPicDesc(String picDesc) {
        this.picDesc = picDesc;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
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

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	@Override
	public String toString() {
		return "AppPic [picDesc=" + picDesc + ", picUrl=" + picUrl
				+ ", delFlag=" + delFlag + "]";
	}
    
    
}