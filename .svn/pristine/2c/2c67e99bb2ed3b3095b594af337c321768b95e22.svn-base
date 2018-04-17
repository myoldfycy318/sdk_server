package com.dome.sdkserver.metadata.dao.mapper;

import com.dome.sdkserver.bo.AppPicture;
import com.dome.sdkserver.metadata.dao.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppPictureMapper extends IBaseMapper {

	void add(AppPicture pic);
	
	void update(AppPicture pic);
	
	List<AppPicture> queryPicList(@Param("appId")int appId);
	
	void batchAdd(@Param("appPicList")List<AppPicture> appPicList);
	
	void del(@Param("appId")int appId, @Param("picUrlList")List<String> picUrlList);
}
