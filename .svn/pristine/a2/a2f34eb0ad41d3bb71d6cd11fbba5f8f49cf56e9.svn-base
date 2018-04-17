package com.dome.sdkserver.metadata.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * BI report 日志
 * Created by ym on 2017/9/16.
 */
public class BiReportLogEntity {
    private Date dataTime;
    private String userId;
    private String appCode;
    private String channelId;
    private String sysType;
    private String imsi;
    private String platformType;//平台类型 0 移动平台(手游) 1 H5平台 2 页游OGP平台 3 VR平台
    private String recType;//日志记录类型  0 登录 1 注册 2 激活
    private String statisticsType;//统计类型 gameChange --> 游戏转化表(手游部分) webGame --> 游戏转化表(H5 页游部分)  platform --> 平台统计表

    public boolean validate() {
        if (StringUtils.isBlank(appCode)) {
            return false;
        }
        if (StringUtils.isBlank(userId)) {
            return false;
        }
        if (StringUtils.isBlank(sysType)) {
            return false;
        }
        if (StringUtils.isBlank(channelId)) {
            return false;
        }

        if (StringUtils.isBlank(recType)) {
            return false;
        }
        return true;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getRecType() {
        return recType;
    }

    public void setRecType(String recType) {
        this.recType = recType;
    }


    public String getStatisticsType() {
        return statisticsType;
    }

    public void setStatisticsType(String statisticsType) {
        this.statisticsType = statisticsType;
    }
}
