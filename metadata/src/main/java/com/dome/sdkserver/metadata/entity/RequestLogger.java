package com.dome.sdkserver.metadata.entity;

import java.util.Date;

public class RequestLogger {

	private long id;
	
	private String className;
	
	private String methodName;
	
	private String args;
	
	/**
	 * 1成功 2失败
	 */
	private int result;
	
	/**
	 * result=2失败时有值
	 */
	private String errorMsg;
	
	private long userId;
	
	private String userName;
	
	private String ip;
	
	private Date reqTime;
	
	private String createTime;
	
	/**
	 * 请求来源类型，open为1，openba为2,channel为3
	 */
	private int type;

	/**
	 * get 1, post 2
	 */
	private int reqMethod;
	
	private String queryString;
	
	private String reqUri;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getReqTime() {
		return reqTime;
	}

	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getReqMethod() {
		return reqMethod;
	}

	public void setReqMethod(int reqMethod) {
		this.reqMethod = reqMethod;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getReqUri() {
		return reqUri;
	}

	public void setReqUri(String reqUri) {
		this.reqUri = reqUri;
	}
	
}
