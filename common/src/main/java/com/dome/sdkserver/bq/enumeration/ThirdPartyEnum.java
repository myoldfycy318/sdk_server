package com.dome.sdkserver.bq.enumeration;

public enum ThirdPartyEnum {
    QBAO("1");

    private String thirdId;

    private ThirdPartyEnum(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdId() {
        return thirdId;
    }
}
