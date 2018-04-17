package com.dome.sdkserver.constants;

/**
 * 业务错误信息枚举
 * @author hexiaoyi
 */
public enum BizResponseEnum {
	
	USER_MERCHANT_EXIST("70001","该用户已注册过商户"),
	
	USER_MERCHANT_NOT_EXIST("70002","该用户未注册商户"),
	
	APP_STATUS_ERROR("70003","应用状态异常"),
	
	USER_NOT_LOGIN("70004","用户未登录"),
	
	MERCHANTAPP_NOT_EXIST("70005","没有找到该应用"),
	
	MERCHANT_NOT_PASS("70006","商户没有审核通过"),
	
	MERCHANTAPP_STATUS_ERROR("70007","应用状态异常");
	
	private String responeCode;
	
	private String responeMsg;
	
	private BizResponseEnum(String responeCode, String responeMsg) {
        this.responeCode = responeCode;
        this.responeMsg = responeMsg;
    }
	
    public static final BizResponseEnum getFromKey(String responeCode) {
        for (BizResponseEnum e : BizResponseEnum.values()) {
            if (e.getResponeCode().equals(responeCode)) {
                return e;
            }
        }
        return null;
    }

	public String getResponeCode() {
		return responeCode;
	}

	public void setResponeCode(String responeCode) {
		this.responeCode = responeCode;
	}

	public String getResponeMsg() {
		return responeMsg;
	}

	public void setResponeMsg(String responeMsg) {
		this.responeMsg = responeMsg;
	}
}
