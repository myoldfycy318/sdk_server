package com.dome.sdkserver.metadata.entity.bq.paystatistics;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * PayStatistics
 *
 * @author Zhang ShanMin
 * @date 2016/7/20
 * @time 17:46
 */
public class PayStatistics {

    //商户Id
    private Integer merchantInfoId;
    //业务代码
    private String appCode;
    //业务名称
    private String appName;
    //钱宝支付（单位：元）
    private BigDecimal bwPayAmount;
    //宝券流水（非折现,单位：分）
    private BigDecimal bwBqAmount;
    //宝券流水（折现）
    private BigDecimal disBwBqAcount;
    //支付宝流水
    private BigDecimal aliPayAmount;
    //流水总计
    private BigDecimal totalWater;
    //付费用户数（去重）
    private Integer payUserCount;
    //交易日期
    @JSONField(format = "yyyy-MM-dd")
    private Date transDate;
    @JSONField(serialize = false)
    private Date createTime;
    //查询开始时间
    @JSONField(format = "yyyy-MM-dd")
    private Date startTime;
    //查询结束时间
    @JSONField(format = "yyyy-MM-dd")
    private Date endTime;
    @JSONField(format = "yyyy-MM-dd")
    private Date publishTime;

    public Integer getMerchantInfoId() {
        return merchantInfoId;
    }

    public void setMerchantInfoId(Integer merchantInfoId) {
        this.merchantInfoId = merchantInfoId;
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

    public BigDecimal getBwPayAmount() {
        return bwPayAmount;
    }

    public void setBwPayAmount(BigDecimal bwPayAmount) {
        this.bwPayAmount = bwPayAmount;
    }

    public BigDecimal getBwBqAmount() {
        return bwBqAmount;
    }

    public void setBwBqAmount(BigDecimal bwBqAmount) {
        this.bwBqAmount = bwBqAmount;
    }

    public BigDecimal getDisBwBqAcount() {
        return disBwBqAcount;
    }

    public void setDisBwBqAcount(BigDecimal disBwBqAcount) {
        this.disBwBqAcount = disBwBqAcount;
    }

    public BigDecimal getAliPayAmount() {
        return aliPayAmount;
    }

    public void setAliPayAmount(BigDecimal aliPayAmount) {
        this.aliPayAmount = aliPayAmount;
    }

    public BigDecimal getTotalWater() {
        return totalWater;
    }

    public void setTotalWater(BigDecimal totalWater) {
        this.totalWater = totalWater;
    }

    public Integer getPayUserCount() {
        return payUserCount;
    }

    public void setPayUserCount(Integer payUserCount) {
        this.payUserCount = payUserCount;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
