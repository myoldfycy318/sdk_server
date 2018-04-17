package com.dome.sdkserver.service.login;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.login.domain.user.User;

public interface QuickLoginService {

	public JSONObject getToken(User user,String appCode);
	
	public JSONObject getUserByToken(String token,String appCode);
	
	public JSONObject getRegisterCode(String mobile,String appCode,String userIp,String bizType);
	
	public JSONObject touristBind(User user,String appCode,String smsCode,String userIp,String bizType,String imsi ,String channelId,String countryCode,String sysType);
}
