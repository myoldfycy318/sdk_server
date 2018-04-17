package com.dome.sdkserver.listener.mqlistener;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.metadata.entity.qbao.PayTransEntity;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.MD5;
import com.dome.sdkserver.util.PriceUtil;
import com.dome.sdkserver.util.PropertiesUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * juBaoPenPayListener
 *
 * @author Zhang ShanMin
 * @date 2017/2/21
 * @time 1:29
 */
@Component("juBaoPenPayListener")
public class JuBaoPenPayListener implements MessageListener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "payConfig")
    protected PropertiesUtil payConfig;

    @Override
    public void onMessage(Message message) {
        try {
            String body = new String(message.getBody(), "utf-8");
            PayTransEntity payTransEntity = JSONObject.parseObject(body, PayTransEntity.class);
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
            map.put("signCode", MD5.md5Encode(MapUtil.createLinkString(map) + "&key=" + payConfig.getString("jubaopen.pay.notify.md5.sign.key")));
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            String res = ApiConnector.post(payTransEntity.getCallbackUrl(), pairs);
            logger.info("Rabbitmq侧聚宝盆支付异步通知,请求url:{},sdk订单号:{},请求参数:{},响应结果:{}", payTransEntity.getCallbackUrl(), payTransEntity.getPayTransId(), pairs, res);
        } catch (Exception e) {
            logger.error("Rabbitmq侧聚宝盆支付异步通知异常", e);
        }
    }
}
