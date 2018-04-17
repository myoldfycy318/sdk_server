package com.dome.sdkserver.constants;

public enum AppChannelEnum {

	qb ("CHA000001", "宝玩应用商店"), baowan("CHA000002", "冰趣应用商店");
	
	private String type;
	
	private String descr;
	
	private AppChannelEnum(String type, String descr) {
		this.type = type;
		this.descr = descr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public static final AppChannelEnum getFromKey(String type) {
        for (AppChannelEnum e : AppChannelEnum.values()) {
            if (e.getType().equals(type)) {
                return e;
            }
        }
        return null;
    }
}
