package com.dome.sdkserver.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bo.AppTypeAttrInfo;
import com.dome.sdkserver.constants.DomeSdkRedisKey;
import com.dome.sdkserver.metadata.dao.mapper.AppTypeAttrMapper;
import com.dome.sdkserver.service.RedisService;
import com.dome.sdkserver.util.RedisUtil;

@Service
public class RedisServiceImpl implements RedisService {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private AppTypeAttrMapper appTypeAttrMapper;
	
	@Resource
	private RedisUtil redisUtil;
	
	@Override
	public void deleteRedisKey(String key) {
		String redisKey = "domesdk:" + key;
		if (DomeSdkRedisKey.MERCHANT_PREFIX.equals(redisKey)|| DomeSdkRedisKey.APP_TYPE_LIST_PREFIX.equals(redisKey)
				|| DomeSdkRedisKey.APP_TYPE_CODE_PREFIX.equals(redisKey)|| DomeSdkRedisKey.APP_TYPE_ID_PREFIX.equals(redisKey)
				|| DomeSdkRedisKey.APP_PREFIX.equals(redisKey)){
			log.info("批量删除redis，key={}", redisKey);
			redisUtil.delPatternKey(redisKey + "*");
		}

	}
	
	/**
	 * 应用类型很少有变动，故做了一个redis缓存
	 */
	@Override
	public AppTypeAttrInfo getAppTypeAttrByCode(String appTypeCode) {
		String key = DomeSdkRedisKey.APP_TYPE_CODE_PREFIX + appTypeCode;
		String value = redisUtil.get(key);
		AppTypeAttrInfo appType = null;
		if (StringUtils.isEmpty(value)) {
			appType = appTypeAttrMapper.getAppTypeAttrByCode(appTypeCode);
			if (appType != null) {
				redisUtil.setex(key, 24 * 60 * 60, JSON.toJSONString(appType));
			}
		} else {
			appType = JSON.parseObject(value, AppTypeAttrInfo.class);
		}
		return appType;
	}

}
