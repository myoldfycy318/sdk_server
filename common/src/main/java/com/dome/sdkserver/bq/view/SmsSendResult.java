/**
 * 
 */
package com.dome.sdkserver.bq.view;

/**
 * @author mazhongmin
 * 短信发放结果
 */
public class SmsSendResult {
	
	//发送状态
	private String status;
	
	//短信发放描述
	private String desc;
	
	//短信验证码
	private String verifyCode;
	
	public SmsSendResult(String status,String desc){
		this.status = status;
		this.desc = desc;
	}
	
	public SmsSendResult(String status,String desc,String verifyCode){
		this.status = status;
		this.desc = desc;
		this.verifyCode = verifyCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
