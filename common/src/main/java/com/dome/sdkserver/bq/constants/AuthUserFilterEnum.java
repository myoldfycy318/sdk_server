package com.dome.sdkserver.bq.constants;

public enum AuthUserFilterEnum {
	
	校验成功(1000),
	请求来源不合法(1001),
	请求太频繁(1002),
	请求失效需要重新进行支付(1003),
	登录失败超过最大次数(1004),
	登录失效请刷新页面重新登录(1005),
	请输入用户名(1006),
	停止过滤器(1007),
	参数异常(1008),
	未知校验异常(9999);

	public int f;
	
	private AuthUserFilterEnum(int f) {
		this.f = f;
	}
	
}
