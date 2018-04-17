package com.dome.sdkserver.interceptors;

import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.web.tools.ReadTransValTool;
import com.dome.sdkserver.web.tools.ValidateParamsTool;

/**
 * 
 * 描述：
 * 
 * @author hexiaoyi
 */
public class TransParamValInterceptor implements HandlerInterceptor  {
	
	@Resource
	private ReadTransValTool readTransValTool;
	
	@Resource
	private ValidateParamsTool validateParamsTool;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		String xml = readTransValTool.getContentMap().get("trans"+request.getParameter("transType"));
		Map<String, Object> resultMap = validateParamsTool.valParam(request, xml);
		Boolean isSuccess = (Boolean)resultMap.get("isSuccess");
		if(isSuccess){
			return true;
		}else{
			PrintWriter out = response.getWriter();
			out.write(JSON.toJSON(resultMap).toString());  
			out.flush();  
			out.close(); 
		}
		return false;
	}

}
