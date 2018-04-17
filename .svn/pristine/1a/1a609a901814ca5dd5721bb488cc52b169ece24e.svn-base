package com.dome.sdkserver.bq.constants;

/**
 * 
 * 业务返回码描述
 * 
 * @author Frank.Zhou
 */
public enum BusResponeEnum {
	
	REFUND_TRANS_NUM_REPEAT("70001","退款流水号重复"),
	
	ORI_TRANS_IS_NOT_EXIST("70002","无对应的原交易"),
	
	MERCHANT_ACCOUNT_NOT_EXIST("70003","商户账户未配置"),
	
	ORIAMOUNT_REFUNDAMOUNT_INEQUALITY("70004","退款金额与原交易金额不一致"),
	
	ORIAMOUNT_REFUND_STATUS_IS_NOT_SUCCESS("70005","原订单对应的交易状态不是支付成功，不能退款"),
	
	ORI_ORDER_ALREADU_REFUND("70006","退款请求对应的原支付订单已退款，不能退款"),
	
	PAY_TRANS_INTRO_IS_ILLEGLL("70007","支付交易简介不合法"),

	PAY_TRANS_NUM_REPEAT("70008","支付流水号重复"),
	
	QUERY_IS_NULL("70009","无对应的交易记录"),
	
	QUERY_IS_OK("70010","有对应的交易记录"),
	
	REFUND_AMOUNT_NOT_ENOUGH("70011","退款失败，账户余额不足"),
	
	SIGN_TYPE_NULL("70012","加密类型不合法");
	
	private String responeCode;
	
	private String responeMsg;
	
	private BusResponeEnum(String responeCode, String responeMsg) {
        this.responeCode = responeCode;
        this.responeMsg = responeMsg;
    }
	
    public static final BusResponeEnum getFromKey(String responeCode) {
        for (BusResponeEnum e : BusResponeEnum.values()) {
            if (e.getResponeCode().equals(responeCode)) {
                return e;
            }
        }
        return null;
    }

	public String getResponeCode() {
		return responeCode;
	}

	public void setResponeCode(String responeCode) {
		this.responeCode = responeCode;
	}

	public String getResponeMsg() {
		return responeMsg;
	}

	public void setResponeMsg(String responeMsg) {
		this.responeMsg = responeMsg;
	}
    
}
