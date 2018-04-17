package com.dome.sdkserver.web.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dome.sdkserver.util.FileUtil;

/**
 * 
 * 描述：
 * 读取校验配置文件
 * @author hexiaoyi
 */
public class ReadTransValTool {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	/** fileName,filePath */
	private Map<String,String> fileMap;
	/** fileName,fileContent */
	private Map<String,String> contentMap;
	
	public void init(){
		if(fileMap != null){
			Iterator<Entry<String, String>> iter = fileMap.entrySet().iterator();
			contentMap = new HashMap<String,String>();
			while(iter.hasNext()){
				Map.Entry<String, String> entry = iter.next();
				String xml = FileUtil.readFileToString(entry.getValue());
				contentMap.put(entry.getKey(), xml);
			}
		}
	}
	
	public String getValXml(String fileName){
		return contentMap.get(fileName);
	}

	public Map<String, String> getFileMap() {
		return fileMap;
	}

	public void setFileMap(Map<String, String> fileMap) {
		this.fileMap = fileMap;
	}

	public Map<String, String> getContentMap() {
		return contentMap;
	}

	public void setContentMap(Map<String, String> contentMap) {
		this.contentMap = contentMap;
	}
}
