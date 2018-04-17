package com.dome.sdkserver.metadata.dao.mapper.bq.pay;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import com.dome.sdkserver.metadata.entity.bq.pay.GlobalVarEntity;


@Repository
public interface GlobalVarMapper  extends IBaseMapperDao<GlobalVarEntity, Long>{
	
	/**
	 * 获得发送短信验证码的交易金额临界点
	 * @return
	 */	
	String getGlobalVarByType(@Param("varType") String varType);
	

}
