package com.dome.sdkserver.bo;

import java.util.Date;

/**
 * 商户信息对象
 * @author hexiaoyi
 */
public class MerchantInfo {
	/** 商户id */
	private transient Integer merchantInfoId;
	/** 商户全称 */
	private String merchantFullName;
	/** 商户编码 */
	private String merchantCode;
	/** 商户注册类型 ：1 企业开发 2 个人开发  */
	private Integer merchantRegType;
	/** 商户网址 */
	private String merchantUrl;
	/** 联系人 */
	private String contacts;
	/** 手机号 */
	private String mobilePhoneNum;
	/** 电话区号 */
	private String phoneAreaNum;
	/** 电话 */
	private String phoneNum;
	/** 组织机构代码 */
	private String orgNum;
	/** 组织机构代码图片地址 */
	private String orgImageUrl;
	/** 商户营业执照号码 */
	private String licenceNum;
	/** 商户营业执照图片地址 */
	private String licenceImageUrl;
	/** 法人代表姓名 */
	private String legalName;
	/** 法人代表姓名图片地址 */
	private String legalImageUrl;
	/** 注册资金 */
	private String registAmount;
	/** 公司性质 */
	private String merchantType;
	/** 公司上市情况 */
	private Integer isListed;
	/** qq号 */
	private String qqNum;
	/** 商户简称 */
	private String merchantIntro;
	/** 账户系统用户ID */
	private transient Integer userId;
	/** 申请人登录账号 */
	private String applyUserAccount;
	/** 状态：1 待审核 2 已通过 3 未通过 */
	private Integer status;
	/** 删除标识：0 未删除 1 已删除 */
	private Integer delFlag;
	/** 备注 */
	private String remark;
	/** 创建人IP */
	private transient String createIp;
	/** 审批人ID */
	private transient Integer auditorId;
	/** 申请时间 */
	private String applyDate;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	
	/** 税务登记证号  **/
	private String taxRegistNo;
	/** 税务登记证图片url  **/
	private String taxRegistUrl;
	/** 网络文化经营许可证号  **/
	private String networkCultureNo;
	/** 网络文化经营许可证图片url  **/
	private String networkCultureUrl;
	/** 计算机软件著作权号  **/
	private String compareSoftwareNo;
	/** 计算机软件著作权图片url  **/
	private String compareSoftwareUrl;
	
	private String email;
	
	private String statusDesc;
	
	public Integer getMerchantInfoId() {
		return merchantInfoId;
	}

	public void setMerchantInfoId(Integer merchantInfoId) {
		this.merchantInfoId = merchantInfoId;
	}

	public String getMerchantFullName() {
		return merchantFullName;
	}

	public void setMerchantFullName(String merchantFullName) {
		this.merchantFullName = merchantFullName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public Integer getMerchantRegType() {
		return merchantRegType;
	}

	public void setMerchantRegType(Integer merchantRegType) {
		this.merchantRegType = merchantRegType;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getMobilePhoneNum() {
		return mobilePhoneNum;
	}

	public void setMobilePhoneNum(String mobilePhoneNum) {
		this.mobilePhoneNum = mobilePhoneNum;
	}

	public String getPhoneAreaNum() {
		return phoneAreaNum;
	}

	public void setPhoneAreaNum(String phoneAreaNum) {
		this.phoneAreaNum = phoneAreaNum;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getLicenceImageUrl() {
		return licenceImageUrl;
	}

	public void setLicenceImageUrl(String licenceImageUrl) {
		this.licenceImageUrl = licenceImageUrl;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	public String getMerchantIntro() {
		return merchantIntro;
	}

	public void setMerchantIntro(String merchantIntro) {
		this.merchantIntro = merchantIntro;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getApplyUserAccount() {
		return applyUserAccount;
	}

	public void setApplyUserAccount(String applyUserAccount) {
		this.applyUserAccount = applyUserAccount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public Integer getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(String orgNum) {
		this.orgNum = orgNum;
	}

	public String getOrgImageUrl() {
		return orgImageUrl;
	}

	public void setOrgImageUrl(String orgImageUrl) {
		this.orgImageUrl = orgImageUrl;
	}

	public String getLicenceNum() {
		return licenceNum;
	}

	public void setLicenceNum(String licenceNum) {
		this.licenceNum = licenceNum;
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

	public String getRegistAmount() {
		return registAmount;
	}

	public void setRegistAmount(String registAmount) {
		this.registAmount = registAmount;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public Integer getIsListed() {
		return isListed;
	}

	public void setIsListed(Integer isListed) {
		this.isListed = isListed;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getCompareSoftwareNo() {
		return compareSoftwareNo;
	}

	public void setCompareSoftwareNo(String compareSoftwareNo) {
		this.compareSoftwareNo = compareSoftwareNo;
	}

	public String getCompareSoftwareUrl() {
		return compareSoftwareUrl;
	}

	public void setCompareSoftwareUrl(String compareSoftwareUrl) {
		this.compareSoftwareUrl = compareSoftwareUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	@Override
	public String toString() {
		return "MerchantInfo [merchantCode=" + merchantCode
				+ ", merchantFullName=" + merchantFullName + ", status=" + status + "]";
	}
	
}
