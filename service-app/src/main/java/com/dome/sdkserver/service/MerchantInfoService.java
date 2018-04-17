package com.dome.sdkserver.service;

import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.ResultBo;

/**
 * 前端商户信息
 * @author hexiaoyi
 */
public interface MerchantInfoService {
	
	/**
	 * 新增商户
	 * @param merchantInfo
	 */
	ResultBo registeMerchant(MerchantInfo merchantInfo);
	
	/**
	 * 重新提交商户
	 * @param merchantInfo
	 */
	ResultBo editMerchant(MerchantInfo merchantInfo,MerchantInfo loginMerchant);
	
	/**
	 * 根据userId获得商户信息
	 * @param userId
	 * @return
	 */
	MerchantInfo getMerchantInfoByUserId(Long userId);
}
