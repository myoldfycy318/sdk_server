package com.dome.sdkserver.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class MathUtils {

	/**
	 * 精确除，结果四舍五入，保留两位小数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double divideExact(double d1, double d2){
		if (d2 == 0.0) return 0.0;
		BigDecimal b = new BigDecimal(d1).divide(new BigDecimal(d2), 2, BigDecimal.ROUND_HALF_UP);
		
		return b.doubleValue();
	}
	
	/**
	 * 货币数值保留两位小数，若是10显示为10.00；若是5.5显示为5.50
	 * @param money
	 * @return
	 */
	public static String moneyStr(double money){
		// NumberFormat和SimpleDateFormat一样是非同步安全类
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		return numberFormat.format(money);
	}
	
	/**
	 * 数值的百分号展示，小数部分为0，不显示，譬如1.00展示100%，0.532展示53.2%
	 * @param d
	 * @return
	 */
	public static String percentStr(double d){
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMaximumIntegerDigits(3);
		percentFormat.setMaximumFractionDigits(2);
		
		return percentFormat.format(d);
	}
	
	public static void main(String[] args) {
		int a =1, b=3;
		System.out.println("result: " + divideExact(1, 3));
		a = 1; b =0;
		System.out.println("result: " + divideExact(a, b));
		
		System.out.println("perchent result: " + percentStr(1));
		System.out.println("perchent result: " + percentStr(0.58));
		System.out.println("perchent result: " + percentStr(0.333));
	}
}
