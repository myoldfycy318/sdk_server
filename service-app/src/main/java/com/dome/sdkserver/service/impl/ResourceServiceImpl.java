package com.dome.sdkserver.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dome.sdkserver.bo.ResourceInfo;
import com.dome.sdkserver.bo.SearchResourceBo;
import com.dome.sdkserver.metadata.dao.mapper.ResourceMapper;
import com.dome.sdkserver.service.ResourceService;
import com.dome.sdkserver.util.DateUtil;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Resource
	private ResourceMapper resourceMapper;
	
	@Override
	public List<ResourceInfo> getResourceInfoListByColumn(String resourceColumn) {
		SearchResourceBo searchResourceBo = new SearchResourceBo();
		searchResourceBo.setResourceColumn(resourceColumn);
		List<ResourceInfo> resourceInfoList = resourceMapper
				.getResourceInfoListByCondition(searchResourceBo);
		for(ResourceInfo resourceInfo : resourceInfoList){
			resourceInfo.setShowUpdateTime(DateUtil.dateToDateString(resourceInfo.getUpdateTime()));
		}
		return resourceInfoList;
	}

	@Override
	public ResourceInfo getResourceInfoById(Integer resourceId) {
		return resourceMapper.getResourceInfoListById(resourceId);
	}

}
