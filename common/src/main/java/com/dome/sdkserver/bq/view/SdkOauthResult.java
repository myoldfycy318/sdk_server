package com.dome.sdkserver.bq.view;

import com.alibaba.fastjson.annotation.JSONField;

public class SdkOauthResult {
	public static final int CODE_SUCCESS = 1000;

	public static final int CODE_FAILED = 1005;
    //标识结束代码流程
    public static final int END_PROCESS = 2000;

	private int responseCode;

	private int errorCode;

	private String errorMsg = "";

	private Object data = "";

	public SdkOauthResult() {
	}

	private SdkOauthResult(int code, Object data, String message) {
		this.responseCode = code;
		this.errorMsg = message;
		this.data = data;
	}

	private SdkOauthResult(int code, int errorCode, String message) {
		this.responseCode = code;
		this.errorCode = errorCode;
		this.errorMsg = message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public Object getData() {
		return data;
	}

	public void setSuccess(boolean success) {
		if (success) {
			responseCode = CODE_SUCCESS;
		} else {
			responseCode = CODE_FAILED;
		}
	}

	@JSONField(serialize = false)
	public boolean isSuccess() {
		return CODE_SUCCESS == responseCode;
	}
    @JSONField(serialize = false)
      public boolean isError() {
        return CODE_FAILED == responseCode;
    }
    @JSONField(serialize = false)
    public boolean isEndProcess() {
        return END_PROCESS == responseCode;
    }

	public static final SdkOauthResult success() {
		return new SdkOauthResult(CODE_SUCCESS, null, null);
	}

	public static final SdkOauthResult success(Object data) {
		return new SdkOauthResult(CODE_SUCCESS, data, null);
	}

	public static final SdkOauthResult success(Object data, String message) {
		return new SdkOauthResult(CODE_SUCCESS, data, message);
	}

	public static final SdkOauthResult failed() {
		return new SdkOauthResult(CODE_FAILED, null, null);
	}

	public static final SdkOauthResult failed(String message) {
		return new SdkOauthResult(CODE_FAILED, null, message);
	}

	public static final SdkOauthResult failed(Object data, String message) {
		return new SdkOauthResult(CODE_FAILED, data, message);
	}

	public static final SdkOauthResult failed(Object data) {
		return new SdkOauthResult(CODE_FAILED, data, "");
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static final SdkOauthResult failed(int errorCode, String message) {
		return new SdkOauthResult(CODE_FAILED, errorCode, message);
	}

    public static final SdkOauthResult endProcess() {
        return new SdkOauthResult(END_PROCESS, null, null);
    }
}
