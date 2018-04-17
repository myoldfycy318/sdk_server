package com.dome.sdkserver.util;

import com.dome.sdkserver.bq.enumeration.SysTypeEnum;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

    /**
     * 判断是否为ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }

    /**
     * 获取请求来源
     *
     * @param request
     * @return
     */
    public static String getSysType(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.toLowerCase().indexOf("windows") >= 0) {
            return SysTypeEnum.WEB.name();
        }  else if (userAgent.toLowerCase().indexOf("android") >= 0) {
            return SysTypeEnum.WAP.name();
        } else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
            return SysTypeEnum.WAP.name();
        } else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
            return SysTypeEnum.WEB.name();
        }else {
            return SysTypeEnum.WAP.name();
        }
    }
}
