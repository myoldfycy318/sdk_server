package com.dome.sdkserver.metadata.entity.bq.pay;

public class AppInfoEntity extends NewOpenGameInfoEntity{
    //二级应用编码
    private String appCode;
    //应用名称
    private String appName;
    //对外rsa公钥
    private String outPublicRsaKey;
    //对外rsa私钥
    private String outPrivateRsaKey;
    //测试环境对外rsa公钥
    private String testOutPublicRsaKey;
    //测试环境对外rsa私钥
    private String testOutPrivateRsaKey;
    //游戏状态，0:下架，1:上架
    private Integer status;
    //商户ID
    private Integer merchantId;
    // 登录回调地址
    private String loginCallBackUrl;
    // 线上联调环境登录回调地址
    private String testLoginCallBackUrl;
    // 支付完成后游戏端的回调地址*
    private String payNotifyUrl;
    //线上联调环境 支付完成后游戏端的回调地址
    private String testPayNotifyUrl;
    //游戏地址
    private String gameUrl;
    //test游戏url
    private String testGameUrl;
    //H5游戏,页游登录key
    private String loginKey;
    //H5游戏,页游支付key
    private String payKey;
    //H5游戏,页游其他key
    private String appKey;
    //渠道编码
    private String channelCode;
    // 注册回调地址
    private String registCallBackUrl;
    //线上联调环境 注册回调地址
    private String testRegistCallBackUrl;
    //OGP平台字段 gameId
    private String ogpGameId;
    ///OGP平台字段 ogpGameKey
    private String ogpGameKey;
    //商户code
    private String merchantCode;

    public String getTestGameUrl() {
        return testGameUrl;
    }

    public void setTestGameUrl(String testGameUrl) {
        this.testGameUrl = testGameUrl;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getOgpGameId() {
        return ogpGameId;
    }

    public void setOgpGameId(String ogpGameId) {
        this.ogpGameId = ogpGameId;
    }

    public String getOgpGameKey() {
        return ogpGameKey;
    }

    public void setOgpGameKey(String ogpGameKey) {
        this.ogpGameKey = ogpGameKey;
    }

    public String getRegistCallBackUrl() {
        return registCallBackUrl;
    }

    public void setRegistCallBackUrl(String registCallBackUrl) {
        this.registCallBackUrl = registCallBackUrl;
    }

    public String getTestRegistCallBackUrl() {
        return testRegistCallBackUrl;
    }

    public void setTestRegistCallBackUrl(String testRegistCallBackUrl) {
        this.testRegistCallBackUrl = testRegistCallBackUrl;
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

    public String getLoginCallBackUrl() {
        return loginCallBackUrl;
    }

    public void setLoginCallBackUrl(String loginCallBackUrl) {
        this.loginCallBackUrl = loginCallBackUrl;
    }

    public String getOutPublicRsaKey() {
        return outPublicRsaKey;
    }

    public void setOutPublicRsaKey(String outPublicRsaKey) {
        this.outPublicRsaKey = outPublicRsaKey;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }

    public String getTestLoginCallBackUrl() {
        return testLoginCallBackUrl;
    }

    public void setTestLoginCallBackUrl(String testLoginCallBackUrl) {
        this.testLoginCallBackUrl = testLoginCallBackUrl;
    }

    public String getTestPayNotifyUrl() {
        return testPayNotifyUrl;
    }

    public void setTestPayNotifyUrl(String testPayNotifyUrl) {
        this.testPayNotifyUrl = testPayNotifyUrl;
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

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getOutPrivateRsaKey() {
        return outPrivateRsaKey;
    }

    public void setOutPrivateRsaKey(String outPrivateRsaKey) {
        this.outPrivateRsaKey = outPrivateRsaKey;
    }

    public String getTestOutPublicRsaKey() {
        return testOutPublicRsaKey;
    }

    public void setTestOutPublicRsaKey(String testOutPublicRsaKey) {
        this.testOutPublicRsaKey = testOutPublicRsaKey;
    }

    public String getTestOutPrivateRsaKey() {
        return testOutPrivateRsaKey;
    }

    public void setTestOutPrivateRsaKey(String testOutPrivateRsaKey) {
        this.testOutPrivateRsaKey = testOutPrivateRsaKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
