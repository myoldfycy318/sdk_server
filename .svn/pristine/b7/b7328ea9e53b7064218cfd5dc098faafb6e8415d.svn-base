package com.dome.sdkserver.biz.utils;

import com.dome.sdkserver.bq.constants.PayConstant;
import com.dome.sdkserver.bq.enumeration.OpenSelectEnum;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.service.game.GameService;
import com.dome.sdkserver.service.web.requestEntity.HttpRequestOrderInfo;
import com.dome.sdkserver.util.MD5;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RSACoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * BizUtil
 *
 * @author Zhang ShanMin
 * @date 2017/10/17
 * @time 11:01
 */
public class BizUtil {

    private static  Logger logger = LoggerFactory.getLogger(BizUtil.class);
    /**
     * @param appInfoEntity
     * @param envFlag       环境标识，0：测试环境，1：生产环境
     * @return
     */
    public static String getGameUrl(AppInfoEntity appInfoEntity, Integer envFlag) {
        return (appInfoEntity.getOpenSelect() == OpenSelectEnum.新开放平台.getOpenSelect() && envFlag == 0) ? appInfoEntity.getTestGameUrl() : appInfoEntity.getGameUrl();
    }


    /**
     * 验证请求参数
     *
     * @param orderReqInfo
     * @return
     */
    public static SdkOauthResult validateOrderCreateParams(HttpRequestOrderInfo orderReqInfo) {
        if (StringUtils.isBlank(orderReqInfo.getBuyerId()) ||
                "null".equalsIgnoreCase(orderReqInfo.getBuyerId()) ||
                StringUtils.isBlank(orderReqInfo.getOutOrderNo()) ||
                StringUtils.isBlank(orderReqInfo.getNotifyUrl()) ||
                StringUtils.isBlank(orderReqInfo.getSignCode()) ||
                StringUtils.isBlank(orderReqInfo.getPayOrigin()) ||
                StringUtils.isBlank(orderReqInfo.getSubject()) ||
                StringUtils.isBlank(orderReqInfo.getBody()) ||
                StringUtils.isBlank(orderReqInfo.getReqIp()))
            return SdkOauthResult.failed("必填参数不能为空");
        if (orderReqInfo.getTotalFee() == 0) return SdkOauthResult.failed("支付金额不能为空");
        PayConstant.PAY_REQ_ORIGIN payReqOrigin = PayConstant.PAY_REQ_ORIGIN.getPayReqOrigin(orderReqInfo.getPayOrigin());
        if (payReqOrigin == null) return SdkOauthResult.failed("未知支付来源");
        switch (payReqOrigin) {
            case VR_PAY: {
                if (StringUtils.isBlank(orderReqInfo.getAppCode()) ||
                        StringUtils.isBlank(orderReqInfo.getChannelCode())||
                        StringUtils.isBlank(orderReqInfo.getSysType()))
                    return SdkOauthResult.failed("必填参数不能为空");
            }
            break;
        }
        try {
            orderReqInfo.setSubject(new String(org.apache.commons.codec.binary.Base64.decodeBase64(orderReqInfo.getSubject()), "utf-8"));
            orderReqInfo.setBody(new String(org.apache.commons.codec.binary.Base64.decodeBase64(orderReqInfo.getBody()), "utf-8"));
        } catch (Exception e) {
            SdkOauthResult.failed("必填参数数据解析错误");
        }
        return SdkOauthResult.success();
    }

    /**
     * 验证签名
     *
     * @param orderReqInfo
     * @return
     */
    public static SdkOauthResult validateCreateOrderSign(HttpRequestOrderInfo orderReqInfo, PropertiesUtil propertiesUtil) throws Exception {
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("outOrderNo", orderReqInfo.getOutOrderNo());
        signMap.put("buyerId", orderReqInfo.getBuyerId());
        signMap.put("totalFee", String.valueOf(orderReqInfo.getTotalFee()));
        signMap.put("notifyUrl", orderReqInfo.getNotifyUrl());
        signMap.put("payOrigin", orderReqInfo.getPayOrigin());
        String signParam = "";
        PayConstant.PAY_REQ_ORIGIN payReqOrigin = PayConstant.PAY_REQ_ORIGIN.getPayReqOrigin(orderReqInfo.getPayOrigin());
        String paySignKey = propertiesUtil.getString(payReqOrigin.getPayKey());
        boolean signResult = false;
        switch (payReqOrigin) {
            case VR_PAY: {
                signMap.put("appCode", orderReqInfo.getAppCode());
                StringBuilder sb = new StringBuilder(MapUtil.createLinkString(signMap)).append("&key=").append(paySignKey);
                signResult = orderReqInfo.getSignCode().equals(MD5.md5Encode(sb.toString()));
            }
            break;
            default: {
                signParam = MapUtil.createLinkString(signMap);
                signResult = RSACoder.verify(signParam.getBytes(), paySignKey, orderReqInfo.getSignCode());
            }
        }
        return signResult ? SdkOauthResult.success() : SdkOauthResult.failed("支付签名错误");
    }

    /**
     * 根据appCode获取应用信息
     * @param appCode
     * @return
     */
    public static AppInfoEntity getAppInfo(String appCode) {
        AppInfoEntity appInfoEntity = null;
        try {
            GameService gameService = SpringBeanProxy.getBean(GameService.class, "gameService");
            if (gameService == null) {
                logger.error("appCode:{}获取不到对应应用信息", appCode);
                return null;
            }
            return gameService.getAppInfo(appCode);
        } catch (Exception e) {
            logger.info("appCode:{}获取不到对应应用信息异常", appCode, e);
        }
        return null;
    }

}
