package com.dome.sdkserver.metadata.dao.mapper.bq.pay;

import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("orderMapper")
public interface OrderMapper extends IBaseMapperDao<OrderEntity, Long> {
	
	/**
	 * 创建订单信息
	 * @param orderEntity
	 */
	boolean insertOrder(@Param("entity")OrderEntity orderEntity);
	
	/**
     * 更新订单状态
     */
    boolean updateOrder(@Param("entity")OrderEntity orderEntity,@Param("curMonth")String curMonth);

    /**
     * 更新订单信息
     */
    boolean updateOrderInfo(@Param("entity")OrderEntity orderEntity,@Param("curMonth")String curMonth);

	/**
	 * 查询订单金额
	 * @param
	 */
	double queryOrderAmount(@Param("orderNo")String orderNo,@Param("curMonth")String curMonth);
	
	/**
	 * 查询订单金额
	 * @param orderNo
	 */
	OrderEntity queryOrderByOrderNo(@Param("orderNo")String orderNo,@Param("curMonth")String curMonth);
	
	/**
	 * 根据游戏订单号查询订单
	 * @param
	 */
	OrderEntity queryOrderByOutOrderNo(@Param("entity")OrderEntity orderEntity);
	
	void createTable(@Param("tableSuffix")String tableSuffix);

    /**
     * 创建第三方订单信息
     * @param orderEntity
     */
    boolean insertThirdOrder(@Param("entity")OrderEntity orderEntity);

	boolean insertThirdOrder2(@Param("entity")OrderEntity orderEntity);

    /**
     * 更新第三方订单信息
     * @param orderEntity
     */
    boolean updateThirdOrder(@Param("entity")OrderEntity orderEntity);

    /**
     * 查询第三方订单信息
     * @param orderEntity
     */
    OrderEntity queryThirdOrder(@Param("entity")OrderEntity orderEntity);

}
