package com.dome.sdkserver.metadata.dao.mapper.bq.login;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import com.dome.sdkserver.metadata.entity.bq.login.OauthAccessRecordEntity;

@Repository
public interface OauthAccessRecordMapper extends IBaseMapperDao<OauthAccessRecordEntity, String> {
	public void insertOauthAccessRecord(OauthAccessRecordEntity entity);
	
	void createTable(@Param("tableSuffix")String tableSuffix);
}
