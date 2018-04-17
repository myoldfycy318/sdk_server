package com.dome.sdkserver.service.impl.channel;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.constants.RedisKeyEnum;
import com.dome.sdkserver.metadata.dao.mapper.bq.pay.NewOpenChannelMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.NewOpenChannel;
import com.dome.sdkserver.service.channel.NewOpenChannelService;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewOpenChannelServiceImpl implements NewOpenChannelService {

    @Autowired
    private NewOpenChannelMapper mapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean synNewOpenChannel(NewOpenChannel channel) {
        boolean recordExist = mapper.isChannelExist(channel);
        boolean flag = false;
        if (!recordExist) {
            //新增
            flag = mapper.insertSelective(channel);
        } else {
            //修改
            flag = mapper.updateByChannelCodeSelective(channel);
        }
        return flag ? flushChannelRedisCache(channel) : false;
    }

    private boolean flushChannelRedisCache(NewOpenChannel channel) {
        String redisKey = RedisKeyEnum.NEW_OPEN_CHANNEL.getKey() + channel.getChannelCode();
        redisUtil.set(redisKey, JSONObject.toJSONString(channel));
        return true;
    }

    public boolean containChanneCode(String channelCode) {
        boolean flag = false;
        String channelKey = RedisKeyEnum.NEW_OPEN_CHANNEL.getKey() + channelCode;
        String channelValue = redisUtil.get(channelKey);
        if (StringUtils.isNotBlank(channelValue))
            return true;
        //查询表中数据
        NewOpenChannel channel = mapper.selectByChannelCode(channelCode);
        if (channel != null) {
            redisUtil.set(channelKey, JSONObject.toJSONString(channel));
            flag = true;
        }
        return flag;
    }
}
