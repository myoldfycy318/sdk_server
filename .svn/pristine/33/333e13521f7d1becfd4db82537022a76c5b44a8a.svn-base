package com.dome.sdkserver.constants;

/**
 * 审核状态
 * @author hexiaoyi
 */
public enum AuditStatusEnum {
	AUDIT_WAIT(1,"待审核"),
	
	AUDIT_PASS(2,"已通过"),
	
	AUDIT_REJECT(3,"驳回");
	
	private Integer status;
	
	private String msg;
	
	private AuditStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
	
    public static final AuditStatusEnum getFromKey(int status) {
        for (AuditStatusEnum e : AuditStatusEnum.values()) {
            if (e.getStatus() == status) {
                return e;
            }
        }
        return null;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
