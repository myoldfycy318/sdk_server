package com.dome.sdkserver.controller.sign.qbao;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.metadata.entity.sign.SignEntity;
import com.dome.sdkserver.service.login.ThridRequestService;
import com.dome.sdkserver.service.pay.qbao.bo.UserInfo;
import com.dome.sdkserver.service.sign.SignService;
import com.dome.sdkserver.util.MD5;
import com.dome.sdkserver.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuekuan on 2017/3/29.
 */
@Controller
@RequestMapping("sign")
public class SignController extends BaseController {
    @Autowired
    private SignService signService;
    @Autowired
    protected PropertiesUtil domainConfig;
    @Autowired
    private ThridRequestService thridRequestService;
    @Autowired
    protected AmqpTemplate signTemplate;

    /**
     * 玩家玩游戏满30分钟通知我方
     *
     * @param request
     * @param signEntity
     * @return
     */
    @RequestMapping("/callResult")
    @ResponseBody
    public SdkOauthResult sign(HttpServletRequest request, SignEntity signEntity) {
        String userId = signEntity.getUserId();
        try {
            if (StringUtils.isBlank(userId))
                return SdkOauthResult.failed("四月签到分宝石与爬塔分用户ID为空");
           /* SdkOauthResult sdkOauthResult = checkUser(userId);
            if (!sdkOauthResult.isSuccess())
                return SdkOauthResult.failed("userId=" + userId + "：四月签到分宝石与爬塔该用户不存在");
            String overTime = request.getParameter("finshTime");
            if (StringUtils.isBlank(overTime))
                return SdkOauthResult.failed("四月签到分宝石与爬塔玩家完成任务的时间点为空");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = sdf.parse(overTime);
            signEntity.setFinshTimes(time);
            //验证签名
            SdkOauthResult result = validSignResult(signEntity);
            if (!result.isSuccess()) {
                log.info("四月签到分宝石与爬塔验签失败");
                return result;
            }
            signTemplate.convertAndSend("h5_game_pata_key", signEntity); //异步通知使用rabbitmq  */    //该接口只对四月签到，现屏蔽
        } catch (Exception a) {
            log.error("四月签到分宝石与爬塔系统异常", a);
            return SdkOauthResult.failed();
        }
        return SdkOauthResult.success();
    }

    /**
     * 校验用户是否存在
     *
     * @param userId
     * @return
     */
    private SdkOauthResult checkUser(String userId) {
        //判断是否存在该用户
        UserInfo userInfo = null;
        for (int i = 0; i < 3; i++) { //尝试3次去获取用户信息，
            if (null == userInfo) {
                userInfo = thridRequestService.loadUserInfo(Long.parseLong(userId.trim()));
            }
        }
        if (null == userInfo) {
            log.error("四月签到分宝石与爬塔用户中心返回结果：不存在该用户,用户ID：{}", userId);
            return SdkOauthResult.failed("userId=" + userId + "：该用户不存在");
        }
        return SdkOauthResult.success();
    }

    /**
     * 签名验证
     *
     * @param signEntity
     * @return
     */
    private SdkOauthResult validSignResult(SignEntity signEntity) {
        SdkOauthResult result;
        Map<String, String> map = new HashMap<>();
        Date date = signEntity.getFinshTimes();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        map.put("userId", signEntity.getUserId());
        map.put("finshTime", time);
        String str = MapUtil.createLinkString(map) + "&md5key=" + domainConfig.getString("sign.score.md5.key");
        String md5 = MD5.md5Encode(str);
        String signCode = signEntity.getSignCode();
        if (StringUtils.isBlank(signCode))
            return SdkOauthResult.failed("传入的MD5的值为空");
        result = signCode.equals(md5) ? SdkOauthResult.success("验证签名成功") : SdkOauthResult.failed("验签失败");
        return result;
    }

    /**
     * 根据玩家id查询玩家今日是否玩游戏超过半小时
     *
     * @param signEntity
     * @return
     */
    @RequestMapping("playGameResponse")
    @ResponseBody
    public void playGameResponse(HttpServletResponse response, SignEntity signEntity) {
        String userId = signEntity.getUserId();
        if (StringUtils.isBlank(userId)) {
            log.info("四月签到分宝石与爬塔用户Id为空:{}", userId);
            return;
        }
        signEntity.setNotifyDate(DateUtils.getCurDateFormatStr(DateUtils.YYYYMMDD));
        signEntity.setCurMonth(DateUtils.getCurDateFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT));
        String result = JSON.toJSONString(signService.playGameResponse(signEntity));
        log.info(">>>>>>>>>>>>>>四月签到分宝石与爬塔响应结果：{}", result);
        try {
            PrintWriter pw = response.getWriter();
            pw.write(result);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            log.error("四月签到分宝石与爬塔通知集团接口异常", e);
        }
    }

}
