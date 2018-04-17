package com.dome.sdkserver.metadata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

public interface IBaseMapperDao<E, PK> extends IBaseMapper {

	/**
	 * @description 详细说明
	 * @param e
	 */
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void add(final E e);

	/**
	 * @description 详细说明
	 * @param e
	 */
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void batchAdd(final List<E> list);

	/**
	 * @description 详细说明
	 * @param e
	 */
	Integer remove(@Param(value = "id") final PK e);

	void batchRemove(final PK[] ids);

	/**
	 * @description 详细说明
	 * @param e
	 */
	Integer update(final E e);

	/**
	 * @description 详细说明
	 * @param id
	 * @return
	 * @throws Exception
	 */
	E getEntity(@Param(value = "id") final PK id);
}
