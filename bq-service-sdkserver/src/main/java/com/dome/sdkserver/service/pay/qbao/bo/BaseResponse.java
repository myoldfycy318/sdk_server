package com.dome.sdkserver.service.pay.qbao.bo;

/**
 * 
 * 描述：
 * 返回信息
 * @author hexiaoyi
 */
public class BaseResponse {
	private boolean isSuccess;
	
	private String code;
	
	private String msg;
	
	public BaseResponse(){}
	
	public BaseResponse(boolean isSuccess){
		this.isSuccess = isSuccess;
	}
	
	public BaseResponse(boolean isSuccess,String code,String msg){
		this.isSuccess = isSuccess;
		this.code = code;
		this.msg = msg;
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
}
