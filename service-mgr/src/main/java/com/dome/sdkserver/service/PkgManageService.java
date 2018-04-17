package com.dome.sdkserver.service;

import java.util.List;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.PkgVo;
import com.dome.sdkserver.bo.SearchMerchantAppBo;
import com.dome.sdkserver.bo.pkgmanage.Pkg;

public interface PkgManageService {

	
	Pkg query(String appCode);
	
	List<PkgVo> queryHistory(String appCode);
	
	Pkg queryById(long id);
	
	int getAppInfoCountByCondition(SearchMerchantAppBo searchMerchantAppBo);
	
	List<MerchantAppInfo> getAppInfoByCondition(SearchMerchantAppBo searchMerchantAppBo);
}
