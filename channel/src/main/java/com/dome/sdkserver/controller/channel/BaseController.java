 package com.dome.sdkserver.controller.channel;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.constants.DomeSdkRedisKey;
import com.dome.sdkserver.constants.channel.ChannelStatusEnum;
import com.dome.sdkserver.metadata.entity.channel.Channel;
import com.dome.sdkserver.service.MerchantAppService;
import com.dome.sdkserver.service.MerchantInfoService;
import com.dome.sdkserver.service.channel.ChannelService;
import com.dome.sdkserver.util.RedisUtil;
import com.dome.sdkserver.util.ServletUtil;
import com.dome.sdkserver.web.tools.channel.ReadTransValTool;
import com.dome.sdkserver.web.tools.channel.ValidateParamsTool;

public abstract class BaseController {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private MerchantInfoService merchantInfoService;
	
	@Resource
	private ReadTransValTool readTransValTool;

	@Resource
	private ValidateParamsTool validateParamsTool;
	
	@Resource
	private MerchantAppService merchantAppServiceImpl;

//	private static final String KEY_USER = "USER";
	
	@Resource
    private RedisUtil redisUtil;
	protected String getCurrentUsername(HttpServletRequest request) {
		User user = getLoginUser(request);
		return user.getLoginName();
	}
	
	public long getCurrentUserId(HttpServletRequest request) {
		User user = getLoginUser(request);
		return Long.parseLong(user.getUserId());
	}

	/**
	 * 参数校验
	 * @param request
	 * @param fileName
	 * @return
	 */
	protected Map<String, Object> valParam(HttpServletRequest request,String fileName){
		String xml = readTransValTool.getContentMap().get(fileName);
		Map<String, Object> resultMap = validateParamsTool.valParam(request, xml);
		return resultMap;
	}
	
	/**
	 * 得到分页起使值
	 * @param request
	 * @param fileName
	 * @return
	 */
	protected Integer getStart(String pageNum,Integer pageSize){
		int pageNumInt = 0;
		try {
			if(pageNum == null || Integer.parseInt(pageNum) <= 0){
				pageNumInt = 1;
			}else{
				pageNumInt = Integer.parseInt(pageNum);
			}
		} catch (NumberFormatException e) {
			log.error("页码参数不是数值", e);;
			pageNumInt = 1;
		}
		Integer start = (pageNumInt - 1) * pageSize;
		return start;
	}
	
	/**
	 * 商户已审核通过
	 * @return
	 */
	protected MerchantInfo getCurrentMerchant(HttpServletRequest request){
		//获取当前用户
		Long userId = this.getCurrentUserId(request);
		//log.info("用户信息id{}",userId);
		MerchantInfo merchantInfo = merchantInfoService.getMerchantInfoByUserId(userId);
		return merchantInfo;
	}
	
	/**
	 * 安全性校验
	 * 必须提供appCode且是当前登陆商户下面的应用
	 * @param appCode
	 * @return
	 */
	
	public boolean isLegalMerchant(String appCode, HttpServletRequest request){
		boolean isLegal = false;
		if (StringUtils.isEmpty(appCode)){
			log.error("计费点操作必须要提供应用编码，appCode={}", appCode);
		} else {
			MerchantAppInfo app = merchantAppServiceImpl.queryApp(appCode);
			MerchantInfo merchant = this.getCurrentMerchant(request);
			if (merchant == null || app == null || !app.getMerchantInfoId().equals(app.getMerchantInfoId())){
				log.error("用户没有登录或者没有权限操作该应用");
			} else {
				isLegal = true;
			}
			
		}
		return isLegal;
	}
	
	public void addUserInfo(MerchantAppInfo app, HttpServletRequest request){
		long userId = this.getCurrentUserId(request);
		String userName = this.getCurrentUsername(request);
		
		app.setOperUserId(userId);
		app.setOperUser(userName);
	}

	// 商户申请是否通过
	protected boolean isIllegalMerchant(MerchantInfo merchantInfo) {
		return merchantInfo.getStatus() != 2;
	}
	
	/**
	 * 
	 * session中的用户request.getSession().getAttribute("USER")
	 * 从本地cookie中取出redis key 后缀，然后从redis中取出用户信息
	 * @param request
	 * @return
	 */
	public User getLoginUser(HttpServletRequest request) {
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
					
				}
				if (StringUtils.isNotEmpty(token)){
					userInfoText = redisUtil.get(DomeSdkRedisKey.APP_USER_LOGIN_USERINFO + token);
				
				}
			}
		}
		if (StringUtils.isNotEmpty(userInfoText)){
			return JSON.parseObject(userInfoText, User.class);
		}
		log.error("发现有用户未登陆的请求，url=" +request.getRequestURI());
		throw new RuntimeException("user not login");
//		User user= new User();
//		user.setUserId("1");
//		user.setLoginName("13913942013");
//		user.setPassword("123456");
//		return user;
	}
	
	@Autowired
	private ChannelService channelService;
	
	/**
	 * 渠道商用后才会正确返回渠道id，否则会抛出异常。
	 * 渠道审批入口不能使用本方法
	 * @param request
	 * @return
	 */
	protected long getCurrChannelId(HttpServletRequest request){
		long userId=this.getCurrentUserId(request);
		Channel channel=channelService.select(0, userId);
		// 渠道商用后才需要进行其他操作
		if (channel==null || channel.getStatus()!=ChannelStatusEnum.商用.getCode()){
			throw new RuntimeException("illegal user, not channel user");
		}
		return channel.getId();
	}
	
	public boolean isLegalChannelUser(HttpServletRequest request){
		long userId=this.getCurrentUserId(request);
		Channel channel=channelService.select(0, userId);
		// 渠道商用后才需要进行其他操作
		if (channel==null || channel.getStatus()!=ChannelStatusEnum.商用.getCode()){
			return false;
		}
		return true;
	}
}
