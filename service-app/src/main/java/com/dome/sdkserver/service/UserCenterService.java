package com.dome.sdkserver.service;

import java.util.List;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.PayApp;

/**
 * 用户中心服务接口
 * 
 * @author Frank.Zhou
 *
 */
public interface UserCenterService {

	/**
	 * 根据用户ID获取对应的商户信息
	 * 
	 * @param userId
	 * @return
	 */
	MerchantInfo getMerchantInfoByUserId(Integer userId);
	
	/**
	 * 根据商户ID获取对应的应用列表
	 * @param merchantInfoId
	 * @param start
	 * @param size
	 * @return
	 */
	List<MerchantAppInfo> getAppListByMerchantId(Integer merchantInfoId,Integer start,Integer size);
	
	/**
	 * 根据商户ID获取对应的应用总记录数
	 * 
	 * @param merchantInfoId
	 * @return
	 */
	int getAppCountByMerchantId(Integer merchantInfoId);
	
	
	/**
	 * 根据商户ID获取当前商户的支付应用
	 * 
	 * @param merchantInfoId
	 * @return
	 */
	 List<PayApp> getPayAppByMerchantId(Integer merchantInfoId);
	
	 String getAsyncPublicKey();
}
