package com.dome.sdkserver.service.pay.mycard;

import com.dome.sdkserver.metadata.entity.bq.pay.PublishOrderEntity;

import java.util.List;

public interface PublishOrderService {
    /**
     * 创建订单信息
     * @param
     */
    boolean insert(PublishOrderEntity publishOrderEntity);

    /**
     * 更新订单信息
     */
    boolean updateOrderInfo(PublishOrderEntity publishOrderEntity);

    /**
     * 更新订单信息
     */
    boolean updateOrderById(PublishOrderEntity publishOrderEntity);

    /**
     * 根据订单号查询订单详细信息
     * @param publishOrderEntity
     * @return
     */
    PublishOrderEntity queryOrderByOrderNo(PublishOrderEntity publishOrderEntity);

    /**
     * 交易结果差异对比
     * @param publishOrderEntity
     * @return
     */
    List<PublishOrderEntity> queryByCondition(PublishOrderEntity publishOrderEntity,String StartDateTime,String EndDateTime);
}
