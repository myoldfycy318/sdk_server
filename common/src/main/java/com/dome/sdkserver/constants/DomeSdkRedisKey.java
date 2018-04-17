package com.dome.sdkserver.constants;

public final class DomeSdkRedisKey {

	public static String APP_TYPE_LIST_PREFIX = "domesdk:apptype_list:parentTypeCode";
	
	public static String APP_TYPE_CODE_PREFIX = "domesdk:apptype:appTypeCode";
	
	public static String APP_TYPE_ID_PREFIX = "domesdk:apptype:appTypeId";
	
	public static String APP_PREFIX = "domesdk:app:appcode";
	
	public static String MERCHANT_PREFIX = "domesdk:merchant:merchantcode";

    public static String  DATA_DICT_PREFIX ="domesdk:data:dict:";

    public static String  APP_USER_LOGIN_TOKEN ="domesdk:token:";

    public static String  APP_USER_LOGIN_USERINFO ="domesdk:userinfo:";
    
    /**
     * 渠道系统中渠道最近一个结算周期的结算数据
     */
    public static String CHANNEL_JS_AMOUNT="domesdk:channel:jsorder_";
    
    /**
     * 渠道的级别，一级或二级
     */
    public static String CHANNEL_PARENTID="domesdk:channel:userid_";

    /**
     * 修改回调地址次数
     */
    public static String APP_CALLBACK_URL_COUNT_PREFIX = "domesdk:app:callbackUrlCount";
    /**
     * H5  页游 激活key
     */
    public static String H5_HAND_GAME_ACTIVATE_KEY = "dome:data:h5handgame:activate";
}
