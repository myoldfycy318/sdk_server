package com.dome.sdkserver.bq.constants;
/**
 * 应用市场大转盘规则条件类型
 * @author liuxingyue
 *
 */
public enum RechargeTypeEnum {
	//充值规则类型：0 单笔、1 每日、2 每周、3 每月、4 时间范围
	SINGLE("0","单笔"),
	
	EVERY_DAY("1","每日"),
	
	EVERY_WEEK("2","每周"),
	
	EVERY_MONTH("3","每月"),
	
	TIME_PERIOD("4"," 时间范围");
	
	private String status;
	
	private String desc;
	
	
	private RechargeTypeEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }
	
    public static final RechargeTypeEnum getFromKey(String status) {
        for (RechargeTypeEnum e : RechargeTypeEnum.values()) {
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
