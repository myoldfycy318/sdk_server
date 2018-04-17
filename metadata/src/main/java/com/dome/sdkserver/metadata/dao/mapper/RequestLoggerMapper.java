package com.dome.sdkserver.metadata.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.metadata.entity.RequestLogger;

public interface RequestLoggerMapper {

	void add(@Param("log")RequestLogger logger, @Param("month")String month);
}
