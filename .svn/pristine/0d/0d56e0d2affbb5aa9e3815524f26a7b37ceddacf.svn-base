package com.dome.sdkserver.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 描述：
 * 数据类型转换
 * @author hexiaoyi
 */
public class DataConventUtil {
	
	/**
	 * 字符串转map
	 * @param str
	 * @return
	 */
	public static Map<String,Object> stringToMap(String str){
		String[] array = str.split(",");
		Map<String,Object> map = new HashMap<String,Object>();
		for(int i = 0; i < array.length; i++) {
			String[] data = array[i].split("=");
			map.put(data[0],data[1]);
		}
		return map;
	}
	
	
}
