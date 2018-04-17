package com.dome.sdkserver.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.bo.AppPicture;


public interface AppPictureService {

	void add(AppPicture appPic);
	
	void update(AppPicture appPic);
	
	List<AppPicture> queryAppPicList(int appId);
	
	void batchAdd(List<AppPicture> appPicList);
	
	void del(@Param("appId")int appId, @Param("picUrlList")List<String> picUrlList);
}
