package com.dome.sdkserver.controller;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.service.MerchantAppAuditService;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.sdkserver.metadata.entity.AppOperRecord;
import com.dome.sdkserver.service.AppOperRecordService;
import com.dome.sdkserver.service.BusinessService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BusinessController
 * 业务查询
 *
 * @author Zhang ShanMin
 * @date 2016/4/7
 * @time 10:44
 */
@Controller
@RequestMapping("/business")
public class BusinessController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private BusinessService businessService;
    @Resource
    private AppOperRecordService appOperRecordService;
    @Resource
    private MerchantAppAuditService merchantAppAuditService;


    @RequestMapping("/exportMerchantInfo")
    public void exportMerchantInfo(HttpServletResponse response) {
        OutputStream os = null;
        try {
//            byte[] fileNameByte = ("商户信息.xls").getBytes("GBK");
//            String filename = new String(fileNameByte, "ISO8859-1");
            String filename = generateFileName("merchant");
            byte[] bytes = businessService.exportMerchantInfo();
            response.setContentType("application/x-msdownload");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "attachment;filename="
                    + filename);
            os = response.getOutputStream();
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            logger.error("exportMerchantInfo error", e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    @RequestMapping("/exportBusinessInfo")
    public void exportBusinessInfo(HttpServletResponse response) {
        OutputStream os = null;
        try {
//            byte[] fileNameByte = ("业务信息.xls").getBytes("GBK");
//            String filename = new String(fileNameByte, "ISO8859-1");
        	String filename = generateFileName("app");
            byte[] bytes = businessService.exportBusinessInfo();
            response.setContentType("application/x-msdownload");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "attachment;filename="
                    + filename);
            os = response.getOutputStream();
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            logger.error("exportBusinessInfo error", e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    @RequestMapping("/buOperateHistory")
    @ResponseBody
    public AjaxResult getBuOperateHistory(@RequestParam(value = "appCode") String appCode,  Paginator paginator,
    		HttpServletRequest request) {
        if (StringUtils.isEmpty(appCode)) return AjaxResult.failed("appCode不能为空");
        int count = appOperRecordService.selectOperRecordCount(appCode);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("totalCount", count);
        List<AppOperRecord> list = null;
        if (count > 0){
			paginator=Paginator.handlePage(paginator, count, request);
            list = appOperRecordService.queryOperRecordList(appCode, paginator.getStart(), paginator.getPageSize());
        } else {
        	list = new ArrayList<AppOperRecord>();
        }
        dataMap.put("operList", list);
        AjaxResult ajaxResult = AjaxResult.success(dataMap);
        return ajaxResult;
    }
    
    private static String generateFileName(String filenamePrefix){
    	String filename = filenamePrefix + "_" + DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss")
    			+ ".xls";
    	return filename;
    }
}
