package com.dome.sdkserver.constants.channel;

import java.util.HashMap;
import java.util.Map;

public enum JsStatusEnum {
	已结算(1), 申请中(2), 驳回(3);
	
	private int status;
	private JsStatusEnum(int status){
		this.status=status;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private static Map<Integer, String> jsStatusMap=new HashMap<Integer, String>();
	static{
		
		for (JsStatusEnum en: JsStatusEnum.values()){
			jsStatusMap.put(en.status, en.name());
		}
	}
	public static boolean isLeggal(int code){
		return jsStatusMap.containsKey(code);
	}
	
	public static String getStatusDesc(int status){
		return jsStatusMap.get(status);
	}
}
