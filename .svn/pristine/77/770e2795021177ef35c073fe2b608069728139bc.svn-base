package com.dome.sdkserver.metadata.dao.mapper.qbao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import com.dome.sdkserver.metadata.entity.qbao.PayTransEntity;

@Repository
public interface PayTransMapper extends IBaseMapperDao<PayTransEntity, Long> {
	
	/**
	 * 新增支付请求对象
	 * @param
	 */
	boolean addPayTransRequest(@Param("entity")PayTransEntity payTransEntity,@Param("suffix") String suffix);
	
	/**
	 * 根据商户编码和商户交易流水号得到支付交易对象
	 * @param merchantCode 商户编码
	 * @param merchantTransCode 商户交易流水号
	 * @return
	 */
	PayTransEntity getPayTransReqByMertCodeTransCode(@Param("appCode")String appCode
			,@Param("bizCode")String bizCode,@Param("suffix") String suffix);
	
	
	/**
	 * 根据支付ID得到支付交易对象
	 * @param payTransId 支付ID
	 * @return
	 */
	PayTransEntity getPayTransReqById(@Param("payTransId")Long payTransId,@Param("suffix") String suffix);
	
	/**
	 * 更新支付结果
	 * @param payTransEntity
	 */
	boolean updatePayTransByPayTransId(@Param("entity")PayTransEntity payTransEntity,@Param("suffix") String suffix);

    /**
     * 记录第三方钱宝支付流水
     * @param
     */
    boolean saveTPPayTransRequest(@Param("entity")PayTransEntity payTransEntity,@Param("suffix") String suffix);

    /**
     * 更新第三方钱宝支付流水
     * @param payTransEntity
     * @param suffix
     * @return
     */
    boolean updateTPPayTrans(@Param("entity")PayTransEntity payTransEntity,@Param("suffix") String suffix);

}
