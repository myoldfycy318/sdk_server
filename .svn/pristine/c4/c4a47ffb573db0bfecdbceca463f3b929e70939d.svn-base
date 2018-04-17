package com.dome.sdkserver.job;

import com.dome.sdkserver.service.impl.award.SendBqByAliPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;


/**
 * 自动发放宝券
 */
public class SendBqByAliPayTask {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "sendBqByAliPayService")
    SendBqByAliPayService sendBqByAliPayService;

    public void sumConsume() {
        log.info("冰趣宝玩渠道支付宝支付发放宝券 start..");
        sendBqByAliPayService.sendBqByAliPay();
        log.info("冰趣宝玩渠道支付宝支付发放宝券  end..");
    }
}
