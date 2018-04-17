package com.dome.sdkserver.common;

import java.util.regex.Pattern;

public abstract class Constants {

	/**
	 * redis失效时间一天
	 */
	public static final int REDIS_EXPIRETIME_ONEDAY = 24 * 60 * 60;
	
	/**
	 * redis失效时间一个小时
	 */
	public static final int REDIS_EXPIRETIME_ONEHOUR = 60 * 60;
	
	/**
	 * redis失效时间一分钟
	 */
	public static final int REDIS_EXPIRETIME_ONEMINUTE = 60;
	
	/**
	 * 数字正则表达式，id列入参校验时用到
	 */
	public static final String REGEX_NUM="^\\d{1,9}$";
	
	public static final Pattern PATTERN_NUM = Pattern.compile(REGEX_NUM);
	
	
	/**
	 * 货币正则表达式
	 */
	public static final String REGEX_CURRENCY="(^[1-9][0-9]{0,9}$)|(^[1-9][0-9]{0,9}\\.[0-9]{1,2}$)|(^0\\.[0-9]{1,2}$)";
	
	public static final Pattern PATTERN_CURRENCY = Pattern.compile(REGEX_CURRENCY);
	/**
	 * 简单校验日期字符串是否符合格式
	 */
	public static final String REGEX_DATE="^20\\d{2}\\-\\d{1,3}\\-\\d{1,3}$";
	
	public static final Pattern PATTERN_DATE=Pattern.compile(REGEX_DATE);
	
	/**
	 * apk包中的sdx配置文件相对路径
	 * 里面有sdk version和渠道号
	 */
	public static final String SDK_XMLPATH = "assets/DomeSdk/buildConfig.xml";
	
	public static final int DEF_PAGE_NO = 1;
	
	public static final int DEF_PAGE_SIZE = 15;
	
	/**
	 * 官方上传的冰趣APP app_id
	 */
//	public static final long BQ_APP_ID = 1000;
	
	/**
	 * 渠道编码前缀
	 */
	public static final String CHANNEL_CODE_PREFIX = "CHA";
	
	public static final String CHANNEL_DOMAIN="channel.domestore.cn";
	
	/**
	 * h5游戏标识渠道为冰趣，添加游戏时传入的类型参数
	 */
	public final static String H5_BINGQU_MARK="h5game-d";
	
	/**
	 * 游戏的应用类型编码
	 */
	public static final String APP_TYPE_GAME = "10000000";
	
	/**
	 * 冰穹默认个人商户号
	 */
	public static final int DOME_DEFAULT_MERCHANTID = 11111111;
	
	/**
	 * 冰穹默认个人商户标识字段
	 */
	public static final String DOME_DEFAULT_MERCHAT_FLAG = "source";
	
	/**
	 * 冰穹默认个人商户标识
	 */
	public static final String DOME_DEFAULT_MERCHAT_VALUE = "domeManager";
}
