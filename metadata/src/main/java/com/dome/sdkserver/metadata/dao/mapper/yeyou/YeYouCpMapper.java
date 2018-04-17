package com.dome.sdkserver.metadata.dao.mapper.yeyou;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bo.SearchChargePointBo;
import com.dome.sdkserver.metadata.entity.yeyou.YeYouCp;

public interface YeYouCpMapper<T extends YeYouCp>{
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
	
	int selectCount(@Param("entity")SearchChargePointBo searchChargePointBo);
	
	List<T> selectList(@Param("entity")SearchChargePointBo searchChargePointBo, @Param("p")Paginator paginator);
	/**
	 * 名称全匹配
	 * @param name
	 * @return
	 */
	T selectByName(@Param("appCode")String appCode, @Param("name")String name);
	
	/**
	 * 查询已驳回和待提交的计费点数目
	 * @return
	 */
	int selectNeedHandleCpCount(@Param("appCode")String appCode);
}
