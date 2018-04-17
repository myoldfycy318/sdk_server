package com.dome.sdkserver.bq.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * UrlUtil
 *
 * @author Zhang ShanMin
 * @date 2017/3/10
 * @time 23:06
 */
public class UrlUtil {

    private static final Logger logger = LoggerFactory.getLogger(UrlUtil.class);

    /**
     * url
     *
     * @param reqUrl 请求url
     * @param params 请求参数格式为 "k1=v1&k2=v2&...k3=v3"
     * @return
     */
    public static String post(String reqUrl, String params) {
        String strLine = "";
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection connectionGetToken = (HttpURLConnection) url.openConnection();
            connectionGetToken.setRequestMethod("POST");
            connectionGetToken.setDoOutput(true);
            // 开始传送参数
            OutputStreamWriter writer = new OutputStreamWriter(connectionGetToken.getOutputStream());
            writer.write(params);
            writer.close();
            //若响应码为200则表示请求成功
            if (connectionGetToken.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connectionGetToken.getInputStream(), "utf-8"));
                while ((strLine = reader.readLine()) != null) {
                    sb.append(strLine);
                }
                return sb.toString();
            }
        } catch (Exception e) {
            logger.error("post请求异常，请求url:{},请求参数：{},异常信息为：", reqUrl, params, e);
        }
        return null;
    }

    /**
     *
     * @param reqUrl
     * @return
     */
    public static String get(String reqUrl) {
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection connectionObtainOrder = (HttpURLConnection) url.openConnection();
            connectionObtainOrder.setRequestMethod("GET");
            connectionObtainOrder.setDoOutput(true);
            if (connectionObtainOrder.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder sbLines = new StringBuilder("");
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        connectionObtainOrder.getInputStream(), "utf-8"));
                String strLine = "";
                while ((strLine = reader.readLine()) != null) {
                    sbLines.append(strLine);
                }
                return sbLines.toString();
            }
        } catch (Exception e) {
            logger.error("get请求异常,请求rul:{},异常为：", reqUrl, e);
        }
        return null;
    }

}
