package com.dome.sdkserver.bq.constants;
/**
 * 应用市场大转盘规则条件类型
 * @author liuxingyue
 *
 */
public enum RechargeConditionEnum {
	//条件类型：0 >=、1 =、2 >.
	GREATER_THAN_OR_EQUAL_TO("0",">="),
	
	EQUAL_TO("1","="),
	
	GREATER_THAN("2",">");
	
	private String status;
	
	private String desc;
	
	
	private RechargeConditionEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }
	
    public static final RechargeConditionEnum getFromKey(String status) {
        for (RechargeConditionEnum e : RechargeConditionEnum.values()) {
            if (e.getStatus().equals(status)) {
                return e;
            }
        }
        return null;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
