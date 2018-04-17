package com.dome.sdkserver.service.impl.award;

import com.dome.sdkserver.bq.util.ThreadLocalUtil;
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
 * 冰趣钱宝渠道支付包支付返宝劵
 *
 * @author Zhang ShanMin
 * @date 2016/8/5
 * @time 16:35
 */
@Component("sendBqByAliPayService")
public class SendBqByAliPayService extends AutoSendBq {

    private static final int PAGE_SIZE = 1000;

    /**
     * redis hash 钱宝渠道支付宝支付返劵，redis key
     */
    public static final String REDIS_ALI_PAY_HAS_SEND_BQ_USER= "BqSdkServer:AliPay:HasSendBqUser";

    public void sendBqByAliPay() {
        String transDate = DateUtil.getCurLastDay().replaceAll("-", ""); //当前日期前一天
        ThreadLocalUtil.set("transDate", transDate);
        int total = PAGE_SIZE;
        // 应用赠券规则Map
        Map<String, Object> appRuleMap = new HashMap<String, Object>();
        List<BqAutoSendEntity> entityList = null;
        int count = 1;
        for (int start = 0; start < total; start += PAGE_SIZE) {
            PageHelper.startPage(count++, PAGE_SIZE);
            Page<BqAutoSendEntity> bqAutoSendEntityPage = (Page<BqAutoSendEntity>) autoSendBqMapper.sumBwChannelAliPay(transDate.substring(0, 6), transDate);
            total = new Long(bqAutoSendEntityPage.getTotal()).intValue();
            entityList = bqAutoSendEntityPage.getResult();
            if (!CollectionUtils.isEmpty(entityList)) {
                //发送宝劵处理
                handleSendBq(appRuleMap, entityList);
            }
        }
    }


    /**
     * 保存返劵记录
     *
     * @param entity
     */
    @Override
    protected void saveReturnTickets(BqAutoSendEntity entity) {
        entity.setTransDate(ThreadLocalUtil.get("transDate") == null ? "" : ThreadLocalUtil.get("transDate").toString().substring(0, 8));
        autoSendBqMapper.insertAliPayReturnTickets(entity);
    }

    /**
     * 获取支付方式:1.钱宝,2.支付宝
     *
     * @return
     */
    @Override
    protected String getPayType() {
        return ALI_PAY;
    }

    /**
     * 获取宝玩渠道支付宝支付已发宝劵redis key
     *
     * @return
     */
    @Override
    protected String getHasSendBqUserKey() {
        return REDIS_ALI_PAY_HAS_SEND_BQ_USER;
    }
}
