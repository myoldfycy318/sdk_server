package com.dome.sdkserver.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.constants.AuditStatusEnum;
import com.dome.sdkserver.controller.MerchantAppController;

/**
 * 应用管理页面要求用户首先是一个合法商户
 */
public class AppManageFilter implements Filter {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private FilterConfig config;
	
	public void init(FilterConfig config) throws ServletException{
		this.config = config;
	}
	
    /**
     * Default constructor. 
     */
    public AppManageFilter() {
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
		// 用户有商户权限
		// 获取applicationContext对象
		/**
		 * servlet 3.0中ServletRequest才有getServletContext()接口，Tomcat 7.0才使用servlet 3.0规范，虽引用servlet-api-3.0.jar，仍会报方法getServletContext()找不到。
		 */
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		if (context == null) return;
		MerchantAppController appController = context.getBean(MerchantAppController.class);
		if (request instanceof HttpServletRequest){
			HttpServletRequest httpRequest=(HttpServletRequest) request;
			String method = httpRequest.getMethod();
			if ("get".equalsIgnoreCase(method) || "post".equalsIgnoreCase(method)){
				MerchantInfo merchant=appController.getCurrentMerchant(httpRequest);
				boolean illegalMerchantUser=false;
				if (merchant==null||merchant.getStatus()!=AuditStatusEnum.AUDIT_PASS.getStatus()){
					illegalMerchantUser=true;
				} else {
					String appCode = request.getParameter("appCode");
					if (StringUtils.isNotEmpty(appCode) && !appController.isLegalMerchant(appCode, httpRequest)){
						log.error("没有提供参数appCode，或当前用户没有权限管理该应用，userId={}, appCode={}", appController.getCurrentUserId(httpRequest), appCode);
						illegalMerchantUser=true;
					}
				}
				
				if (illegalMerchantUser){
					request.getRequestDispatcher("/").forward(request, response);
					return;
				}
			}
		}
		
			
		chain.doFilter(request, response);
	}


}
