package com.dome.sdkserver.biz.executor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.CBStatusEnum;
import com.dome.sdkserver.biz.utils.SpringBeanProxy;
import com.dome.sdkserver.metadata.entity.qbao.PayTransEntity;
import com.dome.sdkserver.service.pay.qbao.SdkPayService;
import com.dome.sdkserver.util.ApiConnector;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 页游钱宝支付成功后异步通知
 * WebGameQbaoPayNotifyThread
 *
 * @author Zhang ShanMin
 * @date 2016/8/26
 * @time 10:43
 */
public class WebGameQbaoPayNotifyThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(WebGameQbaoPayNotifyThread.class);

    private PayTransEntity payTransEntity;


    public WebGameQbaoPayNotifyThread(PayTransEntity payTransEntity) {
        this.payTransEntity = payTransEntity;
    }

    @Override
    public void run() {
        try {
            SdkPayService sdkPayService = SpringBeanProxy.getBean(SdkPayService.class, "sdkPayService");
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("userId", String.valueOf(payTransEntity.getPayUserId())));
            pairs.add(new BasicNameValuePair("zoneId", JSONObject.parseObject(payTransEntity.getExtraField()).getString("zoneId")));
            pairs.add(new BasicNameValuePair("orderNo", String.valueOf(payTransEntity.getPayTransId())));
            pairs.add(new BasicNameValuePair("price", String.valueOf(payTransEntity.getTransAmount().doubleValue())));
            String res = ApiConnector.post(payTransEntity.getCallbackUrl(), pairs);
            logger.info("页游钱宝支付通知,请求url:{},请求参数:{},响应结果:{}", payTransEntity.getCallbackUrl(), pairs, res);
            JSONObject jsonObject = JSONObject.parseObject(res);
            if (jsonObject != null && StringUtils.isNotBlank(jsonObject.getString("code")) && "0".equals(jsonObject.getString("code"))) {
                String extraField = payTransEntity.getExtraField();
                jsonObject = StringUtils.isBlank(extraField) ? null : (JSONObject.parseObject(res) == null ? null : JSONObject.parseObject(extraField));
                if (jsonObject == null)
                    return;
                jsonObject.put("webgame", "paid");
                PayTransEntity transEntity = new PayTransEntity();
                transEntity.setPayTransId(payTransEntity.getPayTransId());
                transEntity.setExtraField(JSON.toJSONString(jsonObject));
                transEntity.setCallbackStatus(CBStatusEnum.RESP_SUCCESS.getCode());
                sdkPayService.updatePayTrans(transEntity, payTransEntity.getTransDate().substring(0, 6));
            }
        } catch (Exception e) {
            logger.error("页游钱宝支付异步通知异常", e);
        }
    }
}
