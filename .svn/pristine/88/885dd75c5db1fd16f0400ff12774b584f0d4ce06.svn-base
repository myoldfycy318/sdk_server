package com.dome.sdkserver.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.UserApp;
import com.dome.sdkserver.bo.UserCenter;
import com.dome.sdkserver.constants.PageSizeEnum;
import com.dome.sdkserver.service.UserCenterService;

/**
 * 用户中心
 * 
 * @author Frank.Zhou
 *
 */
@Controller
@RequestMapping("/usercenter")
public class UserCenterController extends BaseController{
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	UserCenterService userCenterService;
	
	/**
	 * 获取当前用户的商户信息
	 * 
	 * @return
	 */
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request) {
		//获取当前登录用户的商户信息
		MerchantInfo merchantInfo = userCenterService.getMerchantInfoByUserId(Integer.valueOf(getCurrentUserId(request)+""));
		//获取当前用户下的所有接入支付的应用
		ModelAndView mav = new ModelAndView("center-app");
		if(null == merchantInfo){
			mav.addObject("merchantId", "");
		}else{
			mav.addObject("merchantId", merchantInfo.getMerchantCode());
		}
		
		return mav;
	}
	
	/**
	 * 获取商户的应用列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object getAppList(HttpServletRequest request) {
		//根据当前用户ID获取商户信息
		MerchantInfo merchantInfo = userCenterService.getMerchantInfoByUserId(Integer.valueOf(getCurrentUserId(request)+""));
		UserCenter userCenter = new UserCenter();
		if(merchantInfo != null){
			//获取当前商户下所有应用的总记录数
			int count = userCenterService.getAppCountByMerchantId(merchantInfo.getMerchantInfoId());
			//获取当前商户下的所有应用信息
			String pageNum = request.getParameter("pageNum");
			List<MerchantAppInfo> appList = userCenterService
					.getAppListByMerchantId(merchantInfo.getMerchantInfoId(),getStart(pageNum,PageSizeEnum.SIZE_TEN.getSize()),PageSizeEnum.SIZE_TEN.getSize()); 
			List<UserApp> userAppList = new ArrayList<UserApp>(appList.size());
			for(MerchantAppInfo merchantAppInfo : appList){
				UserApp userApp = new UserApp();
				userApp.setAppId(merchantAppInfo.getAppCode());
				userApp.setAppName(merchantAppInfo.getAppName());
				// 支付回调公钥
				String asyncPublicKey = userCenterService.getAsyncPublicKey();
				userApp.setSecretKey(asyncPublicKey);
				userApp.setPrivateKey(merchantAppInfo.getOutPrivateRsakey());
				userAppList.add(userApp);
			}
			userCenter.setCount(count);
			userCenter.setList(userAppList);
			return JSON.toJSON(userCenter);
		}else{
			List<UserApp> userAppList = new ArrayList<UserApp>();
			userCenter.setList(userAppList);
			return JSON.toJSON(userCenter);
		}

	}
	
}
