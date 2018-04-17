package com.dome.sdkserver.controller.stastic;

import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.metadata.entity.bq.paystatistics.PayStatistics;
import com.dome.sdkserver.service.statistic.PaySatisticsService;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.view.AjaxResult;
import com.github.pagehelper.Page;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.*;

/**
 * PaytSatisticsController
 *
 * @author Zhang ShanMin
 * @date 2016/7/21
 * @time 10:14
 */

@Controller
@RequestMapping("/payStatistic")
public class PaytSatisticsController extends BaseController {

    @Resource(name = "paySatisticsService")
    private PaySatisticsService paySatisticsService;

    @ResponseBody
    @RequestMapping(value = "/list")
    public AjaxResult queryPayStatisticsList(HttpServletRequest request) {
        Map<String, Object> map = null;
        try {
            String pageNo = request.getParameter("pageNo");
            //默认查找第一页
            int pageNumber = (StringUtils.isEmpty(pageNo) || !pageNo.matches("\\d+")) ? 1 : Integer.valueOf(pageNo);
            String pSize = request.getParameter("pageSize");
            int pageSize = (StringUtils.isEmpty(pSize) || !pSize.matches("\\d+")) ? 10 : Integer.valueOf(pSize);
            PayStatistics payStatistics = new PayStatistics();
            payStatistics.setAppCode(request.getParameter("appCode"));
            //封装查询时间
            AjaxResult result = encapsulateTime(request, payStatistics);
            if (!AjaxResult.isSucees(result))
                return result;
            result = validateAppCode(request);//验证appCode是否属于当前商户
            if (!AjaxResult.isSucees(result))
                return result;
            Page<PayStatistics> payStatisticsPage = paySatisticsService.queryPayStatistics(payStatistics, pageNumber, pageSize);
            map = new HashMap<String, Object>();
            map.put("totalCount", payStatisticsPage.getTotal());
            map.put("list", payStatisticsPage);
            return AjaxResult.success(map);
        } catch (Exception e) {
            log.error("payStatistic.list.error", e);
            return AjaxResult.failed("系统异常，请重试");
        }
    }

    @ResponseBody
    @RequestMapping("/allWaterList")
    public AjaxResult queryPayStasByMerchantId(HttpServletRequest request) {
        try {
            PayStatistics payStatistics = new PayStatistics();
            //封装查询时间
            AjaxResult result = encapsulateTime(request, payStatistics);
            if (!AjaxResult.isSucees(result))
                return result;
            String appCode = request.getParameter("appCode");
            if (StringUtils.isBlank(appCode)) {
                //当前商户信息
                MerchantInfo merchantInfo = getCurrentMerchant(request);
                if (merchantInfo == null)
                    return AjaxResult.success();
                payStatistics.setMerchantInfoId(merchantInfo.getMerchantInfoId());
            } else {
                //验证appCode是否属于当前商户
                result = validateAppCode(request);
                if (!AjaxResult.isSucees(result))
                    return result;
                payStatistics.setAppCode(appCode);
            }
            List<PayStatistics> list = paySatisticsService.queryPayTotalWater(payStatistics);
            for (PayStatistics p : list) {
                p.setStartTime(payStatistics.getStartTime());
                p.setEndTime(payStatistics.getEndTime());
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("list", list);
            return AjaxResult.success(map);
        } catch (Exception e) {
            log.error("payStatistic.allWaterList.error", e);
            return AjaxResult.failed("系统异常，请重试");
        }
    }

    /**
     * 封装查询时间
     *
     * @param request
     * @param payStatistics
     */
    public AjaxResult encapsulateTime(HttpServletRequest request, PayStatistics payStatistics) throws ParseException {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        if (StringUtils.isNotEmpty(startTime)) {
            payStatistics.setStartTime(DateUtil.getDate(startTime, "yyyy-MM-dd"));
        } else {
            payStatistics.setStartTime(DateUtil.getYesterdayDate());//默认前一天
        }
        if (StringUtils.isNotEmpty(endTime)) {
            payStatistics.setEndTime(DateUtil.getDate(endTime, "yyyy-MM-dd"));
        } else {
            payStatistics.setEndTime(DateUtil.getYesterdayDate());//默认前一天
        }
        if (DateUtil.getFormatDate(payStatistics.getEndTime(), "yyyy-MM-dd").getTime() >= (DateUtil.getFormatDate(new Date(), "yyyy-MM-dd")).getTime())
            return AjaxResult.failed("结束时间不得选择今日及大于今日时间");
        if (payStatistics.getStartTime().after(payStatistics.getEndTime()))
            return AjaxResult.failed("查询开始时间不能小于结束时间");
        if (DateUtil.getBetweenDays(payStatistics.getStartTime(), payStatistics.getEndTime()) > 30)
            return AjaxResult.failed("查询时间跨度不得大于1个月");
        return AjaxResult.success();
    }

    @ResponseBody
    @RequestMapping("/queryPayChart")
    public AjaxResult queryPayChart(HttpServletRequest request) {
        try {
            PayStatistics payStatistics = new PayStatistics();
            //封装查询时间
            AjaxResult result = encapsulateTime(request, payStatistics);
            if (!AjaxResult.isSucees(result))
                return result;
            String _appCode = request.getParameter("appCode");
            if (StringUtils.isBlank(_appCode)) {
                //当前商户信息
                MerchantInfo merchantInfo = getCurrentMerchant(request);
                if (merchantInfo == null)
                    return AjaxResult.success();
                payStatistics.setMerchantInfoId(merchantInfo.getMerchantInfoId());
            } else {
                //验证appCode是否属于当前商户
                if (!AjaxResult.isSucees(validateAppCode(request))) {
                    return AjaxResult.success();
                }
                payStatistics.setAppCode(_appCode);
            }
            List<PayStatistics> allPayStatistics = paySatisticsService.queryPayChart(payStatistics);
            //获取查询时间区间日期列表
            List<Date> dateList = DateUtil.findDates(payStatistics.getStartTime(), payStatistics.getEndTime());
            List<String> labelTime = new ArrayList<>(dateList.size());
            Map<String, Object> outMap = new HashMap<>();
            List<PayStatistics> payStatisticsList = null;
            List<Map<String, Object>> dataList = new ArrayList<>();
            Map<String, Object> dataMap = null;
            List<PayStatistics> tempStatisticses = null;
            for (Date date : dateList) {
                labelTime.add(DateUtils.toDateText(date, "yyyy-MM-dd"));
            }
            outMap.put("labels", labelTime);
            Map<String, List<PayStatistics>> appCodeMap = new HashMap<String, List<PayStatistics>>();
            String appCode = null;
            for (PayStatistics statistics : allPayStatistics) {
                appCode = statistics.getAppCode();
                payStatisticsList = appCodeMap.get(appCode);
                if (payStatisticsList == null) {
                    payStatisticsList = new ArrayList<PayStatistics>();
                    appCodeMap.put(appCode, payStatisticsList);
                }
                payStatisticsList.add(statistics);
            }
            String[] waterTotal = null;
            for (Map.Entry<String, List<PayStatistics>> entry : appCodeMap.entrySet()) {
                dataMap = new HashMap<String, Object>();
                tempStatisticses = entry.getValue();
                waterTotal = new String[dateList.size()];
                dataMap.put("label", tempStatisticses.get(0).getAppName());
                for (int i = 0; i < dateList.size(); i++) {
                    for (PayStatistics statistics : tempStatisticses) {
                        if (DateUtil.dateToDateString(dateList.get(i), "yyyyMMdd").equals(DateUtil.dateToDateString(statistics.getTransDate(), "yyyyMMdd"))) {
                            waterTotal[i] = Double.toString(statistics.getTotalWater().doubleValue());
                        }
                    }
                    if (StringUtils.isBlank(waterTotal[i])) {
                        waterTotal[i] = "0";
                    }
                }
                dataMap.put("data", waterTotal);
                dataList.add(dataMap);
            }
            outMap.put("datasets", dataList);
            return AjaxResult.success(outMap);
        } catch (Exception e) {
            log.error("payStatistic.queryPayChart.error:", e);
            return AjaxResult.failed("系统异常，请重试");
        }
    }

    /**
     * 获取商户应用列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryAppCode")
    public AjaxResult queryPayStatistics(HttpServletRequest request) {
        //当前商户信息
        MerchantInfo merchantInfo = getCurrentMerchant(request);
        Map<String, Object> map = new HashMap<String, Object>();
        if (merchantInfo == null) {
            map.put("list", "");
            return AjaxResult.success(map);
        }
        List<Map<String, String>> appCodes = paySatisticsService.queryAppCodeByMerchantId(merchantInfo.getMerchantInfoId(),
        		merchantInfo.getUserId());
        map.put("list", appCodes);
        return AjaxResult.success(map);
    }


    @RequestMapping("/exportTotalWatter")
    public void exportTotalWatter(HttpServletRequest request, HttpServletResponse response) {
        OutputStream os = null;
        boolean flag = false;
        try {
            PayStatistics payStatistics = new PayStatistics();
            //封装查询时间
            encapsulateTime(request, payStatistics);
            String appCode = request.getParameter("appCode");
            if (StringUtils.isBlank(appCode)) {
                //当前商户信息
                MerchantInfo merchantInfo = getCurrentMerchant(request);
                if (merchantInfo == null) {
                    flag = true;
                } else {
                    payStatistics.setMerchantInfoId(merchantInfo.getMerchantInfoId());
                }
            } else {
                payStatistics.setAppCode(appCode);
                //验证appCode是否属于当前商户
                if (!AjaxResult.isSucees(validateAppCode(request))) {
                    flag = true;
                }
            }
            byte[] fileNameByte = ("支付总流水.xls").getBytes("GBK");
            String filename = new String(fileNameByte, "ISO8859-1");
            byte[] bytes = null;
            if (flag) {
                bytes = new byte[0];
            } else {
                bytes = paySatisticsService.exportTotalWatter(payStatistics);
            }
            response.setContentType("application/x-msdownload");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "attachment;filename="
                    + filename);
            os = response.getOutputStream();
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            log.error("exportTotalWatter.error", e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 导出应用支付流水Excel
     *
     * @param request
     * @param response
     */
    @RequestMapping("/exportAppWatter")
    public void exportAppWatter(HttpServletRequest request, HttpServletResponse response) {
        OutputStream os = null;
        byte[] bytes = null;
        boolean flag = false;
        try {
            PayStatistics payStatistics = new PayStatistics();
            payStatistics.setAppCode(request.getParameter("appCode"));
            //封装查询时间
            AjaxResult ajaxResult = encapsulateTime(request, payStatistics);
            if (!AjaxResult.isSucees(ajaxResult)) {
                flag = true;
            }
            //验证appCode是否属于当前商户
            ajaxResult = validateAppCode(request);
            if (!AjaxResult.isSucees(ajaxResult)) {
                flag = true;
            }
            byte[] fileNameByte = (request.getParameter("appCode") + ".xls").getBytes("GBK");
            String filename = new String(fileNameByte, "ISO8859-1");
            if (flag) {
                bytes = new byte[0];
            } else {
                bytes = paySatisticsService.exportAppWatter(payStatistics);
            }
            response.setContentType("application/x-msdownload");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "attachment;filename="
                    + filename);
            os = response.getOutputStream();
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            log.error("exportAppWatter error", e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 验证appCode是否属于当前商户
     *
     * @param request
     * @return
     */
    public AjaxResult validateAppCode(HttpServletRequest request) {
        String appCode = request.getParameter("appCode");
        //当前商户信息
        MerchantInfo merchantInfo = getCurrentMerchant(request);
        if (merchantInfo == null) {
            return AjaxResult.failed("找不到对应的商户信息");
        }
        List<Map<String, String>> appCodes = paySatisticsService.queryAppCodeByMerchantId(merchantInfo.getMerchantInfoId(),
        		merchantInfo.getUserId());
        if (appCodes == null || appCodes.size() <= 0)
            return AjaxResult.failed("找不到对应的应用信息");
        for (Map<String, String> appCodeMap : appCodes) {
            if (appCodeMap.get("appCode").equals(appCode)) {
                return AjaxResult.success();
            }
        }
        return AjaxResult.failed("找不到对应的应用信息");
    }

    /**
     * 导出前判断是否
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/isCanExport")
    public AjaxResult isCanExport(HttpServletRequest request) {
        try {
            PayStatistics payStatistics = new PayStatistics();
            //封装查询时间
            AjaxResult result = encapsulateTime(request, payStatistics);
            if (!AjaxResult.isSucees(result))
                return result;
        } catch (Exception e) {
            log.error("payStatistic.isCanExport.error", e);
            return AjaxResult.failed("系统异常，请重试");
        }
        return AjaxResult.success();
    }
}
