package com.dome.sdkserver.bo;

/**
 * 应用类型返回参数
 * @author liuxingyue
 *
 */
public class AppTypeAttrBo {
	/** 应用类型属性ID*/
	private int typeAttrId;
	/** 类型属性编码*/
	private String typeAttrCode;
	/** 类型属性名称*/
	private String typeAttrName;
	/** 类型属性父编码*/
	private String typeAttrParentCode;
	/** 层级*/
	private int level;
	
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getTypeAttrId() {
		return typeAttrId;
	}
	public void setTypeAttrId(int typeAttrId) {
		this.typeAttrId = typeAttrId;
	}
	public String getTypeAttrCode() {
		return typeAttrCode;
	}
	public void setTypeAttrCode(String typeAttrCode) {
		this.typeAttrCode = typeAttrCode;
	}
	public String getTypeAttrName() {
		return typeAttrName;
	}
	public void setTypeAttrName(String typeAttrName) {
		this.typeAttrName = typeAttrName;
	}
	public String getTypeAttrParentCode() {
		return typeAttrParentCode;
	}
	public void setTypeAttrParentCode(String typeAttrParentCode) {
		this.typeAttrParentCode = typeAttrParentCode;
	}

}
