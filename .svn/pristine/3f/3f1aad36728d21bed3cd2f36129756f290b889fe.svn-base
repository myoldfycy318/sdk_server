package com.dome.sdkserver.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CasPropertiesTag extends TagSupport{
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(CasPropertiesTag.class);
	
	private static String loginOutUrl = null;
	
	private static Properties cas = null;
	
	public void init(){
		if(cas == null){
			InputStream is = CasPropertiesTag.class.getResourceAsStream("/cas.properties");
			cas = new Properties();
			try {
				cas.load(is);
				loginOutUrl = cas.getProperty("logout.url");
			} catch (IOException e) {
				log.error("读取cas.properties时发生错误{}",e);
			}
		}
	}

	public int doEndTag() throws JspException {
		init();
		try {
			pageContext.getOut().write(loginOutUrl);
		} catch (IOException e) {
			log.error("标签输入页面失败,原因{}",e.getMessage());
		}
		return  1;
	}
	
}
