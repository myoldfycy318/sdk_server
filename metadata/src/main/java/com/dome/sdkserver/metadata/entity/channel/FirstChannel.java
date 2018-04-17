package com.dome.sdkserver.metadata.entity.channel;


import java.util.List;

import com.dome.sdkserver.metadata.entity.channel.Channel;

public class FirstChannel extends Channel {

	private String companyName;
	
	private String companyUrl;
	
	/**
	 * 区号
	 */
	private String zoneCode;
	
	private String telephone;
	
	/**
	 * icp备案号
	 */
	private String icpRecord;
	
	/**
	 * 增值证号Value added Certificate No
	 */
	private String vatNo;
	
	/**
	 * 增值证号图片
	 */
	private String vatPicUrl;

	/** 公司营业执照号码 */
	private String licenceNum;
	/** 公司营业执照图片地址 */
	private String licenceImageUrl;
	/** 法人代表姓名 */
	private String legalName;
	/** 法人代表姓名图片地址 */
	private String legalImageUrl;
	
	/** 税务登记证号  **/
	private String taxRegistNo;
	/** 税务登记证图片url  **/
	private String taxRegistUrl;
	/** 网络文化经营许可证号  **/
	private String networkCultureNo;
	/** 网络文化经营许可证图片url  **/
	private String networkCultureUrl;

	/** 注册资金 */
	private String registAmount;
	/** 公司性质 */
	private int companyType;
	/** 公司上市情况  0未上市  1已上市  */
	private int isListed;

	/** 公司介绍 */
	private String companyIntro;


	/**
	 * 渠道关联的推广分类id，提供给接口使用者。
	 * 后台编辑应用传递typeIds是string。因此决定传参使用Channel，避免冲突
	 */
	private List<ChannelType> typeIds;
	
	public FirstChannel(){}
	
	public FirstChannel(Channel channel){
		super(channel);
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIcpRecord() {
		return icpRecord;
	}

	public void setIcpRecord(String icpRecord) {
		this.icpRecord = icpRecord;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	public String getVatPicUrl() {
		return vatPicUrl;
	}

	public void setVatPicUrl(String vatPicUrl) {
		this.vatPicUrl = vatPicUrl;
	}

	public String getLicenceNum() {
		return licenceNum;
	}

	public void setLicenceNum(String licenceNum) {
		this.licenceNum = licenceNum;
	}

	public String getLicenceImageUrl() {
		return licenceImageUrl;
	}

	public void setLicenceImageUrl(String licenceImageUrl) {
		this.licenceImageUrl = licenceImageUrl;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLegalImageUrl() {
		return legalImageUrl;
	}

	public void setLegalImageUrl(String legalImageUrl) {
		this.legalImageUrl = legalImageUrl;
	}

	public String getTaxRegistNo() {
		return taxRegistNo;
	}

	public void setTaxRegistNo(String taxRegistNo) {
		this.taxRegistNo = taxRegistNo;
	}

	public String getTaxRegistUrl() {
		return taxRegistUrl;
	}

	public void setTaxRegistUrl(String taxRegistUrl) {
		this.taxRegistUrl = taxRegistUrl;
	}

	public String getNetworkCultureNo() {
		return networkCultureNo;
	}

	public void setNetworkCultureNo(String networkCultureNo) {
		this.networkCultureNo = networkCultureNo;
	}

	public String getNetworkCultureUrl() {
		return networkCultureUrl;
	}

	public void setNetworkCultureUrl(String networkCultureUrl) {
		this.networkCultureUrl = networkCultureUrl;
	}

	public String getRegistAmount() {
		return registAmount;
	}

	public void setRegistAmount(String registAmount) {
		this.registAmount = registAmount;
	}

	public int getCompanyType() {
		return companyType;
	}

	public void setCompanyType(int companyType) {
		this.companyType = companyType;
	}

	public int getIsListed() {
		return isListed;
	}

	public void setIsListed(int isListed) {
		this.isListed = isListed;
	}

	public String getCompanyIntro() {
		return companyIntro;
	}

	public void setCompanyIntro(String companyIntro) {
		this.companyIntro = companyIntro;
	}

	public List<ChannelType> getTypeIds() {
		return typeIds;
	}

	public void setTypeIds(List<ChannelType> typeIds) {
		this.typeIds = typeIds;
	}
	
}
