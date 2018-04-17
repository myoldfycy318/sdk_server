package com.dome.sdkserver.service.channel;

import java.util.List;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.entity.channel.AppPkg;
import com.dome.sdkserver.metadata.entity.channel.BqApp;

public interface ChannelPkgAuditService {
	AppPkg selectAppPkg(long pid);
	
	int updateAppPkg(AppPkg pkg);
	
	int selectAppPkgCount(long channelId,
			String channelName);
	
	List<AppPkg> selectAppPkgList(long channelId,
			String channelName, Paginator paginator);
	
	String pkg(long pid);
	
	String batchPkg(List<Long> pidList);
	
	String receBqApp(BqApp app);
}
