package com.dome.sdkserver.controller.pay.basic;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.utils.TenpayHttpClient;
import com.dome.sdkserver.biz.utils.WechatPayUtil;
import com.dome.sdkserver.biz.utils.alipay.AliPayUtil;
import com.dome.sdkserver.biz.utils.alipay.AlipayCore;
import com.dome.sdkserver.biz.utils.alipay.RSA;
import com.dome.sdkserver.bq.constants.PayConstant;
import com.dome.sdkserver.bq.enumeration.ErrorCodeEnum;
import com.dome.sdkserver.bq.enumeration.PayTypeEnum;
import com.dome.sdkserver.bq.enumeration.SysTypeEnum;
import com.dome.sdkserver.bq.util.PriceUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.BqChargePointInfo;
import com.dome.sdkserver.metadata.entity.bq.pay.WeChatEntity;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.channel.NewOpenChannelService;
import com.dome.sdkserver.service.web.requestEntity.HttpRequestOrderInfo;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * PayBaseController
 *
 * @author Zhang ShanMin
 * @date 2016/8/15
 * @time 10:09
 */
public abstract class PayBaseController extends BaseController {

    @Autowired
    protected PropertiesUtil domainConfig;

    @Autowired
    private NewOpenChannelService newOpenChannelService;

    /**
     * 校验订单创建请求参数
     *
     * @param order
     * @param request
     * @return
     * @throws Exception
     */
    protected SdkOauthResult checkOrderRequest(HttpRequestOrderInfo order, HttpServletRequest request) throws Exception {
        order.setSysType(WebUtils.getSysType(request));
        String channelCode = request.getParameter("channelCode");
        if (StringUtils.isBlank(order.getPayOrigin()))
            order.setPayOrigin("app");
//        if (StringUtils.isBlank(channelCode) || !BqSdkConstants.channelCodeSet.contains(channelCode)) {
        //从redis中获取渠道信息
        if (StringUtils.isBlank(channelCode) || !newOpenChannelService.containChanneCode(channelCode)) {
            return SdkOauthResult.failed(ErrorCodeEnum.渠道号错误.code, ErrorCodeEnum.渠道号错误.name());
        }
        if (request.getRequestURI().indexOf("createOrderInfo") > -1) {
            if ("app".equals(order.getPayOrigin())) {
                if (StringUtils.isBlank(order.getChargePointCode())) {
                    return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
                }
                if (StringUtils.isBlank(order.getGameOrderNo())) {
                    return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
                }
                SysTypeEnum sysTypeEnum = SysTypeEnum.getSysType(request.getHeader("devType"));
                order.setSysType(sysTypeEnum != null ? sysTypeEnum.name() : SysTypeEnum.AD.name());
            }
            if (StringUtils.isBlank(request.getParameter("authToken"))) {
                return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
            }
            if (StringUtils.isBlank(order.getSign())) {
                return SdkOauthResult.failed("2004", "请退出游戏重新登录");
            }
        }
        if (request.getRequestURI().indexOf("createPcOrder") > -1) {
            if (StringUtils.isBlank(request.getParameter("zoneId"))) {
                return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
            }
        }
        if (request.getRequestURI().indexOf("createWapOrder") > -1) {
            if (StringUtils.isBlank(request.getParameter("sign"))) {
                return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, "支付签名信息为空");
            }
        }
        return SdkOauthResult.success();
    }


    /**
     * 获取应用信息
     *
     * @param request
     * @param order
     * @return
     * @throws Exception
     */
    protected SdkOauthResult getAppInfoEntity(HttpServletRequest request, HttpRequestOrderInfo order) {
        SdkOauthResult result = checkClient(order.getAppCode());
        if (!result.isSuccess()) {
            return result;
        }
        AppInfoEntity clientDetails = (AppInfoEntity) result.getData();
        //sdk线上环境,默认是线上环境
        if ("1".equals(domainConfig.getString("sdk.notify.environment", "1"))) {
            order.setGameNotifyUrl(clientDetails.getPayNotifyUrl());
        } else {
            order.setGameNotifyUrl(clientDetails.getTestPayNotifyUrl());
        }
        if (StringUtils.isBlank(order.getGameNotifyUrl()))
            return SdkOauthResult.failed("游戏支付通知地址不能为空");
        return result;
    }


    protected Object getPayInfoByPayType(HttpRequestOrderInfo order, Map<String, String> payConfig, BqChargePointInfo chargePointInfo, HttpServletRequest request) throws Exception {
        if (order.getPayType() == PayTypeEnum.支付宝支付.getCode()) {
            if (PayConstant.ALI_PAY_ORIGIN.PC.name().equalsIgnoreCase(order.getPayOrigin())
                    || PayConstant.ALI_PAY_ORIGIN.RC.name().equalsIgnoreCase(order.getPayOrigin())) {
                return createPcAliPayInfo(order.getOrderNo(), payConfig, chargePointInfo);
            } else if (PayConstant.ALI_PAY_ORIGIN.WAP.name().equalsIgnoreCase(order.getPayOrigin())) {
                return createWapAliPayInfo(order, payConfig, chargePointInfo);
            } else {
                return createAppAliPayInfo(order.getOrderNo(), payConfig, chargePointInfo);
            }
        }

        if (order.getPayType() == PayTypeEnum.微信支付.getCode()) {
            //微信支付方式 0是wap(通用方式) 1是app支付(部落崛起游戏定制使用)
            String wxPayType = payConfig.get(BqSdkConstants.weixinPayType);
            if (PayConstant.ALI_PAY_ORIGIN.WAP.name().equalsIgnoreCase(order.getPayOrigin())) {
                return createWapWeiXinPayInfo(order.getOrderNo(), payConfig, chargePointInfo, request);
            } else if (PayConstant.ALI_PAY_ORIGIN.APP.name().equalsIgnoreCase(order.getPayOrigin())){

                return wxPayType.equals("0") ? createWapWeiXinPayInfo(order.getOrderNo(), payConfig, chargePointInfo, request)
                        : createAppWeiXinPayInfo(order.getOrderNo(), payConfig, chargePointInfo, request);
            }else if (PayConstant.ALI_PAY_ORIGIN.PC.name().equalsIgnoreCase(order.getPayOrigin())){
                Double fee = chargePointInfo.getChargePointAmount();
                Long totalFee =  PriceUtil.convertToFen(fee);
                WeChatEntity weChatEntity = new WeChatEntity.Builder().nonceStr(WechatPayUtil.generateNonceStr()).spBillCreateIp(order.getReqIp()).outTradeNo(order.getOrderNo()).tradeType("NATIVE").body(order.getAppName()+"_充值").totalFee(totalFee).build();
                return WechatPayUtil.weixinPayUnifiedorder(weChatEntity, payConfig);
            }
        }
        return null;
    }

    /**
     * 创建支付宝-移动支付请求信息
     *
     * @param orderNo
     * @param payConfig
     * @param chargePointInfo
     * @return
     * @throws UnsupportedEncodingException
     */
    protected String createAppAliPayInfo(String orderNo, Map<String, String> payConfig, BqChargePointInfo chargePointInfo) throws UnsupportedEncodingException {
        Map<String, String> data = new HashMap<String, String>();
        data.put("partner", "\"" + payConfig.get(BqSdkConstants.aliPayPartner) + "\"");
        data.put("seller_id", "\"" + payConfig.get(BqSdkConstants.aliPaySellerId) + "\"");
        data.put("out_trade_no", "\"" + orderNo + "\"");
        data.put("subject", "\"" + chargePointInfo.getChargePointName() + "\"");
        data.put("body", "\"" + chargePointInfo.getDesc() + "\"");
        data.put("total_fee", "\"" + chargePointInfo.getChargePointAmount() + "\"");
        data.put("notify_url", "\"" + payConfig.get(BqSdkConstants.aliPayNotifyUrl) + "\"");
        data.put("service", "\"mobile.securitypay.pay\"");
        data.put("payment_type", "\"1\"");
        data.put("_input_charset", "\"" + payConfig.get(BqSdkConstants.aliPayCharset) + "\"");
        data.put("it_b_pay", "\"30m\"");
        data.put("show_url", "\"http://m.alipay.com\"");
        String linkStr = AlipayCore.createLinkString(data);
        String charset = payConfig.get(BqSdkConstants.aliPayCharset);
        String sign = RSA.sign(linkStr, payConfig.get(BqSdkConstants.sellerPrivateKey), charset);
        sign = URLEncoder.encode(sign, charset);
        String returnStr = linkStr + "&sign=\"" + sign + "\"&sign_type=\"RSA\"";
        return returnStr;
    }

    /**
     * 创支付宝-即时到账(pc)请求信息
     *
     * @param orderNo
     * @param payConfig
     * @param chargePointInfo
     * @return
     * @throws Exception
     */
    protected Object createPcAliPayInfo(String orderNo, Map<String, String> payConfig, BqChargePointInfo chargePointInfo) throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("service", "create_direct_pay_by_user");
        data.put("notify_url", payConfig.get(BqSdkConstants.aliPayNotifyUrl));//异步通知
        data.put("return_url", payConfig.get(BqSdkConstants.PC_SYNC_NOTIFY_URL));//同步通知
        if (StringUtils.isNotBlank(payConfig.get(BqSdkConstants.EXTRA_COMMON_PARAM))) {
            data.put("extra_common_param", payConfig.get(BqSdkConstants.EXTRA_COMMON_PARAM));//公用回传参数
        }
        return getAliPayInfo(orderNo, payConfig, chargePointInfo, data);
    }

    /**
     * 支付宝-wap支付请求信息
     *
     * @param
     * @param payConfig
     * @param chargePointInfo
     * @return
     * @throws Exception
     */
    protected Object createWapAliPayInfo(HttpRequestOrderInfo order, Map<String, String> payConfig, BqChargePointInfo chargePointInfo) throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("service", "alipay.wap.create.direct.pay.by.user");
//        data.put("app_pay", "Y");//app_pay=Y：尝试唤起支付宝客户端进行支付
        data.put("goods_type", "0");//商品类型,1：实物类商品、0：虚拟类商品 不传默认为实物类商品。
        data.put("notify_url", payConfig.get(BqSdkConstants.aliPayNotifyUrl));//异步通知
        if (StringUtils.isEmpty(order.getReturnUrl())) {
            data.put("return_url", payConfig.get(BqSdkConstants.WAP_SYNC_NOTIFY_URL));//H5游戏同步通知
        } else {
            data.put("return_url", order.getReturnUrl());//同步通知
        }
        return getAliPayInfo(order.getOrderNo(), payConfig, chargePointInfo, data);
    }

    /**
     * 创建支付宝支付请求公用参数
     *
     * @param orderNo
     * @param payConfig
     * @param chargePointInfo
     * @param data
     * @return
     */
    private Object getAliPayInfo(String orderNo, Map<String, String> payConfig, BqChargePointInfo chargePointInfo, Map<String, String> data) {
        data.put("partner", payConfig.get(BqSdkConstants.aliPayPartner));
        data.put("seller_id", payConfig.get(BqSdkConstants.aliPayPartner));
        data.put("_input_charset", payConfig.get(BqSdkConstants.aliPayCharset));
        data.put("payment_type", "1");
        data.put("out_trade_no", orderNo);
        data.put("subject", chargePointInfo.getChargePointName());
        data.put("total_fee", String.valueOf(chargePointInfo.getChargePointAmount()));
        data.put("body", chargePointInfo.getDesc());
        data.put("it_b_pay", "30m");
//        data.put("show_url", "http://m.alipay.com");
        Map<String, String> sPara = AlipayCore.paraFilter(data);
        String linkStr = AlipayCore.createLinkString(sPara);
        String charset = payConfig.get(BqSdkConstants.aliPayCharset);
        String sign = RSA.sign(linkStr, payConfig.get(BqSdkConstants.sellerPrivateKey), charset);
//        sign = URLEncoder.encode(sign, charset);
        sPara.put("sign_type", "RSA");
        sPara.put("sign", sign);
        return AliPayUtil.encapsulatePayKV(sPara);
    }

    /**
     * 微信支付 - 调用统一下单返回预交单信息
     */
    public String createAppWeiXinPayInfo(String orderNo, Map<String, String> payConfig,
                                         BqChargePointInfo chargePointInfo, HttpServletRequest request) throws Exception {
        String nonceStr = WechatPayUtil.generateNonceStr();
        String timestamp = WechatPayUtil.getTimeStamp();
        String ip = WechatPayUtil.getRemoteHost(request);
        Double totalfee = chargePointInfo.getChargePointAmount() * 100;
        WeChatEntity weChatEntity = new WeChatEntity.Builder().nonceStr(nonceStr).spBillCreateIp(ip).outTradeNo(orderNo).tradeType("APP").body(chargePointInfo.getChargePointName()).totalFee(totalfee.longValue()).build();
        String prepay_id = WechatPayUtil.weixinPayUnifiedorder(weChatEntity, payConfig);
//        String urlParam = "";
        Map<String, String> data = new HashMap<String, String>();
        if (StringUtils.isNotBlank(prepay_id)) {
            data.put("appid", payConfig.get(BqSdkConstants.weixinPayAppId));
            data.put("partnerid", payConfig.get(BqSdkConstants.weixinPayMchId));
            data.put("noncestr", nonceStr);
            data.put("prepayid", prepay_id);
            data.put("timestamp", timestamp);
            data.put("package", "Sign=WXPay");
            String signKey = payConfig.get(BqSdkConstants.weixinPaySignKey);
            String sign = WechatPayUtil.createSign(data, signKey);
            data.put("sign", sign);
            log.info("微信支付页面 - sign:{}", sign);
//            urlParam = WechatPayUtil.maptoXml(data);
            log.info("微信支付页面参数 - data:{}", JSONObject.toJSONString(data));
        }
        return JSONObject.toJSONString(data);
    }

    /**
     * 微信H5支付 - 调用统一下单返回url
     */
    public String createWapWeiXinPayInfo(String orderNo, Map<String, String> payConfig,
                                         BqChargePointInfo chargePointInfo, HttpServletRequest request) throws Exception {
        String nonceStr = WechatPayUtil.generateNonceStr();
        String ip = WechatPayUtil.getRemoteHost(request);
        Double totalfee = chargePointInfo.getChargePointAmount() * 100;
        WeChatEntity weChatEntity = new WeChatEntity.Builder().nonceStr(nonceStr).spBillCreateIp(ip).outTradeNo(orderNo).tradeType("MWEB").body(chargePointInfo.getChargePointName()).totalFee(totalfee.longValue()).build();
        String mweb_url = WechatPayUtil.weixinPayUnifiedorder(weChatEntity, payConfig);
        if (StringUtils.isBlank(mweb_url)) {
            log.error("调用统一下单接口发生错误!");
        }
        return mweb_url;
    }

    /**
     * 微信查询订单
     */
    public JSONObject weiXinOrderQuery(String orderNo, Map<String, String> payConfig) throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", payConfig.get(BqSdkConstants.weixinPayAppId));
        data.put("mch_id", payConfig.get(BqSdkConstants.weixinPayMchId));
        data.put("out_trade_no", orderNo);
        data.put("nonce_str", WechatPayUtil.generateNonceStr());
        String signKey = payConfig.get(BqSdkConstants.weixinPaySignKey);
        String sign = WechatPayUtil.createSign(data, signKey);
        data.put("sign", sign);
        log.info("微信查询订单 - sign:{}", sign);

        String urlParam = WechatPayUtil.maptoXml(data);
        log.info("微信查询订单 xml参数 --> urlParam:{}", urlParam);
        log.info("----------------------------------------------");
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setReqContent(payConfig.get(BqSdkConstants.weixinPayOrderQuery));
        String resContent = "";
        Map<String, Object> result = null;
        if (httpClient.callHttpPost(payConfig.get(BqSdkConstants.weixinPayOrderQuery), urlParam)) {
            resContent = httpClient.getResContent();
            Map<String, Object> mapParam = WechatPayUtil.xmltoMap(resContent);
            log.info("*********************************************************************");
            log.info("微信查询订单接口返回值 -----> map:{}", mapParam);
            log.info("*********************************************************************");
            result = new HashMap<>();
            if ("SUCCESS".equals(mapParam.get("return_code")) && "SUCCESS".equals(mapParam.get("result_code")) && "SUCCESS".equals(mapParam.get("trade_state"))) {
                log.info("查询订单支付成功!");
                result.put("isPay", "true");
            } else {
                log.error("微信查询订单接口失败,return_msg:{},err_code:{},err_code_des:{},trade_state:{},trade_state_desc:{}",
                        mapParam.get("return_msg"),
                        StringUtils.isNotBlank((String) mapParam.get("err_code")) ? mapParam.get("err_code") : "",
                        StringUtils.isNotBlank((String) mapParam.get("err_code_des")) ? mapParam.get("err_code_des") : "",
                        StringUtils.isNotBlank((String) mapParam.get("trade_state")) ? mapParam.get("trade_state") : "",
                        StringUtils.isNotBlank((String) mapParam.get("trade_state_desc")) ? mapParam.get("trade_state_desc") : "");
                result.put("isPay", "false");
            }
        }
        return new JSONObject(result);
    }



    /**
     * 获取支付宝即时支付响应报文
     *
     * @param orderReqInfo
     * @param result
     * @return
     * @throws Exception
     */
    protected Map<String, Object> getAliPayDownResp(HttpRequestOrderInfo orderReqInfo, SdkOauthResult result) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>(3);
        resultMap.put("payUrl", AliPayUtil.AliGateWay.PAYDOWN.getGateWap());
        resultMap.put("sdkOrderNo", orderReqInfo.getOrderNo());
        if (orderReqInfo.getResFormat() == 0) { //响应格式  0:返回kv,1:返回支付宝form表单
            resultMap.put("payInfo", result.getData());
        } else {
            resultMap.put("form", AliPayUtil.getAliWapPayInfo((Object[]) result.getData()));
        }
        return resultMap;
    }

}
