package com.dome.sdkserver.bq.login.domain.request;

import java.io.Serializable;

import com.dome.sdkserver.bq.login.domain.user.User;


public class AuthorizationRequest implements Serializable {
	private static final long serialVersionUID = -7130915737098734016L;

	/**
	 * 应用id
	 */
	private String clientId;

	/**
	 * 用户信息
	 */
	private User user;

	/**
	 * accessToken
	 */
	private String tokenId;

	/**
	 * 接入类型
	 */
	private int accessType;

	public AuthorizationRequest() {
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public int getAccessType() {
		return accessType;
	}

	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}
}
