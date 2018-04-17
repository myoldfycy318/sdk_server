package com.dome.sdkserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.RealName;
import com.dome.sdkserver.metadata.dao.mapper.RealNameMapper;
import com.dome.sdkserver.service.RealNameService;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by heyajun on 2017/5/25.
 */
@Service
public class RealNameServiceImpl implements RealNameService{

    @Autowired
    private RealNameMapper mapper;

    @Autowired
    private RedisUtil redisUtil;

    private final String OPENBA_REAL_NAME_SWITCH = "openba:real_name:switch";

    @Override
    public RealName query() {
        String redisValue = redisUtil.get(OPENBA_REAL_NAME_SWITCH);
        RealName realName = null;
        if(StringUtils.isBlank(redisValue)){
            realName = mapper.query();
            redisUtil.set(OPENBA_REAL_NAME_SWITCH, JSONObject.toJSONString(realName));
        }else {
            realName = JSONObject.parseObject(redisValue, RealName.class);
        }
        return realName;
    }

    @Override
    public void update(Integer status) throws Exception{
        redisUtil.del(OPENBA_REAL_NAME_SWITCH);
        int i = mapper.update(status);
        if(i != 1){
            throw new Exception("修改强制实名失败");
        }
    }
}
