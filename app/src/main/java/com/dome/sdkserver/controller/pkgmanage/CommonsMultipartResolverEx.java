package com.dome.sdkserver.controller.pkgmanage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.dome.sdkserver.listener.FileUploadProgressListener;


public class CommonsMultipartResolverEx extends CommonsMultipartResolver {

	private HttpServletRequest request;
	
	@Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {  
    	// 获取到request,要用到session  
        this.request = request;
        return super.resolveMultipart(request);  
    } 
    
	@Override
	protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
		ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
		if (request != null){
			HttpSession session = request.getSession();
			upload.setProgressListener(new FileUploadProgressListener(session));
		}
		
		return upload;
	}
}
