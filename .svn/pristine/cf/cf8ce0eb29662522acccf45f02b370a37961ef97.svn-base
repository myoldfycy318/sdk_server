package com.dome.sdkserver.autopack;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.constants.Constant;

public class FtpConnection {
	private static final Logger logger = LoggerFactory.getLogger(FtpConnection.class);
	
	private final FTPClient ftp = new FTPClient();
	
	private boolean isConnect = false;
	
	public void connect(){
		isConnect = false;
		try {
			ftp.setDefaultTimeout(60*60*1000);
			ftp.connect(FtpConfig.ip, FtpConfig.port);
			ftp.login(FtpConfig.user, FtpConfig.password);
			int reply = ftp.getReplyCode(); 
	        if (!FTPReply.isPositiveCompletion(reply)) {
	        	logger.error("FTP server response code: " + reply);
	            ftp.disconnect();
	            return;
	        }
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
	        ftp.enterLocalPassiveMode();
	        isConnect = true;
		} catch (Exception e) {
			logger.error("create ftp connection error", e);
			isConnect = false;
			disconnect();
		}
	}
	
	public boolean upload(String path, String filename, File srcFile){
		boolean flag =false;
		try {
			flag =this.upload(path, filename, new FileInputStream(srcFile));
		} catch (FileNotFoundException e) {
			logger.error("source file not found, file path:" +srcFile.getPath(), e);
		}
		return flag;
	}
	
	public boolean upload(String path, String filename, InputStream is){
		
		try {
			// 超时会自动连接
			if (!isConnect()) {
				connect();
				if (!isConnect) throw new AutoPackException("connect ftp server fail");
			}
			this.changeWorkDirctory(path);
			
			boolean success = ftp.storeFile(filename, is);
	        if (!success){
	        	logger.error("FTP upload file: {} faied", path + File.separator + filename);
	        }
	        return success;
		} catch (Exception e) {
			logger.error("upload file to ftp error, dest file:" + (path + File.separator + filename), e);
		}finally {
			// 每次上传完成都断开连接
			disconnect();
		}
		return false;
	}
	
	/**
	 * 需要注意ftp当前目录和下载文件的目录
	 * @param relativeFilePath
	 * @param localFilePath
	 * @return
	 */
	public boolean download(String relativeFilePath, String localFilePath){
		boolean flag = false;
		try {
			// 超时会自动连接
			if (!isConnect()) {
				connect();
				if (!isConnect) throw new AutoPackException("connect ftp server fail");
			}
			flag = ftp.retrieveFile(relativeFilePath, new FileOutputStream(localFilePath));
			if (!flag){
				logger.error("download file from ftp failed, ftp path:" + relativeFilePath +", local file path:" +localFilePath);
			}
		} catch (IOException e) {
			logger.error("download file from ftp error, ftp path:" + relativeFilePath, e);
		}finally {
			// 每次下载完成都断开连接
			disconnect();
		}
		return flag;
	}
	/**
	 * 切换工作目录，若目录不存在会逐级创建目录
	 * 文件路径分隔符使用/，不能使用\
	 * @param path
	 * @throws IOException 
	 */
	public boolean changeWorkDirctory(String path) throws IOException{
		boolean flag = false;
		flag=ftp.changeWorkingDirectory(path);
		// dirPath值改变不会影响path
		String dirPath = path;
		int index = -1;
		// 提高效率，先每次后退一层目录来切换目录
		while (!flag){
			index = dirPath.lastIndexOf("/");
			// FTP服务器目录为/，一层目录都没有创建时，就不需要切换
			if (index==0) break;
			dirPath = dirPath.substring(0, index);
			flag = ftp.changeWorkingDirectory(dirPath);
		}
		if (index >-1){
			String needCreateDirPath = path.substring(index+1);
			String[] dirs = needCreateDirPath.split("/");
			// 每次切换一层目录，不成功，创建目录
			for (String dir : dirs){
				flag =ftp.makeDirectory(dir);
				if (!flag){
					throw new AutoPackException("ftp change directory: " + path +", make directory failed, dir: " + dir);
				}
				ftp.changeWorkingDirectory(dir);
			}
		}
			
		return flag;
	}
	
	public boolean isConnect(){
		return isConnect && ftp.isConnected();
	}
	
	public void disconnect(){
		try {
			if (isConnect || ftp.isConnected() || ftp.isAvailable()){
				ftp.disconnect();
			}
		} catch (IOException e) {
			logger.error("disconnect ftp failed", e);
		}
	}
	
	public static void main(String[] args) {
		FtpConnection conn = new FtpConnection();
		conn.connect();
		String relativeFilePath="/home/wwwroot/tdl.domegame.cn/uploadPkg/20160905";
		String localFilePath = "D:\\qq_file\\dntk2.apk";
		long start =System.currentTimeMillis();
		boolean flag = conn.download("/dl.domestore.cn/domestore/20160831_151358_1.2.0.apk ", localFilePath);
//		boolean flag=conn.upload(relativeFilePath, "dntk.apk", new File(localFilePath));
		long end =System.currentTimeMillis();
		System.out.println("download result:" + flag +", time:" + (end-start) +"ms");
		conn.disconnect();
	}
}
