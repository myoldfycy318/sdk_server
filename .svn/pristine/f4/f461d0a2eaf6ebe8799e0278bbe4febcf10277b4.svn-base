package com.dome.sdkserver.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dome.sdkserver.bo.CallbackAudit;
import com.dome.sdkserver.bo.ChargePointInfo;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.constants.AuditStatusEnum;
import com.dome.sdkserver.constants.ChargePointStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.CallbackAuditMapper;
import com.dome.sdkserver.metadata.dao.mapper.ChargePointMapper;
import com.dome.sdkserver.metadata.dao.mapper.MerchantAppMapper;
import com.dome.sdkserver.metadata.dao.mapper.MerchantInfoMapper;
import com.dome.sdkserver.metadata.dao.mapper.bq.pay.AppInfoMapper;
import com.dome.sdkserver.metadata.dao.mapper.h5.H5GameMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.metadata.entity.h5.H5Game;
import com.dome.sdkserver.service.OpenSdkSynDataService;
import com.qianbao.framework.datasource.annotation.DataSource;

/**
 * 同步商户、应用和计费点数据到测试环境
 * 只有新起事务，才能重新获取连接，这样数据源切换才生效
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class OpenSdkSynDataServiceImpl implements OpenSdkSynDataService {

	@Autowired
	private MerchantInfoMapper merchantInfoMapper;
	
	@Autowired
	private MerchantAppMapper merchantAppMapper;
	
	@Autowired
	private ChargePointMapper chargePointMapper;
	
	@Resource
    private CallbackAuditMapper callbackAuditMapper;
	
	@Resource
	AppInfoMapper appInfoMapper;
	
	@Resource
	H5GameMapper<H5Game> h5GameMapper;
	

	@Override
	public void synMerchant(@DataSource(field="dbIndex")int dbIndex, MerchantInfo merchantInfo) {
		
		merchantInfoMapper.deleteMerchant(merchantInfo.getMerchantCode());
		merchantInfo.setStatus(AuditStatusEnum.AUDIT_PASS.getStatus());
		merchantInfoMapper.addMerchantInfo(merchantInfo);
	}

	@Override
	public void synApp(@DataSource(field="dbIndex")int dbIndex, MerchantAppInfo app) {
		merchantAppMapper.deleteApp(app.getAppCode());
		app.setStatus(AppStatusEnum.shelf_finish.getStatus());
		merchantAppMapper.addApp(app);
	}

    /**
     * 预发布测试库(test_dome_sdkserver ,
     * 在预发布环境开放平台后台的jdbc.properties配置文件中test.jdbc.url值为test_dome_sdkserver库)
     */
	@Override
	public void synH5(@DataSource(field="dbIndex")int dbIndex, AppInfoEntity app) 
	{
		//冰趣是HD
		if(app.getAppCode().startsWith("HD"))
		{
			appInfoMapper.deleteBqH5game(app.getAppCode());
            //将H5预发布环境测试库的 app 对象的status修改为1 ,这样联调人员在用测试库才能进行测试,否则同步到测试库的状态为12, 联调人员那边没有办法测试
            app.setStatus(AppStatusEnum.shelf_finish.getStatus());
			appInfoMapper.insertBqH5game(app);
		}
		//宝玩是H
		else if(app.getAppCode().startsWith("H"))
		{
			appInfoMapper.deleteBwH5game(app.getAppCode());
            //将H5预发布环境测试库的 app 对象的status修改为1 ,这样联调人员在用测试库才能进行测试,否则同步到测试库的状态为12, 联调人员那边没有办法测试
            app.setStatus(AppStatusEnum.shelf_finish.getStatus());
			appInfoMapper.insertBwH5game(app);
		}
	}

	@Override
	public void synCharge(@DataSource(field="dbIndex")int dbIndex, ChargePointInfo charge) {
		chargePointMapper.deleteCharge(charge.getChargePointCode());
		charge.setStatus(ChargePointStatusEnum.ENABLED.getStatus());
		chargePointMapper.addChargePointInfo(charge);
	}

	@Override
	public void synCallback(@DataSource(field="dbIndex")int dbIndex, CallbackAudit callbackAudit) {
		MerchantAppInfo merchantAppInfo = new MerchantAppInfo();
		merchantAppInfo.setAppCode(callbackAudit.getAppCode());
		merchantAppInfo.setTestLoginCallBackUrl(callbackAudit.getTestLoginCallbackUrl());
		merchantAppInfo.setLoginCallBackUrl(callbackAudit.getLoginCallbackUrl());
		merchantAppInfo.setTestPayCallBackUrl(callbackAudit.getTestPayCallbackUrl());
		merchantAppInfo.setPayCallBackUrl(callbackAudit.getPayCallbackUrl());
        merchantAppInfo.setRegistCallBackUrl(callbackAudit.getRegistCallbackUrl());
        merchantAppInfo.setTestRegistCallBackUrl(callbackAudit.getTestRegistCallbackUrl());
        //游戏icon
		merchantAppInfo.setAppIcon(callbackAudit.getAppIcon());
		merchantAppMapper.modfiyCallbackUrl(merchantAppInfo);
		
	}

}
