package com.dome.sdkserver.web.response;

public class ResponseCode {
	public static int SUCESS = 0;
	
	public static int PAMARETER_ERROR = -5;
	
	public static int USERNAME_NOT_EXIST = -1;
	
	public static int TOKEN_FAILD = -16;
	
	public static int PASSWORD_ERROR = -2;
	
	public static int TOKEN_INVAILD = -6;
	
	public static int TOKEN_VERFIY_FAILD = -7;
	
	public static int NEWPASSWORD_IS_SAME_WITH_OLD = -29;
	
	public static int SERVER_EXCEPTION = 9999;
	
	public static int SEND_SMS_SUCESS = 91000;
	//短信验证码发送过于频繁
	public static int SEND_SMS_OFTEN = 91040;
	//短信验证码一分钟内只能发送一次
	public static int SEND_SMS_ONCE_MIN = 91041;
	//短信码过期，重新发送
	public static int SMS_IS_EXPIRE = 91042;
	//短信码错误，重新发送
	public static int SMS_ERROR = 91043;
	
}
