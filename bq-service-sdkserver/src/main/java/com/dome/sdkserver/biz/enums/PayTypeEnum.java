package com.dome.sdkserver.biz.enums;

public enum PayTypeEnum {

    支付宝支付(1),

    钱宝支付(2),

    银联支付(3),

    微信支付(4);

    private int code;

    private PayTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PayTypeEnum getTypeEnum(Integer code) {
        if (code == null)
            return null;
        for (PayTypeEnum typeEnum : PayTypeEnum.values()) {
            if (typeEnum.code == code)
                return typeEnum;
        }
        return null;
    }

}
