package com.dome.sdkserver.metadata.entity.channel;

import java.math.BigDecimal;

import com.dome.sdkserver.metadata.entity.Base;


public class Channel extends Base{

	private String name;
	
	private String channelCode;
	
	private transient long id;
	/**
	 * 合作方式
	 */
	private int cooperType;
	
	private String email;
	
	private String qq;
	
	private String mobilePhone;
	
	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 联系地址
	 */
	private String address;
	
	private transient long userId;
	
//	/**
//	 * 审批状态，1待审核 2审核通过  3已驳回
//	 */
//	private int status;
//	
//	private transient int delFlag;
	
	private String remark;
	
	private transient String registerIp;

	/**
	 * 开户行
	 */
	private String bankName;
	
	/**
	 * 开户账号
	 */
	private String bankAccount;
	
	/**
	 * 一级渠道id
	 */
	private long parentId;
	

	private BigDecimal dividePercent;
	
	private BigDecimal activityUnitPrice;

	
	/**
	 * 代码方法用来传递typeIds参数，不需要展现给接口使用者
	 */
	private transient String typeIdStr;
	public Channel(){
		
	}
	
	public Channel(Channel channel){
		this.userId = channel.getUserId();
		this.registerIp = channel.getRegisterIp();
		this.cooperType = channel.getCooperType();
		this.name = channel.getName();
		this.email=channel.getEmail();
		this.qq=channel.getQq();
		this.mobilePhone=channel.getMobilePhone();
		this.address=channel.getAddress();
		this.contact=channel.getContact();
		long id = channel.getId();
		if (id!=0){
			this.id=id;
		}
		String channelCode = channel.getChannelCode();
		if (channelCode!=null && !"".equals(channelCode)){
			this.channelCode=channelCode;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCooperType() {
		return cooperType;
	}

	public void setCooperType(int cooperType) {
		this.cooperType = cooperType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public BigDecimal getDividePercent() {
		return dividePercent;
	}

	public void setDividePercent(BigDecimal dividePercent) {
		this.dividePercent = dividePercent;
	}

	public BigDecimal getActivityUnitPrice() {
		return activityUnitPrice;
	}

	public void setActivityUnitPrice(BigDecimal activityUnitPrice) {
		this.activityUnitPrice = activityUnitPrice;
	}


	public String getTypeIdStr() {
		return typeIdStr;
	}

	public void setTypeIdStr(String typeIdStr) {
		this.typeIdStr = typeIdStr;
	}

	
}
