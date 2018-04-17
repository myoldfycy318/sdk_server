package com.dome.sdkserver.service.impl;


import com.dome.sdkserver.bo.DiscountThreshold;
import com.dome.sdkserver.metadata.dao.mapper.DiscountThresholdMapper;
import com.dome.sdkserver.service.DiscountThresholdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

/**
 * Created by heyajun on 2016/8/5.
 */
@Service
@Transactional
public class DiscountThresholdServiceImpl implements DiscountThresholdService{
    private Logger log = LoggerFactory.getLogger(DiscountThresholdServiceImpl.class);

    @Resource
    private DiscountThresholdMapper discountThresholdMapper;

    @Override
    public void setCpa(DiscountThreshold dt) {
        discountThresholdMapper.updateSetDiscount(dt);
    }

    @Override
    public void setCps(DiscountThreshold dt) {
    	discountThresholdMapper.updateSetDiscount(dt);
    }

    @Override
    public void changeCpa(DiscountThreshold dt) {
        try{
            discountThresholdMapper.updateDeleteFlag(dt);
            discountThresholdMapper.insertChangeDiscount(dt);
        }catch (Exception e){
            log.error("修改CPA失败",e);
        }
    }

    @Override
    public void changeCps(DiscountThreshold dt){
        try{
            discountThresholdMapper.updateDeleteFlag(dt);
            discountThresholdMapper.insertChangeDiscount(dt);
        }catch (Exception e){
            log.error("修改CPS失败",e);
        }
    }

	@Override
	public List<DiscountThreshold> searchChannel(DiscountThreshold dt, Integer pageSize, Integer begin) {
		return discountThresholdMapper.selectChannel(dt, pageSize, begin);
	}

	@Override
	public int countSecond(DiscountThreshold dt) {
		return discountThresholdMapper.selectCountSecond(dt);
	}

    @Override
	public DiscountThreshold searchCreateTime(DiscountThreshold dt) {
		return discountThresholdMapper.selectCreatTime(dt);
	}

	@Override
	public DiscountThreshold searchUpdateTime(DiscountThreshold dt) {
		return discountThresholdMapper.selectChangeTime(dt);
	}

    @Override
    public Set<String> selectDiscountTable() {
        return discountThresholdMapper.selectDiscountTable();
    }

    @Override
    public Set<String> selectSecondTable() {
        return discountThresholdMapper.selectSecondTable();
    }

    @Override
    public int insertChannelCode(DiscountThreshold dt) {
        return discountThresholdMapper.insertChannelCode(dt);
    }

}
