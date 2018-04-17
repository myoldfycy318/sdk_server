package com.dome.sdkserver.service.pay.bq;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.metadata.entity.qbao.PayTransEntity;
import com.dome.sdkserver.service.pay.qbao.bo.SdkPayResponse;

import java.util.Map;

/**
 * 支付通知处理
 * PayNotifyService
 *
 *
 * @author Zhang ShanMin
 * @date 2016/11/11
 * @time 14:20
 */
public interface PayNotifyService {


    /**
     * 处理第三方支付通知
     * @param requestParams
     * @return
     */
    SdkOauthResult handleThirdPayNotify(Map<String, String[]> requestParams) throws Exception;

    /**
     * 处理钱宝支付cp通知
     * @param entity
     * @param response
     */
    void handleQbaoPayNotify(PayTransEntity entity, SdkPayResponse response);

    /**
     * 处理微信、支付宝支付cp通知
     * @param aliPayConfig
     * @param order
     * @param object
     */
    void handleDomePayNotify(JSONObject aliPayConfig, OrderEntity order, Object object);
}
