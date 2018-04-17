package com.dome.sdkserver.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.service.BqSdkConstants;
import com.spreada.utils.chinese.ZHConverter;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 中文繁体、简体转化
 *
 * @author Zhang ShanMin
 * @date 2017/5/9
 * @time 17:49
 */
@Aspect
@Component
public class ZhConverterIntercept {

    @Around("within(@org.springframework.stereotype.Controller *) &&@annotation(com.dome.sdkserver.bq.annotation.ZhConverter)")
    public Object arround(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Object result = pjp.proceed();
        JSONObject jsonObject = null;
        String str = "";
        if (("createOrder".equals(method.getName()) || "notify".equals(method.getName()))
                && pjp.getArgs() != null && StringUtils.isNotBlank(str = JSON.toJSONString(pjp.getArgs()))
                && (jsonObject = JSON.parseObject(str.substring(1, str.length() - 1))) != null) {
            if (StringUtils.isNotBlank(jsonObject.getString("channelCode")) && !BqSdkConstants.channelCodeOverseas.equals(jsonObject.getString("channelCode"))) {
                SdkOauthResult sdkOauthResult = JSONObject.parseObject(JSON.toJSONString(result), SdkOauthResult.class);
                if (!sdkOauthResult.isSuccess() && StringUtils.isNotBlank(sdkOauthResult.getErrorMsg())) {
                    sdkOauthResult.setErrorMsg(zh_TW2zh_CN(sdkOauthResult.getErrorMsg()));
                    return sdkOauthResult;
                }

            }
        }
        return result;
    }

    /**
     * 中文繁体转简体
     *
     * @param traditionalSrc
     * @return
     */
    private String zh_TW2zh_CN(String traditionalSrc) {
        ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
        return converter.convert(traditionalSrc);
    }

    /**
     * 中文简体转繁体
     *
     * @param simplifiedSrc
     * @return
     */
    private String zh_CH2zh_TW(String simplifiedSrc) {
        return ZHConverter.convert(simplifiedSrc, ZHConverter.TRADITIONAL);
    }
}
