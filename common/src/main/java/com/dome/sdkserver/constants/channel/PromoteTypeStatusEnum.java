package com.dome.sdkserver.constants.channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 推广分类状态
 * @author lilongwei
 *
 */
public enum PromoteTypeStatusEnum {
	启用(1),暂停(2);
	
	private int status;
	private PromoteTypeStatusEnum(int status){
		this.status=status;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private static Map<Integer, String> typeConfigStatusMap=new HashMap<Integer, String>();
	static{
		
		for (PromoteTypeStatusEnum en: PromoteTypeStatusEnum.values()){
			typeConfigStatusMap.put(en.status, en.name());
		}
	}
	public static boolean isLeggal(int code){
		return typeConfigStatusMap.containsKey(code);
	}
	
	public static String getStatusDesc(int status){
		return typeConfigStatusMap.get(status);
	}
}
