package com.dome.sdkserver.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.MerchantVo;
import com.dome.sdkserver.bo.SearchMerchantInfoBo;
import com.dome.sdkserver.constants.AuditStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.MerchantInfoMapper;
import com.dome.sdkserver.service.MerchantInfoAuditService;
import com.dome.sdkserver.service.OpenSdkSynDataService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.util.MultipleDatasource;
import com.dome.sdkserver.util.PropertiesUtil;

/**
 * 商户信息审核服务实现
 * @author hexiaoyi
 */
@Service
public class MerchantInfoAuditServiceImpl implements MerchantInfoAuditService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	private MerchantInfoMapper merchantInfoMapper;
	
//	@Resource
//	private ResourceMapper resourceMapper;
	
	@Autowired
	PropertiesUtil domainConfig;
	
	@Resource
	private OpenSdkSynDataService openSdkSynDataServiceImpl;
	
	@Override
	public List<MerchantInfo> getMerchantInfoByCondition(
			SearchMerchantInfoBo searchMerchantInfoBo) {
		List<MerchantInfo> merchantInfoList = merchantInfoMapper.getMerchantInfoListByCondition(searchMerchantInfoBo);
		for(MerchantInfo merchantInfo : merchantInfoList){
			// 设置商户状态描述
			int statusVal = merchantInfo.getStatus();
			merchantInfo.setStatusDesc(AuditStatusEnum.getFromKey(statusVal).getMsg());
			merchantInfo.setApplyDate(DateUtil.dateToDateString(merchantInfo.getUpdateTime()));
		}
		return merchantInfoList;
	}

	@Override
	public int getCountByCondition(SearchMerchantInfoBo searchMerchantInfoBo) {
		int count = merchantInfoMapper.getCountByCondition(searchMerchantInfoBo);
		return count;
	}

	@Override
	public MerchantInfo getMerchantInfoByCode(String merchantCode) {
		MerchantInfo m = merchantInfoMapper.getMerchantInfoByCode(merchantCode);
		if (m != null) {
			// 设置商户状态描述
			int statusVal = m.getStatus();
			m.setStatusDesc(AuditStatusEnum.getFromKey(statusVal).getMsg());
		}
		return m;
	}

	@Override
	public void doReject(String merchantCode, String reason) {
		MerchantInfo merchantInfo = new MerchantInfo();
		merchantInfo.setMerchantCode(merchantCode);
		merchantInfo.setStatus(AuditStatusEnum.AUDIT_REJECT.getStatus());
		merchantInfo.setRemark(reason);
		merchantInfoMapper.editMerchantInfoByCode(merchantInfo);
		
	}

	@Override
	@Transactional
	public void doPass(final String merchantCode) {
		MerchantInfo merchantInfo = new MerchantInfo();
		merchantInfo.setMerchantCode(merchantCode);
		merchantInfo.setStatus(AuditStatusEnum.AUDIT_PASS.getStatus());
		merchantInfo.setRemark(AuditStatusEnum.AUDIT_PASS.getMsg());
		merchantInfoMapper.editMerchantInfoByCode(merchantInfo);
		
		// 同步数据到宝玩
		String errorMsg = null;
		try {
			errorMsg = doSynMerchantToBaowan(merchantCode);
		} catch (Exception e) {
			errorMsg = "商户信息同步到宝玩出错";
			log.error(errorMsg, e);
		}
		errorMsg = synTest(merchantCode);
		if (errorMsg != null && !"".equals(errorMsg)) throw new RuntimeException(errorMsg);
		
	}
	
	private String synTest(String merchantCode){
		try {
			MerchantInfo merchantInfo = merchantInfoMapper.getMerchantInfoByCode(merchantCode);
			MultipleDatasource.setDataSource("testDataSource");
			openSdkSynDataServiceImpl.synMerchant(OpenSdkSynDataService.TEST_DB, merchantInfo);
			MultipleDatasource.setDataSource("dataSource");
		} catch (Exception e) {
			String errorMsg = "商户信息同步到测试环境失败";
			log.error(errorMsg, e);
			return errorMsg;
		}
		return StringUtils.EMPTY;
	}
	private String doSynMerchantToBaowan(String merchantCode) {
		MerchantInfo merchant = this.getMerchantInfoByCode(merchantCode);
		if (merchant != null) {
			String url = domainConfig.getString("domesdk.merchant.synbaowan");
			MerchantVo merchantVo = new MerchantVo();
			BeanUtils.copyProperties(merchant, merchantVo);
			JSON json = (JSON) JSON.toJSON(merchantVo);
			log.info("同步商家信息到宝玩渠道：{}", (json == null ? "" : json.toString()));
			String response = ApiConnector.postJson(url, json);
			log.info("同步商家信息到宝玩渠道返回结果：{}", response);
			
			if (!StringUtils.isEmpty(response)){
				JSONObject jsonObj = JSON.parseObject(response);
				if (jsonObj.getInteger("responseCode") == 1000 ){
					return StringUtils.EMPTY;
				}
				
			}
		}
		return "商户信息同步到宝玩渠道异常";
	}

	@Override
	public MerchantInfo getMerchantInfoByUserId(Long userId) {
		MerchantInfo merchantInfo = merchantInfoMapper.getMerchantInfoByUserId(userId.intValue());
		if (merchantInfo != null) {
			// 设置商户状态描述
			int statusVal = merchantInfo.getStatus();
			merchantInfo.setStatusDesc(AuditStatusEnum.getFromKey(statusVal).getMsg());
		}
		return merchantInfo;
	}
}
