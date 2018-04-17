package com.dome.sdkserver.bo;


import java.io.Serializable;
import java.util.Date;

/**
 * 和宝玩渠道同步商户信息使用到的实体类
 * @author l
 *
 */
public class MerchantVo implements Serializable {
    /**
     * 商户id.
     */
    private Integer id;

    /**
     * 商户全称.
     */
    private String merchantFullName;

    /**
     * 商户编码.
     */
    private String merchantCode;

    /**
     * 商户网址.
     */
    private String merchantUrl;

    /**
     * 联系人.
     */
    private String contacts;

    /**
     * 手机号.
     */
    private String mobilePhoneNum;

    /**
     * 电话区号.
     */
    private String phoneAreaNum;

    /**
     * 电话.
     */
    private String phoneNum;

    /**
     * 注册资金.
     */
    private String registAmount;

    /**
     * 公司性质.
     */
    private String merchantType;

    /**
     * 公司上市情况 : 0 未上市 1 已上市.
     */
    private Byte isListed;

    /**
     * 组织机构代码.
     */
    private String orgNum;

    /**
     * 组织机构代码图片地址.
     */
    private String orgImageUrl;

    /**
     * 商户营业执照号码.
     */
    private String licenceNum;

    /**
     * 商户营业执照图片地址.
     */
    private String licenceImageUrl;

    /**
     * 法人代表姓名.
     */
    private String legalName;

    /**
     * 法人代表姓名图片地址.
     */
    private String legalImageUrl;

    /**
     * qq号.
     */
    private String qqNum;

    /**
     * 商户注册类型 ：1 企业开发 2 个人开发.
     */
    private int merchantRegType;

    /**
     * 公司介绍.
     */
    private String merchantIntro;

    /**
     * 商户用户ID.
     */
    private Integer merchantUserId;

    /**
     * 申请者钱宝账号.
     */
    private String applyUserAccount;

    /**
     * 状态：1 待审核 2 已通过 3 未通过 .
     */
    private int status;

    /**
     * 备注：驳回原因.
     */
    private String remark;

    /**
     * 删除标识：0 未删除 1 已删除.
     */
    private int delFlag;

    /**
     * 创建人IP.
     */
    private String createIp;

    /**
     * 审批人ID.
     */
    private Integer auditorId;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 更新时间.
     */
    private Date updateTime;

    /**
     * 税务登记证号.
     */
    private String taxRegistNo;

    /**
     * 税务登记证号图片.
     */
    private String taxRegistUrl;

    /**
     * 网络文化经营许可证号.
     */
    private String networkCultureNo;

    /**
     * 网络文化经营许可证图片.
     */
    private String networkCultureUrl;

    /**
     * 计算机软件著作权号.
     */
    private String compareSoftwareNo;

    /**
     * 计算机软件著作权号图片.
     */
    private String compareSoftwareUrl;

    /**
     * 联系人邮箱.
     */
    private String email;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Byte getIsListed() {
        return isListed;
    }

    public void setIsListed(Byte isListed) {
        this.isListed = isListed;
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

    public Integer getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(Integer merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public String getApplyUserAccount() {
        return applyUserAccount;
    }

    public void setApplyUserAccount(String applyUserAccount) {
        this.applyUserAccount = applyUserAccount;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getMerchantRegType() {
        return merchantRegType;
    }

    public void setMerchantRegType(int merchantRegType) {
        this.merchantRegType = merchantRegType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
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
}