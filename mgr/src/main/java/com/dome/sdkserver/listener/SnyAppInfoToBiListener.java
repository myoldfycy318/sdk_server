package com.dome.sdkserver.listener;


import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.SnyAppInfoToBi;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.SnyAppInfoToBiMapper;
import com.dome.sdkserver.service.SnyAppInfoToBiService;
import com.dome.sdkserver.util.RedisUtil;
import com.dome.sdkserver.view.AjaxResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyajun on 2017/5/10.
 */
@Component("snyAppInfoToBiListener")
public class SnyAppInfoToBiListener implements MessageListener{

    private Logger log = LoggerFactory.getLogger(SnyAppInfoToBiListener.class);

    @Autowired
    private SnyAppInfoToBiService snyAppInfoToBiService;

    @Override
    public void onMessage(Message message) {
        try{
            String body = new String(message.getBody(), "utf-8");
            log.info("开放平台同步应用信息数据至bi>>>>> 内容：{}",body);
            MerchantAppInfo appInfo = JSONObject.parseObject(body,MerchantAppInfo.class);
            log.info("开放平台同步应用信息数据至bi开始...");
            snyAppInfoToBiService.snyAppInfoToBi(appInfo);
            log.info("开放平台同步应用信息数据至bi结束...");
        }catch (Exception e){
            log.error("消息队列处理错误>>>>>开放平台同步手游,页游,H5数据至bi",e);
        }
    }

}
