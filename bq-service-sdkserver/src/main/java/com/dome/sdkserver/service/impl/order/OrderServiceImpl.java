package com.dome.sdkserver.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.CBStatusEnum;
import com.dome.sdkserver.biz.enums.OrderStatusEnum;
import com.dome.sdkserver.bq.constants.redis.RedisConstants;
import com.dome.sdkserver.bq.enumeration.PayOriginEnum;
import com.dome.sdkserver.bq.enumeration.PaySource2BiEnum;
import com.dome.sdkserver.bq.enumeration.PayType2BiEnum;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.metadata.dao.mapper.bq.pay.OrderMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.PayRecordSync;
import com.dome.sdkserver.service.impl.pay.AlipayConfig;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.service.rabbitmq.RabbitMqService;
import com.dome.sdkserver.service.web.requestEntity.HttpRequestOrderInfo;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AlipayConfig aliPayConfig;
    @Resource(name = "rabbitMqService")
    protected RabbitMqService rabbitMqService;


    @Override
    public boolean createOrder(OrderEntity orderEntity) {
        domePaySyncBi(orderEntity);
        return orderMapper.insertOrder(orderEntity);
    }

    @Override
    public void createAndSaveOrder(HttpRequestOrderInfo order) {
        OrderEntity orderEntity = createOrderEntity(order);
        orderMapper.insertOrder(orderEntity);
        domePaySyncBi(orderEntity);//支付宝、微信支付数据同步到Bi报表
    }

    /**
     * 支付宝、微信支付数据同步到Bi报表
     *
     * @param orderEntity
     */
    void domePaySyncBi(OrderEntity orderEntity) {
        try {
            //非冰穹支付来源不同步到Bi
            if (!PayOriginEnum.isInsidePay(orderEntity.getPayOrigin()))
                return;
            PayRecordSync syncEntity = new PayRecordSync();
            BeanUtils.copyProperties(orderEntity, syncEntity);
            syncEntity.setCpOrderNo(orderEntity.getGameOrderNo());
            syncEntity.setPaySources(PaySource2BiEnum.domepay.name());
            syncEntity.setPayType(PayType2BiEnum.getBiPayType(orderEntity.getPayType()));
            syncEntity.setUserId(orderEntity.getBuyerId());
            syncEntity.setChargePointAmount(new BigDecimal(orderEntity.getChargePointAmount()));
            rabbitMqService.syncInsertPayData2Bi(syncEntity);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateOrder(OrderEntity orderEntity, String curMonth) {
        boolean result = orderMapper.updateOrder(orderEntity, curMonth);
        if (result && orderEntity.getOrderStatus() == OrderStatusEnum.orderstatus_pay_sucess.code) {
            Map<String, Object> map = new HashMap<>();
            map.put("isPay", "true");
            map.put("name", orderEntity.getAppName());
            map.put("price", orderEntity.getChargePointAmount());
            redisUtil.setex(RedisConstants.ALI_PAY_PREFIX + orderEntity.getOrderNo(), 30 * 60, JSONObject.toJSONString(map));
        }
        snycdomePayUpdate2Bi(orderEntity);//微信、支付宝支付同步Bi报表
        return result;
    }


    /**
     * 有票、微信、支付宝支付同步Bi报表
     *
     * @param orderEntity
     */
    protected void snycdomePayUpdate2Bi(OrderEntity orderEntity) {
        try {
            //非冰穹支付来源不同步到Bi
            if (!PayOriginEnum.isInsidePay(orderEntity.getPayOrigin()))
                return;
            PayRecordSync syncEntity = new PayRecordSync();
            BeanUtils.copyProperties(orderEntity, syncEntity);
            syncEntity.setChannelOrderNo(orderEntity.getTradeNo());
            syncEntity.setPayType(PayType2BiEnum.getBiPayType(orderEntity.getPayType()));
            rabbitMqService.syncUpdatePayData2Bi(syncEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OrderEntity createOrderEntity(HttpRequestOrderInfo order) {
        OrderEntity entity = new OrderEntity();
        BeanUtils.copyProperties(order, entity);
        entity.setPayNotifyUrl(order.getGameNotifyUrl());
        entity.setOrderStatus(OrderStatusEnum.orderstatus_no_pay.code);
        entity.setCallbackStatus(CBStatusEnum.NO_NOTIFY.getCode());
        entity.setCurMonth(DateUtils.getCurDateFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT));
        //页游添加拓展字段，用于标识页游区服id
        if (order.getPayOrigin().equals("pc") && StringUtils.isNotBlank(order.getExtraField())) {
            entity.setExtraField2(JSONObject.parseObject(order.getExtraField()).getString("zoneId"));
        }
        //passport充值账户
        if (order.getPayOrigin().equalsIgnoreCase("rc") && StringUtils.isNotBlank(order.getPassport())) {
            entity.setExtraField2(order.getPassport());
        }
        if (order.getCreateTime() != null) {
            entity.setCreateTime(order.getCreateTime());
        }
        return entity;
    }

    @Override
    public double queryOrderAmount(String orderNo, String curMonth) {
        return orderMapper.queryOrderAmount(orderNo, curMonth);
    }

    @Override
    public OrderEntity queryOrderByOrderNo(String orderNo, String curMonth) {
        OrderEntity order = orderMapper.queryOrderByOrderNo(orderNo, curMonth);
        if (order == null) {
            curMonth = DateUtils.getPreviousMonthFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT);
            order = orderMapper.queryOrderByOrderNo(orderNo, curMonth);
        }
        return order;
    }

    @Override
    public void createTable() {
        String tableSuffix = DateUtils.getCurDateFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT);
        orderMapper.createTable(tableSuffix);
    }

    /**
     * 判断订单是否已支付
     *
     * @param orderNo
     * @return true:已付|false:未支付
     */
    @Override
    public JSONObject isOrderPaid(String orderNo) throws Exception {
        String val = redisUtil.get(RedisConstants.ALI_PAY_PREFIX + orderNo);
        JSONObject jsonObject = null;
        Map<String, Object> map = null;
        if (StringUtils.isNotBlank(val) && (jsonObject = JSONObject.parseObject(val)) != null)
            return jsonObject;
        String curMonth = PayUtil.getPayMonth(orderNo);
        OrderEntity order = orderMapper.queryOrderByOrderNo(orderNo, curMonth);
        map = new HashMap<>();
        map.put("channelCode",order.getChannelCode());
        if (order == null) {
            map.put("isPay", "false");
            return new JSONObject(map);
        }
        map.put("name", order.getAppName());
        map.put("price", order.getChargePointAmount());
        String isPay = order.getOrderStatus() != OrderStatusEnum.orderstatus_pay_sucess.code ? "false" : "true";
        map.put("isPay", isPay);
        redisUtil.setex(RedisConstants.ALI_PAY_PREFIX + order.getOrderNo(), 15 * 60, JSONObject.toJSONString(map));
        return new JSONObject(map);
    }

    /**
     * 更新订单信息
     *
     * @param orderEntity
     * @param curMonth
     * @return
     */
    @Override
    public boolean updateOrderInfo(OrderEntity orderEntity, String curMonth) {
        boolean result = true;
        result = orderMapper.updateOrderInfo(orderEntity, curMonth);
        if (!result) {
            result = orderMapper.updateOrderInfo(orderEntity, DateUtils.getPreviousMonthFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT));
        }
        snycdomePayUpdate2Bi(orderEntity); //微信、支付宝支付同步Bi报表
        return result;
    }

    @Override
    public boolean insertThirdOrder(OrderEntity orderEntity) {
        return orderMapper.insertThirdOrder(orderEntity);
    }

    @Override
    public boolean insertThirdOrder2(OrderEntity orderEntity) {
        return orderMapper.insertThirdOrder2(orderEntity);
    }

    /**
     * 更新第三方订单信息
     *
     * @param orderEntity
     */
    @Override
    public boolean updateThirdOrder(OrderEntity orderEntity) {
        boolean result = false;
        result = orderMapper.updateThirdOrder(orderEntity);
        if (!result) {
            orderEntity.setCurMonth(DateUtils.getPreviousMonthFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT));
            result = orderMapper.updateThirdOrder(orderEntity);
        }
        snycdomePayUpdate2Bi(orderEntity); //有票、微信、支付宝支付同步Bi报表
        return result;
    }

    /**
     * 查询第三方订单信息
     *
     * @param orderEntity
     */
    @Override
    public OrderEntity queryThirdOrder(OrderEntity orderEntity) {
        OrderEntity order = orderMapper.queryThirdOrder(orderEntity);
        if (order == null) {
            orderEntity.setCurMonth(DateUtils.getPreviousMonthFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT));
            order = orderMapper.queryThirdOrder(orderEntity);
        }
        return order;
    }

    @Override
    public OrderEntity queryOrderByOutOrderNo(OrderEntity orderEntity) {
        return orderMapper.queryOrderByOutOrderNo(orderEntity);
    }

}
