package com.dome.sdkserver.constants;
/**
 * 公司性质
 * @author liuxingyue
 *
 */
public enum MerchantTypeEnum {
	MERCHANT_TYPE_10("10","国有企业"),
	
	MERCHANT_TYPE_20("20","私营企业"),
	
	MERCHANT_TYPE_30("30","台资"),
	
	MERCHANT_TYPE_40("40","外企");
	
	private String merchantTypeCode;
	
	private String merchantTypeName;

	private MerchantTypeEnum(){}
	
	private MerchantTypeEnum(String merchantTypeCode, String merchantTypeName) {
		this.merchantTypeCode = merchantTypeCode;
		this.merchantTypeName = merchantTypeName;
	}

	public final static MerchantTypeEnum getFromKey(String merchantTypeCode){
		for(MerchantTypeEnum e : MerchantTypeEnum.values()){
			if(e.getMerchantTypeCode().equals(merchantTypeCode)){
				return e;
			}
		}
		return null;
		
	}
	public String getMerchantTypeCode() {
		return merchantTypeCode;
	}

	public void setMerchantTypeCode(String merchantTypeCode) {
		this.merchantTypeCode = merchantTypeCode;
	}

	public String getMerchantTypeName() {
		return merchantTypeName;
	}

	public void setMerchantTypeName(String merchantTypeName) {
		this.merchantTypeName = merchantTypeName;
	}
	
	
	
	
}
