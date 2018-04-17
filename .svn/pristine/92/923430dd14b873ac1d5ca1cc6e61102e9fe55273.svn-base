/**
 * 
 */
package com.dome.sdkserver.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dome.sdkserver.bo.ChargePointInfo;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.SearchChargePointBo;
import com.dome.sdkserver.constants.ChargePointStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.ChargePointMapper;
import com.dome.sdkserver.service.ChargePointService;

/**
 * @author liuxingyue
 *
 */
@Service
public class ChargePointServiceImpl implements ChargePointService {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ChargePointMapper chargePointMapper;
	
	@Override
	public List<ChargePointInfo> getChargePointInfos(SearchChargePointBo searchChargePointBo) {
		List<ChargePointInfo>  ChargePointInfos = chargePointMapper.getChargePontInfosByCondition(searchChargePointBo);
		return ChargePointInfos;
	}

	@Override
	public int getChargePontCountByCondition(SearchChargePointBo searchChargePointBo) {
		int count = chargePointMapper.getChargePontCountByCondition(searchChargePointBo);
		return count;
	}

	@Override
	@Deprecated
	public ChargePointInfo getChargePointInfoById(String chargePointId) {
		ChargePointInfo chargePointInfo = chargePointMapper.getChargePointInfoById(chargePointId);
		return chargePointInfo;
	}

	/**
	 * 该方法记录了业务日志
	 */
	@Override
	public void updateChargePointInfo(MerchantAppInfo app, ChargePointInfo chargePointInfo) {
		chargePointMapper.updateChargePointInfo(chargePointInfo);
		
		if (chargePointInfo.getStatus() == ChargePointStatusEnum.WAIT_AUDIT.getStatus()){ // 提交
			app.setStatusDesc("计费点提交");
		} else {
			app.setStatusDesc("计费点保存");
		}
		app.setPastStatus(app.getStatus());
		
	}

	@Override
	public ChargePointInfo getChargePointInfoByCode(String chargePointCode) {
		
		return chargePointMapper.getChargePointInfoByCode(chargePointCode);
	}
	
	@Override
	@Deprecated
	public List<ChargePointInfo> getChargePontInfosByCode(String chargePointCode) {
//		List<ChargePointInfo> infos = chargePointMapper.getChargePointInfoByCode(chargePointCode);
		
		return null;
	}

	@Override
	public void doPost(ChargePointInfo chargePointInfo) {
		
		chargePointInfo.setStatus(ChargePointStatusEnum.WAIT_AUDIT.getStatus());
		chargePointMapper.updateChargePointInfo(chargePointInfo);
	}
	
	@Override
	public boolean doBatchPost(Integer status, String[] chargePointCodes, MerchantAppInfo app) {
		try {
			//chargePointMapper.doBatchPass(ChargePointStatusEnum.WAIT_CHECKIN.getStatus(), chargePointCodes, appCode);
			for (String chargePointCode : chargePointCodes){
				ChargePointInfo chargePointInfo = this.getChargePointInfoByCode(chargePointCode);
				if (chargePointInfo == null) continue;
				// 减少不必要的字段更新
				ChargePointInfo newCharge = new ChargePointInfo();
				newCharge.setChargePointId(chargePointInfo.getChargePointId());
				newCharge.setStatus(ChargePointStatusEnum.WAIT_AUDIT.getStatus());
				this.updateChargePointInfo(app, newCharge);
			}
			return true;
		} catch (Exception e) {
			log.error("批量提交计费点操作异常",e);
			return false;
		}
		
	}

	
	@Override
	public void addChargePointInfo(ChargePointInfo chargePointInfo) {
		//添加计费点信息
		chargePointMapper.addChargePointInfo(chargePointInfo);
		//根据主键id生成计费点编码
		String chargePointCode =  "C" + String.format("%07d", chargePointInfo.getChargePointId());
		chargePointInfo.setChargePointCode(chargePointCode); // 记录业务日志时用的，写到remark备注列
		chargePointMapper.updateChargePointCode(chargePointInfo.getChargePointId(), chargePointCode);
	}
	
	@Transactional
	public void changeChargePoint(MerchantAppInfo app, ChargePointInfo chargePointInfo){
		
		// 将已生效的设置为失效
		ChargePointInfo ch = new ChargePointInfo();
		ch.setChargePointId(chargePointInfo.getChargePointId());
		ch.setChargePointCode(chargePointInfo.getChargePointCode());
		ch.setStatus(ChargePointStatusEnum.DISABLE.getStatus()); // 70 已失效
		chargePointMapper.updateChargePointInfo(ch);
		// 新添加计费点，状态为待审核
		chargePointInfo.setStatus(ChargePointStatusEnum.WAIT_AUDIT.getStatus()); // 10待审核
		chargePointMapper.addChargePointInfo(chargePointInfo);
		
		app.setPastStatus(app.getStatus());
		chargePointInfo.setStatus(ChargePointStatusEnum.ENABLED.getStatus());
	}

	@Override
	public ChargePointInfo getRencentPreviousChargePoint(String appCode,
			String chargePointCode) {
		return chargePointMapper.getRencentPreviousChargePoint(appCode, chargePointCode);
	}

	@Override
	public boolean isAllAvailable(String appCode) {
		List<ChargePointInfo> chargeList = chargePointMapper.getWaitAuditCharges(appCode);
		// 不存在计费点状态为待审核和驳回
		if (CollectionUtils.isEmpty(chargeList)){
			return true;
		}
		return false;
	}

	@Override
	public void delChargePoint(ChargePointInfo charge) {
		chargePointMapper.updateChargePointInfo(charge);
		
	}

	@Override
	public ChargePointInfo selectCharge(String appCode, String chargeName) {
		return chargePointMapper.selectCharge(appCode, chargeName);
	}

	@Override
	public List<ChargePointInfo> selectPassChargePoint(String appCode) {
		return chargePointMapper.selectPassChargePoint(appCode);
	}
}
