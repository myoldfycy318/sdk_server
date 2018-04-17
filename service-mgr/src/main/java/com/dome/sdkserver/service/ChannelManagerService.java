package com.dome.sdkserver.service;

import com.dome.sdkserver.bo.ChannelManager;
import com.dome.sdkserver.bo.QueryPageEntity;
import com.dome.sdkserver.bq.util.PageDto;

import java.util.Map;

public interface ChannelManagerService {
//    PageDto<ChannelManager> queryList(ChannelManager channelManager, Integer pageNo, Integer pageSize,boolean isPage);
    PageDto<ChannelManager> queryList(QueryPageEntity<ChannelManager> entity);

    Map<String,String> autoCreateChannelCode();

    int deleteChannelCode(Integer id);

    int addChannle(ChannelManager channelManager);
}
