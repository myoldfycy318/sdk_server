package com.dome.sdkserver.service.impl;

import com.dome.sdkserver.bo.*;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.constants.AuditStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.ChargePointMapper;
import com.dome.sdkserver.metadata.dao.mapper.MerchantAppMapper;
import com.dome.sdkserver.metadata.dao.mapper.MerchantInfoMapper;
import com.dome.sdkserver.metadata.entity.AppVo;
import com.dome.sdkserver.service.BusinessService;
import com.dome.sdkserver.service.ChargePointService;
import com.dome.sdkserver.util.DateUtil;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private MerchantInfoMapper merchantInfoMapper;

    @Resource
    private MerchantAppMapper merchantAppMapper;

    @Resource
    private ChargePointMapper chargePointMapper;

    /**
     * 导出商户信息
     *
     * @return
     */
    @Override
    public byte[] exportMerchantInfo() throws Exception {
        ByteArrayOutputStream out = null;
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        MerchantInfo merchantInfo = null;
        try {
            List<MerchantInfo> merchantInfos =merchantInfoMapper.getMerchantInfoListByCondition(new SearchMerchantInfoBo());
            // Workbook，对应一个Excel文件
            wb = new HSSFWorkbook();
            //创建sheet
            sheet = wb.createSheet("商户信息");
            //在sheet中添加表头第0行
            HSSFRow row = sheet.createRow((int) 0);
            // 创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            // 创建一个居中格式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //设置表头
            List<String> excelHead = getMerchantExcelHead();
            // excel头
            getExcelHeader(row, style, excelHead);
            // 写入实体数据
            for (int i = 0; i < merchantInfos.size(); i++) {
                row = sheet.createRow((int) i + 1);
                merchantInfo = merchantInfos.get(i);
                // 创建单元格，并设置值
                int j = 0;
                insertCell(row, j++, merchantInfo.getMerchantFullName(), style);
                insertCell(row, j++, merchantInfo.getMerchantCode(), style);
                insertCell(row, j++, merchantInfo.getContacts(), style);
                insertCell(row, j++, merchantInfo.getMobilePhoneNum(), style);
                insertCell(row, j++, merchantInfo.getEmail(), style);
                insertCell(row, j++, DateUtil.dateToDateString(merchantInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"), style);
                String statusDesc = merchantInfo.getStatus() == null ? "" : AuditStatusEnum.getFromKey(merchantInfo.getStatus()).getMsg();
                insertCell(row, j++, statusDesc, style);
            }
            out = new ByteArrayOutputStream();
            wb.write(out);
            wb.close();
            return out.toByteArray();
        } catch (Exception e) {
            logger.error("exportMerchantInfo error", e);
            throw e;
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 导出全量业务信息
     *
     * @return
     */
    @Override
    public byte[] exportBusinessInfo() throws Exception {
        ByteArrayOutputStream out = null;
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        MerchantAppInfo merchantAppInfo = null;
        try {
            //获取应用信息
            List<AppVo> merchantAppInfoList = merchantAppMapper.getAppListByCondition(new SearchMerchantAppBo());
            // Workbook，对应一个Excel文件
            wb = new HSSFWorkbook();
            //创建sheet
            sheet = wb.createSheet("业务信息");
            //在sheet中添加表头第0行
            HSSFRow row = sheet.createRow((int) 0);
            // 创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            // 创建一个居中格式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //设置表头
            List<String> excelHead = getBusinessExcelHead();
            // excel头
            getExcelHeader(row, style, excelHead);
            // 写入实体数据
            for (int i = 0; i < merchantAppInfoList.size(); i++) {
                row = sheet.createRow((int) i + 1);
                merchantAppInfo = merchantAppInfoList.get(i);
                // 创建单元格，并设置值
                int j = 0;
                insertCell(row, j++, merchantAppInfo.getAppCode(), style);
                insertCell(row, j++, merchantAppInfo.getAppName(), style);
                insertCell(row, j++, merchantAppInfo.getMerchantCode(), style);
                insertCell(row, j++, merchantAppInfo.getMerchantFullName(), style);
                insertCell(row, j++, DateUtil.dateToDateString(merchantAppInfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"), style);
                insertCell(row, j++, DateUtil.dateToDateString(merchantAppInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"), style);
                String statusDesc = merchantAppInfo.getStatus() == null ? "" : (AppStatusEnum.getFromKey(merchantAppInfo.getStatus()) == null ? "" : AppStatusEnum.getFromKey(merchantAppInfo.getStatus()).getMsg());
                insertCell(row, j++, statusDesc, style);
            }
            out = new ByteArrayOutputStream();
            wb.write(out);
            wb.close();
            return out.toByteArray();
        } catch (Exception e) {
            logger.error("exportBusinessInfo error", e);
            throw e;
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
    @Autowired
    private ChargePointService chargePointService;
    /**
     * 导出计费点信息
     *
     * @return
     */
    @Override
    public byte[] exportChargePointInfo(String appCode) throws Exception {
        ByteArrayOutputStream out = null;
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        ChargePointInfo chargePointInfo = null;
        try {
            SearchChargePointBo searchBo = new SearchChargePointBo();
            if (!StringUtils.isEmpty(appCode)) {
            	searchBo.setAppCode(appCode);
            }
			List<ChargePointInfo> chargePointInfos = chargePointService.getChargePointInfos(searchBo);
			//chargePointMapper.getChargePontInfosByCondition(searchBo);
            // Workbook，对应一个Excel文件
            wb = new HSSFWorkbook();
            //创建sheet
            sheet = wb.createSheet("计费点信息");
            //在sheet中添加表头第0行
            HSSFRow row = sheet.createRow((int) 0);
            // 创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            // 创建一个居中格式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //设置表头
            List<String> excelHead = getChargePointHead();
            // excel头
            getExcelHeader(row, style, excelHead);
            // 写入实体数据
            for (int i = 0; i < chargePointInfos.size(); i++) {
                row = sheet.createRow((int) i + 1);
                chargePointInfo = chargePointInfos.get(i);
                // 创建单元格，并设置值
                int j = 0;
                insertCell(row, j++, chargePointInfo.getChargePointName(), style);
                insertCell(row, j++, chargePointInfo.getChargePointAmount()+"", style);
                insertCell(row, j++, chargePointInfo.getDesc(), style);
                insertCell(row, j++, chargePointInfo.getPath(), style);
            }
            out = new ByteArrayOutputStream();
            wb.write(out);
            wb.close();
            return out.toByteArray();
        } catch (Exception e) {
            logger.error("exportChargePointInfo error", e);
            throw e;
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 设置excel表头
     *
     * @param row
     * @param style
     * @param excelHead
     */
    private void getExcelHeader(HSSFRow row, HSSFCellStyle style, List<String> excelHead) {
        HSSFCell cell;
        for (int i = 0; i < excelHead.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(excelHead.get(i));
            cell.setCellStyle(style);
        }
    }

    /**
     * cell 设值
     *
     * @param row
     * @param i
     * @param object
     */
    private void insertCell(HSSFRow row, int i, Object object, HSSFCellStyle style) {
        HSSFCell cell = row.createCell(i);
        if (object == null) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(object.toString());
        }
        cell.setCellStyle(style);
    }

    /**
     * 获取公司信息表头
     *
     * @return
     */
    @SuppressWarnings("serial")
	private List<String> getBusinessExcelHead() {
        List<String> headers = new ArrayList<String>() {{
            add("appid");
            add("业务名称");
            add("商户编码");
            add("公司名称");
            add("申请时间");
            add("更新时间");
            add("状态");
        }};
        return headers;
    }


    /**
     * 获取公司信息表头
     *
     * @return
     */
    @SuppressWarnings("serial")
	private List<String> getMerchantExcelHead() {
        List<String> headers = new ArrayList<String>() {{
            add("公司名称");
            add("商户编码");
            add("联系人");
            add("手机号码");
            add("邮箱地址");
            add("申请时间");
            add("状态");
        }};
        return headers;
    }

    /**
     * 获取公司信息表头
     *
     * @return
     */
    @SuppressWarnings("serial")
	private List<String> getChargePointHead() {
        List<String> headers = new ArrayList<String>() {{
            add("计费点名称");
            add("计费点金额");
            add("道具说明");
            add("道具路径");
        }};
        return headers;
    }

}
