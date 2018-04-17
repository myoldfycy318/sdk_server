package com.dome.sdkserver.service.login;

import com.dome.sdkserver.metadata.entity.bq.login.OauthAccessRecordEntity;

public interface OauthAccessRecordService {
	/**
	 * 添加oauth访问记录
	 * 
	 * @param accessRecord
	 *            访问记录对象
	 */
	void addOauthAccessRecord(OauthAccessRecordEntity accessRecord);
	
	public void createTable();
}