 package com.dome.sdkserver.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.constants.channel.ChannelStatusEnum;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;
import com.dome.sdkserver.metadata.entity.AbstractGame;
import com.dome.sdkserver.metadata.entity.channel.Channel;
import com.dome.sdkserver.security.role.CasUserDetail;
import com.dome.sdkserver.service.AppService;
import com.dome.sdkserver.service.MerchantInfoService;
import com.dome.sdkserver.service.channel.ChannelService;
import com.dome.sdkserver.service.newgame.GameService;
import com.dome.sdkserver.util.DomeSdkUtils;
import com.dome.sdkserver.util.business.GameUtils;
import com.dome.sdkserver.web.tools.ReadTransValTool;
import com.dome.sdkserver.web.tools.ValidateParamsTool;

public abstract class BaseController {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private MerchantInfoService merchantInfoService;
	
	@Resource
	private ReadTransValTool readTransValTool;

	@Resource
	private ValidateParamsTool validateParamsTool;
	
	@Resource
	protected AppService appService;

//	private static final String KEY_USER = "USER";

	protected String getCurrentUsername(HttpServletRequest request) {
		User user = getLoginUser(request);
		return user.getLoginName();
	}
	
	public long getCurrentUserId(HttpServletRequest request) {
		User user = getLoginUser(request);
		return Long.parseLong(user.getUserId());
	}
	
	/**
	 * 获取用户手机号
	 * 
	 * @return
	 * @throws DataValidateException
	 */
	protected String getCurrentUserMobile() {
		String mobile = null;
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return mobile;
		}
		Authentication authentication = context.getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof CasUserDetail) {
				mobile = ((CasUserDetail) principal).getMobilePhone();
			}
		}
		return mobile;
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
	public MerchantInfo getCurrentMerchant(HttpServletRequest request){
		//获取当前用户
		Long userId = this.getCurrentUserId(request);
		//log.info("用户信息id{}",userId);
		MerchantInfo merchantInfo = merchantInfoService.getMerchantInfoByUserId(userId);
		return merchantInfo;
	}
	@Autowired
	private GameService gameService;
	/**
	 * 安全性校验
	 * 必须提供appCode且是当前登陆商户下面的应用
	 * 方法功能强大，1、校验appCode不能为empty 2、根据登录商户信息，且要求审批通过
	 * 3、appCode表示的应用要求存在，且和登录用户和商户对应上
	 * @param appCode
	 * @return
	 */
	public boolean isLegalMerchant(String appCode, HttpServletRequest request){
		boolean isLegal = false;
		if (StringUtils.isEmpty(appCode)){
			log.error("应用编码不能为空，appCode={}", appCode);
			return isLegal;
		}
		MerchantInfo merchant = this.getCurrentMerchant(request);
		if (merchant== null || isIllegalMerchant(merchant)){ // 商户需要通过
			log.error("商户信息不存在或没有审批通过, merchant={}", (merchant!=null ? merchant.getMerchantCode():null));
			return isLegal;
		}
		// 根据应用编码第一个字符来判断游戏类型
		// h5，yeyou 每个商户只能操作其下面的应用
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		switch (em){
		case yeyougame:
		case h5game: {
			AbstractGame game=gameService.select(em.name(), appCode);
			if (game!=null && game.getUserId()==merchant.getUserId()){
				return true;
			}
			return false;
		}
		
		default:;
		}
		MerchantAppInfo app = appService.selectApp(appCode);
		if (app == null || !merchant.getMerchantInfoId().equals(app.getMerchantInfoId())){
			log.error("应用不存在或者没有权限操作该应用, merchant={}, app={}", merchant.getMerchantCode(),
				(app!=null?app.getAppCode():null));
		} else {
			isLegal = true;
		}
		
		return isLegal;
	}
	
	public void addUserInfo(MerchantAppInfo app, HttpServletRequest request){
		User user=getLoginUser(request);
		app.setOperUserId(Long.parseLong(user.getUserId()));
		app.setOperUser(user.getLoginName());
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
	protected User getLoginUser(HttpServletRequest request) {
		User user= DomeSdkUtils.getLoginUserStatistic(request);
		return user;
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
