package com.dome.sdkserver.service.statistic;

import com.dome.sdkserver.metadata.entity.bq.paystatistics.PayStatistics;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * PaytSatisticsService
 *
 * @author Zhang ShanMin
 * @date 2016/7/21
 * @time 10:09
 */
public interface PaySatisticsService {

    Page<PayStatistics> queryPayStatistics(PayStatistics payStatistics, int pageNumber, int pageSize);

    List<PayStatistics> queryPayChart(PayStatistics payStatistics);

    List<PayStatistics> queryPayTotalWater(PayStatistics payStatistics);

    List<Map<String,String>> queryAppCodeByMerchantId(int merchantId,
    		int userId);

    byte[] exportTotalWatter(PayStatistics payStatistics) throws Exception;

    byte[] exportAppWatter(PayStatistics payStatistics) throws Exception;

}
