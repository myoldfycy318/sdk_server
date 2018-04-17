package com.dome.sdkserver.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * LogUtil
 *
 * @author Zhang ShanMin
 * @date 2016/7/7
 * @time 11:34
 */
public class LogUtil {

    /**
     * @param args
     * @param <T>
     * @return
     */
    public static <T> String obj2String(T... args) {
        StringBuilder sb = new StringBuilder();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sb.append("operateTime=").append(df.format(new Date()));
        String fieldName = "";
        Object fieldVal = null;
        for (T t : args) {
            try {
                Field[] field = t.getClass().getDeclaredFields();
                for (int i = 0; i < field.length; i++) {
                    // 单一安全性检查
                    field[i].setAccessible(true);
                    if (field[i].getName().equals("serialVersionUID")) {
                        continue;
                    }
                    sb.append("&");
                    fieldName = field[i].getName();
                    fieldVal = field[i].get(t);
                    if (Date.class.isAssignableFrom(field[i].getType())) {
                        sb.append(fieldName).append("=");
                        sb.append(fieldVal == null ? "" : df.format((Date) fieldVal));
                    } else {
                        sb.append(fieldName).append("=");
                        sb.append(fieldVal == null ? "" : fieldVal.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    public static String map2String(Map<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sb.append("operateTime=").append(df.format(new Date()));
        if (paramMap == null)
            return sb.toString();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            sb.append("&").append(entry.getKey()).append("=");
            sb.append(entry.getValue() == null ? "" : entry.getValue());
        }
        return sb.toString();
    }

}
