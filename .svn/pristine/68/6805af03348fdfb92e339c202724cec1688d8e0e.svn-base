package com.dome.sdkserver.service.impl.pay;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.PayTypeEnum;
import com.dome.sdkserver.service.pay.PayConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("payConfigService")
public class PayConfigServiceImpl implements PayConfigService {

    @Autowired
    private AlipayConfig aliPayConfig;

    @Override
    public String getPayConfigByPayType(int payType) {
        if (payType == PayTypeEnum.支付宝支付.getCode()) {
            return aliPayConfig.getPayConfigInfo();
        }
        return null;
    }

    @Override
    public JSONObject getNotifyPayConfigByPayType(int payType) {
        return aliPayConfig.getNotifyConfigInfo(payType,"");
    }

    @Override
    public JSONObject getNotifyPayConfigByPayType(int payType, String appCode) {
        return aliPayConfig.getNotifyConfigInfo(payType,appCode);
    }

    @Override
    public Map<String, String> getAllConfig(int payType) {
        return aliPayConfig.getConfigInfo(payType,"");
    }

    @Override
    public Map<String, String> getAllConfig(int payType, String appCode) {
        return aliPayConfig.getConfigInfo(payType, appCode);
    }

}
