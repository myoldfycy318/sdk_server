package com.dome.sdkserver.view;

import org.apache.commons.lang3.StringUtils;

import com.dome.sdkserver.common.Constants;

public abstract class RequestParamHandler {

	/**
	 * 获取请求中的状态参数数值
	 * 接口调用过程中会出现status=情况，譬如http://openba.domestore.cn/channel/settle/list?name=&status=&pageNo=1&pageSize=15
	 * 故请求参数传递的status为string，对应对象的status字段。而状态数值对应对象的statusVal字段。
	 * 
	 * @param statusParam
	 * @return
	 */
	public static int getStatus(String statusParam){
		int status = 0;
		if (StringUtils.isNotBlank(statusParam) && Constants.PATTERN_NUM.matcher(statusParam).matches()){
			status = Integer.parseInt(statusParam);
		}
		return status;
	}
}
