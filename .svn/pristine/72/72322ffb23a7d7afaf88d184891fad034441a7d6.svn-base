package com.dome.sdkserver.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * Constant
 *
 * @author Zhang ShanMin
 * @date 2016/3/29
 * @time 22:33
 */
public interface Constant {

    /**
     * 数据字典枚举
     */
    enum DataDictEnum {
        APPROVE_RESULT_NOTIFY("APPROVE_RESULT_NOTIFY", "审批结果通知"),
        PARTNER_APPLY_NOTIFY("PARTNER_APPLY_NOTIFY", "合作方申请通知"),
        MSG_NOTIFY_MAIL("MSG_NOTIFY_MAIL", "消息通知发件人邮箱");

        private DataDictEnum(String attrCode, String attrName) {
            this.attrCode = attrCode;
            this.attrName = attrName;
        }

        private String attrCode;
        private String attrName;

        public String getAttrCode() {
            return attrCode;
        }

        public String getAttrName() {
            return attrName;
        }
    }

    /**
     * 消息通知枚举
     */
    enum NotifyEnum {
        MAIL_SUBJECT("MAIL_SUBJECT", "邮件主题"),
        MAIL_CONTENT("MAIL_CONTENT", "邮件内容"),
        RECIPIENTS_ADDR("RECIPIENTS_ADDR", "收件人地址"),
        MERCHANT_INFO("MERCHANT_INFO", "商户信息"),
        APP_INFO("APP_INFO", "应用信息"),
        APPROVENOTIFY_NODE("APPROVENOTIFY_NODE", "审批结果通知节点"),
        PARTNERNOTIFY_NODE("PARTNERNOTIFY_NODE", "合作方申请通知节点");

        private NotifyEnum(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        private String name;
        private String desc;

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 通知类型
     */
    enum NotifyType {
        MAIL_MSG_NOTIFY("0", "邮件与短信通知"),
        MAIL_NOTIFY("1", "邮件通知"),
        MSG_NOTIFY("2", "短信通知");

        private NotifyType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 消息通知节点枚举
     */
    enum NotifyNodeEnum {
        ACCESS_DENIED("接入驳回", "应用%s的接入申请被驳回，请登录钱宝开放平台处理。驳回原因：%s。"),
        /**
         * 考虑到H5没有计费点，故将“请登录钱宝开放平台添加计费点。”去掉
         */
        ACCESS_SUCCESS("接入通过", "应用%s已接入成功。"),
        CHARGING_POINT_EFFECT("计费点全部生效", "应用%s计费点已生效，请登录钱宝开放平台上传包体。"),
        DYNAMIC_REINFORCE("动态加固完成", "应用%s包体动态加固完成，请登录钱宝开放平台申请测试环境联调。"),
        TEST_ADJUST_FINISH("测试环境联调通过", "应用%s测试环境联调通过，请登录钱宝开放平台申请线上环境联调。"),
        ONLINE_ADJUST_FINISH("线上环境联调通过", "应用%s线上环境联调通过，请登录钱宝开放平台申请测试。"),
        TEST_REJECT("测试驳回", "应用%s测试不通过，请登录钱宝开放平台处理。驳回原因：%s。"),
        TEST_SUCCESS("测试通过", "应用%s测试通过，请登录钱宝开放平台申请上架。"),
        SHELF_SUCCESS("上架", "恭喜应用%s上架成功，应用推广链接：可登录钱宝开放平台关注运营数据。"),
        MERCHANT_APPLY_SUBMIT("开发者信息提交完成", "您好，您有一条开发者接入申请，请尽快审批，谢谢"),
        MERCHANT_APPLY_ACCESS("申请接入", "您好，您有一条应用接入申请，请尽快审批，谢谢"),
        CHARGING_POINT_CHANGE("变更计费点", "您好，您有一条计费点变更申请，请尽快审批，谢谢"),
        ADD_CHARGING_POINT("添加计费点", "您好，您有一条计费点新增申请，请尽快审批，谢谢"),
        TEST_ADJUST_APPLY("测试环境联调申请", "您好，您有一条测试环境联调申请，请尽快操作，谢谢"),
        ONLINE_ADJUST_APPLY("线上环境联调申请", "您好，您有一条线上环境联调申请，请尽快操作，谢谢"),
        TEST_APPLY("测试申请", "您好，您有一条测试申请，请尽快操作，谢谢"),
        SHELF_APPLY("上架申请", "您好，您有一条上架申请，请尽快操作，谢谢"),
        adjust_deny("联调驳回", "您好，应用%s联调被驳回，驳回原因：%s。");

        private NotifyNodeEnum(String desc, String notityMsg) {
            this.desc = desc;
            this.notityMsg = notityMsg;
        }

        public static NotifyNodeEnum getEnum(String name) {
            if (StringUtils.isBlank(name))
                return null;
            for (NotifyNodeEnum notifyNodeEnum : NotifyNodeEnum.values()) {
                if (notifyNodeEnum.name().equals(name))
                    return notifyNodeEnum;
            }
            return null;
        }

        private String desc;
        private String notityMsg;

        public String getDesc() {
            return desc;
        }

        public String getNotityMsg() {
            return notityMsg;
        }
    }

    /**
     * 审批菜单枚举
     */
    enum ApprovalMenuEumu {
        DEVELOPER_APPROVAL("开发者审批"),
        APPLICATION_APPROVAL("应用审批"),
        CHARGING_POINT_APPROVAL("计费点审批"),
        TEST_ADJUST_APPROVAL("测试环境联调审批"),
        ONLINE_ADJUST_APPROVAL("线上环境联调审批"),
        TEST_APPROVAL("测试审批"),
        PUTAWAY_APPROVAL("上架审批");

        private ApprovalMenuEumu(String desc) {
            this.desc = desc;
        }

        private String desc;

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 商户状态枚举
     */
    enum MerchantStatus {
        PENDING_AUDIT(1, "待审核"),
        PASSED(2, "已通过"),
        FAIL(3, "未通过");

        private MerchantStatus(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        private int status;
        private String desc;

        public static String getMerchantStatusDesc(int status) {
            for (MerchantStatus merchantStatus : MerchantStatus.values()) {
                if (merchantStatus.getStatus() == status)
                    return merchantStatus.getDesc();
            }
            return null;
        }

        public int getStatus() {
            return status;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * SdkVersion发布状态
     */
    enum SdkVersionReleaseStatus {
        RELEASED(1, "已发布"),
        NOT_RELEASED(0, "未发布");

        private SdkVersionReleaseStatus(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        private int status;
        private String desc;

        public int getStatus() {
            return status;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * Sdk渠道
     */
    enum SdkChannel {
        DEFAULT(0, "CHA000001;CHA000002"),
        BAOWAN(1, "CHA000001"),//宝玩渠道
        DOME(2, "CHA000002"),//冰趣渠道
        FAXING(3, "CHA000003") //发行渠道
        ;

        public static SdkChannel getSdkChannle(Integer type) {
            if (type == null)
                return null;
            for (SdkChannel sdkChannel : SdkChannel.values()) {
                if (sdkChannel.type == type)
                    return sdkChannel;
            }
            return null;
        }

        private SdkChannel(Integer type, String code) {
            this.type = type;
            this.code = code;
        }

        private Integer type;
        private String code;

        public String getCode() {
            return code;
        }
    }

    /**
     * SdkVersion影响结果
     */
    enum SdkVersionResEnum {
        SUCCESS(0, "成功"),
        FAILED(1, "保存失败"),
        VERSION_NAME_NULL(2, "版本名称为空"),
        VERSION_NUM_NULL(3, "版本号为空"),
        VERSION_NUM_ERROR(4, "版本号只能为5位数"),
        NEED_UPDATE_VERSION_ERROR(5, "需热更版本有误"),
        NEED_UPDATE_GAME_ERROR(6, "需热更游戏有误"),
        SDK_PATH_NULL(7, "包体上传路径为空"),
        SDK_ID_NULL(8, "sdkId为空"),
        SDK_VERSION_NOT_FOUND(9, "找不到对应的sdk版本"),
        SDK_VERSION_UPDATE_FAIL(10, "sdk版本编辑失败"),
        VERSION_NUM_EXISTS(11, "版本号已存在");

        private int retCode;
        private String retMsg;

        private SdkVersionResEnum(int retCode, String retMsg) {
            this.retCode = retCode;
            this.retMsg = retMsg;
        }

        public int getRetCode() {
            return retCode;
        }

        public String getRetMsg() {
            return retMsg;
        }
    }

    /**
     * 用户注册、登录返回码
     */
    enum UserLongRegister {
        SUCCESS(0, "成功"),
        USERNAME_NULL(1, "用户名不能为空"),
        USERNAME_FORMAT_ERROR(2, "账户名只能为手机号或邮箱"),
        BIZBYPE_NULL(3, "系统异常，请重试"),//用户中心业务类型参数为空
        GET_VERIFY_CODE_ERROR(4, "请重新获取验证码"),// 获取验证码异常
        VERIFY_CODE_NULL(5, "验证码不能为空"),
        VALIDATE_VERIFY_CODE_ERROR(6, "验证码错误，请重新输入"),//验证验证码异常
        PASSWORD_NULL(7, "密码不能为空"),
        PASSWORD_INCONFORMITY(8, "密码不一致"),
        REGISTER_EXCEPITON(9, "注册失败，请重试"),
        LOGIN_EXCEPITON(10, "登录失败，请重试"),
        VERIFYCODE_TOKEN_NULL(11, "系统异常，请重试"),//验证码token不能为空
        RESET_PASSWORD_EXCEPTION(12, "重置密码失败"),
        NEW_PASSWORD_INCONFORMITY(13, "二次密码不一致"),
        MODIFY_PASSWORD_EXCEPTION(14, "修改密码失败"),
        VERIFY_TOKEN_NULL(15, "注册失败，请重试"),//VerifyToken为空
        NEWWORK_ERROR(16, "网路异常"),
        PASSWORD_FOMAT_ERROR(17, "密码只能为6-20位字母、数字、符号"),
        NOT_CURRENT_USER(18, "非当前账号");

        private UserLongRegister(int retCode, String retMsg) {
            this.retCode = retCode;
            this.retMsg = retMsg;
        }

        private int retCode;
        private String retMsg;

        public int getRetCode() {
            return retCode;
        }

        public String getRetMsg() {
            return retMsg;
        }
    }

    /**
     * 用户中心业务类型
     */
    enum UserCenterBizType {
        LOGIN(0, "登录"),
        REGISTER(1, "注册"),
        GET_USERINFO(2, "获取用户信息"),
        RESET_PASSWORD(3, "重置密码"),
        MODIFY_PASSWORD(4, "修改密码");
        private int bizBype;
        private String desc;

        public static UserCenterBizType getBizType(String serviceName) {
            if (StringUtils.isEmpty(serviceName))
                return null;
            for (UserCenterBizType userCenterBizType : UserCenterBizType.values()) {
                if (userCenterBizType.name().equalsIgnoreCase(serviceName))
                    return userCenterBizType;
            }
            return null;
        }

        private UserCenterBizType(int bizBype, String desc) {
            this.bizBype = bizBype;
            this.desc = desc;
        }

        public int getBizBype() {
            return bizBype;
        }

        public String getDesc() {
            return desc;
        }
    }


}
