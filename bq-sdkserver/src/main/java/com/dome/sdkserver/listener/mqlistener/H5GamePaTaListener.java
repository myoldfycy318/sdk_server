package com.dome.sdkserver.listener.mqlistener;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.metadata.entity.sign.SignEntity;
import com.dome.sdkserver.service.sign.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * juBaoPenPayListener
 *
 * @author Zhang ShanMin
 * @date 2017/3/31
 * @time 1:29
 */
@Component("h5GamePaTaListener")
public class H5GamePaTaListener implements MessageListener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SignService signService;

    @Override
    public void onMessage(Message message) {
        try {
            String body = new String(message.getBody(), "utf-8");
            logger.info("四月签到分宝石与爬塔，内容：{}",body);
            SignEntity signEntity = JSONObject.parseObject(body, SignEntity.class);
            signEntity.setNotifyDate(DateUtils.getCurDateFormatStr(DateUtils.YYYYMMDD));
            signEntity.setNotifyTime(DateUtils.getCurDateFormatStr("HHmmss"));
            signEntity.setCurMonth(DateUtils.getCurDateFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT));
            signService.insert(signEntity);
        } catch (Exception e) {
            logger.error("Rabbitmq四月签到分接收宝石与爬塔游戏方通知异常", e);
        }
    }
}
