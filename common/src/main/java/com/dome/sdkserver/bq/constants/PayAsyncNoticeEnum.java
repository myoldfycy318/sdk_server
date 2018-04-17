package com.dome.sdkserver.bq.constants;

/**
 * 支付异步通知发送返回类型
 * @author liuxingyue
 *
 */
public enum PayAsyncNoticeEnum {

	
	SEND_SUCCESS("10","发送成功"),
	
	IN_SENDING("20", "发送中"),
	
	MER_RES_FAIL("30", "商家返回码错误"),
	
	NET_UNUSUAL("40", "网络异常");
	
	private String status;
	
	private String desc;
	
	
	private PayAsyncNoticeEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }
	
    public static final PayAsyncNoticeEnum getFromKey(String status) {
        for (PayAsyncNoticeEnum e : PayAsyncNoticeEnum.values()) {
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
