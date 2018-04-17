package com.dome.sdkserver.metadata.dao.mapper.channel;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.metadata.dao.IBaseMapper;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;

public interface FirstChannelMapper extends IBaseMapper{

	long add(@Param("ch")FirstChannel firstChannel);
	
	/**
	 * 更新时使用code为条件
	 * 插入一条记录后，更新渠道编码使用方法updateChannelCode(long id, String channelCode)
	 * @param firstChannel
	 * @return
	 */
	int update(@Param("ch")FirstChannel firstChannel);

	/**
	 * 插入一条渠道数据后，更新渠道编码
	 * @param id
	 * @param channelCode
	 * @return
	 */
	int updateChannelCode(@Param("id")long id, @Param("channelCode")String channelCode);
	
	/**
	 * 统计一级渠道的数量，del_flag=1不统计
	 * @return
	 */
	int selectFirstChannelCount();
}
