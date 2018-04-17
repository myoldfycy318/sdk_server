package com.dome.sdkserver.service;

import com.dome.sdkserver.bo.CallbackAudit;
import com.dome.sdkserver.bo.ChargePointInfo;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;

public interface OpenSdkSynDataService {

	public static final int TEST_DB = 11;
	
	void synMerchant(int dbIndex, MerchantInfo merchantInfo);
	
	void synApp(int dbIndex, MerchantAppInfo app);
	
	void synCharge(int dbIndex, ChargePointInfo charge);
	
	void synCallback(int dbIndex, CallbackAudit callbackAudit);

	void synH5(int dbIndex, AppInfoEntity app);
}
 