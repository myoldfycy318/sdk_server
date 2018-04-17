package com.dome.sdkserver.controller.collect;

import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.service.game.GameService;
import com.dome.sdkserver.service.game.NewOpenGameInfoService;
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

/**
 * 同步OGP平台的 gameId,gameKey字段
 * Created by heyajun on 2017/8/8.
 */
@Controller
@RequestMapping("/synOgp")
public class SynOGP {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource(name = "gameService")
    private GameService gameService;

    @Autowired
    private NewOpenGameInfoService newOpenGameInfoService;

    @RequestMapping(value = "/gameParams", method = RequestMethod.POST)
    @ResponseBody
    public SdkOauthResult snyGame(AppInfoEntity appInfoEntity, HttpServletRequest request) {
        try {
            if (StringUtils.isBlank(appInfoEntity.getAppCode())
                    || StringUtils.isBlank(appInfoEntity.getOgpGameId())
                    || StringUtils.isBlank(appInfoEntity.getOgpGameKey())) {
                return SdkOauthResult.failed("有必传参数为空");
            }
            //appCode不存在时依然保存状态为下架
            AppInfoEntity app = gameService.getAppInfo(appInfoEntity.getAppCode());
            if (app == null) {
                appInfoEntity.setAppName("unknow");
                appInfoEntity.setMerchantId(-1);
                appInfoEntity.setStatus(0);//保存为下架
            }else {
                app.setOgpGameId(appInfoEntity.getOgpGameId());
                app.setOgpGameKey(appInfoEntity.getOgpGameKey());
            }
            //兼容新老开放平台.
            return newOpenGameInfoService.syncOgpParams(app) ? SdkOauthResult.success() : SdkOauthResult.failed("同步OGP信息失败");
        } catch (Exception e) {
            log.error("同步OGP信息错误", e);
            return SdkOauthResult.failed("同步OGP信息错误");
        }

    }
}
