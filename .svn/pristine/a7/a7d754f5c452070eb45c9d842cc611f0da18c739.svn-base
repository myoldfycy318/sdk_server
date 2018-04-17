package com.dome.sdkserver.metadata.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.bo.ChargePointInfo;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.SearchChargePointBo;
import com.dome.sdkserver.bo.SearchMerchantAppBo;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;

public interface ChargePointMapper extends IBaseMapperDao<ChargePointInfo, Long>{
	
	/**
	 * 添加手游计费点信息
	 * @param chargePointInfo
	 * @param appCode
	 */
	void addChargePointInfo(@Param("entity")ChargePointInfo chargePointInfo);
	
	/**
	 * 根据查询条件查找(flag=1 后台)
	 * @param searchChargePointBo
	 * @return
	 */
	List<ChargePointInfo> getChargePontInfosByCondition(@Param("entity")SearchChargePointBo searchChargePointBo);
	
	/**
	 * 根据查询条件得到总数(flag=1 后台)
	 * @param searchChargePointBo
	 * @return
	 */
	Integer getChargePontCountByCondition(@Param("entity")SearchChargePointBo searchChargePointBo);
	
	/**
	 * 根据计费点id查找手游计费点信息
	 * @param chargePointId
	 * @return
	 */
	@Deprecated
	ChargePointInfo getChargePointInfoById(String chargePointId);
	
	/**
	 * 根据计费点id更新计费点信息
	 * @param chargePointInfo
	 */
	void updateChargePointInfo(@Param("entity")ChargePointInfo chargePointInfo);
	
	/**
	 * 批量通过计费点信息
	 * @param status
	 * @param chargePointIds
	 */
	void doBatchPass(@Param("status")Integer status,@Param("codes")String[] chargePointCodes, @Param("appCode")String appCode);
	
	/**
	 * 根據計費點code查詢记录
	 * @param chargePointCode
	 * @return
	 */
	ChargePointInfo getChargePointInfoByCode(@Param("chargePointCode")String chargePointCode);

	void updateChargePointCode(@Param("id")int chargePointId, @Param("code")String chargePointCode);
	
	/**
	 * 查看计费点过去最新的历史记录
	 * 变更会将计费点设置为失效，新增一条记录。失效的记录就会是返回结果
	 * 如果有多条失效记录，返回最新的一条记录
	 * @param appCode
	 * @param chargePointCode
	 * @return
	 */
	ChargePointInfo getRencentPreviousChargePoint(@Param("appCode")String appCode, @Param("chargePointCode")String chargePointCode);
	
	/**
	 * 获取待审核和驳回的计费点
	 * @param appCode
	 * @return
	 */
	List<ChargePointInfo> getWaitAuditCharges(@Param("appCode")String appCode);
	
	/**
	 * 根据查询条件得到应用列表
	 * @param searchMerchantAppBo
	 * @return
	 */
	List<MerchantAppInfo> getAppListByCondition(@Param("entity")SearchMerchantAppBo searchMerchantAppBo);
	
	/**
	 * 根据查询条件得到应用数量
	 * @param searchMerchantAppBo
	 * @return
	 */
	int getAppInfoCountByCondition(@Param("entity")SearchMerchantAppBo searchMerchantAppBo);
	
	/**
	 * 返回某个应用的计费点
	 * @param appCode
	 * @return
	 */
	int selectChargeCountByCpCode(@Param("chargePointCode")String chargePointCode);
	
	void deleteCharge(@Param("chargeCode")String chargeCode);
	
	ChargePointInfo selectCharge(@Param("appCode")String appCode, @Param("chargeName")String chargeName);
	
	int updateStatus(@Param("id")long id, @Param("status")int status);


	List<ChargePointInfo> selectPassChargePoint(String appCode);
}