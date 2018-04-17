package com.dome.sdkserver.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.dome.sdkserver.common.Constants;

public class Paginator {

	/**
	 * 起始记录。若start=-1将不分页，返回所有数据
	 */
	private int start;
	
	/**
	 * 页码
	 */
	private int pageNo = 1;
	
	private int pageSize =15;

	/**
	 * 默认查询带分页，传false时不带分页
	 */
	private boolean pagination = true;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public boolean isPagination() {
		return pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	@Override
	public String toString() {
		return "Paginator [start=" + start + ", pageSize=" + pageSize + "]";
	}
	
	private static final int DEFAULT_PAGENO=1;
	private static final int DEFAULT_PAGESIZE=15;
	/**
	 * 假分页
	 * 当前没有考虑pagination布尔变量，都会是要分页的
	 * request中字段分别为pageNo和pageSize
	 * @param list
	 * @param paginator
	 * @param request
	 * @return
	 */
	public static <E> List<E> page(List<E> list, Paginator paginator, HttpServletRequest request){
		if (CollectionUtils.isEmpty(list)) return new ArrayList<E>();
		paginator = pageParam(paginator, request);
		int pageNo=paginator.getPageNo();
		int pageSize=paginator.getPageSize();
		int totalCount =list.size();
		if (totalCount<=pageSize) return list;
		int start = (pageNo-1)*pageSize;
		if (start>totalCount) {
			start=0;
			pageNo=1;
		}
		int end=(start<=0 ? pageSize: start +pageSize);
		List<E> resultList = list.subList((start<=0 ? 0: start), (end>totalCount ? totalCount: end));
		return resultList;
	}
	
	/**
	 * 初始化分页参数pageNo和pageSize
	 * 若paginator没有赋值，从request中读取
	 * @param paginator
	 * @param request
	 * @return
	 */
	private static Paginator pageParam(Paginator paginator, HttpServletRequest request){
		int pageNo=DEFAULT_PAGENO;
		int pageSize=DEFAULT_PAGESIZE;
		
		if (paginator!=null){
			pageNo=paginator.getPageNo();
			pageSize = paginator.getPageSize();
			
		} else if (request !=null){
			String pageParam = request.getParameter("pageNo");
			if (StringUtils.isNotEmpty(pageParam) && Constants.PATTERN_NUM.matcher(pageParam).matches()){
				pageNo=Integer.parseInt(pageParam);
			}
			pageParam = request.getParameter("pageSize");
			if (StringUtils.isNotEmpty(pageParam) && Constants.PATTERN_NUM.matcher(pageParam).matches()){
				pageSize=Integer.parseInt(pageParam);
			}
		}
		if (request !=null){
			String pageNumStr=request.getParameter("pageNum");
			if (StringUtils.isNotEmpty(pageNumStr) &&Constants.PATTERN_NUM.matcher(pageNumStr).matches()){
				pageNo=Integer.parseInt(pageNumStr);
			}
		}
		
		if (pageNo<=0) pageNo=DEFAULT_PAGENO;
		// 页长不允许超过50
		if (pageSize<=0 || pageSize>50) pageSize=DEFAULT_PAGESIZE;
		if (paginator == null) paginator=new Paginator();
		paginator.setPageNo(pageNo);
		paginator.setPageSize(pageSize);
		return paginator;
	}
	
	/**
	 * 分页，根据总记录数和分页参数分析出start值
	 * @param paginator
	 * @param totalCount
	 * @param request
	 * @return
	 */
	public static Paginator handlePage(Paginator paginator, int totalCount, HttpServletRequest request){
		paginator = pageParam(paginator, request);
		int pageNo=paginator.getPageNo();
		int pageSize=paginator.getPageSize();
		if (totalCount<=pageSize) {
			// 重写用户误传的start
			paginator.setStart(0);
			return paginator;
		}
		int start = (pageNo-1)*pageSize;
		if (start>totalCount) {
			start=0;
			// pageNo=1;
		}
		paginator.setStart(start);
		
		return paginator;
	}
	public static void main(String[] args) {
		List<Integer> dataList= Arrays.asList(0,1,2,3,4,5,6,7,8,9,10);
		Paginator p = new Paginator();
		p.setPageNo(5);p.setPageSize(20);
		List<Integer> resultList=page(dataList, p, null);
		System.out.println("result: " + resultList);
		
	}
}
