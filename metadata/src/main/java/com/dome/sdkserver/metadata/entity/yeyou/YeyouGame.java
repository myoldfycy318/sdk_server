package com.dome.sdkserver.metadata.entity.yeyou;

import com.dome.sdkserver.metadata.entity.AbstractGame;

public class YeyouGame extends AbstractGame{

    private String loginCallBackUrl;

    private String loginKey;

    private String payKey;

    private String appKey;

    public String getLoginCallBackUrl() {
        return loginCallBackUrl;
    }

    public void setLoginCallBackUrl(String loginCallBackUrl) {
        this.loginCallBackUrl = loginCallBackUrl;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
