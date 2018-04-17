package com.dome.sdkserver.constants;

/**
 * 分页常量
 * @author hexiaoyi
 */
public enum PageSizeEnum {
	SIZE_TEN(10,"每页10条"),
	
	APP_MERTAPP_SIZE_TEN(10,"每页10条"),
	
	SIZE_FIFTEEN(15, "每页15条");
	
	
	private Integer size;
	
	private String msg;
	
	private PageSizeEnum(Integer size, String msg) {
        this.size = size;
        this.msg = msg;
    }
	
    public static final PageSizeEnum getFromKey(String size) {
        for (PageSizeEnum e : PageSizeEnum.values()) {
            if (e.getSize().equals(size)) {
                return e;
            }
        }
        return null;
    }

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
