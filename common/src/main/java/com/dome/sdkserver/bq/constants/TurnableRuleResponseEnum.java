package com.dome.sdkserver.bq.constants;
/**
 * 应用市场大转盘规则返回码
 * @author liuxingyue
 *
 */
public enum TurnableRuleResponseEnum {
	GET_TURNTABLE_RULE_SUCCESS("82000","操作成功"),
	
	TURNTABLE_RULE_DEL_ERROR("82001","规则删除异常"),
	
	TURNTABLE_RULE_UPDATE_ERROR("82003","规则添加/更新异常"),
	
	TURNTABLE_RULE_PARAM_ILLEGAL("82005","参数校验不合法"),
	
	IP_ERROR("82004","IP不合法")
	;
	
	private String code;
	
	private String msg;
	
	private TurnableRuleResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
	
    public static final TurnableRuleResponseEnum getFromKey(String code) {
        for (TurnableRuleResponseEnum e : TurnableRuleResponseEnum.values()) {
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


}
