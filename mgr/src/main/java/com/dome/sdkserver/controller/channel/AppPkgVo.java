package com.dome.sdkserver.controller.channel;

import org.springframework.beans.BeanUtils;

import com.dome.sdkserver.constants.channel.PkgStatusEnum;
import com.dome.sdkserver.metadata.entity.channel.AppPkg;

/**
 * 仅供APP渠道打包管理界面使用
 * @author lilongwei
 *
 */
public class AppPkgVo extends AppPkg{

	private String statusDesc;
	
	public AppPkgVo() {
	}
	
	public AppPkgVo(AppPkg ap) {
		BeanUtils.copyProperties(ap, this);
		this.statusDesc = PkgStatusEnum.getStatusDesc(ap.getStatus());
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
	
}
