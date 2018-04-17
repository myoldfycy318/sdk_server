package com.dome.sdkserver.bq.login.domain.user;

import java.io.Serializable;

/**
 * 实名认证请求信息封装
 *
 * @author wuxiaoyang
 */
public class IdCardRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 冰穹uc下发的token
     */
    private String accessToken;

    /**
     * 身份证号码
     */
    private String card;

    /**
     * 游戏的appCode
     */
    private String appCode;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 国家码(默认86)
     */
    private String countryCode;

    private String buId;

    public IdCardRecord(){

    }

    private IdCardRecord(Builder b) {
        this.accessToken = b.accessToken;
        this.card = b.card;
        this.appCode = b.appCode;
        this.mobile = b.mobile;
        this.verifyCode = b.verifyCode;
        this.name = b.name;
        this.countryCode = b.countryCode;
        this.buId = b.buId;
    }

    public static class Builder {
        private String accessToken;
        private String card;
        private String appCode;
        private String mobile;
        private String verifyCode;
        private String name;
        private String countryCode;
        private String buId;

        public Builder() {
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder card(String card) {
            this.card = card;
            return this;
        }

        public Builder appCode(String appCode) {
            this.appCode = appCode;
            return this;
        }

        public Builder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public Builder verifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder buId(String buId) {
            this.buId = buId;
            return this;
        }

        public IdCardRecord build() {
            return new IdCardRecord(this);
        }
    }


    public String getBuId() {
        return buId;
    }

    public void setBuId(String buId) {
        this.buId = buId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


}
