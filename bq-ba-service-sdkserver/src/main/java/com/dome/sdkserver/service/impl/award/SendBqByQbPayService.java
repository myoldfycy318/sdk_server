package com.dome.sdkserver.service.impl.award;

import com.dome.sdkserver.metadata.entity.bq.award.BqAutoSendEntity;
import com.dome.sdkserver.util.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 宝玩渠道钱宝支付返宝劵
 */
@Component("sendBqByQbPayService")
public class SendBqByQbPayService extends AutoSendBq {

    private static final int PAGE_SIZE = 1000;


    /**
     * redis hash 钱宝渠道支付宝支付返劵，redis key
     */
    public static final String REDIS_QB_PAY_HAS_SEND_BQ_USER= "BqSdkServer:QBPay:HasSendBqUser";

    /**
     * 充反逻辑V2.0  2016-05-30
     */
    public void sumTransConsumeV2() {

        String transDate = DateUtil.getCurLastDay().replaceAll("-", ""); //当前日期前一天
        int total = PAGE_SIZE;
        // 应用赠券规则Map
        Map<String, Object> appRuleMap = new HashMap<String, Object>();
        List<BqAutoSendEntity> entityList = null;
        int count = 1;
        for (int start = 0; start < total; start += PAGE_SIZE) {
            PageHelper.startPage(count++, PAGE_SIZE);
            Page<BqAutoSendEntity> bqAutoSendEntityPage = (Page<BqAutoSendEntity>) autoSendBqMapper.sumTransConsumeV2(transDate.substring(0, 6), transDate);
            total = new Long(bqAutoSendEntityPage.getTotal()).intValue();
            entityList = bqAutoSendEntityPage.getResult();
            if (!CollectionUtils.isEmpty(entityList)) {
                handleSendBq(appRuleMap, entityList);
            }
        }
    }

    /**
     * 保存返劵记录
     *
     * @param entity
     */
    protected void saveReturnTickets(BqAutoSendEntity entity) {
        autoSendBqMapper.insertSumTrans(entity);
    }

    /**
     * 获取支付方式:1.钱宝,2.支付宝
     *
     * @return
     */
    @Override
    protected String getPayType() {
        return QBAO_PAY;
    }

    /**
     * 获取宝玩渠道钱宝支付已发宝劵redis key
     *
     * @return
     */
    @Override
    protected String getHasSendBqUserKey() {
        return REDIS_QB_PAY_HAS_SEND_BQ_USER;
    }

}
