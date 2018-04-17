package com.dome.sdkserver.bq.constants;

/**
 * 
 * 签名方式
 * 
 * @author Frank.Zhou
 */
public enum SignTypeEnum {
	
	SIGN_RSA("RSA","RSA签名方式"),
	
	SIGN_MD5("MD5","MD5加密方式");
	
	private String value;
	
	private String name;
	
	private SignTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }
	
    public static final SignTypeEnum getFromKey(String value) {
        for (SignTypeEnum e : SignTypeEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
