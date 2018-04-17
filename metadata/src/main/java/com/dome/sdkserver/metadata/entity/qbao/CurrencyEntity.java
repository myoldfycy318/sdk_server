package com.dome.sdkserver.metadata.entity.qbao;

public class CurrencyEntity {
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 货比名称
	 */
	private String currencyName;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 与1分钱的比例 (如 1元=100分) 
	 */
	private Integer scale;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getScale() {
		return scale;
	}
	public void setScale(Integer scale) {
		this.scale = scale;
	}
	
	
	
	
}
