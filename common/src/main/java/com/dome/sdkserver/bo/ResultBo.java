package com.dome.sdkserver.bo;

import java.util.Map;

/**
 * 返回值业务对象
 * @author hexiaoyi
 *
 */
public class ResultBo {
	private boolean isSuccess;
	
	private String code;
	
	private String msg;
	
	public ResultBo(){
		isSuccess = true;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public ResultBo conventMap(Map<String,Object> map){
		ResultBo resultBo = new ResultBo();
		resultBo.setSuccess((Boolean)map.get("isSuccess"));
		resultBo.setCode(map.get("code")+"");
		resultBo.setMsg(map.get("msg")+"");
		return resultBo;
	}
}
