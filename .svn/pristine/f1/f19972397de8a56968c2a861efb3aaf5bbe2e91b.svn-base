package com.dome.sdkserver.constants.channel;

import java.util.HashMap;
import java.util.Map;

public enum CooperTypeEnum {
	CPA(1), CPS(2);
	private int code;
	private CooperTypeEnum(int code){
		this.code=code;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	private static Map<Integer, String> cooperTypeMap=new HashMap<Integer, String>();
	static{
		
		for (CooperTypeEnum en: CooperTypeEnum.values()){
			cooperTypeMap.put(en.code, en.name());
		}
	}
	public static boolean isLeggal(int code){
		return cooperTypeMap.containsKey(code);
	}
	
	public static String getDesc(int code)
	{
	    return cooperTypeMap.get(code);
	}
}
