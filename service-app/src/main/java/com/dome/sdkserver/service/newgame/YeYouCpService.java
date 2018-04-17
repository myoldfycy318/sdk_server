package com.dome.sdkserver.service.newgame;

import java.util.List;
import java.util.Set;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bo.SearchChargePointBo;
import com.dome.sdkserver.metadata.entity.yeyou.YeYouCp;

public interface YeYouCpService {
	String add(YeYouCp cp);
	
	int selectCount(SearchChargePointBo searchChargePointBo);
	
	List<YeYouCp> selectList(SearchChargePointBo searchChargePointBo, Paginator paginator);
	
	YeYouCp select(String code);
	
	String update(YeYouCp cp);
	
	/**
	 * 变更计费点
	 * 将已生效的记录设置为失效 新增一条记录
	 * @param cp
	 * @return
	 */
	String change(YeYouCp cp);
	
	String delete(String code);
	
	String updateStatus(String code, int status);
	
	/**
	 * 批量提交计费点
	 * @param appCode
	 * @param cpSet
	 * @return
	 */
	String batchSubmitCps(String appCode, Set<String> cpSet);
	
}
