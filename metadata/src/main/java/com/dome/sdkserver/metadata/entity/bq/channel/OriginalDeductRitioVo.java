package com.dome.sdkserver.metadata.entity.bq.channel;

import java.io.Serializable;
import java.math.BigDecimal;

public class OriginalDeductRitioVo implements Serializable 
{
    
    private static final long serialVersionUID = 1L;

    /**
     * 渠道id.
     */
    private Integer channelId;
    
    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 渠道类型
     */
    private String channelType;
    
    /**
     * 合作类型
     */
    private String cooperationType;
    
    /**
     * 支付用户数量.
     */
    private Integer payUserCount;
    
    /**
     * 扣量支付用户数量
     */
    private Integer deductedPayUserCount;
    
    /**
     * 激活用户数量.
     */
    private Integer activateUserCount;
    
    /**
     * 扣量激活用户数量
     */
    private Integer deductedActivateUserCount;
    
    /**
     * 充值总金额.
     */
    private BigDecimal chargeAmount;
    
    /**
     * 扣量充值总金额
     */
    private BigDecimal deductedChargeAmount;

    
    public Integer getChannelId()
    {
        return channelId;
    }

    
    public void setChannelId(Integer channelId)
    {
        this.channelId = channelId;
    }

    
    public String getChannelName()
    {
        return channelName;
    }

    
    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
    }

    
    public String getChannelType()
    {
        return channelType;
    }

    
    public void setChannelType(String channelType)
    {
        this.channelType = channelType;
    }

    
    public String getCooperationType()
    {
        return cooperationType;
    }

    
    public void setCooperationType(String cooperationType)
    {
        this.cooperationType = cooperationType;
    }

    
    public Integer getPayUserCount()
    {
        return payUserCount;
    }

    
    public void setPayUserCount(Integer payUserCount)
    {
        this.payUserCount = payUserCount;
    }

    
    public Integer getDeductedPayUserCount()
    {
        return deductedPayUserCount;
    }

    
    public void setDeductedPayUserCount(Integer deductedPayUserCount)
    {
        this.deductedPayUserCount = deductedPayUserCount;
    }

    
    public Integer getActivateUserCount()
    {
        return activateUserCount;
    }

    
    public void setActivateUserCount(Integer activateUserCount)
    {
        this.activateUserCount = activateUserCount;
    }

    
    public Integer getDeductedActivateUserCount()
    {
        return deductedActivateUserCount;
    }

    
    public void setDeductedActivateUserCount(Integer deductedActivateUserCount)
    {
        this.deductedActivateUserCount = deductedActivateUserCount;
    }

    
    public BigDecimal getChargeAmount()
    {
        return chargeAmount;
    }

    
    public void setChargeAmount(BigDecimal chargeAmount)
    {
        this.chargeAmount = chargeAmount;
    }

    
    public BigDecimal getDeductedChargeAmount()
    {
        return deductedChargeAmount;
    }

    
    public void setDeductedChargeAmount(BigDecimal deductedChargeAmount)
    {
        this.deductedChargeAmount = deductedChargeAmount;
    }


    @Override
    public String toString()
    {
        return String.format(
            "OriginalDeductRitioVo [channelId=%s, channelName=%s, channelType=%s, cooperationType=%s, payUserCount=%s, deductedPayUserCount=%s, activateUserCount=%s, deductedActivateUserCount=%s, chargeAmount=%s, deductedChargeAmount=%s]",
            channelId, channelName, channelType, cooperationType, payUserCount,
            deductedPayUserCount, activateUserCount, deductedActivateUserCount,
            chargeAmount, deductedChargeAmount);
    }

}