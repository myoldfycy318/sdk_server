package com.dome.sdkserver.constants;

/**
 * 资源栏目
 * @author hexiaoyi
 *
 */
public enum ResourceColumnEnum {
	
	OAUTH_COLUMN("10","联合登录"),
	
	PAY_COLUMN("20","统一支付");

	private String columnCode;
	
	private String columnName;
	
	
	private ResourceColumnEnum(String columnCode, String columnName) {
        this.columnCode = columnCode;
        this.columnName = columnName;
    }
	
    public static final ResourceColumnEnum getFromKey(String columnCode) {
        for (ResourceColumnEnum e : ResourceColumnEnum.values()) {
            if (e.getColumnCode().equals(columnCode)) {
                return e;
            }
        }
        return null;
    }

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
