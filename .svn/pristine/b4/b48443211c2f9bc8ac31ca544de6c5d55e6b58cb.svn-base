package com.dome.sdkserver.metadata.dao.mapper;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.SearchMerchantAppBo;
import com.dome.sdkserver.bo.pkgmanage.Pkg;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PkgMapper extends IBaseMapperDao<Pkg, Long>{

	void upload(@Param("p")Pkg p);
	
	void jiagu(@Param("p")Pkg p);
	
	/**
	 * 查询最新的两条包体上传记录
	 * @param appCode
	 * @return
	 */
	List<Pkg> query(@Param("appCode")String appCode);
	
	List<Pkg> queryHistory(@Param("appCode")String appCode);
	
	Pkg queryById(@Param("id")long id);
	
	/**
	 * 查询最新的一条包体上传记录
	 * @param appCode
	 * @return
	 */
	Pkg queryNew(@Param("appCode")String appCode);
	
	List<MerchantAppInfo> getAppListByCondition(@Param("entity")SearchMerchantAppBo searchMerchantAppBo);
	
	int getAppInfoCountByCondition(@Param("entity")SearchMerchantAppBo searchMerchantAppBo);
}
