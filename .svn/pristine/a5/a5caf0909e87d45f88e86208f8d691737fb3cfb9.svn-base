package com.dome.sdkserver.metadata.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dome.sdkserver.bo.PayConfigInfo;
import com.dome.sdkserver.bo.ResourceInfo;
import com.dome.sdkserver.bo.SearchPayConfigInfoBo;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;

/**
 * 应用接入支付配置Mapper
 * @author liuxingyue
 *
 */
@Repository
public interface PayConfigMapper extends IBaseMapperDao<PayConfigInfo, Long>{
	/**
	 * 新增配置信息
	 * @param payConfigInfo
	 */
	void addPayConfigInfo(@Param("entity")PayConfigInfo payConfigInfo);
	
	/**
	 * 根据查询条件查询
	 * @param searchResourceBo
	 * @return
	 */
	List<PayConfigInfo> getPayConfigListByCondition(@Param("entity")SearchPayConfigInfoBo searchPayConfigInfoBo);
	
	/**
	 * 根据查询条件获得查询总数
	 * @param searchResourceBo
	 * @return
	 */
	int getPayConfigCountByCondition (@Param("entity")SearchPayConfigInfoBo searchPayConfigInfoBo);
	
	/**
	 * 根据id修改配置信息
	 * @param payConfigInfo
	 */
	int editPayConfigById(@Param("entity")PayConfigInfo payConfigInfo);
	
	/**
	 * 根据id查找配置信息
	 * @param configId
	 * @return
	 */
	PayConfigInfo getPayConfigById(@Param("configId")Integer configId); 
	
	/**
	 * 根据应用编码查询支付配置信息
	 * @param appCode  应用编码
	 * @return
	 */
	PayConfigInfo queryByAppCode(@Param("appCode")String appCode);

}
