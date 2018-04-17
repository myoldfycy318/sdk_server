package com.dome.sdkserver.constants;

/**
 * 
 * 描述：结算状态
 * 
 * @author Frank.Zhou
 */
public enum SettleStatusEnum {
	
	WAIT_SETTLE("10","待结算"),
	
	WAIT_EFFECT("20","待生效"),
	
	EFFECT("30","生效");
	
	private String value;
	
	private String name;
	
	
	private SettleStatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }
	
    public static final SettleStatusEnum getFromKey(String value) {
        for (SettleStatusEnum e : SettleStatusEnum.values()) {
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
