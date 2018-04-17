package com.dome.sdkserver.util;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.constants.AppOperEnum;
import com.dome.sdkserver.constants.DomeSdkRedisKey;
import com.dome.sdkserver.util.redis.RedisStaticUtils;

/**
 * app和ba部分处理逻辑相同，减少重复代码，抽取出来的工具类
 * 类中方法跟开放平台的业务有关系
 * @author lilongwei
 *
 */
public class DomeSdkUtils {
	
    private static RedisStaticUtils redisUtil;
    
	public static void setAppOperRecordField(MerchantAppInfo app, int status, long operUserId, String operUser){
		// 应用操作状态为其操作前状态拼接上操作后状态
		String statusDesc = AppOperEnum.getFromKey(Integer.toString(app.getStatus()) + Integer.toString(status)).getDesc();
		app.setStatusDesc(statusDesc);
		app.setOperUserId(operUserId);
		app.setOperUser(operUser);
	}

	/**
	 * 很多个地方需要获取用户登录数据，故抽取出重复代码
	 * @param request
	 * @return
	 */
	public static User getLoginUserStatistic(HttpServletRequest request){
		if(Constants.DOME_DEFAULT_MERCHAT_VALUE.equals(request.getParameter(Constants.DOME_DEFAULT_MERCHAT_FLAG)))
		{
			User user = new User();
			user.setUserId(String.valueOf(Constants.DOME_DEFAULT_MERCHANTID));
			user.setLoginName(Constants.DOME_DEFAULT_MERCHAT_VALUE);
			return user;
		}
		if (redisUtil==null) redisUtil=new RedisStaticUtils();
		Cookie cookie = ServletUtil.getCookie(request, "dome_access_token");
		if (cookie != null){
			String token = cookie.getValue();
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(token)){
				String userInfoText = redisUtil.get(DomeSdkRedisKey.APP_USER_LOGIN_USERINFO + token);
				if (org.apache.commons.lang3.StringUtils.isNotEmpty(userInfoText)){
					return JSON.parseObject(userInfoText, User.class);
				}
				
			}
			
		}
		throw new RuntimeException("user not login");
//		User user= new User();
//		user.setUserId("1");
//		user.setLoginName("13913942013");
//		user.setPassword("123456");
//		return user;
	}
}
