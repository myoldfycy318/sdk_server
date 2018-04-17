package com.dome.sdkserver.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.sdkserver.aop.AppOperLogger;
import com.dome.sdkserver.bo.ChargePointInfo;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bo.SearchChargePointBo;
import com.dome.sdkserver.bo.SearchMerchantAppBo;
import com.dome.sdkserver.constants.ChargePointStatusEnum;
import com.dome.sdkserver.service.AppService;
import com.dome.sdkserver.service.BusinessService;
import com.dome.sdkserver.service.ChargePointService;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.sdkserver.vo.MerchantAppVo;

/**
 * 计费点接入Controll
 * @author liuxingyue
 *
 */
@Controller
@RequestMapping("/chargepoint")
public class ChargePointAuditController extends BaseController{
	
	@Resource
	private ChargePointService chargePointService;
	
	@Resource
	private AppService appService;
	
    @Resource
    private BusinessService businessService;
	
	/**
	 * 查询出计费点需要审批的应用
	 * @param searchChargePointBo
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/appList")
	@ResponseBody
	public AjaxResult appList(SearchMerchantAppBo searchMerchantAppBo, Paginator paginator,
			HttpServletRequest request){
		int count = chargePointService.getAppInfoCountByCondition(searchMerchantAppBo);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<MerchantAppVo> merchantVoList = null;
		if (count > 0) {
			paginator=Paginator.handlePage(paginator, count, request);
			searchMerchantAppBo.setStart(paginator.getStart());
			searchMerchantAppBo.setSize(paginator.getPageSize());
		    List<MerchantAppInfo> merchantAppList = chargePointService.getAppInfoByCondition(searchMerchantAppBo);
		    MerchantAppVo appVo = null;
		    merchantVoList = new ArrayList<MerchantAppVo>(merchantAppList.size());
		    for (MerchantAppInfo app : merchantAppList) {
		    	appVo = new MerchantAppVo(app);
		    	merchantVoList.add(appVo);
		    }
			
		} else {
			merchantVoList = new ArrayList<MerchantAppVo>();
		}
		dataMap.put("totalCount", count);
		dataMap.put("appList", merchantVoList);
		return AjaxResult.success(dataMap);
	}
	/**
	 * 计费点审核主界面、查询
	 * 包括页游的计费点
	 * @param searchChargePointBo
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/list")
	@ResponseBody
	public AjaxResult chargePointList(SearchChargePointBo searchChargePointBo, Paginator paginator,
			HttpServletRequest request){
		String appCode = searchChargePointBo.getAppCode();
		if (StringUtils.isEmpty(appCode)) return AjaxResult.failed("appCode不能为空");
		int count = chargePointService.getChargePontCountByCondition(searchChargePointBo);
		List<ChargePointInfo> chargePointInfos=null;
		if (count>0){
			paginator=Paginator.handlePage(paginator, count, request);
			searchChargePointBo.setStart(paginator.getStart());
			searchChargePointBo.setSize(paginator.getPageSize());
			chargePointInfos = chargePointService.getChargePointInfos(searchChargePointBo);
		} else {
			chargePointInfos =Collections.emptyList();
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("totalCount", count);
		dataMap.put("chargeList", chargePointInfos);
		return AjaxResult.success(dataMap);
	}
	
	/**
	 * 驳回
	 * @param request
	 * @return
	 */
	@RequestMapping("/doreject")
	@ResponseBody
	@AppOperLogger
	public AjaxResult doReject(ChargePointInfo chargePoint, HttpServletRequest request) {
		if (chargePoint==null ||StringUtils.isEmpty(chargePoint.getAppCode())){
			return AjaxResult.failed("appCode不能为空");
		} else if (StringUtils.isEmpty(chargePoint.getChargePointCode())){
			return AjaxResult.failed("chargePointCode不能为空");
		}
		if (StringUtils.isEmpty(chargePoint.getRemark())) {
			return AjaxResult.failed("驳回理由为空");
		}
		if (chargePoint.getRemark().length() > 200){
			return AjaxResult.failed("驳回理由不能超过200个字符");
		}
		
		MerchantAppInfo app = appService.selectApp(chargePoint.getAppCode());
		String errorMsg = validateChargePoint(app, chargePoint.getChargePointCode());
		if (errorMsg != null) {
			return AjaxResult.failed(errorMsg);
		}
		ChargePointInfo ch = new ChargePointInfo();
		ch.setAppCode(chargePoint.getAppCode());
		ch.setChargePointCode(chargePoint.getChargePointCode());
		ch.setStatus(ChargePointStatusEnum.REJECT.getStatus());
		ch.setRemark(chargePoint.getRemark());
		try{
			chargePointService.rejectCp(app, ch);
			return AjaxResult.success();
		}catch(Exception e){
			log.error("驳回计费点出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 查看
	 * @param chargePointCode, String appCode
	 * @return
	 */
	@RequestMapping("/toview")
	@ResponseBody
	public AjaxResult toView(String appCode, String chargePointCode){
		if (StringUtils.isEmpty(appCode)){
			return AjaxResult.failed("appCode不能为空");
		} else if (StringUtils.isEmpty(chargePointCode)){
			return AjaxResult.failed("chargePointCode不能为空");
		}
		ChargePointInfo chargePointInfo = chargePointService.getChargePontInfoByCode(appCode, chargePointCode);
		if (chargePointInfo == null || !appCode.equals(chargePointInfo.getAppCode())) {
			return AjaxResult.failed("没有查询到计费点");
		}
		
		return AjaxResult.success(chargePointInfo);
	}
	
	/**
	 * 通过
	 * @param chargePointId
	 * @return
	 */
	@RequestMapping("/dopass")
	@ResponseBody
	@AppOperLogger
	public AjaxResult doPass(String appCode, String chargePointCode, HttpServletRequest request){
		if (StringUtils.isEmpty(appCode)){
			return AjaxResult.failed("appCode不能为空");
		} else if (StringUtils.isEmpty(chargePointCode)){
			return AjaxResult.failed("chargePointCode不能为空");
		}
		boolean flag = false;
		try {
			MerchantAppInfo app = appService.selectApp(appCode);
			String errorMsg = validateChargePoint(app, chargePointCode);
			if (errorMsg != null) {
				return AjaxResult.failed(errorMsg);
			}
			ChargePointInfo ch = new ChargePointInfo();
			ch.setChargePointCode(chargePointCode);
			flag = chargePointService.passCp(ch, app);
			if(flag){
				return AjaxResult.success();
			}	
		} catch (Exception e) {
			log.error("审批通过计费点出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	/**
	 * 批量通过
	 * @param chargePointCodes
	 * @return
	 */
	@RequestMapping("/doBatchPass")
	@ResponseBody
	@AppOperLogger
	public AjaxResult doBatchPass(String chargePointCodes, String appCode, HttpServletRequest request){
		if (StringUtils.isEmpty(appCode)){
			return AjaxResult.failed("appCode不能为空");
		} else if (StringUtils.isEmpty(chargePointCodes)){
			return AjaxResult.failed("chargePointCodes不能为空");
		}
		String[] codes = chargePointCodes.split(",");
		boolean flag = false;
		try {
			MerchantAppInfo app = appService.selectApp(appCode);
			if (app == null) {
				return AjaxResult.failed("应用没有查询到");
			}
			
			for (String code : codes) {
				ChargePointInfo ch = chargePointService.getChargePontInfoByCode(appCode, code);
				if (ch == null || !appCode.equals(ch.getAppCode())) return AjaxResult.failed("计费点不存在，chargePointCode=" + code);
			}
			for (String code : codes) {
				ChargePointInfo chargePoint = new ChargePointInfo();
				chargePoint.setChargePointCode(code);
				flag = chargePointService.passCp(chargePoint, app);
			}
			if(flag){
				return AjaxResult.success();
			}	
		} catch (Exception e) {
			log.error("批量通过计费点出错", e);
		}
		
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 若提供应用编码，导出应用的计费点，否则导出所有应用的计费点。
	 * 已失效（status=70）的不导出
	 * @param appCode
	 * @param response
	 */
    @RequestMapping("/exportChargepoint")
    public void exportBusinessInfo(String appCode, HttpServletResponse response) {
        OutputStream os = null;
        try {
            String filename = generateFileName();
            byte[] bytes = businessService.exportChargePointInfo(appCode);
            response.setContentType("application/x-msdownload");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "attachment;filename="
                    + filename);
            os = response.getOutputStream();
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            log.error("exportBusinessInfo error", e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }
    
    private static String generateFileName(){
    	String filename = "charge_" + DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss")
    			+ ".xls";
    	return filename;
    }
    
    private String validateChargePoint(MerchantAppInfo app, String chargePointCode){
    	String errorMsg = null;
		if (app== null) {
			errorMsg = "应用没有查询到";
			
		} else {
			ChargePointInfo chp = chargePointService.getChargePontInfoByCode(app.getAppCode(), chargePointCode);
			if (chp == null) {
				errorMsg = "计费点没有查询到，计费点编码为：" + chargePointCode;
			} else {
				if (ChargePointStatusEnum.WAIT_AUDIT.getStatus() != chp.getStatus()){
					ChargePointStatusEnum chpEnum = ChargePointStatusEnum.getFromKey(chp.getStatus());
					errorMsg = "计费点不允许审批，状态为：" + chpEnum == null ? "状态错误" : chpEnum.getMsg();
				} else if (app.getAppCode() != null && !app.getAppCode().equals(chp.getAppCode())) {
					errorMsg = "计费点不是应用下面的，应用编码为：" + app.getAppCode() + "，计费点所属的应用编码为：" + chp.getAppCode()
							+ "，计费点编码为：" + chargePointCode;
				}
			}
		}
		return errorMsg;
    }
    
    @ResponseBody
    @RequestMapping("/batchReject")
    @AppOperLogger
    public AjaxResult batchReject(String appCode, String chargePointCodes, String remark, HttpServletRequest request){
    	if (StringUtils.isEmpty(appCode)){
			return AjaxResult.failed("appCode不能为空");
		} else if (StringUtils.isEmpty(chargePointCodes)){
			return AjaxResult.failed("chargePointCodes不能为空");
		}
		if (StringUtils.isEmpty(remark)) {
			return AjaxResult.failed("驳回理由为空");
		}else if (remark.length() > 200){
			return AjaxResult.failed("驳回理由不能超过200个字符");
		}
		try {
			MerchantAppInfo app = appService.selectApp(appCode);
			String[] chargeCodes = chargePointCodes.split(",");
			for (String chargePointCode : chargeCodes){
				if (StringUtils.isEmpty(chargePointCode)) return AjaxResult.failed("计费点编码集不合法，" + chargePointCodes);
				String errorMsg = validateChargePoint(app, chargePointCode);
				if (errorMsg != null) {
					return AjaxResult.failed(errorMsg);
				}
			}
			for (String chargePointCode : chargeCodes){
				ChargePointInfo ch = new ChargePointInfo();
				ch.setChargePointCode(chargePointCode);
				ch.setStatus(ChargePointStatusEnum.REJECT.getStatus());
				ch.setRemark(remark);
				chargePointService.rejectCp(app, ch);
			}
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("批量驳回计费点出错", e);;
		}
    	return AjaxResult.failedSystemError();
    }
    
}
