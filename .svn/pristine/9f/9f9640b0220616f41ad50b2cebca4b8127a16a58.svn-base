package com.dome.sdkserver.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.PkgVo;
import com.dome.sdkserver.bo.SearchMerchantAppBo;
import com.dome.sdkserver.bo.pkgmanage.Pkg;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.PkgMapper;
import com.dome.sdkserver.service.PkgManageService;

@Service
public class PkgManageServiceImpl implements PkgManageService {
	
	@Resource
	private PkgMapper pkgMapper;
	
	@Override
	public Pkg query(String appCode) {
		List<Pkg> pkgList = pkgMapper.query(appCode);
		if (!CollectionUtils.isEmpty(pkgList)) {
			Pkg p = pkgList.get(0);
			p.setId(0l);
			// 最新的上传包体记录中没有发现有加固，取上一次加固的数据
			if ((p.getJiaguPath() == null || "".equals(p.getJiaguPath())) && pkgList.size() >= 2) {
				Pkg p2 = pkgList.get(1);
				if (!StringUtils.isEmpty(p2.getJiaguPath()) && p2.getJiaguStatus() == 1) { // 加固成功，上一条上传包体记录中若仍没有发现有加固，就放弃继续查找
					p.setJiaguPath(p2.getJiaguPath());
					p.setJiaguTime(p2.getJiaguTime());
					p.setSdkVersion(p2.getSdkVersion());
					p.setId(p2.getId());
				}
			}
			return p;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PkgVo> queryHistory(String appCode) {
		List<Pkg> pkgList = pkgMapper.queryHistory(appCode);
		if (!pkgList.isEmpty()){
			
			List<PkgVo> voList = new ArrayList<PkgVo>(pkgList.size());
			for (int i = 0, size = pkgList.size(); i < size; i++) {
				Pkg pkg = pkgList.get(i);
				
				PkgVo vo = new PkgVo();
				vo.setName(pkg.getName());
				vo.setUploadTime(pkg.getUploadTime());
				if (i == 0){
					vo.setRemark("上传包体");
				} else {
					vo.setRemark("变更包体");
				}
				voList.add(vo);
			}
			return voList;
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public Pkg queryById(long id) {
		
		return pkgMapper.queryById(id);
	}

	@Override
	public int getAppInfoCountByCondition(SearchMerchantAppBo searchMerchantAppBo) {
		// TODO Auto-generated method stub
		return pkgMapper.getAppInfoCountByCondition(searchMerchantAppBo);
	}

	@Override
	public List<MerchantAppInfo> getAppInfoByCondition(SearchMerchantAppBo searchMerchantAppBo) {
		List<MerchantAppInfo> appList = pkgMapper.getAppListByCondition(searchMerchantAppBo);
		// 需要展示应用状态
		for (MerchantAppInfo app : appList) {
			int status = app.getStatus();
			AppStatusEnum em = AppStatusEnum.getFromKey(status);
			if (em != null) {
				app.setStatusDesc(em.getMsg());
			}
		}
		return appList;
	}

}
