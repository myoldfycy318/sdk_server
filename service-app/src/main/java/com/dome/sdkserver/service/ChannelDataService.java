package com.dome.sdkserver.service;

import java.util.Date;
import java.util.List;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.entity.bq.channel.BalanceAmountVo;
import com.dome.sdkserver.metadata.entity.bq.channel.SettleAmountDto;

/**
 * **********************************************************
 *  内容摘要	：<p>
 *
 *  作者	：94841
 *  创建时间	：2016年8月10日 下午2:44:01 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2016年8月10日 下午2:44:01 	修改人：
 *  	描述	:
 ***********************************************************
 */
public interface ChannelDataService
{
    /**
     * 
     *  函数名称 : select
     *  功能描述 :  
     *  参数及返回值说明：
     *  	@param pageNo
     *  	@param pageSize
     *  	@param channelId
     *  	@param channelName
     *  	@param beginDate
     *  	@param endDate
     *  	@param cooperationType
     *  	@return
     *
     *  修改记录：
     *  	日期 ：2016年8月23日 下午4:18:12	修改人：  
     *  	描述	：
     *
     */
    List<BalanceAmountVo> select(Paginator paginator, Integer channelId, 
        String channelName, Date beginDate, Date endDate, Integer cooperationType);
    
    
    /**
     * 
     *  函数名称 : select
     *  功能描述 :  
     *  参数及返回值说明：
     * @param channelId
     * @param channelName
     * @param beginDate
     * @param endDate
     * @param cooperationType
     *  	@return
     *
     *  修改记录：
     *  	日期 ：2016年8月23日 下午4:18:22	修改人：  
     *  	描述	：
     *
     */
    int selectCount(Integer channelId, String channelName, Date beginDate, 
        Date endDate, Integer cooperationType);
    
    /**
     * 
     *  函数名称 : selectDetail
     *  功能描述 :  
     *  参数及返回值说明：
     *  	@param pageNo
     * @param pageSize
     * @param channelId
     * @param cooperationType TODO
     * @param beginDate
     * @param endDate
     * @param cooperationType
     *  	@return
     *
     *  修改记录：
     *  	日期 ：2016年8月29日 上午10:46:52	修改人：  
     *  	描述	：
     *
     */
    List<BalanceAmountVo> selectDetail(Paginator paginator, Integer channelId,
        Integer cooperationType, Date beginDate, Date endDate);
    
    /**
     * 
     *  函数名称 : selectCount
     *  功能描述 :  
     *  参数及返回值说明：
     *  	@param channelId
     *  	@param beginDate
     *  	@param endDate
     *  	@param cooperationType
     *  	@return
     *
     *  修改记录：
     *  	日期 ：2016年8月29日 上午10:46:59	修改人：  
     *  	描述	：
     *
     */
    int selectDetailCount(Integer channelId, Date beginDate, Date endDate);
    
    
    /**
     * 
     *  函数名称 : selectSettleAmount
     *  功能描述 :  
     *  参数及返回值说明：
     *  	@param channelId
     *  	@param beginDate
     *  	@param endDate
     *  	@return
     *
     *  修改记录：
     *  	日期 ：2016年8月23日 下午4:18:32	修改人：  
     *  	描述	：
     *
     */
    SettleAmountDto selectSettleAmount(Integer channelId, Date beginDate, Date endDate);
    
    void synTjData();
}
