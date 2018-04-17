package com.dome.sdkserver.bo.sdkversion;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * SdkVersionInfo
 *
 * @author Zhang ShanMin
 * @date 2016/5/9
 * @time 22:05
 */
public class SdkVersionInfo {

    //主键
    private Integer id;
    // 版本名称
    private String versionName;
    //版本号
    private String versionNum;
    //需热更版本
    private String needUpdateVersion;
    //需热更游戏
    private String needUpdateGame;
    //sdk路径
    private String sdkPath;
    //版本说明
    private String versionDesc;
    //是否发布,0:未,1:已发布
    private Integer isRelease;
    //  创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
    //发布时间
    private Date releaseTime;
    //sdk上传时间
    private Date sdkUploadTime;
    //sdk上传时间开始时间
    @JSONField(serialize = false)
    private Date sdkUploadStartTime;
    //sdk上传结束时间
    @JSONField(serialize = false)
    private Date sdkUploadEndTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getNeedUpdateVersion() {
        return needUpdateVersion;
    }

    public void setNeedUpdateVersion(String needUpdateVersion) {
        this.needUpdateVersion = needUpdateVersion;
    }

    public String getNeedUpdateGame() {
        return needUpdateGame;
    }

    public void setNeedUpdateGame(String needUpdateGame) {
        this.needUpdateGame = needUpdateGame;
    }

    public String getSdkPath() {
        return sdkPath;
    }

    public void setSdkPath(String sdkPath) {
        this.sdkPath = sdkPath;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public Integer getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(Integer isRelease) {
        this.isRelease = isRelease;
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

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Date getSdkUploadTime() {
        return sdkUploadTime;
    }

    public void setSdkUploadTime(Date sdkUploadTime) {
        this.sdkUploadTime = sdkUploadTime;
    }

    public Date getSdkUploadStartTime() {
        return sdkUploadStartTime;
    }

    public void setSdkUploadStartTime(Date sdkUploadStartTime) {
        this.sdkUploadStartTime = sdkUploadStartTime;
    }

    public Date getSdkUploadEndTime() {
        return sdkUploadEndTime;
    }

    public void setSdkUploadEndTime(Date sdkUploadEndTime) {
        this.sdkUploadEndTime = sdkUploadEndTime;
    }
}
