package com.dome.sdkserver.filter;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bq.constants.AuthUserFilterEnum;
import com.dome.sdkserver.bq.util.IPUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class AuthUserFilter implements Filter {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private final int ERROR_TIMES;

	private final int INTERVAL_TIME;

	private static final String FILENAME = "sdkserver-login.properties";

	private static final String PREFIX_AUTH_ERROR = "bqSdkserver:login:Error:";

	private static final String PREFIX_AUTH_ERROR_FLAG = "bqSdkserver:login:Error:Flag:";

	private final Properties props;

	private ApplicationContext ctx;
	private RedisTemplate<String, String> redisTemplate = null;

	public AuthUserFilter() {
		Resource resource = new ClassPathResource(FILENAME, this.getClass().getClassLoader());
		if (resource.exists()) {
			try {
				props = PropertiesLoaderUtils.loadProperties(resource);
			} catch (IOException e) {
				throw new IllegalArgumentException("加载{}配置文件报错！" + FILENAME);
			}
			log.info(FILENAME + "配置文件加载成功，条数：" + props.size());
		} else {
			throw new IllegalArgumentException("{}配置文件不存在！" + FILENAME);
		}
		ERROR_TIMES = Integer.parseInt(props.getProperty("user_day_login_error_times", "5"));
		INTERVAL_TIME = Integer.parseInt(props.getProperty("user_login_error_interval_time", "30"));
	}

	public AuthUserFilterEnum validator(HttpServletRequest request) {
		RedisTemplate<String, String> redisTemplate = (RedisTemplate) ctx.getBean("redisTemplate");
		String username = request.getParameter("loginName");
		String key = PREFIX_AUTH_ERROR + username.toString().trim();
		// 海外SDK需加国别码区分
		String countryCode = request.getParameter("countryCode");
		log.info("海外SDK国别码：{}", countryCode);
		if (StringUtils.isNotBlank(countryCode)) {
			key += countryCode;
		}
		log.info("登录错误记录次数key：{}", key);
		Object obj = redisTemplate.opsForHash().get(key, "flag");
		if (null == obj) {
			return AuthUserFilterEnum.校验成功;
		}
		// log.info("当前用户{}登录失败超过最大次数", username);
		return AuthUserFilterEnum.登录失败超过最大次数;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AuthUserFilterEnum afterValidator(HttpServletRequest request, HttpServletResponse response) {
		if (null == ctx) {
			return AuthUserFilterEnum.校验成功;
		}
		redisTemplate = (RedisTemplate) ctx.getBean("redisTemplate");
		String keyError = PREFIX_AUTH_ERROR + request.getParameter("loginName").trim();
		String keyErrorFlag = PREFIX_AUTH_ERROR_FLAG + request.getParameter("loginName").trim();
		// 海外SDK需加国别码区分
		String countryCode = request.getParameter("countryCode");
		log.info("海外SDK国别码：{}", countryCode);
		if (StringUtils.isNotBlank(countryCode)) {
			keyError += countryCode;
			keyErrorFlag += countryCode;
		}
		if (null == request.getAttribute("authUserFail")) {
			if (null != request.getAttribute("authUserOk")) {
				redisTemplate.delete(keyError);
				redisTemplate.delete(keyErrorFlag);
			}
			return AuthUserFilterEnum.校验成功;
		}
		Object username = request.getParameter("loginName").trim()+countryCode;
		// String key = PREFIX_AUTH_ERROR + username.toString().trim();
		long cTimes = redisTemplate.opsForHash().increment(keyError, "time", 1);
		if (1 == cTimes) {
			redisTemplate.expire(keyError, INTERVAL_TIME, TimeUnit.MINUTES);
		} else if (ERROR_TIMES <= cTimes) {
			redisTemplate.opsForHash().put(keyError, "flag", Long.toString(cTimes));

		}
		log.info("用户ip:{},当前用户{}登录错误次数{}", IPUtil.getIpAddr(request), username, cTimes);

		return AuthUserFilterEnum.校验成功;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		AuthUserFilterEnum filterEnum = validator((HttpServletRequest) request);
		if (filterEnum == AuthUserFilterEnum.停止过滤器) {
			return;
		}
		if (filterEnum != AuthUserFilterEnum.校验成功) {
			sendErrorMsg(filterEnum, (HttpServletResponse) response);
			return;
		}
		chain.doFilter(request, response);

		filterEnum = afterValidator((HttpServletRequest) request, (HttpServletResponse) response);

		if (filterEnum != AuthUserFilterEnum.校验成功) {
			sendErrorMsg(filterEnum, (HttpServletResponse) response);
			return;
		}
	}

	private void sendErrorMsg(AuthUserFilterEnum filterEnum, HttpServletResponse response) {
		HttpServletResponse _res = (HttpServletResponse) response;
		_res.setCharacterEncoding("utf-8");
		_res.setHeader("Content-type", "text/html;charset=UTF-8");

		PrintWriter out = null;
		try {
			out = _res.getWriter();
			out.write(JSON.toJSONString(SdkOauthResult.failed(filterEnum.name())));
			out.flush();
		} catch (Exception e) {
			log.error("统一支付过滤器返回消息异常," + e.getMessage(), e);
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		}
		redisTemplate = (RedisTemplate) ctx.getBean("redisTemplate");
	}
}
