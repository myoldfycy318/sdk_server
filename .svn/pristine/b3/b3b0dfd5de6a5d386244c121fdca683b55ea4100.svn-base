package com.dome.sdkserver.constants;

/**
 *  外部应用类型
 *  
 * @author Frank.Zhou
 *
 */
public enum RunPlatformEnum {
	
	APP_TYPE_10("10","Pc"),
	
	APP_TYPE_20("20","Android"),
	
	APP_TYPE_30("30","IOS"),
	
	APP_TYPE_40("40","平板"),
	
	APP_TYPE_PC("Pc","网站"),
	
	APP_TYPE_ANDROID("Android","安卓"),
	
	APP_TYPE_IOS("IOS","IOS");
	

	private String code;
	
	private String name;
	
	
	private RunPlatformEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
	
    public static final RunPlatformEnum getFromKey(String code) {
        for (RunPlatformEnum e : RunPlatformEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
