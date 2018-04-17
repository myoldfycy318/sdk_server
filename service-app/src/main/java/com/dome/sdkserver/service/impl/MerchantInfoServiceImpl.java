package com.dome.sdkserver.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.ResultBo;
import com.dome.sdkserver.metadata.dao.mapper.MerchantInfoMapper;
import com.dome.sdkserver.service.MerchantInfoService;
import com.dome.sdkserver.util.GlobalCodeUtils;

@Service
public class MerchantInfoServiceImpl implements MerchantInfoService {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private MerchantInfoMapper merchantInfoMapper;
	
	@Transactional
	@Override
	public ResultBo registeMerchant(MerchantInfo merchantInfo) {
		ResultBo result = new ResultBo();
		//业务校验
		try{
			merchantInfoMapper.addMerchantInfo(merchantInfo);
			//根据生成的ID更新编码
			String merchantCode = GlobalCodeUtils.genMerchantCode("M", merchantInfo.getMerchantInfoId());
			merchantInfoMapper.updateMerchantCode(merchantInfo.getMerchantInfoId(), merchantCode);;
		}catch(Exception e){
			log.error("新增商户失败 ,原因 :{}" , e.getMessage());
			throw new RuntimeException("新增商户失败");
		}
		return result ;
	}
	
	@Transactional
	@Override
	public ResultBo editMerchant(MerchantInfo merchantInfo,MerchantInfo loginMerchant) {
		ResultBo result = new ResultBo();
		try{
			merchantInfo.setMerchantInfoId(loginMerchant.getMerchantInfoId());
			merchantInfoMapper.editMerchantInfo(merchantInfo);
		}catch(Exception e){
			log.error("编辑商户失败 ,原因 :{}" , e.getMessage());
			throw new RuntimeException("编辑商户失败");
		}
		return result ;
	}
	

	@Override
	public MerchantInfo getMerchantInfoByUserId(Long userId) {
		MerchantInfo merchantInfo = merchantInfoMapper.getMerchantInfoByUserId(userId.intValue());
		return merchantInfo;
	}
}
