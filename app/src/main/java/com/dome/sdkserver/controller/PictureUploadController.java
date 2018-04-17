package com.dome.sdkserver.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dome.sdkserver.util.UploadUtil;
import com.dome.sdkserver.view.AjaxResult;

@Controller("/pic")
public class PictureUploadController extends BaseController {

	/** 有效的图片扩展名 **/
    private static final List<String> imgExtensions = Arrays.asList("jpeg", "jpg", "gif", "png", "bmp");
    
    /**
     * 上传图片限制为10M
     */
    private static final int imgMaxFileSize = 10 * 1024 * 1024;

	/**
	 * 图片上传
	 * @param request
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public AjaxResult uploadFile(@RequestParam MultipartFile pic){
		String result = checkPictureExtension(pic.getOriginalFilename());
		if (result != null) {
			return AjaxResult.failed(result);
		}
		if (pic.getSize() > imgMaxFileSize) {
			return AjaxResult.failed("图片大小限制为10M以下");
		}
		
		String errorMsg = "上传图片出错";
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        try {
			BufferedImage bufferedFile = ImageIO.read(pic.getInputStream());
			ImageIO.write(bufferedFile, "png", byteArray);
		} catch (IOException e) {
			log.error(errorMsg, e);
			return AjaxResult.failed(errorMsg);
		} finally {
			if (byteArray != null){
				try {
					byteArray.close();
				} catch (IOException e) {
					
					log.error("关闭输出流失败", e);
					return AjaxResult.failed(errorMsg);
				}
			}
		}
        String imgUrl = UploadUtil.upload(UUID.randomUUID().toString(), byteArray.toByteArray());
        Map<String, String> picMap = new HashMap<String, String>(4);
        picMap.put("imgUrl", imgUrl);
        
		return AjaxResult.success(picMap);
	}

    
    private String checkPictureExtension(String picFileName){
    	if (!StringUtils.isEmpty(picFileName)) {
    		int index = picFileName.lastIndexOf(".");
    		if (index > 0){
    			String extension = picFileName.substring(index).toLowerCase();
    			if (imgExtensions.contains(extension)){
    				return null;
    			}
    		}
    	}
    	log.error("上传的图片文件名为：{}", picFileName);
    	return "图片不合法，仅支持jpg、jpeg、png、gif、bmp等格式";
    }
}
