package com.dome.sdkserver.service.pay.qbao.bo;

import com.dome.sdkserver.metadata.entity.qbao.CurrencyEntity;


/**
 * 用户支付信息返回结果
 * @author liuxingyue
 *
 */
public class SdkPayRequestData{
	public SdkPayRequestData() {
		super();
	}
	
	
	/** sdk收单流水号*/
	 private String sdkflowId;
	 /** 是否可用宝券 0 不可用 1 可用*/
     private Integer useBqFlag;
     /** 钱宝金额*/
     private Long qbAmount;
     /** 宝券金额*/
     private Long bqAmount;
     /** 是否短信验证 0 不需要 1 需要*/
     private Integer msgFlag;
     /** 创建时间**/
     private String transDate;
     /** 道具名称**/
     private String propName;
     /** 道具价格**/
     private double propPrice;
     /**
      * 货币信息
      */
    private CurrencyEntity currency;
 	
 	public CurrencyEntity getCurrency() {
 		return currency;
 	}
 	public void setCurrency(CurrencyEntity currency) {
 		this.currency = currency;
 	}
     
     


	public String getSdkflowId() {
		return sdkflowId;
	}
	public void setSdkflowId(String sdkflowId) {
		this.sdkflowId = sdkflowId;
	}
	public Integer getUseBqFlag() {
		return useBqFlag;
	}
	public void setUseBqFlag(Integer useBqFlag) {
		this.useBqFlag = useBqFlag;
	}
	public Long getQbAmount() {
		return qbAmount;
	}
	public void setQbAmount(Long qbAmount) {
		this.qbAmount = qbAmount;
	}
	public Long getBqAmount() {
		return bqAmount;
	}
	public void setBqAmount(Long bqAmount) {
		this.bqAmount = bqAmount;
	}
	public Integer getMsgFlag() {
		return msgFlag;
	}
	public void setMsgFlag(Integer msgFlag) {
		this.msgFlag = msgFlag;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public double getPropPrice() {
		return propPrice;
	}

	public void setPropPrice(double propPrice) {
		this.propPrice = propPrice;
	}

     
}
