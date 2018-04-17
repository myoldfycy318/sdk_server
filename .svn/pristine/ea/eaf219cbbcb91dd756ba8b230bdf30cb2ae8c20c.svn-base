package com.dome.sdkserver.metadata.dao.mapper.bq.channel;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dome.sdkserver.bo.DiscountThreshold;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import com.dome.sdkserver.metadata.entity.bq.channel.BalanceAmountEntity;

@Repository
public interface BalanceAmountEntityMapper extends IBaseMapperDao<BalanceAmountEntity, Integer>{
    
    BalanceAmountEntity selectSettleAmount(@Param("channelId")Integer channelId, @Param("beginDate")Date beginDate, @Param("endDate")Date endDate);

    /**
     * 
     *  函数名称 : selectCount
     *  功能描述 :  
     *  参数及返回值说明：
     *  	@param channelId
     *  	@param channelName
     *  	@param beginDate
     *  	@param endDate
     *  	@param cooperationType
     *  	@return
     *
     *  修改记录：
     *  	日期 ：2016年8月23日 下午4:17:43	修改人：  
     *  	描述	：
     *
     */
    int selectCount(@Param("channelId")Integer channelId, @Param("channelName")String channelName,
        @Param("beginDate")Date beginDate, @Param("endDate")Date endDate, @Param("cooperationType")Integer cooperationType);
    
    /**
     * 
     *  函数名称 : select
     *  功能描述 :  
     *  参数及返回值说明：
     *  	@param channelId
     *  	@param channelName
     *  	@param beginDate
     *  	@param endDate
     *  	@param cooperationType
     *  	@return
     *
     *  修改记录：
     *  	日期 ：2016年8月23日 下午4:17:47	修改人：  
     *  	描述	：
     *
     */
    List<BalanceAmountEntity> select(@Param("channelId")Integer channelId, @Param("channelName")String channelName,
        @Param("beginDate")Date beginDate, @Param("endDate")Date endDate, @Param("cooperationType")Integer cooperationType,
        @Param("p")Paginator paginator);
    
    /**
     * 
     *  函数名称 : selectDetailCount
     *  功能描述 :  
     *  参数及返回值说明：
     *  	@param channelId
     *  	@param beginDate
     *  	@param endDate
     *  	@return
     *
     *  修改记录：
     *  	日期 ：2016年8月29日 上午10:50:07	修改人：  
     *  	描述	：
     *
     */
    int selectDetailCount(@Param("channelId")Integer channelId, @Param("beginDate")Date beginDate, @Param("endDate")Date endDate);
    
    /**
     * 
     *  函数名称 : selectDetail
     *  功能描述 :  
     *  参数及返回值说明：
     *  	@param channelId
     *  	@param beginDate
     *  	@param endDate
     *  	@return
     *
     *  修改记录：
     *  	日期 ：2016年8月29日 上午10:50:11	修改人：  
     *  	描述	：
     *
     */
    List<BalanceAmountEntity> selectDetail(@Param("channelId")Integer channelId, @Param("beginDate")Date beginDate, @Param("endDate")Date endDate,
            @Param("p")Paginator paginator);
    
    /**
     * 查询表中已统计的最大日期
     * @return
     */
    String selectMaxTjDate();
    
    /**
     * 查询某天需要统计数据的渠道列表，返回的是channelCode List
     * @param tjDate
     * @return
     */
    List<String> selectChannelCodeList(@Param("tjDate")String tjDate);
    
    /**
     * 同步数据到domesdk_balance_amount表中
     * 一次同步一个渠道的一天数据
     * @param beginDate
     */
    void batchSynTjData(@Param("tjDate")String tjDate, @Param("d")DiscountThreshold discount);
    
}