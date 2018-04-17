package com.dome.sdkserver.service.chargePoint;

import java.util.List;

import com.dome.sdkserver.metadata.entity.bq.pay.BqChargePointInfo;

/**
 * 计费点服务
 * @author liuxingyue
 *
 */
public interface ChargePointService {
	
	/**
	 * 根据计费点code查找手游计费点信息
	 * @param chargePointId
	 * @return
	 */
	BqChargePointInfo getChargePointInfoByCode(String chargePointCode,String appCode);
	
	/**
	 * 根据appCode查询计费点信息列表
	 * @param appCode
	 * @return
	 */
	List<BqChargePointInfo> selectByAppCode(String appCode);
	

}
