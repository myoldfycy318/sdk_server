package com.dome.sdkserver.service.channel.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dome.sdkserver.bo.DiscountThreshold;
import com.dome.sdkserver.constants.channel.ChannelConfigItemEnum;
import com.dome.sdkserver.constants.channel.CooperTypeEnum;
import com.dome.sdkserver.metadata.dao.mapper.channel.ChannelConfigMapper;
import com.dome.sdkserver.metadata.dao.mapper.channel.SecondChannelMapper;
import com.dome.sdkserver.metadata.entity.channel.ChannelConfig;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;
import com.dome.sdkserver.metadata.entity.channel.JieSuanConfig;
import com.dome.sdkserver.service.channel.ChannelConfigService;

@Service
public class ChannelConfigServiceImpl implements ChannelConfigService {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ChannelConfigMapper mapper;
	
	@Autowired
	private SecondChannelMapper secondChannelMapper;
	@Override
	public ChannelConfig queryConfig(long channelId, String dateStr,
			Set<ChannelConfigItemEnum> itemEnumList) {
		if (StringUtils.isEmpty(dateStr) || CollectionUtils.isEmpty(itemEnumList)){
			throw new IllegalArgumentException("param date or itemEnumList is empty");
		}
		ChannelConfig cc = new ChannelConfig();
		/**
		 * 假如统计8月17号数据，扣量若今天中午12点修改，使用修改后的值。若12点后修改，统计明天的数据才使用修改后的值。
		 * 合作方式和分成比例是次日生效。即今天配置的值，只有在明天统计今天的数据时才使用。
		 * 1、扣量比例等是12点前修改，影响昨天的数据统计，12点后配置的从当天的数据统计开始 
		 * 2、合作方式和分成比例是修改会影响当天的数据统计
		 */
		String afterDateStr = calcAfterDay(dateStr);
		FirstChannel channel=secondChannelMapper.select(channelId, 0);
		DiscountThreshold discount =mapper.selectDiscountThreshold(channel.getChannelCode(), afterDateStr);
		JieSuanConfig jsConfig =mapper.selectJsConfig(channelId, dateStr);
		for (ChannelConfigItemEnum itemEnum :itemEnumList){
			switch (itemEnum){
			/**
			 * 	
			 */
			case 合作方式:{
				int cooperType=mapper.selectCooperType(channelId, dateStr);
				if (cooperType!=0){
					cc.setCooperType(cooperType==1 ? CooperTypeEnum.CPA : CooperTypeEnum.CPS);
				}
				
				break;
			}
			case 激活单价:{
				if (cc!=null){
					cc.setActivityUnitPrice(jsConfig.getActivityUnitPrice());
				}
				
				break;
			}
			case 分成比例:{
				if (cc!=null){
					cc.setDividePercent(jsConfig.getDividePercent());
				}
				break;
			}
			
			case 扣量比例:{
				if (discount!=null){
					cc.setDiscount(discount.getDiscount());
				}
				break;
			}
			case 激活量阈值:{
				if (discount!=null){
					cc.setActiveThreshold(discount.getActiveThreshold());
				}
				break;
			}
			case 付费用户扣量比例:{
				if (discount!=null){
					cc.setPayingUserDiscount(discount.getPayingUserDiscount());
				}
				break;
			}
			case 充值金额扣量比例:{
				if (discount!=null){
					cc.setRechargeAmountDiscount(discount.getRechargeAmountDiscount());
				}
				break;
			}
			case 充值量阈值:{
				if (discount!=null){
					cc.setPayingThreshold(discount.getPayingThreshold());
				}
				break;
			}
			default: ;
			}
		}
		return cc;
	}
	
	/**
	 * 计算后一天的日期
	 * @param date 日期，譬如2016-08-18
	 * @return
	 */
	private String calcAfterDay(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date=sdf.parse(dateStr);
			Calendar c =Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 1);
			return sdf.format(c.getTime());
		} catch (ParseException e) {
			log.error("计算日期的后一天，解析日期出错", e);
		}
		return "";
	}

}
