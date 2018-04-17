package com.dome.webgameserver.controller;

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

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2016-9-6
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/webgame/yfs")
public class YfsController {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String LY = "qbao";

    public static final String LOGINKEY = "98qbao230f2b513f9442d5bfcb72974da15657";

    public static final String PAYKEY = "98qbaoab0500ad6d3343ec967ce6167437086b";

    public static final String OHTHERKEY="98qbao230f2b513f9442d5bfcb72974da15657";

    	public static final String LOGIN_URL = "http://gc.yfs.kongzhong.com/yfsly/loginlygameurl/{ly}/{key}";
//    public static final String LOGIN_URL = "http://118.242.26.158:80/yfsly/loginlygameurl/{ly}/{key}";

	public static final String PAY_URL = "http://gc.yfs.kongzhong.com/yfsly/sendItemYb/{ly}/{key}";

//    public static final String PAY_URL = "http://118.242.26.158:80/yfsly/sendItemYb/{ly}/{key}";

	public static final String CHECKROLE_URL = "http://gc.yfs.kongzhong.com/yfsly/userInfo/{ly}/{key}";

//    public static final String CHECKROLE_URL = "http://118.242.26.158:80/yfsly/userInfo/{ly}/{key}";


    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult login(String userId, String zoneId, HttpServletRequest request, HttpServletResponse response) {
        // userId = "test002";
        // zoneId = "1";

        String isadult = "1";

        Map<String, String> loginParam = new HashMap<String, String>();
        loginParam.put("ly", LY);
        loginParam.put("uid", userId);
        loginParam.put("serverId", zoneId);
        String ptime = DateUtil.dateToDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
        loginParam.put("ptime", ptime);
        loginParam.put("cm", isadult);

        String signBase = LY + userId + zoneId +ptime  + isadult + LOGINKEY;
        // log.info("原始sign:" + signBase);

        String md5Sgin = MD5Util.getMD5String(signBase);
        // log.info("MD5 sign:" + md5Sgin);
        loginParam.put("sign", md5Sgin);

        String jsonParam = JSONObject.toJSONString(loginParam);
        // log.info("jsonParam:" + jsonParam);

        String key = new String(Base64.encode(jsonParam.getBytes()));
        // log.info("key:" + key);

        String url = LOGIN_URL.replace("{ly}", LY);
        url = url.replace("{key}", key);

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
        // 是否是调试模式 0=是 1=不是(正式环境改为1)
        String debug = "1";

        // 充值比例
        int rmbToXz = 10;
        DecimalFormat df = new DecimalFormat("#.00");
        String priceStr = df.format(price);
        int num = rmbToXz * price.intValue();

        Map<String, Object> loginParam = new HashMap<String, Object>();
        loginParam.put("ly", LY);
        loginParam.put("uid", userId);
        loginParam.put("serverId", zoneId);
        String ptime = DateUtil.dateToDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
        loginParam.put("ptime", ptime);
        loginParam.put("orderno", orderNo);
        loginParam.put("price", price);
        loginParam.put("num", String.valueOf(num));
        loginParam.put("debug", debug);

        String signBase = LY + userId + zoneId + ptime+orderNo+priceStr + num  + PAYKEY;
        log.info("原始sign:" + signBase);

        String md5Sgin = MD5Util.getMD5String(signBase);
        log.info("MD5 sign:" + md5Sgin);


        loginParam.put("sign", md5Sgin);

        String jsonParam = JSONObject.toJSONString(loginParam);
        log.info("jsonParam:" + jsonParam);

        String key = new String(Base64.encode(jsonParam.getBytes()));
        log.info("key:" + key);

        String url = PAY_URL.replace("{ly}", LY);
        url = url.replace("{key}", key);
        log.info("请求url:" + url);

        String result = ApiConnector.post(url, null);
        if (result != null && !result.equals("")) {
            JSONObject jsonResult = JSONObject.parseObject(result);
            String state = jsonResult.getString("state");
            String info = jsonResult.getString("info");
            if (state.equals("true")) {
                return AjaxResult.success(info);
            } else {
                return AjaxResult.failed(info);
            }
            // 信息入库
        } else

        {
            log.error("支付无响应,订单号:" + orderNo + ",用户帐号:" + userId);
            return AjaxResult.failed("支付无响应");
        }

    }

    @RequestMapping("/checkRole")
    @ResponseBody
    public AjaxResult checkRole(String userId, String zoneId, HttpServletRequest request, HttpServletResponse response) {


        Map<String, Object> loginParam = new HashMap<String, Object>();
        loginParam.put("ly", LY);
        loginParam.put("uid", userId);
        loginParam.put("serverId", zoneId);
        String ptime = DateUtil.dateToDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
        loginParam.put("ptime", ptime);

        String signBase = LY + userId + zoneId + ptime + OHTHERKEY;
        // log.info("原始sign:" + signBase);

        String md5Sgin = MD5Util.getMD5String(signBase);
        // log.info("MD5 sign:" + md5Sgin);
        loginParam.put("sign", md5Sgin);

        String jsonParam = JSONObject.toJSONString(loginParam);
        // log.info("jsonParam:" + jsonParam);

        String key = new String(Base64.encode(jsonParam.getBytes()));
        // log.info("key:" + key);

        String url = CHECKROLE_URL.replace("{ly}", LY);
        url = url.replace("{key}", key);
        log.info("请求url:" + url);

        String result = ApiConnector.post(url, null);

        if (result != null && !result.equals("")) {
            JSONObject jsonResult = JSONObject.parseObject(result);
            String resultCheck = jsonResult.getString("state");
            return resultCheck.equals("true") ? AjaxResult.success(true) : AjaxResult.success(false);
        } else {
            log.error("查询角色无响应,用户帐号:" + userId);
            return AjaxResult.failed("查询角色无响应");
        }
    }

}
