package com.dome.sdkserver.service.login;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.service.pay.qbao.bo.UserInfo;

import java.util.Map;

public interface UserLoginService {

    public JSONObject getRegisterCode(String mobile, String appCode, String userIp, String bizType, String buld, String countryCode);

    public JSONObject registerUser(User user, String appCode, String regCode, String countryCode);

    public JSONObject modifyUser(String accessToken, String oldPassword, String newPassword, String appCode, String buld);

    public JSONObject getToken(User user, String appCode, String captcha, String captchaKey, String countryCode);

    public JSONObject getUserByToken(String token, String appCode, String buld, Map<String, Object> map);

    public JSONObject getUserByToken(String token, String appCode);

    public JSONObject resetPassword(User user, String appCode, String regCode, String smsToken, String countryCode);

    public JSONObject verifySmsCode(String mobile, String appCode, String smsCode, String userIp, String bizType, String buld, String countryCode);

    public JSONObject boundDomeUserId(UserInfo userInfo, String appCode, User user);

    public JSONObject getValidateCode(String captchaKey,String buld);

    public JSONObject getGmailCode(String email ,String appCode,String userIp,String bizType,String buld,String channel);
    public JSONObject gmailUser(User user,String appCode,String emailCode );

    public JSONObject verfyGmailCode(String email,String appCode,String emailCode,String userIp,String bizType,String buld);
    public JSONObject gmailResetPassword(User user,String appCode,String emailCode,String emailToken);

    public UserInfo getUserByUserId(String userId);
    public UserInfo getUserByPassport(String passport);
    
    public JSONObject thirdImplicitBind(String thirdId,String openId,User user,String appCode);
    
    public JSONObject isBind(String thirdId,String openId,User user,String appCode);

    public JSONObject realNameAuth (User user,Map<String,String> map);

    public JSONObject getHandGameActive(String buId, String imsi, String appCode, String channelId, String sysType);

    public JSONObject getUserInfoByOpenId(String thirdId, String openId);

    String getWXOpenId(String userId, String wxAppId);
}
