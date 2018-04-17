package com.dome.sdkserver.service;

import java.util.List;

import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.SearchMerchantInfoBo;

/**
 * 商户审核服务
 * @author hexiaoyi
 */
public interface MerchantInfoAuditService {
	
	/**
	 * 根据查询条件得到商户列表
	 * @param searchMerchantInfoBo
	 * @return
	 */
	List<MerchantInfo> getMerchantInfoByCondition(SearchMerchantInfoBo searchMerchantInfoBo);
	
	/**
	 * 获取总记录数
	 * @param searchMerchantInfoBo
	 * @return
	 */
	int getCountByCondition(SearchMerchantInfoBo searchMerchantInfoBo);
	
	/**
	 * 根据商户编码得到商户对象
	 * @param merchantCode
	 * @return
	 */
	MerchantInfo getMerchantInfoByCode(String merchantCode);
	/**
	 * 商户审核通过
	 * @param merchantCode
	 */
	void doPass(String merchantCode);
	/**
	 * 商户审核驳回
	 * @param merchantCode
	 */
	void doReject(String merchantCode, String reason);
	
	public MerchantInfo getMerchantInfoByUserId(Long userId);	
}
