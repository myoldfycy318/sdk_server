package com.dome.sdkserver.constants;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * UserCenterRepConstant
 *
 * @author Zhang ShanMin
 * @date 2016/5/14
 * @time 14:41
 */
public final class UserCenterRespConstant {

    //用户中心错误码
    private static Map<String, String> CENTER_USER_RETURN_CODE_MAP = new HashMap<String, String>() {
        {
            put("0", "成功");
            put("99", "操作失败，请重试");
            put("-1", "ip为空");
            put("-2", "ip格式错误");
            put("-3", "请输入账号");
            put("-4", "账号不存在");
            put("-5", "账号格式不正确");
            put("-6", "请输入密码");
            put("-7", "密码错误");
            put("-8", "密码应为6-16个字符，限字母、数字，区分大小写");
            put("-9", "验证码为空");
            put("-10", "验证码错误");
            put("-11", "一分钟内发送邮件验证码,暂停发送");
            put("-12", "一分钟内发送短信,暂停发送");
            put("-13", "账号被锁");
            put("-51", "该账号已注册");
            put("-56", "用户不存在");
            put("-66", "两次密码不一致");
            put("-67", "新密码与原密码相同");
            put("-68", "请输入原密码");
            put("-69", "请输入新密码");
            put("-70", "原密码有误，请重新输入");
            put("-76", "请选择头像");
            put("-77", "请选择性别");
            put("-78", "性别参数非法");
            put("-79", "请输入昵称");
            put("-80", "昵称非法");
            put("-86", "绑定失败");
        }
    };

    //验证码Token返回码
    private static Map<String, String> VERIFY_TOKEN_RETURN_MAP = new HashMap<String, String>() {
        {
            put("-36", "短信验证码token已过期");
            put("-37", "短信验证码token错误");
            put("-43", "邮件验证码token已过期");
            put("-44", "邮件验证码token为空");
            put("-45", "邮件验证码token错误");
            put("-46", "短信验证码token为空");
        }
    };

    /**
     * 获取用户中心错误码
     *
     * @param respCode
     * @return
     */
    public static String getUserCenterResDesc(String respCode) {
        return CENTER_USER_RETURN_CODE_MAP.get(respCode);
    }

    /**
     * 判断返回码是否是验证码token失效
     *
     * @param code
     * @return
     */
    public static boolean verifyTokenExpired(String code) {
        if (StringUtils.isEmpty(code))
            return false;
        return VERIFY_TOKEN_RETURN_MAP.keySet().contains(code);
    }
}
