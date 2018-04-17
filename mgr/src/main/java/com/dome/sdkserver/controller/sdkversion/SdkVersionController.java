package com.dome.sdkserver.controller.sdkversion;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bo.sdkversion.SdkVersionInfo;
import com.dome.sdkserver.constants.Constant.SdkVersionReleaseStatus;
import com.dome.sdkserver.constants.Constant.SdkVersionResEnum;
import com.dome.sdkserver.service.sdkversion.SdkVersionService;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.util.FtpUtil;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RegexpUtil;
import com.dome.sdkserver.view.AjaxResult;
import com.github.pagehelper.Page;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SdkVersionController
 *
 * @author Zhang ShanMin
 * @date 2016/5/10
 * @time 12:30
 */

@Controller
@RequestMapping("/sdkversion")
public class SdkVersionController {

    Logger logger = LoggerFactory.getLogger(SdkVersionController.class);

    @Resource(name = "sdkVersionService")
    private SdkVersionService sdkVersionService;

    @Autowired
    private PropertiesUtil domainConfig;

    //默认分页条数
    private static final Integer DEAULT_PAGE_SIZE = 10;
    @Value("${ftp.hostname}")
    private String hostname;
    
    @Value("${ftp.port}")
    private int port;
    
    @Value("${ftp.username}")
    private String username;
    
    @Value("${ftp.password}")
    private String password;
    
    @Value("${ftp.downloadurl}")
    private String downloadDomain;
    
    @Value("${ftp.workpath}")
    private String workPath;
    /**
     * 新增sdk版本信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult save(HttpServletRequest request) {
        try {
            SdkVersionInfo sdkVersionInfo = new SdkVersionInfo();
            AjaxResult ajaxResult = validateReqParam(request, sdkVersionInfo);
            logger.info("sdkversion.save.sdkVersionInfo="+ JSON.toJSONString(sdkVersionInfo));
            if (ajaxResult.getResponseCode() != AjaxResult.CODE_SUCCESS)
                return ajaxResult;
            sdkVersionInfo.setIsRelease(SdkVersionReleaseStatus.NOT_RELEASED.getStatus());
            if (!sdkVersionService.insertSdkVersion(sdkVersionInfo))
                return AjaxResult.failed(SdkVersionResEnum.FAILED.getRetCode(), SdkVersionResEnum.FAILED.getRetMsg());
        } catch (DuplicateKeyException dkException){
            logger.error("sdkversion.dkException.error",dkException);
            return AjaxResult.failed(SdkVersionResEnum.VERSION_NUM_EXISTS.getRetCode(), SdkVersionResEnum.VERSION_NUM_EXISTS.getRetMsg());
        } catch (Exception e) {
            logger.error("sdkversion.save.error",e);
            return AjaxResult.failed(SdkVersionResEnum.FAILED.getRetCode(), SdkVersionResEnum.FAILED.getRetMsg());
        }
        return AjaxResult.success(SdkVersionResEnum.SUCCESS.getRetCode(), SdkVersionResEnum.SUCCESS.getRetMsg());
    }

    /**
     * 验证热更请求参数
     *
     * @param request
     * @param sdkVersionInfo
     * @return
     * @throws UnsupportedEncodingException
     */
    public AjaxResult validateReqParam(HttpServletRequest request, SdkVersionInfo sdkVersionInfo) throws UnsupportedEncodingException {
        String versionName = request.getParameter("versionName");
        //版本名称（不能为空）
        if (StringUtils.isEmpty(versionName))
            return AjaxResult.failed(SdkVersionResEnum.VERSION_NAME_NULL.getRetCode(), SdkVersionResEnum.VERSION_NAME_NULL.getRetMsg());
        sdkVersionInfo.setVersionName(versionName);
        //版本号（不能为空）且都为5位数字
        String versionNum = request.getParameter("versionNum");
        if (StringUtils.isEmpty(versionNum))
            return AjaxResult.failed(SdkVersionResEnum.VERSION_NUM_NULL.getRetCode(), SdkVersionResEnum.VERSION_NUM_NULL.getRetMsg());
        if (!RegexpUtil.isNumber(versionNum, 5))
            return AjaxResult.failed(SdkVersionResEnum.VERSION_NUM_ERROR.getRetCode(), SdkVersionResEnum.VERSION_NUM_ERROR.getRetMsg());
        sdkVersionInfo.setVersionNum(versionNum);
        //需热更版本 不填默认为全部版本
        String needUpdateVersion = request.getParameter("needUpdateVersion");
        if (StringUtils.isEmpty(needUpdateVersion)) {
            sdkVersionInfo.setNeedUpdateVersion("");
        } else {
            if (!(needUpdateVersion.length() >= 5 && needUpdateVersion.matches("[0-9,]+") && validateNeedUpdateVersions(needUpdateVersion.split(","))))
                return AjaxResult.failed(SdkVersionResEnum.NEED_UPDATE_VERSION_ERROR.getRetCode(), SdkVersionResEnum.NEED_UPDATE_VERSION_ERROR.getRetMsg());
            sdkVersionInfo.setNeedUpdateVersion(needUpdateVersion);
        }
        //需热更游戏 不填写默认全部游戏
        String needUpdateGame = request.getParameter("needUpdateGame");
        if (StringUtils.isEmpty(needUpdateGame)) {
            sdkVersionInfo.setNeedUpdateGame("");
        } else {
            if (!needUpdateGame.matches("[A-Za-z0-9,]+"))
                return AjaxResult.failed(SdkVersionResEnum.NEED_UPDATE_GAME_ERROR.getRetCode(), SdkVersionResEnum.NEED_UPDATE_GAME_ERROR.getRetMsg());
            sdkVersionInfo.setNeedUpdateGame(needUpdateGame);
        }
        //包体上传
        String sdkPath = request.getParameter("sdkPath");
        if (StringUtils.isEmpty(sdkPath))
            return AjaxResult.failed(SdkVersionResEnum.SDK_PATH_NULL.getRetCode(), SdkVersionResEnum.SDK_PATH_NULL.getRetMsg());
        sdkVersionInfo.setSdkPath(sdkPath);
        //版本说明
        String versionDesc = request.getParameter("versionDesc");
        sdkVersionInfo.setVersionDesc(StringUtils.isEmpty(versionDesc) ? "" : versionDesc);
        return AjaxResult.success();
    }

    /**
     * 需热更版本必须都为5位数字
     *
     * @param needUpdateVersions
     * @return
     */
    public boolean validateNeedUpdateVersions(String[] needUpdateVersions) {
        for (String needUpdateVersion : needUpdateVersions)
            if (!RegexpUtil.isNumber(needUpdateVersion, 5))
                return false;
        return true;
    }

    /**
     * 编辑
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult update(HttpServletRequest request) {
        try {
            SdkVersionInfo sdkVersionInfo = new SdkVersionInfo();
            String sdkId = request.getParameter("id");
            if (StringUtils.isEmpty(sdkId))
                return AjaxResult.failed(SdkVersionResEnum.SDK_ID_NULL.getRetCode(), SdkVersionResEnum.SDK_ID_NULL.getRetMsg());
            sdkVersionInfo.setId(Integer.valueOf(sdkId));
            //验证请求参数
            AjaxResult ajaxResult = validateReqParam(request, sdkVersionInfo);
            if (ajaxResult.getResponseCode() != AjaxResult.CODE_SUCCESS)
                return ajaxResult;
            if (!sdkVersionService.updateSdkVersionById(sdkVersionInfo))
                return AjaxResult.failed(SdkVersionResEnum.SDK_VERSION_UPDATE_FAIL.getRetCode(), SdkVersionResEnum.SDK_VERSION_UPDATE_FAIL.getRetMsg());
        } catch (Exception e) {
            logger.error("sdkversion.update.error", e);
            return AjaxResult.failed(SdkVersionResEnum.SDK_VERSION_UPDATE_FAIL.getRetCode(), SdkVersionResEnum.SDK_VERSION_UPDATE_FAIL.getRetMsg());
        }
        return AjaxResult.success(SdkVersionResEnum.SUCCESS.getRetCode(), SdkVersionResEnum.SUCCESS.getRetMsg());
    }

    @RequestMapping(value = "/delopy", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult delopy(HttpServletRequest request) {
        try {
            SdkVersionInfo sdkVersionInfo = new SdkVersionInfo();
            String sdkId = request.getParameter("id");
            if (StringUtils.isEmpty(sdkId))
                return AjaxResult.failed(SdkVersionResEnum.SDK_ID_NULL.getRetCode(), SdkVersionResEnum.SDK_ID_NULL.getRetMsg());
            sdkVersionInfo.setId(Integer.valueOf(sdkId));
            sdkVersionInfo.setReleaseTime(new Date());
            sdkVersionInfo.setIsRelease(SdkVersionReleaseStatus.RELEASED.getStatus());
            if (!sdkVersionService.delopySdkVersionById(sdkVersionInfo))
                return AjaxResult.failed(SdkVersionResEnum.SDK_VERSION_UPDATE_FAIL.getRetCode(), SdkVersionResEnum.SDK_VERSION_UPDATE_FAIL.getRetMsg());
        } catch (Exception e) {
            logger.error("sdkversion.delopy.error", e);
            return AjaxResult.failed(SdkVersionResEnum.SDK_VERSION_UPDATE_FAIL.getRetCode(), SdkVersionResEnum.SDK_VERSION_UPDATE_FAIL.getRetMsg());
        }
        return AjaxResult.success(SdkVersionResEnum.SUCCESS.getRetCode(), SdkVersionResEnum.SUCCESS.getRetMsg());
    }

    /**
     * 获取sdk版本信息列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public AjaxResult getSdkVersionList(HttpServletRequest request) {
        SdkVersionInfo sdkVersionInfo = new SdkVersionInfo();
        try {
            encapsulateListParams(request, sdkVersionInfo);
            String pageNo = request.getParameter("pageNo");
            //默认查找第一页
            int pageNumber = (StringUtils.isEmpty(pageNo) || !pageNo.matches("\\d+")) ? 1 : Integer.valueOf(pageNo);
            String pSize = request.getParameter("pageSize");
            logger.info("sdkversion.getSdkVersionList.reqParams="+JSON.toJSONString(sdkVersionInfo));
            int pageSize = (StringUtils.isEmpty(pSize) || !pSize.matches("\\d+")) ? DEAULT_PAGE_SIZE : Integer.valueOf(pSize);
            Page<SdkVersionInfo> page = sdkVersionService.getSdkVersionByPage(sdkVersionInfo, pageNumber, pageSize);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("totalCount", page.getTotal());
            map.put("sdkList", page);
            return AjaxResult.success(map);
        } catch (NumberFormatException e) {
            logger.error("sdkversion.list.error:", e);
            return AjaxResult.failed();
        }
    }

    /**
     * 封装获取sdk列表请求参数
     *
     * @param request
     * @param sdkVersionInfo
     */
    private void encapsulateListParams(HttpServletRequest request, SdkVersionInfo sdkVersionInfo) {
        String versionNum = request.getParameter("versionNum");
        if (StringUtils.isNotEmpty(versionNum))
            sdkVersionInfo.setVersionNum(versionNum);
        String startTime = request.getParameter("startTime");
        if (StringUtils.isNotEmpty(startTime))
            sdkVersionInfo.setSdkUploadStartTime(DateUtil.getDate(startTime, "yyyy-MM-dd"));
        String endTime = request.getParameter("endTime");
        if (StringUtils.isNotEmpty(endTime))
            sdkVersionInfo.setSdkUploadEndTime(DateUtil.getDate(endTime, "yyyy-MM-dd"));
    }

    /**
     * 根据sdkId获取对应的sdk版本信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSdkVersion", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getSdkVersion(HttpServletRequest request) {
        try {
            SdkVersionInfo sdkVersionInfo = new SdkVersionInfo();
            String sdkId = request.getParameter("id");
            if (StringUtils.isEmpty(sdkId))
                return AjaxResult.failed(SdkVersionResEnum.SDK_ID_NULL.getRetCode(), SdkVersionResEnum.SDK_ID_NULL.getRetMsg());
            sdkVersionInfo.setId(Integer.valueOf(sdkId));
            List<SdkVersionInfo> sdkVersionInfoList = sdkVersionService.getSdkVersionList(sdkVersionInfo);
            if (sdkVersionInfoList == null || sdkVersionInfoList.size() <= 0)
                return AjaxResult.failed(SdkVersionResEnum.SDK_VERSION_NOT_FOUND.getRetCode(), SdkVersionResEnum.SDK_VERSION_NOT_FOUND.getRetMsg());
            return AjaxResult.success(sdkVersionInfoList.get(0));
        } catch (NumberFormatException e) {
            logger.error("sdkversion.list.error:", e);
            return AjaxResult.failed();
        }
    }
    // 生成上传后的文件名，加上时间戳
    private static String generateUploadFileName(String fileName){
    	int index = fileName.lastIndexOf(".");
    	String finalFileName = null;
    	if (index == -1) {
    		finalFileName= Long.toString(System.currentTimeMillis());
    	} else {
    		String postfix = fileName.substring(index);
    		finalFileName= Long.toString(System.currentTimeMillis()) + postfix;
    	}
    	return finalFileName;
    }
    /**
     * sdk版本上传
     *
     * @param multipartFile
     * @param request
     * @return
     */
//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    @ResponseBody
    public AjaxResult upload(@RequestParam(value = "file") MultipartFile multipartFile, HttpServletRequest request) {
        String fileName = multipartFile.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)) return AjaxResult.failed("filename is empty");
        try {
        	/**
        	 * sdk热更包会根据文件名进行校验，需要保留原有的文件名
        	 */
            //fileName = generateUploadFileName(fileName);
            
//            String domain = domainConfig.getString("sdk.version.upload.domain");
//            String serverDir = domainConfig.getString("sdk.version.upload.path");
            String currentDate = DateUtil.dateToDateString(new Date(), "yyyyMMdd");
//            String currentTime = DateUtil.dateToDateString(new Date(), "HHmm");
//            String targetFileDir =  serverDir + File.separator + currentDate+File.separator+currentTime+File.separator;
//            String relativePath = File.separator + currentDate+File.separator+currentTime+File.separator;
            
//            File targetFile = new File(targetFileDir, fileName);
//            if (!targetFile.exists()) {
//                targetFile.mkdirs();
//            }
            String path = this.workPath + currentDate;
            logger.info("sdk版本上传,路径为:{},版本名为:{}", path, fileName);
            boolean flag = FtpUtil.uploadFile(hostname, port, username, password, path, fileName, multipartFile.getInputStream());
            if (!flag) return AjaxResult.failed("upload failed");
            // 保存
//            multipartFile.transferTo(targetFile);
            Map<String,String> map = new HashMap<String, String>();
            map.put("sdkPath",downloadDomain + currentDate + File.separator +fileName);
            map.put("fileName",fileName);
            return AjaxResult.success(map);
        } catch (Exception e) {
            logger.error("sdk版本:{}上传错误:{}", fileName, e);
            return AjaxResult.failed();
        }
    }


}
