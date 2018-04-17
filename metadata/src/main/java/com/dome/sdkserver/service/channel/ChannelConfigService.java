package com.dome.sdkserver.service.channel;

import java.util.Set;

import com.dome.sdkserver.constants.channel.ChannelConfigItemEnum;
import com.dome.sdkserver.metadata.entity.channel.ChannelConfig;

public interface ChannelConfigService {
	
	 ChannelConfig queryConfig(long channelId, String date,
			 Set<ChannelConfigItemEnum> itemEnumList); 
}
