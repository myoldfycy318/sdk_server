package com.dome.sdkserver.metadata.entity.statistic;

/**
 * 统计图表节点
 */
public class Node {

	/** 日期 */
	private String date;
	/** 值 */
	private Object value;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
