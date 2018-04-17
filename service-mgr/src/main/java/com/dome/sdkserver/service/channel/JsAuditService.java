package com.dome.sdkserver.service.channel;

import java.util.List;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.entity.channel.JsOrder;

public interface JsAuditService {

	int selectCount(JsOrder order);
	
	List<JsOrder> selectList(JsOrder order, Paginator paginator);
	
	JsOrder select(String orderNo);
	
	int update(JsOrder order);
}
