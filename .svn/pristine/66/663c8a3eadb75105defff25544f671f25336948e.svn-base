package com.dome.sdkserver.controller.pkgmanage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dome.sdkserver.autopack.FtpUploadTask;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.pkgmanage.Pkg;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.service.ChargePointService;
import com.dome.sdkserver.service.MerchantAppService;
import com.dome.sdkserver.service.PkgService;
import com.dome.sdkserver.util.HanziToPinyinUtils;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.sdkserver.bo.PkgVo;

@Controller
@RequestMapping("/pkgmanage")
public class PkgController extends BaseController{

    @Resource
	private PropertiesUtil domainConfig;
    
    @Resource
    private PkgService pkgServiceImpl ;
    
    @Resource
	private MerchantAppService merchantAppServiceImpl;
    
    @Resource
	private ChargePointService chargePointServiceImpl;
    
    //private String tempDir = System.getProperty("java.io.tmpdir");
    
    private static Set<String> pkgPostfixSet = new HashSet<String>();
    
//    /**
//     * 需要在mvc中加载pkg.properties，Controller类中才能获取到配置
//     */
//    @Value("${domesdk.pkg.download.domain}")
//    private String downloadDomain;
//    
    @Value("${domesdk.pkg.upload.path}")
    private String uploadPath;
    
    @Value("${ftp.hostname}")
    private String hostname;
    
    @Value("${ftp.port}")
    private int port;
    
    @Value("${ftp.username}")
    private String username;
    
    @Value("${ftp.password}")
    private String password;
    
    @Value("${ftp.downloadurl}")
    private String downloadDomain;
    
    @Value("${ftp.workpath}")
    private String workPath;
    
    static {
    	pkgPostfixSet.add("apk");
    	pkgPostfixSet.add("zip");
    	pkgPostfixSet.add("rar");
    }
    /**
     * 包体限制为500M
     */
    private static final int pkgMaxFileSize = 500 * 1024 * 1024;
    
    @RequestMapping("/cancelUpload")
    @ResponseBody
    public AjaxResult cancelUpload(HttpServletRequest req){
//    	HttpSession session = req.getSession();
//    	Object obj = session.getAttribute("uploadFileBos");
//    	if (obj != null) {
//    		session.removeAttribute("uploadFileBos");
//    		@SuppressWarnings({"unused", "resource" })
//			BufferedInputStream bis = (BufferedInputStream) obj;
//    		bis = null;
//    		return AjaxResult.success();
//    	} else {
//    		return AjaxResult.failed("文件已上传完成，不需要取消上传！");
//    	}
    	// 无法取消上传
    	return AjaxResult.success();
    	
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
//    @RequestMapping("/upload")
//    @ResponseBody
	public AjaxResult upload(String appCode, @RequestParam MultipartFile pkg, HttpServletRequest req){
		if (pkg == null){
			return AjaxResult.failed("系统异常");
		}
		String fileName = pkg.getOriginalFilename();
		if (StringUtils.isEmpty(fileName)) {
			return AjaxResult.failed("包体文件名为空");
		}
//		if (!validateFilename(fileName)) {
//			return AjaxResult.failed("包体文件名不能包含中文字符，只能为英文字符和数字、减号、下划线、小数点等。");
//		}
		int index = fileName.lastIndexOf(".");
		String postfixName = fileName.substring(index + 1);
		if (!pkgPostfixSet.contains(postfixName.toLowerCase())) {
			return AjaxResult.failed("包体文件扩展名" + postfixName + "不对，只能为apk、zip、rar。");
		}
		if (pkg.getSize() > pkgMaxFileSize) {
			return AjaxResult.failed("包体文件大小不能超过500M");
		}

		String filenamePrefix = fileName.substring(0, index);
		// 包体文件名不能超过50个字符
		if (filenamePrefix.length()>FILENAME_SIZE){
			return AjaxResult.failed("包体文件名不能超过" + Integer.toString(FILENAME_SIZE)+"个字符，不包括扩展名");
		}
		String errorMsg = validateApp(appCode);
		if (errorMsg != null) return AjaxResult.failed(errorMsg);
		
		// 将文件名中的中文转换为拼音，截取前面的30个字符
		
		String pinyinFileName = HanziToPinyinUtils.getPingYin(filenamePrefix);
		pinyinFileName=pinyinFileName.replaceAll("[^\\w-.]*", ""); // 替换掉特殊字符，除字母、数字减号、下划线、小数点外
		String finalFileName = (pinyinFileName.length()>30 ? pinyinFileName.substring(0, 30) :pinyinFileName) +"_"+System.currentTimeMillis() +fileName.substring(index);
		String uploadDir = this.workPath + appCode;
//		File f = new File(uploadDir);
//		if (!f.exists()) f.mkdirs();
		
		// 记录包体开始上传的时间
		Date d = new Date();
		try {
			// 采用带回调的多线程任务，复制流和上传包体到ftp同时进行节省上传文件的时间
			// ExecutorService service = Executors.newFixedThreadPool(2);避免创建线程池的开销
			// 已确认pkg.getInputStream()每次调用获取的流对象不是同一个Object，因此可以两个线程分别处理。参考org.apache.commons.fileupload.disk.DiskFileItem.getInputStream()
			FutureTask<Boolean> ftpUploadTask = new FutureTask<Boolean>(new FtpUploadTask(uploadDir, finalFileName, pkg.getInputStream()));
			new Thread(ftpUploadTask).start();
			String localPkgDir=uploadPath + appCode;
			FutureTask<Boolean> writeFileTask = new FutureTask<Boolean>(new WriteFileTask(localPkgDir, finalFileName, pkg.getInputStream()));
			new Thread(writeFileTask).start();
			List<FutureTask<Boolean>> resultList = new ArrayList<FutureTask<Boolean>>();
			resultList.add(ftpUploadTask);
			resultList.add(writeFileTask);
			int i=0;
			for (Future<Boolean> result : resultList){
				// 30分钟若没有处理完，会报超时异常
				boolean success = result.get(30, TimeUnit.MINUTES);
				// 上传到ftp失败，删除主站目录下的文件
				if (!success && i==0){
					File localPkgFile= new File(localPkgDir, finalFileName);
					if (localPkgFile.exists()){
						localPkgFile.delete();
					}
				}
				if (!success){
					return AjaxResult.failed("上传原始包不成功，请重新上传");
				}
				i++;
			}
			
//			boolean flag = FtpUtil.uploadFile(hostname, port, username, password, uploadDir, finalFileName, pkg.getInputStream());
//			if (!flag) {
//				return AjaxResult.failed("上传失败");
//			}
//			//pkg.transferTo(dest);
//			// 需要解析sdk版本号，包体需要在主站上保存一份，后期可以集中删除
//			savePkg(pkg.getInputStream(), appCode, uploadDir, finalFileName, req);
		} catch (Exception e) {
			log.error("上传包体出错，包体文件名：" + finalFileName, e);
			return AjaxResult.failed("上传原始包不成功，请重新上传");
		}
		// 只有包体上传成功才会存库
		Pkg p = new Pkg();
		p.setAppCode(appCode);
		p.setName(fileName.substring(0, index));
		p.setFileName(fileName);
		p.setFileSize(getFileSize(pkg.getSize()));
		p.setUploadTime(d);
		p.setPhysicalFilePath(uploadPath + appCode + File.separator + finalFileName);
		//log.info("pkg physical path: {}", uploadDir + finalFileName);
		// 用户下载使用的是cdn URL，需要nginx中配置资源文件静态映射
		String downloadUrl = downloadDomain + appCode + File.separator
				+ finalFileName;
		p.setUploadPath(downloadUrl);
		try {
			MerchantAppInfo app = merchantAppServiceImpl.queryApp(appCode);
			addUserInfo(app, req);
			pkgServiceImpl.upload(app, p);
			HttpSession session = req.getSession();
			Object obj = session.getAttribute("uploadFileBos");
	    	if (obj != null) {
	    		session.removeAttribute("uploadFileBos");
	    	}
			return AjaxResult.success();
		} catch (NullPointerException e){
			log.info("上传文件出错，可能是用户取消了上传文件", e);
			return AjaxResult.success(null, "用户取消了上传文件");
		} catch (Exception e) {
			log.error("记录上传原始包数据出错，包体文件名：" + fileName, e);
		}
		return AjaxResult.failed("上传原始包没有成功，请联系管理员");
	}
    
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
	
    @RequestMapping("/view")
    @ResponseBody
    public AjaxResult queryPkg(String appCode){
    	Pkg p = pkgServiceImpl.query(appCode);
    	if (p == null) {
    		return AjaxResult.failed("应用没有上传过包体");
    	}
    	// 封装PkgVo
    	
    	return AjaxResult.success(generatePkgVo(p));
    }
    
    // 转换为视图层PkgVo对象
    private PkgVo generatePkgVo(Pkg pkg){
    	PkgVo vo = new PkgVo();
    	BeanUtils.copyProperties(pkg, vo);
    	vo.setDownloadUrl(pkg.getUploadPath());
    	return vo;
    }
    
//    // 生成上传后的文件名，加上时间戳
//    private String generateUploadFileName(String fileName){
//    	int index = fileName.lastIndexOf(".");
//    	if (index == -1) {
//    		return fileName + System.currentTimeMillis();
//    	}
//		String name = fileName.substring(0, index);
//    	String postfix = fileName.substring(index);
//		String finalFileName = System.currentTimeMillis() + postfix; // name + "_" + 
//		return finalFileName;
//		// 文件名中带有空格，导致通过url无法下载
//		//return finalFileName.replaceAll("\\s*|\t|\r|\n", "");
//    }
//    
//    // 保存文件输入流到硬盘
//	private void savePkg(InputStream input, String appCode, String filePath, String filename, HttpServletRequest req) throws Exception{
//    	BufferedOutputStream bos = null;
//    	BufferedInputStream bis = null;
//    	byte[] b = new byte[4084];
//    	try {
//    		bis = new BufferedInputStream(input);
//    		
//    		int len = 0;
//    		String localFilePath = uploadPath + appCode + File.separator + filename;
//    		File pkgFile = new File(uploadPath + appCode);
//    		if (!pkgFile.exists()){
//    			pkgFile.mkdirs();
//    		}
//			bos = new BufferedOutputStream(new FileOutputStream(localFilePath)); //修改为上传文件到FTP服务器
//    		
//			// 保存文件输出流，可以取消上传
//			HttpSession session = req.getSession();
//			session.setAttribute("uploadFileBis", bis);
//			while ((len = bis.read(b)) != -1) {
//				bos.write(b, 0, len);
//			}
//		} finally {
//			IOUtils.closeQuietly(bos);
//			IOUtils.closeQuietly(bis);
//		}
//    }

    private String validateApp(String appCode){
    	String msg = null;
    	if (StringUtils.isEmpty(appCode)) {
    		msg = "应用编码不能为空";
    	} else {
    		// 未接入、待接入和接入驳回状态不能上传文件
    		MerchantAppInfo app = merchantAppServiceImpl.queryApp(appCode);
    		if (app.getStatus() == AppStatusEnum.unaccess.getStatus() || app.getStatus() == AppStatusEnum.wait_access.getStatus()
    				|| app.getStatus() == AppStatusEnum.deny_access.getStatus()) {
    			msg = "应用接入后才能上传包体";
    		} else if (!chargePointServiceImpl.isAllAvailable(appCode)){ // 判断计费点是否都已生效
    			
    			msg = "存在计费点还没有审批完成";
    		}
    	}
    	return msg;
    }
    
    /**
     * 
     * appCode参数使用不到，过滤器校验当前登陆用户是否可以访问该应用，使用到
     * @param appCode
     * @param id
     * @return
     */
//    @RequestMapping("/download")
//    @ResponseBody
    public AjaxResult downloadPkg(String appCode, long id, HttpServletRequest req, HttpServletResponse resp){
    	if (id == 0) return AjaxResult.failed("加固包没有查询到");
    	Pkg p = pkgServiceImpl.queryById(id);
    	if (p == null || !appCode.equals(p.getAppCode())) {
    		return AjaxResult.failed("加固包没有查询到");
    	}
    	try {
			outputFile(resp, p);
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("下载加固包下载出错，appCode=" + appCode + ", id=" + id, e);
		}
    	return AjaxResult.failed("加固包下载不成功");
    }
    
    private void outputFile(HttpServletResponse resp, Pkg pkg) throws Exception{
    	BufferedInputStream bis = null;
    	BufferedOutputStream bos = null;
    	byte[] b = new byte[4084];
    	try {
    		int len = 0;
    		resp.setContentType("application/octet-stream");
			resp.setHeader("Content-disposition", "attachment; filename="+ pkg.getFileName());
			bis = new BufferedInputStream(new FileInputStream(pkg.getJiaguPath()));
			bos = new BufferedOutputStream(resp.getOutputStream());
			while ((len = bis.read(b)) != -1){
				bos.write(b, 0, len);
			}
		} finally {
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(bis);
		}
    }
    
    @RequestMapping("/canChange")
    @ResponseBody
    public AjaxResult canChangePkg(String appCode){
    	Pkg pkg = pkgServiceImpl.queryNew(appCode);
    	if (pkg == null) return AjaxResult.failed("没有查询到上传包体记录");
    	if ( pkg.getJiaguStatus() == 1) { // 最新的包体上传记录加固完成
    		return AjaxResult.success();
    	}
    	
    	return AjaxResult.failed("包体加固没有完成，不能变更包体");
    }
    
//    @RequestMapping("/updProgress")
//    @ResponseBody
    public AjaxResult getUploadProgress(HttpServletRequest req){
    	HttpSession session = req.getSession();
    	Object obj = session.getAttribute("updProgress");
    	if (obj != null) {
    		
    		return AjaxResult.success(obj);
    	}
    	return AjaxResult.failed("没有正在上传的文件或已上传完成");
    }
}
