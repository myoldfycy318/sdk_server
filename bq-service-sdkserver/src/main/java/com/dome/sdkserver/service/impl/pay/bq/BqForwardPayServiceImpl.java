package com.dome.sdkserver.service.impl.pay.bq;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.utils.H5GameUtil;
import com.dome.sdkserver.bq.constants.ThirdPartyEnum;
import com.dome.sdkserver.bq.enumeration.H5Game2PlatformEnum;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.h5game.H5GameEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.service.login.UserLoginService;
import com.dome.sdkserver.service.pay.bq.ForwardPayService;
import com.dome.sdkserver.service.pay.qbao.bo.UserInfo;
import com.dome.sdkserver.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * 冰趣H5游戏跳支付页处理
 * BqForwardPayServiceImpl
 *
 * @author Zhang ShanMin
 * @date 2016/11/9
 * @time 20:03
 */
@Service("bqForwardPayService")
public class BqForwardPayServiceImpl implements ForwardPayService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PropertiesUtil domainConfig;
    @Resource(name = "userLoginService")
    private UserLoginService userLoginService;

    /**
     * 跳转冰趣支付页
     *
     * @param entity
     * @return
     */
    @Override
    public SdkOauthResult toPayPage(H5GameEntity entity, AppInfoEntity appInfoEntity) throws Exception {
        UserInfo userInfo = null;
        if (H5Game2PlatformEnum.isQbaoChannel(appInfoEntity.getChannelCode()) && entity.getUserId().indexOf("bq_") < 0) {
            //钱宝支付设置冰穹userId
            JSONObject jsonObject = userLoginService.getUserInfoByOpenId(ThirdPartyEnum.QBAO.getThirdId(), String.valueOf(entity.getUserId()));
            if (jsonObject != null && StringUtils.isNotBlank(jsonObject.getString("userId"))) {
                userInfo = new UserInfo();
                userInfo.setUserId(jsonObject.getString("userId"));
                userInfo.setUsername(jsonObject.getString("userName"));
            }
        } else {
            userInfo = userLoginService.getUserByUserId(entity.getUserId()); //获取冰穹用户信息
        }
        //根据userId查询用户名称
        if (userInfo == null) {
            return SdkOauthResult.failed("找不到对应的用户信息");
        }
        //获取H5游戏支付url
        String url = getH5PayUrl(entity, userInfo, entity.getUserId(), appInfoEntity);
        return SdkOauthResult.success(url);
    }

    /**
     * 获取H5游戏支付url
     *
     * @param entity
     * @param userInfo
     * @param deUserId
     */
    private String getH5PayUrl(final H5GameEntity entity, UserInfo userInfo, String deUserId, AppInfoEntity appInfoEntity) {
        StringBuilder sb = new StringBuilder();
        try {
            String userId = PayUtil.orderDesEncrypt(deUserId, domainConfig.getString("ali.pay.webpagegame.des.secretkey"));
            String zoneId = PayUtil.orderDesEncrypt(entity.getZoneId(), domainConfig.getString("ali.pay.webpagegame.des.secretkey"));
            sb.append("/paysdkh5.html").append("?");
            sb.append("userId").append("=").append(userInfo.getUserId());
            sb.append("&").append("buyerId").append("=").append(userId);
            sb.append("&").append("appCode").append("=").append(entity.getAppCode());
            sb.append("&").append("zoneId").append("=").append(zoneId);
            sb.append("&").append("price").append("=").append(entity.getPrice());
            sb.append("&").append("username").append("=").append(URLEncoder.encode(userInfo.getUsername(), "utf-8"));
            sb.append("&").append("channelCode").append("=").append(appInfoEntity.getChannelCode());
            sb.append("&").append("gameName").append("=").append(URLEncoder.encode(appInfoEntity.getAppName(), "utf-8"));
            if (StringUtils.isNotBlank(entity.getItem())) {
                sb.append("&").append("item").append("=").append(URLEncoder.encode(URLDecoder.decode(entity.getItem(), "utf-8"), "utf-8").replace("+", "%20"));
            }
            if (StringUtils.isNotBlank(entity.getZoneName())) {
                sb.append("&").append("zoneName").append("=").append(URLEncoder.encode(URLDecoder.decode(entity.getZoneName(), "utf-8"), "utf-8").replace("+", "%20"));
            }
            if (StringUtils.isNotBlank(entity.getP1())) {
                sb.append("&").append("p1").append("=").append(URLEncoder.encode(entity.getP1(), "utf-8"));
            }
            if (StringUtils.isNotBlank(entity.getP2())) {
                sb.append("&").append("p2").append("=").append(URLEncoder.encode(entity.getP2(), "utf-8"));
            }
            if (StringUtils.isNotBlank(entity.getItemCode())) {
                sb.append("&").append("chargePointCode").append("=").append(URLEncoder.encode(entity.getItemCode(), "utf-8"));
            }
            if (StringUtils.isNotBlank(entity.getOutOrderNo())) {
                sb.append("&").append("outOrderNo").append("=").append(URLEncoder.encode(entity.getOutOrderNo(), "utf-8"));
            }
            if (StringUtils.isNotBlank(entity.getRoleId())) {
                sb.append("&").append("roleId").append("=").append(URLEncoder.encode(entity.getRoleId(), "utf-8"));
            }
            sb.append("&").append("sign").append("=").append(H5GameUtil.generateSign(new HashMap<String, String>() {{
                put("appCode", entity.getAppCode());
                put("price", Double.valueOf(entity.getPrice()).intValue()+"");
            }}, appInfoEntity.getPayKey()));//对H5的支付金额加签
        } catch (Exception e) {
            logger.error("H5游戏拼接支付url异常", e);
        }
        return sb.toString();
    }


}
