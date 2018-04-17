package com.dome.sdkserver.service.channel;

import java.util.List;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.entity.channel.Channel;
import com.dome.sdkserver.metadata.entity.channel.ChannelQueryParam;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;
import com.dome.sdkserver.metadata.entity.channel.SecondChannel;

public interface ChannelService {
	
	public static final String CHANNEL_CODE_PREFIX = "CHA";
	long addFirstChannel(FirstChannel firstChannel);
	
	long addSecondChannel(SecondChannel secondChannel);
	
	FirstChannel select(long id, long userId);
	
	FirstChannel selectByCode(String channelCode);
	
	int updateStatus(long id, int status);
	
	int updateFirstChannel(FirstChannel firstChannel);
	
	int updateSecondChannel(SecondChannel secondChannel);
	
	int selectCount(long parentId, ChannelQueryParam param);
	
	List<FirstChannel> selectList(long parentId, ChannelQueryParam param,
		Paginator paginator);
	
	String checkUserRegister(long id, String passport);
	
	/**
	 * 根据渠道名查询渠道。不支持模糊查询
	 * @param name
	 * @return
	 */
	Channel selectByName(String name);
}
