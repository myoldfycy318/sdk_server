package com.dome.sdkserver.service;

import java.util.HashSet;
import java.util.Set;

public class BqSdkConstants {

    public static String redis_key_authCode_prefix = "bq_sdkserver_authCode";

    public static String redis_key_token_prefix = "bq_sdkserver_token";

    public static String redis_key_user_prefix = "bq_sdkserver_user";

    public static String redis_key_registerCode_prefix = "bq_sdkserver_register";

    public static int token_cache_time = 3 * 60;

    public static int user_cache_time = 3 * 60;

    public static String buId = "BQ001";

    public static String aliPayPartner = "alipay.partner";

    public static String aliPayNotifyUrl = "alipay.notifyUrl";

    public static final String PC_SYNC_NOTIFY_URL = "pc.sync.notify.url";

    public static final String WAP_SYNC_NOTIFY_URL = "wap.sync.notify.url";

    public static String aliPaySellerId = "alipay.sellerId";

    public static String aliPaySinType = "alipay.sinType";

    public static String aliPayCharset = "alipay.charset";

    public static String aliPayPublicKey = "aliPayPublicKey";

    public static String sellerPrivateKey = "sellerPrivateKey";

    public static String asyncPrivateKey = "asyncPrivateKey";

    public static String asyncNoticeMaxTryTimes = "asyncNoticeMaxTryTimes";

    public static String asyncNoticeThreadSleep = "asyncNoticeThreadSleep";

    public static String bizType = "bizType";

    public static String gameOrderNo = "gameOrderNo";

    public static String gameNotifyUrl = "gameNotifyUrl";

    public static String domeUserId = "domeUserId";

    public static String domeUserName = "domeUserName";

    public static String qbaoUid = "qbaoUid";

    public static String qbaoUserName = "qbaoUserName";

    public static String idCardNo = "card";

    public static String domesdk_login_verifyImage = "domesdk_login_verifyImage";

    public static String EXTRA_COMMON_PARAM = "extraCommonParam";

    public static final String DEF_VAL = "00";

    public static Set<String> channelCodeSet = new HashSet<String>();//sdk渠道集合
    public static Set<String> bqChannelCodes = new HashSet<String>();//冰穹渠道集合

	public static String channelCodeQbao = "CHA000001";//宝玩渠道码
	public static String channelCodeDome = "CHA000002";//冰趣渠道码
	public static String channelCodePublish = "CHA000003";//发行渠道码
	public static String channelCodeOverseas = "CHA000004";//海外渠道码
    public static String channelCodeWebPage = "CHA000005";//页游渠道码
    public static String channelCodeYouPiao = "CHA000006";//有票渠道码

    public static String weixinPayAppId = "weixinPayAppId";//应用ID =>微信开放平台审核通过的应用APPID
    public static String weixinPayMchId = "weixinPayMchId";//商户号 =>微信支付分配的商户号
    public static String weixinPayNotifyUrl = "weixinPayNotifyUrl";//通知地址 =>接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
    public static String weixinPayUnifiedorder = "weixinPayUnifiedorder";//统一下单url
    public static String weixinPaySignKey = "weixinPaySignKey";//生成sign的秘钥 微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
    public static String weixinPayOrderQuery = "weixinPayOrderQuery";//微信查询订单


    public static String weixinPayType = "weixinPayType";//微信支付方式 0是wap(通用方式) 1是app支付(部落崛起游戏定制使用)


    static {
        channelCodeSet.add(channelCodeQbao);
        channelCodeSet.add(channelCodeDome);
        channelCodeSet.add(channelCodePublish);
        channelCodeSet.add(channelCodeOverseas);
        channelCodeSet.add(channelCodeWebPage);
    }

    static {
        bqChannelCodes.add(channelCodeDome);
        bqChannelCodes.add(channelCodePublish);
    }

}
