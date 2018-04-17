package com.dome.sdkserver.service.impl.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.bq.util.AESCoder;
import com.dome.sdkserver.service.login.UserLoginService;
import com.dome.sdkserver.service.pay.qbao.bo.UserInfo;
import com.dome.sdkserver.service.redis.RedisService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("userLoginService")
public class UserLoginServiceImpl extends UserCorrelate implements UserLoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisService redisService;

    @Value("${user_center_url}")
    private String USER_CENTER_URL;

    @Value("${uc_rsa_public_key_sdk}")
    private String uc_rsa_public_key_sdk;

    @Value("${buid}")
    private String BUID;

    @Override
    public JSONObject registerUser(User user, String appCode, String regCode, String countryCode) {
        String response = null;
        JSONObject obj = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("countryCode", countryCode));
            pairs.add(new BasicNameValuePair("password", encrpyContent(user.getPassword())));
            pairs.add(new BasicNameValuePair("passport", user.getLoginName()));
            pairs.add(new BasicNameValuePair("mobileCode", regCode));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            pairs.add(new BasicNameValuePair("sysType", user.getSysType()));
            pairs.add(new BasicNameValuePair("channelId", user.getChannelCode()));
            pairs.add(new BasicNameValuePair("platformType", "0"));
            if (StringUtils.isNotBlank(user.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", user.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            wrapIsRealName(user, pairs);//封装是否需要实名参数
            String url = USER_CENTER_URL + (user.isRealName() ? "/v2/register/mobile" : "/register/registerConfirm.html");
            response = ApiConnector.post(url, pairs);
            LOGGER.info("手机号注册请求url:{},请求参数：{}，响应结果：{}", url, JSONObject.toJSONString(pairs), response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                obj = JSONObject.parseObject(response);
            }
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return obj;
    }


    public JSONObject modifyUser(String accessToken, String oldPassword, String newPassword, String appCode,
                                 String buId) {
        String response = null;
        JSONObject json = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("passwordOld", encrpyContent(oldPassword)));
            pairs.add(new BasicNameValuePair("passwordNew", encrpyContent(newPassword)));
            pairs.add(new BasicNameValuePair("accessToken", accessToken));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            if (StringUtils.isNotBlank(buId)) {
                pairs.add(new BasicNameValuePair("buId", buId));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            LOGGER.info("传过去的参数为：{}", JSON.toJSONString(pairs));
            response = ApiConnector.post(USER_CENTER_URL + "/user/modifyPassword", pairs);
            LOGGER.info("响应消息为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                json = JSONObject.parseObject(response);
            }

        } catch (Exception e) {
            LOGGER.error(">>>>>>>>修改用户请求UC失败: " + e.getMessage());
        }
        return json;
    }

    @Override
    public JSONObject getToken(User user, String appCode, String captcha, String captchaKey, String countryCode) {
        String response = null;
        JSONObject json = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("countryCode", countryCode));
            pairs.add(new BasicNameValuePair("password", encrpyContent(user.getPassword())));
            pairs.add(new BasicNameValuePair("passport", user.getLoginName()));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            if (StringUtils.isNotBlank(user.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", user.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            if (!StringUtils.isBlank(captcha)) {
                pairs.add(new BasicNameValuePair("captcha", captcha));
            }
            if (!StringUtils.isBlank(captchaKey)) {
                pairs.add(new BasicNameValuePair("captchaKey", captchaKey));
            }
            pairs.add(new BasicNameValuePair("sysType", user.getSysType()));
            pairs.add(new BasicNameValuePair("channelId", user.getChannelCode()));
            LOGGER.info("传过去的参数为：{}", JSON.toJSONString(pairs));
            response = ApiConnector.post(USER_CENTER_URL + "/login/login.html", pairs);
            LOGGER.info("响应消息为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                json = JSONObject.parseObject(response);
            }

        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return json;
    }


    /**
     * @param token    冰穹uc下发的token
     * @param clientId （请求来源为游戏时必填，来源不是游戏不需要填）冰穹分配给游戏的appCode
     * @param buId     冰穹uc分配给BU部门的id
     * @return
     */
    @Override
    public JSONObject getUserByToken(String token, String clientId, String buId, Map<String, Object> map) {
        String response = null;
        JSONObject json = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("accessToken", encrpyContent(token)));
            pairs.add(new BasicNameValuePair("clientId", clientId));
            if (StringUtils.isNotBlank(buId)) {
                pairs.add(new BasicNameValuePair("buId", buId));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            if (map != null) {
                pairs.add(new BasicNameValuePair("channelId", map.get("channelCode").toString()));
                pairs.add(new BasicNameValuePair("sysType", map.get("sysType").toString()));
            }
            String url = USER_CENTER_URL + "/user/getByToken.html";
            response = ApiConnector.post(url, pairs);
            LOGGER.info("getUserByToken url:{},请求参数：{}，响应结果：{}", url, JSONObject.toJSONString(pairs), response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                json = JSONObject.parseObject(response);
            }
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return json;
    }

    private String encrpyContent(String content) throws UnsupportedEncodingException, Exception {
        return new String(AESCoder.getEncryptResult(content, uc_rsa_public_key_sdk));
    }

    @Override
    public JSONObject getRegisterCode(String mobile, String appCode, String userIp, String bizType, String buld, String countryCode) {
        String response = null;
        JSONObject json = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("countryCode", countryCode));
            pairs.add(new BasicNameValuePair("mobile", mobile));
            pairs.add(new BasicNameValuePair("userIp", userIp));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            pairs.add(new BasicNameValuePair("bizType", bizType));
            if (StringUtils.isNotBlank(buld)) {
                pairs.add(new BasicNameValuePair("buId", buld));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            LOGGER.info("传过去的参数为：{}", JSON.toJSONString(pairs));
            response = ApiConnector.post(USER_CENTER_URL + "/mobile/getCode.html", pairs);
            LOGGER.info("响应消息为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                json = JSONObject.parseObject(response);
            }

        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return json;
    }

    @Override
    public JSONObject resetPassword(User user, String appCode, String smsCode, String smsToken, String countryCode) {
        String response = null;
        JSONObject obj = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("countryCode", countryCode));
            pairs.add(new BasicNameValuePair("password", encrpyContent(user.getPassword())));
            pairs.add(new BasicNameValuePair("mobile", user.getLoginName()));
            pairs.add(new BasicNameValuePair("mobileCode", smsCode));
            pairs.add(new BasicNameValuePair("smsToken", smsToken));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            if (StringUtils.isNotBlank(user.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", user.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            LOGGER.info("传过去的参数为：{}", JSON.toJSONString(pairs));
            response = ApiConnector.post(USER_CENTER_URL + "/user/resetPassword", pairs);
            LOGGER.info("响应消息为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                obj = JSONObject.parseObject(response);
            }

        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return obj;
    }

    @Override
    public JSONObject verifySmsCode(String mobile, String appCode, String smsCode, String userIp, String bizType, String buld, String countryCode) {
        String response = null;
        JSONObject obj = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("countryCode", countryCode));
            pairs.add(new BasicNameValuePair("mobile", mobile));
            pairs.add(new BasicNameValuePair("mobileCode", smsCode));
            pairs.add(new BasicNameValuePair("userIp", userIp));
            pairs.add(new BasicNameValuePair("bizType", bizType));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            if (StringUtils.isNotBlank(buld)) {
                pairs.add(new BasicNameValuePair("buId", buld));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            LOGGER.info("传过去的参数为：{}", JSON.toJSONString(pairs));
            response = ApiConnector.post(USER_CENTER_URL + "/mobile/verifyCode", pairs);
            LOGGER.info("响应消息为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                obj = JSONObject.parseObject(response);
            }

        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return obj;
    }

    @Override
    public JSONObject boundDomeUserId(UserInfo userInfo, String appCode, User user) {
        String response = null;
        JSONObject obj = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("qbaoUid", userInfo.getUserId()));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            if (StringUtils.isNotBlank(user.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", user.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            if (StringUtils.isNotBlank(userInfo.getUsername())) {
                pairs.add(new BasicNameValuePair("userName", userInfo.getUsername()));
            }
            pairs.add(new BasicNameValuePair("sysType", user.getSysType()));
            pairs.add(new BasicNameValuePair("channelId", user.getChannelCode()));
            LOGGER.info("传过去的参数为：{}", JSON.toJSONString(pairs));
            response = ApiConnector.post(USER_CENTER_URL + "/qbao/login", pairs);
            LOGGER.info("响应消息为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                obj = JSONObject.parseObject(response);
            }

        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return obj;
    }

    /**
     * 获取图形验证码
     *
     * @return
     */
    @Override
    public JSONObject getValidateCode(String captchaKey, String buld) {
        JSONObject json = null;
        String response = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            if (StringUtils.isNotBlank(buld)) {
                pairs.add(new BasicNameValuePair("buId", buld));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            if (!StringUtils.isBlank(captchaKey)) {
                pairs.add(new BasicNameValuePair("captchaKey", captchaKey));
            }
            LOGGER.info("传过去的参数为：{}", JSON.toJSONString(pairs));
            response = ApiConnector.post(USER_CENTER_URL + "/captcha/getValidateCode", pairs);
            LOGGER.info("响应消息为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                json = JSONObject.parseObject(response);
            }
        } catch (Exception e) {
            LOGGER.error("UserCenterFacade.getValidateCode failed", e);
        }
        return json;
    }

    @Override
    public JSONObject getGmailCode(String email, String appCode, String userIp, String bizType, String buld, String channel) {
        String response = null;
        JSONObject json = null;
        String flag = "0"; //海外区别标准 0=国内 1=海外
        try {
            if ("CHA000004".equals(channel)) {
                flag = "1";
            }
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("email", email));
            pairs.add(new BasicNameValuePair("userIp", userIp));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            pairs.add(new BasicNameValuePair("bizType", bizType));
            LOGGER.info("传过去的flag为：{}", flag);
            pairs.add(new BasicNameValuePair("isInland", flag));
            if (StringUtils.isNotEmpty(buld)) {
                pairs.add(new BasicNameValuePair("buId", buld));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            LOGGER.info("传过去的参数为：{}", JSON.toJSONString(pairs));
            response = ApiConnector.post(USER_CENTER_URL + "/email/getCode", pairs);
            LOGGER.info("响应消息为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                json = JSONObject.parseObject(response);
            }

        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return json;
    }

    @Override
    public JSONObject gmailUser(User user, String appCode, String emailCode) {
        String response = null;
        JSONObject obj = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("password", encrpyContent(user.getPassword())));
            pairs.add(new BasicNameValuePair("email", user.getLoginName()));
            pairs.add(new BasicNameValuePair("emailCode", emailCode));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            pairs.add(new BasicNameValuePair("sysType", user.getSysType()));
            pairs.add(new BasicNameValuePair("channelId", user.getChannelCode()));
            if (StringUtils.isNotBlank(user.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", user.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            wrapIsRealName(user, pairs);//封装是否需要实名参数
            String url = USER_CENTER_URL + (user.isRealName() ? "/v2/register/email" : "/register/registerEmail");
            response = ApiConnector.post(url, pairs);
            LOGGER.info("短信注册请求url:{},请求参数：{}，响应结果：{}", url, JSONObject.toJSONString(pairs), response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                obj = JSONObject.parseObject(response);
            }
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }

        return obj;
    }

    @Override
    public JSONObject verfyGmailCode(String email, String appCode, String emailCode, String userIp, String bizType, String buld) {
        String response = null;
        JSONObject obj = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("email", email));
            pairs.add(new BasicNameValuePair("emailCode", emailCode));
            pairs.add(new BasicNameValuePair("userIp", userIp));
            pairs.add(new BasicNameValuePair("bizType", bizType));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            if (StringUtils.isNotBlank(buld)) {
                pairs.add(new BasicNameValuePair("buId", buld));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            response = ApiConnector.post(USER_CENTER_URL + "/email/verifyCode", pairs);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                obj = JSONObject.parseObject(response);
            }

        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return obj;
    }

    @Override
    public JSONObject gmailResetPassword(User user, String appCode, String emailCode, String emailToken) {
        String response = null;
        JSONObject obj = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("password", encrpyContent(user.getPassword())));
            pairs.add(new BasicNameValuePair("email", user.getLoginName()));
            pairs.add(new BasicNameValuePair("emailCode", emailCode));
            pairs.add(new BasicNameValuePair("emailToken", emailToken));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            if (StringUtils.isNotBlank(user.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", user.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            response = ApiConnector.post(USER_CENTER_URL + "/user/resetPasswordEmail", pairs);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                obj = JSONObject.parseObject(response);
            }
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return obj;
    }

    @Override
    public JSONObject getUserByToken(String token, String appCode) {
        return this.getUserByToken(token, appCode, null, null);
    }


    /**
     * 根据冰趣userID获取冰趣用户信息
     *
     * @param userId
     * @return
     */
    public UserInfo getUserByUserId(String userId) {
        UserInfo userInfo = null;
        String json = null;
        JSONObject jsonObject = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("userId", userId));
            json = ApiConnector.get(USER_CENTER_URL + "/user/getUserById", pairs);
            if (StringUtils.isNotBlank(json) && null != JSONObject.parseObject(json) && "0".equals(JSONObject.parseObject(json).get("code")) && StringUtils.isNotBlank(JSONObject.parseObject(json).getString("data"))) {
                jsonObject = JSONObject.parseObject(JSONObject.parseObject(json).getString("data"));
                userInfo = new UserInfo();
                userInfo.setUserId(userId);
                userInfo.setUsername(jsonObject.getString("domeUserName"));
                userInfo.setMobile(jsonObject.getString("mobile"));
                userInfo.setOpenId(jsonObject.getString("openId"));
                userInfo.setThirdId(jsonObject.getString("thirdId"));
                return userInfo;
            }
        } catch (Exception e) {
            LOGGER.error("根据冰趣userID:{}获取冰趣用户信息异常", userId, e);
        }
        return null;
    }


    /**
     * 根据冰趣账户获取冰趣用户信息
     *
     * @param passport
     * @return
     */
    public UserInfo getUserByPassport(String passport) {
        UserInfo userInfo = null;
        JSONObject jsonObject = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("passport", passport));
            String url = USER_CENTER_URL + "/user/getUserByPassport?passport=" + passport;
            String response = ApiConnector.get(url, null);
            LOGGER.info(" 根据冰趣账户获取用户url:{},请求参数：{}，响应结果：{}", url, JSONObject.toJSONString(pairs), response);
            if (StringUtils.isNotBlank(response) && null != (jsonObject = JSONObject.parseObject(response)) && "0".equals(jsonObject.get("code"))
                    && null != (jsonObject.getJSONObject("data")) && StringUtils.isNotBlank(jsonObject.getJSONObject("data").getString("domeUserId"))) {
                userInfo = new UserInfo();
                userInfo.setUserId(jsonObject.getJSONObject("data").getString("domeUserId"));
                return userInfo;
            }
        } catch (Exception e) {
            LOGGER.error("根据冰趣passport:{}获取冰趣用户信息异常", passport, e);
        }
        return null;
    }


    /**
     * 第三方隐式绑定
     */
    public JSONObject thirdImplicitBind(String thirdId, String openId, User user, String appCode) {
        String response = null;
        JSONObject obj = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("thirdId", thirdId));
            pairs.add(new BasicNameValuePair("openId", openId));
            if (StringUtils.isNotBlank(user.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", user.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            pairs.add(new BasicNameValuePair("clientId", appCode));
            LOGGER.info("传过去的参数为：{}", JSON.toJSONString(pairs));
                response = ApiConnector.post(USER_CENTER_URL + "/thirdpart/implicitBind", pairs);
            LOGGER.info("响应消息为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                obj = JSONObject.parseObject(response);
            }
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return obj;
    }

    /**
     * 查询绑定关系接口
     */
    public JSONObject isBind(String thirdId, String openId, User user, String appCode) {
        String response = null;
        JSONObject obj = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("thirdId", thirdId));
            pairs.add(new BasicNameValuePair("openId", openId));
            pairs.add(new BasicNameValuePair("userId", user.getUserId()));
            if (StringUtils.isNotBlank(user.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", user.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            pairs.add(new BasicNameValuePair("clientId", appCode));
            LOGGER.info("传过去的参数为：{}", JSON.toJSONString(pairs));
            response = ApiConnector.post(USER_CENTER_URL + "/thirdpart/isBind", pairs);
            LOGGER.info("响应消息为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                obj = JSONObject.parseObject(response);
            }
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return obj;
    }

    /**
     * 实名认证
     *
     * @param user
     * @param map
     * @return
     */
    public JSONObject realNameAuth(User user, Map<String, String> map) {
        String response = null;
        JSONObject obj = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("accessToken", encrpyContent(map.get("accessToken"))));
            pairs.add(new BasicNameValuePair("name", user.getIdCardName()));
            pairs.add(new BasicNameValuePair("card", user.getIdCardNo()));
            pairs.add(new BasicNameValuePair("clientId", map.get("appCode")));
            if (StringUtils.isNotBlank(user.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", user.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            String url = USER_CENTER_URL + "/v2/idcard/attestation";
            response = ApiConnector.post(url, pairs);
            LOGGER.info("实名认证url:{},请求参数：{}，响应结果：{}", url, JSONObject.toJSONString(pairs), response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                obj = JSONObject.parseObject(response);
            }
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>实名认证异常", e);
        }
        return obj;
    }

    /**
     * 根据openId获取冰穹用户信息
     *
     * @param thirdId
     * @param openId
     * @return
     */
    @Override
    public JSONObject getUserInfoByOpenId(String thirdId, String openId) {
        JSONObject json = null;
        String response = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("thirdId", thirdId));
            pairs.add(new BasicNameValuePair("openId", openId));
            String url = USER_CENTER_URL + "/thirdParty/getUserInfoByOPenId";
            response = ApiConnector.post(url, pairs);
            LOGGER.info("请求url:{},请求参数:{},响应消息为：{}", url, JSONObject.toJSONString(pairs), response);
            if (StringUtils.isNotBlank(response) && (json = JSONObject.parseObject(response)) != null
                    && StringUtils.isNotBlank(json.getString("code")) && json.getString("code").equals("0") && json.getJSONObject("data") != null) {
                return json.getJSONObject("data");
            }
        } catch (Exception e) {
            LOGGER.error("根据openId获取冰穹用户信息异常", e);
        }
        return json;
    }

    @Override
    public JSONObject getHandGameActive(String buId, String imsi, String appCode, String channelId, String sysType) {
        JSONObject json = null;
        String response = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("imsi", imsi));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            pairs.add(new BasicNameValuePair("channelId", channelId));
            pairs.add(new BasicNameValuePair("sysType", sysType));
            LOGGER.info("传过去的参数为：{}", JSON.toJSONString(pairs));
            response = ApiConnector.post(USER_CENTER_URL + "/login/handGameActiveLog", pairs);
            LOGGER.info("响应消息为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                json = JSONObject.parseObject(response);
            }
        } catch (Exception e) {
            LOGGER.error("UserCenterFacade.getValidateCode failed", e);
        }
        return json;
    }

    /**
     * 根据冰穹userId、微信公众号appid获取微信对应的openid
     *
     * @param wxAppId
     * @param userId
     * @return
     */
    @Override
    public String getWXOpenId(String userId, String wxAppId) {
        JSONObject json = null;
        String response = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("userId", userId));
            pairs.add(new BasicNameValuePair("appId", wxAppId));
            String url = USER_CENTER_URL + "/thirdParty/getOpenIdByUserId";
            response = ApiConnector.post(url, pairs);
            LOGGER.info("请求url:{},请求参数:{},响应消息为：{}", url, JSONObject.toJSONString(pairs), response);
            if (StringUtils.isNotBlank(response) && (json = JSONObject.parseObject(response)) != null && StringUtils.isNotBlank(json.getString("code")) && json.getString("code").equals("0") && json.getJSONObject("data") != null && StringUtils.isNotBlank(json.getJSONObject("data").getString("wxOpenId"))) {
                return json.getJSONObject("data").getString("wxOpenId");
            }
        } catch (Exception e) {
            LOGGER.error("根据冰穹userId、微信公众号appid获取微信对应的openid异常", e);
        }
        return null;
    }
}
