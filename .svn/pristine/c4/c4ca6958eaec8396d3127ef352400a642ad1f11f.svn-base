package com.dome.sdkserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 描述：
 * 配置文件加载
 * @author hexiaoyi
 */
public class PropertiesUtil {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
    private static Properties properties = new Properties();
    
    private String filePath;
    

    public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	/**
     * 初始化方法,如果初始化不成功，环境无法启动
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void init()
    {
        InputStream in = PropertiesUtil.class.getResourceAsStream(filePath);
        
        if (in == null) {
        	log.info("properties not found");
        } else {
            if (!(in instanceof BufferedInputStream)) {
                in = new BufferedInputStream(in);
            }
            try {
                properties.load(in);
            } catch (IOException e) {
            	log.info("Error while processing properties file");
            }
            finally{
                if (null != in)
                {
                    try {
                        //关闭流
                        in.close();
                    } catch (IOException e) {
                        
                        //关闭流失败
                    	log.error("close stream failed", e);
                    }
                }
            }
        }
    }
    
  
    /**
     * get a properties value
     * 
     * @param key
     * @return
     */
    public String getString(String key) {
        return properties.getProperty(key);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * get a properties int value
     * 
     * @param key
     * @return
     */
    public int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public int getInt(String key,String defaultValue) {
        return Integer.parseInt(properties.getProperty(key, defaultValue));
    }
 
}
