package com.dome.sdkserver.aop;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.debug.DevepDebugConfig;

/**
 * 已确认不加@Component 注入不会生效
 * @author li
 *
 */
@Aspect
@Component
public class RequestAspect {

	/**
	 * 项目中root日志级别一般设置为Info，aop注入日志使用.info()
	 */
	private Logger logger = LoggerFactory.getLogger("request.logger");
	
	private static final boolean NEED_REQUEST_LOGGER = DevepDebugConfig.isDevepMode() && DevepDebugConfig.needRequestLogger();
	
	
	@Before(value="execution(public * com.dome..*.*Controller.*(..))")
	public void logReqParams(JoinPoint jp){ //ProceedingJoinPoint用于around注入
		if (!NEED_REQUEST_LOGGER) return;
		Method method = ((MethodSignature)jp.getSignature()).getMethod();
		// 仅处理public方法。测试发现public 方法method.getModifiers()返回1，而public常量为0
//		if (method.getModifiers() != Method.PUBLIC) {
//			return;
//		}
//		Class<?>[] parameterTypes = method.getParameterTypes();
		Object[] args = jp.getArgs();
		// 参数名暂无法打印，考虑到编译时没带-g等，参数名为param1，没有实际意义
		int len = args.length;
//		if (len != args.length){
//			logger.error("parameterTypes length: {}, args length: {}, both should be equal, but not", len, args.length);
//			return;
//		}
		if (len ==0) return;
		StringBuilder sb = new StringBuilder();
		
		sb.append("request method: ").append(method.toString()).append(", param values: ");
		for (int i=0; i<len; i++){
			Object arg = args[i];
			if (arg instanceof javax.servlet.http.HttpServletRequest){
				// 请求url和请求方法
				HttpServletRequest request = (HttpServletRequest)arg;
				String reqUrl = "request url: " + request.getRequestURI() + ", query string: " + request.getQueryString()
						+", method: " + request.getMethod() + ", ";
				sb.insert(0, reqUrl);
				sb.append(getReqScopeParams(request));
			}else if (arg instanceof javax.servlet.http.HttpServletResponse){
				// response参数打印内存地址即可，无实际用处
				sb.append(args);
			} else {
				sb.append(JSON.toJSON(arg));
			}
			if (i != len-1){
				sb.append(", ");
			}
		}
		logger.info(sb.toString());
	}
	
	private String getReqScopeParams(ServletRequest request){
		Map<String, String[]> paramMap = request.getParameterMap();
		return JSON.toJSONString(paramMap);
	}
}
