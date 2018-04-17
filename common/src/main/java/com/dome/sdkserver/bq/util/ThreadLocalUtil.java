package com.dome.sdkserver.bq.util;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocalUtil
 *
 * @author Zhang ShanMin
 * @date 2016/8/8
 * @time 14:44
 */
public class ThreadLocalUtil {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object obj) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, obj);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        return map.get(key);
    }
}
