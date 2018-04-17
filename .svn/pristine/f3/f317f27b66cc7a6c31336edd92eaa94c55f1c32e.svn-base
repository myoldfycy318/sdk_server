package com.dome.sdkserver.bq.constants;

/**
 * 
 * 安全检查返回码
 * 
 * @author Frank.Zhou
 */
public enum SafeCheckResponeEnum {
	
	REQUEST_IP_ILLEGAL("80001","请求IP不合法"),
	
	REQUEST_LACK_SIGN_PARAM("80002", "请求缺少签名参数"),
	
	REQUEST_LACK_SIGN_CONFIG("80003", "缺少签名配置（未提供公钥）"),
	
	TIMESTAMP_ILLEGAL("80004", "请求的时间戳非法"),
	
	GARNT_CODE_ILLEGAL("80005", "请求的授权令牌非法"),
	
	MERCHANT_CODE_NOT_GRANT("80006", "未开户的商户号"),
	
	SIGN_CODE_ILLEGAL("80007", "签名信息不合法"),
	
	GARNT_CODE_IS_NULL("80008", "授权令牌不合法"),
	
	REQUEST_HAVENOT_GRANT("80009", "该流水未授权"),
	
	TRANS_TYPE_ILLEGAL("80010","交易类型不合法");
	
	private String responeCode;
	
	private String responeMsg;
	
	
	private SafeCheckResponeEnum(String responeCode, String responeMsg) {
        this.responeCode = responeCode;
        this.responeMsg = responeMsg;
    }
	
    public static final SafeCheckResponeEnum getFromKey(String responeCode) {
        for (SafeCheckResponeEnum e : SafeCheckResponeEnum.values()) {
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
