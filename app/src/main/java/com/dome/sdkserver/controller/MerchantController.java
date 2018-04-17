package com.dome.sdkserver.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.sdkserver.aop.AppOperLogger;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.constants.AuditStatusEnum;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;
import com.dome.sdkserver.service.AppTypeAttrService;
import com.dome.sdkserver.service.MerchantAppService;
import com.dome.sdkserver.service.MerchantInfoService;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.sdkserver.web.util.IPUtil;

/**
 * 前台商户controller
 * @author hexiaoyi
 *
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController extends BaseController{
	
	@Resource
	private MerchantInfoService merchantInfoService;

	@Resource
	private MerchantAppService merchantAppService;
	
	@Resource
	private AppTypeAttrService appTypeAttrService;
	
	private static final String APPTYPE_ATTRCODE_LEVEL_1 = "0";
	
	/**
	 * 商户审批流程页面跳转
	 * @return
	 */
	@RequestMapping("/addservice")
	public void addService(HttpServletRequest req, HttpServletResponse resp) {
		MerchantInfo merchantInfo = this.getCurrentMerchant(req);
		String url = null;
		if(merchantInfo == null){
			url = "/merchant/index.html";
		}else if(AuditStatusEnum.AUDIT_WAIT.getStatus().equals(merchantInfo.getStatus())){
			url = "/merchant/waiting.html";
		}else if(AuditStatusEnum.AUDIT_PASS.getStatus().equals(merchantInfo.getStatus())){
			String t=req.getParameter("t");
			if (StringUtils.isNotEmpty(t)){
				if (GameTypeEnum.h5game.name().equals(t)){
					url = "/merchant/h5/appList.html";
				} else if (GameTypeEnum.yeyougame.name().equals(t)){
					url = "/merchant/webgame/appList.html";
				}
			}
			if (url==null){
				Integer totalCount = merchantAppService.getAppCountByMertId(merchantInfo.getMerchantInfoId());
				if(totalCount == 0){//如果没有应用，跳转成功页面，如果有应用，跳转应用列表
					url = "/merchant/review.html";
				}else{
					url = "/merchant/appList.html";
				}
			}
			
		}else if(AuditStatusEnum.AUDIT_REJECT.getStatus().equals(merchantInfo.getStatus())){
			url = "/merchant/review.html";
		}
		try {
			if (url == null) url = "/app/index";
			//req.getRequestDispatcher(url).forward(req, resp);
			resp.sendRedirect(url);
		} catch (Exception e) {
			log.error("管理控制台页面重定向出错，url=" + url, e);
		}
	}
	
	/**
	 * 商户申请
	 * @param request
	 * @return
	 */
	@RequestMapping("/merchantapply")
	public void merchantApply(HttpServletRequest request, HttpServletResponse response) {
		String merchantRegType = request.getParameter("merchantRegType");
		merchantRegType = "1"; // 仅支持企业开发者
		if(merchantRegType == null || "".equals(merchantRegType)){
			merchantRegType = "1";
		}
		String url = "/dist/html/submit-application.html";
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			log.error("重定向商户申请页面出错，url=" + url, e);
		}
	}
	
	/**
	 * 注册和驳回后编辑
	 * @param request
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	@AppOperLogger
	public AjaxResult register(MerchantInfo merchantInfo, HttpServletRequest request) {
		long userId = this.getCurrentUserId(request);
		
		//参数校验不通过
		Map<String, Object> resultMap = this.valParam(request, "merchantregister");
		Boolean isSuccess = (Boolean)resultMap.get("isSuccess");
		if(!isSuccess){
			return AjaxResult.conventMap(resultMap);
		}
		// 没有提供商户注册类型，默认为企业开发者
		Object merchantRegType = merchantInfo.getMerchantRegType();
		if (merchantRegType == null || (Integer)merchantRegType == 0 || (Integer)merchantRegType != 2){
			merchantInfo.setMerchantRegType(1);
		}
//		AjaxResult result = this.validateMerchantParam(merchantInfo);
//		if (result != null) return result;
		merchantInfo.setStatus(AuditStatusEnum.AUDIT_WAIT.getStatus());
		
		try{

			MerchantInfo loginMerchant = this.getCurrentMerchant(request);
			if(loginMerchant == null){
				merchantInfo.setDelFlag(0);
				merchantInfo.setUserId(new Long(userId).intValue());
				merchantInfo.setApplyUserAccount(this.getCurrentUsername(request));
				String ip = IPUtil.getIpAddr(request);
				merchantInfo.setCreateIp(ip);
				merchantInfoService.registeMerchant(merchantInfo);
			}else{
				if(AuditStatusEnum.AUDIT_REJECT.getStatus().equals(loginMerchant.getStatus())){//只有驳回状态才能修改
					// 合作伙伴提交不能修改驳回意见
					merchantInfo.setRemark(null);
					merchantInfoService.editMerchant(merchantInfo, loginMerchant);
				}else{
					return AjaxResult.failed("商户已存在，且不允许编辑商户信息");
				}
			}
			return AjaxResult.success();
		}catch(Exception e){
			log.error("商户提交资料出错", e);;
		}
		
		return AjaxResult.failedSystemError();
	}
	
	private AjaxResult validateMerchantParam(MerchantInfo merchant){
		String errorMsg = null;
		if (StringUtils.isEmpty(merchant.getMerchantFullName()) || StringUtils.isEmpty(merchant.getMerchantUrl())
				|| StringUtils.isEmpty(merchant.getPhoneAreaNum()) || StringUtils.isEmpty(merchant.getPhoneNum())
				|| StringUtils.isEmpty(merchant.getOrgNum()) || StringUtils.isEmpty(merchant.getOrgImageUrl()) // 组织机构代码
				|| StringUtils.isEmpty(merchant.getLicenceNum()) || StringUtils.isEmpty(merchant.getLicenceImageUrl()) // 公司营业执照
				|| StringUtils.isEmpty(merchant.getLegalName()) || StringUtils.isEmpty(merchant.getLegalImageUrl()) // 法人代表姓名
				|| StringUtils.isEmpty(merchant.getCompareSoftwareNo()) || StringUtils.isEmpty(merchant.getCompareSoftwareUrl())
				|| StringUtils.isEmpty(merchant.getMerchantIntro())
				|| StringUtils.isEmpty(merchant.getContacts())
				|| StringUtils.isEmpty(merchant.getQqNum())
				|| StringUtils.isEmpty(merchant.getMobilePhoneNum())
				|| StringUtils.isEmpty(merchant.getEmail())
				){
			errorMsg = "提供的参数不完整";
		} else if (merchant.getMerchantFullName().length() > 50) {
			errorMsg = "公司名称长度不能超过50个中文字符";
		} else if (merchant.getMerchantIntro().length()>2048){
			errorMsg = "公司名称介绍不能超过2048个中文字符";
		} else if (!validateUrlParam(merchant.getMerchantUrl(), merchant.getOrgImageUrl(), merchant.getLicenceImageUrl(), merchant.getLegalImageUrl(), 
				merchant.getCompareSoftwareUrl())){
			errorMsg = "url参数非法";
		}
		if (errorMsg == null) return null;
		return AjaxResult.failed(errorMsg);
	}
	
	// 验证url地址是否合法
	private boolean validateUrlParam(String... urlParams){
		if (urlParams == null || urlParams.length == 0) return true;
		for (String urlParam: urlParams){
			if (StringUtils.isEmpty(urlParam) || !(urlParam.toLowerCase().startsWith("http://") || urlParam.toLowerCase().startsWith("https://"))){
				return false;
			}
		}
		
		return true;
	}
	@RequestMapping("/toview")
	@ResponseBody
	public AjaxResult viewMerchant(HttpServletRequest request){
		MerchantInfo merchant = this.getCurrentMerchant(request);
		if (merchant == null){
			return AjaxResult.failed("查询商户信息，或已被删除");
		}
		return AjaxResult.success(merchant);
	}
}
