package com.dome.sdkserver.util.shangsu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.shangsu.PersonTraceResp;
import com.dome.sdkserver.shangsu.PersonTradeReqInfo;
import com.dome.sdkserver.util.ApiConnector;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

/**
 * CollectionUtil
 *
 * @author Zhang ShanMin
 * @date 2016/5/4
 * @time 18:35
 */
public class YanSuCommonUtil {

    private static Logger yanSuCommonLogger = LoggerFactory.getLogger(YanSuCommonUtil.class);

    /**
     * 对map中的key值按字典顺序（从a到z，首字母相同则看第二个字母）排序并拼接
     *
     * @param map
     * @return
     */
    public static String map2String(Map<String, String> map) {
        if (map == null || map.size() <= 0)
            return null;
        Map<String, String> treeMap = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        treeMap.putAll(map);
        StringBuilder sb = new StringBuilder();
        for (String key : treeMap.keySet())
            sb.append("&").append(key).append("=").append(treeMap.get(key));
        return sb.toString().substring(1);
    }

    /**
     * 通过MD5算法进行摘要，生成了如下摘要值:D7316E8983ECCAB666E59D4D86595136
     *
     * @param str
     * @return
     */
    public static String md5Sign(String str) {
        return new String(Hex.encodeHex(DigestUtils.md5(str), false));
    }


    /**
     * 获取商肃支付方式
     *
     * @param rmbAmount 人民币金额
     * @param bqAmount  宝券金额
     * @return 0：组合支付，1：人民币，2：宝券
     */
    public static String getPayType(BigDecimal rmbAmount, BigDecimal bqAmount) {
        BigDecimal zero = new BigDecimal(0);
        if (rmbAmount.compareTo(zero) == 0 && bqAmount.compareTo(zero) != 0)
            return "2";
        if (bqAmount.compareTo(zero) == 0 && rmbAmount.compareTo(zero) != 0)
            return "1";
        return "0";
    }


    /**
     * 4.1个人业务类接口
     * 送宝劵/会员会费充值
     */
    public static PersonTraceResp personalBusiness(PersonTradeReqInfo tradeReqInfo, String requestUrl, String rsaPrivateKey) {
        try {
            List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            Map<String, String> map = new HashMap<String, String>();
            map.put("inputCharset", tradeReqInfo.getInputCharset());
            map.put("groupCode", tradeReqInfo.getGroupCode());
            map.put("userId", String.valueOf(tradeReqInfo.getUserId()));
            map.put("busiType", tradeReqInfo.getBusiType());
            map.put("tradeTime", tradeReqInfo.getTradeTime());
            if (StringUtils.isNotEmpty(tradeReqInfo.getNotifyUrl())) {
                map.put("notifyUrl", tradeReqInfo.getNotifyUrl());
            }
            map.put("outTradeNo", tradeReqInfo.getOutTradeNo());
            map.put("feeAmount", String.valueOf(tradeReqInfo.getFeeAmount()));
            if (StringUtils.isNotEmpty(tradeReqInfo.getTradeDesc())) {
                map.put("tradeDesc", Base64.encodeBase64URLSafeString(tradeReqInfo.getTradeDesc().getBytes("UTF-8")));
            }
            if (StringUtils.isNotEmpty(tradeReqInfo.getTerminal())) {
                map.put("terminal", tradeReqInfo.getTerminal());
            }
            map.put("data", JSONObject.toJSONString(tradeReqInfo.getTradeItems()));
            String map2Stirng = YanSuCommonUtil.map2String(map);
            //MD5算法摘要
            String md5Sign = YanSuCommonUtil.md5Sign(map2Stirng);
            String signCode = RSAUtil.sign(md5Sign, RSAUtil.getPrivateKey(rsaPrivateKey));
            paramsList.add(new BasicNameValuePair("signCode", signCode));
            paramsList.add(new BasicNameValuePair("signType", "RSA"));
            for (Map.Entry<String, String> entry : map.entrySet()) {
                paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            yanSuCommonLogger.info("调用个人业务类接口url:{},请求参数{}", requestUrl, JSONObject.toJSONString(paramsList));
            String responseBody = ApiConnector.post(requestUrl, paramsList, "UTF-8");
            yanSuCommonLogger.info("调用个人业务类接口url:{}返回信息:{}", tradeReqInfo.getUserId(), responseBody);
            if (StringUtils.isBlank(responseBody)) {
                return null;
            }
            return JSON.parseObject(responseBody, PersonTraceResp.class);
        } catch (Exception e) {
            yanSuCommonLogger.error("调用个人业务类接口error=", e);
            return null;
        }
    }

    /**
     * 4.3	个人业务类交易状态查询接口
     */
    public static PersonTraceResp queryStatus(PersonTradeReqInfo tradeReqInfo, String requestUrl, String rsaPrivateKey) {
        try {
            List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            Map<String, String> map = new HashMap<String, String>();
            map.put("busiType", tradeReqInfo.getBusiType());
            map.put("inputCharset", tradeReqInfo.getInputCharset());
            map.put("groupCode", tradeReqInfo.getGroupCode());
            map.put("userId", String.valueOf(tradeReqInfo.getUserId()));
            map.put("tradeTime", tradeReqInfo.getTradeTime());
            map.put("outTradeNo", tradeReqInfo.getOutTradeNo());
            String map2Stirng = YanSuCommonUtil.map2String(map);
            //MD5算法摘要
            String md5Sign = YanSuCommonUtil.md5Sign(map2Stirng);
            String signCode = RSAUtil.sign(md5Sign, RSAUtil.getPrivateKey(rsaPrivateKey));
            paramsList.add(new BasicNameValuePair("signCode", signCode));
            paramsList.add(new BasicNameValuePair("signType", "RSA"));
            for (Map.Entry<String, String> entry : map.entrySet()) {
                paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            String responseBody = ApiConnector.post(requestUrl, paramsList, "UTF-8");
            yanSuCommonLogger.info("个人业务类交易状态接口url:{},sdk订单号:{},请求参数:{},返回信息:{}", requestUrl, tradeReqInfo.getOutTradeNo(), JSONObject.toJSONString(paramsList), responseBody);
            if (StringUtils.isBlank(responseBody)) {
                return null;
            }
            return JSON.parseObject(responseBody, PersonTraceResp.class);
        } catch (Exception e) {
            yanSuCommonLogger.error("个人业务类交易状态接口异常,userid:{},sdk订单号:{},error=", tradeReqInfo.getUserId(), tradeReqInfo.getOutTradeNo(), e);
            return null;
        }
    }

}
