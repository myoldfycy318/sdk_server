package com.dome.sdkserver.service.pay.bq;

import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.h5game.H5GameEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;

/**
 * 调支付页处理
 * ForwardPayService
 *
 * @author Zhang ShanMin
 * @date 2016/11/9
 * @time 20:01
 */
public interface ForwardPayService {

    /**
     * 跳转支付页
     *
     * @param entity
     * @return
     */
    SdkOauthResult toPayPage(H5GameEntity entity, AppInfoEntity appInfoEntity) throws Exception;
}
