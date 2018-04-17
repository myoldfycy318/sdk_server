package com.dome.sdkserver.metadata.entity.bq.award;

import java.math.BigDecimal;
import java.util.Date;


public class BqAutoSendEntity {

	// 主键id
	private String id;

	// 应用编码
	private String appCode;

	// 商户编码
	private String merchantCode;

	// 支付账号
	private String payUserId;

	// 交易日期
	private String transDate;

	// 宝币
	private BigDecimal accountAmount;

	// 宝券
	private BigDecimal bqAccountAmount;

	// 业务类型
	private int bizType;

	// 业务摘要
	private String bizDesc;

	// 宝券类型 默认 voucher
	private String bqType;

	// 商户类型 默认 qbao
	private String merchantType;

	// 发送状态 详查 SendBqStatusEnum
	private String status;

	// 返回码
	private String returnCode;

	// 返回信息
	private String returnMsg;

	// 赠送宝券数量
	private BigDecimal bqAward;

	// 创建时间
	private Date createTime;

	// 更新时间
	private Date updateTime;

	// 应用名称
	private String appName;

	// 用户名
	private String loginId;

	// 会员等级
	private String memberLevel;

    //商肃支付系统返回的交易流水号
    private String payTradeNo;

    //应用市场活动规则id
    private Integer activityConfId;

    //返劵来源,BqGame:冰穹游戏，NewBusi:新业务返劵,FuLu:福禄返劵
    private String bqSource;

    //业务类型
    private String busiType;
    //外部流水号
    private String outTradeNo;
    //手续费,人民币，单位：分
    private BigDecimal feeAmount;
    //终端 1：PC，2：IOS，3：Android
    private Integer terminal;
    //交易流水
    private String tradeNo;

    public String getPayTradeNo() {
        return payTradeNo;
    }

    public void setPayTradeNo(String payTradeNo) {
        this.payTradeNo = payTradeNo;
    }

    public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getPayUserId() {
		return payUserId;
	}

	public void setPayUserId(String payUserId) {
		this.payUserId = payUserId;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	public String getBizDesc() {
		return bizDesc;
	}

	public void setBizDesc(String bizDesc) {
		this.bizDesc = bizDesc;
	}

	public String getBqType() {
		return bqType;
	}

	public void setBqType(String bqType) {
		this.bqType = bqType;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public BigDecimal getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	}

	public BigDecimal getBqAccountAmount() {
		return bqAccountAmount;
	}

	public void setBqAccountAmount(BigDecimal bqAccountAmount) {
		this.bqAccountAmount = bqAccountAmount;
	}

	public BigDecimal getBqAward() {
		return bqAward;
	}

	public void setBqAward(BigDecimal bqAward) {
		this.bqAward = bqAward;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

    public Integer getActivityConfId() {
        return activityConfId;
    }

    public void setActivityConfId(Integer activityConfId) {
        this.activityConfId = activityConfId;
    }

    public String getBqSource() {
        return bqSource;
    }

    public void setBqSource(String bqSource) {
        this.bqSource = bqSource;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }
}
