package com.dome.sdkserver.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.bq.util.AESCoder;
import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.constants.Constant.UserCenterBizType;
import com.dome.sdkserver.constants.Constant.UserLongRegister;
import com.dome.sdkserver.constants.DomeSdkRedisKey;
import com.dome.sdkserver.constants.UserCenterRespConstant;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RedisUtil;
import com.dome.sdkserver.util.ServletUtil;
import com.dome.sdkserver.util.UuidGenerator;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.sdkserver.web.util.IPUtil;

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

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController
 *
 * @author Zhang ShanMin
 * @date 2016/5/13
 * @time 16:54
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PropertiesUtil domainConfig;
    @Resource
    private RedisUtil redisUtil;

    //登录token缓存默认失效时间
    private int CACHE_LOGIN_TOKEN_TIMEOUT = 2 *60 * 60;
    
    /**
     * 去掉缓存用户信息到cookie，所有验证信息以redis读出来的数据为依据
     */
//    public static final String COOKIE_NAME_USERINFO = "dome_userinfo";
    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public AjaxResult getUserInfo(HttpServletRequest request) {
//    	User user= new User();
//		user.setUserId("1");
//		user.setLoginName("13913942013");
//		user.setPassword("123456");
//	      Map<String, Object> map = new HashMap<String, Object>();
//	      int level=1;
//	      map.put("level", Integer.valueOf(level));
//	      map.put("user", user);
//	      return AjaxResult.success(map);
        try {
        	/**
        	 * userinfo缓存在redis中
        	 */
            String accessToken = ServletUtil.getCookieValue(request, "dome_access_token");
            String userinfoText = null;
            if (StringUtils.isEmpty(accessToken))
                return AjaxResult.accessReject();
//            HttpSession session = request.getSession(false);
//            if (session == null || session.getAttribute("USER") == null)
//                return AjaxResult.accessReject();
            String token = redisUtil.get(DomeSdkRedisKey.APP_USER_LOGIN_TOKEN + accessToken);
            String userInfoKey = DomeSdkRedisKey.APP_USER_LOGIN_USERINFO +  accessToken;
            /**
             * 用户信息以redis缓存数据为准
             */
            userinfoText = redisUtil.get(userInfoKey);
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userinfoText)) {
            	return AjaxResult.accessReject();
            }
            
            User user = JSON.parseObject(userinfoText, User.class);
            logger.info("getUserInfo.getUserFromSession.result:{}", JSON.toJSON(user));
            if (user == null || StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getUserId())) {
                return AjaxResult.accessReject();
            }
            // 若缓存时间没到期，时间小于60min，重新设置。前提是几乎每一个与服务器端的操作都在调本接口
            // 这里考虑到平均包体上传时间保证在60min之内
            long timeout = redisUtil.ttl(userInfoKey);
            if (timeout<60*60){
            	redisUtil.setex(userInfoKey, CACHE_LOGIN_TOKEN_TIMEOUT, userinfoText);
            	redisUtil.setex(DomeSdkRedisKey.APP_USER_LOGIN_TOKEN + accessToken, CACHE_LOGIN_TOKEN_TIMEOUT, token);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            // 保存渠道基本信息
            String parentIdText=redisUtil.get(DomeSdkRedisKey.CHANNEL_PARENTID +user.getUserId());
            int level=2;
            if (StringUtils.isNotEmpty(parentIdText)){
            	level=(Long.parseLong(parentIdText)>0 ? 2:1);
            }
            map.put("level", Integer.valueOf(level));
            map.put("user", user);
            return AjaxResult.success(map);
        } catch (Exception e) {
            logger.error("getUserInfo.error=", e);
            return AjaxResult.accessReject();
        }
    }


    /**
     * 判断操作用户是否为登录用户
     * @param request
     * @param userName
     * @return
     */
    private boolean isCurrentUser(HttpServletRequest request, String userName) {
//        HttpSession session = request.getSession(false);
//        if (session == null)
//           throw new RuntimeException("获取session为空");
//        User user = (User) session.getAttribute("USER");
//        if (user == null || StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getUserId()))
//            throw new RuntimeException("获取session用户信息为空");
        String token = ServletUtil.getCookieValue(request, "dome_access_token");
        String accessToken = redisUtil.get(DomeSdkRedisKey.APP_USER_LOGIN_TOKEN + token);
        String userInfoText = redisUtil.get(DomeSdkRedisKey.APP_USER_LOGIN_USERINFO + token);
        if (StringUtils.isEmpty(userInfoText) || StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(token)) {
        	return false;
        }
        
        User user = JSON.parseObject(userInfoText, User.class);
        return user.getLoginName().equals(userName);
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult modifyPassword(HttpServletRequest request, HttpServletResponse response) {
        String userCenterRespCode = "";
        try {
            Cookie cookie = ServletUtil.getCookie(request, "dome_access_token");
            if (cookie == null)
                return AjaxResult.accessReject();
            String resetPasswordUrl = domainConfig.getString("usercenter.modify.password.url");
            String userName = request.getParameter("userName");
            //验证用户名
            AjaxResult ajaxResult = validateUserName(userName);
            if (AjaxResult.CODE_SUCCESS != ajaxResult.getResponseCode())
                return ajaxResult;
            /**
             * 修改密码时也传入了旧密码，使用cookie简单校验一下用户名和登陆用户名是否相同
             * 修改密码需要用户登陆
             */
            if(!isCurrentUser(request, userName))
                return AjaxResult.failed(UserLongRegister.NOT_CURRENT_USER.getRetCode(), UserLongRegister.NOT_CURRENT_USER.getRetMsg());
            String passWord = request.getParameter("passWord");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            if (StringUtils.isEmpty(passWord) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(confirmPassword))
                return AjaxResult.failed(UserLongRegister.PASSWORD_NULL.getRetCode(), UserLongRegister.PASSWORD_NULL.getRetMsg());
            if (!newPassword.equals(confirmPassword))
                return AjaxResult.failed(UserLongRegister.NEW_PASSWORD_INCONFORMITY.getRetCode(), UserLongRegister.NEW_PASSWORD_INCONFORMITY.getRetMsg());
            //http://www.bqiong.com/open/modifyPassword?passport=18061651013&passwordOld=xxx&passwordNew=xxx&clientId=xxx
            //验证密码
            ajaxResult = validatePassWord(passWord);
            if (AjaxResult.CODE_SUCCESS != ajaxResult.getResponseCode())
                return ajaxResult;
            ajaxResult = validatePassWord(newPassword);
            if (AjaxResult.CODE_SUCCESS != ajaxResult.getResponseCode())
                return ajaxResult;
            passWord = AESCoder.getEncryptResult(passWord, domainConfig.getString("usercenter.aes.publickey"));
            newPassword = AESCoder.getEncryptResult(newPassword, domainConfig.getString("usercenter.aes.publickey"));
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("passport", userName));
            pairs.add(new BasicNameValuePair("passwordOld", passWord));//passWord aes加密
            pairs.add(new BasicNameValuePair("passwordNew", newPassword));//newPassword aes加密
            logger.info("modifyPassword.requestUrl:{},请求参数:{}", resetPasswordUrl, JSON.toJSONString(pairs));
            String respContent = ApiConnector.post(resetPasswordUrl, pairs);
            logger.info("modifyPassword.respContent:{}", respContent);
            JSONObject jsonObject = JSONObject.parseObject(respContent);
            userCenterRespCode = jsonObject.getString("code");
            if ("0".equals(userCenterRespCode)) {
                //ServletUtil.delCookie(response, cookie, request.getServerName());
                removeLoginCache(cookie, request, response);
                return AjaxResult.success();
            }
        } catch (Exception e) {
            logger.error("user.modifyPassword.error", e);
            return AjaxResult.failed(UserLongRegister.MODIFY_PASSWORD_EXCEPTION.getRetCode(), UserLongRegister.MODIFY_PASSWORD_EXCEPTION.getRetMsg());
        }
        return AjaxResult.failed(UserCenterRespConstant.getUserCenterResDesc(userCenterRespCode));
    }


    /**
     * 重置密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult resetPassword(HttpServletRequest request, HttpServletResponse response) {
        String userCenterRespCode = "";
        try {
            String resetPasswordUrl = domainConfig.getString("usercenter.reset.password.url");
            String userName = request.getParameter("userName");
            //验证用户名
            AjaxResult ajaxResult = validateUserName(userName);
            if (AjaxResult.CODE_SUCCESS != ajaxResult.getResponseCode())
                return ajaxResult;
            String resetPassword = request.getParameter("resetPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            if (StringUtils.isEmpty(resetPassword) || StringUtils.isEmpty(confirmPassword))
                return AjaxResult.failed(UserLongRegister.PASSWORD_NULL.getRetCode(), UserLongRegister.PASSWORD_NULL.getRetMsg());
            if (!resetPassword.equals(confirmPassword))
                return AjaxResult.failed(UserLongRegister.PASSWORD_INCONFORMITY.getRetCode(), UserLongRegister.PASSWORD_INCONFORMITY.getRetMsg());
            String verifyToken = request.getParameter("verifyToken");
            if (StringUtils.isEmpty(verifyToken))
                return AjaxResult.failed(UserLongRegister.VERIFYCODE_TOKEN_NULL.getRetCode(), UserLongRegister.VERIFYCODE_TOKEN_NULL.getRetMsg());
            //验证密码
            ajaxResult = validatePassWord(resetPassword);
            if (AjaxResult.CODE_SUCCESS != ajaxResult.getResponseCode())
                return ajaxResult;
            //http://www.bqiong.com/open/resetPassword?passport=18061651013&resetPassword=xxx&confirmPassword=xxx&clientId=xxx&verifyToken=xxx
            resetPassword = AESCoder.getEncryptResult(resetPassword, domainConfig.getString("usercenter.aes.publickey"));
            confirmPassword = AESCoder.getEncryptResult(confirmPassword, domainConfig.getString("usercenter.aes.publickey"));
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("passport", userName));
            pairs.add(new BasicNameValuePair("resetPassword", resetPassword));//resetPassword aes加密
            pairs.add(new BasicNameValuePair("confirmPassword", confirmPassword));//confirmPassword aes加密
            pairs.add(new BasicNameValuePair("verifyToken", verifyToken));
            logger.info("resetPassword.requestUrl:{},请求参数:{}", resetPasswordUrl, JSON.toJSONString(pairs));
            String respContent = ApiConnector.post(resetPasswordUrl, pairs);
            logger.info("resetPassword.respContent:{}", respContent);
            JSONObject jsonObject = JSONObject.parseObject(respContent);
            userCenterRespCode = jsonObject.getString("code");
            if ("0".equals(userCenterRespCode)) {
                Cookie cookie = ServletUtil.getCookie(request, "dome_access_token");
                if (cookie != null) {
                    //ServletUtil.delCookie(response, cookie, request.getServerName());
                    removeLoginCache(cookie, request, response);
                }
                return AjaxResult.success();
            }
        } catch (Exception e) {
            logger.error("user.resetPassword.error", e);
            return AjaxResult.failed(UserLongRegister.RESET_PASSWORD_EXCEPTION.getRetCode(), UserLongRegister.RESET_PASSWORD_EXCEPTION.getRetMsg());
        }
        return AjaxResult.failed(UserCenterRespConstant.getUserCenterResDesc(userCenterRespCode));
    }

    /**
     * 根据accessToken获取用户信息
     *
     * @param accessToken 冰穹uc下发的token(加密)
     * @return
     */
    private User getUserByToken(String accessToken) {
        String queryUserInfoUrl = domainConfig.getString("usercenter.query.user.info.url");
        if (StringUtils.isEmpty(accessToken))
            return null;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("accessToken", AESCoder.getEncryptResult(accessToken, domainConfig.getString("usercenter.aes.publickey").trim())));
        logger.info("queryUserInfoUrl.requestUrl:{},请求参数:{}", queryUserInfoUrl, JSON.toJSONString(pairs));
        // http://www.bqiong.com/open/getByToken?accessToken=xxxx&clientId=xxx
        String respContent = ApiConnector.post(queryUserInfoUrl, pairs);
        logger.info("queryUserInfoUrl.respContent:{}", respContent);
        JSONObject jsonObject = JSONObject.parseObject(respContent);
        if ("0".equals(jsonObject.getString("code"))) {
            User user = new User();
            user.setLoginName(jsonObject.getJSONObject("data").getString("userName"));
            user.setUserId(jsonObject.getJSONObject("data").getString("userId"));
            return user;
        }
        return null;
    }


    /**
     * 登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult login(HttpServletRequest request, HttpServletResponse response) {
        String userCenterRespCode = "";
        try {
            String usercenterLoginUrl = domainConfig.getString("usercenter.login.url");
            String userName = request.getParameter("userName");
            //验证用户名
            AjaxResult ajaxResult = validateUserName(userName);
            if (AjaxResult.CODE_SUCCESS != ajaxResult.getResponseCode())
                return ajaxResult;
            String passWord = request.getParameter("password");
            if (StringUtils.isEmpty(passWord))
                return AjaxResult.failed(UserLongRegister.PASSWORD_NULL.getRetCode(), UserLongRegister.PASSWORD_NULL.getRetMsg());
            //验证密码
            ajaxResult = validatePassWord(passWord);
            if (AjaxResult.CODE_SUCCESS != ajaxResult.getResponseCode())
                return ajaxResult;
            // http://www.bqiong.com/open/login?passport=18061651000&password=3A1A029100B5DD3AD4863E038F7B4651&clientId=xxx
            passWord = AESCoder.getEncryptResult(passWord, domainConfig.getString("usercenter.aes.publickey"));
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("passport", userName));
            pairs.add(new BasicNameValuePair("password", passWord));//password aes加密
            logger.info("login.requestUrl:{},请求参数:{}", usercenterLoginUrl, JSON.toJSONString(pairs));
            String respContent = ApiConnector.post(usercenterLoginUrl, pairs);
            logger.info("login.respContent:{}", respContent);
            JSONObject jsonObject = JSONObject.parseObject(respContent);
            userCenterRespCode = jsonObject.getString("code");
            if ("0".equals(userCenterRespCode)) {
                String accessToken = jsonObject.getJSONObject("data").getString("accessToken");
                User user = getUserByToken(accessToken);
                logger.info("user.getUserByToken.result:{}", JSON.toJSON(user));
                if (user == null || StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getUserId()))
                    return AjaxResult.failed(UserLongRegister.LOGIN_EXCEPITON.getRetCode(), UserLongRegister.LOGIN_EXCEPITON.getRetMsg());
                cacheLogin(accessToken, user, request, response);
                //redisUtil.setex(DomeSdkRedisKey.APP_USER_LOGIN_USERINFO+ uuidStr, CACHE_LOGIN_TOKEN_TIMEOUT, JSON.toJSONString(user));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("user", user);
                return AjaxResult.success(map);
            }
        } catch (Exception e) {
            logger.error("user.login.error", e);
            return AjaxResult.failed(UserLongRegister.LOGIN_EXCEPITON.getRetCode(), UserLongRegister.LOGIN_EXCEPITON.getRetMsg());
        }
        return AjaxResult.failed(UserCenterRespConstant.getUserCenterResDesc(userCenterRespCode));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult register(HttpServletRequest request, HttpServletResponse response) {
        String userCenterRespCode = "";
        try {
            String usercenterRegisterUrl = domainConfig.getString("usercenter.register.url");
            String userName = request.getParameter("userName");
            //验证用户名
            AjaxResult ajaxResult = validateUserName(userName);
            if (AjaxResult.CODE_SUCCESS != ajaxResult.getResponseCode())
                return ajaxResult;
            String verifyToken = request.getParameter("verifyToken");
            if (StringUtils.isEmpty(verifyToken))
                return AjaxResult.failed(UserLongRegister.VERIFY_TOKEN_NULL.getRetCode(), UserLongRegister.VERIFY_TOKEN_NULL.getRetMsg());
            String passWord = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            if (StringUtils.isEmpty(passWord) || StringUtils.isEmpty(confirmPassword))
                return AjaxResult.failed(UserLongRegister.PASSWORD_NULL.getRetCode(), UserLongRegister.PASSWORD_NULL.getRetMsg());
            if (!passWord.equals(confirmPassword))
                return AjaxResult.failed(UserLongRegister.PASSWORD_INCONFORMITY.getRetCode(), UserLongRegister.PASSWORD_INCONFORMITY.getRetMsg());
            //验证密码
            ajaxResult = validatePassWord(passWord);
            if (AjaxResult.CODE_SUCCESS != ajaxResult.getResponseCode())
                return ajaxResult;
            //http://www.bqiong.com/open/register?passport=18061651000&password=3A1A029100B5DD3AD4863E038F7B4651&clientId=xxx&code=xxxxxx
            passWord = AESCoder.getEncryptResult(passWord, domainConfig.getString("usercenter.aes.publickey").trim());
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("passport", userName));
            pairs.add(new BasicNameValuePair("password", passWord));//password aes加密
            pairs.add(new BasicNameValuePair("verifyToken", verifyToken));
            logger.info("register.requestUrl:{},请求参数:{}", usercenterRegisterUrl, JSON.toJSONString(pairs));
            String respContent = ApiConnector.post(usercenterRegisterUrl, pairs);
            logger.info("register.respContent:{}", respContent);
            if (StringUtils.isEmpty(respContent)) {
                logger.error("usercenter.Register返回空");
                return AjaxResult.failed(UserLongRegister.NEWWORK_ERROR.getRetCode(), UserLongRegister.NEWWORK_ERROR.getRetMsg());
            }
            JSONObject jsonObject = JSONObject.parseObject(respContent);
            userCenterRespCode = jsonObject.getString("code");
            if ("0".equals(userCenterRespCode)) {
                String accessToken = jsonObject.getJSONObject("data").getString("accessToken");
                User user = getUserByToken(accessToken);
                logger.info("user.getUserByToken.result:{}", JSON.toJSON(user));
                if (user == null || StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getUserId()))
                    return AjaxResult.failed(UserLongRegister.LOGIN_EXCEPITON.getRetCode(), UserLongRegister.LOGIN_EXCEPITON.getRetMsg());
                cacheLogin(accessToken, user, request, response);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("user", user);
                return AjaxResult.success(map);
            }
        } catch (Exception e) {
            logger.error("user.register.error", e);
            return AjaxResult.failed(UserLongRegister.REGISTER_EXCEPITON.getRetCode(), UserLongRegister.REGISTER_EXCEPITON.getRetMsg());
        }
        return AjaxResult.failed(UserCenterRespConstant.getUserCenterResDesc(userCenterRespCode));
    }


	private void cacheLogin(String accessToken, User user,
			HttpServletRequest request, HttpServletResponse response) {
		String uuidStr = UuidGenerator.getUuid();
		ServletUtil.addCookieByDefaultTime(response, "dome_access_token", uuidStr, request.getServerName());
		ServletUtil.addCookieByDefaultTime(response, "dome_access_token", uuidStr, Constants.CHANNEL_DOMAIN);
		//WebUtils.setSessionAttribute(request, "USER", user);
		//ServletUtil.addCookieByDefaultTime(response, COOKIE_NAME_USERINFO, JSON.toJSONString(user), request.getServerName());
		redisUtil.setex(DomeSdkRedisKey.APP_USER_LOGIN_TOKEN + uuidStr, CACHE_LOGIN_TOKEN_TIMEOUT, accessToken);
		redisUtil.setex(DomeSdkRedisKey.APP_USER_LOGIN_USERINFO + uuidStr, CACHE_LOGIN_TOKEN_TIMEOUT, JSON.toJSONString(user));
	}

    /**
     * 获取验证码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getValidateCode", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getValidateCode(HttpServletRequest request) {
        String userCenterRespCode = "";
        try {
            String getValidateCodeUrl = domainConfig.getString("query.usercenter.verifycode.url");
            UserCenterBizType userCenterBizType = UserCenterBizType.getBizType(request.getParameter("bizType"));
            if (userCenterBizType == null)
                return AjaxResult.failed(UserLongRegister.BIZBYPE_NULL.getRetCode(), UserLongRegister.BIZBYPE_NULL.getRetMsg());
            String userName = request.getParameter("userName");
            //验证用户名
            AjaxResult ajaxResult = validateUserName(userName);
            if (AjaxResult.CODE_SUCCESS != ajaxResult.getResponseCode())
                return ajaxResult;
            // http://www.bqiong.com/open/getCode?assport=18061651000&clientId=xxx&bizType=3&userIp=xxx
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("passport", userName));
            pairs.add(new BasicNameValuePair("bizType", userCenterBizType.getBizBype() + ""));//业务类型
            pairs.add(new BasicNameValuePair("userIp", IPUtil.getIpAddr(request)));
            logger.info("getValidateCode.requestUrl:{},请求参数:{}", getValidateCodeUrl, JSON.toJSONString(pairs));
            String respContent = ApiConnector.post(getValidateCodeUrl, pairs);
            logger.info("getValidateCode.respContent:{}", respContent);
            JSONObject jsonObject = JSONObject.parseObject(respContent);
            userCenterRespCode = jsonObject.getString("code");
            if ("0".equals(userCenterRespCode))
                return AjaxResult.success();
        } catch (Exception e) {
            logger.error("getValidateCode.error", e);
            return AjaxResult.failed(UserLongRegister.GET_VERIFY_CODE_ERROR.getRetCode(), UserLongRegister.GET_VERIFY_CODE_ERROR.getRetMsg());
        }
        return AjaxResult.failed(UserCenterRespConstant.getUserCenterResDesc(userCenterRespCode));
    }

    /**
     * 验证验证码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/validateVerifyCode", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult validateVerifyCode(HttpServletRequest request) {
        AjaxResult ajaxResult = null;
        String userCenterRespCode = "";
        try {
            String validateVerifycodeUrl = domainConfig.getString("validate.usercenter.verifycode.url");
            UserCenterBizType userCenterBizType = UserCenterBizType.getBizType(request.getParameter("bizType"));
            if (userCenterBizType == null)
                return AjaxResult.failed(UserLongRegister.BIZBYPE_NULL.getRetCode(), UserLongRegister.BIZBYPE_NULL.getRetMsg());
            String userName = request.getParameter("userName");
            //验证用户名
            ajaxResult = validateUserName(userName);
            if (AjaxResult.CODE_SUCCESS != ajaxResult.getResponseCode())
                return ajaxResult;
            String verifycode = request.getParameter("verifycode");
            if (StringUtils.isEmpty(verifycode))
                return AjaxResult.failed(UserLongRegister.VERIFY_CODE_NULL.getRetCode(), UserLongRegister.VERIFY_CODE_NULL.getRetMsg());
            //http://www.bqiong.com/open/verifyCode? passport=18061651000&clientId=xxx&bizType=3&code=xxx&userIp=xxx
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("passport", userName));
            pairs.add(new BasicNameValuePair("bizType", userCenterBizType.getBizBype() + ""));//业务类型
            pairs.add(new BasicNameValuePair("code", verifycode));
            pairs.add(new BasicNameValuePair("userIp", IPUtil.getIpAddr(request)));
            logger.info("validateVerifyCode.requestUrl:{},请求参数:{}", validateVerifycodeUrl, JSON.toJSONString(pairs));
            String respContent = ApiConnector.post(validateVerifycodeUrl, pairs);
            logger.info("validateVerifyCode.respContent:{}", respContent);
            JSONObject jsonObject = JSONObject.parseObject(respContent);
            userCenterRespCode = jsonObject.getString("code");
            if ("0".equals(userCenterRespCode)) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("smsToken", jsonObject.getJSONObject("data").getString("verifyCodeToken"));
                return AjaxResult.success(map);
            }
        } catch (Exception e) {
            logger.error("validateVerifyCode.error", e);
            return AjaxResult.failed(UserLongRegister.VALIDATE_VERIFY_CODE_ERROR.getRetCode(), UserLongRegister.VALIDATE_VERIFY_CODE_ERROR.getRetMsg());
        }
        return AjaxResult.failed(UserCenterRespConstant.getUserCenterResDesc(userCenterRespCode));
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = ServletUtil.getCookie(request, "dome_access_token");
        if (cookie != null) {
            try {
                //ServletUtil.delCookie(response, cookie, request.getServerName());
                
                HttpSession session = request.getSession();
                session.invalidate();
                String logOutUrl = domainConfig.getString("usercenter.logout.url");
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                final String key = DomeSdkRedisKey.APP_USER_LOGIN_TOKEN + cookie.getValue();
				String token = redisUtil.get(key);
                if (StringUtils.isEmpty(token)) return AjaxResult.failed("session timeout");
                removeLoginCache(cookie, request, response);
                String accessToken = AESCoder.getEncryptResult(token, domainConfig.getString("usercenter.aes.publickey"));
                pairs.add(new BasicNameValuePair("accessToken", accessToken));
                //http://www.bqiong.com/open/logout?accessToken=xxxx
                logger.info("validateVerifyCode.requestUrl:{},请求参数:{}", logOutUrl, JSON.toJSONString(pairs));
                String respContent = ApiConnector.post(logOutUrl, pairs);
                logger.info("user.logout.respContent:{}", respContent);
                JSONObject jsonObject = JSONObject.parseObject(respContent);
                if ("0".equals(jsonObject.getString("code"))) {
                    return AjaxResult.success();
                }
            } catch (Exception e) {
                logger.error("uesr.logout.error=", e);
            }
        }
        return AjaxResult.success();
    }

    /**
     * 验证用户名
     *
     * @param userName
     * @return
     */
    private AjaxResult validateUserName(String userName) {
        //用户名为空
        if (StringUtils.isEmpty(userName))
            return AjaxResult.failed(UserLongRegister.USERNAME_NULL.getRetCode(), UserLongRegister.USERNAME_NULL.getRetMsg());
        //phonePattern = Pattern.compile("^13\\d{9}||15[8,9]\\d{8}||18\\d{9}$");
        // passwordPattern = Pattern.compile("[(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*)(?=.*[!@#$%^*].*).]{8,32}");
        if (!(userName.matches("^1\\d{10}$") || userName.matches("^([\\w\\.-]+)@([\\w-]+)((\\.\\w+)+)$")))
            return AjaxResult.failed(UserLongRegister.USERNAME_FORMAT_ERROR.getRetCode(), UserLongRegister.USERNAME_FORMAT_ERROR.getRetMsg());
        return AjaxResult.success();
    }

    /**
     * 密码只能为6-20位字母、数字、符号
     *
     * @param password
     * @return
     */
    private AjaxResult validatePassWord(String password) {
        if (!password.matches("^\\S{6,20}$"))
            return AjaxResult.failed(UserLongRegister.PASSWORD_FOMAT_ERROR.getRetCode(), UserLongRegister.PASSWORD_FOMAT_ERROR.getRetMsg());
        return AjaxResult.success();
    }
    
    private void removeLoginCache(Cookie tokenCookie, HttpServletRequest request, HttpServletResponse response){
    	String host = request.getServerName();
    	if (tokenCookie != null){
    		host=Constants.CHANNEL_DOMAIN;
    		ServletUtil.delCookie(response, tokenCookie, host);
//    		host="channel.domestore.cn";
//    		ServletUtil.delCookie(response, tokenCookie, host);
    		String token = tokenCookie.getValue();
    		redisUtil.del(DomeSdkRedisKey.APP_USER_LOGIN_TOKEN + token);
    		redisUtil.del(DomeSdkRedisKey.APP_USER_LOGIN_USERINFO + token);
    	}
    }
}
