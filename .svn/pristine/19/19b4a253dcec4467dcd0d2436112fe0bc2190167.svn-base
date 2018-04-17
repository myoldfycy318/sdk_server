package com.dome.sdkserver.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.PayApp;
import com.dome.sdkserver.metadata.dao.mapper.MerchantInfoMapper;
import com.dome.sdkserver.metadata.dao.mapper.UserCenterMapper;
import com.dome.sdkserver.service.UserCenterService;
import com.dome.sdkserver.util.PropertiesUtil;

/**
 * 用户中心服务接口实现
 * 
 * @author Frank.Zhou
 *
 */
@Service("userCenterService")
public class UserCenterServiceImpl implements UserCenterService {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	MerchantInfoMapper merchantInfoMapper;

	@Resource
	UserCenterMapper userCenterMapper;

	@Resource
	private PropertiesUtil domainConfig;
	/*
	 * 
	 * @see com.dome.sdkserver.service.UserCenterService#getMerchantInfoByUserId(java.lang.Integer)
	 */
	@Override
	public MerchantInfo getMerchantInfoByUserId(Integer userId) {
		MerchantInfo merchantInfo = merchantInfoMapper.getMerchantInfoByUserId(userId);
		return merchantInfo;
	}

	/*
	 * 
	 * @see com.dome.sdkserver.service.UserCenterService#getAppListByMerchantId(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<MerchantAppInfo> getAppListByMerchantId(Integer merchantInfoId,Integer start,Integer size) {
		List<MerchantAppInfo> appList = userCenterMapper.getAppListByMerchantId(merchantInfoId,start,size);
		return appList;
	}
	
	/*
	 * 
	 * @see com.dome.sdkserver.service.UserCenterService#getAppCountByMerchantId(java.lang.Integer)
	 */
	@Override
	public int getAppCountByMerchantId(Integer merchantInfoId) {
		return userCenterMapper.getAppCountByMerchantId(merchantInfoId);
	}

	/*
	 * 
	 * @see com.dome.sdkserver.service.UserCenterService#getPayAppByUserId(java.lang.Integer)
	 */
	@Override
	public List<PayApp> getPayAppByMerchantId(Integer merchantInfoId) {
		return userCenterMapper.getPayAppByMerchantId(merchantInfoId);
	}

	/**
	 * 支付回调公钥，显示给合作伙伴
	 * @return
	 */
	public String getAsyncPublicKey(){
		
		return domainConfig.getString("async.public.key");
	}
}
