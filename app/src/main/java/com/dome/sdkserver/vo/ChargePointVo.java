package com.dome.sdkserver.vo;

import java.util.List;

import com.dome.sdkserver.bo.ChargePointInfo;

/**
 * 计费点列表
 * @author liuxingyue
 *
 */
public class ChargePointVo {
	private Integer totalCount;
	
	private List<ChargePointInfo> chargePointInfoList;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<ChargePointInfo> getChargePointInfoList() {
		return chargePointInfoList;
	}

	public void setChargePointInfoList(List<ChargePointInfo> chargePointInfoList) {
		this.chargePointInfoList = chargePointInfoList;
	}

}
