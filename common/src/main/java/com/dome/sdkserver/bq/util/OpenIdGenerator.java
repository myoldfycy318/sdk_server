package com.dome.sdkserver.bq.util;

import org.apache.commons.lang3.StringUtils;

import com.dome.sdkserver.util.EncryptUtil;

/**
 * 
 * @author niuzan
 *
 */
public class OpenIdGenerator {
	
	public static String genOpenId(String uid, String appCode)
	{
		if(StringUtils.isBlank(uid) || StringUtils.isBlank(appCode))
		{
			return null;
		}
		String initString = uid + appCode + String.valueOf(System.currentTimeMillis());
		
		return EncryptUtil.md5Hex(initString);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(genOpenId("1234556", "app001"));
	}

}
