package com.dome.sdkserver.constants;


/**
 * 
 * 应用渠道
 * 
 * @author Frank.Zhou
 */
public enum AppSourceEnum {
	
	SOURCE_PC(1, "Pc"),
	
	SOURCE_ANDROID(2, "Android"),
	
	SOURCE_IOS(0, "IOS");
	
	private int sourceCode;
	
	private String sourceName;
	
	
	private AppSourceEnum(int sourceCode, String sourceName) {
        this.sourceCode = sourceCode;
        this.sourceName = sourceName;
    }
	
    public static final AppSourceEnum getFromKey(int sourceCode) {
        for (AppSourceEnum e : AppSourceEnum.values()) {
            if (e.getSourceCode() == sourceCode) {
                return e;
            }
        }
        return null;
    }

	public int getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(int sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

}
