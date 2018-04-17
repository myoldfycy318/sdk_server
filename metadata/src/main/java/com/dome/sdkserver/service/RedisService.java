package com.dome.sdkserver.service;

import com.dome.sdkserver.bo.AppTypeAttrInfo;

public interface RedisService {

	void deleteRedisKey(String key);
	
	AppTypeAttrInfo getAppTypeAttrByCode(String appTypeCode);
}
