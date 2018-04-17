package com.dome.sdkserver.service.channel;

import java.util.List;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.entity.channel.JsOrder;
import com.dome.sdkserver.metadata.entity.channel.JsOrderVo;

public interface JsService {

	static String MONTH_DESC_FORMAT = "yyyy年MM月";
	
	static String ORDERNO_PREFIX = "JS";
	JsOrderVo query(long channelId);
	
	int selectCount(long channelId);
	
	List<JsOrder> selectList(long channelId, Paginator paginator);
	
	JsOrder queryNew(long channelId);
	
	String apply(long channelId);
}
