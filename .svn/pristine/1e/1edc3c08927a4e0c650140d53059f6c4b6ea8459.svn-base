package com.dome.sdkserver.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dome.sdkserver.bo.ResourceInfo;
import com.dome.sdkserver.constants.ResourceColumnEnum;
import com.dome.sdkserver.service.ResourceService;



/**
 * 前台商户应用controller
 * @author hexiaoyi
 *
 */
@Controller
@RequestMapping("/app")
public class AppStaticController extends BaseController{

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ResourceService resourceService;
	
	/**
	 * 钱宝服务
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	/**
	 * 钱宝服务登出
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(null);
		return "index";
	}
	
	/**
	 * 钱宝服务
	 * @return
	 */
	@RequestMapping("/home")
	public String home() {
		return "index";
	}
	
	/**
	 * 联合登录详情
	 * @return
	 */
	@RequestMapping("/oauthindex")
	public ModelAndView oauthIndex() {
		ModelAndView mav = new ModelAndView("union-login");
		List<ResourceInfo> resourceList = resourceService
				.getResourceInfoListByColumn(ResourceColumnEnum.OAUTH_COLUMN.getColumnCode());
		mav.addObject("resourceList", resourceList);
		return mav;
	}
	
	/**
	 * 统一支付详情
	 * @return
	 */
	@RequestMapping("/payindex")
	public ModelAndView payIndex() {
		ModelAndView mav = new ModelAndView("union-pay");
		List<ResourceInfo> resourceList = resourceService
				.getResourceInfoListByColumn(ResourceColumnEnum.PAY_COLUMN.getColumnCode());
		mav.addObject("resourceList", resourceList);
		return mav;
	}
	
	/**
	 * 联系我们
	 * @return
	 */
	@RequestMapping("/contactus")
	public ModelAndView contactUs() {
		ModelAndView mav = new ModelAndView("contact-us");
		return mav;
	}
	
	/**
	 * 资源下载
	 * @return
	 */
	@RequestMapping("/resourcedownload")
	public void resourceDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resourceId = request.getParameter("resourceId");
		ResourceInfo resourceInfo = resourceService.getResourceInfoById(Integer.valueOf(resourceId));
		response.setContentType("text/html;charset=UTF-8"); 
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		request.setCharacterEncoding("UTF-8");
		File f =  new File(resourceInfo.getResourceUrl());
		String fileName = f.getName();
		try {
			response.addHeader("content-type", "application/x-msdownload");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-disposition", "attachment; filename="+java.net.URLEncoder.encode(fileName, "UTF-8"));
			response.setHeader("Content-Length",String.valueOf(f.length()));
			in = new BufferedInputStream(new FileInputStream(f));
			out = new BufferedOutputStream(response.getOutputStream());
			byte[] data = new byte[1024];
			int len = 0;
			while (-1 != (len=in.read(data, 0, data.length))) {
				out.write(data, 0, len);
			}
			log.debug("下载文件:"+f.getPath());
		} catch (Exception e) {
			log.error("下载文件出错:"+f.getPath(), e);
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}
