package com.dome.sdkserver.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.dome.sdkserver.bo.FileUpdProgress;



/**
 * 
 * 创建人：fantasy <br>
 * 创建时间：2013-12-6 <br>
 * 功能描述： 文件上传进度<br>
 */
public class FileUploadProgressListener implements ProgressListener {
	
	private HttpSession session;

	public FileUploadProgressListener() {  }  
	
    public FileUploadProgressListener(HttpSession session) {
        this.session=session;  
        FileUpdProgress progress = new FileUpdProgress();
        session.setAttribute("updProgress", progress);  
    }  
	
	/**
	 * pBytesRead 到目前为止读取文件的比特数 pContentLength 文件总大小 pItems 目前正在读取第几个文件
	 */
	public void update(long pBytesRead, long pContentLength, int pItems) {
		FileUpdProgress progress = (FileUpdProgress) session.getAttribute("updProgress");
		progress.setBytesRead(pBytesRead);
		progress.setContentLength(pContentLength);
		progress.setItems(pItems);
		session.setAttribute("updProgress", progress);
	}
}