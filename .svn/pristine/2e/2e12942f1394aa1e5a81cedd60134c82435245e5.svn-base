package com.dome.sdkserver.service.impl.login;

import com.dome.sdkserver.bq.enumeration.ErrorCodeEnum;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.login.SdkAuthorizationService;
import com.dome.sdkserver.util.RSACoder;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("sdkAuthorizationService")
public class SdkAuthorizationServiceImpl implements SdkAuthorizationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SdkAuthorizationServiceImpl.class);

	@Value("${rsa_private_key_4_sdk}")
	private String RSA_PRIVATE_KEY_4_SDK;

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public SdkOauthResult checkAuthorizeRequest(String appCode, String loginName, String password) {
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
		}
		String srcPasswordAndCode = "";
		try {
			srcPasswordAndCode = new String(RSACoder.decryptByPrivateKey(RSACoder.decryptBASE64(password), RSA_PRIVATE_KEY_4_SDK));
		} catch (Exception e) {
			LOGGER.error("字符串解密失败",e);
			return SdkOauthResult.failed(ErrorCodeEnum.字符串解密失败.code, ErrorCodeEnum.字符串解密失败.name());
		}
		if (srcPasswordAndCode.length() < 12) {
			return SdkOauthResult.failed(ErrorCodeEnum.无效的密码.code, ErrorCodeEnum.无效的密码.name());
		}
		String srcPassword = srcPasswordAndCode.substring(0, srcPasswordAndCode.length() - 6);
		String authCode = srcPasswordAndCode.substring(srcPasswordAndCode.length() - 6);

		String redisKey = BqSdkConstants.redis_key_authCode_prefix + appCode + authCode;
		if (StringUtils.isBlank(redisUtil.get(redisKey))) {
			return SdkOauthResult.failed(ErrorCodeEnum.无效的authCode.code, ErrorCodeEnum.无效的authCode.name());
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("srcPassword", srcPassword);
		resultMap.put("authCode", authCode);
		return SdkOauthResult.success(resultMap);
	}

}
