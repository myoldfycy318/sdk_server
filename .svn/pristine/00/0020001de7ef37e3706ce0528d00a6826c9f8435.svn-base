package com.dome.sdkserver.metadata.dao.mapper.channel;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.bo.DiscountThreshold;
import com.dome.sdkserver.metadata.entity.channel.JieSuanConfig;

public interface ChannelConfigMapper {
	/**
	 * 
	 * @param channelId
	 * @param date
	 * @return
	 */
	JieSuanConfig selectJsConfig(long channelId, String date);
	
	DiscountThreshold selectDiscountThreshold(@Param("code")String channelCode, @Param("date")String date);
	
	int selectCooperType(long channelId, String date);
}
