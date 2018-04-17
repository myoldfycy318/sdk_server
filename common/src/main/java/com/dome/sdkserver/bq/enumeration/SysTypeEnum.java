package com.dome.sdkserver.bq.enumeration;


import org.apache.commons.lang3.StringUtils;

/**
 * SysTypeEnum
 *
 * @author Zhang ShanMin
 * @date 2017/9/12
 * @time 17:23
 */
public enum SysTypeEnum {
    IOS, AD, WEB, WAP;

    public static SysTypeEnum getSysType(String sysType) {
        if (StringUtils.isBlank(sysType))
            return null;
        if (sysType.toLowerCase().contains("android"))
            return AD;
        return null;
    }
}
