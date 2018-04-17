package com.dome.webgameserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.view.AjaxResult;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**亚瑟神剑
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2016-9-27
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/webgame/yssj")
public class YssjController {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String LY = "bingq";

    public static final String GID = "1";

    public static final String LOGINKEY = "35500ec8c22460e9cfae0a6895f9a898";

    public static final String PAYKEY = "PuKnUAmV4b8WG2Vtb1Pv";

    public static final String OHTHERKEY = "35500ec8c22460e9cfae0a6895f9a898";

    public static final String LOGIN_URL = "http://mltxapi.kuaiqin.com/api/mltx/login";


    public static final String PAY_URL = "http://gamepay.ccjoy.cc/LYPayHandler.ashx";


    public static final String CHECKROLE_URL = "http://mltxapi.kuaiqin.com/api/mltx/roleInfo";


    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult login(String userId, String zoneId, HttpServletRequest request, HttpServletResponse response) {
        // userId = "test002";
        // zoneId = "1";

        String isadult = "1";

        long curTime = System.currentTimeMillis();
        String time = String.valueOf(curTime).substring(0, 10);
        String hash = MD5Util.getMD5String(LY+zoneId+userId +isadult + time  + LOGINKEY);
        log.info("hash的原始值:" + LY+zoneId+userId +isadult + time  + LOGINKEY);
        log.info("hash的md5值:" + hash);

        String param = "platform=" + LY + "&account=" + userId + "&server=" + zoneId + "&time=" + time + "&fm=" + isadult + "&sign=" + hash;
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
        int money=price.intValue();
        int gold = money * 10;

        long curTime = System.currentTimeMillis();
        String time = String.valueOf(curTime).substring(0, 10);

//        System.out.println("sing加密前:" + "pid=" + LY + "&account=" + userId + "&gid="+GID+"&sid=" + zoneId + "&tradeno=" + orderNo+ "&money="+money+ "&point=" + gold  +"&time="+time+ "&key="+PAYKEY);
        String sign = MD5Util.getMD5String("pid=" + LY + "&account=" + userId + "&gid="+GID+"&sid=" + zoneId + "&tradeno=" + orderNo+ "&money="+money+ "&point=" + gold  +"&time="+time+ "&key="+PAYKEY);

//        System.out.println("sing加密后:" + sign);
        String param = "pid=" + LY + "&account=" + userId + "&gid="+GID+"&sid=" + zoneId + "&tradeno=" + orderNo+ "&money="+money+ "&point=" + gold  +"&time="+time+"&sign=" + sign;


        String url = PAY_URL + "?" + param;
//        System.out.println("请求url:" + url);
        log.info("请求url:" + url);

        String result = ApiConnector.get(url, null);
        if (result != null && !result.equals("")) {
            if (result.equals("1")) {
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

        long curTime = System.currentTimeMillis();
        String time = String.valueOf(curTime).substring(0, 10);

        String sign = MD5Util.getMD5String(LY + zoneId  + userId  +time+ OHTHERKEY);
        String param = "platform=" + LY + "&server=" + zoneId + "&account=" + userId +"&time="+time +"&sign=" + sign;

        String url = CHECKROLE_URL + "?" + param;
        log.info("请求url:" + url);
//        System.out.println(url);
        String result = ApiConnector.get(url, null);
//        System.out.println("------result:" + result);
        if (result != null && !result.equals("")) {
                JSONObject jsonResult = JSONObject.parseObject(result);
                String data = jsonResult.getString("error");
                return data.equals("0") ? AjaxResult.success(true) : AjaxResult.success(false);
        } else {
            log.error("查询角色无响应,用户帐号:" + userId);
            return AjaxResult.failed("查询角色无响应");
        }

    }
}
