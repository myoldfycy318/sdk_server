package com.dome.sdkserver.interceptors;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.constants.DomeSdkRedisKey;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RedisUtil;
import com.dome.sdkserver.util.ServletUtil;
import com.dome.sdkserver.view.AjaxResult;

/**
 * LoginInterceptor
 *
 * @author Zhang ShanMin
 * @date 2016/5/16
 * @time 11:39
 */
public class AuthInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<String> allowUrls;

    @Autowired
    private PropertiesUtil domainConfig;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //过滤不拦截的请求
        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
        if ("/".equals(requestUrl)) return true;
        boolean isAjaxReq = isAjax(request);
        logger.info("requestUrl=" + requestUrl + ",isAjaxReq=" + isAjaxReq);
        //当请求是冰穹同步数据请求时不做拦截
        if(Constants.DOME_DEFAULT_MERCHAT_VALUE.equals(request.getParameter(Constants.DOME_DEFAULT_MERCHAT_FLAG)))
        {
        	return true;
        }
        for (String allowUrl : allowUrls) {
            if (requestUrl.contains(allowUrl)) {
                logger.info("AuthInterceptor.result=" + requestUrl.contains(allowUrl));
                return true;
            }
        }
        try {
        	// 两种情况需要重新登录  1、关闭浏览器或注销、修改密码等会删除cookie 2、redis中缓存的accessToken已失效
        	boolean needLogin = false;
            String cookieVal = ServletUtil.getCookieValue(request, "dome_access_token");
            String cacheTokenVal= null;
            if (StringUtils.isEmpty(cookieVal)){
            	
            	needLogin = true;
            } else {
            	cacheTokenVal = redisUtil.get(DomeSdkRedisKey.APP_USER_LOGIN_TOKEN + cookieVal);
            	String userInfoText = redisUtil.get(DomeSdkRedisKey.APP_USER_LOGIN_USERINFO + cookieVal);
            	if (StringUtils.isEmpty(cacheTokenVal) || StringUtils.isEmpty(userInfoText)){
            		needLogin = true;
            	}
            }
            
            if (needLogin) {
                logger.error("登录拦截,cookieVal=" + cookieVal + ",cacheTokenVal=" + cacheTokenVal);
                outResponse(isAjaxReq, request, response);
                return false;
            }

        } catch (Exception e) {
            logger.error("AuthInterceptor.queryUserInfoUrl.error", e);
            outResponse(isAjaxReq, request, response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void setAllowUrls(List<String> allowUrls) {
        this.allowUrls = allowUrls;
    }


    /**
     * 输出响应
     *
     * @param response
     */
    private void outResponse(boolean isAjaxReq, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isAjaxReq) {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JSON.toJSONString(AjaxResult.accessReject()));
            printWriter.flush();
            printWriter.close();
        } else {
        	String reqParam="";
        	String requestUrl = request.getRequestURI();
        	if ("/merchant/addservice.html".equals(requestUrl)){ //点击管理控制台为链接跳转，假装认为是ajax。返回1004
        		reqParam="??"+request.getRequestURL().toString();
        		String tParam=request.getParameter("t");
        		if (StringUtils.isNotEmpty(tParam)){
        			reqParam+="?t="+tParam;
        		}
	        }
            response.sendRedirect("/merchant/login.html"+reqParam);
        }
    }

    /**
     * 判断ajax请求
     *
     * @param request
     * @return
     */
    boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }

}
