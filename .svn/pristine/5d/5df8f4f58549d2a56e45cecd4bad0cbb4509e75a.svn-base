package com.dome.sdkserver.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

public class PaginatorUtils {

	public static final int DEFAULT_PAGE_SIZE = 1;
	public static final boolean DEFAULT_SORT_ASC = true;
	
	public static final String PAGE_NUMBER_KEY = "pageNum";
	public static final String PAGE_COUNT_KEY = "pageCount";
	public static final String ITEM_COUNT_KEY	= "itemCount";
	public static final String CUR_PAGE_SIZE_KEY = "curPageSize";
	
	
	public static final String SORT_COL_KEY = "sortCol";
	public static final String SORT_ASC_KEY = "sortAsc";
	
	private int pageSize;
	private int pageNumber;
	
	public PaginatorUtils() {
		init(DEFAULT_PAGE_SIZE, null, DEFAULT_SORT_ASC);
	}
	
	public PaginatorUtils(int pageSize) {
		init(pageSize, null, DEFAULT_SORT_ASC);
	}
	
	public PaginatorUtils(
		int pageSize, 
		String defaultSortColumn,
		boolean sortAsc) {
		init(pageSize, defaultSortColumn, sortAsc);
	}
	
	private void init(
		int pageSize,
		String defaultSortColumn,
		boolean defaultSortAsc) {
		Assert.isTrue(pageSize > 0, "pageSize must be greater than zero.");
		this.pageSize = pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public PaginatorUtils executePage(HttpServletRequest request,int totalCount){
		PaginatorUtils baseController = new PaginatorUtils();
		int pageNumber = getPageNumber(request);
		int itemCount = totalCount;
		int pageSize = getPageSize();
		int pageCount;
		int curPageSize;
		
		if (itemCount != -1) {
			pageCount = itemCount / pageSize + (itemCount % pageSize > 0 ? 1 : 0);
		}else{
			pageCount = 0;
		}
		
		if (pageNumber <= 0) {
			pageNumber = 1;
		}
		else if (pageNumber > pageCount) {
			pageNumber = pageCount;
		}
		
		if (pageNumber < pageCount) {
			curPageSize = pageSize;
		}else {
			curPageSize = itemCount % pageSize;
			if (curPageSize == 0 && itemCount > 0) {
				curPageSize = pageSize;
			}
		}
		request.setAttribute(PAGE_NUMBER_KEY, new Integer(pageNumber));
		request.setAttribute(PAGE_COUNT_KEY, new Integer(pageCount));
		request.setAttribute(ITEM_COUNT_KEY, new Integer(itemCount));
		request.setAttribute(CUR_PAGE_SIZE_KEY, new Integer(curPageSize));
		baseController.setPageNumber(pageNumber);
		baseController.setPageSize(pageSize);
		return baseController;
	}	
	
	public int getPageNumber(HttpServletRequest request) {
		Object value = request.getAttribute(PAGE_NUMBER_KEY);
		
		if (value == null) {
			value = request.getParameter(PAGE_NUMBER_KEY);
		}
		if (value == null) value = request.getParameter("pageNo");
		if (value== null || "".equals(value)) return 1; // 默认是第一页
		
		if (value instanceof String) {
			int pNum = 1;
			try {
				pNum = Integer.parseInt((String) value);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
			return pNum;
		} else if (value instanceof Integer) {
			return ((Integer) value).intValue();
		} else {
			return 1;
		}
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
    public static String getQuerySql(String querySql, int beginIndex, int endinIndex){
    	String sql = querySql +"LIMIT "+ endinIndex + " OFFSET "+beginIndex;
    	return sql;
    }
    
    public static int getStart(PaginatorUtils paginatorUtils){
    	if (paginatorUtils.getPageNumber() == 0)
			paginatorUtils.setPageNumber(1);
		int start = (paginatorUtils.getPageNumber() - 1) * paginatorUtils.getPageSize();
		return start;
    }
}
