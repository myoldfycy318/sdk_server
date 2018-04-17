package com.dome.sdkserver.service.pay;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.metadata.entity.bq.pay.PayOptions;
import com.dome.sdkserver.metadata.entity.bq.pay.PayType;
import com.dome.sdkserver.metadata.entity.bq.pay.PayTypeCondition;

import java.util.List;

/**
 * PayOptionsService
 *
 * @author Zhang ShanMin
 * @date 2016/10/13
 * @time 11:48
 */
public interface PayOptionsService {

    /**
     * merge 支付选项
     *
     * @param payOptions
     * @return
     */
    boolean megerPayOptions(PayOptions payOptions);

    boolean delPayOptions(PayOptions payOptions);

    /**
     * 支付选项列表
     *
     * @param payOptions
     * @return
     */
    PayOptions queryPayOptions(PayOptions payOptions);

    /**
     * 获取支持的支付列表
     * @return
     */
    List<PayType> queryPayTypeList( PayTypeCondition payTypeCondition);

    /**
     * 是否支持钱宝宝劵支付
     * @param appCode
     * @return true；支付，false:不支持
     */
    public boolean isSupportBq(String appCode);

    /**
     * 游戏充值中心获取游戏信息
     * @param appCode
     * @return
     */
    public JSONObject queryGameInfo(String appCode);

    /**
     * 游戏充值中心获取cp游戏区服信息
     * @param jsonObject
     * @return
     */
    public JSONObject queryGameZooServers(JSONObject jsonObject);
}
