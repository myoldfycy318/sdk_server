package com.dome.sdkserver.metadata.dao.mapper.bq.pay;

import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayTypeMapper  extends IBaseMapperDao{
	
	/**
	 * 根据渠道获得该应用的支付方式
	 * @return
	 */	
	String getPayTypeByQdCode(@Param("qdCode") String qdCode);
	

}
