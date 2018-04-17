package com.dome.sdkserver.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 接入的计费点状态
 * @author qianbao
 *
 */
public enum ChargePointStatusEnum {
	
	WAIT_AUDIT(10,"待审核"),
	
	ENABLED(20,"已生效"),
	
	REJECT(30,"已驳回"),
	
	DRAFT(40,"草稿"),
	@Deprecated
	CHANGE_APPLY(50,"变更申请"),
	@Deprecated
	CHANGE_REJECTED(60,"变更驳回"),
	
	DISABLE(70,"已失效");
	
	private int status;
	
	private String msg;

	private ChargePointStatusEnum(int status,String msg){
		this.status = status;
		this.msg = msg;
		
	}
	
	public static final ChargePointStatusEnum getFromKey(int status) {
	    
	    return cpStatusMap.get(status);
	}
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	private static Map<Integer, ChargePointStatusEnum> cpStatusMap=new HashMap<Integer, ChargePointStatusEnum>();
	static{
		
		for (ChargePointStatusEnum en: ChargePointStatusEnum.values()){
			cpStatusMap.put(en.status, en);
		}
	}
	
	public static String getStatusDesc(int status){
		ChargePointStatusEnum en=cpStatusMap.get(status);
		return en!=null ?en.getMsg():null;
	}

}
