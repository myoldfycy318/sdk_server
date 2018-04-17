package com.dome.sdkserver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegexpUtil
 *
 * @author Zhang ShanMin
 * @date 2016/3/30
 * @time 14:08
 */
public class RegexpUtil {

    /**
     * 判断输入字符串是邮箱
     * @param inputStr
     * @return
     */
    public static boolean isMail(String inputStr){
        String check = "^([\\w\\.-]+)@([\\w-]+)((\\.\\w+)+)$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(inputStr);
        return matcher.matches();
    }

    /**
     * 判断输入字符串是指定位数整数
     *
     * @param inputStr
     * @param num 指定位数
     * @return
     */
    public static boolean isNumber(String inputStr, int num) {
        String check = "^[0-9]{" + num + "}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(inputStr);
        return matcher.matches();
    }
}
