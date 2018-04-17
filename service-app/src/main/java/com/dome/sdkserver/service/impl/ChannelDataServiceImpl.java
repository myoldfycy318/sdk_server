package com.dome.sdkserver.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dome.sdkserver.bo.DiscountThreshold;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.constants.channel.CooperTypeEnum;
import com.dome.sdkserver.metadata.dao.mapper.bq.channel.BalanceAmountEntityMapper;
import com.dome.sdkserver.metadata.dao.mapper.channel.ChannelConfigMapper;
import com.dome.sdkserver.metadata.entity.bq.channel.BalanceAmountEntity;
import com.dome.sdkserver.metadata.entity.bq.channel.BalanceAmountVo;
import com.dome.sdkserver.metadata.entity.bq.channel.SettleAmountDto;
import com.dome.sdkserver.service.ChannelDataService;
import com.dome.sdkserver.util.DateUtil;

/**
 * **********************************************************
 *  内容摘要	：<p>
 *
 *  作者	：94841
 *  创建时间	：2016年8月10日 下午2:44:01 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2016年8月10日 下午2:44:01 	修改人：
 *  	描述	:
 ***********************************************************
 */
@Service("channelDataService")
public class ChannelDataServiceImpl implements ChannelDataService
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    @Autowired
    BalanceAmountEntityMapper balanceAmountEntityMapper;
    
    @Override
    public List<BalanceAmountVo> select(Paginator paginator, Integer channelId, 
        String channelName, Date beginDate, Date endDate, Integer cooperationType)
    {
        List<BalanceAmountEntity> qryList = balanceAmountEntityMapper.select(channelId, channelName, beginDate, endDate, cooperationType,
        		paginator);
        List<BalanceAmountVo> result = new ArrayList<BalanceAmountVo>();
        for(BalanceAmountEntity entity : qryList)
        {
            BalanceAmountVo balanceAmountVo = new BalanceAmountVo();
            BeanUtils.copyProperties(entity, balanceAmountVo);
            balanceAmountVo.setActivateUserCount(entity.getDeductActivateUserCount());
            balanceAmountVo.setPayUserCount(entity.getDeductPayUserCount());
            balanceAmountVo.setChargeAmount(entity.getDeductChargeAmount());
            balanceAmountVo.setSettleAmount(entity.getDeductSettleAmount());
            balanceAmountVo.setDate(getFormatDateString(entity.getDate()));
            balanceAmountVo.setActivityUnitPrice(entity.getActivityUnitPrice());
            balanceAmountVo.setCooperType(CooperTypeEnum.getDesc(entity.getCooperType()));
            
            result.add(balanceAmountVo);
        }
        BalanceAmountVo totalAmountVo = new BalanceAmountVo();
        totalAmountVo.setChannelName("合计");
        int totalActivateUserCount = 0, totalPayUserCount = 0;
        BigDecimal totalChargeAmount = new BigDecimal(0);
        BigDecimal totalSettleAmount = new BigDecimal(0);
        for(BalanceAmountVo balanceAmountVo : result)
        {
            totalActivateUserCount += (balanceAmountVo.getActivateUserCount()==null?0:balanceAmountVo.getActivateUserCount());
            totalPayUserCount += (balanceAmountVo.getPayUserCount()==null?0:balanceAmountVo.getPayUserCount());
            totalChargeAmount=totalChargeAmount.add((balanceAmountVo.getChargeAmount()==null?new BigDecimal(0):balanceAmountVo.getChargeAmount()));
            totalSettleAmount = totalSettleAmount.add((balanceAmountVo.getSettleAmount()==null?new BigDecimal(0):balanceAmountVo.getSettleAmount()));
        }
        totalAmountVo.setActivateUserCount(totalActivateUserCount);
        totalAmountVo.setPayUserCount(totalPayUserCount);
        totalAmountVo.setChargeAmount(totalChargeAmount);
        totalAmountVo.setSettleAmount(totalSettleAmount);
        result.add(totalAmountVo);
        return result;
    }
    
    @Override
    public int selectCount(Integer channelId, String channelName, Date beginDate, 
        Date endDate, Integer cooperationType)
    {
        return balanceAmountEntityMapper.selectCount(channelId, channelName, beginDate, endDate, cooperationType);
    }
    
    
    private String getFormatDateString(String oriDateString)
    {
        return DateUtil.dateToDateString(DateUtil.getDate(oriDateString, DATE_FORMAT), DATE_FORMAT);
    }


    @Override
    public SettleAmountDto selectSettleAmount(Integer channelId, Date beginDate, Date endDate)
    {
        SettleAmountDto settleAmountDto = new SettleAmountDto();
        BalanceAmountEntity balanceAmountEntity = balanceAmountEntityMapper.selectSettleAmount(channelId, beginDate, endDate);
        if(balanceAmountEntity != null)
        {
            BeanUtils.copyProperties(balanceAmountEntity, settleAmountDto);
        }
        return settleAmountDto;
    }

    @Override
    public List<BalanceAmountVo> selectDetail(Paginator paginator,
        Integer channelId, Integer cooperationType, Date beginDate, Date endDate)
    {
        List<BalanceAmountEntity> qryList = balanceAmountEntityMapper.selectDetail(channelId, beginDate, endDate,
        		paginator);
        List<BalanceAmountVo> result = new ArrayList<BalanceAmountVo>();
        switch (cooperationType)
        {
            case 1:
                for(BalanceAmountEntity entity : qryList)
                {
                    BalanceAmountVo balanceAmountVo = new BalanceAmountVo();
                    balanceAmountVo.setActivateUserCount(entity.getDeductActivateUserCount());
                    balanceAmountVo.setDate(getFormatDateString(entity.getDate()));
                    result.add(balanceAmountVo);
                }
                BalanceAmountVo totalAmountVo4Cpa = new BalanceAmountVo();
                totalAmountVo4Cpa.setDate("合计");
                int totalActivateUserCount = 0;
                for(BalanceAmountVo balanceAmountVo : result)
                {
                    totalActivateUserCount += (balanceAmountVo.getActivateUserCount()==null?0:balanceAmountVo.getActivateUserCount());
                }
                totalAmountVo4Cpa.setActivateUserCount(totalActivateUserCount);
                result.add(totalAmountVo4Cpa);
                break;
            case 2:
                for(BalanceAmountEntity entity : qryList)
                {
                    BalanceAmountVo balanceAmountVo = new BalanceAmountVo();
                    balanceAmountVo.setAppName(entity.getAppName());
                    balanceAmountVo.setPayUserCount(entity.getDeductPayUserCount());
                    balanceAmountVo.setChargeAmount(entity.getDeductChargeAmount());
                    balanceAmountVo.setSettleAmount(entity.getDeductSettleAmount());
                    
                    balanceAmountVo.setActivateUserCount(entity.getActivateUserCount());
                    balanceAmountVo.setDate(getFormatDateString(entity.getDate()));
                    result.add(balanceAmountVo);
                }
                BalanceAmountVo totalAmountVo4Cps = new BalanceAmountVo();
                totalAmountVo4Cps.setDate("合计");
                int totalPayUserCount = 0;
                BigDecimal totalChargeAmount = new BigDecimal(0);
                BigDecimal totalSettleAmount = new BigDecimal(0);
                int totalUser=0;
                for(BalanceAmountVo balanceAmountVo : result)
                {
                    totalPayUserCount += (balanceAmountVo.getPayUserCount()==null?0:balanceAmountVo.getPayUserCount());
                    totalChargeAmount =totalChargeAmount.add((balanceAmountVo.getChargeAmount()==null?new BigDecimal(0):balanceAmountVo.getChargeAmount()));
                    totalSettleAmount = totalSettleAmount.add((balanceAmountVo.getSettleAmount()==null?new BigDecimal(0):balanceAmountVo.getSettleAmount()));
                    totalUser+=(balanceAmountVo.getActivateUserCount()==null?0:balanceAmountVo.getActivateUserCount());
                }
                totalAmountVo4Cps.setPayUserCount(totalPayUserCount);
                totalAmountVo4Cps.setChargeAmount(totalChargeAmount);
                totalAmountVo4Cps.setSettleAmount(totalSettleAmount);
                totalAmountVo4Cps.setActivateUserCount(totalUser);
                result.add(totalAmountVo4Cps);
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public int selectDetailCount(Integer channelId, Date beginDate, Date endDate)
    {
        return balanceAmountEntityMapper.selectDetailCount(channelId, beginDate, endDate);
    }

    @Autowired
    private ChannelConfigMapper channelConfigMapper;
	@Override
	public void synTjData() {
		String tjEndDate=balanceAmountEntityMapper.selectMaxTjDate();
		// 第一次开始统计数据时
		if (StringUtils.isEmpty(tjEndDate)) {
			tjEndDate="2016-09-01";
		}
		SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT);
		String calcEndDate =calcEndDate();
		if (calcEndDate.equals(tjEndDate)) return;
		
		// 需要同步数据
		Date endDate=null;
		try {
			endDate = sdf.parse(tjEndDate);
		} catch (ParseException e) {
			logger.error("解析日期出错，date="+tjEndDate, e);
			return;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.DATE, 1);
		String tjDate=sdf.format(c.getTime());
		
		/**
		 * 遍历日期
		 * 查询日期中有哪些渠道由交易数据
		 * 遍历渠道
		 * 查询对应的扣量配置id
		 * 同步数据
		 */
		String beginDate=tjDate;
		logger.info("渠道系统结算管理开始同步数据，startDate={}，endDate={}",beginDate, calcEndDate);
		while (tjDate.compareTo(calcEndDate)<=0){
			// 查询渠道列表
			List<String> codeList= balanceAmountEntityMapper.selectChannelCodeList(tjDate);
			if (!CollectionUtils.isEmpty(codeList)){
				for (String code: codeList){
					// 查询扣量配置
					// 12点修改，影响昨日的数据计算，12点后修改，影响今天的数据计算
					DiscountThreshold discount=channelConfigMapper.selectDiscountThreshold(code, tjDate);
					// null列处理
					if (discount!=null){
						discount.setChannelCode(code);
						BigDecimal decimalObj=discount.getDiscount();
						if (decimalObj==null) discount.setDiscount(new BigDecimal(0));
						Integer value=discount.getActiveThreshold();
						if (value==null) discount.setActiveThreshold(0);
						decimalObj=discount.getPayingThreshold();
						if (decimalObj==null) discount.setPayingThreshold(new BigDecimal(0));
						decimalObj=discount.getPayingUserDiscount();
						if (decimalObj==null) discount.setPayingUserDiscount(new BigDecimal(0));
						decimalObj=discount.getRechargeAmountDiscount();
						if (decimalObj==null) discount.setRechargeAmountDiscount(new BigDecimal(0));
					} else {
						discount=createDefaultDiscountObj();
						discount.setChannelCode(code);
					}
					balanceAmountEntityMapper.batchSynTjData(tjDate, discount);
					
				}
			}
			c.add(Calendar.DATE, 1);
			tjDate=sdf.format(c.getTime());
		}

		logger.info("渠道系统结算管理同步数据完成，startDate={}，endDate={}",beginDate, calcEndDate);
	}
	
	private DiscountThreshold createDefaultDiscountObj(){
		DiscountThreshold d=new DiscountThreshold();
		d.setDiscount(new BigDecimal(0));
		d.setActiveThreshold(0);
		d.setPayingUserDiscount(new BigDecimal(0));
		d.setRechargeAmountDiscount(new BigDecimal(0));
		d.setPayingThreshold(new BigDecimal(0));
		return d;
	}
	/**
	 * 计算可以统计的结束日期
	 * @return
	 */
	private String calcEndDate(){
		// 12:30前能查看到两日前的数据，12:30后可以看到昨日的数据
		// 12:30后开始同步昨日数据。12:30前同步两日前数据
		Calendar c = Calendar.getInstance();
        if (c.get(Calendar.HOUR_OF_DAY)>12 || (c.get(Calendar.HOUR_OF_DAY)==12 && c.get(Calendar.MINUTE)>=30)){
        	c.add(Calendar.DATE, -1);
        } else {
        	c.add(Calendar.DATE, -2);
        }
        
        return DateFormatUtils.format(c, DATE_FORMAT);
	}
	
}
