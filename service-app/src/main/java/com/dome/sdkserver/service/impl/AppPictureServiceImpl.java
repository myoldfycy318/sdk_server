package com.dome.sdkserver.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dome.sdkserver.bo.AppPicture;
import com.dome.sdkserver.metadata.dao.mapper.AppPictureMapper;
import com.dome.sdkserver.service.AppPictureService;

@Service
public class AppPictureServiceImpl implements AppPictureService {

	@Resource
	private AppPictureMapper picMapper;
	
	@Override
	public void add(AppPicture appPic) {
		picMapper.add(appPic);

	}

	@Override
	public void update(AppPicture appPic) {
		picMapper.update(appPic);

	}

	@Override
	public List<AppPicture> queryAppPicList(int appId) {
		// TODO Auto-generated method stub
		return picMapper.queryPicList(appId);
	}

	@Override
	public void batchAdd(List<AppPicture> appPicList) {
		picMapper.batchAdd(appPicList);
		
	}

	@Override
	public void del(int appId, List<String> picUrlList) {
		picMapper.del(appId, picUrlList);
		
	}

}
