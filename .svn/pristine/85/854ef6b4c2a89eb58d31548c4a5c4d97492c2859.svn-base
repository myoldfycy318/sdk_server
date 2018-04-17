package com.dome.sdkserver.service;

import java.util.List;
import java.util.Set;

import com.dome.sdkserver.bo.DiscountThreshold;



/**
 * Created by heyajun on 2016/8/5.
 */
public interface DiscountThresholdService{
    /**
     * 设置CPA
     * @param dt
     */
    void setCpa(DiscountThreshold dt);
   
    
    /**
     * 修改CPA
     * @param dt
     */
    void changeCpa(DiscountThreshold dt) ;
    
    /**
     * 设置CPS
     * @param dt
     */
    void setCps(DiscountThreshold dt);
    
    /**
     * 修改CPS
     * @param dt
     */
    void changeCps(DiscountThreshold dt);
    
    /**
     * 查询CPA/CPS 的创建时间
     * @param dt
     */
    DiscountThreshold searchCreateTime(DiscountThreshold dt);
    
    
    /**
     * 查询CPA/CPS 的修改时间
     * @param dt
     */
    DiscountThreshold searchUpdateTime(DiscountThreshold dt);
    
    /**
     * 查询渠道扣量信息
     * @param dt
     * @param pageSize
     * @param begin
     */ 
    List<DiscountThreshold> searchChannel(DiscountThreshold dt, Integer pageSize, Integer begin);

    /**
     * 查询渠道总数
     * @param dt
     */
    int countSecond(DiscountThreshold dt);


    /**
     * 向扣量表中查询channelCode
     */
   Set<String> selectDiscountTable();

    /**
     * 向domesdk_second_channel表中查询渠道code
     *
     */
    Set<String> selectSecondTable();

    /**
     * 向表中插入渠道code
     * @param dt
     */
    int insertChannelCode(DiscountThreshold dt);

}
