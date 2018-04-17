package com.dome.sdkserver.controller.collect;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.NewOpenChannel;
import com.dome.sdkserver.metadata.entity.bq.pay.NewOpenChargePoint;
import com.dome.sdkserver.service.channel.NewOpenChannelService;
import com.dome.sdkserver.service.chargePoint.NewOpenChargePointService;
import com.dome.sdkserver.service.game.NewOpenGameInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
@RequestMapping("/newOpen")
public class NewOpenSynController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private NewOpenGameInfoService newOpenGameInfoService;

    @Autowired
    private NewOpenChargePointService newOpenChargePointService;

    @Autowired
    private NewOpenChannelService newOpenChannelService;

    /**
     * 同步计费点信息
     *
     * @param charge
     * @param request
     * @return
     */
    @RequestMapping("/synCharge")
    @ResponseBody
    public SdkOauthResult synNewOpenCharge(NewOpenChargePoint charge, HttpServletRequest request) {
        try {
            log.info("charge:" + JSONObject.toJSONString(charge));
            //验证参数
            if (!validateChargePointParams(charge)) {
                return SdkOauthResult.failed("缺少参数");
            }
            //验证金额  传入的chargePointAmount单位为元
            if (!validateChargePointAmount(request)) {
                return SdkOauthResult.failed("金额格式不正确");
            }
            BigDecimal chargePointAmount = new BigDecimal(request.getParameter("chargePointAmount"));
            charge.setChargePointAmount(chargePointAmount);
            //同步计费点数据
            return newOpenChargePointService.synNewOpenChargePoint(charge)
                    ? SdkOauthResult.success() : SdkOauthResult.failed("新开放平台同步计费点至SDK失败");
        } catch (Exception e) {
            log.error("新开放平台同步计费点至SDK错误.", e);
            return SdkOauthResult.failed("新开放平台同步计费点至SDK错误");
        }
    }

    private boolean validateChargePointAmount(HttpServletRequest request) {
        String chagePointAmount = request.getParameter("chargePointAmount");
        if (chagePointAmount.matches("^\\d{1,12}$")) {
            //整数
            return true;
        } else if (chagePointAmount.matches("\\d{1,12}\\.\\d{1,2}$")) {
            //小数
            return true;
        } else {
            return false;
        }
    }

    private boolean validateChargePointParams(NewOpenChargePoint charge) {
        if (charge == null) {
            return false;
        }
        if (StringUtils.isBlank(charge.getChargePointCode())
                || StringUtils.isBlank(charge.getChargePointName())
                || charge.getChargePointAmount() == null
                || StringUtils.isBlank(charge.getAppCode())
                || StringUtils.isBlank(charge.getDesc())
                || charge.getStatus() == null) {
            return false;
        }
        return true;
    }


    /**
     * 同步新开放平台手游,页游,H5信息
     *
     * @return
     */
    @RequestMapping("/synAppInfo")
    @ResponseBody
    public SdkOauthResult synNewOpenAppInfo(AppInfoEntity entity, HttpServletRequest request) {//AppInfoEntity entity,
        try {
            log.info("params:" + JSONObject.toJSONString(request.getParameterMap()));
            //验证参数
            boolean pass = validateGameInfoParams(entity);
            if (!pass) {
                return SdkOauthResult.failed("缺少参数");
            }
            return newOpenGameInfoService.synNewOpenAppInfo(entity)
                    ? SdkOauthResult.success() : SdkOauthResult.failed("新开放平台同步手游/页游/H5应用信息至SDK失败");
        } catch (Exception e) {
            log.error("新开放平台同步手游/页游/H5应用信息至SDK错误", e);
            return SdkOauthResult.failed("新开放平台同步手游/页游/H5应用信息至SDK错误");
        }
    }

    private boolean validateGameInfoParams(AppInfoEntity entity) {
        if (entity == null) {
            return false;
        }
        //基础验证
        if (!validateGameInfoBaseParams(entity)) {
            return false;
        }
//        //回调url验证
        if (!validateGameInfoCallBackUrl(entity)) {
            return false;
        }
        //应用key验证
        if (!validateGameInfoKey(entity)) {
            return false;
        }
        return true;
    }

    private boolean validateGameInfoKey(AppInfoEntity entity) {
        String appCode = entity.getAppCode();
        if (appCode.startsWith("D") || appCode.startsWith("AR") || appCode.startsWith("MV") || appCode.startsWith("PV")) { //手游,移动端VR,PC端VR
            if (StringUtils.isBlank(entity.getOutPublicRsaKey())
                    || StringUtils.isBlank(entity.getOutPrivateRsaKey())) {
                return false;
            }
        } else if (appCode.startsWith("Y") || appCode.startsWith("H")) { //页游或H5
            if (StringUtils.isBlank(entity.getAppKey())
                    || StringUtils.isBlank(entity.getLoginKey())
                    || StringUtils.isBlank(entity.getPayKey())) {
                return false;
            }
        }
        return true;
    }

    private boolean validateGameInfoCallBackUrl(AppInfoEntity entity) {
        String appCode = entity.getAppCode();
        if((appCode.startsWith("MV") || appCode.startsWith("PV"))){
            if(StringUtils.isBlank(entity.getLoginCallBackUrl())){
                return false;
            }
        }else {
            //手游/页游/h5
            if (StringUtils.isBlank(entity.getPayNotifyUrl())) {
                return false;
            }
            //手游url 验证
            if ((appCode.startsWith("D") || appCode.startsWith("AR")) && StringUtils.isBlank(entity.getLoginCallBackUrl())) {// 手游
                return false;
            } else if ((appCode.startsWith("H") || appCode.startsWith("Y"))
                    && (StringUtils.isBlank(entity.getGameUrl()))) {
                return false;
            }
        }
        return true;
    }

    private boolean validateGameInfoBaseParams(AppInfoEntity entity) {
        if (StringUtils.isBlank(entity.getAppCode())
                || StringUtils.isBlank(entity.getAppName())
                || StringUtils.isBlank(entity.getMerchantCode())
                || StringUtils.isBlank(entity.getMerchantName())
                || entity.getStatus() == null) {
            return false;
        }
        return true;
    }


    @RequestMapping("/synChannel")
    @ResponseBody
    public SdkOauthResult synNewOpenChannel(NewOpenChannel channel, HttpServletRequest request) {
        try {
            //验证参数
            if (!validateChannelParams(channel)) {
                return SdkOauthResult.failed("缺少参数");
            }
            return newOpenChannelService.synNewOpenChannel(channel) ?
                    SdkOauthResult.success("新开放平台同步渠道信息至SDK成功") : SdkOauthResult.failed("新开放平台同步渠道信息至SDK失败");
        } catch (Exception e) {
            log.error("SDK同步新开放平台渠道数据接口错误", e);
            return SdkOauthResult.failed("SDK同步新开放平台渠道数据接口错误");
        }
    }

    //验证渠道参数
    boolean validateChannelParams(NewOpenChannel channel) {
        if (channel == null) {
            return false;
        }
        if (StringUtils.isBlank(channel.getChannelCode())
                || StringUtils.isBlank(channel.getChannelName())
                || StringUtils.isBlank(channel.getNote())) {
            return false;
        }
        return true;
    }

}
