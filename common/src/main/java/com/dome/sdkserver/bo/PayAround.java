package com.dome.sdkserver.bo;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by heyajun on 2017/4/11.
 */
public class PayAround {
    private Integer id;
    private String appCode;
    private String appName;
    private Integer merchantInfoId;
    private String merchantFullName;
    private String merchantCode;
    private Integer isAround;
    private String payType;
    private Timestamp createTime;
    private Timestamp updateTime;

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

    public Integer getMerchantInfoId() {
        return merchantInfoId;
    }

    public void setMerchantInfoId(Integer merchantInfoId) {
        this.merchantInfoId = merchantInfoId;
    }

    public String getMerchantFullName() {
        return merchantFullName;
    }

    public void setMerchantFullName(String merchantFullName) {
        this.merchantFullName = merchantFullName;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public Integer getIsAround() {
        return isAround;
    }

    public void setIsAround(Integer isAround) {
        this.isAround = isAround;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public PayAround() {
    }

    public PayAround(String appCode, String appName) {
        this.appCode = appCode;
        this.appName = appName;
    }
}
