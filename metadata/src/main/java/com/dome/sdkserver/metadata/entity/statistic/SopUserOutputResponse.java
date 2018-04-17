package com.dome.sdkserver.metadata.entity.statistic;

import java.math.BigDecimal;
import java.util.List;

/**
 * SOP 用户产出--响应
 * 
 * @author xuefeihu
 *
 */
public class SopUserOutputResponse {

	/** 近日概况 */
	private List<SdkTrans> currentList;

	/** 付费金额 */
	private List<Node> payAmountList;

	/** 付费次数 */
	private List<Node> payTimesList;

	/** 付费用户 */
	private List<Node> payUserList;

	/** 付费转化率 */
	private List<Node> convertRateList;

	/** arpuList */
	private List<Node> arpuList;

	/** arppuList */
	private List<Node> arppuList;

	public List<SdkTrans> getCurrentList() {
		return currentList;
	}

	public void setCurrentList(List<SdkTrans> currentList) {
		this.currentList = currentList;
	}

	public List<Node> getPayAmountList() {
		return payAmountList;
	}

	public void setPayAmountList(List<Node> payAmountList) {
		this.payAmountList = payAmountList;
	}

	public List<Node> getPayTimesList() {
		return payTimesList;
	}

	public void setPayTimesList(List<Node> payTimesList) {
		this.payTimesList = payTimesList;
	}

	public List<Node> getPayUserList() {
		return payUserList;
	}

	public void setPayUserList(List<Node> payUserList) {
		this.payUserList = payUserList;
	}

	public List<Node> getConvertRateList() {
		return convertRateList;
	}

	public void setConvertRateList(List<Node> convertRateList) {
		this.convertRateList = convertRateList;
	}

	public List<Node> getArpuList() {
		return arpuList;
	}

	public void setArpuList(List<Node> arpuList) {
		this.arpuList = arpuList;
	}

	public List<Node> getArppuList() {
		return arppuList;
	}

	public void setArppuList(List<Node> arppuList) {
		this.arppuList = arppuList;
	}





}
