package com.dome.sdkserver.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	
	protected final static Logger log = LoggerFactory.getLogger(DomUtil.class);
	
	 /**
     * 读取文件
     */
   public static String readFileByLines(String filePath) {
       File file = new File(filePath);
       BufferedReader reader = null;
       StringBuffer buffer = new StringBuffer();
       try {
           reader = new BufferedReader(new FileReader(file));
           String tempString = null;
           int line = 1;
           // 一次读入一行，直到读入null为文件结束
           while ((tempString = reader.readLine()) != null) {
               // 显示行号
               System.out.println("line " + line + ": " + tempString);
               buffer.append(tempString);
               line++;
           }
           reader.close();
       } catch (IOException e) {
    	   log.error("读取文件失败 {} ,原因 {} ",filePath,e.getMessage());
       } finally {
           if (reader != null) {
               try {
                   reader.close();
               } catch (IOException e1) {
               }
           }
       }
       return buffer.toString();
   }
   
   /**
    * 读取文件
    * @param is
    * @return
    */
   public static String readFileToString(String filePath) {
	   InputStream is = null;
	   StringBuilder sb = new StringBuilder();  
	   try {
		    is = PropertiesUtil.class.getResourceAsStream(filePath);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));    
	          
	        String line = null;    
            
            while ((line = reader.readLine()) != null) {    
                sb.append(line);    
            }    
        } catch (IOException e) {    
        	log.error("读取文件失败 {} ,原因 {} ",filePath,e.getMessage());   
        } finally {
        	if(is != null){
	            try {    
	                is.close();    
	            } catch (IOException e) {    
	            }    
        	}
        }    
        return sb.toString();    
    }    
}
