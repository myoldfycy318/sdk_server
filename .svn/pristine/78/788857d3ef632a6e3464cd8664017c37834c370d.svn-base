package com.dome.sdkserver.controller.collect;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bq.enumeration.GameTypeEunm;
import com.dome.sdkserver.bq.view.Result;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.service.game.GameService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * SynchPayOptions
 * 同步开放平台游戏
 *
 * @author Zhang ShanMin
 * @date 2016/10/17
 */
@Controller
@RequestMapping("/syncGame")
public class SyncGame {

    private static Logger logger = LoggerFactory.getLogger(SyncGame.class);

    @Resource(name = "gameService")
    private GameService gameService;

    /**
     * 同步游戏信息
     *
     * @param appInfoEntity
     * @param request
     * @return
     */
    @RequestMapping(value = "/megerGame", method = RequestMethod.POST)
    @ResponseBody
    public SdkOauthResult megerWebGame(AppInfoEntity appInfoEntity, HttpServletRequest request) {
        try {
            logger.info("同步游戏entity={}", JSON.toJSONString(appInfoEntity));
            SdkOauthResult result = validateGameInfo(appInfoEntity);
            if (!result.isSuccess())
                return result;
            return gameService.megerGameAppInfo(appInfoEntity) ? SdkOauthResult.success() : SdkOauthResult.failed("同步页游信息失败");
        } catch (Exception e) {
            logger.error("同步游戏异常appCode:{}", appInfoEntity.getAppCode(), e);
            return SdkOauthResult.failed(Result.RESPONSE_CODE_PARAM_ERROR, "参数校验失败");
        }
    }


    /**
     * 验证参数
     *
     * @param entity
     * @return
     */
    private SdkOauthResult validateGameInfo(AppInfoEntity entity) {
        if (StringUtils.isBlank(entity.getAppCode()))
            return SdkOauthResult.failed("appCode为空");
        if (null == entity.getMerchantId())
            return SdkOauthResult.failed("merchantId为空");
//        if (StringUtils.isBlank(entity.getPayNotifyUrl()))
//            return SdkOauthResult.failed("PayNotifyUrl为空");
//        if (StringUtils.isBlank(entity.getChannelCode()))
//            return SdkOauthResult.failed("channelCode为空");
        if (null == entity.getStatus())
            return SdkOauthResult.failed("游戏状态不能为空");
        GameTypeEunm gameTypeEunm = GameTypeEunm.getGameType(entity.getAppCode());
        if (gameTypeEunm == null)
            return SdkOauthResult.failed("没有对应的游戏类型");
        switch (gameTypeEunm) {
            case BW_H5:
            case BQ_H5:
                if (StringUtils.isBlank(entity.getGameUrl()))
                    return SdkOauthResult.failed("游戏的url不能为空");
                if (StringUtils.isBlank(entity.getLoginKey()) || StringUtils.isBlank(entity.getPayKey()) || StringUtils.isBlank(entity.getAppCode()))
                    return SdkOauthResult.failed("签名key为空");
                if (StringUtils.isBlank(entity.getGameUrl()))
                    return SdkOauthResult.failed("H5登录url为空");
                break;
            case WEB_GAME:
                break;
            case APP_GAME:
                if (StringUtils.isBlank(entity.getOutPublicRsaKey()))
                    return SdkOauthResult.failed("手游必传参数为空");
                break;
            default:
                return SdkOauthResult.failed("没有对应的游戏类型");
        }
        return SdkOauthResult.success();
    }

}
