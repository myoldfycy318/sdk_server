package com.dome.sdkserver.biz.executor;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.CBStatusEnum;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.bq.util.PriceUtil;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.security.ras.RSA;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.util.ApiConnector;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.util.Base64Utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * 冰趣平台币充值Ali支付成功后异步通知
 * H5GameQbaoPayNotifyThread
 *
 * @author Zhang ShanMin
 * @date 2016/11/18
 * @time 10:43
 */
public class BqPlatformRatioAliPayNotifyThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(BqPlatformRatioAliPayNotifyThread.class);

    private OrderEntity orderEntity;
    private OrderService orderService;

    private static Properties properties = new Properties();

    static {
        InputStream in = null;
        try {
            logger.error("冰趣平台币通知加载pay.properties.....");
            String filePath = "/pay.properties";
            in = WebpageGamePayNotifyThread.class.getResourceAsStream(filePath);
            if (in == null) {
                logger.info("domain.properties not found");
            } else {
                if (!(in instanceof BufferedInputStream)) {
                    in = new BufferedInputStream(in);
                }
                properties.load(in);
            }
        } catch (Exception e) {
            logger.error("冰趣平台币通知加载pay.properties异常", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }


    public BqPlatformRatioAliPayNotifyThread(OrderEntity orderEntity, Object object) {
        this.orderEntity = orderEntity;
        this.orderService = (OrderService) object;
    }

    @Override
    public void run() {
        JSONObject json = null;
        JSONObject extraField = null;
        int maxTryTimes = 3;
        int sleepTime = 1000 * 60;
        try {
            Map<String, String> params = new HashMap<String, String>(9);
            params.put("userId", String.valueOf(orderEntity.getBuyerId()));
            params.put("userName", "测试");
            Double amount = orderEntity.getChargePointAmount();
            params.put("amount", String.valueOf(PriceUtil.convertToFen(amount)));
            params.put("type", "51");  //平台比充值type:51支付宝
            params.put("desc", "充值");
            params.put("sign", handleSign(params));//处理签名
            params.put("signType", "RSA");
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 冰趣平台币充值url
            String url = properties.getProperty("bq.platform.ratio.recharge.url", "http://pay.domestore.cn/bqcoin/pay/cz");
            for (int i = 0; i < maxTryTimes; i++) {
                String res = ApiConnector.post(url, pairs);
                logger.info("冰趣平台币Ali支付通知,请求url:{},请求参数:{},响应结果:{}", url, pairs, res);
                if (StringUtils.isNotBlank(res) && (json = JSONObject.parseObject(res)) != null && StringUtils.isNotBlank(json.getString("responseCode")) &&
                        "1000".equals(json.getString("responseCode")) && StringUtils.isNotBlank(json.getString("data")) && JSONObject.parseObject(json.getString("data")) != null
                        && StringUtils.isNotBlank(JSONObject.parseObject(json.getString("data")).getString("czNo"))) {
                    orderEntity.setTradeNo(JSONObject.parseObject(json.getString("data")).getString("czNo"));
                    orderEntity.setCallbackStatus(CBStatusEnum.RESP_SUCCESS.getCode());
                    orderService.updateOrderInfo(orderEntity, PayUtil.getPayMonth(orderEntity.getOrderNo()));
                    break;
                } else {
                    Thread.sleep(sleepTime);
                }
            }
        } catch (Exception e) {
            logger.error("冰趣平台币Ali支付通知异常", e);
        }
    }

    /**
     * 处理平台币签名
     *
     * @param params
     * @return
     * @throws Exception
     */
    public String handleSign(Map<String, String> params) throws Exception {
        String str = MapUtil.createLinkString(params);
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String md5Str = encoder.encodePassword(str, properties.getProperty("bq.platform.ratio.md5salt"));
        String signStr = RSA.sign(md5Str, properties.getProperty("bq.platform.ratio.rsa.privatekey"));
        return Base64Utils.encodeToString(signStr.getBytes("utf-8"));
    }

}
