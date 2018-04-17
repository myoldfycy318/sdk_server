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

/**    灵符大师 (此游戏无文档)
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2016-9-28
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/webgame/lfds")
public class LfdsController {

    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String LY = "qbao";

    public static final String LOGINKEY = "_i@!dkqbao#w";

    public static final String PAYKEY = "_i@!dkqbao#w";

//    public static final String OHTHERKEY = ">)g2lB1+^<[8iWK";

    public static final String LOGIN_URL = "http://{zoneId}/index.html";


    public static final String PAY_URL = "http://{zoneId}:9090/server/pay.action";


//    public static final String CHECKROLE_URL = "http://ls2.assc.yileweb.com/gmt/kvmphp4012/api/PlayerInfo.php";


    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult login(String userId, String zoneId, HttpServletRequest request, HttpServletResponse response) {
        // userId = "test002";
        // zoneId = "1";

        String isadult = "1";

        long curTime = System.currentTimeMillis();
        String hash = MD5Util.getMD5String(LY+"_"+userId  +"_"+ curTime  + LOGINKEY);
//        log.info("hash的原始值:" + LY+"_"+userId  +"_"+ curTime  + LOGINKEY);
//        System.out.println("hash的原始值:" + LY + "_" + userId + "_" + curTime + LOGINKEY);
//        log.info("hash的md5值:" + hash);
//        System.out.println("hash的md5值:" + hash);
        String param = "pid=" + LY + "&user=" + userId + "&time=" + curTime + "&adult=" + isadult + "&sig=" + hash;
        String url = LOGIN_URL + "?" + param;
        url=url.replace("{zoneId}",zoneId);
//        System.out.println("请求参数::" + param);
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
        String sign = MD5Util.getMD5String(LY+"_"+userId + "_" + gold + "_" + orderNo + PAYKEY);
        String param = "pid=" + LY + "&user=" + userId + "&order=" + orderNo + "&gold=" + gold + "&sig=" + sign;

//        System.out.println("加密之前:" + LY+"_"+userId + "_" + gold + "_" + orderNo  + PAYKEY);
//        System.out.println("md5之后:" + sign);
//        System.out.println("请求参数::" + param);

        String url = PAY_URL + "?" + param;
        url=url.replace("{zoneId}",zoneId);
//        log.info("请求url:" + url);

        String result = ApiConnector.get(url, null);
        if (result != null && !result.equals("")) {
            JSONObject o= JSONObject.parseObject(result);
           int r= o.getInteger("ret");
            String msg=o.getString("msg");
            if (r==1 ) {
                return AjaxResult.success("充值成功");
            } else {
                return AjaxResult.failed("充值失败!错误码:" + r+"|错误提示:"+msg);
            }
            // 信息入库
        } else {
            log.error("支付无响应,订单号:" + orderNo + ",用户帐号:" + userId);
            return AjaxResult.failed("支付无响应");
        }
    }

//    @RequestMapping("/checkRole")
//    @ResponseBody
//    public AjaxResult checkRole(String userId, String zoneId, HttpServletRequest request, HttpServletResponse response) {
//
//        String sign = MD5Util.getMD5String(LY + "_" + zoneId + "_" + userId + "_" + OHTHERKEY);
//        String param = "platid=" + LY + "&srvid=" + zoneId + "&uid=" + userId + "&sign=" + sign;
//
//        String url = CHECKROLE_URL + "?" + param;
//        log.info("请求url:" + url);
//
//        String result = ApiConnector.get(url, null);
//        System.out.println("------result:" + result);
//        if (result != null && !result.equals("")) {
//            result=result.trim();
//            if (result.length() > 5) {
//                JSONArray jsonResult = JSONObject.parseArray(result);
//                String name = jsonResult.getJSONObject(0).getString("name");
//                System.out.println("------name:" + name);
//                return name.equals("") ? AjaxResult.success(false) : AjaxResult.success(true);
//            } else {
//                return AjaxResult.success(false);
//            }
//        } else {
//            log.error("查询角色无响应,用户帐号:" + userId);
//            return AjaxResult.failed("查询角色无响应");
//        }
//
//    }
}
