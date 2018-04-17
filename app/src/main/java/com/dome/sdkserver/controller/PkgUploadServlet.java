package com.dome.sdkserver.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.dome.sdkserver.autopack.FtpUploadTask;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.pkgmanage.Pkg;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.service.AppService;
import com.dome.sdkserver.service.ChargePointService;
import com.dome.sdkserver.service.PkgService;
import com.dome.sdkserver.util.DomeSdkUtils;
import com.dome.sdkserver.util.HanziToPinyinUtils;
import com.dome.sdkserver.util.RedisUtil;
import com.dome.sdkserver.util.ServletUtil;
import com.dome.sdkserver.view.AjaxResult;

/**
 * Servlet implementation class PkgUploadServlet
 */
public class PkgUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
    
	private static Set<String> pkgPostfixSet = new HashSet<String>();
	
    /**
     * 包体限制为500M
     */
    private static final int pkgMaxFileSize = 500 * 1024 * 1024;
    
	static {
    	pkgPostfixSet.add("apk");
    	pkgPostfixSet.add("zip");
    	pkgPostfixSet.add("rar");
    }
	/**
	 * servlet使用@Resource注入失败
	 */
    private RedisUtil redisUtil;
	
    private PkgService pkgServiceImpl ;
    
	private AppService appService;
    
	private ChargePointService chargePointServiceImpl;
	
//    @Value("${domesdk.pkg.upload.path}")
    private String uploadPath;
    
//    @Value("${ftp.downloadurl}")
    private String downloadDomain;
    
//    @Value("${ftp.workpath}")
    private String workPath;
    
    {
    	Properties prop=null;
    	Properties redisProp=null;
		try {
			prop = PropertiesLoaderUtils.loadProperties(new ClassPathResource("pkg.properties"));
			redisProp= PropertiesLoaderUtils.loadProperties(new ClassPathResource("redis.properties"));
		} catch (IOException e) {
			log.error("加载ftp.properties配置文件出错", e);
		}
		if (prop!=null){
	    	this.downloadDomain=prop.getProperty("ftp.downloadurl");
	    	this.workPath=prop.getProperty("ftp.workpath");
	    	this.uploadPath=prop.getProperty("domesdk.pkg.upload.path");
		}
		if (redisProp!=null){
			/**
			 * 		<property name="maxTotal" value="${redis.maxTotal}" />
			 *	    <property name="maxIdle" value="${redis.maxIdle}" />
			 *	    <property name="testOnBorrow" value="${redis.testOnBorrow}" />
			 */
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setMaxTotal(Integer.parseInt(redisProp.getProperty("redis.maxTotal")));
			jedisPoolConfig.setMaxIdle(Integer.parseInt(redisProp.getProperty("redis.maxIdle")));
			jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(redisProp.getProperty("redis.testOnBorrow")));
			JedisPool pool=new redis.clients.jedis.JedisPool(jedisPoolConfig, redisProp.getProperty("redis.ip"),
					Integer.parseInt(redisProp.getProperty("redis.port")));
			redisUtil = new RedisUtil();
			
			redisUtil.setPool(pool);
		}
    	
    	
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PkgUploadServlet() {
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
		// 上传文件入口，保证都初始化
		if (pkgServiceImpl==null){
			// 从applicationContext中获取
			HttpSession session =request.getSession();
			
			ApplicationContext context =WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
			pkgServiceImpl = context.getBean(PkgService.class);
			if (appService==null) appService=context.getBean(AppService.class);
			if (chargePointServiceImpl==null) chargePointServiceImpl=context.getBean(ChargePointService.class);
		}
		PrintWriter writer = response.getWriter();
		String appCode = request.getParameter("appCode");
		String errorMsg = validateApp(appCode);
		if (errorMsg != null) {
			ServletUtil.writeErrorMsg(errorMsg, writer);
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
		
		// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		String orginalFileName = null;
		try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {

				} else {// 如果fileitem中封装的是上传文件
						// 得到上传的文件名称，
					String fileName = item.getName();
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					orginalFileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
					fileName = orginalFileName;
//					if (!validateFilename(fileName)) {
//						ServletUtil.writeErrorMsg("包体文件名不能包含中文字符，只能为英文字符和数字、减号、下划线、小数点等。", writer);
//						return;
//					}
					int index = fileName.lastIndexOf(".");
					String postfixName = fileName.substring(index + 1);
					if (!pkgPostfixSet.contains(postfixName.toLowerCase())) {
						ServletUtil.writeErrorMsg("包体文件扩展名" + postfixName + "不对，只能为apk、zip、rar。", writer);
						return;
					}
					if (item.getSize() > pkgMaxFileSize) {
						ServletUtil.writeErrorMsg("包体大小不能超过500M", writer);
						return;
					}

					String filenamePrefix = fileName.substring(0, index);
					// 包体文件名不能超过50个字符
					if (filenamePrefix.length()>FILENAME_SIZE){
						ServletUtil.writeErrorMsg("包体文件名不能超过" + Integer.toString(FILENAME_SIZE)+"个字符，不包括扩展名", writer);
						return;
					}
					
					// 将文件名中的中文转换为拼音，截取前面的30个字符
					
					String pinyinFileName = HanziToPinyinUtils.getPingYin(filenamePrefix);
					pinyinFileName=pinyinFileName.replaceAll("[^\\w-.]*", ""); // 替换掉特殊字符，除字母、数字减号、下划线、小数点外
					String finalFileName = (pinyinFileName.length()>30 ? pinyinFileName.substring(0, 30) :pinyinFileName) +"_"+System.currentTimeMillis() +fileName.substring(index);
					String uploadDir = this.workPath + appCode;
			//		File f = new File(uploadDir);
			//		if (!f.exists()) f.mkdirs();
					
					// 记录包体开始上传的时间
					Date d = new Date();
					// 采用带回调的多线程任务，复制流和上传包体到ftp同时进行节省上传文件的时间
					// ExecutorService service = Executors.newFixedThreadPool(2);避免创建线程池的开销
					// 已确认pkg.getInputStream()每次调用获取的流对象不是同一个Object，因此可以两个线程分别处理。参考org.apache.commons.fileupload.disk.DiskFileItem.getInputStream()
					FutureTask<Boolean> ftpUploadTask = new FutureTask<Boolean>(new FtpUploadTask(uploadDir, finalFileName, item.getInputStream()));
					new Thread(ftpUploadTask).start();
					log.info("FtpUploadTask上传文件至ftp服务器");
					String localPkgDir=uploadPath + appCode;
//					FutureTask<Boolean> writeFileTask = new FutureTask<Boolean>(new WriteFileTask(localPkgDir, finalFileName, item.getInputStream()));
//					new Thread(writeFileTask).start();
					List<FutureTask<Boolean>> resultList = new ArrayList<FutureTask<Boolean>>();
					resultList.add(ftpUploadTask);
//					resultList.add(writeFileTask);
					int i=0;
					for (Future<Boolean> result : resultList){
						// 30分钟若没有处理完，会报超时异常
						boolean success = result.get(30, TimeUnit.HOURS);
						log.info("上传文件到ftp服务器结果为:"+success);
						// 上传到ftp失败，删除主站目录下的文件
						if (!success && i==0){
							File localPkgFile= new File(localPkgDir, finalFileName);
							if (localPkgFile.exists()){
								localPkgFile.delete();
							}
						}
						if (!success){
							ServletUtil.writeErrorMsg("上传原始包不成功，请重新上传", writer);
							return;
						}
						i++;
					}
					//result.get获取ftp上传结果有阻塞,FtpUploadTask执行完成时将流关闭了
					FutureTask<Boolean> writeFileTask = new FutureTask<Boolean>(new WriteFileTask(localPkgDir, finalFileName, item.getInputStream()));
					new Thread(writeFileTask).start();
					boolean success = writeFileTask.get(30, TimeUnit.HOURS);
					log.info("WriteFileTask复制");
					if (!success){
						File localPkgFile= new File(localPkgDir, finalFileName);
						if (localPkgFile.exists()){
							localPkgFile.delete();
						}
						ServletUtil.writeErrorMsg("上传原始包不成功，请重新上传", writer);
						return;
					}
					log.info("WriteFileTask复制结果:"+success);




		//			boolean flag = FtpUtil.uploadFile(hostname, port, username, password, uploadDir, finalFileName, pkg.getInputStream());
		//			if (!flag) {
		//				return AjaxResult.failed("上传失败");
		//			}
		//			//pkg.transferTo(dest);
		//			// 需要解析sdk版本号，包体需要在主站上保存一份，后期可以集中删除
		//			savePkg(pkg.getInputStream(), appCode, uploadDir, finalFileName, req);
			
					// 只有包体上传成功才会存库
					Pkg p = new Pkg();
					p.setAppCode(appCode);
					p.setName(fileName.substring(0, index));
					p.setFileName(fileName);
					p.setFileSize(getFileSize(item.getSize()));
					p.setUploadTime(d);
					p.setPhysicalFilePath(uploadPath + appCode + File.separator + finalFileName);
					//log.info("pkg physical path: {}", uploadDir + finalFileName);
					// 用户下载使用的是cdn URL，需要nginx中配置资源文件静态映射
					String downloadUrl = downloadDomain + appCode + File.separator
							+ finalFileName;
					p.setUploadPath(downloadUrl);
					MerchantAppInfo app = appService.selectApp(appCode);
					addUserInfo(app, request);
					pkgServiceImpl.upload(app, p);
					ServletUtil.writeJson(AjaxResult.success(), writer);
					return;
				}
			}
		} catch (Exception e) {
			log.error("上传包体出错，包体文件名：" + orginalFileName, e);
		}
		ServletUtil.writeErrorMsg("上传原始包没有成功，请联系管理员", writer);
	}
	
    
//    private static final Pattern FILENAME_PATTERN = Pattern.compile("^[\\w\\-\\.\\(\\)\\[\\]]+\\.[\\w]{1,6}$");
    
    private final int FILENAME_SIZE = 50;
    
    /**
     * 校验包体文件名。只允许是字母、数字和减号、下划线、小数点、小括号、中括号
     * @param filename
     * @return
     */
//    private static boolean validateFilename(String filename){
//    	return FILENAME_PATTERN.matcher(filename).matches();
//    }
    private class WriteFileTask implements Callable<Boolean>{
    	
    	private InputStream is;
    	
    	private String localPkgDir;
    	
    	private String filename;
    	
		public WriteFileTask(String localPkgDir, String filename, InputStream is) {
			super();
			this.localPkgDir = localPkgDir;
			this.filename = filename;
			this.is = is;
		}


		@Override
    	public Boolean call() throws Exception {
			File dirFile = new File(localPkgDir);
			if (!dirFile.exists()){
				dirFile.mkdirs();
			}
			// 方法中会关闭is
    		FileUtils.copyInputStreamToFile(is, new File(localPkgDir, filename));
    		return true;
    	}
    }
    private String getFileSize(long size){
    	String fileSize = null;
    	if (size < 1 * 1024 * 1024) { // 小于1M
    		fileSize = Math.round(size * 100/ 1024)/100.0d + "K";
    	} else {
    		fileSize = Math.round(size * 100/(1024*1024))/100.0d + "M";
    	}
    	return fileSize;
    }

    private String validateApp(String appCode){
    	String msg = null;
    	if (StringUtils.isEmpty(appCode)) {
    		msg = "应用编码不能为空";
    	} else {
    		// 未接入、待接入和接入驳回状态不能上传文件
    		MerchantAppInfo app = appService.selectApp(appCode);
    		if (app.getStatus() == AppStatusEnum.unaccess.getStatus() || app.getStatus() == AppStatusEnum.wait_access.getStatus()
    				|| app.getStatus() == AppStatusEnum.deny_access.getStatus()) {
    			msg = "应用接入后才能上传包体";
    		} else if (!chargePointServiceImpl.isAllAvailable(appCode)){ // 判断计费点是否都已生效

    			msg = "存在计费点还没有审批完成";
    		}
    	}
    	return msg;
    }
    
	public void addUserInfo(MerchantAppInfo app, HttpServletRequest request){
		User user=DomeSdkUtils.getLoginUserStatistic(request);
		if (user==null) throw new RuntimeException("user not login");
	
		long userId = Long.parseLong(user.getUserId());
		String userName = user.getLoginName();
		
		app.setOperUserId(userId);
		app.setOperUser(userName);
	}
}
