package com.dome.sdkserver.metadata.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.PayApp;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;

/**
 * 
 * @author Frank.Zhou
 *
 */
@Repository
public interface UserCenterMapper extends IBaseMapperDao<MerchantInfo, Long> {
	
	/**
	 * 根据商户ID获取对应的应用总记录数
	 * 
	 * @param merchantInfoId
	 * @return
	 */
	int getAppCountByMerchantId(Integer merchantInfoId);
	
	/**
	 * 根据商户ID获取对应的应用列表
	 * 
	 * @param merchantInfoId
	 * @param start
	 * @param size
	 * @return
	 */
	List<MerchantAppInfo> getAppListByMerchantId(@Param("merchantInfoId")Integer merchantInfoId,@Param("start")Integer start,@Param("size")Integer size);
	
	/**
	 * 根据商户ID获取当前商户的支付应用
	 * 
	 * @param merchantInfoId
	 * @return
	 */
	List<PayApp> getPayAppByMerchantId(@Param("merchantInfoId")Integer merchantInfoId);
	
}
