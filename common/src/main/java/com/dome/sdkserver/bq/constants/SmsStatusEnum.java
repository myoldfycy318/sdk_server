/**
 * 
 */
package com.dome.sdkserver.bq.constants;

/**
 * @author mazhongmin
 *
 */
public enum SmsStatusEnum {

	
	SEND_SMS_OK("91000","短信发送发送成功"),
	
	SEND_SMS_OFTEN("91040", "短信验证码发送过于频繁"),
	
	SEND_SMS_ONCE_MIN("91041", "短信验证码一分钟内只能发送一次"),
	
	SMS_IS_EXPIRE("91042", "短信码过期，重新发送"),
	
	SMS_ERROR("91043","短信码错误，重新发送");
	
	
	private String status;
	
	private String desc;
	
	
	private SmsStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }
	
    public static final SmsStatusEnum getFromKey(String status) {
        for (SmsStatusEnum e : SmsStatusEnum.values()) {
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
