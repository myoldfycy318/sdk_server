package com.dome.sdkserver.controller;


import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.service.MerchantInfoAuditService;
import com.dome.store.user.BaUser;


public abstract class BaseController {
	
	protected Logger log = LoggerFactory.getLogger(BaseController.class);

	@Resource
	MerchantInfoAuditService merchantInfoService;
	
	private static final String KEY_USER = "USER";
	
	protected String getCurrentUsername(HttpServletRequest request) {
		BaUser user = getLoginUser(request);
		return user.getUsername();
	}
	
	protected long getCurrentUserId(HttpServletRequest request) {
		BaUser user = getLoginUser(request);
		return user.getId();
	}
	
	/**
	 * 
	 * session中的用户request.getSession().getAttribute("USER")
	 * @param request
	 * @return
	 */
	protected BaUser getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(KEY_USER);
		if (obj !=null && obj instanceof BaUser) {
			BaUser user = (BaUser) obj;
			return user;
		}
		throw new RuntimeException("user not login");
	}
	
	public void addUserInfo(MerchantAppInfo app, HttpServletRequest request){
		BaUser user = getLoginUser(request);
		app.setOperUserId(user.getId());
		app.setOperUser(user.getUsername());
	}
	
	private static final Pattern numPattern = Pattern.compile("\\d{1,3}");
	
	protected int getPageSize(ServletRequest request){
		String pageSizeStr = request.getParameter("pageSize");
		int pageSize = 0;
		if (StringUtils.isNotEmpty(pageSizeStr) && numPattern.matcher(pageSizeStr).matches()){
			int pSize = Integer.parseInt(pageSizeStr);
			if (pSize <1 || pSize > 100){
				pageSize = 15;
			} else {
				pageSize = pSize;
			}
		} else {
			pageSize = 15;
		}
		return pageSize;
	}
}
