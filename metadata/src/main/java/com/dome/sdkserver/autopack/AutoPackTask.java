package com.dome.sdkserver.autopack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.util.CollectionUtils;

import brut.apktool.Main;

import com.dome.sdkserver.common.Constants;

/**
 * 自动打包流程
 * 1、检查主站服务器上有没有对应的安装包，若不存在，先从Ftp下载到自动打包目录/opt/download/autopack/appCode；若存在也会先复制到自动打包目录下
 * 考虑到Tomcat集群，用户上传包体可能只有一台服务器硬盘上有
 * 2、修改assets/DomeSdk/buildConfig.xml文件，写入渠道号
 * 3、上传到Ftp
 * 4、成功后，下载地址存入表中
 * 开放平台默认有宝玩和冰趣两个渠道。合作方申请上架时触发打包流程
 * 渠道商申请打包时也会触发打包流程
 * 
 * 
 * @author li
 *
 */
public class AutoPackTask implements Callable<PackResult> {
	private static Logger logger = LoggerFactory.getLogger("channelpkg");
	/**
	 * 共享的一个工厂对象
	 */
	private static FtpFactory factory= new FtpFactory();
	
	private String appCode;
	
	/**
	 * 包体ftp上传路径，不包括appCode层级，且要求是/结尾，需要到domestore或uploadPkg，
	 * 譬如/home/wwwroot/tdl.domegame.cn/uploadPkg/或/dl.domestore.cn/domestore/
	 */
	private String filePath;
	
	private String channelCode;
	
	/**
	 * System.getProperty("java.io.tmpdir")
	 * 测试发现在tomcat中部署应用，临时目录变成/opt/domesop_server/domesopba_server/tomcat_domesopba/temp/autopack/null/CHA0000027/TestDomeSDK(10003CHA000001).apk
	 * 默认情况下，Tomcat中catalina.sh中设置了 -Djava.io.tmpdir=\"$CATALINA_TMPDIR\" \
	 */
	private static final String autopack_tmp_dir = "/tmp" + File.separator + "autopack";
	
	static {
		File tmpDir = new File(autopack_tmp_dir);
		if (!tmpDir.exists()){
			tmpDir.mkdir();
		}
	}
//	@Autowired
//	private PkgService pkgService;
	
	/**
	 * 1 APP渠道包  2、游戏渠道包
	 */
	private int type;
	/**
	 * FTP服务器上的下载地址
	 */
	private String downloadUrl;
	public AutoPackTask(String appCode, String filePath, String channelCode,
			String downloadUrl, int type){
		this.appCode=appCode;
		this.filePath=filePath;
		this.channelCode=channelCode;
		this.downloadUrl=downloadUrl;
		this.type=type;
	}
	@Override
	public PackResult call() throws Exception {
//		// 查询应用的下载url，判断安装包是否在当前主站服务器上
//		Pkg pkg = pkgService.queryNew(appCode);
//		if (pkg==null){
//			return new PackResult(false, "包体没有上传, appCode:" + appCode, null);
//		}
		// 白包的下载url，格式类似http://dl.domestore.cn/uploadPkg/D0001319/1467862105869.zip
//		String downloadUrl = pkg.getUploadPath();
		String downloadUrl =this.downloadUrl;
		int index = downloadUrl.lastIndexOf("/");
		String filename = downloadUrl.substring(index+1);
		// linux对中文字符，特殊字符支持不好，譬如英文的括号，需要将特殊字符都替换掉(APP渠道包打包会遇到)
		final String newFileName = filename.replaceAll("[^\\w._]*", "");
		// 游戏包体上传到主站上的路径
		String filePath = FtpConfig.getUploadPath() + appCode + File.separator + filename;
		File uploadPkgFile = new File(filePath);
		
		String targetParentDir=autopack_tmp_dir + File.separator+ channelCode+ File.separator+ appCode;
		File parentDirFile = new File(targetParentDir);
		//考虑到减少下载次数，包体文件做了一次缓存
//		String dlApkCachDir = autopack_tmp_dir+File.separator +"download"+ File.separator+ appCode;
		File downloadDirFile = new File(targetParentDir, "download");
		File decodeDirFile = new File(parentDirFile, "decode");
		File buildDirFile = new File(parentDirFile, "build");
		File signDirFile = new File(parentDirFile, "sign");
		if (!parentDirFile.exists()) {
			parentDirFile.mkdirs();
		}
		// channelCode目录下面分别创建download decode build sign 四个目录，等处理完成后再分别删除
		if (!downloadDirFile.exists())	downloadDirFile.mkdirs();
		if (!decodeDirFile.exists())	decodeDirFile.mkdirs();
		if (!buildDirFile.exists())	buildDirFile.mkdirs();
		if (!signDirFile.exists())	signDirFile.mkdirs();
		
		String localFilePath=downloadDirFile.getAbsolutePath() + File.separator + newFileName;
		// APP渠道包不需要appCode层级文件夹，而游戏需要
		String relativeFilePath=this.filePath +(type==2 ? (appCode + File.separator) :"") + filename;
		FtpConnection conn = factory.createFtpConnection(true);
		if (!uploadPkgFile.exists()) {
			conn.changeWorkDirctory(FtpConfig.getPkgUploadPath());
			boolean flag=conn.download(relativeFilePath, localFilePath);
			if (!flag) return new PackResult(false, "下载包体出错", null);
			filePath=localFilePath;
		} else {
			// 主站上已存在，使用原有的包文件，减少复制包文件。复制包体到对应的渠道目录下，避免并发读取同一个文件
			//FileUtils.copyFileToDirectory(uploadPkgFile, downloadDirFile);
		}
		filename = newFileName;
		String signApkFile=null;
		ApkToolMain tool = new ApkToolMain();
		try {
			// 反编译
			StringBuilder command= new StringBuilder("apktool d -f ");
			command.append(filePath).append(" -o ").append(decodeDirFile.getAbsolutePath());
			execCommand(command.toString(), "Copying original files...");
//			String[] args=new String[]{"d", "-f", filePath, "-o", decodeDirFile.getAbsolutePath() };
//			Main.main(args);
//			tool.cmdDecode(new File(filePath), decodeDirFile);
			// 修改渠道号
			if (type==1){
//				decompAndroidManifest(decodeDirFile);
				modifyAndroidManifest(decodeDirFile, channelCode);
			} else if (type==2){
				String sdkXmlFilePath  = decodeDirFile.getAbsolutePath() + File.separator
						+ Constants.SDK_XMLPATH;
				writeChannelCode(sdkXmlFilePath, channelCode);
			}
			
			
			File buildApkFile = new File(buildDirFile, filename);
			// 编译
			command=new StringBuilder("apktool b -f ");
			command.append(decodeDirFile.getAbsolutePath()).append(" -o ")
			.append(buildApkFile.getAbsolutePath());
			execCommand(command.toString(), "Copying unknown files/dir...");
//			args=new String[]{"b", "-f", decodeDirFile.getAbsolutePath(), "-o", buildApkFile.getAbsolutePath() };
//			Main.main(args);
//			tool.cmdBuild(decodeDirFile, buildApkFile);
						
			// apk包签名
			signApkFile=signDirFile.getAbsolutePath() +File.separator+filename;
			String unsignApkFile=buildApkFile.getAbsolutePath();
			signApk(FtpConfig.getKeystore(), signApkFile, unsignApkFile,
					FtpConfig.getKeystoreAlias(), FtpConfig.getKeystoreSecret());
		} catch (Exception e) {
			logger.error("自动打渠道包出错", e);
			return new PackResult(false, e.getMessage(), null);
		}
		
		// 新apk包文件名仍取上传到FTP上面的文件名
		String path = FtpConfig.getAutoPkgUploadPath()+channelCode +"/" +appCode;
		// 上传到FTP上需要加上channelId和时间戳
		String finalUploadFileName = generateNewFileName(filename);
		long start=System.currentTimeMillis();
		logger.info("渠道打包开始上传包体，filename={}", finalUploadFileName);
		boolean uploadSuccess=conn.upload(path, finalUploadFileName, new File(signDirFile, filename));
		logger.info("渠道打包上传包体完成，上传结果uploadSuccess={}，filename={}，执行耗时：{}ms", uploadSuccess, finalUploadFileName,
				(System.currentTimeMillis()-start));
		conn.disconnect();
		if (uploadSuccess){
			downloadUrl=FtpConfig.getAutoPkgDownloadUrl()+channelCode +"/" +appCode +"/" + finalUploadFileName;
			FileUtils.deleteDirectory(parentDirFile);
			return new PackResult(true, "success", downloadUrl);
		}
		
		
		return new PackResult(false, "上传文件失败", null);
		
	}

	private String generateNewFileName(String filename){
		// 获取扩展名
		int dotIndex =filename.lastIndexOf(".");
		String postfixName = filename.substring(dotIndex);
		// 判断文件名中是否含_
		int index = filename.lastIndexOf("_");
		String oriFileName = null;
		if (index>0){
			oriFileName = filename.substring(0, index);
		} else {
			oriFileName=filename.substring(0, dotIndex);
		}
		// 拼上渠道号
		StringBuilder sb = new StringBuilder(oriFileName);
		sb.append("_").append(Long.parseLong(channelCode.replaceAll(Constants.CHANNEL_CODE_PREFIX, "")))
			.append("_").append(System.currentTimeMillis()).append(postfixName);
		return sb.toString();
	}
	/**
	 * apk包签名
	 * 命名示例：jarsigner -verbose -keystore /Users/dn0383/game.keystore  -signedjar /work/android_develop/decompile/apktool/test1.apk
	 *  /work/android_develop/decompile/apktool/test.apk game
	 */
	private void signApk(String keystoreFile, String signApkFile, String unsignApkFile, String keystoreAlias, String signSecret){
		
		StringBuilder command=new StringBuilder(255);
		command.append("jarsigner -verbose -keystore ").append(keystoreFile)
		.append(" -signedjar ").append(signApkFile).append(" ").append(unsignApkFile)
		.append(" ").append(keystoreAlias).append(" -storepass ").append(signSecret);
		// .jks签名需要加上 -keypass ***
		// jarsigner -verbose -keystore domekeystore.jks  -signedjar  test6_sign.apk test4.apk   domeappstore -storepass ***  -keypass ***
		String keyPassword = FtpConfig.getKeyPassword();
		if (StringUtils.isNotEmpty(keyPassword)){
			command.append("  -keypass ").append(keyPassword);
		}
//		logger.info("jarsigner comand: " +command.toString());
		BufferedReader reader =null;
		try {
			Runtime run =Runtime.getRuntime();
			long start = System.currentTimeMillis();
			Process process = run.exec(command.toString());
			logger.info("渠道打包开始apk签名，file={}", unsignApkFile);
//			int exitValue=-1;
//			try {
			// 这种方式程序会出现假死现象
//				exitValue = process.waitFor();
//			} catch (InterruptedException e) {
//				logger.error("apk证书签名等待jarsigner命令执行完成出错", e);
//			}
//			if (exitValue!=0){
//				logger.error("apk证书签名失败,unsignApkFile="+unsignApkFile);
				// 失败情况下打印输出信息
				InputStream is = process.getInputStream();
				reader = new BufferedReader(new InputStreamReader(is));
				// 打包日志中有"jar signed."说明打包成功
				String msg=null;
				boolean signSuccess=false;
				while ((msg=reader.readLine())!=null){
					//logger.info("jar sign: " +msg);
					if (msg.contains("jar signed.")){
						signSuccess=true;
					}
				}
				logger.info("渠道打包apk签名执行完成，签名是否成功signSuccess={},签名耗时：{}ms", signSuccess,
						(System.currentTimeMillis()-start));
				
				
				if (!signSuccess) throw new AutoPackException("apk证书签名失败");
//				
//			}
			
		} catch (Exception e) {
			logger.error("apk包签名出错", e);
			throw new AutoPackException("apk sign error");
		}finally{
			IOUtils.closeQuietly(reader);
		}
	}
	/**
	 * 修改apl sdk配置文件，写入渠道号
	 * @param xmlFilePath
	 * @param channelCode
	 * @throws Exception 
	 */
	private static void writeChannelCode(String xmlFilePath, String channelCode) throws Exception{
		SAXReader reader = new SAXReader();
		FileWriter writer =null;
		try {
			// 判断文件是否存在，若不存在创建
			File sdkXmlFile = new File(xmlFilePath);
			if (!sdkXmlFile.exists()){
				File parentDir = sdkXmlFile.getParentFile();
				if (!parentDir.exists()) parentDir.mkdirs();
				// 获取当前包路径
				final String classDir = AutoPackTask.class.getClassLoader().getResource("").getPath();
				FileUtils.copyFileToDirectory(new File(classDir +File.separator +"buildConfig.xml"), parentDir);
			}
			Document doc =reader.read(new FileInputStream(xmlFilePath));
			Element sdkVersionElement = doc.getRootElement().element("ChannelId"); // "ChannelId"
			sdkVersionElement.setText(channelCode);
			String xmlContent =doc.asXML();
			logger.info("after modify, xml content: {}", xmlContent);
			reader = null;
			writer = new FileWriter(xmlFilePath);
			doc.write(writer);
			writer.flush();
			
			
		} catch (Exception e) {
			logger.error("write channel code to sdk xml file error", e);
			throw e;
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
	
	private static final String ANDROID_MANIFESTXML_CONFIGFILE="AndroidManifest.xml";
	
	private static final String ANDROID_MANIFESTXML_TMPCONFIGFILE="AndroidManifest_tmp.xml";
	private static String aXMLPrinter2JarPath;
	{
		final String classDir = AutoPackTask.class.getClassLoader().getResource("").getPath();
		// AXMLPrinter2.jar放在/WEB-INF/lib路径下
		aXMLPrinter2JarPath =new File(classDir.replaceAll("classes", "lib") +File.separator +"AXMLPrinter2.jar").getAbsolutePath();
	}
	/**
	 *  AndroidManifest.xml解压缩，解压缩后文件重命名为原来的文件名
	 *  使用apk1.4反编译后xml文件是加密的
	 *  使用2.2.0不需要解压缩
	 * @param decodeFile
	 */
	private void decompAndroidManifest(File decodeFile){
		// java -jar AXMLPrinter2.jar AndroidManifest.xml > AndroidManifest2.xml
		final String parentDir = decodeFile.getAbsolutePath() +File.separator;
		String srcXmlPath = parentDir +ANDROID_MANIFESTXML_CONFIGFILE;
		String tmpXmlPath = parentDir +ANDROID_MANIFESTXML_TMPCONFIGFILE;
//		StringBuilder cmdStr= new StringBuilder("java -jar ");
//		cmdStr.append(aXMLPrinter2JarPath).append(" ").append(srcXmlPath).append("  > AndroidManifest2.xml")
//			.append(tmpXmlPath);
		long start=System.currentTimeMillis();
//		Runtime rt = Runtime.getRuntime();
//		BufferedReader reader =null;
		try {

//			Process process =rt.exec(cmdStr.toString());
			logger.info("冰趣apk包AndroidManifest.xml开始解压缩，xmlfile={}", srcXmlPath);
			String[] args = new String[]{srcXmlPath, tmpXmlPath};
			MyAXMLPrinter.main(args);
//			InputStream is = process.getInputStream();
//			reader = new BufferedReader(new InputStreamReader(is));
//			String msg=null;
//			// 休眠1s
//			Thread.sleep(2000);
//			while ((msg=reader.readLine())!=null){
//				logger.info("冰趣apk包AndroidManifest.xml解压缩过程: " +msg);
//				
//			}
//			
			File tmpFile = new File(tmpXmlPath);
			boolean decompSuccess=false;
			if (tmpFile.exists()){
				boolean delSuccess=new File(srcXmlPath).delete();
				if (delSuccess){
					// 重命名成功，认为xml解压缩成功
					decompSuccess=tmpFile.renameTo(new File(decodeFile, ANDROID_MANIFESTXML_CONFIGFILE));
				}
				
			}
			logger.info("冰趣apk包AndroidManifest.xml解压缩执行完成，decompSuccess={},耗时：{}ms", decompSuccess,
					(System.currentTimeMillis()-start));
		} catch (Exception e) {
			logger.error("冰趣apk包AndroidManifest.xml解压缩出错", e);
			throw new AutoPackException("AndroidManifest.xml decompression error");
		}finally{
//			IOUtils.closeQuietly(reader);
		}
	}
	
	// 冰趣apk通过修改AndroidManifest.xml写入渠道号
	private void modifyAndroidManifest(File decodeFile, String channelCode) throws Exception{
		File sdkXmlFile= new File(decodeFile, ANDROID_MANIFESTXML_CONFIGFILE);
		SAXReader reader = new SAXReader();
		FileWriter writer =null;
		try {
			// 判断文件是否存在，若不存在不需要处理
			if (!sdkXmlFile.exists()){
				return;
			}
			Document doc =reader.read(new FileInputStream(sdkXmlFile));
			/**
			 * <meta-data android:name="CHANNEL_ID" android:value="CHA000001"/>
			 */
			final Element applicationElement = doc.getRootElement().element("application");
			if (applicationElement!=null){
				List elements = applicationElement.elements("meta-data");
				if (elements!=null && elements.size()>0){
					out:
					for (Object obj : elements){
						if (obj instanceof Element){
							Element element =(Element)obj;
							List attrs = element.attributes();
							if (!CollectionUtils.isEmpty(attrs)){
								// 先根据name="CHANNEL_ID"找到渠道编码填写的节点
								boolean findChannelCodeAttr=false;
								for (Object o:attrs){
									if (o instanceof Attribute){
										Attribute attr =(Attribute)o;
										String  channelCodeAttrVal = attr.getValue();
										if (StringUtils.isNotBlank(channelCodeAttrVal) && "CHANNEL_ID".equalsIgnoreCase(channelCodeAttrVal)){
											findChannelCodeAttr=true;
											continue;
										}
										if (findChannelCodeAttr){
											String  channelCodeAttrName = attr.getName();
											if (StringUtils.isNotBlank(channelCodeAttrName) && "value".equalsIgnoreCase(channelCodeAttrName)){
												attr.setValue(channelCode);
												break out;
											}
										}
									}
								}
							}
							
						}
					}
				}
			}
			
			
			String xmlContent =doc.asXML();
//			logger.info("冰趣apk AndroidManifest.xml配置文件写入渠道号结果: {}", xmlContent);
			reader = null;
			writer = new FileWriter(sdkXmlFile);
			doc.write(writer);
			writer.flush();
		} catch (Exception e) {
			logger.error("冰趣apk AndroidManifest.xml配置文件写入渠道号出错", e);
			throw e;
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
	
	private static void execCommand(String command, String finishMark){
		logger.info("渠道打包执行命令，command={}", command);
		BufferedReader reader =null;
		try {
			Runtime run =Runtime.getRuntime();
			long start = System.currentTimeMillis();
			Process process = run.exec(command);

				InputStream is = process.getInputStream();
				reader = new BufferedReader(new InputStreamReader(is));
				// 打包日志中有"jar signed."说明打包成功
				String msg=null;
				boolean execSuccess=false;
				while ((msg=reader.readLine())!=null){
					// 打包日志中包含结束标志性内容，认为执行成功
					if (msg.contains(finishMark)){
						execSuccess=true;
					}
				}
				logger.info("渠道打包执行命令完成，是否成功execSuccess={}, 执行命令耗时：{}ms", execSuccess,
						(System.currentTimeMillis()-start));
				
				
				if (!execSuccess) throw new AutoPackException("渠道打包执行命令失败");
			
		} catch (Exception e) {
			logger.error("渠道打包执行命令完成出错", e);
			throw new AutoPackException("apk pack execute command error");
		}finally{
			IOUtils.closeQuietly(reader);
		}
	}
	public static void main(String[] args) {
		try {
			new AutoPackTask(null, null, null,null, 1)
			.modifyAndroidManifest(new File("C:\\Users\\lilongwei\\Desktop"), "a111bcdefg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
