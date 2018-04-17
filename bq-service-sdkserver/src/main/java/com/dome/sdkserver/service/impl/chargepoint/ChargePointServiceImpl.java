/**
 * 
 */
package com.dome.sdkserver.service.impl.chargepoint;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.dome.sdkserver.metadata.dao.mapper.bq.pay.NewOpenChargePointMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.NewOpenChargePoint;
import com.dome.sdkserver.service.pay.qbao.bo.ChargePointInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.sdkserver.metadata.dao.mapper.bq.pay.BqChargePointMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.BqChargePointInfo;
import com.dome.sdkserver.service.chargePoint.ChargePointService;
import com.dome.sdkserver.util.RedisUtil;

/**
 * @author liuxingyue
 *
 */
@Service
public class ChargePointServiceImpl implements ChargePointService {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private BqChargePointMapper bqChargePointMapper;

	@Resource
	private NewOpenChargePointMapper newOpenChargePointMapper;
	
	@Autowired
	private RedisUtil redisUtil;
	@Override
	public BqChargePointInfo getChargePointInfoByCode(String chargePointCode,String appCode) {
		BqChargePointInfo chargePointInfo = bqChargePointMapper.getChargePointInfoByCode(chargePointCode,appCode);
		if(chargePointInfo == null){//老平台没有查询到查询新开放平台
			NewOpenChargePoint newOpenChargePoint = newOpenChargePointMapper.selectByParam(new NewOpenChargePoint(appCode,chargePointCode));
			if (newOpenChargePoint != null) {
				BqChargePointInfo bqChargePointInfo = new BqChargePointInfo();
				BeanUtils.copyProperties(newOpenChargePoint, bqChargePointInfo);
				bqChargePointInfo.setChargePointId(newOpenChargePoint.getId());
				//bigDecimal转换为double类型
				double chargePointAmount = newOpenChargePoint.getChargePointAmount().doubleValue();
				bqChargePointInfo.setChargePointAmount(chargePointAmount);
				chargePointInfo = bqChargePointInfo;
			}
		}
//		if(chargePointInfo == null){
//			chargePointInfo = bqChargePointMapper.getChargePointInfoByCode2(chargePointCode, appCode);
//		}
		return chargePointInfo;
	}
	
	@Override
	public List<BqChargePointInfo> selectByAppCode(String appCode) {
		return bqChargePointMapper.selectByAppCode(appCode);
	}
}
