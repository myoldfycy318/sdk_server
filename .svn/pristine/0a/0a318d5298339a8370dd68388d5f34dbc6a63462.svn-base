package com.dome.sdkserver.metadata.dao.mapper;

import com.dome.sdkserver.bo.AppTypeAttrInfo;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppTypeAttrMapper extends IBaseMapperDao<AppTypeAttrInfo, Long>{
	/**
	 * 根据类型属性父编码查找应用类型信息
	 * @param typeAttrParentCode 类型属性父编码
	 * @return
	 */
	List<AppTypeAttrInfo> getAppTypeAttrList(String typeAttrParentCode);

	/**
	 * 根据类型属性编码查找应用类型信息
	 * @param typeAttrCode 类型属性编码
	 * @return
	 */
	AppTypeAttrInfo getAppTypeAttrByCode(String typeAttrCode);
	
	void add(AppTypeAttrInfo appType);
}
