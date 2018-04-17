package com.dome.sdkserver.service;

import com.dome.sdkserver.bo.MerchantAppInfo;


public interface AppService {

	MerchantAppInfo selectApp(String appCode);
}
