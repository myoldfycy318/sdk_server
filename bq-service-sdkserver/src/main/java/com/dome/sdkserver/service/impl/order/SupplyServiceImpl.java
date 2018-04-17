package com.dome.sdkserver.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.CBStatusEnum;
import com.dome.sdkserver.biz.enums.OrderStatusEnum;
import com.dome.sdkserver.biz.executor.Executor;
import com.dome.sdkserver.biz.executor.H5GameThridPartyPayNotifyThread;
import com.dome.sdkserver.bq.constants.PayResEnum;
import com.dome.sdkserver.bq.constants.TransStatusEnum;
import com.dome.sdkserver.bq.enumeration.PaySource2BiEnum;
import com.dome.sdkserver.bq.enumeration.PayTypeEnum;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.dao.mapper.qbao.PayTransMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.metadata.entity.qbao.PayTransEntity;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.service.order.SupplyService;
import com.dome.sdkserver.service.pay.PayConfigService;
import com.dome.sdkserver.service.pay.bq.PayNotifyService;
import com.dome.sdkserver.service.pay.qbao.bo.SdkPayResponse;
import com.dome.sdkserver.service.web.requestEntity.HttpRequestOrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SupplyServiceImpl
 *
 * @author Zhang ShanMin
 * @date 2017/10/9
 * @time 16:21
 */
@Service("supplyService")
public class SupplyServiceImpl implements SupplyService {
    @Autowired
    protected OrderService orderService;
    @Resource
    private PayTransMapper payTransMapper;
    @Autowired
    private PayConfigService payConfigService;
    @Resource(name = "payNotifyService")
    private PayNotifyService payNotifyService;
    @Resource(name = "executor")
    private Executor executor;

    @Override
    public SdkOauthResult supply(HttpRequestOrderInfo orderInfo) {
        PaySource2BiEnum paySource2BiEnum = PaySource2BiEnum.getPaySourceTbl(orderInfo.getPaySources());
        String payYearMonth = "";
        SdkOauthResult sdkOauthResult = null;
        switch (paySource2BiEnum) {
            case qbaopay: {
                payYearMonth = PayUtil.getqBaoPayMonth(orderInfo.getOrderNo());
                orderInfo.setPayYearMonth(payYearMonth);
                sdkOauthResult = isQbaopayNotifyPartner(orderInfo);
                break;
            }
            case thirdpay: {
                payYearMonth = PayUtil.getThirdPayMonth(orderInfo.getOrderNo());
                orderInfo.setPayYearMonth(payYearMonth);
                sdkOauthResult = isThirdpayNotifyPartner(orderInfo);
                break;
            }
            case domepay: {
                payYearMonth = PayUtil.getPayMonth(orderInfo.getOrderNo());
                orderInfo.setPayYearMonth(payYearMonth);
                sdkOauthResult = isDomepayNotifyPartner(orderInfo);
                break;
            }
        }
        return sdkOauthResult;
    }


    /**
     * 验证支付宝、微信支付结果是否已通知cp
     *
     * @param orderInfo
     * @return
     */
    private SdkOauthResult isDomepayNotifyPartner(HttpRequestOrderInfo orderInfo) {
        OrderEntity orderEntity = orderService.queryOrderByOrderNo(orderInfo.getOrderNo(), orderInfo.getPayYearMonth());
        if (orderEntity == null) {
            return SdkOauthResult.failed("无效订单");
        }
        if (orderEntity.getOrderStatus() != OrderStatusEnum.orderstatus_pay_sucess.code) {
            return SdkOauthResult.failed("该订单未支付");
        }
        if (orderEntity.getCallbackStatus() == CBStatusEnum.RESP_SUCCESS.getCode()) {
            return SdkOauthResult.failed("该订单道具已下发");
        }
        //H5、手游补单处理
        JSONObject payConfig = payConfigService.getNotifyPayConfigByPayType(PayTypeEnum.支付宝支付.getCode());
        //支付成功异步通知合作方
        payNotifyService.handleDomePayNotify(payConfig, orderEntity, orderService);
        return SdkOauthResult.success();
    }


    /**
     * 验证有票、页游支付结果是否已通知游戏cp
     *
     * @param orderInfo
     * @return
     */
    private SdkOauthResult isThirdpayNotifyPartner(HttpRequestOrderInfo orderInfo) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNo(orderInfo.getOrderNo());
        orderEntity.setCurMonth(orderInfo.getPayYearMonth());
        orderEntity = orderService.queryThirdOrder(orderEntity);
        if (orderEntity == null) {
            return SdkOauthResult.failed("无效订单");
        }
        if (orderEntity.getOrderStatus() != OrderStatusEnum.orderstatus_pay_sucess.code) {
            return SdkOauthResult.failed("该订单未支付");
        }
        if (orderEntity.getCallbackStatus() == CBStatusEnum.RESP_SUCCESS.getCode()) {
            return SdkOauthResult.failed("该订单道具已下发");
        }
        //有票、页游支付结果补单处理
        executor.executor(new H5GameThridPartyPayNotifyThread(orderEntity, orderService));
        return SdkOauthResult.success();
    }


    /**
     * 验证钱宝支付结果是否已通知cp
     *
     * @param orderInfo
     * @return
     */
    private SdkOauthResult isQbaopayNotifyPartner(HttpRequestOrderInfo orderInfo) {
        PayTransEntity entity = payTransMapper.getPayTransReqById(Long.valueOf(orderInfo.getOrderNo()), orderInfo.getPayYearMonth());
        if (entity == null) {
            return SdkOauthResult.failed("无效订单");
        }
        if (!TransStatusEnum.PAY_TRANS_SUCCESS.getStatus().equals(entity.getStatus())) {
            return SdkOauthResult.failed("该订单未支付");
        }
        if (entity.getCallbackStatus() == CBStatusEnum.RESP_SUCCESS.getCode()) {
            return SdkOauthResult.failed("该订单道具已下发");
        }
        //H5、手游补单处理
        payNotifyService.handleQbaoPayNotify(entity, new SdkPayResponse(PayResEnum.SUCCESS_CODE.getCode(), null));
        return SdkOauthResult.success();
    }


}
