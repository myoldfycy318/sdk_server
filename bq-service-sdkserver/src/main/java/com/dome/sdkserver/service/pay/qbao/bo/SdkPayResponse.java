/**
 *
 */
package com.dome.sdkserver.service.pay.qbao.bo;

/**
 * SDK返回信息
 */
public class SdkPayResponse {

    public static final String CODE_SUCCESS = "1000";

    public static final String CODE_FAILED = "1005";

    // 1000 成功 1005 失败
    private String responseCode;

    //错误码
    private String errorCode;

    //错误信息
    private String errorMsg;

    //返回数据
    private Object data;

    public SdkPayResponse(String responseCode) {
        this.responseCode = responseCode;
    }

    public SdkPayResponse(String responseCode, String errorCode, String errorMsg) {
        this.responseCode = responseCode;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public SdkPayResponse(String responseCode, String errorCode, String errorMsg, Object data) {
        this(responseCode, errorCode, errorMsg);
        this.data = data;
    }

    public SdkPayResponse(String responseCode, Object data) {
        this.responseCode = responseCode;
        this.data = data;
    }


    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static SdkPayResponse failed(String errorCode, String message) {
        return new SdkPayResponse(CODE_FAILED, errorCode, message);
    }

    public static SdkPayResponse failed(String message) {
        return new SdkPayResponse(CODE_FAILED, null, message);
    }

    public static SdkPayResponse success(Object data) {
        return new SdkPayResponse(CODE_SUCCESS, data);
    }

    public static SdkPayResponse success() {
        return new SdkPayResponse(CODE_SUCCESS, null);
    }

    public static boolean isSuccess(SdkPayResponse sdkPayResponse) {
        return CODE_SUCCESS.equals(sdkPayResponse.getResponseCode());
    }

}
