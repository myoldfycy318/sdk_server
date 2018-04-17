/**
 *
 */
package com.dome.sdkserver.metadata.dao.mapper.bq.award;

import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import com.dome.sdkserver.metadata.entity.bq.award.BqAutoSendEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AutoSendBqMapper extends IBaseMapperDao {


    /**
     * 批量插入汇总记录
     *
     * @param entity 汇总记录实体对象
     */
    void insertSumTrans(@Param("entity") BqAutoSendEntity entity);

    /**
     * 保存宝玩渠道支付宝支付返劵记录
     *
     * @param entity 汇总记录实体对象
     */
    void insertAliPayReturnTickets(@Param("entity") BqAutoSendEntity entity);

    /**
     * 根据id查询数据总量
     *
     * @param id
     * @return
     */
    int getTransConsumeById(@Param("id") String id);

    /**
     * 查询指定天的每个游戏下每个用户宝币消费总数
     *
     * @param @param  seq
     * @param @param  transDate
     * @param @return
     */
    List<BqAutoSendEntity> sumTransConsumeV2(@Param("seq") String seq, @Param("transDate") String transDate);

    /**
     * 获取指定天的每个游戏下每个用户的冰趣钱宝渠道支付包支付总数
     * @param seq
     * @param transDate
     * @return
     */
    List<BqAutoSendEntity> sumBwChannelAliPay(@Param("seq") String seq, @Param("transDate") String transDate);

    /**
     * 记录外部发劵流水
     * @param entity
     * @return
     */
    boolean insertThridPartyBqTrans(@Param("entity") BqAutoSendEntity entity);

    /**
     * 根据外部流水查询返劵流水
     * @param entity
     * @return
     */
    BqAutoSendEntity queryTpBqTransByOutTradeNo(BqAutoSendEntity entity);

    /**
     * 根据流水号更新返劵信息
     * @param entity
     * @return
     */
    boolean updateTpBqTransByTradeNo(BqAutoSendEntity entity);
}
