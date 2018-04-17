package com.dome.sdkserver.bq.constants;

public enum TransTypeEnum {

	
	TRANS_TYPE_GRANT("1010","授权"),
	
	TRANS_TYPE_PAY("2010","支付"),
	
	TRANS_TYPE_REFUND("2011","退款"),
	
	TRANS_TYPE_TRUST("2020","信任支付"),
	
	TRANS_TYPE_SIGNLE_QUERY("3011","单笔查询"),
	
	TRANS_TYPE_BATCH_QUERY("3010","批量查询"),
	
	TRANS_TYPE_SDK_SERVER("2012","SDK支付")
	;
	
	private String transCode;
	
	private String transType;
	
	
	private TransTypeEnum(String transCode, String transType) {
        this.transCode = transCode;
        this.transType = transType;
    }
	
    public static final TransTypeEnum getFromKey(String transCode) {
        for (TransTypeEnum e : TransTypeEnum.values()) {
            if (e.transCode.equals(transCode)) {
                return e;
            }
        }
        return null;
    }

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
    

}
