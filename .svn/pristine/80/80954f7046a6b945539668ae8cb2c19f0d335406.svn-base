package com.dome.sdkserver.controller.h5sdk;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.utils.BizUtil;
import com.dome.sdkserver.biz.utils.H5GameUtil;
import com.dome.sdkserver.biz.utils.WechatPayUtil;
import com.dome.sdkserver.bq.constants.ThirdPartyEnum;
import com.dome.sdkserver.bq.enumeration.H5Game2PlatformEnum;
import com.dome.sdkserver.bq.util.NumberUtils;
import com.dome.sdkserver.bq.util.PayUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.controller.util.CollectDataLog;
import com.dome.sdkserver.metadata.entity.BiReportLogEntity;
import com.dome.sdkserver.metadata.entity.bq.h5game.H5GameEntity;
import com.dome.sdkserver.metadata.entity.bq.h5game.H5LoginEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.service.channel.NewOpenChannelService;
import com.dome.sdkserver.service.game.GameService;
import com.dome.sdkserver.service.login.UserLoginService;
import com.dome.sdkserver.service.pay.bq.PayManagerService;
import com.dome.sdkserver.service.pay.qbao.bo.ChargePointInfo;
import com.dome.sdkserver.service.rabbitmq.RabbitMqService;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.ServletUtil;
import com.dome.sdkserver.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2016-9-5
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/h5game")
public class H5Conrtroller extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PropertiesUtil domainConfig;
    @Resource(name = "gameService")
    private GameService gameService;
    @Resource(name = "payManagerService")
    private PayManagerService payManagerService;
    @Resource(name = "rabbitMqService")
    private RabbitMqService rabbitMqService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private NewOpenChannelService newOpenChannelService;

    @RequestMapping(value = "/entry")
    public void login(H5LoginEntity loginEntity, HttpServletResponse response, HttpServletRequest request) {
        logger.info("H5游戏登录参数{}", JSONObject.toJSONString(loginEntity));
        StringBuilder sb = new StringBuilder("/msg.html?msg=");
        try {
            if (!validateLoginPara(loginEntity)) {
                ServletUtil.httpRedirect(response, sb.append(URLEncoder.encode("请求参数错误", "utf-8")).toString());
                return;
            }
            AppInfoEntity appInfoEntity = gameService.getAppInfo(loginEntity.getAppCode());
            if (appInfoEntity == null) {
                logger.info("获取不到H5游戏{}应用信息", loginEntity.getAppCode());
                ServletUtil.httpRedirect(response, sb.append(URLEncoder.encode("找不到对应的游戏", "utf-8")).toString());
                return;
            }
            String channelCode = H5Game2PlatformEnum.getChannelByPlatform(loginEntity.getPlatformCode());
            channelCode = extractChannelCode(channelCode); //提取渠道code
            if (StringUtils.isBlank(channelCode)) {
                ServletUtil.httpRedirect(response, sb.append(URLEncoder.encode("渠道类型错误", "utf-8")).toString());
                return;
            }
            Map<String, String> requestMap = new HashMap<>();
            if (StringUtils.isNotBlank(loginEntity.getEncryptUi()) && "true".equals(loginEntity.getEncryptUi())) {
                requestMap.put("userId", PayUtil.orderDesDecrypt(loginEntity.getUserId(), domainConfig.getString("ali.pay.webpagegame.des.secretkey")));
            } else {
                requestMap.put("userId", loginEntity.getUserId());
            }
            requestMap.put("appCode", loginEntity.getAppCode());
            requestMap.put("time", DateUtil.dateToDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            requestMap.put("zoneId", StringUtils.isBlank(loginEntity.getZoneId()) ? "1" : loginEntity.getZoneId());
            requestMap.put("isAdult", StringUtils.isBlank(loginEntity.getIsAdult()) ? "1" : loginEntity.getIsAdult());
            String sign = H5GameUtil.generateSign(requestMap, appInfoEntity.getLoginKey());
            requestMap.put("platformCode", channelCode);
            String auth = H5GameUtil.generateAuth(requestMap);
            String gameUrl = BizUtil.getGameUrl(appInfoEntity, domainConfig.getInt("sdk.notify.environment", "1"));
            String url = gameUrl.indexOf("?") > -1 ? appInfoEntity.getGameUrl() + "&auth=" + auth + "&sign=" + sign : appInfoEntity.getGameUrl() + "?auth=" + auth + "&sign=" + sign;
            logger.info("跳转H5游戏url:{}, 参数:{}", url, JSONObject.toJSONString(requestMap));
            String bqUserId = "";
            if (H5Game2PlatformEnum.isQbaoChannel(channelCode) && loginEntity.getUserId().indexOf("bq_") < 0) {
                //钱宝支付设置冰穹userId
                JSONObject jsonObject = userLoginService.getUserInfoByOpenId(ThirdPartyEnum.QBAO.getThirdId(), String.valueOf(loginEntity.getUserId()));
                if (jsonObject != null && StringUtils.isNotBlank(jsonObject.getString("userId"))) {
                    bqUserId = jsonObject.getString("userId");
                }
            }
            //h5 手游Bi report日志记录
            BiReportLogEntity biEntity = new BiReportLogEntity();
            biEntity.setAppCode(loginEntity.getAppCode());
            biEntity.setUserId(StringUtils.isBlank(bqUserId) ? loginEntity.getUserId() : bqUserId);
            biEntity.setChannelId(channelCode);
            biEntity.setSysType(WebUtils.getSysType(request));
            CollectDataLog.recordLog(biEntity);
            response.sendRedirect(url);
        } catch (Exception e) {
            logger.error("跳H5游戏登录异常", e);
            try {
                ServletUtil.httpRedirect(response, sb.append(URLEncoder.encode("系统异常，请稍后重试！", "utf-8")).toString());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 提取渠道code
     * @param channelCode
     * @return
     */
    private String extractChannelCode(String channelCode) {
        if (StringUtils.isBlank(channelCode))
            return null;
        if (channelCode.indexOf("_") > -1) {
            channelCode = newOpenChannelService.containChanneCode(channelCode.split("_")[0]) ? channelCode : null;
        } else {
            channelCode = newOpenChannelService.containChanneCode(channelCode) ? channelCode : null;
        }
        return channelCode;
    }

    /**
     * 验证登录请求参数
     *
     * @param loginEntity
     * @return
     */
    private boolean validateLoginPara(H5LoginEntity loginEntity) {
        if (StringUtils.isBlank(loginEntity.getUserId()) || StringUtils.isBlank(loginEntity.getAppCode()))
            return false;
        return true;
    }

    /**
     * 打开支付页面
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/payinfo")
    @ResponseBody
    public void payInfo(@ModelAttribute H5GameEntity entity, HttpServletRequest request, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder("/msg.html?msg=");
        try {
            logger.info("h5跳支付页请求参数:" + JSONObject.toJSONString(entity));
            AppInfoEntity appInfoEntity = gameService.getAppInfo(entity.getAppCode());
            if (appInfoEntity == null) {
                logger.info("跳H5支付获取不到游戏{}应用信息", entity.getAppCode());
                ServletUtil.httpRedirect(response, sb.append(URLEncoder.encode("找不到对应的游戏", "utf-8")).toString());
                return;
            }
            if (!NumberUtils.isNumeric(entity.getPrice())) {
                logger.error("参数类型不正确");
                ServletUtil.httpRedirect(response, sb.append(URLEncoder.encode("参数类型不正确", "utf-8")).toString());
                return;
            }
            Map<String, String> fieldMap = new HashMap<String, String>();
            for (Field field : entity.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                fieldMap.put(field.getName(), field.get(entity) + "");
            }
            if (!H5GameUtil.validateSign(fieldMap, appInfoEntity.getPayKey(), entity.getSign())) {
                logger.error("验证签名失败");
                ServletUtil.httpRedirect(response, sb.append(URLEncoder.encode("验证签名失败", "utf-8")).toString());
                return;
            }
            entity.setSysType(WebUtils.getSysType(request));//支付系统类型
            handleH5GameChargePoint(entity);//同步H5游戏支付物品信息到Bi报表(计费点信息)
            entity.setIp(WechatPayUtil.getRemoteHost(request));
            //产生H5游戏支付页连接
            SdkOauthResult result = payManagerService.producePayPageLink(entity, appInfoEntity);
            if (result.isError()) {
                ServletUtil.httpRedirect(response, sb.append(URLEncoder.encode(result.getErrorMsg(), "utf-8")).toString());
                return;
            }
            //结束代码流程
            if (result.isEndProcess()) {
                return;
            }
            //获取H5游戏支付url
            String url = (String) result.getData();
            logger.info("获取H5游戏支付url:{}", url);
            response.sendRedirect(url);
        } catch (Exception e) {
            logger.error("h5游戏跳支付页异常", e);
            try {
                ServletUtil.httpRedirect(response, sb.append(URLEncoder.encode("系统异常，请稍后重试！", "utf-8")).toString());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 同步H5游戏支付物品信息到Bi报表(积分点信息)
     * 2017-9-28 by_zhangshanmin
     *
     * @param entity
     */
    private void handleH5GameChargePoint(H5GameEntity entity) {
        ChargePointInfo chargePointInfo = new ChargePointInfo();
        chargePointInfo.setAppCode(entity.getAppCode());
        chargePointInfo.setChargePointCode(entity.getItemCode());
        chargePointInfo.setChargePointName(entity.getItem());
        chargePointInfo.setChargePointAmount(new BigDecimal(entity.getPrice()));
        rabbitMqService.syncChargePoint(chargePointInfo);
    }

    /**
     * H5社区入口
     *
     * @return
     */
    @RequestMapping(value = "/getH5CommunityUrl")
    @ResponseBody
    public SdkOauthResult getH5CommunityUrl(String bqH5Token) {
        String url = domainConfig.getString("h5.community.url");
        if (StringUtils.isBlank(bqH5Token)) {
            return SdkOauthResult.success(url);
        } else {
            return SdkOauthResult.success(url + "?bqH5Token=" + bqH5Token);
        }

    }


}
