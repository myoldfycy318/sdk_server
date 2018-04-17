package com.dome.sdkserver.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dome.sdkserver.metadata.entity.bq.pay.PayType;

public class LocalCahce{
	private static Map<String, List<PayType>> payTypesCache = new HashMap<String, List<PayType>>();
	
	private static Map<String, String> sdkVersionsCache = new HashMap<String, String>();
	
	public static List<PayType> getPayTypes(String key){
		return payTypesCache.get(key);
	}
	
	public static String getSdkVersionInfo(String key){
		return sdkVersionsCache.get(key);
	}
	
	public static void clearPayType(){
		payTypesCache.clear();
	}
	
	public static void clearSdkVersion(){
		sdkVersionsCache.clear();
	}
	
	public static void clearAll(){
		payTypesCache.clear();
		sdkVersionsCache.clear();
	}
	
	public static void addVersion(String key,String value){
		sdkVersionsCache.put(key, value);
	}
	
	public static void addPayTypes(String key,List<PayType> list){
		payTypesCache.put(key, list);
	}
}
