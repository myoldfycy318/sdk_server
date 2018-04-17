package com.dome.sdkserver.service.pay.mycard.impl;

import com.dome.sdkserver.metadata.dao.mapper.bq.pay.PublishOrderMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.PublishOrderEntity;
import com.dome.sdkserver.service.pay.mycard.PublishOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xuekuan on 2017/2/17.
 */
@Service("publishOrderService")
public class PublishOrderServiceImpl implements PublishOrderService {

    @Autowired
    private PublishOrderMapper publishOrderMapper;

    @Override
    public boolean insert(PublishOrderEntity publishOrderEntity) {
        return publishOrderMapper.insert(publishOrderEntity);
    }

    @Override
    public boolean updateOrderInfo(PublishOrderEntity publishOrderEntity) {
        return publishOrderMapper.updateById(publishOrderEntity);
    }

    @Override
    public boolean updateOrderById(PublishOrderEntity publishOrderEntity) {
        return publishOrderMapper.updateOrderById(publishOrderEntity);
    }

    @Override
    public PublishOrderEntity queryOrderByOrderNo(PublishOrderEntity publishOrderEntity) {
        return publishOrderMapper.queryOrderByOrderNo(publishOrderEntity.getOrderNo(),publishOrderEntity.getCurMonth());
    }

    @Override
    public List<PublishOrderEntity> queryByCondition(PublishOrderEntity publishOrderEntity,String StartDateTime,String EndDateTime) {
        return publishOrderMapper.queryByCondition(publishOrderEntity,StartDateTime,EndDateTime);
    }
}
