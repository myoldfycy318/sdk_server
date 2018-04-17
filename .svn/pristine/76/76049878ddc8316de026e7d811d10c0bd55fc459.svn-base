package com.dome.sdkserver.job;

import com.dome.sdkserver.service.impl.award.SendBqByQbPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;


/**
 * 自动发放宝券
 */
public class AutoSendBqTask {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "sendBqByQbPayService")
    private SendBqByQbPayService sendBqByQbPayService;

    public void sumConsume() {
        log.info("自动发放宝券 start..");
        sendBqByQbPayService.sumTransConsumeV2();
        log.info("自动发放宝券  end..");
    }
}
