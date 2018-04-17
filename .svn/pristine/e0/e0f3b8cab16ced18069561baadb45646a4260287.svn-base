package com.dome.sdkserver.metadata.entity.bq.award;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 游戏活动奖励规则
 * 
 * @author xuefeihu
 *
 */
public class StoreAwardConfEntity {

	/** 主键 */
	private Integer id;

	/** 商户编码 */
	private String merchantCode;

	/** 活动名称 */
	private String activityName;

	/** 应用市场APP id */
	private String storeAppId;

	/** APP编码 */
	private String appCode;

	/** 应用名称 */
	private String appName;

	/** 消费钱宝币数额 */
	private BigDecimal accountAmount;

	/** 宝券奖励数额 */
	private BigDecimal bqAward;

	/** 活动开始日期 */
	private String startDate;

	/** 活动开始时间 */
	private String startTime;

	/** 活动结束日期 */
	private String endDate;

	/** 活动结束时间 */
	private String endTime;

	/** 上下架：0 下架、1 上架 */
	private Integer publish;

	/** 应用市场配置ID */
	private String storeConfId;

	/** 重复发放：0 否、1 是 */
	private Integer reGrant;

	/** 规则类型：10 充返奖励 20 登录奖励 */
	private Integer type;

	/** 创建时间 */
	private Date createTime;

	/** 更新时间 */
	private Date updateTime;
	
	//-------------------------------------
	/** 是否有会员等级 0否1是 */
	private Integer idMember;
	
	/** 会员等级ID */
	private Integer memberLevelId;
	
	/** 会员等级 */
	private String memberLevel;

    private List<CouponPayConfig>  couponPayConfigs;

	public Integer getIdMember() {
		return idMember;
	}

	public void setIdMember(Integer idMember) {
		this.idMember = idMember;
	}

	public Integer getMemberLevelId() {
		return memberLevelId;
	}

	public void setMemberLevelId(Integer memberLevelId) {
		this.memberLevelId = memberLevelId;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getReGrant() {
		return reGrant;
	}

	public void setReGrant(Integer reGrant) {
		this.reGrant = reGrant;
	}

	public String getStoreConfId() {
		return storeConfId;
	}

	public void setStoreConfId(String storeConfId) {
		this.storeConfId = storeConfId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getStoreAppId() {
		return storeAppId;
	}

	public void setStoreAppId(String storeAppId) {
		this.storeAppId = storeAppId;
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

	public BigDecimal getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	}

	public BigDecimal getBqAward() {
		return bqAward;
	}

	public void setBqAward(BigDecimal bqAward) {
		this.bqAward = bqAward;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getPublish() {
		return publish;
	}

	public void setPublish(Integer publish) {
		this.publish = publish;
	}

	@Override
	public String toString() {
		return "StoreAwardConfEntity [id=" + id + ", merchantCode=" + merchantCode + ", activityName=" + activityName
				+ ", storeAppId=" + storeAppId + ", appCode=" + appCode + ", appName=" + appName + ", accountAmount="
				+ accountAmount + ", bqAward=" + bqAward + ", startDate=" + startDate + ", startTime=" + startTime
				+ ", endDate=" + endDate + ", endTime=" + endTime + ", publish=" + publish + ", storeConfId="
				+ storeConfId + ", reGrant=" + reGrant + ", type=" + type + "]";
	}

    public List<CouponPayConfig> getCouponPayConfigs() {
        return couponPayConfigs;
    }

    public void setCouponPayConfigs(List<CouponPayConfig> couponPayConfigs) {
        this.couponPayConfigs = couponPayConfigs;
    }
}
