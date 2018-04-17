package com.dome.sdkserver.util;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.view.AjaxResult;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ServletUtil
 *
 * @author Zhang ShanMin
 * @date 2016/5/15
 * @time 21:59
 */
public class ServletUtil {

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(name)) {
                    return cookies[i];
                }
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = ServletUtil.getCookie(request, cookieName);
        return cookie == null ? null : cookie.getValue();
    }

    /**
     * 添加指定有效期的COOKIE
     *
     * @param res
     * @param key
     * @param value
     * @param timeout 如果为负数，该Cookie为临时Cookie，关闭浏览器即失效，浏览器也不会以任何形式保存该Cookie。
     *                如果为0，表示删除该Cookie。默认为–1
     */
    public static void addCookie(HttpServletResponse res, String key, String value, int timeout, String host) {
        Cookie cookie = new Cookie(key, value);
        host = ".domestore.cn";
        cookie.setDomain(host);
        cookie.setMaxAge(timeout);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    /**
     * 删除cookie
     *
     * @param res
     * @param cookie
     * @param host
     */
    public static void delCookie(HttpServletResponse res, Cookie cookie, String host) {
        cookie.setDomain(host);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    /**
     * 默认cookies失效时间是直到关闭浏览器
     *
     * @param res
     * @param key
     * @param value
     */
    public static void addCookieByDefaultTime(HttpServletResponse res, String key, String value, String host) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(host);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    /**
     * @param request
     * @param seesionKey 会话key
     * @param obj        value
     * @param <T>
     */
    public static <T> void setSession(HttpServletRequest request, String seesionKey, T obj) {
        HttpSession session = request.getSession(true);
        session.setAttribute(seesionKey, obj);
//        session.setMaxInactiveInterval(outTime);
    }


    public static void writeErrorMsg(String message, PrintWriter writer) {
        writer.write(JSON.toJSONString(AjaxResult.failed(message)));
        writer.flush();
        writer.close();
    }

    public static void writeJson(Object data, PrintWriter writer) {
        writer.write(JSON.toJSONString(AjaxResult.success(data)));
        writer.flush();
        writer.close();
    }

    public static void httpRedirect(HttpServletResponse response, String path) {
        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开新窗口
     * @param response
     * @param url
     * @throws Exception
     */
    public static void windowOpen( String url,HttpServletResponse response) {
        try{
            PrintWriter printWriter = response.getWriter();
            printWriter.print("<script>window.open('" + url + "')</script>");
            printWriter.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
