package com.dome.sdkserver.biz.executor;

import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.metadata.entity.qbao.PayTransEntity;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.MD5;
import com.dome.sdkserver.util.PriceUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * 新业务侧聚宝盆需求钱宝支付成功后异步通知
 *
 * @author Zhang ShanMin
 * @date 2016-12-09
 * @time 10:43
 */
public class JuBaoPenQbaoPayNotifyThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(JuBaoPenQbaoPayNotifyThread.class);
    private PayTransEntity payTransEntity;

    public JuBaoPenQbaoPayNotifyThread(PayTransEntity payTransEntity) {
        this.payTransEntity = payTransEntity;
    }

    private static Properties properties = new Properties();

    static {
        InputStream in = null;
        try {
            logger.error("聚宝盆需求钱宝支付异步通知加载pay.properties.....");
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
            logger.error("聚宝盆需求钱宝支付异步通知加载pay.properties异常", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    @Override
    public void run() {
        int maxTryTimes = 3;
        int sleepTime = 1000 * 60;
        try {
            Map<String, String> map = new HashMap<String, String>(9);
            map.put("outOrderNo", payTransEntity.getBizCode());
            map.put("sdkOrderNo", String.valueOf(payTransEntity.getPayTransId()));
            map.put("userId", String.valueOf(payTransEntity.getPayUserId()));
            map.put("transAmount", String.valueOf(PriceUtil.convertToFenL(payTransEntity.getTransAmount()).longValue()));
            map.put("rmbAmount", String.valueOf(PriceUtil.convertToFenL(payTransEntity.getAccountAmount()).longValue()));
            map.put("bqAmount", String.valueOf(PriceUtil.convertToFenL(payTransEntity.getBqAccountAmount()).longValue()));
            map.put("feeAmount", String.valueOf(PriceUtil.convertToFenL(payTransEntity.getFeeAmount()).longValue()));
            map.put("payType", String.valueOf(payTransEntity.getPayType()));
            map.put("payTime", DateUtils.toDateText(new Date(), "yyyMMddHHmmss"));
            map.put("payResult", "true");
            map.put("signCode", MD5.md5Encode(MapUtil.createLinkString(map) + "&key=" + properties.getProperty("jubaopen.pay.notify.md5.sign.key")));
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            for (int i = 0; i < maxTryTimes; i++) {
                String res = ApiConnector.post(payTransEntity.getCallbackUrl(), pairs);
                logger.info("侧聚宝盆支付异步通知,请求url:{},请求参数:{},响应结果:{}", payTransEntity.getCallbackUrl(), pairs, res);
                if (!StringUtils.isBlank(res) && "success".equals(res)) {
                    break;
                } else {
                    Thread.sleep(sleepTime);
                }
            }
        } catch (Exception e) {
            logger.error("侧聚宝盆支付异步通知异常", e);
        }
    }

}
