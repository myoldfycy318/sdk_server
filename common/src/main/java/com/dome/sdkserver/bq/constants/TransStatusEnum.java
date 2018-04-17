package com.dome.sdkserver.bq.constants;

/**
 * 
 * 交易状态
 * 
 * @author Frank.Zhou
 */
public enum TransStatusEnum {
	
	PAY_TRANS_ACCEPT_SUCCESS("10","支付受理成功"),
	
	PAY_TRANS_ACCEPT_FAILED("20", "支付受理失败"),
	
	PAY_TRANS_SUCCESS("30", "支付成功"),
	
	PAY_TRANS_FAILED("40", "支付失败"),
	
	REFUND_TRANS_ACCEPT_SUCCESS("50","退款受理成功"),
	
	REFUND_TRANS_ACCEPT_FAILED("60", "退款受理失败"),
	
	REFUND_TRANS_SUCCESS("70", "退款成功"),
	
	REFUND_TRANS_FAILED("80", "退款失败");
	
	private String status;
	
	private String desc;
	
	
	private TransStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }
	
    public static final TransStatusEnum getFromKey(String status) {
        for (TransStatusEnum e : TransStatusEnum.values()) {
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
