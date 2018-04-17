package com.dome.sdkserver.metadata.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;

@Repository
public interface AppCheckintypeRelaMapper extends IBaseMapperDao<MerchantAppInfo, Long> {
	
	/**
	 * 删除关联关系
	 * @param appId
	 */
	void delAppRela(@Param("appId")Integer appId);
	
	/**
	 * 新增关联关系
	 * @param appId
	 * @param checkinTypeCode
	 */
	void addAppRela(@Param("appId")Integer appId,@Param("checkinTypeCode")String checkinTypeCode);
}
