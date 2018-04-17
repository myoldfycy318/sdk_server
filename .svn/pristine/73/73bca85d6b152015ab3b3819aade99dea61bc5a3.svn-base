package com.dome.sdkserver.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.MonthSettleBo;
import com.dome.sdkserver.bo.PayApp;
import com.dome.sdkserver.bo.SettleInfo;
import com.dome.sdkserver.bo.SettleMonthSum;
import com.dome.sdkserver.constants.RunPlatformEnum;
import com.dome.sdkserver.constants.SettleStatusEnum;
import com.dome.sdkserver.service.UserCenterService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.PropertiesUtil;

/**
 * 结算中心
 * 
 * @author Frank.Zhou
 *
 */
@Controller
@RequestMapping("/settle")
public class SettleController extends BaseController {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	UserCenterService userCenterService;
	
	@Autowired
	PropertiesUtil domainConfig;

	/**
	 * 用户中心结算主页面
	 * 
	 * @return
	 */
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request) {
		//System.out.println(getCurrentUserId());
		//获取当前登录用户的商户信息
		MerchantInfo merchantInfo = userCenterService.getMerchantInfoByUserId(Integer.valueOf(getCurrentUserId(request)+""));
		ModelAndView mav = new ModelAndView("center-jiesuan");
		List<PayApp> appList = new ArrayList<PayApp>();
		if(merchantInfo != null){
			//获取当前用户下的所有接入支付的应用
			appList = userCenterService.getPayAppByMerchantId(merchantInfo.getMerchantInfoId());
			mav.addObject("appList", appList);
		}else{
			mav.addObject("appList", appList);
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
	public Object querySettle(HttpServletRequest request) {
		//获取当前登录用户的商户信息
		MerchantInfo merchantInfo = userCenterService.getMerchantInfoByUserId(Integer.valueOf(getCurrentUserId(request)+""));
		MonthSettleBo monthSettleBo = new MonthSettleBo();
		if(merchantInfo != null){
			//获取支付应用的结算信息 
			List<SettleMonthSum> sumList = getSettleMonthSumList(request,merchantInfo.getMerchantCode());
			BigDecimal totalSumAmount = new BigDecimal(0);
			BigDecimal totalSettleAmount = new BigDecimal(0);
			BigDecimal totlePayAmount = new BigDecimal(0);
			List<SettleInfo> list = new ArrayList<SettleInfo>();
			for(SettleMonthSum settleMonthSum : sumList){
				SettleInfo settleInfo = new SettleInfo();
				if(settleMonthSum.getSettleTotalAmount() != null){
					totalSumAmount = totalSumAmount.add(settleMonthSum.getSettleTotalAmount());
				}
				if(settleMonthSum.getShouldSettleAmount() != null){
					totalSettleAmount = totalSettleAmount.add(settleMonthSum.getShouldSettleAmount());
				}
				if(settleMonthSum.getRealSettleAmount() != null){
					totlePayAmount = totlePayAmount.add(settleMonthSum.getRealSettleAmount());
				}
				
				//设置返回的结算对象
				settleInfo.setSettleMonth(settleMonthSum.getSettleMonth());
				settleInfo.setAppName(settleMonthSum.getAppName());
				settleInfo.setAppType(RunPlatformEnum.getFromKey(settleMonthSum.getAppSource()).getName());
				settleInfo.setSumAmount(settleMonthSum.getSettleTotalAmount());
				settleInfo.setSettleAmount(settleMonthSum.getShouldSettleAmount() == null?new BigDecimal(0):settleMonthSum.getShouldSettleAmount());
				settleInfo.setPayAmount(settleMonthSum.getRealSettleAmount()==null?new BigDecimal(0):settleMonthSum.getRealSettleAmount());
				
				settleInfo.setStatus(SettleStatusEnum.getFromKey(settleMonthSum.getSettleStatus()).getName());
				list.add(settleInfo);
			}
			monthSettleBo.setTotalSettleAmount(totalSettleAmount);
			monthSettleBo.setTotalSumAmount(totalSumAmount);
			monthSettleBo.setTotlePayAmount(totlePayAmount);
			monthSettleBo.setList(list);
			log.info("结算查询的返回内容:{}",JSON.toJSON(monthSettleBo));
			return JSON.toJSON(monthSettleBo);
		}else{
			return JSON.toJSON(monthSettleBo);
		}
	
	}
	
	/**
	 * 调用统一支付的月结算信息
	 * 
	 * @param request
	 * @return
	 */
	private List<SettleMonthSum> getSettleMonthSumList(HttpServletRequest request,String merchantCode){
		String settleStart = request.getParameter("settleStart");//开始结算月份
		String settleEnd = request.getParameter("settleEnd");//结算结算月份
		String appId = request.getParameter("appId");//应用名称
		String runPlatform = request.getParameter("runPlatform");//应用平台
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if(settleStart != null){
			settleStart = settleStart.replace("-", "");
		}
		if(settleEnd != null){
			settleEnd = settleEnd.replace("-", "");
		}
		pairs.add(new BasicNameValuePair("settleStart",settleStart));
		pairs.add(new BasicNameValuePair("settleEnd",settleEnd));
		pairs.add(new BasicNameValuePair("appId",appId));
		if(runPlatform != null && (!"".equals(runPlatform))){
			pairs.add(new BasicNameValuePair("runPlatform",RunPlatformEnum.getFromKey(runPlatform).getName()));
		}else{
			pairs.add(new BasicNameValuePair("runPlatform",runPlatform));
		}
		
		pairs.add(new BasicNameValuePair("merchantCode",merchantCode));
		String response = ApiConnector.post(domainConfig.getString("settle.out.query"), pairs);
		log.info("结算明细查询返回结果:{}",response);
		List<SettleMonthSum> sumList = convertMonthJsonToList(response);
		return sumList;
	}
	
	/**
	 * 月度汇总查询转换
	 * 
	 * @param response
	 * @return
	 */
	private List<SettleMonthSum> convertMonthJsonToList(String response){
		List<SettleMonthSum> list = JSON.parseArray(response, SettleMonthSum.class);
		return list;
	}
}
