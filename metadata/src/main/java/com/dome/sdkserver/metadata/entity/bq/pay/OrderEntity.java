package com.dome.sdkserver.metadata.entity.bq.pay;

import java.util.Date;

public class OrderEntity {
	private String orderNo;
	
	private String appCode;
	
	private String appName;
	
	private String buyerId;
	
	private String chargePointCode;
	
	private double chargePointAmount;

	private String chargePointName;
	//1-支付宝，2.钱宝 3-银联，4-微信
	private int payType;
	
	private Date createTime;
	
	private Date finishTime;
	
	private int orderStatus;
	
	private String tradeNo;
	
	private String gameOrderNo;
	
	private String payNotifyUrl;
	
	private String curMonth;
	
	private String channelCode;

	private String bwUserId;
    //支付来源:pc,wap,app
    private String payOrigin;
    //扩展字段
    private String extraField;
    //支付渠道
    private String payChannel;
    //游戏嵌入平台标识
    private String platformCode;
    //扩展字段2
    private String extraField2;
    //买家账户
    private String buyerAccount;
    //系统类型:IOS|AD|PC|WAP
    private String sysType;
    //cp响应状态,0:未通知cp，1:cp响应成功,2:cp响应失败
    private Integer callbackStatus;
    //游戏角色id
    private String roleId;
	//游戏角色id
	private String gRoleId;
	//区服id
	private String zoneId;
	//区服名称
	private String zoneName;

	private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

	public String getCurMonth() {
		return curMonth;
	}

	public void setCurMonth(String curMonth) {
		this.curMonth = curMonth;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public int  getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getChargePointCode() {
		return chargePointCode;
	}

	public void setChargePointCode(String chargePointCode) {
		this.chargePointCode = chargePointCode;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	

	public double getChargePointAmount() {
		return chargePointAmount;
	}

	public void setChargePointAmount(double chargePointAmount) {
		this.chargePointAmount = chargePointAmount;
	}

	public String getChargePointName() {
		return chargePointName;
	}

	public void setChargePointName(String chargePointName) {
		this.chargePointName = chargePointName;
	}

	public String getGameOrderNo() {
		return gameOrderNo;
	}

	public void setGameOrderNo(String gameOrderNo) {
		this.gameOrderNo = gameOrderNo;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
  	public String getBwUserId() {
        return bwUserId;
    }

    public void setBwUserId(String bwUserId) {
        this.bwUserId = bwUserId;
    }

    public String getPayOrigin() {
        return payOrigin;
    }

    public void setPayOrigin(String payOrigin) {
        this.payOrigin = payOrigin;
    }

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getExtraField2() {
        return extraField2;
    }

    public void setExtraField2(String extraField2) {
        this.extraField2 = extraField2;
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public Integer getCallbackStatus() {
		return callbackStatus;
	}

	public void setCallbackStatus(Integer callbackStatus) {
		this.callbackStatus = callbackStatus;
	}

	public String getgRoleId() {
		return gRoleId;
	}

	public void setgRoleId(String gRoleId) {
		this.gRoleId = gRoleId;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
}
