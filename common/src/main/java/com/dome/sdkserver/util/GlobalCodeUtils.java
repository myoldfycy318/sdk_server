package com.dome.sdkserver.util;

/**
 * 全局code公用类
 * @author hexiaoyi
 */
public class GlobalCodeUtils {
	/**
	 * 根据id生成编码
	 * @param preFix
	 * @param id
	 * @return
	 */
	public static String genMerchantCode(String preFix,Integer id){
		 String f = preFix + "%0" + 7 + "d";
         return String.format(f, id);
	}
}
