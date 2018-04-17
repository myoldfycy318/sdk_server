package com.dome.sdkserver.service.newgame.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bo.SearchChargePointBo;
import com.dome.sdkserver.constants.ChargePointStatusEnum;

import static  com.dome.sdkserver.constants.ChargePointStatusEnum.*;

import com.dome.sdkserver.metadata.dao.mapper.yeyou.YeYouCpMapper;
import com.dome.sdkserver.metadata.entity.yeyou.YeYouCp;
import com.dome.sdkserver.service.newgame.YeYouCpService;

@Service
public class YeyouCpServiceImpl implements YeYouCpService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YeYouCpMapper<YeYouCp> yeYouCpMapper;
	@Override
	public String add(YeYouCp cp) {
		YeYouCp yyCp = yeYouCpMapper.selectByName(cp.getAppCode(), cp.getChargePointName());
		if (yyCp!=null) return "计费点名称已被使用，名称："+cp.getChargePointName();
		yeYouCpMapper.insert(cp);
		int id = cp.getChargePointId();
		String cpCode =String.format("C%07d", id);
		yeYouCpMapper.updateCode(id, cpCode);
		cp.setChargePointCode(cpCode);
		return null;
	}

	@Override
	public YeYouCp select(String code) {
		
		return yeYouCpMapper.select(code);
	}

	@Override
	public String update(YeYouCp cp) {
		YeYouCp yyCp = yeYouCpMapper.selectByName(cp.getAppCode(), cp.getChargePointName());
		// 一款应用中不能名称相同的计费点，不同游戏可以重复
		if (yyCp!=null 
				&& !yyCp.getChargePointCode().equals(cp.getChargePointCode())
				){
			return "计费点名称已被使用，名称："+cp.getChargePointName();
		}
		int line=yeYouCpMapper.update(cp);
		if (line!=1) return "编辑计费点出错";
		return null;
	}

	@Override
	public String delete(String code) {
		YeYouCp cp = yeYouCpMapper.select(code);
		// 驳回状态可以删除
		if (cp.getStatus() != DRAFT.getStatus() && cp.getStatus() != REJECT.getStatus()){
			return "计费点不能删除，状态为"+ChargePointStatusEnum.getStatusDesc(cp.getStatus());
		}
		int line=yeYouCpMapper.delele(cp.getChargePointId());
		if (line!=1) return "删除计费点不成功,chargePointCode="+code;
		return null;
	}

	@Override
	public String updateStatus(String code, int status) {
		YeYouCp cp = yeYouCpMapper.select(code);
		final int pastStatus = cp.getStatus();
		if (pastStatus==DRAFT.getStatus() ||pastStatus== REJECT.getStatus()){
			if (status==WAIT_AUDIT.getStatus()){
				yeYouCpMapper.updateStatus(cp.getChargePointId(), status);
			}
		}
		logger.error("页游计费点流程申请异常，变更前状态为{}，变更后状态为{}，判断为非法操作",
				getStatusDesc(pastStatus), getStatusDesc(status));
		return "计费点流程申请被拒绝";
	}

	@Override
	public int selectCount(SearchChargePointBo searchChargePointBo) {
		
		return yeYouCpMapper.selectCount(searchChargePointBo);
	}

	@Override
	public List<YeYouCp> selectList(SearchChargePointBo searchChargePointBo,
			Paginator paginator) {
		
		return yeYouCpMapper.selectList(searchChargePointBo, paginator);
	}

	@Override
	public String batchSubmitCps(String appCode, Set<String> cpSet) {
		// 校验计费点
		Set<Integer> cpIdSet = new HashSet<>(cpSet.size());
		for (String cpCode:cpSet){
			YeYouCp cp = yeYouCpMapper.select(cpCode);
			if (cp==null || !cp.getAppCode().equals(appCode)){
				logger.error("页游计费点审请发现计费点不存在或越权操作,appCode={}, chargePointCode={}", appCode, cpCode);
				return "计费点提交被拒绝,chargePointCode="+cpCode;
			}
			if (cp.getStatus()!=DRAFT.getStatus()){
				logger.error("页游计费点审请计费点的状态不为草稿，提交被拒绝,appCode={}, chargePointCode={}", appCode, cpCode);
				return "计费点的状态为"+getStatusDesc(cp.getStatus())+",提交被拒绝,chargePointCode="+cpCode;
			}
			cpIdSet.add(cp.getChargePointId());
		}
		for (int id :cpIdSet){
			yeYouCpMapper.updateStatus(id, WAIT_AUDIT.getStatus());
		}
		
		return null;
	}

	@Override
	public String change(YeYouCp cp) {
		final String cpCode = cp.getChargePointCode();
		YeYouCp yyCp = yeYouCpMapper.select(cpCode);
		String appCode = yyCp.getAppCode();
		
		if (yyCp.getStatus()!=ENABLED.getStatus()){
			logger.error("页游计费点变更计费点的状态不为已生效，变更被拒绝,appCode={}, chargePointCode={}", appCode, cpCode);
			return "计费点的状态为"+getStatusDesc(yyCp.getStatus())+",变更被拒绝,chargePointCode="+cpCode;
		}
		// 变更计费点将已生效的记录设置为失效，添加一条新记录，计费点编码不变
		yeYouCpMapper.updateStatus(yyCp.getChargePointId(), DISABLE.getStatus());
		cp.setStatus(WAIT_AUDIT.getStatus()); // 变更后设置为待审核状态
		yeYouCpMapper.insert(cp);
		return null;
	}

}
