package com.dome.sdkserver.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.sdkserver.bo.AppTypeAttrInfo;
import com.dome.sdkserver.service.AppTypeAttrService;
import com.dome.sdkserver.view.AjaxResult;

@Controller
@RequestMapping("/apptypeattr")
public class AppTypeAttrController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private AppTypeAttrService appTypeAttrService;
	
	/**
	 * 根据类型属性父编码查找应用类型信息
	 * @param typeAttrParentCode
	 * @return
	 */
	@RequestMapping("/appTypeAttrList")
	@ResponseBody
	public AjaxResult appTypeAttrList(String typeAttrParentCode){
		// 没有提供父类型，默认查询一级应用类型
		if (StringUtils.isEmpty(typeAttrParentCode)){
			typeAttrParentCode = "0";
		}
		try {
			List<AppTypeAttrInfo> appTypeAttrInfos = appTypeAttrService.getAppTypeAttrList(typeAttrParentCode);
			
			return AjaxResult.success(appTypeAttrInfos);
		}catch(Exception e){
			log.error("获取应用类型列表出错", e);
		}
		return AjaxResult.failedSystemError();
	}
}
