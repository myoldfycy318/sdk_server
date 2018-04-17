package com.dome.sdkserver.metadata.entity.bq.login;

import java.io.Serializable;
import java.util.Date;

import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.util.DateUtil;

public class OauthAccessRecordEntity implements Serializable {
	private static final long serialVersionUID = 2278918884951668974L;

	private static String splitFlag = "#";
	/**
	 * 访问时间
	 */
	private Date accessTime = DateUtils.now();

	/**
	 * 接入类型
	 */
	private int accessType;

	/**
	 * 应用编码
	 */
	private String clientId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 访问结果
	 */
	private int result = 1;

	/**
	 * 表名后缀
	 */
	private String curMonth;

	/**
	 * 请求地址url类型
	 */
	private int requestUrlType;
	
	/**
	 * 访问的手机系统版本
	 */
	private String osVersion;
	
	/**
	 * 访问的手机型号
	 */
	private String mobileType;
	
	/**
	 * 访问的ip
	 */
	private String accessIp;
	
	/**
	 * 登录类型
	 */
	private String loginType;
	
	private String loginChannelCode;
	
	private int errorCode;
	
	private String errorMessage;
	
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public int getAccessType() {
		return accessType;
	}

	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getRequestUrlType() {
		return requestUrlType;
	}

	public void setRequestUrlType(int requestUrlType) {
		this.requestUrlType = requestUrlType;
	}

	public String getCurMonth() {
		return curMonth;
	}

	public void setCurMonth(String curMonth) {
		this.curMonth = curMonth;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getMobileType() {
		return mobileType;
	}

	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}

	public String getAccessIp() {
		return accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getLoginChannelCode() {
		return loginChannelCode;
	}

	public void setLoginChannelCode(String loginChannelCode) {
		this.loginChannelCode = loginChannelCode;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("accessTime=").append(DateUtils.toDateTime(accessTime)).append(splitFlag);
		sb.append("accessType=").append(accessType).append(splitFlag);
		sb.append("clientId=").append(clientId).append(splitFlag);
		sb.append("userName=").append(userName).append(splitFlag);
		sb.append("userId=").append(userId).append(splitFlag);
		sb.append("result=").append(result).append(splitFlag);
		sb.append("requestUrlType=").append(requestUrlType).append(splitFlag);
		sb.append("osVersion=").append(osVersion).append(splitFlag);
		sb.append("mobileType=").append(mobileType).append(splitFlag);
		sb.append("osVersion=").append(osVersion).append(splitFlag);
		sb.append("accessIp=").append(accessIp).append(splitFlag);
		sb.append("loginType=").append(loginType).append(splitFlag);
		sb.append("errorCode=").append(errorCode).append(splitFlag);
		sb.append("errorMessage=").append(errorMessage).append(splitFlag);
		sb.append("loginChannelCode=").append(loginChannelCode);
		return sb.toString();
	}
	
}
