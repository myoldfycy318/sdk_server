package com.dome.sdkserver.bq.util;

import java.util.List;

/**
 * 分页VO
 * 
 */
public class PageDto<T> {

    public static final Integer FIRST_PAGES = 1;

    public static final Integer DEF_PAGE_SIZE =10;

	/** 当前页 */
	private Integer pageNo = 1;

	/** 页面大小 */
	private Integer pageSize = DEF_PAGE_SIZE;

	/** 记录总数 */
	private Long totalCount = 0L;

	/** 列表 */
	private List<T> list;

    private Boolean isPage = false;
	
	public static PageDto<?> getInstance() {
		PageDto<?> pageDto = new PageDto();
		pageDto.setPageNo(1);
		pageDto.setPageSize(10);
		pageDto.setTotalCount(0L);
		return pageDto;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

    public Boolean getIsPage() {
        return isPage;
    }

    public void setIsPage(Boolean isPage) {
        this.isPage = isPage;
    }

    @Override
	public String toString() {
		return "PageDto [pageNo=" + pageNo + ", pageSize=" + pageSize + ", totalCount=" + totalCount + ", list=" + list
				+ "]";
	}

}
