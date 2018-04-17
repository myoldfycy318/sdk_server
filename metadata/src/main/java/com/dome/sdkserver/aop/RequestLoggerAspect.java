package com.dome.sdkserver.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.constants.DomeSdkRedisKey;
import com.dome.sdkserver.metadata.dao.mapper.RequestLoggerMapper;
import com.dome.sdkserver.metadata.entity.RequestLogger;
import com.dome.sdkserver.util.DomeSdkUtils;
import com.dome.sdkserver.util.RedisUtil;
import com.dome.sdkserver.util.ServletUtil;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.sdkserver.web.util.IPUtil;
import com.dome.store.user.BaUser;
@Aspect
@Component
public class RequestLoggerAspect {

	/**
	 * 项目中root日志级别一般设置为Info，aop注入日志使用.info()
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private RedisUtil redisUtil;
	
	@Autowired
	private RequestLoggerMapper mapper;
	
	private static Set<String> queryMethodPrefixSet = new HashSet<>();
	
	static {
		queryMethodPrefixSet.add("do");
		queryMethodPrefixSet.add("save");
		queryMethodPrefixSet.add("add");
		queryMethodPrefixSet.add("edit");
		queryMethodPrefixSet.add("pass");
		queryMethodPrefixSet.add("reject");
		queryMethodPrefixSet.add("set");
		queryMethodPrefixSet.add("update");
		queryMethodPrefixSet.add("change");
		queryMethodPrefixSet.add("delete");
		queryMethodPrefixSet.add("apply");
	}
	/**
	 * 目前只处理渠道系统的请求
	 * @param jp
	 * @throws Throwable 
	 */
	@Around(value="execution(public * com.dome.sdkserver.controller.channel.*Controller.*(..))")
//	@Before(value="execution(public * com.dome..*.*Controller.*(..))")
	public Object requestLogger(ProceedingJoinPoint jp) throws Throwable{ //ProceedingJoinPoint用于around注入
		Method method = ((MethodSignature)jp.getSignature()).getMethod();
		// 仅处理public方法。测试发现public 方法method.getModifiers()返回1，而public常量为0
//		if (method.getModifiers() != Method.PUBLIC) {
//			return;
//		}
//		Class<?>[] parameterTypes = method.getParameterTypes();
		
		String methodName = method.getName();
		// 查询类请求不记录，更新提交的需要记录
		boolean needRecord = false;
		for (String methodprefix : queryMethodPrefixSet){
			if (methodName.startsWith(methodprefix)){
				needRecord = true;
				break;
			}
		}
		RequestLogger loggerObj = new RequestLogger();
		final Date date = new Date();
		if (needRecord){
			loggerObj.setReqTime(date);
			String className = method.getDeclaringClass().getSimpleName();
			loggerObj.setClassName(className);
			loggerObj.setMethodName(methodName);
			Object[] args = jp.getArgs();
			// 参数名暂无法打印，考虑到编译时没带-g等，参数名为param1，没有实际意义
			int len = args.length;
//			if (len != args.length){
//				logger.error("parameterTypes length: {}, args length: {}, both should be equal, but not", len, args.length);
//				return;
//			}
			StringBuilder argStr = new StringBuilder();
			
			for (int i=0; i<len; i++){
				Object arg = args[i];
				if (arg instanceof javax.servlet.http.HttpServletRequest){
					// 请求url和请求方法
					HttpServletRequest request = (HttpServletRequest)arg;
					argStr.append(getReqScopeParams(request));
					String ip = IPUtil.getIpAddr(request);
					loggerObj.setIp(ip);
					
					// 判断是请求来源
					String url =request.getServerName();
					int type =0;
					if (url!=null){
						if (url.equals("open.domestore.cn")){
							type=1; //open
							recordOpenUser(loggerObj, request);
						} else if (url.equals("openba.domestore.cn")){
							type=2; //openba
							recordOpenbaUser(loggerObj, request);
						} else if (url.equals("channel.domestore.cn")){
							type=3; //channel
							recordChannelUser(loggerObj, request);
						}
					}
					loggerObj.setType(type);
					
					String reqMethod = request.getMethod();
					loggerObj.setReqMethod(getReqMethodNum(reqMethod));
					String reqUri = request.getRequestURI();
					loggerObj.setReqUri(reqUri);
					String queryString = request.getQueryString();
					loggerObj.setQueryString(queryString);
					// 用户信息
					
					
				}else if (arg instanceof javax.servlet.http.HttpServletResponse){
					// response参数打印内存地址即可，无实际用处
//					sb.append(args);
				} else {
					argStr.append(JSON.toJSON(arg));
				}
				if (i != len-1){
					argStr.append(",");
				}
			}
			// args 4096 varchar
			loggerObj.setArgs(StringUtils.left(argStr.toString(), 4000));
		}
		
		try {
			Object returnObj = jp.proceed();
			if (needRecord && returnObj!= null){
				if (returnObj instanceof AjaxResult){
					AjaxResult result = (AjaxResult)returnObj;
					if (AjaxResult.isSucees(result)){
						loggerObj.setResult(1);
					} else {
						loggerObj.setResult(0);
						loggerObj.setErrorMsg(result.getErrorMsg());
					}
				}
			}
			if (needRecord) mapper.add(loggerObj, DateFormatUtils.format(date, "yyyyMM"));
			return returnObj;
		} catch (Throwable e) {
			logger.error("记录请求日志在获取请求结果出错", e);
			if (needRecord) {
				String message = e.getMessage();
				// errorMsg列为1024 varchar
				loggerObj.setErrorMsg("请求返回错:" +
						((message != null && message.length()>1000) ? message.substring(0, 1000) : message));
				mapper.add(loggerObj, DateFormatUtils.format(date, "yyyyMM"));
			}
			
			throw e;
		}
		
		
	}

	private void recordOpenbaUser(RequestLogger loggerObj,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("USER");
		if (obj !=null && obj instanceof BaUser) {
			BaUser user = (BaUser) obj;
			loggerObj.setUserId(user.getId());
			loggerObj.setUserName(user.getUsername());
		}
	}

	private void recordOpenUser(RequestLogger loggerObj,
			HttpServletRequest request) {
		User user= DomeSdkUtils.getLoginUserStatistic(request);
		loggerObj.setUserId(Integer.parseInt(user.getUserId()));
		loggerObj.setUserName(user.getLoginName());
	}
	
	private void recordChannelUser(RequestLogger loggerObj,
			HttpServletRequest request) {
		// 从open.domestore.cn过来的请求需要带上dome_access_token
		// nginx 默认不支持带下划线的头部名字
		HttpSession session = request.getSession();
		Object loginAttr = session.getAttribute("user");
		String userInfoText=null;
		if (loginAttr!=null){
			userInfoText=(String)loginAttr;
		} else {
			final String cookieName = "domeAccessToken";
			Cookie cookie = ServletUtil.getCookie(request, cookieName);
			String token;
			if (cookie != null){
				token = cookie.getValue();
				
				
			}else {
				token =request.getParameter(cookieName);
				if (StringUtils.isEmpty(token)){
					final Object attr = request.getHeader(cookieName);
					token=(attr==null?"":attr.toString());
					if (StringUtils.isNotEmpty(token)){
						userInfoText = redisUtil.get(DomeSdkRedisKey.APP_USER_LOGIN_USERINFO + token);
					
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(userInfoText)){
			User user= JSON.parseObject(userInfoText, User.class);
			loggerObj.setUserId(Integer.parseInt(user.getUserId()));
			loggerObj.setUserName(user.getLoginName());
		}
	}
	
	private int getReqMethodNum(String reqMethod){
		if ("get".equalsIgnoreCase(reqMethod)){
			return 1;
		} else if ("post".equalsIgnoreCase(reqMethod)){
			return 2;
		} else if ("head".equalsIgnoreCase(reqMethod)){
			return 3;
		} else if ("put".equalsIgnoreCase(reqMethod)){
			return 4;
		} else if ("delete".equalsIgnoreCase(reqMethod)){
			return 5;
		} else if ("options".equalsIgnoreCase(reqMethod)){
			return 6;
		} else if ("trace".equalsIgnoreCase(reqMethod)){
			return 7;
		} else if ("connect".equalsIgnoreCase(reqMethod)){
			return 8;
		}
		logger.error("请求方法无法识别，method=" +reqMethod);
		return 0;
	}
	
	private String getReqScopeParams(ServletRequest request){
		Map<String, String[]> paramMap = request.getParameterMap();
		return JSON.toJSONString(paramMap);
	}
}
