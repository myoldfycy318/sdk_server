package com.dome.sdkserver.debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dome.sdkserver.common.Constants;


/**
 * 开发人员调试配置管理
 * 1、打印请求参数
 * 2、执行sql和耗时
 * 使用注意：1、需要先调用isDevepMode()，返回true时才可以调用其他获取配置项的get方法
 * @author li
 *
 */
public abstract class DevepDebugConfig {
	private static Logger logger = LoggerFactory.getLogger(DevepDebugConfig.class);
	private static boolean isDevepMode = false;
	
	private static Properties props = new Properties();
	static {
		// 最高优先级配置，class路径下面debug.properties
		
		InputStream is = DevepDebugConfig.class.getResourceAsStream("/debug.properties");
		if (is ==null){
			// 获取系统类型
			Properties sysProps = System.getProperties();
			String osName = (String) sysProps.get("os.name");
			boolean isWin = true;
			if (StringUtils.isNotEmpty(osName)){
				osName = osName.toLowerCase();
				if (osName.indexOf("windows")==-1){
					isWin = false;
				}
			} else {
				logger.info("os name: " + osName);
			}
			String configPath = null;
			if (isWin){
				// windows开发人员电脑
				configPath = "D:/domestore/DevepDebug/debug.properties";
			} else {
				// 测试主机，linux系统
				configPath = "/opt/DevepDebug/debug.properties";
			}
			File file = new File(configPath);
		
			if (file.exists() && file.isFile()) {
				try {
					is = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					// ignore
				}
			}
		}
		
		if (is != null){
			isDevepMode = true;
			try {
				props.load(is);
			} catch (IOException e) {
				logger.error("load develepment debug config file error", e);
			}finally {
				IOUtils.closeQuietly(is);
			}
		}
	}
	
	public static boolean isDevepMode(){
		return isDevepMode && props != null;
	}
	
	public static void main(String[] args) {
		System.out.println("Devep mode:" + isDevepMode);
	}
	
	/**
	 * 请求日志开关，默认为true
	 */
	private static final String CONFIG_REQUEST_LOGGER = "request.logger";
	
	/**
	 * mybatis打印执行sql，默认为true
	 */
	private static final String CONFIG_MYBATIS_PRINTSQL_LOGGER = "mybatis.printsql.logger";
	
	/**
	 * mybatis打印sql的执行时间，单位是ms，执行时间>=这个值的才会打印，默认为0
	 */
	private static final String CONFIG_MYBATIS_PRINTSQL_EXECUTETIME = "mybatis.printsql.executetime";
	
	public static boolean needRequestLogger(){
		String flagVal = props.getProperty(CONFIG_REQUEST_LOGGER, "true");
		return "true".equalsIgnoreCase(flagVal);
	}
	
	public static boolean needMybatisPrintSql(){
		String flagVal = props.getProperty(CONFIG_MYBATIS_PRINTSQL_LOGGER, "true");
		return "true".equalsIgnoreCase(flagVal);
	}
	
	public static int getMybatisPrintSqlExecTime(){
		String val = props.getProperty(CONFIG_MYBATIS_PRINTSQL_LOGGER, "0");
		if (Constants.PATTERN_NUM.matcher(val).matches()) {
			return Integer.parseInt(val);
		} else {
			return 0;
		}
	}
}
