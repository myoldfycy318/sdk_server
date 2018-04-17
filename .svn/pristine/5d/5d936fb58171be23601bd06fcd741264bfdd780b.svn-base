package com.dome.sdkserver.service.award;


import com.dome.sdkserver.metadata.entity.bq.award.StoreAwardConfEntity;

import java.util.List;


/**
 * 游戏活动奖励规则
 * @author xuefeihu
 *
 */
public interface StoreAwardConfService {
	
	/**
	 * 插入规则
	 * @param entity
	 * @return
	 */
	boolean save(StoreAwardConfEntity entity);
	
	/**
	 * 上下架
	 * @param isPublish 0 下架、1 上架
	 * @return
	 */
	int publish(String storeConfId, int isPublish, int type);
	
	/**
	 * 查询配置
	 * @param time 发放时间
	 * @param appCode
     * @param payType 支付方式:1 钱宝 2 支付宝
	 * @return
	 */
	List<StoreAwardConfEntity> queryConf(String time, String appCode, String payType);
	
	/**
	 * 删除配置
	 * @param storeConfId 应用市场配置ID
	 * @return
	 */
	boolean deleteByStoreConfId(String storeConfId, Integer type);

}
