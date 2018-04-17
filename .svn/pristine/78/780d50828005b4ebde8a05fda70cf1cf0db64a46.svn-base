package com.dome.sdkserver.service.impl.login;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.bq.util.AESCoder;
import com.dome.sdkserver.service.login.QuickLoginService;
import com.dome.sdkserver.util.ApiConnector;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuickLoginServiceImpl extends UserCorrelate implements QuickLoginService{

    private static final Logger LOGGER = LoggerFactory.getLogger(QuickLoginServiceImpl.class);

    @Value("${user_center_url}")
    private String USER_CENTER_URL;

    @Value("${buid}")
    private String BUID;

    @Value("${uc_rsa_public_key_sdk}")
    private String uc_rsa_public_key_sdk;

    @Override
    public JSONObject getToken(User user, String appCode) {
        String response = null;
        JSONObject json = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("imsi", user.getLoginName()));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            if (StringUtils.isNotBlank(user.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", user.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            pairs.add(new BasicNameValuePair("sysType", user.getSysType()));
            pairs.add(new BasicNameValuePair("channelId", user.getChannelCode()));
            response = ApiConnector.post(USER_CENTER_URL + "/tourist/login.html", pairs);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                json = JSONObject.parseObject(response);
            }
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return json;
    }

	@Override
	public JSONObject getUserByToken(String token, String appCode) {
		String response = null;
		JSONObject json = null;
		try {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("accessToken", encrpyContent(token)));
			pairs.add(new BasicNameValuePair("clientId", appCode));
			pairs.add(new BasicNameValuePair("buId", BUID));
			response = ApiConnector.post(USER_CENTER_URL + "/user/getByToken.html", pairs);
			if (StringUtils.isNotBlank(response) && response.contains("code")) {
				json = JSONObject.parseObject(response);
			}

		} catch (Exception e) {
			LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
		}
		return json;
	}
	
	private String encrpyContent(String content) throws UnsupportedEncodingException, Exception{
		return new String(AESCoder.getEncryptResult(content, uc_rsa_public_key_sdk));
	}

	@Override
	public JSONObject getRegisterCode(String mobile, String appCode, String userIp, String bizType) {
		String response = null;
		JSONObject json = null;
		try {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("mobile", mobile));
			pairs.add(new BasicNameValuePair("userIp", userIp));
			pairs.add(new BasicNameValuePair("clientId", appCode));
			pairs.add(new BasicNameValuePair("bizType", bizType));
			pairs.add(new BasicNameValuePair("buId", BUID));
			response = ApiConnector.post(USER_CENTER_URL + "/mobile/getCode.html", pairs);
			if (StringUtils.isNotBlank(response) && response.contains("code")) {
				json = JSONObject.parseObject(response);
			}

		} catch (Exception e) {
			LOGGER.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
		}
		return json;
	}

    @Override
    public JSONObject touristBind(User user, String appCode, String smsCode, String userIp, String bizType, String imsi, String channelId, String countryCode, String sysType) {
        String response = null;
        JSONObject obj = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("passport", user.getLoginName()));
            pairs.add(new BasicNameValuePair("password", encrpyContent(user.getPassword())));
            pairs.add(new BasicNameValuePair("verifyCode", smsCode));
            pairs.add(new BasicNameValuePair("imsi", imsi));
            pairs.add(new BasicNameValuePair("channelId", channelId));
            pairs.add(new BasicNameValuePair("userIp", userIp));
            pairs.add(new BasicNameValuePair("bizType", bizType));
            pairs.add(new BasicNameValuePair("clientId", appCode));
            pairs.add(new BasicNameValuePair("countryCode", countryCode));
            pairs.add(new BasicNameValuePair("sysType", sysType));
            if (StringUtils.isNotBlank(user.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", user.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            wrapIsRealName(user, pairs);//封装是否需要实名参数
            String url = USER_CENTER_URL + (user.isRealName() ? "/v2/tourist/bind" : "/tourist/bind.html");
			response = ApiConnector.post(url, pairs);
            LOGGER.info("游客绑定调用户中心请求url:{},请求参数：{}，响应结果：{}", url, JSONObject.toJSONString(pairs), response);
			if (StringUtils.isNotBlank(response) && response.contains("code")) {
				obj = JSONObject.parseObject(response);
			}
		} catch (Exception e) {
			LOGGER.error(">>>>>>>>游客绑定调用户中心异常",e );
		}
		return obj;
	}
	
	
}
