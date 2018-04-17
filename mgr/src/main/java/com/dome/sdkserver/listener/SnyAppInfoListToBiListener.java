package com.dome.sdkserver.listener;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.SnyAppInfoToBi;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.SnyAppInfoToBiMapper;
import com.dome.sdkserver.service.SnyAppInfoToBiService;
import com.dome.sdkserver.view.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyajun on 2017/5/10.
 */
@Component("snyAppInfoListToBiListener")
public class SnyAppInfoListToBiListener implements MessageListener{
    @Autowired
    private SnyAppInfoToBiService snyAppInfoToBiService;

    private Logger log = LoggerFactory.getLogger(SnyAppInfoToBiListener.class);

    @Override
    public void onMessage(Message message) {
        try {
            String body = new String(message.getBody(), "utf-8");
            log.info("开放平台手游,页游,H5全部需要同步至bi的数据，内容：{}",body);
            List list = JSONObject.parseObject(body, List.class);
            List<SnyAppInfoToBi> listBi = new ArrayList<SnyAppInfoToBi>();
            for(Object obj : list){
                SnyAppInfoToBi bi = JSONObject.parseObject(obj.toString(),SnyAppInfoToBi.class);
                listBi.add(bi);
            }
            log.info("listBi:"+JSONObject.toJSONString(list));
            if(list.size() != listBi.size() && listBi.size() == 0){
                log.error("开放平台同步数据处理后与处理前数量不一致,处理出错!!!");
                throw new Exception("开放平台同步数据处理后与处理前数量不一致,处理出错!!!");
            }
            snyAppInfoToBiService.insertBiList(listBi);
        }catch (Exception e){
            log.error("消息队列处理错误>>>>>开放平台手游,页游,H5全部需要同步至bi的数据",e);
            e.printStackTrace();
        }
    }



}
