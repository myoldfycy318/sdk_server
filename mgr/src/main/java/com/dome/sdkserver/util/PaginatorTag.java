package com.dome.sdkserver.util;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.util.Assert;

@SuppressWarnings("unchecked")
public class PaginatorTag extends TagSupport {
	private static final long serialVersionUID = 110724376830612517L;
	
	public static final int FORSEE_SIZE = 5;
	private Map parameters;
	private String module;
	private String action;
	private String background;
	private boolean flag = false ; 
	public String jumppage ="";
	
	public String getJumppage() {
		return jumppage;
	}

	public void setJumppage(String jumppage) {
		this.jumppage = jumppage;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	
	public Map getParameters() {
		if (parameters == null) {
			parameters = new HashMap();
		}
		
		Map urlParamsMap = pageContext.getRequest().getParameterMap();
		
		if (urlParamsMap != null) {
			parameters.putAll(urlParamsMap);
		}
		
		return parameters;
	}
	
	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
	
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();

		try {
			int pageCount = getPageCount();
			int curPageCount = getCurPageCount();
			int pageNumber = getPageNumber();
			int itemCount = getItemCount();
			
			if (pageNumber <= 0) {
				pageNumber = 1;
			}
			
			if (pageNumber >= pageCount) {
				pageNumber = pageCount ;
			}
			
			renderPaginator(out, itemCount, pageNumber, pageCount, curPageCount, getParameters());
		} catch(IOException ioe) {
			throw new JspException(ioe);
		}

		clearParameters();
		return EVAL_PAGE;
	}
	
	protected void renderPaginator(
		Writer jspWriter,
		int itemCount,
		int pageNumber,
		int pageCount,
		int curPageCount,
		Map params) throws IOException {
		
		int prevPage  = 0;
		int nextPage  = 0;
		
		String tagContent = "记录";
		
		prevPage = (pageNumber > 1) ? (pageNumber - 1) : 1;//previous page
		nextPage = (pageNumber < pageCount )?(pageNumber + 1):pageCount;//next page
		if (pageCount <= 0 && itemCount == -1) {//If we got item count, show it.
			return;
		}
		
		MarkupWriter mw = new MarkupWriter(jspWriter);
		
		
		//首页
		mw.begin("span");//span
			mw.attribute("class", "first-page");
        	mw.attribute("onclick", "document.location='" + generatePaginationUrl(1, params) + "'");
		mw.end();
		//首页
	
//		��上一页
		mw.begin("span");
			mw.attribute("class", "prev-page");
        	mw.attribute("onclick", "document.location='" + generatePaginationUrl(prevPage, params) + "'");
	    mw.end();
	    
	    // 分页内容
	    mw.begin("span");
	    	mw.attribute("class", "pagination-sum");
		    	mw.write("&nbsp;第 " + pageNumber + "/" + pageCount
		    			+ " 页, 共 " + itemCount + " 条" + tagContent + ",本页 " + curPageCount + " 条");
        mw.end();
		
        mw.begin("span");
        	mw.attribute("class", "next-page");
        	mw.attribute("onclick", "document.location='" + generatePaginationUrl(nextPage, params) + "'");
	    mw.end();//��下一页

	    mw.begin("span");
	    	mw.attribute("class", "last-page");
        	mw.attribute("onclick", "document.location='" + generatePaginationUrl(pageCount, params) + "'");
        mw.end();//末页
	       
        //��分页定位
        mw.begin("span");
        	mw.attribute("class", "go-to-page-sum");
        	mw.write("定位到第：");
        		
			mw.begin("input");
				mw.attribute("id","jumpToPageNum");
				mw.attribute("type","text");
				mw.attribute("size","10");
				mw.attribute("maxlength","10");
				mw.attribute("value", String.valueOf(pageNumber));
				mw.attribute("align","middle");
				mw.attribute("onkeyup", "if(event.keyCode == 13) onPaginatorJumpPage('" + generatePaginationUrl(-1, params) + "')");
				mw.attribute("Onkeypress","onKeyPress()");
				mw.attribute("Onkeydown","onKeyPress()");
				mw.attribute("onblur","onBlur()");
		    mw.end();
		    
		    mw.write("页");
		mw.end();
		
		mw.begin("span");
			mw.attribute("class", "go-to-page");
			mw.attribute("onclick", "onPaginatorJumpPage('" + generatePaginationUrl(-1, params) + "')");
	    mw.end();//span
	    String  enterKeyScript = "function onPaginatorJumpPage(strUrl) {"
			  + " var pageNumObj = document.getElementById('jumpToPageNum');"
			  + " var pageNum = parseInt(pageNumObj.value);"
			  + " if (pageNum == null || isNaN(pageNum)) {"
			  + "	alert('跳转页输入不正确！');"
			  + "		pageNumObj.value = '';"    
			  + "	return;"
			  + "}"
			  + " if (pageNum>2147483647) {"
			  + "	(function($){"
			  + "		$(function(){"    
			  + "	$.ss.frameMessageBox({"
			  + "		title: '提示',"
			  + "		messageTpye: 'mInfo',"
			  + "		messageCode: 'G1003'"
			  + "	});"
			  + "	return;	"
			  + "	});	"
			  + "	})(jQuery);	"
			  + "	return;	"
			  + "		pageNumObj.value = '';"    
			  + "}"
			  + " if (pageNum != '' && pageNum !='0') {"
			  + "	var len = new String(strUrl).indexOf('pageNumber');"
			  + "	var strloc = '';"
			  + "	if(len == -1) {"
			  + "		var flag = new String(strUrl).indexOf('?');"
			  + "		if(flag==-1) {"
			  + "			strloc = strUrl+'?pageNumber='+pageNum;"
			  + "			}"
			  + "		else {"
			  + "			strloc = strUrl+'&pageNumber='+pageNum;"
			  + "		}"
			  + "	}"
			  + "	else {"
			  + "		var ht = 'pageNumber='+pageNum;"
			  + "		var a = /pageNumber=([0-9]+)/;"
			  + "		strloc = new String(strUrl).replace(a, ht);"
			  + "	}"
			  + "	document.location = strloc;"
			  + "}"
			  + "}";
	    String getEvent = "function getEvent(){"
	    	  + " if(document.all)    return window.event;"
	    	  + " func=getEvent.caller;"
	    	  + "  while(func!=null){"
	    	  + " 		var arg0=func.arguments[0];"
	    	  + " if(arg0){if((arg0.constructor==Event || arg0.constructor ==MouseEvent) || (typeof(arg0)==\"object\" && arg0.preventDefault && arg0.stopPropagation)){return arg0;} }"
	    	  + "	func=func.caller;"
	    	  + "	}"
	    	  + "return null;"
	    	  + "}";
	    String onKeyPress =" function onKeyPress(){;var evt = getEvent();"
	    	  + " if(window.event) { "
	    	  + " if( !((evt.keyCode >= 48 && evt.keyCode <= 57) ||(evt.keyCode >= 96 && evt.keyCode <= 105) ||(evt.keyCode ==8) )){"
	    	  + " evt.returnValue = false;"
	    	  + " }"
	    	  + " }else{"
	    	  + " if( !((evt.which >= 48 && evt.which <= 57) || (evt.keyCode >= 96 && evt.keyCode <= 105) ||(evt.keyCode ==8) )){"
	    	  + " evt.preventDefault();"
	    	  + " } }}";
	    String onBlur = " function onBlur(){ "
	    	  + " var pageNumObj = document.getElementById('jumpToPageNum');"
			  + " var pageNum = parseInt(pageNumObj.value);"
			  + " if (pageNum==0) {"
			  + "		pageNumObj.value = '1';"    
			  + "	return;"
			  + "}}";
	mw.write("<script language=javascript>");
	mw.write(enterKeyScript);
	mw.write(getEvent);
	mw.write(onKeyPress);
	mw.write(onBlur);
	mw.write("</script>");
	}
	
	protected int getPageCount() {
		return getPaginationAttribute(PaginatorUtils.PAGE_COUNT_KEY);
	}
	
	protected int getCurPageCount() {
		return getPaginationAttribute(PaginatorUtils.CUR_PAGE_SIZE_KEY);
	}
	
	protected int getPageNumber() {
		return getPaginationAttribute(PaginatorUtils.PAGE_NUMBER_KEY);
	}
	
	protected int getItemCount() {
		return getPaginationAttribute(PaginatorUtils.ITEM_COUNT_KEY);
	}
	
	protected int getPaginationAttribute(String key) {
		Assert.notNull(key, "key can not be blank.");
		
		Object value = pageContext.findAttribute(key);
		
		if (value == null) {
			value = pageContext.getRequest().getParameter(key);
		}
		
		Assert.notNull(value, "Can not find the key[" + key + "].");
		
		if (value instanceof String) {
			return Integer.parseInt((String) value);
		} else if (value instanceof Integer) {
			return ((Integer) value).intValue();
		} else {
			throw new IllegalStateException(
				"The value can not be parsed as integer.");
		}
	}
	
	protected String generatePaginationUrl(int pageNumber, Map inParams) throws MalformedURLException{
		HashMap		params = new HashMap();

		if (inParams != null) {
			for (Iterator iter = inParams.keySet().iterator(); iter.hasNext();) {
				String	key = (String) iter.next();
				Object	value = inParams.get(key);
				
				if (!key.startsWith("org.apache.struts.")) {
					if (value instanceof String[] ) {
						String	ar[] = (String[]) value;
						
						if (ar.length == 1) {
							value = ar[0];
						}
					}
					params.put(key, value);
				}
			}
		}
		
		if (pageNumber >= 0) {
			params.put(PaginatorUtils.PAGE_NUMBER_KEY, new Integer(pageNumber));
		}
		return generaterUrl(params);
	}
	
	protected String generaterUrl(Map params) throws MalformedURLException {
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		String contextPath = req.getContextPath();
		String controller = getAction();
		//拼接URL+参数
		String url = contextPath + controller+"?";
		for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
			String	key = (String) iter.next();
			Object	value = params.get(key);
			String valueStr="";
			try{
				valueStr = java.net.URLEncoder.encode(value.toString(),"utf-8");  
			}catch(Exception e){//ignore exception
			}
			if(url.endsWith("?")){
				url += key+"="+valueStr;
			}else{
				url += "&"+key+"="+valueStr;
			}
			
		}
		return url;//"<sp:url value='"+url+"'>";
	}
	
	public void release() {
		super.release();
		
		clearParameters();
	}

	private void clearParameters() {
		if (parameters != null) {
			parameters.clear();
			parameters = null;
		}
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
