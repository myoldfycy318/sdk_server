package com.dome.sdkserver.service.impl.pay;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.constants.RedisKeyEnum;
import com.dome.sdkserver.metadata.dao.mapper.bq.pay.PayIosSwitchMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.PayIosSwitch;
import com.dome.sdkserver.service.pay.PayIosSwitchService;
import com.dome.sdkserver.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xuekuan on 2017/4/12.
 */
@Service("payIosSwitchService")
public class PayIosSwitchServiceImpl implements PayIosSwitchService {
    @Autowired
    private PayIosSwitchMapper payIosSwitchMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean insertOrUpdate(PayIosSwitch payIosSwitch) {
        Integer isExist = payIosSwitchMapper.isExistAppCode(payIosSwitch);
        boolean isSuccess = false;
        if (isExist >= 1){
            isSuccess = payIosSwitchMapper.updateByAppCode(payIosSwitch);
        }else{
            isSuccess = payIosSwitchMapper.insert(payIosSwitch);
        }
        if (isSuccess) {
            redisUtil.setex(RedisKeyEnum.BQ_IOS_PAY_SWITCH.getKey() + payIosSwitch.getAppCode(), RedisKeyEnum.BQ_IOS_PAY_SWITCH.getExpireTime(), JSONObject.toJSONString(payIosSwitch));
        }
        return isSuccess;
    }

    @Override
    public boolean delByAppCode(PayIosSwitch payIosSwitch) {
        Integer isExist = payIosSwitchMapper.isExistAppCode(payIosSwitch);
        if (isExist >= 1){
            return payIosSwitchMapper.delByAppCode(payIosSwitch);
        }else{
            return true;
        }
    }

    @Override
    public PayIosSwitch selectByAppCode(PayIosSwitch payIosSwitch) {
        return payIosSwitchMapper.selectByAppCode(payIosSwitch);
    }
}
