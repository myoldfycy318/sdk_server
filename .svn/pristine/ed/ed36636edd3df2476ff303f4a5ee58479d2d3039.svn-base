package com.dome.sdkserver.service.redis;

/**
 * @author mazhongmin 
 * redis缓存中获取信息,redis中没有,则DB补偿
 * 1.根据应用编码获取应用信息
 * 2.获取全局配置信息
 */
public interface RedisService {
	
	//全局配置信息redis key
	static final String GLOBAL_VAR = "sdkServer:GLOBAL_VAR_";
	
	//获取应用信息key前缀
	static final String APPINFO_PREFIX = "sdkServer:appinfo_";
	
/*	*//**
	 * 获取app信息
	 * @param appCode 应用编码
	 * @return
	 *//*
	AppInfoEntity getAppInfo(String appCode);*/
	
	/**
	 * 获取全局配置信息
	 * @return
	 */
	String getGlobalVarByType(String varType);

    boolean refreshAppInfo(String appCode);

}
