package com.dome.sdkserver.constants;

public enum SysEnum {

	DATABASE_ERROR("99999","数据库异常"),
	
	SYSTEM_ERROR("99998","系统异常");
	
	private String responeCode;
	
	private String responeMsg;
	
	private SysEnum(String responeCode, String responeMsg) {
        this.responeCode = responeCode;
        this.responeMsg = responeMsg;
    }
	
    public static final SysEnum getFromKey(String responeCode) {
        for (SysEnum e : SysEnum.values()) {
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
