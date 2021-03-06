package com.dome.sdkserver.autopack;

import brut.androlib.ApkDecoder;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;


/**
 * apktool 使用2.2.0  https://bitbucket.org/iBotPeaches/apktool/downloads
 * @author lilongwei
 *
 */
public class ApkToolMain {
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger("channelpkg");

	public void pack(){
		try {
			// 反编译
			cmdDecode(null, null);
			
			// 修改渠道号
			// 编译
			cmdBuild(null, null);
			
			// apk包签名
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 反编译命令示例 ./apktool d -f -r  doudizhu.apk test2
	 * @param apkFile apk文件
	 * @param targetDirFile 反编译后的目录
	 */
	public void cmdDecode(File apkFile, File targetDirFile) {
		ApkDecoder decoder = new ApkDecoder();

		decoder.setForceDelete(true); // -f选项
		decoder.setApkFile(apkFile);
		try {
			decoder.setOutDir(targetDirFile); // -o
//			decoder.setDecodeResources((short)256); // -r不反编译资源文件
			decoder.decode();
		} catch (Exception ex) {
			String message = "反编译apk包出错，apkFile=" +apkFile.getAbsolutePath() +", targetDirFile="
					+ targetDirFile.getAbsolutePath();
			logger.error(message, ex);
			throw new AutoPackException(message, ex);
		}
	}

	/**
	 * 编译命令示例： ./apktool b -f test2 doudizhu2.apk
	 * @param apkDecodeDir apk包反编译的根目录
	 * @param buildApkFile 编译后生成的文件
	 */
	@SuppressWarnings("unchecked")
	public void cmdBuild(File apkDecodeDir, File buildApkFile) {
		//new Androlib(apkOptions).build(new File(appDirName), outFile);
//		 ApkOptions apkOptions = new ApkOptions();
//		 apkOptions.forceBuildAll = true;
//		try {
//			new Androlib(apkOptions).build(apkDecodeDir, buildApkFile);
//
//			// 编译完成后删除目录
//			FileUtils.deleteDirectory(apkDecodeDir);
//		} catch (Exception e) {
//			String message = "编译apk包出错，apkDecodeDir="+apkDecodeDir.getAbsolutePath()+", buildApkFile="
//					+ buildApkFile.getAbsolutePath();
//			logger.error(message, e);
//			throw new AutoPackException(message, e);
//		}
	}
	
	@SuppressWarnings("rawtypes")
	private static HashMap buildOptMap=null;
	
	@SuppressWarnings("rawtypes")
	private static HashMap getBuildOptMap(){
		if (buildOptMap==null){
			buildOptMap = new HashMap();
			buildOptMap.put("forceBuildAll", false); // -f
			buildOptMap.put("debug", false);
			buildOptMap.put("verbose", false);
			buildOptMap.put("injectOriginal", false);
			buildOptMap.put("framework", false);
			buildOptMap.put("update", false);
		}
		return buildOptMap;
	}

}