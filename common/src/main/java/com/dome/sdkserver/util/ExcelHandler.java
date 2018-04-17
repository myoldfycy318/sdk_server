package com.dome.sdkserver.util;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * ExcelHandler
 * Excel输出处理
 * @author Zhang ShanMin
 * @date 2016/7/23
 * @time 18:24
 */
public abstract class ExcelHandler {

    /**
     * 输出excel byte[]
     * @param sheetName
     * @param dateList
     * @param feildMap
     * @return
     * @throws Exception
     */
    public byte[] exportByte(String sheetName, List<?> dateList, Map<String, String> feildMap,List<String> pendFields) throws Exception {
        ByteArrayOutputStream out = null;
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        Object obj = null;
        try {
            // Workbook，对应一个Excel文件
            wb = new HSSFWorkbook();
            //创建sheet
            sheet = wb.createSheet(sheetName);
            //在sheet中添加表头第0行
            HSSFRow row = sheet.createRow((int) 0);
            // 创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            // 创建一个居中格式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //设置表头
            getExcelHeader(row, style, feildMap.values());
            boolean flag = false;
            // 写入实体数据
            for (int i = 0; i < dateList.size(); i++) {
                row = sheet.createRow((int) i + 1);
                //获取写入数据
                obj = dateList.get(i);
                // 创建单元格，并设置值
                int j = 0;
                Field[] fields = obj.getClass().getDeclaredFields();
                for (String key : feildMap.keySet()) {
                    if (pendFields.contains(key)){
                        insertCell(row, j++, handleSpecialField(key, obj), style);
                        continue;
                    }
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (key.equals(field.getName())) {
                            insertCell(row, j++, field.get(obj), style);
                        }
                    }
                }
            }
            out = new ByteArrayOutputStream();
            wb.write(out);
            wb.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(out);
        }
    }


    /**
     * 处理excel中特定字段的映射
     * @param fieldName
     * @param obj
     * @return
     */
    protected abstract String handleSpecialField(String fieldName, Object obj);

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
     * 设置excel表头
     *
     * @param row
     * @param style
     * @param excelHead
     */
    private void getExcelHeader(HSSFRow row, HSSFCellStyle style, Collection<String> excelHead) {
        HSSFCell cell;
        int i = 0;
        Iterator<String> iterator = excelHead.iterator();
        while (iterator.hasNext()) {
            cell = row.createCell(i);
            cell.setCellValue(iterator.next());
            cell.setCellStyle(style);
            i++;

        }
    }

}
