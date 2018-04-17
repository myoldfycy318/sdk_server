package com.dome.sdkserver.listener.mqlistener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.constants.PayConstant;
import com.dome.sdkserver.bq.constants.TransStatusEnum;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.metadata.dao.mapper.qbao.PayTransMapper;
import com.dome.sdkserver.metadata.entity.qbao.PayTransEntity;
import com.dome.sdkserver.shangsu.PersonTraceResp;
import com.dome.sdkserver.shangsu.PersonTradeReqInfo;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.shangsu.YanSuCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 商肃订单状态查询
 * shangSuPayStatusQueryListener
 *
 * @author Zhang ShanMin
 * @date 2017/3/24
 * @time 15:29
 */
@Component("shangSuPayStatusQueryListener")
public class ShangSuPayStatusQueryListener implements MessageListener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "payConfig")
    protected PropertiesUtil payConfig;
    @Autowired
    protected AmqpTemplate amqpTemplate;
    @Resource
    private PayTransMapper payTransMapper;

    @Override
    public void onMessage(Message message) {
        try {
            Thread.sleep(50);
            String body = new String(message.getBody(), "utf-8");
            PayTransEntity entity = JSONObject.parseObject(body, PayTransEntity.class);
            PersonTradeReqInfo tradeReqInfo = new PersonTradeReqInfo();
            tradeReqInfo.setSignType("RSA");
            tradeReqInfo.setInputCharset("UTF-8");
            tradeReqInfo.setGroupCode("G009");//集团账号
            tradeReqInfo.setUserId(String.valueOf(entity.getPayUserId()));
            //商肃业务大类型
            tradeReqInfo.setBusiType(PayConstant.SS_BUSI_TYPE.getBusiType(entity.getPayOrigin()).getBusiType());
            tradeReqInfo.setTradeTime(DateUtils.getCurDateFormatStr("yyyyMMddHHmmss"));
            tradeReqInfo.setOutTradeNo(String.valueOf(entity.getPayTransId()));
            String requestUrl = payConfig.getString("shangsu.personal.query.status");
            String rsaPrivateKey = payConfig.getString("shangsu.rsa.private.key");
            //调商肃支付引擎4.3	个人业务类交易状态查询接口
            PersonTraceResp personTraceResp = YanSuCommonUtil.queryStatus(tradeReqInfo, requestUrl, rsaPrivateKey);
            logger.info("sdk订单号:{},个人业务类交易状态查询结果:{}", entity.getPayTransId(), JSON.toJSONString(personTraceResp));
            if (personTraceResp != null && !"100000".equals(personTraceResp.getRespCode())) {
                return;
            }
            if ("NewBusi".equalsIgnoreCase(entity.getPayOrigin()) || "JB_AU_PAY".equalsIgnoreCase(entity.getPayOrigin())) {//新业务侧聚宝盆混合支付异步通知
                amqpTemplate.convertAndSend("jubaopen_pay_key", entity); //异步通知使用rabbitmq
            }
            PayTransEntity payTransEntity = new PayTransEntity();
            payTransEntity.setStatus(TransStatusEnum.PAY_TRANS_SUCCESS.getStatus());
            payTransEntity.setPayTransId(entity.getPayTransId());
            payTransEntity.setResCode(personTraceResp.getRespCode());
            payTransEntity.setResMsg(personTraceResp.getRespMsg());
            payTransEntity.setPayTradeNo(personTraceResp.getPayTradeNo());
            payTransMapper.updateTPPayTrans(payTransEntity, PayUtil.getqBaoPayMonth(String.valueOf(entity.getPayTransId())));
        } catch (Exception e) {
            logger.error("人业务类交易状态查询,Rabbitmq侧聚宝盆支付异步通知异常", e);
        }
    }
}
