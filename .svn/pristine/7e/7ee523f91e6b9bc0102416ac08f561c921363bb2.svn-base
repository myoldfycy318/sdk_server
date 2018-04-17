package com.dome.sdkserver.service.pay.bq;

import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.h5game.H5GameEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;

import java.util.Map;

/**
 * PayManagerService
 * 游戏支付管理
 *
 * @author Zhang ShanMin
 * @date 2016/11/10
 * @time 12:11
 */
public interface PayManagerService {

    /**
     * 产生H5游戏支付页连接
     * @return
     */
    SdkOauthResult producePayPageLink(H5GameEntity entity,AppInfoEntity appInfoEntity) throws Exception;


    /**
     * 地方支付通知
     * @return
     * @throws Exception
     */
    SdkOauthResult h5ThirdPayNotify(Map<String, String[]> requestParams) throws Exception;

}
