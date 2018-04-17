package com.dome.sdkserver.controller.login;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dome.sdkserver.bq.enumeration.SysTypeEnum;
import com.dome.sdkserver.service.channel.NewOpenChannelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.datadict.DataDictInfo;
import com.dome.sdkserver.bq.enumeration.ErrorCodeEnum;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.bq.util.IPUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.datadict.DataDictService;
import com.dome.sdkserver.service.login.QuickLoginService;
import com.dome.sdkserver.util.RSACoder;
import com.dome.sdkserver.view.AjaxResult;

@Controller
@RequestMapping("/quickLogin/")
public class QuickLoginController extends BaseController {

	@Autowired
	private QuickLoginService quickLoginService;

	@Autowired
	private DataDictService dataDictService;

	@Value("${rsa_private_key_4_sdk}")
	private String RSA_PRIVATE_KEY_4_SDK;

	private static String regMobile =
	"^1(3[0-9]|4[0-9]|5[0-9]|8[0-9]|7[0-9])\\d{8}$";
	// 港澳手机正则
    private static String otherMobile = "^[0-9]*$";

	private static String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
	private static String regIP = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";

	private static String regGmail = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

    @Autowired
    private NewOpenChannelService newOpenChannelService;

    /**
     * 获取登录token
     *
     * @param appCode
     * @param loginType
     * @param request
     * @return
     */
    @RequestMapping(value = "getLoginToken")
    @ResponseBody
    public SdkOauthResult getLoginToken(String appCode, String loginType, HttpServletRequest request) {
        SdkOauthResult result = null;

        try {
            String imsi = request.getParameter("imsi");
            if (StringUtils.isBlank(imsi)) {
                log.error(">>>>>>>>手机设备号不能为空");
                return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
            }
            User user = new User();
            user.setLoginName(imsi);
            String buId = request.getHeader("buId");
            if (StringUtils.isNotBlank(buId)) {
                user.setBuId(buId);
            }
            result = validateReqParams(request);
            if (!result.isSuccess())
                return result;
            result = checkClient(appCode);
            if (!result.isSuccess())
                return result;
            SysTypeEnum sysTypeEnum = SysTypeEnum.getSysType(request.getHeader("devType"));
            user.setSysType(sysTypeEnum != null ? sysTypeEnum.name() : SysTypeEnum.AD.name());//系统类型
            user.setChannelCode(request.getParameter("channelCode"));
            if (LoginTypeEnum.dome.name().equals(LoginTypeEnum.getLoginType(Integer.parseInt(loginType)))) {
                result = getBQLoginToken(user, appCode, request);
            }

			return result;
		} catch (Exception e) {
			log.error("非预期错误", e);
			return SdkOauthResult.failed(ErrorCodeEnum.非预期错误.code, ErrorCodeEnum.非预期错误.name());
		}

	}

	/**
	 * 游客绑定
	 * 
	 * @param appCode
	 * @param bizType
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "touristBind")
	@ResponseBody
	public SdkOauthResult touristBind(String appCode, User user, String bizType, HttpServletRequest request) {
		log.info(">>>>>>>>游客绑定");
		try {
			String loginName = user.getLoginName();
			String password = user.getPassword();
			
			//防止正则匹配时报null异常
			if(StringUtils.isBlank(loginName)){
				log.info(">>>>>登录名不能为空");
				return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
			}
            //国别码
            String countryCode  = request.getParameter("countryCode");
            log.info("传入的国别码为：{}",countryCode);
            
            //国内sdk无国别码
            if(StringUtils.isBlank(countryCode)){
            	countryCode = "86";
            }
            
			if (!checkMobile(loginName,countryCode) && !checkGmail(loginName)) {
				log.info(">>>>>>>>邮箱或手机格式不对");
				return SdkOauthResult.failed(ErrorCodeEnum.邮箱或手机格式不对.code, ErrorCodeEnum.邮箱或手机格式不对.name());
			}
			String buId = request.getHeader("buId");
			if (StringUtils.isNotBlank(buId)) {
				user.setBuId(buId);
			}
			String channelId = request.getParameter("channelId");
			if (StringUtils.isBlank(channelId)) {
				log.info(">>>>>渠道Id不能为空");
				return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
			}
			String ip = IPUtil.getIpAddr(request);
			if (!checkIp(ip)) {
				log.info(">>>>>>>>客户端ip格式错误 IP = " + ip);
				return SdkOauthResult.failed(ErrorCodeEnum.客户端IP格式错误.code, ErrorCodeEnum.客户端IP格式错误.name());
			}
			if (StringUtils.isBlank(bizType)) {
				log.error(">>>>>>>>bizType错不能为空");
				return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
			}
			String smsCode = request.getParameter("smsCode");
			if (StringUtils.isBlank(smsCode)) {
				log.error(">>>>>>>>短信验证码不能为空");
				return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
			}
			String imsi = request.getParameter("imsi");
			if (StringUtils.isBlank(imsi)) {
				log.error(">>>>>>>>手机设备号不能为空");
				return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
			}

            SdkOauthResult result = checkClient(appCode);
            if (!result.isSuccess()) {
                return result;
            }
            SysTypeEnum sysTypeEnum = SysTypeEnum.getSysType(request.getHeader("devType"));
            String sysType = sysTypeEnum != null ? sysTypeEnum.name() : SysTypeEnum.AD.name();//系统类型

			String srcPassword = new String(
					RSACoder.decryptByPrivateKey(RSACoder.decryptBASE64(password), RSA_PRIVATE_KEY_4_SDK));
			user.setPassword(srcPassword);

            JSONObject response = quickLoginService.touristBind(user, appCode, smsCode, ip, bizType, imsi, channelId,
                    countryCode, sysType);
            return parserUcResponse(response);
        } catch (Exception e) {
            log.error(">>>>>>>>非预期错误:", e);
            return SdkOauthResult.failed(ErrorCodeEnum.非预期错误.code, ErrorCodeEnum.非预期错误.name());
        }

	}

	/**
	 * 获取验证码
	 * 
	 * @param appCode
	 * @param mobile
	 * @param bizType
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getSmsCode")
	@ResponseBody
	public SdkOauthResult getSmsCode(String appCode, String mobile, String bizType, HttpServletRequest request) {
		log.info(">>>>>>>>SDK获取手机注册验证码");
		try {
            //国别码
            String countryCode  = request.getParameter("countryCode");
            log.info("传入的国别码为：{}",countryCode);
            if (StringUtils.isBlank(countryCode)) {
				countryCode = "86";
			}
			if (!checkMobile(mobile,countryCode)) {
				log.error(">>>>>>>>手机号码格式错误:");
				return SdkOauthResult.failed(ErrorCodeEnum.手机号码无效.code, ErrorCodeEnum.手机号码无效.name());
			}
			String ip = IPUtil.getIpAddr(request);
			if (!checkIp(ip)) {
				log.error(">>>>>>>>客户端ip格式错误 IP = " + ip);
				return SdkOauthResult.failed(ErrorCodeEnum.客户端IP格式错误.code, ErrorCodeEnum.客户端IP格式错误.name());
			}
			if (StringUtils.isBlank(bizType)) {
				log.error(">>>>>>>>bizType错不能为空");
				return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
			}
			SdkOauthResult result = checkClient(appCode);
			if (!result.isSuccess()) {
				return result;
			}
			JSONObject response = quickLoginService.getRegisterCode(mobile, appCode, ip, bizType);
			return parserUcResponse(response);
		} catch (Exception e) {
			log.error(">>>>>>>>获取手机验证码失败:", e);
			return SdkOauthResult.failed(ErrorCodeEnum.获取手机短信注册码失败.code, ErrorCodeEnum.获取手机短信注册码失败.name());
		}

	}
    //TODO
//    private boolean checkMobile(String mobile) {
//        if (mobile.matches(regMobile)) {
//            return true;
//        }
//        return false;
//    }

    private boolean checkMobile(String mobile, String countryCode) {
        if (mobile.matches(regMobile) && ("86").equals(countryCode)) {
            log.info("国内sdk渠道countryCode：{}", countryCode);
            return true;
        }else if(mobile.matches(otherMobile)&&!("86").equals(countryCode)){
        	log.info("海外sdk渠道countryCode：{}",countryCode);
            return true;
        }
		return false;
	}

	private boolean checkGmail(String mobile) {
		if (mobile.matches(regGmail)) {
			return true;
		}
		return false;
	}

	private boolean checkIp(String ip) {
		if (ip.matches(regIP)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取快速登录令牌
	 *
	 * @param user
	 * @param appCode
	 * @return
	 */
	private SdkOauthResult getBQLoginToken(User user, String appCode, HttpServletRequest request) {
		JSONObject response = quickLoginService.getToken(user, appCode);
		return parserUcResponse(response);
	}

    /**
     * 验证请求参数
     *
     * @param request
     * @return
     */
    private SdkOauthResult validateReqParams(HttpServletRequest request) {
        String channelCode = request.getParameter("channelCode");
        //从redis中获取渠道信息
//        if (StringUtils.isBlank(channelCode) || !BqSdkConstants.channelCodeSet.contains(channelCode))
        if (StringUtils.isBlank(channelCode) || !newOpenChannelService.containChanneCode(channelCode))
            return SdkOauthResult.failed(ErrorCodeEnum.渠道号错误.code, ErrorCodeEnum.渠道号错误.name());
        String loginType = request.getParameter("loginType");
        if (StringUtils.isBlank(loginType) || !loginType.matches("\\d"))
            return SdkOauthResult.failed(ErrorCodeEnum.登录类型错误.code, ErrorCodeEnum.登录类型错误.name());
        if (request.getRequestURI().indexOf("getUserInfoByToken") > -1) {
            String authToken = request.getParameter("authToken");
            String key = request.getParameter("key");
            String loginName = request.getParameter("loginName");
            if (StringUtils.isBlank(authToken) || StringUtils.isBlank(key) || StringUtils.isBlank(loginName)) {
                return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
            }
            if (key.length() > 64) {
                return SdkOauthResult.failed(ErrorCodeEnum.key的长度超过64位.code, ErrorCodeEnum.key的长度超过64位.name());
            }
        }
        return SdkOauthResult.success();
    }

	/**
	 * 查询是否开启邮箱注册
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectRegistWay")
	@ResponseBody
	public AjaxResult select(HttpServletRequest request) {
		try {
			String atrrCode = request.getParameter("atrrCode");
			if (StringUtils.isEmpty(atrrCode))
				return AjaxResult.failed("参数错误");
			List<String> list = new ArrayList<String>();
			list.add(atrrCode);
			List<DataDictInfo> dataDictInfo = dataDictService.getDataDictListByAttrCode(list);
			if (CollectionUtils.isEmpty(list))
				return AjaxResult.failed("查询失败");
			return AjaxResult.success(dataDictInfo);
		} catch (Exception exception) {
			log.error("insert.Exception.error", exception);
			return AjaxResult.failed("查询失败");
		}
	}

}