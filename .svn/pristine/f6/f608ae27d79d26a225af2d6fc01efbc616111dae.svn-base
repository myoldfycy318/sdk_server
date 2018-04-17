package com.dome.sdkserver.service.impl.pay;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.OrderStatusEnum;
import com.dome.sdkserver.biz.enums.PayTypeEnum;
import com.dome.sdkserver.biz.utils.WechatPayUtil;
import com.dome.sdkserver.biz.utils.alipay.AliPayUtil;
import com.dome.sdkserver.bq.constants.PayConstant.PAY_REQ_ORIGIN;
import com.dome.sdkserver.bq.domain.AliPayReq;
import com.dome.sdkserver.bq.enumeration.ErrorCodeEnum;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.util.PriceUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.WeChatEntity;
import com.dome.sdkserver.service.pay.PayConfigService;
import com.dome.sdkserver.service.web.requestEntity.HttpRequestOrderInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * PayOrderManager
 *
 * @author Zhang ShanMin
 * @date 2016/12/17
 * @time 15:14
 */
public abstract class PayOrderManager {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PayConfigService payConfigService;


    /**
     * 处理支付请求
     *
     * @param reqOrderInfo
     * @return
     * @throws Exception
     */
    protected SdkOauthResult handlePayReq(HttpRequestOrderInfo reqOrderInfo) throws Exception {
        Map<String, String> payData = payConfigService.getAllConfig(reqOrderInfo.getPayType());
        if (payData == null || payData.size() == 0) {
            return SdkOauthResult.failed(ErrorCodeEnum.不支持该支付方式.code, ErrorCodeEnum.不支持该支付方式.name());
        }
        //预处理请求参数
        preHandleReqOrder(reqOrderInfo);
        Object obj = null;
        if (reqOrderInfo.getPayType() == PayTypeEnum.支付宝支付.getCode()) {
            //构建唤起支付宝支付所需参数
            obj = getPayInfoByPayType(reqOrderInfo, buildAliPayReq(reqOrderInfo, payData));
        } else if (reqOrderInfo.getPayType() == PayTypeEnum.微信支付.getCode()) {
            WeChatEntity weChatEntity = new WeChatEntity.Builder().nonceStr(WechatPayUtil.generateNonceStr()).spBillCreateIp(reqOrderInfo.getReqIp()).outTradeNo(reqOrderInfo.getOrderNo()).tradeType("NATIVE").body(reqOrderInfo.getSubject()).totalFee(reqOrderInfo.getTotalFee()).build();
            obj = WechatPayUtil.weixinPayUnifiedorder(weChatEntity, payData);
        }
        if (obj == null || obj.equals("")) {
            return SdkOauthResult.failed("构建支付请求参数错误");
        }
        return SdkOauthResult.success(obj);
    }


    /**
     * 构建唤起支付宝支付所需参数
     *
     * @param reqOrderInfo
     * @param aliPayReq
     * @return
     * @throws Exception
     */
    protected Object getPayInfoByPayType(HttpRequestOrderInfo reqOrderInfo, AliPayReq aliPayReq) throws Exception {
        if (reqOrderInfo.getPayType() == PayTypeEnum.支付宝支付.getCode()) {
            PAY_REQ_ORIGIN payReqOrigin = PAY_REQ_ORIGIN.getPayReqOrigin(reqOrderInfo.getPayOrigin());
            if (payReqOrigin == null)
                return null;
            switch (payReqOrigin) {
                case FULU_AP_PAY:
                case PC: {
                    return AliPayUtil.createPayDownInfo(aliPayReq);
                }
                case JUBAOPEN_AP_PAY:
                case TAOBAOER_AP_PAY: {
                    return AliPayUtil.createWapAliPayInfo(aliPayReq);
                }
            }
        }
        return null;
    }

    /**
     * 转化订单入库参数
     *
     * @param order
     * @return
     */
    protected OrderEntity convertOrderEntity(HttpRequestOrderInfo order) {
        OrderEntity entity = new OrderEntity();
        BeanUtils.copyProperties(order, entity);
        entity.setAppName(order.getSubject());
        entity.setChargePointName(order.getBody());
        entity.setChargePointAmount(PriceUtil.convert2YuanD(new BigDecimal(order.getTotalFee())).doubleValue());
        entity.setGameOrderNo(order.getOutOrderNo());
        entity.setPayNotifyUrl(order.getNotifyUrl());
        entity.setOrderStatus(OrderStatusEnum.orderstatus_no_pay.code);
        entity.setCurMonth(DateUtils.getCurDateFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT));
        entity.setgRoleId(order.getRoleId());
        handleExtField(order, entity);//处理扩展字段
        return entity;
    }

    /**
     * 处理扩展字段
     *
     * @param order
     * @param entity
     */
    private void handleExtField(HttpRequestOrderInfo order, OrderEntity entity) {
        String extStr = order.getExtraField();
        JSONObject jsonObject = null;
        Map<String, Object> extMap = new HashMap<String, Object>(2);
        if (StringUtils.isNotBlank(order.getExtraCommonParam())) {
            extMap.put("extraCommonParam", order.getExtraCommonParam());
        }
        if (StringUtils.isNotBlank(order.getReturnUrl())) {
            extMap.put("returnUrl", order.getReturnUrl());
        }
        if (extMap.size() > 0) {
            if (StringUtils.isBlank(extStr) || (jsonObject = JSONObject.parseObject(extStr)) == null) {
                jsonObject = new JSONObject();
            }
            jsonObject.putAll(extMap);
            entity.setExtraField(jsonObject.toJSONString());
        }
    }

    /**
     * 预处理请求参数
     *
     * @param requestOrderInfo
     */
    protected abstract void preHandleReqOrder(HttpRequestOrderInfo requestOrderInfo);

    /**
     * 构建支付宝支付所有的请求参数
     *
     * @param requestOrderInfo
     * @return
     */
    protected abstract AliPayReq buildAliPayReq(HttpRequestOrderInfo requestOrderInfo, Map<String, String> payData);
}
