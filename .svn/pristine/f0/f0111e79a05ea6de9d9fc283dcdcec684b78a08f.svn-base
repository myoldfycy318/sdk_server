package com.dome.sdkserver.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dome.sdkserver.constants.BizParamResponseEnum;
import com.dome.sdkserver.util.ServletUtil;
import com.dome.sdkserver.util.UploadUtil;
import com.dome.sdkserver.web.util.AppUtil;

/**
 * 开放平台图片上传类
 * 请求路径：/merchantapp/v1/uploadfile
 * 之前遇到下面的异常，没找到原因，故使用原始方式解决文件上件上传
 * 
 * INFO: Initializing Spring FrameworkServlet 'mvc'
Sep 01, 2016 12:24:28 PM org.apache.catalina.core.StandardWrapperValve invoke
SEVERE: Servlet.service() for servlet [mvc] in context with path [] threw exception [Handler processing failed; nested exception is java.lang.StackOverflowError] with root cause
java.lang.StackOverflowError
        at java.lang.reflect.Constructor.<init>(Constructor.java:116)
        at java.lang.reflect.Constructor.copy(Constructor.java:140)
        at java.lang.reflect.ReflectAccess.copyConstructor(ReflectAccess.java:144)
        at sun.reflect.ReflectionFactory.copyConstructor(ReflectionFactory.java:314)
        at java.lang.Class.getConstructor0(Class.java:2889)
        at java.lang.Class.getConstructor(Class.java:1723)
        at java.lang.reflect.Proxy.newProxyInstance(Proxy.java:744)
        at sun.reflect.annotation.AnnotationParser.annotationForMap(AnnotationParser.java:300)
        at sun.reflect.annotation.AnnotationParser.parseAnnotation2(AnnotationParser.java:290)
        at sun.reflect.annotation.AnnotationParser.parseAnnotations2(AnnotationParser.java:117)
        at sun.reflect.annotation.AnnotationParser.parseAnnotations(AnnotationParser.java:70)
        at java.lang.reflect.Method.declaredAnnotations(Method.java:714)
        at java.lang.reflect.Method.getAnnotation(Method.java:700)
        at com.alibaba.fastjson.util.TypeUtils.computeGetters(TypeUtils.java:998)
        at com.alibaba.fastjson.util.TypeUtils.computeGetters(TypeUtils.java:967)
        at com.alibaba.fastjson.JSON.toJSON(JSON.java:681)
        at com.alibaba.fastjson.JSON.toJSON(JSON.java:615)
        at com.alibaba.fastjson.JSON.toJSON(JSON.java:687)
        at com.alibaba.fastjson.JSON.toJSON(JSON.java:615)
        at com.alibaba.fastjson.JSON.toJSON(JSON.java:687)

 */
public class PicUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * 上传图片限制为10M
     */
    private static final int imgMaxFileSize = 10 * 1024 * 1024;
    
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PicUploadServlet() {
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
					// 上传的图片不能超过10M
					if (fileSize > imgMaxFileSize) {
						ServletUtil.writeErrorMsg("图片大小限制为10M以下", writer);
						return;
					}
					String checkResult = AppUtil.checkPicFormat(filename); // 图片格式校验
					if (StringUtils.isNotEmpty(checkResult)) {
						ServletUtil.writeErrorMsg(
								BizParamResponseEnum.IMAGE_FORMAT_ILLEGAL.getResponeMsg(),
								writer);
						return;
					} else {
						ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
						try {
							BufferedImage bufferedFile = ImageIO.read(item
									.getInputStream());
							ImageIO.write(bufferedFile, "png", byteArray);
							String imgUrl = UploadUtil.upload(UUID.randomUUID()
									.toString(), byteArray.toByteArray());
							Map<String, Object> dataMap = new HashMap<String, Object>(
									4);
							dataMap.put("imgUrl", imgUrl);
							ServletUtil.writeJson(dataMap, writer);
							// 刪除缓存文件
							item.delete();
							return;
						} catch (IOException e) {
							log.error("上传图片出错", e);

						} finally {
							if (byteArray != null) {
								try {
									byteArray.close();
								} catch (IOException e) {
									log.error("关闭流出错", e);
								}
							}
						}
					}
				}
			}
		} catch (FileUploadException e) {
			log.error("解析上传文件流出错", e);
		}
		ServletUtil.writeErrorMsg("文件上传失败", writer);
			                
	}


}
