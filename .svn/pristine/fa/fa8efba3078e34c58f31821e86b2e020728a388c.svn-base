package com.dome.sdkserver.constants.channel;

import java.util.HashMap;
import java.util.Map;

public enum CompanyTypeEnum {
	国有企业(10), 私营企业(20), 台资(30), 外企(40);
	private int code;
	private CompanyTypeEnum(int code){
		this.code=code;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	private static Map<Integer, String> companyTypeMap=new HashMap<Integer, String>();
	static{
		
		for (CompanyTypeEnum en: CompanyTypeEnum.values()){
			companyTypeMap.put(en.code, en.name());
		}
	}
	public static boolean isLeggal(int code){
		return companyTypeMap.containsKey(code);
	}
	
	public static String getDesc(int code){
		return companyTypeMap.get(code);
	}
}
