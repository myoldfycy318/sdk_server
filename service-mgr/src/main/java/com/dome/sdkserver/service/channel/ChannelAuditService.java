package com.dome.sdkserver.service.channel;

import java.util.List;
import java.util.Set;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.entity.channel.ChannelQueryParam;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;
import com.dome.sdkserver.metadata.entity.channel.JieSuanConfig;

public interface ChannelAuditService {

	/**
	 * 
	 * @param channelCode
	 * @param needTypeIds 是否需要推广分类信息
	 * @return
	 */
	FirstChannel selectByCode(String channelCode, boolean needTypeIds);
	
	FirstChannel select(long id, long userId);
	
	List<FirstChannel> selectList(ChannelQueryParam channelQueryParam, Paginator paginator);
	
	int selectChannelsCount(ChannelQueryParam channelQueryParam);
	
	String updateStatus(long id, int status);
	
	int updateChannel(FirstChannel firstChannel);
	
	String pass(JieSuanConfig jsConfig, String typeIds);
	
	int delete(FirstChannel channel);
	
	public String synToSdk(String channelCode, long channelId, Set<Long> beforeGameIdSet);
}
