package com.dome.sdkserver.service.impl.statistic;

import com.dome.sdkserver.bq.util.PriceUtil;
import com.dome.sdkserver.metadata.dao.mapper.paystatistics.PaytSatisticsMapper;
import com.dome.sdkserver.metadata.entity.bq.paystatistics.PayStatistics;
import com.dome.sdkserver.service.statistic.PaySatisticsService;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.util.ExcelHandler;
import com.dome.sdkserver.util.PropertiesUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * PaytSatisticsServiceImpl
 *
 * @author Zhang ShanMin
 * @date 2016/7/21
 * @time 10:10
 */
@Service("paySatisticsService")
public class PaySatisticsServiceImpl extends ExcelHandler implements PaySatisticsService {

    @Resource(name = "paytSatisticsMapper")
    private PaytSatisticsMapper paytSatisticsMapper;

    @Autowired
    PropertiesUtil domainConfig;

    /**
     * 获取日期区间应用列表
     *
     * @param payStatistics
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Page<PayStatistics> queryPayStatistics(PayStatistics payStatistics, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        Page<PayStatistics> payStatisticsPage = (Page<PayStatistics>) paytSatisticsMapper.queryPayStatistics(payStatistics);
        calculate(payStatisticsPage);
        return payStatisticsPage;
    }

    /**
     * 获取应用日期区间列表
     *
     * @param payStatistics
     * @return
     */
    @Override
    public List<PayStatistics> queryPayChart(PayStatistics payStatistics) {
        List<PayStatistics> list = paytSatisticsMapper.queryPayStatistics(payStatistics);
        calculate(list);
        return list;
    }

    /**
     * 获取商户全部应用、指定应用总流水
     *
     * @param payStatistics
     * @return
     */
    @Override
    public List<PayStatistics> queryPayTotalWater(PayStatistics payStatistics) {
        List<PayStatistics> list = paytSatisticsMapper.queryPayTotalWater(payStatistics);
        calculate(list);
        return list;
    }

    /**
     * 获取商户应用列表
     * @param merchantCode
     * @return
     */
    @Override
    public List<Map<String, String>> queryAppCodeByMerchantId(int merchantId,
    		int userId) {
        return paytSatisticsMapper.queryAppCodeByMerchantId(merchantId, userId);
    }

    /**
     * 导出商户全部应用、指定应用总流水
     *
     * @param payStatistics
     * @return
     */
    @Override
    public byte[] exportTotalWatter(PayStatistics payStatistics) throws Exception {
        //获取应用日期区间的总流水
        try {
            List<PayStatistics> list = queryPayTotalWater(payStatistics);
            if (list == null || list.size() < 0 )
                return new byte[0];
            for (PayStatistics p : list) {
                p.setStartTime(payStatistics.getStartTime());
                p.setEndTime(payStatistics.getEndTime());
            }
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("serarchDate", "日期");
            map.put("publishTime", "上线日期");
            map.put("appName", "应用名称");
            map.put("payUserCount", "付费用户");
            map.put("totalWater", "流水总计");
            map.put("bwPayAmount", "宝玩流水(元)");
            map.put("bwBqAmount", "宝劵流水");
            map.put("disBwBqAcount", "宝劵折现(元)");
            map.put("aliPayAmount", "支付宝流水(元)");
            List<String> pendFeilds = new ArrayList<String>();
            pendFeilds.add("serarchDate");
            pendFeilds.add("publishTime");
            return exportByte("总流水", list, map, pendFeilds);
        } catch (Exception e) {
           throw e;
        }
    }

    /**
     * 导出应用流水Excel
     *
     * @param payStatistics
     * @return
     */
    @Override
    public byte[] exportAppWatter(PayStatistics payStatistics) throws Exception {
        try {
            List<PayStatistics> list = paytSatisticsMapper.queryPayStatistics(payStatistics);
            calculate(list);
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("transDate", "日期");
            map.put("appName", "应用名称");
            map.put("payUserCount", "付费用户");
            map.put("totalWater", "流水总计");
            map.put("bwPayAmount", "宝玩流水(元)");
            map.put("bwBqAmount", "宝劵流水");
            map.put("disBwBqAcount", "宝劵折现(元)");
            map.put("aliPayAmount", "支付宝流水(元)");
            List<String> pendFeilds = new ArrayList<String>();
            pendFeilds.add("transDate");
            return exportByte(list.get(0).getAppName(), list, map, pendFeilds);
        } catch (Exception e) {
           throw e;
        }
    }

    /**
     * 计算宝劵折现、流水总计
     *
     * @param payStatisticsPage
     */
    public void calculate(Page<PayStatistics> payStatisticsPage) {
        List<PayStatistics> list = payStatisticsPage.getResult();
        if (list == null || list.size() <= 0)
            return;
        calculate(list);
    }


    /**
     * 计算宝劵折现、流水总计
     *
     * @param list
     */
    public void calculate(List<PayStatistics> list) {
        if (list == null || list.size() <= 0)
            return;
        //宝玩宝劵折扣比
        String bwBqDisRatio = domainConfig.getString("bwBqDisRatio");
        BigDecimal bqDisRatio = new BigDecimal(bwBqDisRatio);

        BigDecimal disBwBqAcount = null;//宝券流水（折现）
        BigDecimal totalWater = null;//流水总计
        for (PayStatistics payStatistics : list) {
            disBwBqAcount = PriceUtil.div(payStatistics.getBwBqAmount(), bqDisRatio, 2);
            //宝券流水（折现）
            payStatistics.setDisBwBqAcount(disBwBqAcount);
            // 流水总计
            payStatistics.setTotalWater(payStatistics.getBwPayAmount().add(payStatistics.getAliPayAmount()).add(disBwBqAcount));
        }
    }

    /**
     * 处理excel中特定字段的映射
     *
     * @param fieldName
     * @param obj
     * @return
     */
    @Override
    protected String handleSpecialField(String fieldName, Object obj) {
        PayStatistics payStatistics = (PayStatistics) obj;
        //处理excel中的日期属性
        if ("serarchDate".equals(fieldName)) {
            return DateUtil.dateToDateString(payStatistics.getStartTime(), "yyyy.MM.dd") + "--" + DateUtil.dateToDateString(payStatistics.getEndTime(), "yyyy.MM.dd");
        } else if ("publishTime".equals(fieldName)) {
            return DateUtil.dateToDateString(payStatistics.getPublishTime(), "yyyy.MM.dd");
        } else if ("transDate".equals(fieldName)) {
            return DateUtil.dateToDateString(payStatistics.getTransDate(), "yyyy.MM.dd");
        }
        return "";
    }
}
