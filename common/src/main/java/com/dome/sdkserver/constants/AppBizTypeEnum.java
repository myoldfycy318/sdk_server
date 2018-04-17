package com.dome.sdkserver.constants;

//手游业务类型
public enum AppBizTypeEnum {
    发行(1),联运(2);

    private Integer value;

    AppBizTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
