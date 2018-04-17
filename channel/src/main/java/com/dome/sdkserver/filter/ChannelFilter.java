package com.dome.sdkserver.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.controller.channel.ChannelController;
import com.dome.sdkserver.util.WebUtils;
import com.dome.sdkserver.view.AjaxResult;

/**
 * Servlet Filter implementation class ChannelFilter
 */
public class ChannelFilter implements Filter {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static ChannelController controller;
    /**
     * Default constructor. 
     */
    public ChannelFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		if (request instanceof HttpServletRequest ){
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			String method = httpRequest.getMethod();
			if ("get".equalsIgnoreCase(method)||"post".equalsIgnoreCase(method)){
				HttpSession session =httpRequest.getSession();
				// 访问渠道系统需要先去开放平台登录，两个系统共用浏览器的cookie数据
				boolean illegalUser = true;
				Object loginAttr = session.getAttribute("user");
				if (loginAttr==null){
					if (controller==null){
						ApplicationContext context =WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
						controller = context.getBean(ChannelController.class);
					}
					try {
						User user=controller.getLoginUser(httpRequest);
						if (user!=null) {
							// 获取用户数据，避免每次访问都需要带token
							session.setAttribute("user", JSON.toJSONString(user));
							illegalUser=false;
						}
					} catch (Exception e) {
						log.error("获取登录用户信息出错, url=" +httpRequest.getRequestURI(), e);
					}
					
				}
				if (loginAttr==null && illegalUser) {
					//ajax请求，不管ajax还是地址栏跳转
//					if (WebUtils.isAjax(httpRequest)){
						PrintWriter printWriter = response.getWriter();
			            printWriter.write(JSON.toJSONString(AjaxResult.accessReject()));
			            printWriter.flush();
			            printWriter.close();
//					} else {
//						((HttpServletResponse)response).sendRedirect("http://open.domestore.cn");
//					}
					return;
				}
			}
			
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
