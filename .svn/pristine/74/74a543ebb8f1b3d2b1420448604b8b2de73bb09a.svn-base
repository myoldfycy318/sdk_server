package com.dome.webgameserver.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.view.AjaxResult;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.MD5Util;

/**
 * 战甲
 */
@Controller
@RequestMapping("/webgame/zj")
public class ZjController {
	public final Logger log = LoggerFactory.getLogger(this.getClass());
	
    //测试平台码
//    public static final String PLATNAME = "zjtest";
    public static final String PLATNAME = "zjqbao";
    
    public static final String LOGINKEY = "10d22b2f8e4d77add60ad04c8a345cc1";
//    public static final String LOGINKEY = "362c860ec889e7c00ae8359a955ecfcf";
    
    public static final String PAYKEY = "6c0113ce3cd7a4d7fa55598a1594d12d";
//    public static final String PAYKEY = "a3dfbf6991f3e35a3dee155db566a5d9";

    public static final String OHTHERKEY = "de3be256596279f5470c08643529c0ac";
//    public static final String OHTHERKEY = "d7d88ce18e6cd79aa14d29989af86245";

    public static final String LOGIN_URL = "http://m.zwkj7.com/game/zhanjia/index.php";

    public static final String PAY_URL = "http://m.zwkj7.com/game/zhanjia/pay.php";

    public static final String CHECKROLE_URL = "http://m.zwkj7.com/game/zhanjia/getplayer.php";

    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult login(String userId, String zoneId, HttpServletRequest request, HttpServletResponse response) {

        long curTime = System.currentTimeMillis();
        
        StringBuffer sb = new StringBuffer();
        sb.append(curTime)
        .append("account=").append(userId)
        .append("&serverid=").append(zoneId)
        .append("&platname=").append(PLATNAME)
        .append(LOGINKEY);
        
        String hash = MD5Util.getMD5String(sb.toString());
        log.info("Zj_login的加密前:" + sb.toString());
        log.info("Zj_login的加密后:" + hash);

        String param = "account=" + userId + "&serverid=" + zoneId + "&platname=" + PLATNAME + "&timestamp=" + curTime + "&sig=" + hash;
        String url = LOGIN_URL + "?" + param;
        
        return AjaxResult.success(url);
    }

    @RequestMapping("/pay")
    @ResponseBody
    public AjaxResult pay(String userId, String zoneId, String orderNo, Double price, HttpServletRequest request, HttpServletResponse response) {
        log.info("请求参数:userId:" + userId + ",zoneId:" + zoneId + ",orderNo:" + orderNo + ",price:" + price);
        
        int money = price.intValue();
        long curTime = System.currentTimeMillis();
        
        StringBuffer sb = new StringBuffer();
        sb.append(curTime)
        .append("account=").append(userId)
        .append("&rmb=").append(money)
        .append("&billno=").append(orderNo)
        .append("&serverid=").append(zoneId)
        .append("&platname=").append(PLATNAME)
        .append(PAYKEY);

        log.info("Zj_pay加密前:{}" , sb.toString());
        String sign = MD5Util.getMD5String(sb.toString());
        log.info("Zj_pay加密后:{}" ,sign);

        String param = "account=" + userId + "&rmb=" + money + "&billno=" +orderNo + "&serverid=" + zoneId + "&platname=" + PLATNAME + "&timestamp=" + curTime + "&sig=" + sign;
        String url = PAY_URL + "?" + param;
        
        String result = ApiConnector.get(url, null);
        
        if (StringUtils.isNotBlank(result)) {
        	JSONObject jsonResult = JSONObject.parseObject(result);
        	
        	int status = jsonResult.getIntValue("status");
        	
            if (status == 0) {
                return AjaxResult.success("充值成功");
            } else {
                return AjaxResult.failed("充值失败!错误码:" + jsonResult.get("data"));
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
    	
    	StringBuffer sb = new StringBuffer();
        sb.append(curTime)
        .append("account=").append(userId)
        .append("&serverid=").append(zoneId)
        .append("&platname=").append(PLATNAME)
        .append(OHTHERKEY);
       
        String hash = MD5Util.getMD5String(sb.toString());
        log.info("Zj_checkRole的加密前:{}" , sb.toString());
        log.info("Zj_checkRole的加密后:{}" ,hash);
        
        String param = "account=" + userId + "&serverid=" + zoneId + "&platname=" + PLATNAME + "&timestamp=" + curTime + "&sig=" + hash;
        String url = CHECKROLE_URL + "?" + param;
        
        String result = ApiConnector.get(url, null);
        
        if (StringUtils.isNotBlank(result)) {
            JSONObject jsonResult = JSONObject.parseObject(result);
           
            int status = jsonResult.getIntValue("status");
            
            return status == 0 ? AjaxResult.success(true) : AjaxResult.success(false);
        } else {
            log.error("查询角色无响应,用户帐号:" + userId);
            return AjaxResult.failed("查询角色无响应");
        }
    }
}
