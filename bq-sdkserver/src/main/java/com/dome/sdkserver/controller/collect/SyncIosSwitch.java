package com.dome.sdkserver.controller.collect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.enumeration.ErrorCodeEnum;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.constants.RedisKeyEnum;
import com.dome.sdkserver.controller.pay.basic.PayBaseController;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.BqChargePointInfo;
import com.dome.sdkserver.metadata.entity.bq.pay.PayIosSwitch;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.chargePoint.ChargePointService;
import com.dome.sdkserver.service.login.QuickLoginService;
import com.dome.sdkserver.service.pay.PayIosSwitchService;
import com.dome.sdkserver.service.web.requestEntity.HttpRequestOrderInfo;
import com.dome.sdkserver.util.MD5;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RedisUtil;
import com.dome.sdkserver.web.tools.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuekuan on 2017/4/12.
 */
@Controller
@RequestMapping("syncIosSwitch")
public class SyncIosSwitch extends PayBaseController {
    @Autowired
    private PayIosSwitchService payIosSwitchService;
    @Autowired
    private ChargePointService chargePointService;
    @Autowired
    private QuickLoginService quickLoginService;
    @Autowired
    private PropertiesUtil domainConfig;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 支付绕行开关添加、编辑
     *
     * @param payIosSwitch
     * @return
     */
    @RequestMapping("insertOrUpdate")
    @ResponseBody
    public SdkOauthResult syncIosSwitch(PayIosSwitch payIosSwitch) {
        log.info("支付绕行接受开放平台同步参数：{}", payIosSwitch.toString());
        try {
            String appCode = payIosSwitch.getAppCode();
            if (StringUtils.isBlank(appCode)) {
                log.info("支付绕行接受开放平台同步appCode为空...");
                return SdkOauthResult.failed("支付绕行接受开放平台同步appCode为空...");
            }
            Integer isAround = payIosSwitch.getIsAround();
            if (null == isAround || "".equals(isAround)) {
                log.info("支付绕行接受开放平台同步绕行开关为空...");
                return SdkOauthResult.failed("支付绕行接受开放平台同步绕行开关为空...");
            }
            boolean isSuccess = payIosSwitchService.insertOrUpdate(payIosSwitch);
            if (!isSuccess) {
                return SdkOauthResult.failed("支付绕行接受开放平台同步失败");
            }
            return SdkOauthResult.success("支付绕行接受开放平台同步成功");
        } catch (Exception e) {
            log.error("支付绕行接受开放平台同步失败...{}", e);
            return SdkOauthResult.failed("支付绕行接受开放平台同步失败...");
        }
    }

    /**
     * 支付绕行开关同步删除
     * @param payIosSwitch
     * @return
     */
    @RequestMapping("delByAppCode")
    @ResponseBody
    public SdkOauthResult delByAppCode(PayIosSwitch payIosSwitch) {
        log.info("支付绕行接收同步删除appCode:{}", payIosSwitch.getAppCode());
        return payIosSwitchService.delByAppCode(payIosSwitch) ? SdkOauthResult.success("支付绕行同步删除成功") : SdkOauthResult.failed("支付绕行同步删除失败", payIosSwitch.getAppCode());
    }

    /**
     * 支付绕行开关
     *
     * @param request
     * @param order
     * @param authToken
     * @return
     */
    @RequestMapping("startJob")
    @ResponseBody
    public SdkOauthResult paySwitch(HttpServletRequest request, HttpRequestOrderInfo order, String authToken) {
        log.info("支付绕行开关请求参数：{}，authToken:{}", JSONObject.toJSONString(order), authToken);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        SdkOauthResult result = null;
        try {
            result = validateReqParams(request, order, authToken);
            if (!result.isSuccess()) {
                return result;
            }
            HttpRequestOrderInfo orderInfo = (HttpRequestOrderInfo) result.getData();
            String appCode = orderInfo.getAppCode();
            JSONObject response = quickLoginService.getUserByToken(authToken, appCode);
            JSONObject data = response.getJSONObject("data");
            log.info("支付绕行开关获取到的用户信息：{},response:{}", JSONObject.toJSONString(data), JSONObject.toJSONString(response));
            if (data == null) {
                log.info("支付绕行开关没有该authToken和appCode对应的用户信息,authToken:{},appCode:{}", authToken, appCode);
                return SdkOauthResult.failed("支付绕行开关没有该authToken和appCode对应的用户信息");
            }
            String userId = data.getString(BqSdkConstants.domeUserId);
            orderInfo.setBuyerId(userId);

            PayIosSwitch payIosSwitch = new PayIosSwitch();
            payIosSwitch.setAppCode(appCode);
            String payIosSwitchData= redisUtil.get(RedisKeyEnum.BQ_IOS_PAY_SWITCH.getKey() + payIosSwitch.getAppCode());
            log.info(">>>>>>>>>>>>>>>>payIosSwitchData:{}",JSONArray.parseObject(payIosSwitchData));
            if (null == payIosSwitchData){
                payIosSwitch = payIosSwitchService.selectByAppCode(payIosSwitch);
                if (null != payIosSwitch){
                    redisUtil.setex(RedisKeyEnum.BQ_IOS_PAY_SWITCH.getKey() + payIosSwitch.getAppCode(), RedisKeyEnum.BQ_IOS_PAY_SWITCH.getExpireTime(), JSONObject.toJSONString(payIosSwitch));
                }else{
                    resultMap.put("isAround", 0);//开放平台没有同步数据，默认关闭绕行开关，走苹果支付
                    return SdkOauthResult.success(resultMap);
                }
            }else{
                payIosSwitch = JSONArray.parseObject(payIosSwitchData, PayIosSwitch.class);
            }

            log.info("支付绕行开关根据appCode获取到的支付开关信息：{}", JSONObject.toJSONString(payIosSwitch));
            if (payIosSwitch == null) {
                log.info("支付绕行开关没有该appCode对应的支付绕行信息,appCode:{}", appCode);
                return SdkOauthResult.failed("支付绕行开关没有该appCode对应的支付绕行信息,appCode:{}", appCode);
            }
            orderInfo.setPay_Type(payIosSwitch.getPayType().replace(",", "|"));
            int isAround = payIosSwitch.getIsAround();
            //设置字符来源
            orderInfo.setPayOrigin("wap");
            String payUrl = null;
            String sign = sign(orderInfo);
            if (1 == isAround) {
                //绕行开启的时候返回给客户端url
                String base64String = getBase64String(orderInfo, sign);
                payUrl = domainConfig.getString("pay.ios.switch.url");
                payUrl = payUrl + "?" + "data=" + base64String;
                resultMap.put("url", payUrl);
                resultMap.put("isAround", isAround);
                String base1 = new String(org.apache.commons.codec.binary.Base64.decodeBase64((base64String)));
                log.info(">>>>>>>>>>>>>>>>>>>>>>解密后的字符串base1:{}", URLDecoder.decode(base1, "utf-8"));
                return SdkOauthResult.success(resultMap);
            } else {
                resultMap.put("isAround", isAround);
                return SdkOauthResult.success(resultMap);
            }
        } catch (Exception e) {
            log.info("支付绕行开关异常：", e);
            return SdkOauthResult.failed("支付绕行开关异常");
        }
    }

    private SdkOauthResult validateReqParams(HttpServletRequest request, HttpRequestOrderInfo orderInfo, String authToken) {
        SdkOauthResult result;
        String chargePointCode = orderInfo.getChargePointCode();
        if (StringUtils.isBlank(chargePointCode)) {
            return SdkOauthResult.failed("支付绕行计费点Code为空");
        }
        String appCode = orderInfo.getAppCode();
        if (StringUtils.isBlank(appCode)) {
            return SdkOauthResult.failed(ErrorCodeEnum.appCode为空.code, ErrorCodeEnum.appCode为空.name());
        }
        result = getAppInfoEntity(request, orderInfo);
        if (!result.isSuccess()) {
            return result;
        }
        AppInfoEntity appInfo = (AppInfoEntity) result.getData();
        log.info("获取到的应用信息：{}", JSONObject.toJSONString(appInfo));
        orderInfo.setAppName(appInfo.getAppName());
        BqChargePointInfo chargePointInfo = chargePointService.getChargePointInfoByCode(chargePointCode, appCode);
        log.info("获取到的计费点信息：{}", JSONObject.toJSONString(chargePointCode));
        orderInfo.setChargePointName(chargePointInfo.getChargePointName());
        orderInfo.setChargePointAmount(chargePointInfo.getChargePointAmount());
        if (chargePointInfo == null) {
            log.info("支付绕行开关没有该计费点对应的信息chargePointCode：{},appCode:{}", chargePointCode, appCode);
            return SdkOauthResult.failed(ErrorCodeEnum.无效的计费点.code, ErrorCodeEnum.无效的计费点.name());
        }
        String channelCode = orderInfo.getChannelCode();
        if (StringUtils.isBlank(channelCode)) {
            return SdkOauthResult.failed("渠道号为空");
        }
        String gameOrderNo = orderInfo.getGameOrderNo();
        if (StringUtils.isBlank(gameOrderNo)) {
            return SdkOauthResult.failed("游戏方订单号为空");
        }
        if (StringUtils.isBlank(authToken)) {
            return SdkOauthResult.failed(ErrorCodeEnum.token为空.code, ErrorCodeEnum.token为空.name());
        }
        return SdkOauthResult.success(orderInfo);
    }

    private String sign(HttpRequestOrderInfo order) {
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("channelCode", order.getChannelCode());
        signMap.put("gameOrderNo", order.getGameOrderNo());
        signMap.put("appCode", order.getAppCode());
        signMap.put("userId", order.getBuyerId());
        signMap.put("chargePointCode", order.getChargePointCode());
        signMap.put("payOrigin", "wap");
        String str = MapUtil.createLinkString(signMap) + "&WapPay=" + domainConfig.getString("pay.ios.switch.sign.key");
        return MD5.md5Encode(str);
    }

    private String getBase64String(HttpRequestOrderInfo orderInfo, String sign) throws Exception {
        Map<String, Object> signMap = new HashMap<String, Object>();
        signMap.put("channelCode", orderInfo.getChannelCode());
        signMap.put("gameOrderNo", orderInfo.getGameOrderNo());
        signMap.put("appCode", orderInfo.getAppCode());
        signMap.put("buyerId", orderInfo.getBuyerId());
        signMap.put("chargePointCode", orderInfo.getChargePointCode());
        signMap.put("sign", sign);
        signMap.put("appName", URLEncoder.encode(orderInfo.getAppName(), "utf-8"));
        signMap.put("chargePointAmount", orderInfo.getChargePointAmount());
        signMap.put("chargePointName", URLEncoder.encode(orderInfo.getChargePointName(), "utf-8"));
        signMap.put("payType", orderInfo.getPay_Type());
        signMap.put("payOrigin", "wap");
        return Base64.encode(JSONObject.toJSONString(signMap).getBytes());
    }
}
