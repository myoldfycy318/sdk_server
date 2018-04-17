package com.dome.sdkserver.service.pay;

import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.service.web.requestEntity.HttpRequestOrderInfo;

import java.util.Map;

/**
 * PayService
 *
 * @author Zhang ShanMin
 * @date 2016/12/17
 * @time 14:45
 */
public interface PayService {

    /**
     * 创建订单
     * @param requestOrderInfo
     * @return
     */
    SdkOauthResult createOrder(HttpRequestOrderInfo requestOrderInfo) throws Exception;

    /**
     * 创建预支付订单
     * @param requestOrderInfo
     * @return
     * @throws Exception
     */
    void createPreOrder(HttpRequestOrderInfo requestOrderInfo) throws Exception;

    /**
     * 构建支付请求数据
     */
    SdkOauthResult buildPayReqData(OrderEntity orderEntity ,Map<String, String> payData) throws Exception;
}
