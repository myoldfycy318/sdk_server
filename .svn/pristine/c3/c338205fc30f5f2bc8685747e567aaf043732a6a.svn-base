package com.dome.sdkserver.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.controller.ChargePointController;
import com.dome.sdkserver.util.WebUtils;
import com.dome.sdkserver.view.AjaxResult;

/**
 * 计费点操作权限控制过滤器
 * 1、appCode必填参数
 * 2、chargePointCode若有，计费点必须是应用下面的。
 * 除了查询计费点列表接口，其他接口必须传递
 * Servlet Filter implementation class CpOperFilter
 */
public class CpOperFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ChargePointController controller;
	
	private Set<String> nonneedCpCodeUriSet=new HashSet<>();
	
	{
		nonneedCpCodeUriSet.add("/chargepoint/toList");
		nonneedCpCodeUriSet.add("/chargepoint/add");
		nonneedCpCodeUriSet.add("/chargepoint/batchPost");
		nonneedCpCodeUriSet.add("/chargepoint/isAllAvailable");
		// 包体上传
		nonneedCpCodeUriSet.add("/pkgmanage/upload");
		nonneedCpCodeUriSet.add("/pkgmanage/view");
		nonneedCpCodeUriSet.add("/pkgmanage/canChange");
		nonneedCpCodeUriSet.add("/pkgmanage/view");
	}
    /**
     * Default constructor. 
     */
    public CpOperFilter() {
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
				if (controller==null){
					HttpSession session = httpRequest.getSession();
					ApplicationContext context =WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
					controller = context.getBean(ChargePointController.class);
				}
				
				try {
					AjaxResult result=null;
					String appCodeParam = httpRequest.getParameter("appCode");
					if (StringUtils.isEmpty(appCodeParam)){
						logger.error("计费点操作页面权限校验,应用编码为空, url={}, appCode={}", httpRequest.getRequestURI(), appCodeParam);
						result=AjaxResult.failed("应用编码不能为空");
					} else {
						if (!controller.isLegalMerchant(appCodeParam, httpRequest)){
							logger.error("计费点操作页面权限校验,应用操作被拒绝, url={}, appCode={}", httpRequest.getRequestURI(), appCodeParam);
							result=AjaxResult.failed("你没有权限操作改应用");
						} else {
							String cpCodeParam = httpRequest.getParameter("chargePointCode");
							if (StringUtils.isNotEmpty(cpCodeParam) && !controller.checkCp(appCodeParam, cpCodeParam)){
								logger.error("计费点操作页面权限校验,计费点操作被拒绝, url={}, appCode={}, chargePointCode={}",
										httpRequest.getRequestURI(), appCodeParam, cpCodeParam);
								result=AjaxResult.failed("计费点没有查询到");
							}
							if(StringUtils.isEmpty(cpCodeParam)){
								// 接口需要要传递计费点编码
								String requestUri=httpRequest.getRequestURI();
								if (!nonneedCpCodeUriSet.contains(requestUri)){
									logger.error("计费点操作页面权限校验,计费点编码为空, url={}, chargePointCode={}",
											httpRequest.getRequestURI(), cpCodeParam);
									result=AjaxResult.failed("计费点编码不能为空");
								}
							}
						}
					}
					if (result!=null) {
						//ajax请求，不管ajax还是地址栏跳转
						if (WebUtils.isAjax(httpRequest)){
							PrintWriter printWriter = response.getWriter();
				            printWriter.write(JSON.toJSONString(result));
				            printWriter.flush();
				            printWriter.close();
						} else {
							((HttpServletResponse)response).sendRedirect("http://open.domestore.cn/accessDenied.jsp");
						}
						return;
					}
				} catch (Exception e) {
					logger.error("计费点操作页面权限校验出错, url=" +httpRequest.getRequestURI(), e);
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
