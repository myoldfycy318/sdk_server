/**
 * 
 */
package com.dome.sdkserver.bq.constants;

/**
 * @author Administrator
 *
 */
public enum SendBqStatusEnum {

	
	AUTO_SEND_NO("10","未同步宝券系统"),
	
	AUTO_SEND_SUCCESS("20", "宝券发放成功"),
	
	AUTO_SEND_FAIL("30", "宝券发放失败"),
	
	AUTO_SEND_ERROR("40", "系统异常");
	
	private String status;
	
	private String desc;
	
	
	private SendBqStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }
	
    public static final SendBqStatusEnum getFromKey(String status) {
        for (SendBqStatusEnum e : SendBqStatusEnum.values()) {
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
