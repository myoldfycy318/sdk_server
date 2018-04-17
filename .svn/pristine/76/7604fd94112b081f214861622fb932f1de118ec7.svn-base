package com.dome.sdkserver.view;

import com.dome.sdkserver.constants.SysEnum;

import java.util.Map;

public class AjaxResult {
	public static final int CODE_SUCCESS = 1000;
	public static final int CODE_FAILED = 1005;
    public static final int ACCESS_REJECT =1004;
	private int responseCode;
	private String errorMsg;
	private int errorCode;


	private Object data;

	private AjaxResult(int responseCode, Object data, int errorCode, String errorMsg) {
		this.responseCode = responseCode;
		
		this.data = data;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public Object getData() {
		return data;
	}



	public static final AjaxResult success() {
		return new AjaxResult(CODE_SUCCESS, null, 0, null);
	}

	public static final AjaxResult success(Object data) {
		return new AjaxResult(CODE_SUCCESS, data, 0, null);
	}

	public static final AjaxResult success(Object data, String errorMsg) {
		return new AjaxResult(CODE_SUCCESS, data, 0, errorMsg);
	}

	public static final AjaxResult failed() {
		return new AjaxResult(CODE_FAILED, null, 0, null);
	}

	public static final AjaxResult failed(String errorMsg) {
		return new AjaxResult(CODE_FAILED, null, 0, errorMsg);
	}
	
	public static final AjaxResult failed(int errorCode, String errorMsg) {
		return new AjaxResult(CODE_FAILED, null, errorCode, errorMsg);
	}

	public static final AjaxResult failed(Object data, int errorCode, String errorMsg) {
		return new AjaxResult(CODE_FAILED, data, errorCode, errorMsg);
	}
	
	/**
	 * 程序抛出异常，提示给前台
	 * @return
	 */
	public static final AjaxResult failedSystemError() {
		return new AjaxResult(CODE_FAILED, null, 0, SysEnum.SYSTEM_ERROR.getResponeMsg());
	}

	public static AjaxResult conventMap(Map<String, Object> map){
		boolean isSuccess = (Boolean)map.get("isSuccess");
		if (isSuccess) {
			return AjaxResult.success();
		}
		int errorCode = Integer.parseInt((map.get("code")+""));
		String errorMsg = map.get("msg") + "";
		return AjaxResult.failed(errorCode, errorMsg);
	}

    public static final AjaxResult accessReject() {
        return new AjaxResult(ACCESS_REJECT, null, 0, null);
    }

    public static boolean isSucees(AjaxResult result) {
        return CODE_SUCCESS == result.getResponseCode();
    }
}
