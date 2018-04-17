package com.dome.sdkserver.metadata.entity.bq.webgame;

/**
 * Created by heyajun on 2017/8/1.
 */
public class WebGameLoginEntity {
    //开放平台应用id
    private String appCode;

    //冰穹userId
    private String userId;

    //区服Id
//    private String zoneId;
    private String areaServerNumber;


    //游戏名称(透传字段)
    private String gameName;

    private String time;

    private Integer isAdult;

    private String channelCode;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getIsAdult() {
        return isAdult;
    }

    public void setIsAdult(Integer isAdult) {
        this.isAdult = isAdult;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
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

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getAreaServerNumber() {
        return areaServerNumber;
    }

    public void setAreaServerNumber(String areaServerNumber) {
        this.areaServerNumber = areaServerNumber;
    }

}
