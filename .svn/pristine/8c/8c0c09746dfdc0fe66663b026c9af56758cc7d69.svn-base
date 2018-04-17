package com.dome.sdkserver.metadata.dao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * mapper基类
 * @author lilongwei
 *
 * @param <T>
 */
public interface BaseMapper<T> {
	void insert(@Param("t")T t);
	
	int update(@Param("t")T t);
	
	int delele(@Param("id")int id);
	
	T select(@Param("code")String code);
}
