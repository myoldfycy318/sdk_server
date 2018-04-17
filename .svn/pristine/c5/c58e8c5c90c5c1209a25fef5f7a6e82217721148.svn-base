package com.dome.sdkserver.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

/**
 * 七牛图片上传工具类
 * 
 * @author Frank.Zhou
 *
 */
public final class UploadUtil {

    private static final Logger logger = LoggerFactory.getLogger(UploadUtil.class);

    private static String BUCKET_NAME = null;
    private static String QINIU_URL = null;

    private static Mac mac = null;
    
    private UploadUtil() {
    }
    
    static {
        try {
            Properties p = PropertiesLoaderUtils.loadProperties(new ClassPathResource("qiniu.properties"));
            Config.ACCESS_KEY = p.getProperty("ACCESS_KEY");
            Config.SECRET_KEY = p.getProperty("SECRET_KEY");
            BUCKET_NAME = p.getProperty("BUCKET_NAME");
            QINIU_URL = p.getProperty("QINIU_URL");
            mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        } catch (IOException e) {
            logger.error("读取七牛配置文件失败 {}", ExceptionUtils.getStackTrace(e));
        }
        System.out.println("读取七牛配置文件");
    }

    /**
     * 文件上传到七牛服务器
     * @param fileName 文件名
     * @param fileBytes 文件内容
     * @return 文件url路径
     */
    public static String upload(String fileName, byte[] fileBytes) {
        try {
            String imgMd5 = MD5Util.getMD5String(fileBytes);
            // 图片保存的相对目录
            fileName = FileUtils.formatFilePath(FileUtils.DEFAULT_STANDARD_FILE_DELIMITER + imgMd5 + fileName);
            if (StringUtils.startsWith(fileName, "/")) {
                fileName = StringUtils.substringAfter(fileName, "/");
            }
            //
            PutPolicy putPolicy = new PutPolicy(BUCKET_NAME);
            String token = putPolicy.token(mac);

            PutExtra extra = new PutExtra();
            PutRet put = IoApi.Put(token, fileName, new ByteArrayInputStream(fileBytes), extra);
            if(put != null)
            logger.info("put.tostring========" + put.toString());
            logger.info("put.getResponse========" + put.getResponse());
            if (!isSuccess(put)) {
                throw new RuntimeException();
            }
            return QINIU_URL + FileUtils.formatFileDyncPath(fileName);
        } catch (Exception e) {
            logger.error("QnUtils.upload exception {}", ExceptionUtils.getStackTrace(e));
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断上传结果是否成功
     */
    private static boolean isSuccess(PutRet put) {
        return (put != null) && put.toString().startsWith("{\"hash\":\"");
    }
    

}
