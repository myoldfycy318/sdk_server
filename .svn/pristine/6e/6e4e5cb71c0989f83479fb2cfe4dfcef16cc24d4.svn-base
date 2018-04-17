package com.dome.sdkserver.service.overseas;

import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.PublishOrderEntity;

/**
 * OverseasService
 *
 * @author Zhang ShanMin
 * @date 2017/3/9
 * @time 14:15
 */
public interface OverseasService {


    /**
     * 创建订单
     *
     * @param orderEntity
     * @return
     */
    SdkOauthResult createOrder(PublishOrderEntity orderEntity);

    /**
     * 支付通知处理
     *
     * @param orderEntity
     * @return
     */
    SdkOauthResult notify(PublishOrderEntity orderEntity);

}
