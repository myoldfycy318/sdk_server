package com.dome.sdkserver.constants;

/**
 * BusinessType
 * 与商肃对应个人业务类型
 *
 * @author Zhang ShanMin
 * @date 2016/7/19
 * @time 15:13
 */
public enum BusinessType {

    QUERY_DEBIT_CARD_ACCOUNT("QP0101", "查询借记卡账户"),
    QUERY_BQ_ACCOUNT("QP0201", "查询宝券账户"),
    GAME_RECHARGE("P090201", "游戏充值"),
    MEMBERSHIP_FEE("P090101", "会员会费"),
    MEMBER_RECHARGE_SEND_BQ("P090301", "会员充值返券"),
    USER_DOWNLOAD_INSTALL_SEND_BQ("P090302", "用户下载安装送宝券"),
    USER_SHARE_SEND_BQ("P090303", "用户分享送宝券"),
    APP_STORE_RECHARGE_SEND_BQ("P090304", "在应用商店作为渠道充值返宝券");

    private BusinessType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private String type;
    private String desc;

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
