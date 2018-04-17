package com.dome.sdkserver.security.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.util.ApiConnector;

public final class UserInterfaceUtil {

    private final static String USERCENTER_PREFIX_URL = "http://user.qbao.com";

    private static final Logger log = LoggerFactory
            .getLogger(UserInterfaceUtil.class);

    public static final String USER_CAS_ID_KEY = "id";
    public static final String USER_NAME_KEY = "username";
    public static final String USER_HYID_ID_KEY = "hyipUserId";
    public static final String USER_ENABLE_KEY = "enabled";
    public static final String USER_MOBILEPHONE_KEY = "mobile";

    public static List<String> getUserRoleByUserName(String username) {
        log.info("根据用户：{} ,获取用户基本信息接口，参数 :  username-- {} ", username, username);
        final String user_url = USERCENTER_PREFIX_URL
                + "/api/get/userRole/%s/new";
        List<String> list = new ArrayList<String>();
        try {
            String json = ApiConnector.get(String.format(user_url, username),
                    null);
            log.info("根据用户名：{} ,获取用户角色接口，返回:  {}", username, json);
            list = JSON.parseArray(json, String.class);
        } catch (Exception e) {
            log.error("根据用户名：{} ,获取用户角色接口,错误：{}", username, e.getMessage(), e);
        }
        return list;
    }

    public static Map<String, Object> getUserBaseInfo(String username) {
        log.info("根据用户：{} ,获取用户基本信息接口，参数 :  username-- {} ", username, username);
        final String user_url = USERCENTER_PREFIX_URL + "/api/get/user/";
        String securityKey = "dd44db9bf8d04a3eb9b2837fb44e2333";
        String sign = "username=" + username + "&securityKey=" + securityKey;
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        sign = encoder.encodePassword(sign, securityKey);
        try {
            String json = ApiConnector.get(user_url + username + "/" + sign
                    + ".html", null);
            log.info("根据用户：{} ,获取用户基本信息接口，返回 :  {}", username, json);
            Map<String, Object> map = JSON.<Map<String, Object>> parseObject(
                    json, Map.class);
            String code = (String) map.get("code");
            if ("1".equals(code)) {// 用户存在
                json = (String) map.get("data");
                Map<String, Object> map2 = JSON
                        .<Map<String, Object>> parseObject(json, Map.class);
                return map2;
            }
        } catch (Exception e) {
            log.error("根据用户：{} ,获取用户基本信息接口，错误：{}", username, e.getMessage(), e);
        }
        return null;
    }

    public static String getCurrentUsername() {
        String username = "";
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return username;
        }
        Authentication authentication = context.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }
}
