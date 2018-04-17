package com.dome.sdkserver.service;

import java.util.List;



import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.pkgmanage.Pkg;

public interface PkgService {

	void upload(MerchantAppInfo app, Pkg p);
	
	void jiagu(Pkg p);
	
	/**
	 * 查询最新的两条包体上传记录
	 * @param appCode
	 * @return
	 */
	Pkg query(String appCode);
	
	List<Pkg> queryHistory(String appCode);
	
	Pkg queryById(long id);
	
	/**
	 * 查询最新的一条包体上传记录
	 * @param appCode
	 * @return
	 */
	Pkg queryNew(String appCode);
}
