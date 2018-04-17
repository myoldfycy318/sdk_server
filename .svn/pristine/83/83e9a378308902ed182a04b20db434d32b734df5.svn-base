package com.dome.sdkserver.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 增加可以处理创建多级目录
 * @author li
 *
 */
public abstract class FtpUtil {

	private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);
	public static boolean uploadFile(String hostname,int port,String username, String password, String path, String filename, InputStream input) { 
	    boolean success = false; 
	    FTPClient ftp = new FTPClient(); 
	    try { 
	        ftp.connect(hostname, port);//连接FTP服务器 
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器 
	        ftp.login(username, password);//登录 
	        
	        int reply = ftp.getReplyCode(); 
	        if (!FTPReply.isPositiveCompletion(reply)) {
	        	logger.error("FTP server response code: " + reply);
	            ftp.disconnect(); 
	            return success; 
	        }
//	        // 创建多层子目录会返回false，导致上传失败
//	        ftp.makeDirectory(path);
//	        ftp.changeWorkingDirectory(path);
	        // 若多层目录不存在，会逐级创建
	        changeWorkDirctory(ftp, path);
	        ftp.setFileType(FTPClient.BINARY_FILE_TYPE); 
	        ftp.enterLocalPassiveMode();
	        success = ftp.storeFile(filename, input);
	        if (!success){
	        	logger.error("FTP upload file: {} faied", path + File.separator + filename);
	        }
	        ftp.logout(); 
	    } catch (IOException e) { 
	        logger.error("upload file to FTP failed", e); 
	    } finally {
	    	IOUtils.closeQuietly(input);
	        if (ftp.isConnected()) { 
	            try { 
	                ftp.disconnect(); 
	            } catch (IOException ioe) { 
	            } 
	        } 
	    } 
	    return success; 
	}
	
	/**
	 * 切换工作目录，若目录不存在会逐级创建目录
	 * 文件路径分隔符使用/，不能使用\
	 * @param path
	 * @throws IOException 
	 */
	private static boolean changeWorkDirctory(FTPClient ftp, String path) throws IOException{
		boolean flag = false;
		flag=ftp.changeWorkingDirectory(path);
		// dirPath值改变不会影响path
		String dirPath = path;
		int index = -1;
		// 提高效率，先每次后退一层目录来切换目录
		while (!flag){
			index = dirPath.lastIndexOf("/");
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
					throw new RuntimeException("ftp change directory: " + path +", make directory failed, dir: " + dir);
				}
				ftp.changeWorkingDirectory(dir);
			}
		}
			
		return flag;
	}
	
	public static void main(String[] args) {
		String hostname = "115.159.34.17";
		int port = 21;
		String username = "bqftp", password = "cZ7cVKYZ6dgFHQPb";
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream("D:\\Disk_F\\360Downloads\\jqueryApi.zip"));
			boolean flag = uploadFile(hostname, port, username, password, "/dl.domestore.cn/uploadsdk/20160823", "jqueryApi.zip", bis);
			System.out.println("flag: " + flag);
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
	}
}
