package com.dome.sdkserver.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.SearchMerchantInfoBo;
import com.dome.sdkserver.constants.AuditStatusEnum;
import com.dome.sdkserver.constants.PageSizeEnum;
import com.dome.sdkserver.service.MerchantInfoAuditService;
import com.dome.sdkserver.util.PaginatorUtils;
import com.dome.sdkserver.view.AjaxResult;

/**
 * 商户审核controller
 */
@Controller
@RequestMapping("/merchantinfoaudit")
public class MerchantInfoAuditController extends BaseController{
	@Resource
	private MerchantInfoAuditService merchantInfoAuditService;

	/**
	 * 商户信息审核主界面
	 * @return
	 */
	@RequestMapping("/main")
	public String index() {
		return "merchantaudit/main";
	}
	
	/**
	 * 查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/list")
	@ResponseBody
	public AjaxResult merchantList(HttpServletRequest request) {
		//获取查询的参数
		String merchantFullName = request.getParameter("merchantFullName");
		String applyStartDate = request.getParameter("applyStartDate");
		String applyEndDate = request.getParameter("applyEndDate");
		String status = request.getParameter("status");
		String contacts = request.getParameter("contacts");
		String mobilePhoneNum = request.getParameter("mobilePhoneNum");
		//封装查询参数
		SearchMerchantInfoBo searchMerchantInfoBo = new SearchMerchantInfoBo(merchantFullName,applyStartDate
				,applyEndDate,status,contacts,mobilePhoneNum);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//获取总记录数
		int count = merchantInfoAuditService.getCountByCondition(searchMerchantInfoBo);
		PaginatorUtils paginatorUtils = new PaginatorUtils(PageSizeEnum.SIZE_FIFTEEN.getSize());
		int start = PaginatorUtils.getStart(paginatorUtils.executePage(request,count));
		searchMerchantInfoBo.setStart(start);
		searchMerchantInfoBo.setSize(PageSizeEnum.SIZE_FIFTEEN.getSize());
		List<MerchantInfo> merchantInfoList = merchantInfoAuditService.getMerchantInfoByCondition(searchMerchantInfoBo);
//		ModelAndView mav = new ModelAndView("merchantaudit/list");
//		mav.addObject("merchantInfoList", merchantInfoList);
		
		dataMap.put("totalCount", count);
		dataMap.put("merchantList", merchantInfoList);
		return AjaxResult.success(dataMap);
	}
	
	/**
	 * 跳转查看页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toview")
	@ResponseBody
	public AjaxResult toView(String merchantCode) {
		if (StringUtils.isEmpty(merchantCode)) {
			log.error("没有提供商户编码，merchantCode={}", merchantCode);
			return AjaxResult.failed("没有提供商户编码");
		}
		try {
			MerchantInfo merchant = merchantInfoAuditService.getMerchantInfoByCode(merchantCode);
			if (merchant == null) {
				return AjaxResult.failed("没有查询到商户");
			}
			return AjaxResult.success(merchant);
		} catch (Exception e) {
			log.error("查询商户信息出错", e);
		}
		
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 驳回
	 * @param request
	 * @return
	 */
	@RequestMapping("/doreject")
	@ResponseBody
	public AjaxResult doReject(MerchantInfo merchant) {
		if (merchant == null || StringUtils.isEmpty(merchant.getMerchantCode())){
			return AjaxResult.failed("没有提供商户编码");
		}
		if (StringUtils.isEmpty(merchant.getRemark())){
			return AjaxResult.failed("没有填写驳回理由");
		}
		if (merchant.getRemark() != null && merchant.getRemark().length() > 200){
			return AjaxResult.failed("驳回理由不能超过200个字符");
		}
		try {
			String errorMsg = validateMerchantCode(merchant.getMerchantCode(), AuditStatusEnum.AUDIT_WAIT.getStatus());
			if (errorMsg != null) {
				return AjaxResult.failed(errorMsg);
			}
			merchantInfoAuditService.doReject(merchant.getMerchantCode(), merchant.getRemark());
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("商户审批驳回申请出错", e);;
		}
			
		return AjaxResult.failed();
	}
	
	private String validateMerchantCode(String merchantCode, int status) {
		String errorMsg = null;
		MerchantInfo merchant = merchantInfoAuditService.getMerchantInfoByCode(merchantCode);
		if (merchant == null) {
			errorMsg = "没有查询到商户";
		} else {
			if (status != merchant.getStatus()) {
				errorMsg = String.format("商户状态为“%s”，不需要审批", merchant.getStatusDesc());
			}
		}
		
		return errorMsg;
	}
	
	/**
	 * 通过
	 * @param request
	 * @return
	 */
	@RequestMapping("/dopass")
	@ResponseBody
	public AjaxResult doPass(String merchantCode) {
		if (StringUtils.isEmpty(merchantCode)) {
			return AjaxResult.failed("没有提供商户编码");
		}
		String errorMsg = null;
		try{
			errorMsg = validateMerchantCode(merchantCode, AuditStatusEnum.AUDIT_WAIT.getStatus());
			if (errorMsg != null) {
				return AjaxResult.failed(errorMsg);
			}
			merchantInfoAuditService.doPass(merchantCode);
			return AjaxResult.success();
		}catch(Exception e){
			log.error("商户审批审核通过出错", e);
			errorMsg = e.getMessage();
		}
		return AjaxResult.failed(errorMsg);
	}
	
	
	/**
	 * 跳转查看页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toreject")
	public ModelAndView toReject(String merchantCode) {
		if (StringUtils.isEmpty(merchantCode)) {
			log.error("没有提供商户编码，merchantCode={}", merchantCode);
			return null;
		}
		ModelAndView mav = new ModelAndView("merchantaudit/reject");
		mav.addObject("merchantCode", merchantCode);
		return mav;
	}

}
