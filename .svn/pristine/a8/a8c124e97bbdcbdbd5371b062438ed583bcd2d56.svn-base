package com.dome.sdkserver.service.impl.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.metadata.dao.mapper.bq.login.OauthAccessRecordMapper;
import com.dome.sdkserver.metadata.entity.bq.login.OauthAccessRecordEntity;
import com.dome.sdkserver.service.login.OauthAccessRecordService;


@Service("oauthAccessRecordService")
public class OauthAccessRecordServiceImpl implements OauthAccessRecordService {

	@Autowired
	private OauthAccessRecordMapper oauthAccessRecordMapper;

	@Override
	public void addOauthAccessRecord(OauthAccessRecordEntity accessRecord) {
		oauthAccessRecordMapper.insertOauthAccessRecord(accessRecord);
	}

	@Override
	public void createTable() {
		String tableSuffix = DateUtils.getCurDateFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT);
		oauthAccessRecordMapper.createTable(tableSuffix);
	}
}