package com.dome.sdkserver.metadata.entity.bq.pay;

/**
 * PayTypeCondition
 *
 * @author Zhang ShanMin
 * @date 2017/11/29
 * @time 17:32
 */
public class PayTypeCondition {

    private String appCode;
    private String channelCode;
    private String userId;
    private String sysType;


    public PayTypeCondition() {
    }


    private PayTypeCondition(Builder b) {
        this.appCode = b.appCode;
        this.channelCode = b.channelCode;
        this.userId = b.userId;
        this.sysType = b.sysType;
    }

    public static class Builder {
        private String appCode;
        private String channelCode;
        private String userId;
        private String sysType;

        public Builder() {
        }

        public Builder appCode(String appCode) {
            this.appCode = appCode;
            return this;
        }

        public Builder channelCode(String channelCode) {
            this.channelCode = channelCode;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder sysType(String sysType) {
            this.sysType = sysType;
            return this;
        }

        public PayTypeCondition build() {
            return new PayTypeCondition(this);
        }
    }


    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
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

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }
}
