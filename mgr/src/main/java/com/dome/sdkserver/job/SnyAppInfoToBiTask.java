package com.dome.sdkserver.job;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.SnyAppInfoToBi;
import com.dome.sdkserver.service.SnyAppInfoToBiService;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyajun on 2017/5/11.
 */
public class SnyAppInfoToBiTask {
    private Logger log = LoggerFactory.getLogger(SnyAppInfoToBiTask.class);

    @Autowired
    private SnyAppInfoToBiService snyAppInfoToBiService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RedisUtil redisUtil;

    private final String redisKey="open:sny:app:bi";


    public void snyAppInfoToBiByTask (){
        try {
            log.info("开放平台检查是否同步数据至bi定时任务");
            snyAppInfoToBiService.snyAppInfoToBiByMq();
        }catch (Exception e){
            log.error("定时任务>>>开放平台检查是否有手游,页游,H5信息数据未同步至bi出错",e);
            //删除缓存
            redisUtil.del(redisKey);
        }
    }
}
