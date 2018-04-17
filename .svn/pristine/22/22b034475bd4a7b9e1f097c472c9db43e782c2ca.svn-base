package com.dome.sdkserver.constants.channel;

import java.util.HashMap;
import java.util.Map;

public enum ChannelStatusEnum {
	未注册(0), 待审核(1), 商用(2), 驳回(3), 渠道暂停(4);
	
	private int code;
	private ChannelStatusEnum(int code){
		this.code=code;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	private static Map<Integer, String> channelStatusMap=new HashMap<Integer, String>();
	static{
		
		for (ChannelStatusEnum en: ChannelStatusEnum.values()){
			channelStatusMap.put(en.code, en.name());
		}
	}
	public static boolean isLeggal(int code){
		return channelStatusMap.containsKey(code);
	}
	
	public static String getStatusDesc(int code){
		return channelStatusMap.get(code);
	}
}
