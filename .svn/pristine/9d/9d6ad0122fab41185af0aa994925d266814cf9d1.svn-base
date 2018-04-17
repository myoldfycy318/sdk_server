package com.dome.sdkserver.service;

import java.util.List;

import com.dome.sdkserver.bo.ChargePointInfo;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.SearchChargePointBo;
import com.dome.sdkserver.bo.SearchMerchantAppBo;

/**
 * 计费点服务
 * @author liuxingyue
 *
 */
public interface ChargePointService {
	/**
	 * 根据查询条件查找
	 * @param searchChargePointBo
	 * @return
	 */
	List<ChargePointInfo> getChargePointInfos(SearchChargePointBo searchChargePointBo);
	
	/**
	 * 根据查询条件得到总数
	 * @param searchChargePointBo
	 * @return
	 */
	Integer getChargePontCountByCondition(SearchChargePointBo searchChargePointBo);
	
	/**
	 * 根据计费点id查找手游计费点信息
	 * @param chargePointId
	 * @return
	 */
	ChargePointInfo getChargePointInfoById(String chargePointId);
	
	/**
	 * 根据计费点编码更新计费点信息
	 * @param chargePoint
	 */
	void rejectCp(MerchantAppInfo app, ChargePointInfo chargePoint);
	/**
	 * 通过计费点信息
	 * @param chargePointInfo
	 */
	boolean passCp(ChargePointInfo chargePoint, MerchantAppInfo app);
	
	/**
	 * 获得某计费点的上一条生效记录
	 * @param chargePointCode
	 * @return
	 */
	ChargePointInfo getChargePontInfoByCode(String appCode, String chargePointCode);
	
	/**
	 * 根据查询条件得到应用列表
	 * @param conditionMap
	 * @return
	 */
	int getAppInfoCountByCondition(SearchMerchantAppBo searchMerchantAppBo);
	
	/**
	 * 根据查询条件得到应用列表
	 * @param conditionMap
	 * @return
	 */
	List<MerchantAppInfo> getAppInfoByCondition(SearchMerchantAppBo searchMerchantAppBo);
}
