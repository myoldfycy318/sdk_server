package com.dome.sdkserver.service.impl.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.login.domain.user.IdCardRecord;
import com.dome.sdkserver.service.login.IdCardService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IdCardServiceImpl implements IdCardService {

    private static final Logger log = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    @Value("${user_center_url}")
    private String USER_CENTER_URL;

    @Value("${buid}")
    private String BUID;

    @Autowired
    PropertiesUtil domainConfig;

    @Override
    public JSONObject IdcardAttestation(IdCardRecord idCardRecord) {
        String response = null;
        JSONObject json = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("accessToken", idCardRecord.getAccessToken()));
            pairs.add(new BasicNameValuePair("card", idCardRecord.getCard()));
            pairs.add(new BasicNameValuePair("clientId", idCardRecord.getAppCode()));
            pairs.add(new BasicNameValuePair("countryCode", idCardRecord.getCountryCode()));
            pairs.add(new BasicNameValuePair("mobile", idCardRecord.getMobile()));
            pairs.add(new BasicNameValuePair("name", idCardRecord.getName()));
            pairs.add(new BasicNameValuePair("verifyCode", idCardRecord.getVerifyCode()));
            if (StringUtils.isNotBlank(idCardRecord.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", idCardRecord.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            log.info("传过去的参数为：{}", JSON.toJSONString(pairs));
            response = ApiConnector.post(USER_CENTER_URL + "/idcard/attestation", pairs);
            log.info("响应信息response为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                json = JSONObject.parseObject(response);
            }
        } catch (Exception e) {
            log.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return json;
    }

    @Override
    public JSONObject IdcardCheck(IdCardRecord idCardRecord) {
        String response = null;
        JSONObject json = null;
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("accessToken", idCardRecord.getAccessToken()));
            pairs.add(new BasicNameValuePair("clientId", idCardRecord.getAppCode()));
            if (StringUtils.isNotBlank(idCardRecord.getBuId())) {
                pairs.add(new BasicNameValuePair("buId", idCardRecord.getBuId()));
            } else {
                pairs.add(new BasicNameValuePair("buId", BUID));
            }
            log.info("传过去的参数为：{}", JSON.toJSONString(pairs));
            response = ApiConnector.post(USER_CENTER_URL + "/idcard/check", pairs);
            log.info("响应信息response为：{}", response);
            if (StringUtils.isNotBlank(response) && response.contains("code")) {
                json = JSONObject.parseObject(response);
            }
        } catch (Exception e) {
            log.error(">>>>>>>>UserCenterFacade.getCasUser failed: " + e.getMessage());
        }
        return json;
    }

    /**
     * 是否开启实名认证
     *
     * @return true:开启,false:false，默认关闭实名认证
     */
    @Override
    public boolean isNRealName() {
        String url = domainConfig.getString("real.name.query.url");
        String response = null;
        JSONObject jsonObject = null;
        try {
            response = ApiConnector.post(url, null);
            if (StringUtils.isNotBlank(response) && (jsonObject = JSONObject.parseObject(response)) != null
                    && "1000".equals(jsonObject.getString("responseCode"))
                    && jsonObject.getJSONObject("data") != null
                    && StringUtils.isNotBlank(jsonObject.getJSONObject("data").getString("status"))
                    && "1".equals(jsonObject.getJSONObject("data").getString("status"))
                    ) {
                return true;
            }
        } catch (Exception e) {
            log.error("获取是否开启实名认证开关异常，url:{},响应结果:{}", url, response, e);
        }
        return false;
    }
}
