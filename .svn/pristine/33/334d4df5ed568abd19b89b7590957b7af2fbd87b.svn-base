package com.dome.sdkserver.service.impl.pay;

import com.dome.sdkserver.bq.util.PageDto;
import com.dome.sdkserver.metadata.dao.mapper.bq.pay.PublishOrderMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.PayRecordEntity;
import com.dome.sdkserver.service.pay.PayRecordService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/4/11.
 */
@Service("payRecordService")
public class PayRecordServiceImpl implements PayRecordService {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PublishOrderMapper publishOrderMapper;

    @Override
    public List<PayRecordEntity> queryPayRecord(PayRecordEntity orderEntity) {
        return publishOrderMapper.queryPayRecord(orderEntity);
    }

    @Override
    public List<PayRecordEntity> queryPayRecordList(PayRecordEntity orderEntity, PageDto pageDto) {
        if (pageDto.getIsPage()) {
            PageHelper.startPage(pageDto.getPageNo(), pageDto.getPageSize());
            Page<PayRecordEntity> page = (Page<PayRecordEntity>) publishOrderMapper.queryPayRecordList(orderEntity);
            pageDto.setTotalCount(page.getTotal());
            return page;
        }
        return publishOrderMapper.queryPayRecordList(orderEntity);
    }

    @Override
    public List<PayRecordEntity> queryYouPiaoPayRecord(PayRecordEntity orderEntity) {
        return publishOrderMapper.queryYouPiaoPayRecord(orderEntity);
    }


    /**
     * 查询页游、VR游戏支付记录
     *
     * @param orderEntity
     * @param pageDto
     * @return
     */
    @Override
    public List<PayRecordEntity> queryPayStream(PayRecordEntity orderEntity, PageDto pageDto) {
        if (pageDto.getIsPage()) {
            PageHelper.startPage(pageDto.getPageNo(), pageDto.getPageSize());
            Page<PayRecordEntity> page = (Page<PayRecordEntity>) publishOrderMapper.queryPayStream(orderEntity);
            pageDto.setTotalCount(page.getTotal());
            return page;
        }
        return publishOrderMapper.queryPayStream(orderEntity);
    }
}
