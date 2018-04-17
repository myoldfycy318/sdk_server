package com.dome.sdkserver.util;

import org.apache.commons.lang3.StringUtils;

public abstract class ParamValiUtils {

	/**
	 * url为空不会报错，不为空格式不以http://或http://开头，长度超过1024会返回错误提示信息
	 * @param urls
	 * @return
	 */
	public static String valiUrl(String... urls){
		String valiResult = StringUtils.EMPTY;
		if (urls == null || urls.length == 0) return valiResult;
		for (String url : urls){
			if (StringUtils.isBlank(url)) continue; //return "url为空";
			if (!url.startsWith("http://") && !url.startsWith("https://")){
				return "url要求以http://或https://开头";
			}
			if (url.length()> 1024) return "url长度限制为1024位";
		}
		return valiResult;
	}
}
