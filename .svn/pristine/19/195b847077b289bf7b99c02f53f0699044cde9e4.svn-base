package com.dome.sdkserver.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 字符串处理类
 * 1、请求参数中含有中文，解码转换（需要在前台页面先进行两次encodeURI(encodeURI())，且使用编码的是utf-8）
 * @author lilongwei
 *
 */
public class StringUtils {

	public static final String decodeChineseURLParam(String s){
		try {
			return URLDecoder.decode(s, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
