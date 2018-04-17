package com.dome.sdkserver.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;
import com.dome.sdkserver.metadata.dao.mapper.AppOperRecordMapper;
import com.dome.sdkserver.metadata.entity.AppOperRecord;
import com.dome.sdkserver.service.AppOperRecordService;
import com.dome.sdkserver.service.AppService;
import com.dome.sdkserver.util.business.GameUtils;

@Service
public class AppOperRecordServiceImpl implements AppOperRecordService {

	@Resource
	private AppOperRecordMapper appOperRecordMapper;
	
	@Autowired
	private AppService appService;
	@Override
	public void insert(AppOperRecord record) {
		appOperRecordMapper.insert(record);

	}

	@Override
	public List<AppOperRecord> queryOperRecordList(String appCode, int begin, int pageSize) {
		MerchantAppInfo merchantAppInfo = appService.selectApp(appCode);
        if (merchantAppInfo == null)
            return Collections.emptyList();
        GameTypeEnum em = GameUtils.analyseGameType(appCode);
		if (em==GameTypeEnum.mobilegame){
			return appOperRecordMapper.queryOperRecordList(merchantAppInfo.getAppId(), begin, pageSize);
		} else { // 页游和h5
			return appOperRecordMapper.selectGameOperList(appCode, begin, pageSize);
		}
	}

	@Override
	public int selectOperRecordCount(String appCode) {
		MerchantAppInfo merchantAppInfo = appService.selectApp(appCode);
        if (merchantAppInfo == null)
            return 0;
        GameTypeEnum em = GameUtils.analyseGameType(appCode);
		if (em==GameTypeEnum.mobilegame){
			return appOperRecordMapper.selectOperRecordCount(merchantAppInfo.getAppId());
		} else { // 页游和h5
			return appOperRecordMapper.selectGameOperCount(appCode);
		}
	}

    @Override
    public Integer maxId() {
        return appOperRecordMapper.maxId();
    }
}
