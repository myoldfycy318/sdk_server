package com.dome.sdkserver.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chenwei on 2016/3/18
 */
public class AccessAllowOriginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,X-CSRF-TOKEN,dtoken,domeAccessToken");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        // response.setHeader("Access-Control-Max-Age", "3600"); // 浏览器缓存 304
        // 去掉浏览器缓存
         
        response.setHeader("Pragma","no-cache"); 
        response.setDateHeader("Expires",0); 
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache","no-cache");
//        if (servletRequest instanceof HttpServletRequest){
//        	HttpServletRequest httpRequest=(HttpServletRequest)servletRequest;
//        	Enumeration<String> headers=httpRequest.getHeaderNames();
//        	while(headers.hasMoreElements()){
//        		String name=headers.nextElement();
//        		System.out.println(name+":"+httpRequest.getHeader(name));
//        	}
//        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
