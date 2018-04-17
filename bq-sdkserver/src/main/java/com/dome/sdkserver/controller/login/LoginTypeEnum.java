package com.dome.sdkserver.controller.login;

public enum LoginTypeEnum {

    dome(1),
    qbao(2);


    public static String getLoginType(int code) {
        for (LoginTypeEnum loginType : LoginTypeEnum.values()) {
            if (loginType.code == code)
                return loginType.name();
        }
        return "";
    }

    private LoginTypeEnum(int code) {
        this.code = code;
    }

    private int code;
}
