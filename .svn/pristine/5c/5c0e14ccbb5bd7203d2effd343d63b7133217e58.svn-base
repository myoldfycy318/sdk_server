package com.dome.sdkserver.metadata.dao.mapper.bq.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import com.dome.sdkserver.metadata.entity.bq.pay.BqChargePointInfo;

@Repository("bqChargePointMapper")
public interface BqChargePointMapper extends IBaseMapperDao<BqChargePointInfo, Long>{
	
	/**
	 * 根据计费点code查询记录 status=20(已生效）
	 * @param chargePointCode
	 * @return
	 */
	BqChargePointInfo getChargePointInfoByCode(@Param("chargePointCode")String chargePointCode,@Param("appCode")String appCode);
	
	/**
	 * 根据计费点code查询记录 最新的一条status=70(已失效）
	 * @param chargePointCode
	 * @return
	 */
	BqChargePointInfo getChargePointInfoByCode2(@Param("chargePointCode")String chargePointCode,@Param("appCode")String appCode);
	
	/**
	 * 根据计费点code查询记录 status=20(已生效）
	 * @param chargePointCode
	 * @return
	 */
	List<BqChargePointInfo> selectByAppCode(@Param("appCode")String appCode);

}