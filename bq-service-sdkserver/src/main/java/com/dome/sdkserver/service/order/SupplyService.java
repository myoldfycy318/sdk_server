package com.dome.sdkserver.service.order;

import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.service.web.requestEntity.HttpRequestOrderInfo;

/**
 * 游戏充值记录补单
 * SupplyService
 *
 * @author Zhang ShanMin
 * @date 2017/10/9
 * @time 16:18
 */
public interface SupplyService {

    SdkOauthResult supply(HttpRequestOrderInfo order);
}
