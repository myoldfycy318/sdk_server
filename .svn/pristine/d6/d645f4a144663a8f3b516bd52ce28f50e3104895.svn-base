package com.dome.sdkserver.metadata.dao.mapper.channel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.entity.channel.JsOrder;

public interface JsMapper {

	long add(@Param("js")JsOrder order);
	
	/**
	 * 更新结算订单号
	 * @param id
	 * @param orderNo
	 * @return
	 */
	int updateJsOrderNo(@Param("id")long id, @Param("orderNo")String orderNo);
	
	int update(@Param("js")JsOrder order);
	
	JsOrder select(@Param("orderNo")String orderNo);
	/**
	 * 查询渠道的最近的一条结算记录
	 * @param channelId
	 * @return
	 */
	JsOrder selectNew(@Param("channelId")long channelId);
	
	int selectCount(@Param("order")JsOrder order);
	
	/**
	 * 两种业务场景
	 * 1、app端 渠道商提供渠道id查询
	 * 2、mgr 提供渠道名称或状态查询
	 * @param order
	 * @param paginator
	 * @return
	 */
	List<JsOrder> selectList(@Param("order")JsOrder order, @Param("p")Paginator paginator);
	
	JsOrder selectAmount(@Param("channelId")long channelId, @Param("beginDate")String beginDate,
			@Param("endDate")String endDate);
}
