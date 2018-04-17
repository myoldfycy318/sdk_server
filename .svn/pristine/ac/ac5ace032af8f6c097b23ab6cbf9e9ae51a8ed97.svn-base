package com.dome.sdkserver.util;

import java.util.Date;

/**
 * 生成唯一ID
 * 
 * @author Administrator
 *
 */
public class GenOrderCode {
	
	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 0;
	private static final int ROTATION = 999999999;

	public static synchronized String next(String transType) {
		if (seq > ROTATION){
			seq = 0;
		}
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%2$05d",
				date, seq++);
		return transType+str;
	}
}
