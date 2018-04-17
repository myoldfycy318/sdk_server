package com.dome.sdkserver.service.coupon;

import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.award.BqAutoSendEntity;

/**
 * CouponService
 *
 * @author Zhang ShanMin
 * @date 2016/12/7
 * @time 17:24
 */
public interface CouponService {

    SdkOauthResult sendCoupon(BqAutoSendEntity entity);

}
