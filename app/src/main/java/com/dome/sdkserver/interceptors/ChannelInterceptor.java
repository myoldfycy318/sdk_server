package com.dome.sdkserver.interceptors;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.controller.channel.ChannelController;
import com.dome.sdkserver.util.WebUtils;
import com.dome.sdkserver.view.AjaxResult;

public class ChannelInterceptor implements HandlerInterceptor{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static ChannelController controller;
	
//	private static Set<String> whiteInterfaceSet = new HashSet<>();
//	
//	static {
//		whiteInterfaceSet.add("/channel/query");
//		whiteInterfaceSet.add("/channel/channelmanage-subapply.html");
//		whiteInterfaceSet.add("/channel/channelmanage-reviewing.html");
//		whiteInterfaceSet.add("/channel/channelmanage-notrougth.html");
//		whiteInterfaceSet.add("/channel/save");
//
//	}
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (controller==null){
			HttpSession session =request.getSession();
			
			ApplicationContext context =WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
			controller = context.getBean(ChannelController.class);
		}
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String url = httpRequest.getRequestURI();
//		if (!whiteInterfaceSet.contains(url)){
			if (!controller.isLegalChannelUser(httpRequest)){
				log.error("发现有非法访问渠道页面的请求，url:" +url);
				if (WebUtils.isAjax(httpRequest)){
					PrintWriter printWriter = response.getWriter();
		            printWriter.write(JSON.toJSONString(AjaxResult.accessReject()));
		            printWriter.flush();
		            printWriter.close();
				} else {
					response.sendRedirect("/app/index");
				}
				
				return false;
			}
//		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
