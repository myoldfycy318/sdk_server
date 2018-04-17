package com.dome.sdkserver.service.channel.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.constants.channel.JsStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.channel.JsMapper;
import com.dome.sdkserver.metadata.entity.channel.JsOrder;
import com.dome.sdkserver.service.channel.JsAuditService;

@Service
public class JsAuditServiceImpl implements JsAuditService {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JsMapper mapper;
	@Override
	public int selectCount(JsOrder order) {
		
		return mapper.selectCount(order);
	}

	@Override
	public List<JsOrder> selectList(JsOrder order, Paginator paginator) {
		
		return mapper.selectList(order, paginator);
	}

	@Override
	public JsOrder select(String orderNo) {
		
		return mapper.select(orderNo);
	}

	@Override
	public int update(JsOrder order) {
		if (order.getStatus()==JsStatusEnum.已结算.getStatus()){
			logger.info("审批结算单，id=" + order.getId());
		} else {
			logger.info("编辑结算单，id={},jsAmount=", order.getId(), order.getJsAmount());
		}
		
		return mapper.update(order);
	}

}
