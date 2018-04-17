package com.dome.sdkserver.service.channel;

import java.util.List;


import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.entity.channel.AppPkg;
import com.dome.sdkserver.metadata.entity.channel.Game;

public interface ChannelPkgService {
	int selectGameCount(String gameName, long channelId);
	
	List<Game> selectGameList(String gameName, long channelId, Paginator paginator);
	
	int selectDlGameCount(String gameName, long channelId);
	
	List<Game> selectDlGameList(String gameName, long channelId, Paginator paginator);
	
	String addAppPkg(AppPkg pkg);
	
	AppPkg selectAppPkg(long channelId);
	
	String gamePkg(long channelId, long[] gameIds);
}
