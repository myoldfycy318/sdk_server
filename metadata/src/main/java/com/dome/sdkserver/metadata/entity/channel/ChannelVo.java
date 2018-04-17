package com.dome.sdkserver.metadata.entity.channel;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;

import com.dome.sdkserver.constants.channel.ChannelStatusEnum;
import com.dome.sdkserver.constants.channel.CompanyTypeEnum;
import com.dome.sdkserver.util.DateUtil;

/**
 * 传递给前台的实体类
 * 包含日期时间格式化和添加状态描述等
 * @author lilongwei
 *
 */
public class ChannelVo extends FirstChannel {

	/**
	 * 注册时间取create_time
	 */
	private String registerTime;
	
	/**
	 * 申请时间取update_time
	 */
	private String applyTime;
	
	private String statusDesc;
	
	private String companyTypeDesc;
	
	public ChannelVo(FirstChannel firstChannel){
		BeanUtils.copyProperties(firstChannel, this);
		formatDateField();
		initFieldDesc();
		
	}

	private void initFieldDesc() {
		this.statusDesc=ChannelStatusEnum.getStatusDesc(super.getStatus());
		this.companyTypeDesc=CompanyTypeEnum.getDesc(super.getCompanyType());
	}

	private void formatDateField() {
		Date createTime = super.getCreateTime();
		if (createTime!=null) this.registerTime=DateFormatUtils.format(createTime, DateUtil.DATATIMEF_STR);
		Date updateTime = super.getUpdateTime();
		if (updateTime!=null) this.applyTime=DateFormatUtils.format(updateTime, DateUtil.DATATIMEF_STR);
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getCompanyTypeDesc() {
		return companyTypeDesc;
	}

	public void setCompanyTypeDesc(String companyTypeDesc) {
		this.companyTypeDesc = companyTypeDesc;
	}
	
	
}
