package com.dome.sdkserver.metadata.entity.channel;

import java.math.BigDecimal;

import com.dome.sdkserver.metadata.entity.Base;

public class JieSuanConfig extends Base{

	private long id;
	
	private long channelId;
	
	private BigDecimal dividePercent;
	
	private BigDecimal activityUnitPrice;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	public BigDecimal getDividePercent() {
		return dividePercent;
	}

	public void setDividePercent(BigDecimal dividePercent) {
		this.dividePercent = dividePercent;
	}

	public BigDecimal getActivityUnitPrice() {
		return activityUnitPrice;
	}

	public void setActivityUnitPrice(BigDecimal activityUnitPrice) {
		this.activityUnitPrice = activityUnitPrice;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof JieSuanConfig){
			JieSuanConfig js = (JieSuanConfig)obj;
			if (js.getChannelId()==this.channelId){
				BigDecimal activityUnitPrice2 = js.getActivityUnitPrice();
				if ((this.activityUnitPrice==null && activityUnitPrice2==null) ||
						this.activityUnitPrice!=null && activityUnitPrice2!=null && this.activityUnitPrice.compareTo(activityUnitPrice2)==0){
					BigDecimal dividePercent2 = js.getDividePercent();
					if ((this.dividePercent==null && dividePercent2==null) ||
							this.dividePercent!=null && dividePercent2!=null && this.dividePercent.compareTo(dividePercent2)==0){
						return true;
						
					}
				}
			}
		}
		return false;
	};
}
