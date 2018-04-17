package com.dome.webgameserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.view.AjaxResult;
import com.dome.sdkserver.security.ras.Base64;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

/**
 * 盗墓笔记
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2016-10-12
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/webgame/dmbj")
public class DmbjController {

    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String LY = "2362";

    public static final String GAMEID = "146";

    public static final String LOGINKEY = "jHROkXt12DyaqT2G";

    public static final String PAYKEY = "jHROkXt12DyaqT2G";

    public static final String OHTHERKEY = "jHROkXt12DyaqT2G";

    public static final String LOGIN_URL = "http://up.youzu.com/newAPI/Api/login?auth=${auth}&verify=${verify}";


    public static final String PAY_URL = "http://up.youzu.com/newAPI/commonII/charge?auth=${auth}&verify=${verify}";


    public static final String CHECKROLE_URL = "http://up.youzu.com/newAPI/commonII/roleverify?auth=${auth}&verify=${verify}";


    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult login(String userId, String zoneId) {
        // userId = "test002";
        // zoneId = "1";

        String isadult = "1";

        long curTime = System.currentTimeMillis();
        String time = String.valueOf(curTime).substring(0, 10);


        String param = "op_id=" + LY + "&sid=" + zoneId + "&game_id=" + GAMEID + "&account=" + userId + "&adult_flag=" + isadult+ "&time=" + time ;
        System.out.println("明文参数:" + param);
        String  base64Param = null;
        try {
            base64Param = new String(Base64.encodeBase64(param.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("base64值:" + base64Param);
        String sign = MD5Util.getMD5String(base64Param + LOGINKEY);

        System.out.println("md5原始值:" + base64Param + LOGINKEY);
        System.out.println("md5值:" + sign);

        String url = LOGIN_URL.replace("${auth}", base64Param).replace("${verify}", sign);
        System.out.println("请求地址:" + url);
        return AjaxResult.success(url);

    }


    @RequestMapping("/pay")
    @ResponseBody
    public AjaxResult pay(String userId, String zoneId, String orderNo, Double price) {
        log.info("请求参数:userId:" + userId + ",zoneId:" + zoneId + ",orderNo:"
                + orderNo + ",price:" + price);

        // userId = "test002";
        // zoneId = "1";
        // orderNo="11111";
        String gid = "1";
        int gold = price.intValue() * 10;

        long curTime = System.currentTimeMillis();
        String time = String.valueOf(curTime).substring(0, 10);


        String param = "op_id=" + LY + "&game_id=" + GAMEID + "&account=" + userId + "&gid=" + gid + "&sid=" + zoneId + "&order_id=" + orderNo + "&game_money=" + gold + "&u_money=" + price + "&time=" + time;
        System.out.println("明文参数:" + param);
        String  base64Param = null;
        try {
            base64Param = new String(Base64.encodeBase64(param.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sign = MD5Util.getMD5String(base64Param + PAYKEY);

        System.out.println("原始值:" + base64Param + PAYKEY);
        System.out.println("md5值:" + sign);

        String url = PAY_URL.replace("${auth}", base64Param).replace("${verify}", sign);
        System.out.println("请求地址:" + url);
        log.info("请求url:" + url);

        String result = ApiConnector.get(url, null);
        if (result != null && !result.equals("")) {
            JSONObject jsonResult = JSONObject.parseObject(result);
            if (jsonResult.getString("status").equals("0")) {
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
    public AjaxResult checkRole(String userId, String zoneId) {

        long curTime = System.currentTimeMillis();
        String time = String.valueOf(curTime).substring(0, 10);


        String param = "op_id=" + LY + "&sid=" + zoneId + "&game_id=" + GAMEID + "&account=" + userId + "&time" + time;
        System.out.println("明文参数:" + param);
        String  base64Param = null;
        try {
            base64Param = new String(Base64.encodeBase64(param.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sign = MD5Util.getMD5String(base64Param + OHTHERKEY);

        System.out.println("原始值:" + base64Param + OHTHERKEY);
        System.out.println("md5值:" + sign);

        String url = CHECKROLE_URL.replace("${auth}", base64Param).replace("${verify}", sign);
        System.out.println("请求地址:" + url);
        log.info("请求url:" + url);

        String result = ApiConnector.get(url, null);
        System.out.println("------result:" + result);
        if (result != null && !result.equals("")) {
            JSONObject jsonResult = JSONObject.parseObject(result);
            String data = jsonResult.getString("status");
            return data.equals("0") ? AjaxResult.success(true) : AjaxResult.success(false);
        } else {
            log.error("查询角色无响应,用户帐号:" + userId);
            return AjaxResult.failed("查询角色无响应");
        }

    }

}
