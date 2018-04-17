package com.dome.sdkserver.metadata.entity.bq.pay;

import java.util.Date;

/**
 * Created by xuekuan on 2017/4/12.
 */
public class PayIosSwitch {
    private Integer id;

    private String appCode;

    private String appName;
    //绕行关闭状态: 0-绕行关闭 1-绕行开启
    private Integer isAround;
    //绕行支付方式: 1-支付宝 2-微信 3-银联
    private String payType;

    private Date createTime;

    private Date updateTime;

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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public Integer getIsAround() {
        return isAround;
    }

    public void setIsAround(Integer isAround) {
        this.isAround = isAround;
    }

    @Override
    public String toString() {
        return "PayIosSwitch{" +
                "id=" + id +
                ", appCode='" + appCode + '\'' +
                ", appName='" + appName + '\'' +
                ", isAround=" + isAround +
                ", payType='" + payType + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

