package com.dome.webgameserver.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.view.AjaxResult;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 傲视沙城
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2016-9-18
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/webgame/assc")
public class AsscController {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String LY = "4012";

    public static final String LOGINKEY = "!c)]eDf689)^)SB";

    public static final String PAYKEY = "n58b?%1O=Dl,+Y!";

    public static final String OHTHERKEY = ">)g2lB1+^<[8iWK";

    public static final String LOGIN_URL = "http://ls2.assc.yileweb.com/demo4012/indexboli.php";


    public static final String PAY_URL = "http://ls2.assc.yileweb.com/gmt/kvmphp4012/api/billing.php";


    public static final String CHECKROLE_URL = "http://ls2.assc.yileweb.com/gmt/kvmphp4012/api/PlayerInfo.php";


    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult login(String userId, String zoneId, HttpServletRequest request, HttpServletResponse response) {
        // userId = "test002";
        // zoneId = "1";

        String isadult = "1";

        long curTime = System.currentTimeMillis();
        String time = String.valueOf(curTime).substring(0, 10);
        String hash = MD5Util.getMD5String(userId + "_" + time + "_" + LOGINKEY);
        log.info("hash的原始值:" + userId + "_" + time + "_" + LOGINKEY);
        log.info("hash的md5值:" + hash);

        String param = "platid=" + LY + "&user=" + userId + "&srvid=" + zoneId + "&time=" + time + "&non_kid=" + isadult + "&is_miniclient=1&hash=" + hash;
        String url = LOGIN_URL + "?" + param;

        return AjaxResult.success(url);

    }

    @RequestMapping("/pay")
    @ResponseBody
    public AjaxResult pay(String userId, String zoneId, String orderNo, Double price, HttpServletRequest request, HttpServletResponse response) {
        log.info("请求参数:userId:" + userId + ",zoneId:" + zoneId + ",orderNo:"
                + orderNo + ",price:" + price);

        // userId = "test002";
        // zoneId = "1";
        // orderNo="11111";

        int gold = price.intValue() * 100;
        String sign = MD5Util.getMD5String(userId + "_" + gold + "_" + orderNo + "_" + zoneId + "_" + PAYKEY);
        String param = "platid=" + LY + "&srvid=" + zoneId + "&user=" + userId + "&order=" + orderNo + "&gold=" + gold + "&sign=" + sign;

        String url = PAY_URL + "?" + param;
        log.info("请求url:" + url);

        String result = ApiConnector.get(url, null);
        if (result != null && !result.equals("")) {
            result=result.trim();
            if (result.equals("1") || result.equals("6")) {
                return AjaxResult.success("充值成功");
            } else {
                return AjaxResult.failed("充值失败!错误码:" + result);
            }
            // 信息入库
        } else {
            log.error("支付无响应,订单号:" + orderNo + ",用户帐号:" + userId);
            return AjaxResult.failed("支付无响应");
        }
    }

    @RequestMapping("/checkRole")
    @ResponseBody
    public AjaxResult checkRole(String userId, String zoneId, HttpServletRequest request, HttpServletResponse response) {

        String sign = MD5Util.getMD5String(LY + "_" + zoneId + "_" + userId + "_" + OHTHERKEY);
        String param = "platid=" + LY + "&srvid=" + zoneId + "&uid=" + userId + "&sign=" + sign;

        String url = CHECKROLE_URL + "?" + param;
        log.info("请求url:" + url);

        String result = ApiConnector.get(url, null);
        System.out.println("------result:" + result);
        if (result != null && !result.equals("")) {
            result=result.trim();
            if (result.length() > 5) {
                JSONArray jsonResult = JSONObject.parseArray(result);
                String name = jsonResult.getJSONObject(0).getString("name");
                System.out.println("------name:" + name);
                return name.equals("") ? AjaxResult.success(false) : AjaxResult.success(true);
            } else {
                return AjaxResult.success(false);
            }
        } else {
            log.error("查询角色无响应,用户帐号:" + userId);
            return AjaxResult.failed("查询角色无响应");
        }

    }
}
