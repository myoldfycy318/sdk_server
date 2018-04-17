package com.dome.sdkserver.service.impl.chargepoint;

import com.dome.sdkserver.bq.enumeration.PayTypeEnum;
import com.dome.sdkserver.metadata.dao.mapper.bq.pay.PayTypeMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.PayType;
import com.dome.sdkserver.service.chargePoint.PayTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("payTypeService")
public class PayTypeServiceImpl implements PayTypeService {
	
	private static Map<String, List<PayType>> cache = new HashMap<String, List<PayType>>();
	
	@Resource
	private PayTypeMapper payTypeMapper;
	
	@Value("${exclude.paytype.id}")
	private int excludePaytypeId;
	
	public List<PayType> getPayTypesByQdCode(String channelCode) {
		List<PayType> list = cache.get(channelCode);
		if(list == null){
			list = getPayTypesFromDb(channelCode);
			if(list != null && list.size() > 0){
				cache.put(channelCode, list);
			}
		}
		return list;
		
	}
	
	private List<PayType> getPayTypesFromDb(String channelCode){
		String payTypes = payTypeMapper.getPayTypeByQdCode(channelCode);
		if(StringUtils.isBlank(payTypes)){
			return null;
		}
		String _payTypes[] = payTypes.split(","); 
		List<PayType> list = new ArrayList<PayType>();
		for(String payType : _payTypes){
			int payTypeId = Integer.parseInt(payType);
			if(payTypeId == excludePaytypeId){
				continue;
			}
			if(payTypeId == PayTypeEnum.支付宝支付.getCode()){
				list.add(new PayType(PayTypeEnum.支付宝支付.getCode(), PayTypeEnum.支付宝支付.name()));
			}else if(payTypeId == PayTypeEnum.钱宝支付.getCode()){
				list.add(new PayType(PayTypeEnum.钱宝支付.getCode(), PayTypeEnum.钱宝支付.name()));
			}
		}
		return list;
	}
}
