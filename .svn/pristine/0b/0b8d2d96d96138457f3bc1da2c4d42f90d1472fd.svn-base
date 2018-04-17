package com.dome.sdkserver.metadata.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dome.sdkserver.bo.ResourceInfo;
import com.dome.sdkserver.bo.SearchResourceBo;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;

/**
 * 资源信息Mapper
 * @author liuxingyue
 *
 */
@Repository
public interface ResourceMapper extends IBaseMapperDao<ResourceInfo, Long> {
	/**
	 * 新增资源信息
	 * @param resourceInfo
	 */
	void addResourceInfo(@Param("entity")ResourceInfo resourceInfo);
	/**
	 * 根据条件查询资源信息
	 * @param searchResourceBo
	 * @return
	 */
	List<ResourceInfo> getResourceInfoListByCondition(@Param("entity")SearchResourceBo searchResourceBo);
	
	/**
	 * 根据条件查询资源信息总数
	 * @param searchResourceBo
	 * @return
	 */
	int getResourceInfoCountByCondition(@Param("entity")SearchResourceBo searchResourceBo);
	
	/**
	 * 根据id修改资源信息
	 * @param resourceInfo
	 */
	void editResourceInfoById(@Param("entity")ResourceInfo resourceInfo);
	
	/**
	 * 根据id查找资源信息
	 * @param resourceId
	 * @return
	 */
	ResourceInfo getResourceInfoListById(@Param("resourceId")Integer resourceId); 

}
