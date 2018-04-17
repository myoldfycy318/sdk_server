package com.dome.sdkserver.service.sign.impl;

import com.dome.sdkserver.metadata.dao.mapper.sign.SignMapper;
import com.dome.sdkserver.metadata.entity.sign.SignEntity;
import com.dome.sdkserver.service.sign.SignService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/3/29.
 */
@Service("signService")
class SignServiceImpl implements SignService{
    @Autowired
    private SignMapper signMapper;

    @Override
    public Map<String, String> playGameResponse(SignEntity signEntity) {
        Map<String,String> map = new HashMap<>();
        Integer record = signMapper.queryByUserId(signEntity);
        if (record > 0) {
            map.put("message", "PaySuccess");
            map.put("success", "true");
        }else{
            map.put("message", "PayFail");
            map.put("success", "false");
        }
        return map;
    }

    @Override
    public boolean insert(SignEntity signEntity) {
        return signMapper.insert(signEntity) == 1 ? true : false;
    }
}
