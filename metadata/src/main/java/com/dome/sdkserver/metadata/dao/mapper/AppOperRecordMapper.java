package com.dome.sdkserver.metadata.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.metadata.dao.IBaseMapper;
import com.dome.sdkserver.metadata.entity.AppOperRecord;

public interface AppOperRecordMapper extends IBaseMapper{
	void insert(@Param("r")AppOperRecord record);
	
	List<AppOperRecord> queryOperRecordList(@Param("appId")int appId, @Param("begin")int begin, @Param("pageSize")int pageSize);
	
	int selectOperRecordCount(@Param("appId")int appId);

    Integer maxId();

	/**
	 * 以下三个方法针对页游和appCode
	 * @param record 需要有appCode
	 */
	void insertGame(@Param("r")AppOperRecord record);
	
	List<AppOperRecord> selectGameOperList(@Param("appCode")String appCode, @Param("begin")int begin, @Param("pageSize")int pageSize);
	
	int selectGameOperCount(@Param("appCode")String appCode);
}
