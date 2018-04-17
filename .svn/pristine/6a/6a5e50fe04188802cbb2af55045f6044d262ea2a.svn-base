package com.dome.sdkserver.bq.constants;


/**
 * 
 * 描述：
 * 参数基本校验返回码
 * @author hexiaoyi
 */
public enum ParamBaseValResEnum {


    //通用响应
	TRANS_TYPE_IS_NULL("20001", "交易类型参数为空"),
	
	TRANS_TYPE_ILLEGAL("20002", "交易类型参数非法"),
	
	MERCHANT_CODE_IS_NULL("20004","商户号为空"),
	
	MERCHANT_CODE_ILLEGAL("20005","商户号不合法"),
	
	APP_SOURCE_IS_NULL("20006","应用渠道为空"),
	
	APP_SOURCE_IS_ILLEGAL("20007","应用渠道不合法"),
	
	//授权响应
	GRANT_NUM_IS_NULL("30001","授权请求流水号为空"),
	
	GRANT_NUM_ILLEGAL("30002","授权请求流水号不合法"),
	
	GRANT_DATE_IS_NULL("30003","授权请求时间为空 "),
	
	GRANT_DATE_ILLEGAL("30004","授权请求时间不合法"),
	
	GRANT_SIGN_IS_NULL("30005","授权签名为空"),
	
	GRANT_SIGN_ILLEGAL("30006","授权签名不合法"),
	
	//支付响应
	PAY_NUM_IS_NULL("40001","支付请求流水号为空"),
	
	PAY_NUM_ILLEGAL("40002","支付请求流水号不合法"),
	
	PAY_DATE_IS_NULL("40003","支付请求时间为空"),
	
	PAY_DATE_ILLEGAL("40004","支付请求时间不合法"),
	
	PAY_TRANS_INTRO_IS_NULL("40005","交易简介为空"),
	
	PAY_TRANS_INTRO_IS_ILLEGAL("40006","交易简介不合法"),
	
	PAY_AMOUNT_IS_NULL("40007","支付金额为空"),
	
	PAY_AMOUNT_ILLEGAL("40008","支付金额不合法 "),
	
	PAY_SUCCESS_URL_IS_NULL("40009","支付成功跳转地址为空"),
	
	PAY_SUCCESS_URL_IS_ILLEGAL("40010","支付成功跳转地址不合法 "),

	PAY_CALLBACK_URL_IS_NULL("40011","支付请求的异步通知地址为空"),
	
	PAY_CALLBACK_URL_IS_ILLEGAL("40012","支付请求的异步通知地址不合法"),
	
	PAY_TRANS_CLOSE_TIME_IS_NULL("40013","支付请求的关闭时间为空"),
	
	PAY_TRANS_CLOSE_TIME_ILLEGL("40014","支付请求的关闭时间不合法"),
	
	PAY_SIGN_IS_NULL("40015","支付签名为空"),
	
	PAY_SIGN_ILLEGAL("40016","支付签名不合法"),
	
	PAY_MD5_IS_NULL("40017","支付MD5参数为空"),
	
	PAY_MD5_IS_ILLEGAL("40018","支付MD5参数不合法"),
	
	PAY_TRANS_APP_CODE_IS_NULL("40019","支付应用编码为空"),
	
	PAY_TRANS_APP_CODE_IS_ILLEGAL("40020","支付应用编码不合法"),
	
	PAY_USER_ID_IS_NULL("40021","支付用户id为空"),
	
	//退款响应
	REFUND_NUM_IS_NULL("50001","退款请求流水号为空"),

	REFUND_NUM_IS_ILLEGAL("50002","退款请求流水号不合法"),
	
	REFUND_DATE_IS_NULL("50003","退款请求日期为空"),
	
	REFUND_DATE_ILLEGL("50004","退款请求日期不合法 "),
	
	REFUND_ORI_TRANS_NUM_IS_NULL("50005","原交易流水号为空"),

	REFUND_ORI_TRANS_NUM_ILLEGL("50006","原交易流水号不合法"),
	
	REFUND_ORI_TRANS_DATE_IS_NULL("50007","原交易日期为空"),
	
	REFUND_ORI_TRANS_DATE_ILLEGL("50008","原交易日期不合法"),
	
	REFUND_AMOUNT_IS_NULL("50009","退款金额为空"),
	
	REFUND_AMOUNT_ILLEGL("50010","退款金额不合法"),
	
	REFUND_CALLBACK_URL_IS_NULL("50011","退款异步通知地址为空"),
	
	REFUND_CALLBACK_URL_ILLEGL("50012","退款异步通知地址不合法"),
	
	REFUND_SIGN_IS_NULL("50013","退款签名为空"),
	
	REFUND_SIGN_ILLEGAL("50014","退款签名不合法"),
	
	REFUND_MD5_IS_NULL("50015","退款MD5参数为空"),
	
	REFUND_MD5_IS_ILLEGAL("50016","退款MD5参数不合法"),
	
	SINGLE_SEARCH_TRANSDATE_IS_NULL("60001","单笔查询交易日期为空 "),
	
	SINGLE_SEARCH_TRANSDATE_IS_ILLEGAL("60002","单笔查询交易日期不合法 "),
	
	SINGLE_SEARCH_TRANSNUM_IS_NULL("60003","单笔查询交易流水号为空 "),
	
	SINGLE_SEARCH_TRANSNUM_IS_ILLEGAL("60004","单笔查询交易流水号不合法 "),
	
	CHARGE_POINT_IS_ILLEGAL("70001","计费点编码为空 "),
	
	CHARGE_POINT_IS_NULL("70002","计费点编码不合法 "),
	
	PAY_TYPE_IS_NULL("40022","支付方式为空"),
	
	PAY_TYPE_IS_ILLEGAL("40023","支付方式不合法")
	
	;
	
	private String responeCode;
	
	private String responeMsg;
	
	
	private ParamBaseValResEnum(String responeCode, String responeMsg) {
        this.responeCode = responeCode;
        this.responeMsg = responeMsg;
    }
	
    public static final ParamBaseValResEnum getFromKey(String responeCode) {
        for (ParamBaseValResEnum e : ParamBaseValResEnum.values()) {
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
