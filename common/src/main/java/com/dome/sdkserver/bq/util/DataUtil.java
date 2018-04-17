package com.dome.sdkserver.bq.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.util.ApiConnector;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DataUtil
 *
 * @author Zhang ShanMin
 * @date 2016/9/29
 * @time 14:26
 */
public class DataUtil {

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


    /**
     * 用于flum收集数据
     *
     * @param domain
     * @param serviceName
     * @param paramMap
     * @return
     */
    public static String collectDate(String domain, String serviceName, Map<String, String> paramMap) {
        String result = StringUtils.EMPTY;
        if (StringUtils.isBlank(serviceName) || paramMap == null)
            return result;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        paramMap.put("time", sdf.format(new Date()));
        JSONObject headers = new JSONObject();
        headers.put("serviceName", serviceName);
        StringBuilder sb = new StringBuilder(serviceName).append("#");
        sb.append(createLinkString(paramMap));
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("headers", headers);
        jsonObject.put("body", sb.toString());
        jsonArray.add(jsonObject);
        result = ApiConnector.postJson(domain, jsonArray);
        return result;
    }

    /**
     * 转化Map
     * @param paramsMap
     * @return
     */
    public static Map<String, String> mapConvert(Map<String, String[]> paramsMap) {
        Map<String, String> params = new HashMap<String, String>();
        if (paramsMap == null) {
            return params;
        }
        String[] values = null;
        for (Iterator iter = paramsMap.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            values = (String[]) paramsMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

}
