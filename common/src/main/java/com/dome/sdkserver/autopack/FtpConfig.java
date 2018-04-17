package com.dome.sdkserver.autopack;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * ftp和签名证书配置
 * @author li
 *
 */
public class FtpConfig {

	private static final Logger logger = LoggerFactory.getLogger(FtpConfig.class);
	static String user;
	
	static String password;
	
	static String ip;
	
	static int port;
	
	static String remotePath;
	
	/**
	 * 包体在主站上保存的路径
	 */
	private static String uploadPath;
	
	/**
	 * 包体下载地址前缀
	 */
	private static String pkgDownloadUrl;
	/**
	 * 包体上传到ftp的路径
	 */
	private static String pkgUploadPath;
	
	/**
	 * 渠道包体上传到ftp的路径
	 */
	private static String autoPkgUploadPath;
	
	/**
	 * 签名证书文件完整路径
	 */
	private static String keystore;
	
	/**
	 * 签名证书别名
	 */
	private static String keystoreAlias;
	
	/**
	 * apk签名密钥
	 */
	private static String keystoreSecret;
	
	/**
	 * 渠道包下载地址前缀
	 */
	private static String autoPkgDownloadUrl;
	
	/**
	 * 证书-keypass
	 */
	private static String keyPassword;
	static {
		
		try {
			Properties prop= PropertiesLoaderUtils.loadProperties(new ClassPathResource("pkg.properties"));
			user = prop.getProperty("ftp.username");
			password=prop.getProperty("ftp.password");
			ip=prop.getProperty("ftp.hostname");
			port=Integer.parseInt(prop.getProperty("ftp.port", "21"));
			remotePath=prop.getProperty("ftp.workpath");
			uploadPath=prop.getProperty("domesdk.pkg.upload.path");
			pkgUploadPath=prop.getProperty("ftp.workpath");
			pkgDownloadUrl=prop.getProperty("ftp.downloadurl");
			
			keystore=prop.getProperty("apk.keystore");
			keystoreAlias=prop.getProperty("apk.keystore.alias");
			keystoreSecret=prop.getProperty("apk.keystore.secret");
			keyPassword=prop.getProperty("apk.keystore.keypassword");
			autoPkgUploadPath=prop.getProperty("ftp.autopkg.workpath");
			autoPkgDownloadUrl=prop.getProperty("ftp.autopkg.downloadurl");
			
		} catch (IOException e) {
			// sdkserver项目不需要解析pkg.properties
			logger.info("load ftp config file error", e);
		}
	}
	
	public static String getUploadPath() {
		return uploadPath;
	}

	public static String getKeystore() {
		return keystore;
	}

	public static String getKeystoreAlias() {
		return keystoreAlias;
	}

	public static String getKeystoreSecret() {
		return keystoreSecret;
	}

	public static String getAutoPkgUploadPath() {
		return autoPkgUploadPath;
	}

	public static String getAutoPkgDownloadUrl() {
		return autoPkgDownloadUrl;
	}

	public static String getPkgDownloadUrl() {
		return pkgDownloadUrl;
	}

	public static String getPkgUploadPath() {
		return pkgUploadPath;
	}

	public static String getKeyPassword() {
		return keyPassword;
	}
	
	
}
