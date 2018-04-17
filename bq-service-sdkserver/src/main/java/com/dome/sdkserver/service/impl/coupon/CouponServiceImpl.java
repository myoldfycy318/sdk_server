package com.dome.sdkserver.service.impl.coupon;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bq.constants.PayConstant;
import com.dome.sdkserver.bq.constants.SendBqStatusEnum;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.dao.mapper.bq.award.AutoSendBqMapper;
import com.dome.sdkserver.metadata.entity.bq.award.BqAutoSendEntity;
import com.dome.sdkserver.service.coupon.CouponService;
import com.dome.sdkserver.shangsu.PersonTraceResp;
import com.dome.sdkserver.shangsu.PersonTradeReqInfo;
import com.dome.sdkserver.shangsu.TradeItem;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.shangsu.YanSuCommonUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CouponServiceImpl
 *
 * @author Zhang ShanMin
 * @date 2016/12/7
 * @time 17:27
 */
@Service("couponService")
public class CouponServiceImpl implements CouponService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private AutoSendBqMapper autoSendBqMapper;
    @Resource
    PropertiesUtil domainConfig;

    /**
     * 发劵
     *
     * @param entity
     * @return
     */
    @Override
    public SdkOauthResult sendCoupon(BqAutoSendEntity entity) {
        Map<String, String> resultMap = new HashMap<String, String>(1);
        //判断是否返劵流水是否存在
        BqAutoSendEntity originalBqEntity = autoSendBqMapper.queryTpBqTransByOutTradeNo(entity);
        if (originalBqEntity != null && SendBqStatusEnum.AUTO_SEND_SUCCESS.getStatus().equals(originalBqEntity.getStatus())) {
            boolean result = originalBqEntity.getBqAward().compareTo(entity.getBqAward()) == 0 && originalBqEntity.getFeeAmount().compareTo(entity.getFeeAmount()) == 0;
            resultMap.put("sdkOrderNo", originalBqEntity.getTradeNo());
            return result ? SdkOauthResult.success(resultMap) : SdkOauthResult.failed("返劵金额与已存在流水返劵金额不符");
        }
        //返劵
        boolean result = rechargeFanquan(entity);
        if (originalBqEntity == null) {
            resultMap.put("sdkOrderNo", entity.getTradeNo());
            autoSendBqMapper.insertThridPartyBqTrans(entity);
        } else {
            entity.setTradeNo(originalBqEntity.getTradeNo());
            resultMap.put("sdkOrderNo", originalBqEntity.getTradeNo());
            autoSendBqMapper.updateTpBqTransByTradeNo(entity);
        }

        return result ? SdkOauthResult.success(resultMap) : SdkOauthResult.failed("返劵失败");
    }


    /**
     * 发劵
     *
     * @param entity
     * @return
     */
    protected boolean rechargeFanquan(BqAutoSendEntity entity) {
        Long startTime = System.currentTimeMillis();
        boolean success = false;
        try {
            PersonTradeReqInfo tradeReqInfo = new PersonTradeReqInfo();
            tradeReqInfo.setSignType("RSA");
            tradeReqInfo.setInputCharset("UTF-8");
            tradeReqInfo.setGroupCode("G009");
            tradeReqInfo.setUserId(entity.getPayUserId());
            //商肃业务大类型
            tradeReqInfo.setBusiType(PayConstant.SS_BUSI_TYPE.getBusiType(entity.getBqSource()).getBusiType());
            tradeReqInfo.setTradeTime(DateUtils.getCurDateFormatStr("yyyyMMddHHmmss"));
            tradeReqInfo.setOutTradeNo(entity.getTradeNo());
            tradeReqInfo.setFeeAmount(entity.getFeeAmount() == null ? 0L : entity.getFeeAmount().longValue());
            tradeReqInfo.setTradeDesc(Base64.encodeBase64URLSafeString(entity.getBizDesc().getBytes("UTF-8")));
            tradeReqInfo.setTerminal(String.valueOf(entity.getTerminal()));
            List<TradeItem> list = new ArrayList<TradeItem>();
            TradeItem tradeItem1 = new TradeItem();
            tradeItem1.setItemTradeNo(entity.getTradeNo());
            tradeItem1.setPayType("2");// 宝劵
            tradeItem1.setAmount(entity.getBqAward().longValue());
            //明细业务类型
            tradeItem1.setItemBusiType(PayConstant.RET_TICKETS_BUSI_TYPE.getRetTicketsBusiType(entity.getBqSource()).getBusiType());
            list.add(tradeItem1);
            tradeReqInfo.setTradeItems(list);
            logger.info("{}:调商肃发券参数:{}", entity.getBqSource(), JSON.toJSON(tradeReqInfo));
            String requestUrl = domainConfig.getString("shangsu.personal.business");
            String rsaPrivateKey = domainConfig.getString("shangsu.rsa.private.key");
            PersonTraceResp personTraceResp = YanSuCommonUtil.personalBusiness(tradeReqInfo, requestUrl, rsaPrivateKey);
            logger.info("{}:调商肃发券结果:{}", entity.getBqSource(), JSON.toJSONString(personTraceResp));
            if (personTraceResp == null) {
                entity.setStatus(SendBqStatusEnum.AUTO_SEND_FAIL.getStatus());
                return false;
            }
            if ("100000".equals(personTraceResp.getRespCode())) {
                entity.setStatus(SendBqStatusEnum.AUTO_SEND_SUCCESS.getStatus());
                success = true;
            } else {
                entity.setStatus(SendBqStatusEnum.AUTO_SEND_FAIL.getStatus());
            }
            entity.setReturnCode(personTraceResp.getRespCode());
            entity.setReturnMsg(personTraceResp.getRespMsg());
            entity.setPayTradeNo(personTraceResp.getPayTradeNo());
        } catch (Exception e) {
            entity.setStatus(SendBqStatusEnum.AUTO_SEND_ERROR.getStatus());
            logger.error("payUserId:{},来源:{},调商肃发宝劵异常", entity.getBqSource(), entity.getPayUserId(), e);
        }
        Long endTime = System.currentTimeMillis();
        logger.info("payUserId:{},来源:{},发劵end..,耗时:{}", entity.getBqSource(), entity.getPayUserId(), (endTime - startTime) / 1000 + "s");
        return success;
    }

}
