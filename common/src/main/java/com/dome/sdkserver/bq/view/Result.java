package com.dome.sdkserver.bq.view;

public class Result {

	/** 服务器返回成功 */
	public static final int RESPONSE_CODE_SCUESS = 1000;
	
	/** 参数校验失败 */
	public static final int RESPONSE_CODE_PARAM_ERROR = 1001;

	/** 服务器处理失败 */
	public static final int RESPONSE_CODE_HAS_ERROR = 9999;

	/**
	 * 服务器返回的json数据响应码
	 */
	private int responseCode = 0;

	/**
	 * 成功的信息
	 */
	private String errorMsg = null;

	public Result() {
	}

	public Result(int responseCode, String errorMsg) {
		this.responseCode = responseCode;
		this.errorMsg = errorMsg;
	}

	/**
	 * 处理失败
	 * @return
	 */
	public static Result error() {
		Result result = new Result(Result.RESPONSE_CODE_HAS_ERROR, "处理失败");
		return result;
	}
	
	/**
	 * 处理失败
	 * @return
	 */
	public static Result error(int errorCode, String msg) {
		Result result = new Result(errorCode, msg);
		return result;
	}
	
	/**
	 * 处理成功
	 * @return
	 */
	public static Result success() {
		Result result = new Result(Result.RESPONSE_CODE_SCUESS, "处理成功");
		return result;
	}

	public int getResponseCode() {
		return this.responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
