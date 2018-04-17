package com.dome.sdkserver.metadata.dao.mapper.paystatistics;

import com.dome.sdkserver.metadata.dao.IBaseMapper;
import com.dome.sdkserver.metadata.entity.bq.paystatistics.PayStatistics;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Paytatistics
 *
 * @author Zhang ShanMin
 * @date 2016/7/20
 * @time 17:43
 */
@Repository("paytSatisticsMapper")
public interface PaytSatisticsMapper extends IBaseMapper {

    List<PayStatistics> queryPayStatistics(PayStatistics payStatistics);

    List<PayStatistics> queryPayTotalWater(PayStatistics payStatistics);

    List<Map<String,String>> queryAppCodeByMerchantId(@Param("merchantId")int merchantId,
    		@Param("userId")int userId);
}
