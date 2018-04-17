package com.dome.sdkserver.controller.webgame;

import com.dome.sdkserver.biz.utils.H5GameUtil;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.controller.util.CollectDataLog;
import com.dome.sdkserver.metadata.entity.BiReportLogEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.metadata.entity.bq.webgame.WebGameLoginEntity;
import com.dome.sdkserver.service.channel.NewOpenChannelService;
import com.dome.sdkserver.service.game.GameService;
import com.dome.sdkserver.util.MD5;
import com.dome.sdkserver.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by heyajun on 2017/8/1.
 */
@Controller
@RequestMapping("/webgame")
public class WebGameLoginController extends BaseController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource(name = "gameService")
    private GameService gameService;
    @Autowired
    private NewOpenChannelService newOpenChannelService;

    //拼接游戏登录url不做跳转
    @RequestMapping("/getLoginUrl")
    @ResponseBody
    public SdkOauthResult login(WebGameLoginEntity webGameLoginEntity, HttpServletRequest request) {
        String url = null;
        try {
            if (StringUtils.isBlank(webGameLoginEntity.getAppCode()) || StringUtils.isBlank(webGameLoginEntity.getAreaServerNumber()) //区服id
                    || StringUtils.isBlank(webGameLoginEntity.getUserId()) || StringUtils.isBlank(webGameLoginEntity.getChannelCode())) {
                return SdkOauthResult.failed("缺少参数");
            }
            //没有传isAdult参数时则默认为成年
            webGameLoginEntity.setIsAdult(1);
            if (!newOpenChannelService.containChanneCode(webGameLoginEntity.getChannelCode())) {
                return SdkOauthResult.failed("未知渠道类型");
            }
            //查询根据AppCode查询信息 优先从redis中查询
            AppInfoEntity webGame = gameService.getAppInfo(webGameLoginEntity.getAppCode());
            if (webGame == null) {
                return SdkOauthResult.failed("该页游不存在");
            }
            //页游登录地址为gameUrl
            String loginUrl = webGame.getGameUrl();
            String loginKey = webGame.getLoginKey();
            Map<String, String> baseParams = new HashMap<String, String>();
            baseParams.put("appCode", webGameLoginEntity.getAppCode());
            //页游平台配置的区服
            baseParams.put("zoneId", webGameLoginEntity.getAreaServerNumber());
            //冰穹uc的userId
            baseParams.put("userId", webGameLoginEntity.getUserId());
            baseParams.put("time", DateUtils.getCurDateFormatStr(DateUtils.DEFAULT_DATE_TIME_FORMAT));
            baseParams.put("channelCode", webGameLoginEntity.getChannelCode());
            baseParams.put("isAdult", String.valueOf(webGameLoginEntity.getIsAdult()));
            String base = H5GameUtil.createLinkString(baseParams);
            String baseSign = base + "&" + loginKey;
            String sign = MD5.md5Encode(baseSign);
            String auth = H5GameUtil.generateAuth(baseParams);
            //返回游戏登录url及参数
            url = loginUrl + "?" + "auth=" + auth + "&sign=" + sign;
            //h5 手游Bi report日志记录
            BiReportLogEntity biEntity = new BiReportLogEntity();
            biEntity.setAppCode(webGameLoginEntity.getAppCode());
            biEntity.setUserId(webGameLoginEntity.getUserId());
            biEntity.setChannelId(webGameLoginEntity.getChannelCode());
            biEntity.setSysType(WebUtils.getSysType(request));
            CollectDataLog.recordLog(biEntity);

            return SdkOauthResult.success(url);
        } catch (Exception e) {
            log.error("拼装页游登录url错误", e);
            return SdkOauthResult.failed("拼装页游登录url错误");
        }
    }

}
