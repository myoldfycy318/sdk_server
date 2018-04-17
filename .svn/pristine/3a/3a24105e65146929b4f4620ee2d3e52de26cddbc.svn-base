/**
 * 
 */
package com.dome.sdkserver.bq.constants;

/**
 * @author mazhongmin
 *
 */
public enum AccountTypeEnum {

	ACCOUNT_TYPE_BB(0,"宝币账户"),
	
	ACCOUNT_TYPE_BQ(2,"宝券账户"),
	
	ACCOUNT_TYPE_MER(7,"商户冻结账户"),
	
	CHANGE_TYPE_BB_NO(400,"宝币账户变动类型(非认证商家)"),
	
	CHANGE_TYPE_BB_YES(402,"宝币账户变动类型(认证商家)"),
	
	CHANGE_TYPE_BQ_NO(5,"宝券账户变动类型(非认证商家)"),
	
	CHANGE_TYPE_BQ_YES(7,"宝券账户变动类型(认证商家)"),
	
	CHANGE_TYPE_MER(201,"账户账户变动类型(冻结账户)");
	
	private AccountTypeEnum(int type,String desc){
		this.type = type;
		this.desc = desc;
	}
	
	private int type;
	
	private String desc;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
