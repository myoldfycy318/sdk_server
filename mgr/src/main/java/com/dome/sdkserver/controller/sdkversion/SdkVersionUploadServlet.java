package com.dome.sdkserver.controller.sdkversion;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.dome.sdkserver.autopack.FtpUploadTask;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.util.ServletUtil;

/**
 * sdk热更上传包体
 * Servlet implementation class SdkVersionUploadServlet
 */
public class SdkVersionUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
//  @Value("${ftp.downloadurl}")
  private String downloadDomain;
  
//  @Value("${ftp.workpath}")
  private String workPath;
  
  {
  	Properties prop=null;
	try {
		prop = PropertiesLoaderUtils.loadProperties(new ClassPathResource("pkg.properties"));
	} catch (IOException e) {
		log.error("加载ftp.properties配置文件出错", e);
	}
	if (prop!=null){
    	this.downloadDomain=prop.getProperty("ftp.downloadurl");
    	this.workPath=prop.getProperty("ftp.workpath");
	}
  }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SdkVersionUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!ServletFileUpload.isMultipartContent(request)) {
			// 按照传统方式获取数据
			return;
		}
		// 使用Apache文件上传组件处理文件上传步骤：
		// 1、创建一个DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 2、创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 解决上传文件名的中文乱码
		upload.setHeaderEncoding("UTF-8");
		// 3、判断提交上来的数据是否是上传表单的数据
		
		PrintWriter writer = response.getWriter();
		// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {

				} else {// 如果fileitem中封装的是上传文件
						// 得到上传的文件名称，
					String filename = item.getName();
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf(File.separator) + 1);
					long fileSize = item.getSize();
					if (fileSize>500*1024*1024){ //限制500M
						ServletUtil.writeErrorMsg("文件大小不能超过500M", writer);
						return;
					}
		        	/**
		        	 * sdk热更包会根据文件名进行校验，需要保留原有的文件名
		        	 */
		            String currentDate = DateUtil.dateToDateString(new Date(), "yyyyMMdd");
		            String uploadDir = workPath + currentDate;
		            log.info("sdk版本上传,路径为:{},版本名为:{}", uploadDir, filename);
		            FutureTask<Boolean> ftpUploadTask = new FutureTask<Boolean>(new FtpUploadTask(uploadDir, filename, item.getInputStream()));
					new Thread(ftpUploadTask).start();
					boolean flag;
					try {
						flag = ftpUploadTask.get(30, TimeUnit.MINUTES);
						if (!flag) {
			            	ServletUtil.writeErrorMsg("upload failed", writer);
			            	return;
			            }
			            // 保存
//			            multipartFile.transferTo(targetFile);
			            Map<String,String> map = new HashMap<String, String>();
			            map.put("sdkPath",downloadDomain + currentDate + File.separator +filename);
			            map.put("fileName",filename);
			            ServletUtil.writeJson(map, writer);
			            return;
					} catch (Exception e) {
						log.error("sdk版本:"+filename+", 上传出错", e);
					}
		            
		            
				}
			}
		} catch (FileUploadException e) {
			log.error("解析上传文件流出错", e);
		}
		ServletUtil.writeErrorMsg("sdk热更文件上传失败", writer);
	}

}
