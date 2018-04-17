package com.dome.sdkserver.metadata.entity.bq.sdkversion;

/**
 * SdkVersion
 *
 * @author Zhang ShanMin
 * @date 2016/12/5
 * @time 11:44
 */
public class SdkVersion {

    //appcode
    private String appCode;
    //sdk当前版本
    private String currentVersion;
    //sdk渠道
    private String channelCode;
    //渠道类型，int类型，0:默认，1:谷歌，2:mycard
    private Integer channelType = 0;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }
}
