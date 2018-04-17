package com.dome.sdkserver.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dome.sdkserver.service.RedisService;
import com.dome.sdkserver.service.notify.NotifyService;
import com.dome.sdkserver.view.AjaxResult;

@Controller
@RequestMapping("/redis")
public class RedisMgrController extends BaseController {

	@Resource
	private RedisService redisServiceImpl;
	
	@Resource
	private NotifyService notifyService;
	
	@RequestMapping("/deleteKey")
	public AjaxResult delMerchantRedisCach(String key){
		if (StringUtils.isEmpty(key)) {
			return AjaxResult.failed("提供的参数不完整");
		}
		try {
			redisServiceImpl.deleteRedisKey(key);
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("删除redis出错，key=" + key, e);;
		}
		return AjaxResult.failed("删除redis出错");
	}
	
}
