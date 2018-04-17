package com.dome.sdkserver.autopack;

import java.io.InputStream;
import java.util.concurrent.Callable;

import org.apache.commons.io.IOUtils;

public class FtpUploadTask implements Callable<Boolean> {

	private String remoteDir;
	
	private String filename;
	
	private InputStream is;
	
	
	public FtpUploadTask(String remoteDir, String filename, InputStream is) {
		super();
		this.remoteDir = remoteDir;
		this.filename = filename;
		this.is = is;
	}


	@Override
	public Boolean call() throws Exception {
		FtpConnection conn = new FtpConnection();
		conn.connect();
		boolean flag = conn.upload(remoteDir, filename, is);
		IOUtils.closeQuietly(is);
		return flag;
	}
}
