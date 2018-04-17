package com.dome.webgameserver.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.sdkserver.bq.view.AjaxResult;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.MD5Util;

/**
 * 仙境传说
 */
@Controller
@RequestMapping("/webgame/ro")
public class RoController {	

    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String GAMEKEY = "433b99a484443ab1eea91fd1811f14cd";

    public static final String PAYKEY = "2a01fefd3315237f055bcb2a300d67dc";

    public static final String PLATID = "20";

    public static final String LOGIN_URL = "http://s{zoneId}.ro.qbao.utogame.com/check.php";

	public static final String PAY_URL = "http://s{zoneId}.ro.qbao.utogame.com/pay.php";

	public static final String CHECKROLE_URL = "http://s{zoneId}.ro.qbao.utogame.com/checkuser.php";
	
	/*public static final Map<String,String> PAY_RESULTMAP = new HashMap<String,String>(){
		private static final long serialVersionUID = -5253015140308235136L;
		{
			put("0", "充值失败");
			put("1", "充值成功");
			put("-1", "参数错误");
			put("-2", "URL请求时间超时");
			put("-3", "sid或者platid错误");
			put("-4", "sig验证错误");
			put("-5", "无该用户");
			put("-6", "IP禁止访问");
			put("-7", "订单已完成");
			put("-8", "订单重发时前后不一致");
		}
	};*/

    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult login(String userId, String zoneId, HttpServletRequest request, HttpServletResponse response) {
    	String isadult = "1";
    	
    	long tm = System.currentTimeMillis()/1000;
    	
        StringBuffer sb = new StringBuffer();
        sb.append("cm=").append(isadult)
        .append("&platid=").append(PLATID)
        .append("&platuid=").append(userId)
        .append("&sid=").append(zoneId)
        .append("&tm=").append(tm)
        .append(GAMEKEY);
        
        log.info("RO_login加密前:{}" , sb.toString());
        String sig = MD5Util.getMD5String(sb.toString());
        log.info("RO_login加密后:{}" ,sig);
        
        String param = "tm=" + tm + "&platuid=" + userId + "&platid=" + PLATID + "&sid="
        + zoneId + "&cm=" + isadult + "&sig=" + sig; 
        
        String serverid = Integer.valueOf(zoneId)%2000000 + "";
        String url = StringUtils.replace(LOGIN_URL, "{zoneId}", serverid) + "?" + param ;
        
		log.info("url为：{}",url);
		
		return AjaxResult.success(url);
    }
    
    @RequestMapping("/pay")
    @ResponseBody
    public AjaxResult pay(String userId, String zoneId, String orderNo, Double price, HttpServletRequest request, HttpServletResponse response) {
        
    	int money = price.intValue();
    	
    	long tm = System.currentTimeMillis()/1000;
    	 // 充值比例
        int rmbToXz = 10;
        int num = rmbToXz * money;
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("money=").append(money)
        .append("&orderid=").append(orderNo)
        .append("&paypoint=").append(num)
        .append("&platid=").append(PLATID)
        .append("&platuid=").append(userId)
        .append("&sid=").append(zoneId)
        .append("&tm=").append(tm)
        .append(PAYKEY);

    	log.info("RO_pay加密前:{}" , sb.toString());
        String sig = MD5Util.getMD5String(sb.toString());
        log.info("RO_pay加密后:{}" ,sig);

        String param = "orderid=" + orderNo + "&money=" + money + "&paypoint=" 
        		+num + "&platid=" + PLATID + "&platuid=" + userId + "&sid=" + zoneId +"&tm=" + tm + "&sig=" + sig;
        
        String serverid = Integer.valueOf(zoneId)%2000000 + "";
        String url = StringUtils.replace(PAY_URL, "{zoneId}", serverid) + "?" + param ;
        
        String result = ApiConnector.post(url, null);
        
        if (StringUtils.isNotBlank(result)) {
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

    	long tm = System.currentTimeMillis()/1000;
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("platid=").append(PLATID)
        .append("&platuid=").append(userId)
        .append("&sid=").append(zoneId)
        .append("&tm=").append(tm)
        .append(PAYKEY);

    	log.info("RO_checkRole加密前:{}" , sb.toString());
        String sig = MD5Util.getMD5String(sb.toString());
        log.info("RO_checkRole加密后:{}" ,sig);

    	String param = "platid=" + PLATID + "&platuid=" + userId + "&sid=" + zoneId + "&tm=" + tm + "&sig=" + sig;

		String serverid = Integer.valueOf(zoneId) % 2000000 + "";
	    String url = StringUtils.replace(CHECKROLE_URL, "{zoneId}", serverid) + "?" + param ;
		
        String result = ApiConnector.post(url, null);
        
        if (StringUtils.isNotBlank(result)) {
            return result.equals("1") ? AjaxResult.success(true) : AjaxResult.success(false);
        } else {
            log.error("查询角色无响应,用户帐号:" + userId);
            return AjaxResult.failed("查询角色无响应");
        }
    }

}
