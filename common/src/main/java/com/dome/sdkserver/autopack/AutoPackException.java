package com.dome.sdkserver.autopack;

/**
 * 开放平台自动打包异常
 * @author li
 *
 */
public class AutoPackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AutoPackException(){
		
	}
	
	public AutoPackException(String message){
		super(message);
	}
	
	public AutoPackException(String message, Throwable cause){
		super(message, cause);
	}
}
