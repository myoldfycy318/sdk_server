package com.dome.sdkserver.controller.util;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.metadata.entity.BiReportLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.Date;


/**
 * BI 数据收集
 * Created by ym on 2017/11/24.
 */
public class CollectDataLog {
    protected final static Logger log = LoggerFactory.getLogger(CollectDataLog.class);

    public static AmqpTemplate biTemplate;

    public AmqpTemplate getBiTemplate() {
        return biTemplate;
    }

    public void setBiTemplate(AmqpTemplate biTemplate) {
        CollectDataLog.biTemplate = biTemplate;
    }

    /**
     * BI 游戏转化表 (H5 页游)报表需求 记录活跃数 激活数(首次进) = 注册数
     */
    public static void recordLog(BiReportLogEntity biEntity) {
        try {
            biEntity.setDataTime(new Date());
            biEntity.setRecType("0");//活跃数
            biEntity.setStatisticsType("webGame");
            log.info("biInfo:{}", JSONObject.toJSONString(biEntity));
            boolean isFlag = biEntity.validate();
            if (!isFlag) {
                log.error("BI数据收集 -- (appCode,userId,channelId,sysType,buId,platformType)参数中某些为空");
            } else {
                //游戏转化表(D)
                biTemplate.convertAndSend("bi_collect_data_queue_key", biEntity); //异步通知使用rabbitmq
                log.info("游戏转化表- 日志记录成功");
            }
            log.info("BI 数据收集完成");

        } catch (Exception e1) {
            log.error("recordLog 异常", e1);
        }
    }

}
