package com.dome.sdkserver.bo;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletRequest;

public class QueryPageEntity<T> {

    private static final String PAGE_NO = "pageNo";
    private static final String PGAE_SIZE = "pageSize";
    private static final String IS_PAGE = "isPage";

    private Integer pageNo = 1;
    private Integer pageSize = 15;
    private Boolean isPage = true;
    private T t;

    public QueryPageEntity<?> initQueryPageEntity(HttpServletRequest request, T t) {
        QueryPageEntity entity = new QueryPageEntity();
        String pageNo = request.getParameter(PAGE_NO);
        if (!StringUtils.isBlank(pageNo)) {
            entity.pageNo = Integer.valueOf(pageNo);
        }
        String pageSize = request.getParameter(PGAE_SIZE);
        if (!StringUtils.isBlank(pageSize)) {
            entity.pageSize = Integer.valueOf(pageSize);
        }
        String isPage = request.getParameter(IS_PAGE);
        if (!StringUtils.isBlank(isPage) || "true".equals(isPage) || "false".equals(isPage)) {
            entity.isPage = Boolean.valueOf(isPage);
        }
        entity.t = t;
        return entity;
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

    public Boolean getPage() {
        return isPage;
    }

    public void setPage(Boolean page) {
        this.isPage = page;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
