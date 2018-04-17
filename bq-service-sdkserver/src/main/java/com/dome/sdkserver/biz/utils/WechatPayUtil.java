package com.dome.sdkserver.biz.utils;

import com.dome.sdkserver.metadata.entity.bq.pay.WeChatEntity;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by ym on 2017/8/21.
 */
public class WechatPayUtil {
    protected static final Logger log = LoggerFactory.getLogger(WechatPayUtil.class);

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    /**
     * 时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 获取客户端ip
     *
     * @param request
     * @return
     */
    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        int index = ip.indexOf(",");
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : index != -1  ? ip.substring(0,index) : ip;
    }

    /**
     * 生成sign
     *
     * @param parameters
     * @param signKey    key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
     * @return
     */
    public static String createSign(Map<String, String> parameters, String signKey) {
        Set set = parameters.keySet();
        String[] arr = new String[parameters.size()];
        Iterator it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            arr[i] = it.next().toString();
            i++;
        }
        Arrays.sort(arr);
        StringBuffer stringA = new StringBuffer();
        boolean flag = false;
        for (int j = 0; j < arr.length; j++) {
            if (flag) {
                stringA.append("&");
            }
            stringA.append(arr[j]).append("=").append(parameters.get(arr[j]));
            flag = true;
        }
        String stringSignTemp = stringA + "&key=" + signKey;
        String sign = MD5.md5Encode(stringSignTemp).toUpperCase();
        parameters.put("sign", sign);
        return sign;
    }

    //通过xml 发给微信消息
    public static String setXml(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]>" +
                "</return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }
    /**
     * 微信下单，map to xml
     * @param params 参数
     * @return String
     */
    public static String maptoXml(Map<String, String> params) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key   = entry.getKey();
            String value = entry.getValue();
            // 略过空值
            if (StringUtils.isBlank(value)) continue;
            xml.append("<").append(key).append(">");
            xml.append(entry.getValue());
            xml.append("</").append(key).append(">");
        }
        xml.append("</xml>");
        return xml.toString();
    }
    public static Map xmltoMap(String xml) {
        try {
            Map map = new HashMap();
            Document document = DocumentHelper.parseText(xml);
            Element nodeElement = document.getRootElement();
            List node = nodeElement.elements();
            for (Iterator it = node.iterator(); it.hasNext(); ) {
                Element elm = (Element) it.next();
                map.put(elm.getName(), elm.getText());
                elm = null;
            }
            node = null;
            nodeElement = null;
            document = null;
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 支付异步通知时校验sign
     * @param params 参数
     * @param paternerKey 支付密钥
     * @return {boolean}
     */
    public static boolean verifyNotifySign(Map<String, String> params, String paternerKey){
        String sign = params.get("sign");
        params.remove("sign");
        String localSign = WechatPayUtil.createSign(params, paternerKey);
        return sign.equals(localSign);
    }


    /**
     * 调用微信 统一下单接口 返回prepay_id 或 code_url
     *
     * @param weChatEntity
     * @param payConfig
     * @return result
     */
    public static String weixinPayUnifiedorder(WeChatEntity weChatEntity, Map<String, String> payConfig) throws Exception {
        String result = "";
        Map<String, String> data = new HashMap<String, String>();
        String appId = payConfig.get(BqSdkConstants.weixinPayAppId);
        String mchId = payConfig.get(BqSdkConstants.weixinPayMchId);
        String signKey = payConfig.get(BqSdkConstants.weixinPaySignKey);
        //trade_type=JSAPI时（即公众号支付），微信公众号支付有单独的appid、mchId、签名key
        if ("JSAPI".equals(weChatEntity.getTradeType())) {
            data.put("openid", weChatEntity.getOpenId());
            appId = weChatEntity.getAppId();
            mchId = weChatEntity.getMchId();
            signKey = weChatEntity.getSignKey();
        }
        data.put("appid", appId);
        data.put("mch_id", mchId);
        data.put("nonce_str", weChatEntity.getNonceStr());
        data.put("body", weChatEntity.getBody());
        data.put("out_trade_no", weChatEntity.getOutTradeNo());
        data.put("total_fee", weChatEntity.getTotalFee() + "");
        data.put("spbill_create_ip", weChatEntity.getSpBillCreateIp());
        data.put("notify_url", payConfig.get(BqSdkConstants.weixinPayNotifyUrl));
        data.put("trade_type", weChatEntity.getTradeType());

        String sign = WechatPayUtil.createSign(data, signKey);
        data.put("sign", sign);
        String urlParam = WechatPayUtil.maptoXml(data);
        log.info("微信统一下单 xml参数 --> urlParam:{}", urlParam);
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setReqContent(payConfig.get(BqSdkConstants.weixinPayUnifiedorder));
        String resContent = "";
        if (httpClient.callHttpPost(payConfig.get(BqSdkConstants.weixinPayUnifiedorder), urlParam)) {
            resContent = httpClient.getResContent();
            Map<String, Object> map = WechatPayUtil.xmltoMap(resContent);
            if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
                if (weChatEntity.getTradeType().equals("APP")) {//APP
                    result = map.get("prepay_id").toString();
                } else if (weChatEntity.getTradeType().equals("MWEB")) {//H5 wap
                    result = map.get("mweb_url").toString();// + "&redirect_url=" + URLEncoder.encode("http://1s6z265501.51mypc.cn/index.htm?out_trade_no=" + orderNo, "UTF-8");
                } else if (weChatEntity.getTradeType().equals("NATIVE")) {//原生扫码支付
                    result = map.get("code_url").toString();
                } else if (weChatEntity.getTradeType().equals("JSAPI")) {
                    result = map.get("prepay_id").toString();
                }
            } else {
                log.info("微信调用统一下单接口失败,返回值 -----> map:{}", resContent);
            }
        }
        return result;
    }


    /**
     * 微信订单查询接口
     *
     * @param orderNo
     * @param payConfig
     * @return
     * @throws Exception
     */
    public static boolean weiXinOrderQuery(String orderNo, Map<String, String> payConfig) throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", payConfig.get(BqSdkConstants.weixinPayAppId));
        data.put("mch_id", payConfig.get(BqSdkConstants.weixinPayMchId));
        data.put("out_trade_no", orderNo);
        data.put("nonce_str", WechatPayUtil.generateNonceStr());
        String signKey = payConfig.get(BqSdkConstants.weixinPaySignKey);
        String sign = WechatPayUtil.createSign(data, signKey);
        data.put("sign", sign);
        log.info("微信查询订单 - sign:{}", sign);

        String urlParam = WechatPayUtil.maptoXml(data);
        log.info("微信查询订单 xml参数 --> urlParam:{}", urlParam);
        log.info("----------------------------------------------");
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setReqContent(payConfig.get(BqSdkConstants.weixinPayOrderQuery));
        String resContent = "";
        boolean result = false;
        if (httpClient.callHttpPost(payConfig.get(BqSdkConstants.weixinPayOrderQuery), urlParam)) {
            resContent = httpClient.getResContent();
            Map<String, Object> mapParam = WechatPayUtil.xmltoMap(resContent);
            log.info("*********************************************************************");
            log.info("微信查询订单接口返回值 -----> map:{}", mapParam);
            log.info("*********************************************************************");
            if ("SUCCESS".equals(mapParam.get("return_code")) && "SUCCESS".equals(mapParam.get("result_code")) && "SUCCESS".equals(mapParam.get("trade_state"))) {
                log.info("查询订单支付成功!");
                result = true;
            } else {
                log.error("微信查询订单接口失败,return_msg:{},err_code:{},err_code_des:{},trade_state:{},trade_state_desc:{}", mapParam.get("return_msg"), StringUtils.isNotBlank((String) mapParam.get("err_code")) ? mapParam.get("err_code") : "", StringUtils.isNotBlank((String) mapParam.get("err_code_des")) ? mapParam.get("err_code_des") : "", StringUtils.isNotBlank((String) mapParam.get("trade_state")) ? mapParam.get("trade_state") : "", StringUtils.isNotBlank((String) mapParam.get("trade_state_desc")) ? mapParam.get("trade_state_desc") : "");
                result = false;

            }
        }
        return result;
    }


}
