package com.dome.sdkserver.controller.channel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.metadata.entity.bq.channel.BalanceAmountVo;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;
import com.dome.sdkserver.service.ChannelDataService;
import com.dome.sdkserver.service.channel.ChannelService;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.view.AjaxResult;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * **********************************************************
 *  内容摘要	：<p>
 *
 *  作者	：94841
 *  创建时间	：2016年8月10日 下午2:55:12 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2016年8月10日 下午2:55:12 	修改人：
 *  	描述	:
 ***********************************************************
 */
@Controller
@RequestMapping("/channel")
public class ChannelDataController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(ChannelDataController.class);
    
    @Autowired
    ChannelDataService channelDataService;
    
    @Autowired
    ChannelService channelService;
    
    @ResponseBody
    @RequestMapping("/balance/query")
    public AjaxResult balanceQuery(Paginator paginator, String channelName,
        Integer cooperationType, Date beginDate, Date endDate, HttpServletRequest request)
    {
        try
        {
            long lChannelId = getCurrChannelId(request);
            List<Date> dateList = handleTime(beginDate, endDate);
            
            List<BalanceAmountVo> list = null;
            // 第一次访问时触发同步数据
            channelDataService.synTjData();
            int totalCount = channelDataService.selectCount((int)lChannelId, channelName, dateList.get(0), dateList.get(1), cooperationType);
            if (totalCount>0){
            	paginator=Paginator.handlePage(paginator, totalCount, request);
            	list = channelDataService.select(paginator, (int)lChannelId, channelName, dateList.get(0), dateList.get(1), cooperationType);
            } else {
            	list=new ArrayList<>();
            }
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("totalCount", totalCount);
            result.put("list", list);
            return AjaxResult.success(result);
        } catch (Exception e)
        {
        	log.error("渠道数据查询列表数据出错", e);
            return AjaxResult.failedSystemError();
        }
        
    }
    
    
    @ResponseBody
    @RequestMapping("/balance/queryDetail")
    public AjaxResult balanceQueryDetail(Paginator paginator, Integer channelId,
        Date beginDate, Date endDate, HttpServletRequest request)
    {
        
        if(channelId == null)
        {
        	return AjaxResult.failed("渠道ID不能为空");
        }
        int parentChannelId = (int)getCurrChannelId(request);
        FirstChannel channel = channelService.select(channelId, 0);
        int cooperationType = channel.getCooperType();
        if(parentChannelId != channelId && (channel.getParentId() != 0 && channel.getParentId() != parentChannelId))
        {
            return AjaxResult.failed("权限不足");
        }
        List<Date> dateList = handleTime(beginDate, endDate);
        
        List<BalanceAmountVo> list = null;
        int totalCount = channelDataService.selectDetailCount(channelId, dateList.get(0), dateList.get(1));
        if (totalCount>0){
        	paginator = Paginator.handlePage(paginator, totalCount, request);
        	list = channelDataService.selectDetail(paginator, channelId, cooperationType, dateList.get(0), dateList.get(1));
        } else {
        	list=new ArrayList<>();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("totalCount", totalCount);
        result.put("list", list);
        return AjaxResult.success(result);
    }
    
    @ResponseBody
    @RequestMapping("/balance/export")
    public AjaxResult export(String channelName, int cooperationType, Date beginDate, Date endDate,
        HttpServletRequest request, HttpServletResponse response){
        WritableWorkbook wwk = null;
        try 
        {
            long lChannelId = getCurrChannelId(request);
            List<Date> dateList = handleTime(beginDate, endDate);
            Paginator paginator= new Paginator();
            paginator.setStart(-1);
            List<BalanceAmountVo> list = channelDataService.select(paginator, (int)lChannelId, channelName, dateList.get(0), dateList.get(1), cooperationType);
            if (CollectionUtils.isEmpty(list)) {
                return AjaxResult.failed("没有查询到数据");
            }
            String fileName = "channel_ " + DateFormatUtils.format(new Date(), "yyyyMMdd") + ".xls";
            response.setContentType("application/vnd.ms-excel;charset=gbk");
            response.setHeader("Content-disposition", "attachment; filename="+ fileName);
            wwk = Workbook.createWorkbook(response.getOutputStream());
            WritableSheet sheet = wwk.createSheet("Sheet1", 0);
            writeExcelTitle(titleArray, sheet, titleMap);
            writeContent(titleArray, list, sheet, titleMap);
            wwk.write();
            
        } catch (Exception e) {
            log.error("导出Excel失败", e);
            return AjaxResult.failed("导出Excel失败");
        } finally {
            if (wwk != null){
                try {
                    wwk.close();
                } catch (Exception e) {
                    log.error("导出Excel关闭输入流失败", e);
                }
            }
        }
        
        return AjaxResult.success();
    }
    
    @ResponseBody
    @RequestMapping("/balance/exportDetail")
    public AjaxResult exportDetail(long channelId, Date beginDate, Date endDate,
        HttpServletRequest request, HttpServletResponse response){
        WritableWorkbook wwk = null;
        try 
        {
            if(channelId == 0l)
            {
                return AjaxResult.failed("渠道ID不能为空");
            }
            int parentChannelId = (int)getCurrChannelId(request);
            FirstChannel channel = channelService.select(channelId, 0);
            int cooperationType = channel.getCooperType();
            if(parentChannelId != channelId && (channel.getParentId() != 0 && channel.getParentId() != parentChannelId))
            {
                return AjaxResult.failed("权限不足");
            }
            List<Date> dateList = handleTime(beginDate, endDate);
            Paginator paginator = new Paginator();
            paginator.setStart(-1);
            List<BalanceAmountVo> list = channelDataService.selectDetail(paginator, (int)channelId, cooperationType, dateList.get(0), dateList.get(1));
            if (CollectionUtils.isEmpty(list)) {
                return AjaxResult.failed("没有查询到数据");
            }
            String fileName = "channelDetail_ " + DateFormatUtils.format(new Date(), "yyyyMMdd") + ".xls";
            response.setContentType("application/vnd.ms-excel;charset=gbk");
            response.setHeader("Content-disposition", "attachment; filename="+ fileName);
            wwk = Workbook.createWorkbook(response.getOutputStream());
            WritableSheet sheet = wwk.createSheet("Sheet1", 0);
            switch (cooperationType)
            {
                case 1:
                    writeExcelTitle(cpaTitleArray, sheet, cpaTitleMap);
                    writeContent(cpaTitleArray, list, sheet, cpaTitleMap);
                    break;
                case 2:
                    writeExcelTitle(cpsTitleArray, sheet, cpsTitleMap);
                    writeContent(cpsTitleArray, list, sheet, cpsTitleMap);
                    break;
                default:
                    break;
            }
            wwk.write();
            
        } catch (Exception e) {
            log.error("导出Excel失败", e);
            return AjaxResult.failed("导出Excel失败");
        } finally {
            if (wwk != null){
                try {
                    wwk.close();
                } catch (Exception e) {
                    log.error("导出Excel关闭输入流失败", e);
                }
            }
        }
        
        return AjaxResult.success();
    }
    
    private static boolean isNowAfter1230()
    {
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.HOUR_OF_DAY)>12 || (c.get(Calendar.HOUR_OF_DAY)==12 && c.get(Calendar.MINUTE)>=30)){
        	return true;
        }
        
        return false;
    }
    
    @InitBinder    
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception 
    {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
    }  
    
    private static List<Date> handleTime(Date beginDate, Date endDate)
    {
        List<Date> list = new ArrayList<Date>();
      //判断当前时间是否过了中午12点半
        boolean timeFlag = isNowAfter1230();
        //页面加载
        if(endDate == null && beginDate == null)
        {
            if(timeFlag)
            {
                endDate = DateUtil.getYesterdayDate();
                beginDate = endDate;
            }
            else
            {
                endDate = DateUtil.getDayBeforeYesterday();
                beginDate = endDate;
            }
        }
        else if(beginDate != null&& endDate==null)
        {
            if(timeFlag)
            {
                endDate = DateUtil.getYesterdayDate();
            }
            else
            {
                endDate = DateUtil.getDayBeforeYesterday();
            }
        }
        else if(endDate != null&& beginDate==null)
        {
            beginDate = startCalendar.getTime();
        }
        list.add(0, beginDate);
        list.add(1, endDate);
        return list;
    }
    
    private static Calendar startCalendar=Calendar.getInstance();
    static {
    	startCalendar.set(Calendar.YEAR, 2013);
    	startCalendar.set(Calendar.MONTH, 0);
    	startCalendar.set(Calendar.DATE, 1);
    }
    private static final Map<String, String> titleMap = new HashMap<String, String>(); 
    static {
        titleMap.put("channelName", utf8ToGBk("渠道名称"));
        titleMap.put("cooperType", utf8ToGBk("合作方式"));
        titleMap.put("activateUserCount", utf8ToGBk("激活用户数"));
        titleMap.put("activityUnitPrice", utf8ToGBk("激活单价"));
        titleMap.put("payUserCount", utf8ToGBk("付费用户数"));
        titleMap.put("chargeAmount", utf8ToGBk("充值金额"));
        titleMap.put("settleAmount", utf8ToGBk("结算金额"));
    }
    
    private static final Map<String, String> cpaTitleMap = new HashMap<String, String>(); 
    static {
        cpaTitleMap.put("date", utf8ToGBk("日期"));
        cpaTitleMap.put("activateUserCount", utf8ToGBk("激活用户数"));
    }
    
    private static final Map<String, String> cpsTitleMap = new HashMap<String, String>(); 
    static {
        cpsTitleMap.put("date", utf8ToGBk("日期"));
        cpsTitleMap.put("payUserCount", utf8ToGBk("付费用户数"));
        cpsTitleMap.put("chargeAmount", utf8ToGBk("充值金额"));
        cpsTitleMap.put("settleAmount", utf8ToGBk("结算金额"));
    }
    
    private static String[] titleArray = new String[]{"channelName", "cooperType", "activateUserCount", "activityUnitPrice"
        , "payUserCount", "chargeAmount", "settleAmount"};
    
    private static String[] cpaTitleArray = new String[]{"date", "activateUserCount"};
    
    private static String[] cpsTitleArray = new String[]{"date", "payUserCount", "chargeAmount", "settleAmount"};
    
    private static String utf8ToGBk(String s){
        return s;
    }
    
    private void writeExcelTitle(String[] titleArray, WritableSheet sheet, Map<String, String> titleMap) throws RowsExceededException, WriteException{

        Label label = null;
        for (int i =0; i< titleArray.length; i++){
            String title =titleArray[i];
            if (titleMap.get(title) == null) continue;
            label = new Label(i, 0, titleMap.get(title));
            sheet.addCell(label);
        }
    }
    
    private void writeContent(String[] titleArray, List<BalanceAmountVo> dataList, WritableSheet sheet, Map<String, String> titleMap)
            throws RowsExceededException, WriteException{
        Label label = null;
        for (int i = 0; i<dataList.size(); i++) {
            BalanceAmountVo balanceAmountVo = dataList.get(i);
            for (int j = 0; j<titleArray.length; j++){
                String title =titleArray[j];
                if (titleMap.get(title) == null) continue;
                label = new Label(j, i+1, getPropValue(balanceAmountVo, title));
                sheet.addCell(label);
            }
        }

    }
    
    private String getPropValue(BalanceAmountVo balanceAmountVo, String prop){
        String s = "";
        if ("channelName".equalsIgnoreCase(prop)){
            s = utf8ToGBk(balanceAmountVo.getChannelName());
        } else if ("cooperType".equalsIgnoreCase(prop)){
            s = (balanceAmountVo.getCooperType());
        } else if ("activateUserCount".equalsIgnoreCase(prop)){
            s = utf8ToGBk(String.valueOf(balanceAmountVo.getActivateUserCount()));
        } else if ("activityUnitPrice".equalsIgnoreCase(prop)){
            s = utf8ToGBk(String.valueOf(balanceAmountVo.getActivityUnitPrice()));
        } else if ("payUserCount".equalsIgnoreCase(prop)){
            s = utf8ToGBk(String.valueOf(balanceAmountVo.getPayUserCount()));
        } else if ("chargeAmount".equalsIgnoreCase(prop)){
            s = utf8ToGBk(String.valueOf(balanceAmountVo.getChargeAmount()));
        } else if ("settleAmount".equalsIgnoreCase(prop)){
            s = utf8ToGBk(String.valueOf(balanceAmountVo.getSettleAmount()));
        } else if ("date".equalsIgnoreCase(prop)){
            s = utf8ToGBk(String.valueOf(balanceAmountVo.getDate()));
        }
        return s;
    }
}
