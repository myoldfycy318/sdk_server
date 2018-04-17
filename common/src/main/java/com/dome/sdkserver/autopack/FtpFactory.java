package com.dome.sdkserver.autopack;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP连接会超时，因此每次上传或下载文件时都会检查连接，若超时会重新连接（发现超时会调用一次FtpConnection.connect()）。
 * 调用者不需要关注连接是否已断开
 * 
 * @author li
 *
 */
public class FtpFactory {
	private static final Logger logger = LoggerFactory.getLogger(FtpFactory.class);
	
	private final static int CONN_POOL_SIZE = 30;
	/**
	 * ftp连接池最多保存30个
	 * 任务完成后会移除部分连接，活动连接保留10个
	 */
//	private ArrayBlockingQueue<FtpConnection> list = new ArrayBlockingQueue<FtpConnection>(CONN_POOL_SIZE);
	
	public FtpFactory(){
		this.initFtpConnection(10);
	}

	private void initFtpConnection(int connSize) {
//		if (connSize<10) {
//			connSize =10;
//		} else if (connSize>30){
//			connSize =30;
//		}
//		for (int i=0;i <connSize; i++){
//			createFtpConnection(true);
//			
//		}
	}

	public FtpConnection createFtpConnection(boolean needAdd) {
		FtpConnection conn = new FtpConnection();
		conn.connect();
		if (conn.isConnect()){
//			if (needAdd) addFtpConnection(conn);
			return conn;
		}
		return null;
	}
	
	/**
	 * 获取ftp连接，会将连接从队列中移除。若队列为空，返回null
	 * 默认场下，使用完后需要再添加到队列中
	 * @return
	 */
//	public synchronized FtpConnection getFtp(){
//		return list.poll();
//	}
//	
//	public boolean hasFtpConnection(){
//		return !list.isEmpty();
//	}
//	
//	public synchronized boolean addFtpConnection(FtpConnection conn){
//		boolean isSuccess = false;
//		// 存在一种情况创建了连接，但是队列已满。但是createFtpConnection()返回了连接
//		if (list.size()< CONN_POOL_SIZE&& !list.contains(conn)){
//			isSuccess = list.offer(conn);
//		}
//		return isSuccess;
//	}
	
	public static void main(String[] args) {
		FtpFactory factory = new FtpFactory();
		FtpConnection conn= factory.createFtpConnection(true);
		String path = "/home/wwwroot/tdl.domegame.cn/uploadPkg/T001/20160803/";
		String filename="Test01.apk";
		File srcFile=new File("F:\\test\\qianbao_download.apk");
		
		long start =System.currentTimeMillis();
		boolean flag = conn.upload(path, filename, srcFile);
		long end =System.currentTimeMillis();
		System.out.println("download result:" + flag +", time:" + (end-start) +"ms");
		conn.disconnect();
	}
}
