package com.dome.sdkserver.biz.enums;

public enum OrderStatusEnum {
	
	orderstatus_no_pay(0),
	
	orderstatus_pay_sucess(1),
	
	orderstatus_asyncnotice_faild(3),
	
	orderstatus_pay_faild(2);
	
	public final int code;
	
	private OrderStatusEnum(int code){
		this.code = code;
	}
}
