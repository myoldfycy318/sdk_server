package com.dome.sdkserver.metadata.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.entity.AbstractGame;

public interface GameMapper<T extends AbstractGame> {

	void insert(@Param("t")T t);
	
	/**
	 * 编辑和审批驳回使用
	 * @param t
	 * @return
	 */
	int update(@Param("t")T t);
	
	/**
	 * 审批通过使用，驳回操作需要填写驳回理由使用update()
	 * @param id
	 * @param status
	 * @return
	 */
	int updateStatus(@Param("id")int id, @Param("status")int status);
	
	/**
	 * 回写应用编码
	 * @param id
	 * @param code
	 * @return
	 */
	int updateCode(@Param("id")int id, @Param("code")String code);
	
	int delele(@Param("id")int id);
	
	T select(@Param("code")String code);
	
	List<T> selectList(int userId);
	/**
	 * 名称全匹配
	 * @param name
	 * @return
	 */
	T selectByName(@Param("name")String name);
	
	/**
	 * 下面两个方法需要参考，因为审批列表数据需要在代码层做一个整合
	 * 过滤字段和过去的相同
	 * MerchantAppMapper.queryMgrAppCount(MerchantAppInfo, String, String)
	 * @param app
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	int selectAuditCount(@Param("app")MerchantAppInfo app, @Param("beginDate")String beginDate,
			@Param("endDate")String endDate);
	
	List<T> selectAuditList(@Param("app")MerchantAppInfo app, @Param("p")Paginator p, @Param("beginDate")String beginDate,
			@Param("endDate")String endDate);
}
