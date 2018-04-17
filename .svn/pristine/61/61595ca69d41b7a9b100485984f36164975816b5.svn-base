package com.dome.sdkserver.vo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.pkgmanage.Pkg;
import com.dome.sdkserver.constants.AppChannelEnum;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;
import com.dome.sdkserver.util.business.GameUtils;

/**
 * 
 * 设置状态描述、上架渠道描述
 * @author lilongwei
 *
 */
public class MerchantAppVo extends MerchantAppInfo {

	/**
	 * 上架渠道中文展示，多个用逗号分隔
	 */
	private String shelfChannelDesc;
	
	/**
	 * 应用包体sdkversion
	 */
	private String sdkVersion;
	
	private String pkgUploadPath;
	
	private String pkgJiaguPath;
	
	private String pkgFileName;
	
	private String privateRsaKey;
	
	private String publicRsaKey;
	
	private String testPrivateRsaKey;
	
	private String testPublicRsaKey;
	
	public MerchantAppVo(){
		super();
	}
	
	/**
	 * 计费点审批页面的应用列表
	 * @param appInfo
	 */
	public MerchantAppVo(MerchantAppInfo appInfo){
		BeanUtils.copyProperties(appInfo, this);
		
//		initShelfChannelDesc();
		initStatusDesc();
//		initRsaKey(appInfo);
	}

	/**
	 * 应用流程审批页面使用
	 * @param appInfo
	 * @param pkg
	 */
	public MerchantAppVo(MerchantAppInfo appInfo, Pkg pkg){
		BeanUtils.copyProperties(appInfo, this);
		initShelfChannelDesc();
		initStatusDesc();
		// 手游情况
		String appCode=appInfo.getAppCode();
		if (GameUtils.analyseGameType(appCode)==GameTypeEnum.mobilegame){
			initPkgInfo(pkg);
			initRsaKey(appInfo);
		}
	}
	
	private void initShelfChannelDesc() {
		// 上架渠道中文设置
		String shelfChannel = this.getShelfChannel();
		StringBuffer sb = new StringBuffer();
		if (!StringUtils.isEmpty(shelfChannel)) {
			String[] channels = shelfChannel.split(",");
			for (String channel : channels) {
				AppChannelEnum em = AppChannelEnum.getFromKey(channel);
				if (em == null) continue;
				if (sb.length() == 0){
					sb.append(em.getDescr());
				} else {
					sb.append(",").append(em.getDescr());
				}
			}
			this.shelfChannelDesc = sb.toString();
		}
	}
	
	private void initPkgInfo(Pkg pkg){
		if (pkg != null) {
			this.sdkVersion = pkg.getSdkVersion();
			this.pkgUploadPath = pkg.getUploadPath();
			this.pkgJiaguPath = pkg.getJiaguPath();
			String pkgName = pkg.getName();
			String fileName = pkg.getFileName();
			int index = fileName.lastIndexOf(".");
			if (index > -1){
				this.pkgFileName = pkgName + fileName.substring(index);
			}
		}
	}
	
	private void initStatusDesc(){
		// 状态描述设置
		int status = (this.getStatus() == null ? 0 : this.getStatus());
		AppStatusEnum em = AppStatusEnum.getFromKey(status);
		if (em != null) {
			this.setStatusDesc(em.getMsg());
		}
	}

	/**
	 * 初始化应用的公私钥
	 * @param appInfo
	 */
	private void initRsaKey(MerchantAppInfo appInfo){
		this.publicRsaKey = appInfo.getOutPublicRsakey();
		this.privateRsaKey = appInfo.getOutPrivateRsakey();
		this.testPublicRsaKey = appInfo.getTestPublicRsakey();
		this.testPrivateRsaKey = appInfo.getTestPrivateRsakey();
	}
	
	public String getShelfChannelDesc() {
		return shelfChannelDesc;
	}

	public void setShelfChannelDesc(String shelfChannelDesc) {
		this.shelfChannelDesc = shelfChannelDesc;
	}

	public String getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public String getPkgUploadPath() {
		return pkgUploadPath;
	}

	public void setPkgUploadPath(String pkgUploadPath) {
		this.pkgUploadPath = pkgUploadPath;
	}

	public String getPkgFileName() {
		return pkgFileName;
	}

	public void setPkgFileName(String pkgFileName) {
		this.pkgFileName = pkgFileName;
	}

	public String getPkgJiaguPath() {
		return pkgJiaguPath;
	}

	public void setPkgjiaguPath(String pkgJiaguPath) {
		this.pkgJiaguPath = pkgJiaguPath;
	}

	public String getPrivateRsaKey() {
		return privateRsaKey;
	}

	public void setPrivateRsaKey(String privateRsaKey) {
		this.privateRsaKey = privateRsaKey;
	}

	public String getPublicRsaKey() {
		return publicRsaKey;
	}

	public void setPublicRsaKey(String publicRsaKey) {
		this.publicRsaKey = publicRsaKey;
	}

	public String getTestPrivateRsaKey() {
		return testPrivateRsaKey;
	}

	public void setTestPrivateRsaKey(String testPrivateRsaKey) {
		this.testPrivateRsaKey = testPrivateRsaKey;
	}

	public String getTestPublicRsaKey() {
		return testPublicRsaKey;
	}

	public void setTestPublicRsaKey(String testPublicRsaKey) {
		this.testPublicRsaKey = testPublicRsaKey;
	}

	public void setPkgJiaguPath(String pkgJiaguPath) {
		this.pkgJiaguPath = pkgJiaguPath;
	}
	
	
}
