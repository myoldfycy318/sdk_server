package com.dome.sdkserver.web.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


/**
 * App通用工具类
 *
 */
public abstract class AppUtil {

    /** 有效的图片后缀 **/
    public static final String[] imgSuffixs = new String[] { "jpeg", "jpg", "gif", "png", "bmp"};

    public static final Set<String> lowerImgSuffixSet=new HashSet<>(Arrays.asList(imgSuffixs));
    /**
     * 根据用户ID获取分表序列
     * @param userId 用户ID
     * @return 分表序列
     */
    public static long getTableShardingSeq(Integer userId) {
        if (null == userId) {
            throw new RuntimeException("用户ID不能为空");
        }
        return userId == -1 ? 100 : userId % 100;
    }

    /**
     * 图片格式校验
     * @param fileName
     * @return
     */
    public static String checkPicFormat(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()); // 获取文件后缀
        if (!lowerImgSuffixSet.contains(suffix.toLowerCase())) {
            return "图片格式只能是jpeg,gif,jpg,png,bmp";
        }
        return null;
    }
    
    public static String getRealIp(HttpServletRequest request) {
    	 String ip = request.getHeader("X-Real-IP");
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         	ip = request.getHeader("x-forwarded-for");
         }

         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         	ip = request.getHeader("Proxy-Client-IP");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         	ip = request.getHeader("WL-Proxy-Client-IP");
         }

         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getRemoteAddr();
         }

         //防止多级代理时返回过个ip。
         if(ip != null && ip.indexOf(",") != -1){
             ip= ip.substring(0,ip.indexOf(","));
         }
         return ip;
    }

    private AppUtil() {
    }
}
