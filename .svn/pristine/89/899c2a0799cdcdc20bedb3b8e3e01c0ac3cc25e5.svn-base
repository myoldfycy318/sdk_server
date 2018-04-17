package com.dome.sdkserver.service.channel.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.constants.channel.JsStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.channel.JsMapper;
import com.dome.sdkserver.metadata.entity.bq.channel.SettleAmountDto;
import com.dome.sdkserver.metadata.entity.channel.JsOrder;
import com.dome.sdkserver.metadata.entity.channel.JsOrderVo;
import com.dome.sdkserver.service.ChannelDataService;
import com.dome.sdkserver.service.channel.JsService;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.util.RedisUtil;

import static com.dome.sdkserver.constants.DomeSdkRedisKey.CHANNEL_JS_AMOUNT;
@Service
public class JsServiceImpl implements JsService {

	@Autowired
	private JsMapper mapper;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private ChannelDataService channelDataService;
	
	/**
	 * 获取上一个月份的月份字符串，格式为yyyyMM
	 * @return
	 */
	private static String getMonthStr(){
		Calendar c = Calendar.getInstance();
		return DateFormatUtils.format(c, "yyyyMM");
	}
	/**
	 * 查询结算金额，仅限一级渠道
	 */
	@Override
	public JsOrderVo query(long channelId) {
		JsOrder order = queryJsData(channelId);
		String fromDateMonth = DateFormatUtils.format(order.getFromDate(), MONTH_DESC_FORMAT);
		String endDateMonth = DateFormatUtils.format(order.getToDate(), MONTH_DESC_FORMAT);
		String month=fromDateMonth +"-"+endDateMonth;
		BigDecimal amount=order.getAmount();
		
		JsOrderVo vo =new JsOrderVo(month, amount);
		return vo;
	}

	private final Calendar startCalendar = Calendar.getInstance();
	{
		// 设置一个默认开始时间，在所有游戏上线前的时间之前
		startCalendar.set(2013, 0, 1, 0, 0);
	}
	private JsOrder queryJsData(long channelId) {
		String key = CHANNEL_JS_AMOUNT +channelId +"_"+ getMonthStr();
		String text =redisUtil.get(key);
		JsOrder order=null;
		if (StringUtils.isEmpty(text)){
			JsOrder jsOrder = mapper.selectNew(channelId);
			Date beginDate =null;
			Date endDate = DateUtil.getLastMonthEndDate();
			if (jsOrder==null){
				beginDate=startCalendar.getTime();
			} else {
				// 最后一次结算的结束日期
				Date lastEndDate = jsOrder.getToDate();
//				String dateformatStr = "yyyyMMdd";
//				String lastEndDateStr = DateFormatUtils.format(lastEndDate, dateformatStr);
//				String endDateStr = DateFormatUtils.format(DateUtil.getLastMonthEndDate(), dateformatStr);
				// 本次结算的结束日期小于上次结算的结束日期
//				if (!(endDateStr.compareTo(lastEndDateStr)>=0)){
//					
//				}
				Calendar c = Calendar.getInstance();
				c.setTime(lastEndDate);
				c.add(Calendar.DATE, 1);
				beginDate = c.getTime();
			}
//			String dateFormatStr=DateUtil.DATAFORMAT_STR;
//			String beginDateStr = DateFormatUtils.format(beginDate, dateFormatStr);
//			String endDateStr=DateFormatUtils.format(endDate, dateFormatStr);
//			order=mapper.selectAmount(channelId, beginDateStr, endDateStr);
			SettleAmountDto amountDto =channelDataService.selectSettleAmount((int)channelId, beginDate, endDate);
			order = new JsOrder();
			order.setChannelId(channelId);
			if (amountDto==null) {
				order.setAmount(new BigDecimal(0.0));
			} else {
				final BigDecimal settleAmount = amountDto.getSettleAmount();
				order.setAmount(settleAmount==null ?new BigDecimal(0.0) :settleAmount);
			}
			
			order.setChannelId(channelId);
			order.setFromDate(beginDate);
			order.setToDate(endDate);
			// 结算金额乘以（1-扣量），和分成比例
			redisUtil.setex(key, Constants.REDIS_EXPIRETIME_ONEDAY, JSON.toJSONString(order));
		} else {
			order = JSON.parseObject(text, JsOrder.class);
		}
		return order;
		
	}


	@Override
	public JsOrder queryNew(long channelId) {
		
		return mapper.selectNew(channelId);
	}

	/**
	 * 结算金额50元
	 */
	private final BigDecimal FIFTY_JSAMOUNT = new BigDecimal(50.0d);
	
	@Override
	public String apply(long channelId) {
		Date lastEndDate =null;
		JsOrder jsOrder = mapper.selectNew(channelId);
		if (jsOrder!=null){
			lastEndDate = jsOrder.getToDate();
			
		} else {
			lastEndDate=startCalendar.getTime();
		}
		String dateformatStr = "yyyyMMdd";
		String lastEndDateStr = DateFormatUtils.format(lastEndDate, dateformatStr);
		String endDateStr = DateFormatUtils.format(DateUtil.getLastMonthEndDate(), dateformatStr);
		
		if (endDateStr.equals(lastEndDateStr)){
			return "本月已申请过结算，不允许重复申请";
		}
		JsOrder order = queryJsData(channelId);
		BigDecimal amount =order.getAmount();
		if (amount==null || amount.compareTo(FIFTY_JSAMOUNT)<0){
			return "当前结算金额为：" + (amount==null?"0":amount.toString()) +"，低于50元，因此不允许申请结算。";
		}
		order.setApplyDate(new Date());
		// 保存结算记录
		order.setStatus(JsStatusEnum.申请中.getStatus());
		order.setJsAmount(order.getAmount());
		mapper.add(order);
		long id=order.getId();
		String orderNo=generateJsOrderNo(id);
		mapper.updateJsOrderNo(id, orderNo);
		return null;
	}

	private static final String dateFormat = "yyyyMMdd";
	
	// 生成结算订单号，JS+日期时间+主键id的后四位
	private static String generateJsOrderNo(long id){
		Calendar c = Calendar.getInstance();
		String randomStr=String.format("%05d", id%100000);
		return ORDERNO_PREFIX +DateFormatUtils.format(c, dateFormat)+randomStr;
	}
	@Override
	public int selectCount(long channelId) {
		JsOrder order = new JsOrder();
		order.setChannelId(channelId);
		return mapper.selectCount(order);
	}


	@Override
	public List<JsOrder> selectList(long channelId, Paginator paginator) {
		JsOrder order = new JsOrder();
		order.setChannelId(channelId);
		return mapper.selectList(order, paginator);
	}

}
