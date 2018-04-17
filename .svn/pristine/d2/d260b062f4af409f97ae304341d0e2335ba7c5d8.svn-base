/**
 *
 */
package com.dome.sdkserver.controller.pay.qbao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.utils.H5GameUtil;
import com.dome.sdkserver.bq.constants.PayConstant;
import com.dome.sdkserver.bq.constants.PayResEnum;
import com.dome.sdkserver.bq.constants.redis.RedisConstants;
import com.dome.sdkserver.bq.enumeration.SysTypeEnum;
import com.dome.sdkserver.bq.util.IPUtil;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.controller.pay.basic.PayBaseController;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.service.game.GameService;
import com.dome.sdkserver.service.pay.qbao.SdkPayService;
import com.dome.sdkserver.service.pay.qbao.bo.SdkDopayRequest;
import com.dome.sdkserver.service.pay.qbao.bo.SdkPayRequest;
import com.dome.sdkserver.service.pay.qbao.bo.SdkPayResponse;
import com.dome.sdkserver.service.redis.RedisService;
import com.dome.sdkserver.util.DESUtil;
import com.dome.sdkserver.util.RSACoder;
import com.dome.sdkserver.util.RedisUtil;
import com.dome.sdkserver.util.WebUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping("/sdkpay/v10")
public class SdkPayController extends PayBaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SdkPayService sdkPayService;
    @Resource
    RedisService redisService;
    @Autowired
    private RedisUtil redisUtil;
    @Resource(name = "gameService")
    private GameService gameService;

    /**
     * 获得用户支付信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/payrequest")
    @ResponseBody
    public SdkPayResponse payRequest(HttpServletRequest request) {
        SdkPayResponse sdkPayResponse = null;
        try {
            //用户支付信息参数解析
            SdkPayRequest payRequest = payRequestParam(request);
            logger.info("获取用户支付信息请求参数：{}", JSONObject.toJSONString(payRequest));
            sdkPayResponse = sdkPayService.dealWithPayRequest(payRequest);
        } catch (Exception e) {
            logger.error("获取支付信息异常:{}", e);
            return new SdkPayResponse(PayResEnum.FAIL_CODE.getCode()
                    , PayResEnum.UPDATE_TRANS_ERROR.getCode()
                    , PayResEnum.UPDATE_TRANS_ERROR.getMsg()
                    , null);
        }
        logger.info("获取支付信息返回结果:{}", JSON.toJSONString(sdkPayResponse));
        return sdkPayResponse;

    }

    /**
     * SDK支付
     *
     * @param request
     * @param sdkDopayBo
     * @return
     */
    @RequestMapping(value = "/dopay", method = RequestMethod.POST)
    @ResponseBody
    public SdkPayResponse dopay(HttpServletRequest request, SdkDopayRequest sdkDopayBo) {
        logger.info("SDK支付请求参数：{}", sdkDopayBo);
        SdkPayResponse response = sdkPayService.dopay(sdkDopayBo);
        logger.info("支付返回结果:{}", JSON.toJSONString(response));
        return response;
    }


    /**
     * 获得用户支付信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/createWebGameOrder")
    @ResponseBody
    public SdkPayResponse createWebGameOrder(HttpServletRequest request, SdkPayRequest payRequest) {
        SdkPayResponse sdkPayResponse = null;
        try {
            sdkPayResponse = validateReqParams(request, payRequest);
            if (!SdkPayResponse.isSuccess(sdkPayResponse))
                return sdkPayResponse;
            String buyerId = PayUtil.orderDesDecrypt(payRequest.getBuyerId(), domainConfig.getString("ali.pay.webpagegame.des.secretkey"));
            int lockTime = domainConfig.getInt("pay.webgame.qbao.user.lock.time", "1");
            if (redisUtil.tryLock(RedisConstants.QBAO_PAY_LOCK + buyerId, lockTime, "lock"))
                return SdkPayResponse.failed("操作频繁，请稍后重试");
            payRequest.setUserId(Long.valueOf(buyerId));
            payRequest.setZoneId(request.getParameter("zoneId"));
            AppInfoEntity appInfoEntity = gameService.getAppInfo(request.getParameter("appCode"));
            if (appInfoEntity == null)
                return SdkPayResponse.failed(PayResEnum.APP_CODE_NO_EXISTS.getCode(), PayResEnum.APP_CODE_NO_EXISTS.getMsg());
            sdkPayResponse = sdkPayService.createWebGameOrder(payRequest, appInfoEntity);
            return sdkPayResponse;
        } catch (Exception e) {
            logger.error("页游钱宝支付创建订单异常", e);
            return SdkPayResponse.failed("系统异常，请稍后重试");
        }
    }

    /**
     * 获得用户支付信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/createWapGameOrder")
    @ResponseBody
    public SdkPayResponse createWapGameOrder(HttpServletRequest request, final SdkPayRequest payRequest) {
        SdkPayResponse sdkPayResponse = null;
        try {
            sdkPayResponse = validateReqParams(request, payRequest);
            if (!SdkPayResponse.isSuccess(sdkPayResponse))
                return sdkPayResponse;
            String buyerId = PayUtil.orderDesDecrypt(payRequest.getBuyerId(), domainConfig.getString("ali.pay.webpagegame.des.secretkey"));
            String zoneId = PayUtil.orderDesDecrypt(payRequest.getZoneId(), domainConfig.getString("ali.pay.webpagegame.des.secretkey"));
            int lockTime = domainConfig.getInt("pay.wapbgame.qbao.user.lock.time", "1");
            if (redisUtil.tryLock(RedisConstants.QBAO_WAP_PAY_LOCK + buyerId, lockTime, "lock"))
                return SdkPayResponse.failed("操作频繁，请稍后重试");
            payRequest.setUserId(Long.valueOf(buyerId));
            payRequest.setZoneId(zoneId);
            AppInfoEntity appInfoEntity = gameService.getAppInfo(payRequest.getAppCode());
            if (appInfoEntity == null)
                return SdkPayResponse.failed(PayResEnum.APP_CODE_NO_EXISTS.getCode(), PayResEnum.APP_CODE_NO_EXISTS.getMsg());
            //验证创建订单签名
            if (!H5GameUtil.validateSign(new HashMap<String, String>() {
                {
                    put("appCode", payRequest.getAppCode());
                    put("price", payRequest.getChargePointAmount().intValue() + "");
                }
            }, appInfoEntity.getPayKey(), request.getParameter("sign")))
                return SdkPayResponse.failed("支付签名错误，请稍后重试");
            sdkPayResponse = sdkPayService.createWebGameOrder(payRequest, appInfoEntity);
            return sdkPayResponse;
        } catch (Exception e) {
            logger.error("页游钱宝支付创建订单异常", e);
            return SdkPayResponse.failed("系统异常，请稍后重试");
        }
    }


    /**
     * 钱宝混合支付，供外部调用
     *
     * @param request
     * @param sdkPayRequest
     * @return
     */
    @RequestMapping(value = "/qbBlendPay", method = RequestMethod.POST)
    @ResponseBody
    public SdkPayResponse qbBlendPay(HttpServletRequest request, SdkPayRequest sdkPayRequest) {
        logger.info("钱宝混合支付请求参数：{}", JSONObject.toJSONString(sdkPayRequest));
        SdkPayResponse response = null;
        String redisKey = "";
        try {
            //校验混合支付请求参数
            response = validateBqBlendPayParams(sdkPayRequest);
            if (!SdkPayResponse.isSuccess(response))
                return response;
            //验证签名
            response = validateBqBlendPaySign(request,sdkPayRequest);
            if (!SdkPayResponse.isSuccess(response))
                return response;
            int lockTime = domainConfig.getInt("qbao.blend.pay.user.lock.time", "5");
            redisKey = RedisConstants.QBAO_BLEND_PAY_LOCK + sdkPayRequest.getPayOrigin() + ":" + sdkPayRequest.getUserId();
            //支付锁定
            if (redisUtil.tryLock(redisKey, lockTime, "lock"))
                return SdkPayResponse.failed("支付中，请稍后");
            response = sdkPayService.qbBlendPay(sdkPayRequest);
        } catch (Exception e) {
            logger.error("钱宝混合支付异常", e);
            return SdkPayResponse.failed("系统异常请稍后重试");
        } finally {
            redisUtil.del(redisKey);//删除支付锁定
        }
        logger.info("钱宝混合支付返回结果:{}", JSON.toJSONString(response));
        return response;
    }

    /**
     * 校验混合支付签名
     *
     * @param payRequest
     * @return
     */
    private SdkPayResponse validateBqBlendPaySign(HttpServletRequest request,SdkPayRequest payRequest) throws Exception {
        //支付来源
        PayConstant.PAY_ORIGIN payOrigin = PayConstant.PAY_ORIGIN.getPayORIGIN(payRequest.getPayOrigin());
        //是否需要密码支付
        PayConstant.IS_PAY_PW isPayPw = PayConstant.IS_PAY_PW.getIsPayPw(payRequest.getIsNeedPw());
        if (isPayPw ==  PayConstant.IS_PAY_PW.NEED_PW){
            payRequest.setTransPassWord((DESUtil.desBase64Decrypt(String.valueOf(payRequest.getTransPassWord()), domainConfig.getString(payOrigin.getDesKey()))));
        }
        String transInfo = StringUtils.isNotBlank(payRequest.getTransIntro()) ? new String(Base64.decodeBase64(payRequest.getTransIntro()), "utf-8") : payOrigin.getDesc() + "充值";
        payRequest.setTransIntro(transInfo);
        payRequest.setIp(IPUtil.getIpAddr(request));//请求Ip
        Map<String, String> signParams = new HashMap<String, String>();
        signParams.put("userId", String.valueOf(payRequest.getUserId()));
        signParams.put("orderNo", payRequest.getOrderNo());
        signParams.put("appSource", String.valueOf(payRequest.getAppSource()));
        signParams.put("transAmount", String.valueOf(payRequest.getTransAmount()));
        signParams.put("rmbAmount", String.valueOf(payRequest.getRmbAmount()));
        signParams.put("bqAmount", String.valueOf(payRequest.getBqAmount()));
        signParams.put("feeAmount", String.valueOf(payRequest.getFeeAmount()));
        signParams.put("payType", String.valueOf(payRequest.getPayType()));
        signParams.put("payOrigin", payRequest.getPayOrigin());
        String rsaPublicKey = domainConfig.getString(payOrigin.getRsaKey());
        return RSACoder.verify(MapUtil.createLinkString(signParams).getBytes(), rsaPublicKey, payRequest.getSignCode()) ?
                SdkPayResponse.success() : SdkPayResponse.failed("签名验证失败");
    }

    /**
     * 校验混合支付请求参数
     *
     * @param payRequest
     * @return
     */
    private SdkPayResponse validateBqBlendPayParams(SdkPayRequest payRequest) {
        if (StringUtils.isBlank(payRequest.getOrderNo()) || null == payRequest.getUserId() ||
                null == payRequest.getAppSource() || null == payRequest.getTransAmount() ||
                null == payRequest.getRmbAmount() || null == payRequest.getBqAmount() ||
                null == payRequest.getFeeAmount() || null == payRequest.getPayType() ||
                StringUtils.isBlank(payRequest.getSignCode()) || StringUtils.isBlank(payRequest.getPayOrigin()))
            return SdkPayResponse.failed("必填参数为空");
        //是否需要密码支付
        PayConstant.IS_PAY_PW isPayPw = PayConstant.IS_PAY_PW.getIsPayPw(payRequest.getIsNeedPw());
        if (isPayPw == PayConstant.IS_PAY_PW.NEED_PW && StringUtils.isBlank(payRequest.getTransPassWord()))
             return SdkPayResponse.failed("支付密码为空");
        if (StringUtils.isBlank(payRequest.getSignCode()))
            return SdkPayResponse.failed("签名参数不能为空");
        if (null == PayConstant.SS_BUSI_TYPE.getBusiType(payRequest.getPayOrigin()) ||
                null == PayConstant.PAY_ORIGIN.getPayORIGIN(payRequest.getPayOrigin()))
            return SdkPayResponse.failed("没有该支付来源");
        if (null == PayConstant.PAY_TYPE.getPayType(payRequest.getPayType()))
            return SdkPayResponse.failed("不支持该支付类型");
        return SdkPayResponse.success();
    }

    public SdkPayResponse validateReqParams(HttpServletRequest request, SdkPayRequest payRequest) throws Exception {
        if (StringUtils.isBlank(payRequest.getAppCode()))
            return SdkPayResponse.failed("AppCode不能为空");
        if (StringUtils.isBlank(payRequest.getZoneId()))
            return SdkPayResponse.failed("游戏服务不能为空");
        if (StringUtils.isBlank(payRequest.getBuyerId()))
            return SdkPayResponse.failed("UserId为空");
        if (payRequest.getChargePointAmount() == null)
            return SdkPayResponse.failed("ChargePointmount为空");
        if (request.getRequestURI().indexOf("createWapGameOrder") > -1) {
            if (StringUtils.isBlank(request.getParameter("sign"))) {
                return SdkPayResponse.failed("支付签名信息为空");
            }
        }
        String chargePointCode = StringUtils.isBlank(payRequest.getChargePointCode()) ? "C0000000" : payRequest.getChargePointCode();
        payRequest.setBillingCode(chargePointCode);
        payRequest.setSysType(WebUtils.getSysType(request));
        if (StringUtils.isNotBlank(payRequest.getZoneName())){
            payRequest.setZoneName(URLDecoder.decode(payRequest.getZoneName(),"UTF-8"));
        }
        return SdkPayResponse.success();
    }

    /**
     * 用户支付信息参数解析
     *
     * @param request
     * @return
     */
    public SdkPayRequest payRequestParam(HttpServletRequest request) {
        //接收参数
        String transType = request.getParameter("transType");//交易类型 默认 2012
        String appCode = request.getParameter("appCode");//应用编码
        String orderNo = request.getParameter("orderNo");//业务流水号
        String userId = request.getParameter("userId");//用户ID
        String billingCode = request.getParameter("payCode");//计费点编码
        String appSource = request.getParameter("appSource");//应用渠道 0：IOS 1：Web端 2：Android
        String transIntro = request.getParameter("transIntro");//交易简介
        String payCallbackUrl = request.getParameter("payNotifyUrl");//异步通知URL
        String signCode = request.getParameter("signCode");//签名
        String signType = request.getParameter("signType");//签名
        String channelCode = request.getParameter("channelCode");//签名

        //封装参数
        SdkPayRequest payRequest = new SdkPayRequest();
        payRequest.setTransType(transType);
        payRequest.setAppCode(appCode);
        payRequest.setOrderNo(orderNo);
        payRequest.setUserId(Long.valueOf(userId));
        payRequest.setBillingCode(billingCode);
        payRequest.setAppSource(Integer.parseInt(appSource));
        payRequest.setTransIntro(transIntro);
        payRequest.setPayCallbackUrl(payCallbackUrl);
        payRequest.setSignCode(signCode);
        payRequest.setChannelCode(channelCode);
        payRequest.setSignType(signType);
        payRequest.setRoleId(request.getParameter("roleId"));
        payRequest.setZoneId(request.getParameter("zoneId"));
        payRequest.setZoneName(request.getParameter("zoneName"));
        SysTypeEnum sysTypeEnum = SysTypeEnum.getSysType(request.getHeader("devType"));
        payRequest.setSysType(sysTypeEnum != null ? sysTypeEnum.name() : SysTypeEnum.AD.name());
        return payRequest;


    }


    /**
     * 获取用户可用余额(人民币)
     *
     * @param payRequest
     * @return
     */
    @RequestMapping(value = "/queryUserBalance")
    @ResponseBody
    public SdkPayResponse queryAvailableBalance(SdkPayRequest payRequest) {
        try {
            String key = domainConfig.getString("qbao.query.user.balance.des.key");
            logger.info(" 获取用户可用余额,请求参数：{},签名key:{}",JSONObject.toJSONString(payRequest),key);
            payRequest.setUserId(Long.valueOf(DESUtil.desBase64Decrypt(payRequest.getBuyerId() + "", key)));
        } catch (Exception e) {
            return SdkPayResponse.failed("userId解密错误");
        }
        Long availableBalance = sdkPayService.queryAvailableBalance(payRequest);
        Map<String, Object> map = new HashMap<String, Object>(1);
        if (null != availableBalance) {
            map.put("availableBalance", availableBalance);
        }
        return availableBalance == null ? SdkPayResponse.failed("") : SdkPayResponse.success(map);
    }

}
