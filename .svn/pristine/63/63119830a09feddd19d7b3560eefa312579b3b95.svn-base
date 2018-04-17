package com.dome.sdkserver.bq.util;

import com.dome.sdkserver.util.MD5;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * MapUtil
 *
 * @author Zhang ShanMin
 * @date 2016/11/15
 * @time 14:03
 */
public class MapUtil {


    /**
     * 提出Val为空的参数
     *
     * @param map
     * @return
     * @throws Exception
     */
    public static Map<String, String> delValParams(Map<String, String> map) throws Exception {
        Map<String, String> result = new HashMap<String, String>();
        if (map == null || map.size() <= 0) {
            return result;
        }
        for (String key : map.keySet()) {
            String value = map.get(key);
            if (StringUtils.isBlank(value)) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 将map转为KV数组
     *
     * @param data
     * @return
     */
    public static Object[] map2KvArr(Map<String, String> data) {
        class KV {
            private String name;
            private String value;

            public KV(String name, String value) {
                this.name = name;
                this.value = value;
            }

            public String getName() {
                return name;
            }

            public String getValue() {
                return value;
            }
        }
        Object[] objects = new Object[data.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            objects[i++] = new KV(entry.getKey(), entry.getValue());
        }
        return objects;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    public static Map<String, String> convert2Map(Map<String, String[]> requestParams) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }

    public static void main(String[] args) {
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("channelCode", "CHA000004");
        signMap.put("gameOrderNo", "488");
        signMap.put("appCode", "D0001284");
        signMap.put("userId", "001");
        signMap.put("payOrigin", "wap");
        signMap.put("chargePointCode", "C0000488");
        System.out.println(MD5.md5Encode(MapUtil.createLinkString(signMap) + "&aliWapPay=" + "1qaz@WSX"));
        Map<String, String> signMap2 = new HashMap<String, String>();
        signMap2.put("appCode", "D0001284");
        signMap2.put("userId", "123456");
        signMap2.put("chargePointCode", "C0000488");
        signMap2.put("payOrigin", "wap");
        signMap2.put("gameOrderNo", "12");
        signMap2.put("channelCode", "CHA000004");
        System.out.println(MD5.md5Encode(MapUtil.createLinkString(signMap2) + "&aliWapPay=" + "1qaz@WSX"));

    }
}
