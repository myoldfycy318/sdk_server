/**
 * 
 */
package com.dome.sdkserver.bq.constants;

/**
 * @author mazhongmin
 * SDK支付错误返回码
 */
public enum PayResEnum {
	
	SUCCESS_CODE("1000","成功"),
	
	FAIL_CODE("1005","失败"),
	
	PARAM_BLANK("90001","必填参数为空"),
	
	APP_CODE_NO_EXISTS("90002","当前应用不存在"),
	
	CHARGE_POINT_NO_EXISTS("90003","当前计费点不存在"),
	
	PARAM_ILLEGAL("90004","参数不合法"),
	
	DECRYPT_ERROR("90005","参数解密不存在"),
	
	PAY_PSW_NO_EXISTS("90006","没有设置支付密码"),
	
	NO_BIND_BANK_CARD("90007","没有绑定银行卡"),
	
	NO_BIND_MOBILE("90008","没有绑定手机号"),
	
	TRANS_NO_EXISTS("90009","当前流水不存在"),
	
	TRANS_INFO_IS_EXIST("90010","交易流水已存在"),
	
	SIGN_CODE_ILLEGAL("90011", "签名信息不合法"),
	
	USE_NOT_EXIST("90012", "用户信息不存在"),
	
	USE_BQ("1", "可用宝券"),
	
	NO_USE_BQ("0", "不可用宝券"),
	
	SEND_MSG("1", "需要短信验证"),
	
	NO_SEND_MSG("0", "不需要短信验证"),
	
	PWD_CHECK_NET_ERROR("90013","校验交易密码网络异常"),
	
	PWD_CHECK_ERROR("90014","交易密码错误"),
	
	PWD_CHECK_DEADLINE("90015","交易密码错误次数超过日上线"),
	
	PWD_CHECK_UNKNOW("90016","交易密码校验未知异常"),
	
	CANT_NOT_USE_VOUCHER("90017","无法使用宝券"),
	
	VOUCHER_QUERY_ERROR("90018","获取宝券异常"),
	
	BALANCE_NOT_ENOUGH("90019","余额不足"),
	
	TRADE_UNKNOW("90020","支付位置异常"),
	
	TRADE_NET_ERROR("90021","网络异常"),
	
	STATUS_UNKNOW("90022","未知流水号"),
	
	BQ_OUT_ERROR("90023","宝券扣除异常"),
	
	BB_OUT_ERROR("90024","账户扣款异常"),
	
	TIMESTAMP_ILLEGAL("90025","支付信息过期"),
	
	INPUT_VERIFY_CODE("90026","请输入短信验证码"),
	
	NO_SEND_SMS("90027","请直接支付，无需发送短信"),
	
	SDKFLOWID_IS_EXIST("90028","正在支付中"),
	
	ACCOUNT_QUERY_ERROR("90029","获取账户信息异常"),
	
	TURNTABLE_RULE_QUERY_ERROR("90030","获取应用市场大转盘规则异常(单笔)"),
	
	REQUEST_IP_ERROR("90031","请求ip地址异常"),
	
	SIGN_TYPE_ERROR("90032","加签方式异常"),

	PAY_EXIST("90033","订单已支付"),

	UPDATE_TRANS_ERROR("99999","系统异常"),
	
	;
	
	private String code;
	
	private String msg;
	
	private PayResEnum(String code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public static final PayResEnum getFromKey(String code){
		for(PayResEnum e:PayResEnum.values()){
			if(e.getCode().equals(code)){
				return e;
			}
		}
		return null;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
