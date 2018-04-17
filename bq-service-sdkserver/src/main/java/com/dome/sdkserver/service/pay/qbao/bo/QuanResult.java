/**
 * 
 */
package com.dome.sdkserver.service.pay.qbao.bo;

/**
 * @author mazhongmin
 * 宝券扣减返回结果类
 */
public class QuanResult {
	
	//返回码
	private String code;
	
	//返回信息
	private String message;
	
	//返回值
	private QuanResultData data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public QuanResultData getData() {
		return data;
	}

	public void setData(QuanResultData data) {
		this.data = data;
	}
	
}
