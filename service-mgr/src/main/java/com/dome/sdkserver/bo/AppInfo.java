package com.dome.sdkserver.bo;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 与宝玩渠道应用信息同步使用的实体类
 * @author li
 *
 */
public class AppInfo implements Serializable {

    public enum STATUS_ENUM{

        STATUS_1(1,"上架");

        private int value;
        private String name;

        STATUS_ENUM(int value,String name){
            this.name=name;
            this.value=value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * .
     */
    private Integer id;

    /**
     * 应用ID，宝玩自动生成
     */
    private String serviceId;

    /**
     * 商户ID.
     */
    private Integer merchantInfoId;

    private String merchantCode;
    
    /**
     * 应用名称.
     */
    private String appName;

    /**
     * 应用编码.
     */
    private String appCode;

    /**
     * 应用icon.
     */
    private String appIcon;

    /**
     * 应用下载地址.
     */
    private String appUrl;

    /**
     * 应用一句话描述
     */
    private String introduction;

    /**
     * 0下架 1上架 2停止
     */
    private int status;

    /**
     * 删除标识：0 未删除 1 已删除.
     */
    private int delFlag;

    /**
     * 更新时间.
     */
    private Date updateTime;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 安卓版本号
     */
    private String runPlatform;

    /**
     * 应用1级类型，值为类型id.
     */
    private String appType0;

    /**
     * 应用2级类型.
     */
    private String appType1;

    /**
     * 应用3级分类.
     */
    private String appType2;

    /**
     * app关键字.
     */
    private String appKeyword;

    /**
     * app包名.
     */
    private String appApkName;

    /**
     * 包体大小.
     */
    private String appApkSize;

    /**
     * 软件版本
     */
    private String appApkVersion;

    /**
     * 包体名称
     */
    private String appApkFileName;

    /**
     * 下载量.
     */
    private Integer downloadNum;

    /**
     * 游戏评分.
     */
    private Integer score;

    /**
     * 应用长描述.
     */
    private String appDesc;

    private List<AppPic> appPics;

    private static final long serialVersionUID = 1L;

    public List<AppPic> getAppPics() {
		return appPics;
	}

	public void setAppPics(List<AppPic> appPics) {
		this.appPics = appPics;
	}

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

    public Integer getMerchantInfoId() {
        return merchantInfoId;
    }

    public void setMerchantInfoId(Integer merchantInfoId) {
        this.merchantInfoId = merchantInfoId;
    }

    public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRunPlatform() {
        return runPlatform;
    }

    public void setRunPlatform(String runPlatform) {
        this.runPlatform = runPlatform;
    }

    public String getAppType0() {
        return appType0;
    }

    public void setAppType0(String appType0) {
        this.appType0 = appType0;
    }

    public String getAppType1() {
        return appType1;
    }

    public void setAppType1(String appType1) {
        this.appType1 = appType1;
    }

    public String getAppType2() {
        return appType2;
    }

    public void setAppType2(String appType2) {
        this.appType2 = appType2;
    }

    public String getAppKeyword() {
        return appKeyword;
    }

    public void setAppKeyword(String appKeyword) {
        this.appKeyword = appKeyword;
    }

    public String getAppApkName() {
        return appApkName;
    }

    public void setAppApkName(String appApkName) {
        this.appApkName = appApkName;
    }

    public String getAppApkSize() {
        return appApkSize;
    }

    public void setAppApkSize(String appApkSize) {
        this.appApkSize = appApkSize;
    }

    public String getAppApkVersion() {
        return appApkVersion;
    }

    public void setAppApkVersion(String appApkVersion) {
        this.appApkVersion = appApkVersion;
    }

    public String getAppApkFileName() {
        return appApkFileName;
    }

    public void setAppApkFileName(String appApkFileName) {
        this.appApkFileName = appApkFileName;
    }

    public Integer getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(Integer downloadNum) {
        this.downloadNum = downloadNum;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

	@Override
	public String toString() {
		return "AppInfo [merchantInfoId=" + merchantInfoId + ", appName="
				+ appName + ", appCode=" + appCode + ", appIcon=" + appIcon
				+ ", appUrl=" + appUrl + ", introduction=" + introduction
				+ ", status=" + status + ", delFlag=" + delFlag
				+ ", updateTime=" + updateTime + ", createTime=" + createTime
				+ ", runPlatform=" + runPlatform + ", appType0=" + appType0
				+ ", appType1=" + appType1 + ", appType2=" + appType2
				+ ", appKeyword=" + appKeyword + ", appApkName=" + appApkName
				+ ", appApkSize=" + appApkSize + ", appApkVersion="
				+ appApkVersion + ", appApkFileName=" + appApkFileName
				+ ", downloadNum=" + downloadNum + ", score=" + score
				+ ", appDesc=" + appDesc + ", appPics=" + appPics + "]";
	}
    
    
}