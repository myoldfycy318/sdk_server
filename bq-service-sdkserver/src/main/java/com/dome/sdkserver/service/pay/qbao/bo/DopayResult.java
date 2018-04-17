/**
 * 
 */
package com.dome.sdkserver.service.pay.qbao.bo;

/**
 * @author mazhongmin
 *
 */
public class DopayResult {

	private long sdkflowId;
	
	private String orderNo;
	
	public DopayResult(long sdkflowId,String bizCode){
		this.sdkflowId = sdkflowId;
		this.orderNo = bizCode;
	}

	public long getSdkflowId() {
		return sdkflowId;
	}

	public void setSdkflowId(long sdkflowId) {
		this.sdkflowId = sdkflowId;
	}

	public String getBizCode() {
		return orderNo;
	}

	public void setBizCode(String bizCode) {
		this.orderNo = bizCode;
	}

	
}
