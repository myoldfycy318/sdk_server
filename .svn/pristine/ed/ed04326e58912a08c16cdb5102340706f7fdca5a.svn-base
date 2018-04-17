package com.dome.sdkserver.controller.captcha;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.view.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * VerificateCodeController
 *
 * @author Zhang ShanMin
 * @date 2016/8/4
 * @time 17:30
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PropertiesUtil domainConfig;

    @RequestMapping(value = "/mergeCaptcha", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult mergeCaptcha(HttpServletRequest request) {
        try {
            AjaxResult ajaxResult = validateParams(request);
            if (!AjaxResult.isSucees(ajaxResult))
                return ajaxResult;
            String url = domainConfig.getString("bq.usercenter.captcha.merge.url");
            String captchaSwitch = request.getParameter("captchaSwitch");
            String limitCount = request.getParameter("limitCount");
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("captchaSwitch", captchaSwitch));
            pairs.add(new BasicNameValuePair("limitCount", limitCount));
            pairs.add(new BasicNameValuePair("buId", domainConfig.getString("buid")));
            logger.info("mergeCaptcha.requestUrl:{},请求参数:{}", url, JSON.toJSONString(pairs));
            String respContent = ApiConnector.post(url, pairs);
            logger.info("mergeCaptcha.返回结果:{}", respContent);
            if (StringUtils.isBlank(respContent))
                return AjaxResult.failed();
            JSONObject jsonObject = JSONArray.parseObject(respContent);
            String responseCode = jsonObject.getString("responseCode");
            if (!StringUtils.isBlank(responseCode) && "1000".equals(responseCode)) {
                return AjaxResult.success();
            }
        } catch (Exception e) {
            logger.error("mergeCaptcha.error:", e);
        }
        return AjaxResult.failed();
    }

    /**
     * 请求参数校验
     *
     * @param request
     * @return
     */
    private AjaxResult validateParams(HttpServletRequest request) {
        String captchaSwitch = request.getParameter("captchaSwitch");
        String limitCount = request.getParameter("limitCount");
        if (StringUtils.isBlank(captchaSwitch) || !captchaSwitch.matches("(^on$)|(^off$)"))
            return AjaxResult.failed("请求参数不正确");
        if (StringUtils.isBlank(limitCount) || !limitCount.matches("\\d+")) {
            return AjaxResult.failed("请求参数不正确");
        }
        return AjaxResult.success();
    }


    @RequestMapping(value = "/queryCaptcha", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult queryCaptcha(HttpServletRequest request) {
        try {
            String url = domainConfig.getString("bq.usercenter.captcha.query.url");
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("buId", domainConfig.getString("buid")));
            logger.info("queryCaptcha.requestUrl:{},请求参数:{}", url, JSON.toJSONString(pairs));
            String respContent = ApiConnector.post(url, pairs);
            logger.info("queryCaptcha.返回结果:{}", respContent);
            if (StringUtils.isBlank(respContent))
                return AjaxResult.failed();
            JSONObject jsonObject = JSONArray.parseObject(respContent);
            String responseCode = jsonObject.getString("responseCode");
            if (!StringUtils.isBlank(responseCode) && "1000".equals(responseCode)) {
                return AjaxResult.success(jsonObject.getJSONObject("data"));
            }
        } catch (Exception e) {
            logger.error("queryCaptcha.error", e);
        }
        return AjaxResult.failed();
    }
}
