package com.dome.sdkserver.metadata.dao.mapper.bq.pay;

import com.dome.sdkserver.metadata.dao.IBaseMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.PayRecordEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.PublishOrderEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublishOrderMapper extends IBaseMapper {
	
	/**
	 * 创建订单信息
	 * @param
	 */
	boolean insert(PublishOrderEntity publishOrderEntity);

    /**
     * 更新订单信息
     */
    boolean updateById(PublishOrderEntity publishOrderEntity);

    /**
     * 更新订单信息
     */
    boolean updateOrderById(PublishOrderEntity publishOrderEntity);

	/**
	 * 查询订单金额
	 * @param
	 */
	double queryOrderAmount(@Param("orderNo") String orderNo, @Param("curMonth") String curMonth);
	
	/**
	 * 根据订单号查询订单详细信息
	 * @param orderNo
	 */
    PublishOrderEntity queryOrderByOrderNo(@Param("orderNo") String orderNo, @Param("curMonth") String curMonth);

    /**
     * 交易结果差异对比
     * @param publishOrderEntity
     * @return
     */
    List<PublishOrderEntity> queryByCondition(@Param("order") PublishOrderEntity publishOrderEntity,@Param("StartDateTime")String StartDateTime,@Param("EndDateTime")String EndDateTime);

    /**
     * 查询用户支付记录
     * @param orderEntity
     * @return
     */
    List<PayRecordEntity> queryPayRecord(PayRecordEntity orderEntity);

    List<PayRecordEntity> queryPayRecordList(PayRecordEntity orderEntity);

	/**
	 * 查询H5游戏用户支付记录
	 * @param orderEntity
	 * @return
	 */
	List<PayRecordEntity> queryYouPiaoPayRecord(PayRecordEntity orderEntity);


	/**
	 * 查询页游、VR游戏支付记录
	 * @return
	 */
	List<PayRecordEntity> queryPayStream(PayRecordEntity orderEntity);
}
