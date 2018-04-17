package com.dome.sdkserver.bq.util;

/**
 * Created by admin on 2017/6/27.
 */
public class NumberUtils {
    /**
     * 验证price是否大于等于1
     * @param price
     * @return boolean
     */
    public static boolean isNumeric(String price){
        double dou =  Double.parseDouble(price);
        if (dou < 1.0){
            return false;
        }
        return true;
    }
}
