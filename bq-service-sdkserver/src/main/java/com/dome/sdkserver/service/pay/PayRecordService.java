package com.dome.sdkserver.service.pay;

import com.dome.sdkserver.bq.util.PageDto;
import com.dome.sdkserver.metadata.entity.bq.pay.PayRecordEntity;

import java.util.List;

/**
 * Created by admin on 2017/4/11.
 */
public interface PayRecordService {
    /**
     * 查询用户支付记录
     * @param orderEntity
     * @return
     */
    List<PayRecordEntity> queryPayRecord(PayRecordEntity orderEntity);

    /**
     * 查询用户支付记录
     * @param orderEntity
     * @return
     */
    List<PayRecordEntity> queryPayRecordList(PayRecordEntity orderEntity,PageDto pageDto);
    /**
     * 查询有票用户支付记录
     * @param orderEntity
     * @return
     */
    List<PayRecordEntity> queryYouPiaoPayRecord(PayRecordEntity orderEntity);




    /**
     * 查询页游、VR游戏支付记录
     * @param orderEntity
     * @return
     */
    List<PayRecordEntity> queryPayStream(PayRecordEntity orderEntity,PageDto pageDto);
}
