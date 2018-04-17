package com.dome.sdkserver.service.impl;

import com.dome.sdkserver.bo.AppTypeAttrBo;
import com.dome.sdkserver.bo.AppTypeAttrInfo;
import com.dome.sdkserver.metadata.dao.mapper.AppTypeAttrMapper;
import com.dome.sdkserver.service.AppTypeAttrService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author liuxingyue
 *
 */
@Service
public class AppTypeAttrServiceImpl implements AppTypeAttrService {
	@Resource
	private AppTypeAttrMapper appTypeAttrMapper;

	@Override
    public List<AppTypeAttrInfo> getAppTypeAttrList(String typeAttrParentCode) {
        List<AppTypeAttrInfo> list = new ArrayList<AppTypeAttrInfo>();
        //获取所有的
        List<AppTypeAttrInfo> appTypeAttrInfos = appTypeAttrMapper.getAppTypeAttrList(null);
        if (appTypeAttrInfos == null || appTypeAttrInfos.size() == 0)
            return list;
        for (AppTypeAttrInfo appTypeAttrInfo : appTypeAttrInfos) {
            if (ArrayUtils.contains(appTypeAttrInfo.getTypeAttrParentCode().split(","), typeAttrParentCode)) {
                list.add(appTypeAttrInfo);
            }
        }
        return list;
    }

	@Override
	public List<AppTypeAttrBo> getAppType(String typeAttrCode) {
		List<AppTypeAttrBo> list = new ArrayList<AppTypeAttrBo>();
		while(true){
			AppTypeAttrBo appTypeAttrBo = new AppTypeAttrBo();
			AppTypeAttrInfo appTypeAttrInfo = appTypeAttrMapper.getAppTypeAttrByCode(typeAttrCode);
			appTypeAttrBo.setLevel(appTypeAttrInfo.getLevel());
			appTypeAttrBo.setTypeAttrId(appTypeAttrInfo.getTypeAttrId());
			appTypeAttrBo.setTypeAttrCode(appTypeAttrInfo.getTypeAttrCode());
			appTypeAttrBo.setTypeAttrName(appTypeAttrInfo.getTypeAttrName());
			appTypeAttrBo.setTypeAttrParentCode(appTypeAttrInfo.getTypeAttrParentCode());
			list.add(appTypeAttrBo);
			typeAttrCode = appTypeAttrBo.getTypeAttrParentCode();
			if("0".equals(typeAttrCode)){
				break;
			}
			
		}
		return list;
	}

}
