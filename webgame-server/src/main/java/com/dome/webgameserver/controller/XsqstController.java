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

@Controller
@RequestMapping("/webgame/xsqst")
public class XsqstController {
	public final Logger log = LoggerFactory.getLogger(this.getClass());

	public static final String XSQST_LY = "qbao";

	public static final String XSQST_LOGINKEY = "70qbaobd1a82d722bb4743a4009e186965fcd3";

	public static final String XSQST_PAYKEY = "70qbao75df386001014565948a3f0f1c2b09fe";

	public static final String XSQST_LOGIN_URL = "http://gc.74.kongzhong.com/xsqstly/loginlygameurl/{ly}/{key}";

	public static final String XSQST_PAY_URL = "http://gc.74.kongzhong.com/xsqstly/sendItemYb/{ly}/{key}";

	public static final String XSQST_CHECKROLE_URL = "http://gc.74.kongzhong.com/xsqstly/checkRoleExist/{ly}/{key}";

	@RequestMapping("/login")
	@ResponseBody
	public AjaxResult login(String userId, String zoneId, HttpServletRequest request, HttpServletResponse response) {
		// userId = "test002";
		// zoneId = "1";

		String isadult = "1";

		Map<String, String> loginParam = new HashMap<String, String>();
		loginParam.put("ly", XSQST_LY);
		loginParam.put("account", userId);
		loginParam.put("zoneid", zoneId);
		String ptime = DateUtil.dateToDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
		loginParam.put("ptime", ptime);
		loginParam.put("isadult", isadult);

		String signBase = XSQST_LY + userId + zoneId + isadult + ptime + XSQST_LOGINKEY;
		// log.info("原始sign:" + signBase);

		String md5Sgin = MD5Util.getMD5String(signBase);
		// log.info("MD5 sign:" + md5Sgin);
		loginParam.put("sign", md5Sgin);

		String jsonParam = JSONObject.toJSONString(loginParam);
		// log.info("jsonParam:" + jsonParam);

		String key = new String(Base64.encode(jsonParam.getBytes()));
		// log.info("key:" + key);

		String url = XSQST_LOGIN_URL.replace("{ly}", XSQST_LY);
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
		int xznum = rmbToXz * price.intValue();

		Map<String, Object> loginParam = new HashMap<String, Object>();
		loginParam.put("ly", XSQST_LY);
		loginParam.put("account", userId);
		loginParam.put("zoneid", zoneId);
		String ptime = DateUtil.dateToDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
		loginParam.put("ptime", ptime);
		loginParam.put("orderno", orderNo);
		loginParam.put("price", price);
		loginParam.put("xznum", String.valueOf(xznum));
		loginParam.put("debug", debug);

		String signBase = XSQST_LY + userId + zoneId + priceStr + xznum + orderNo + ptime + debug + XSQST_PAYKEY;
		log.info("原始sign:" + signBase);

		String md5Sgin = MD5Util.getMD5String(signBase);
		log.info("MD5 sign:" + md5Sgin);
		loginParam.put("sign", md5Sgin);

		String jsonParam = JSONObject.toJSONString(loginParam);
		log.info("jsonParam:" + jsonParam);

		String key = new String(Base64.encode(jsonParam.getBytes()));
		log.info("key:" + key);

		String url = XSQST_PAY_URL.replace("{ly}", XSQST_LY);
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

		userId = "test001";
		zoneId = "1";

		Map<String, Object> loginParam = new HashMap<String, Object>();
		loginParam.put("ly", XSQST_LY);
		loginParam.put("account", userId);
		loginParam.put("zoneid", zoneId);
		String ptime = DateUtil.dateToDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
		loginParam.put("ptime", ptime);

		String signBase = XSQST_LY + userId + zoneId + ptime + XSQST_LOGINKEY;
		// log.info("原始sign:" + signBase);

		String md5Sgin = MD5Util.getMD5String(signBase);
		// log.info("MD5 sign:" + md5Sgin);
		loginParam.put("sign", md5Sgin);

		String jsonParam = JSONObject.toJSONString(loginParam);
		// log.info("jsonParam:" + jsonParam);

		String key = new String(Base64.encode(jsonParam.getBytes()));
		// log.info("key:" + key);

		String url = XSQST_CHECKROLE_URL.replace("{ly}", XSQST_LY);
		url = url.replace("{key}", key);
		// log.info("请求url:" + url);

		String result = ApiConnector.post(url, null);
		if (result != null && !result.equals("")) {
			JSONObject jsonResult = JSONObject.parseObject(result);
			String resultCheck = jsonResult.getString("info");
			return resultCheck.equals("exist") ? AjaxResult.success(true) : AjaxResult.success(false);
		} else {
			log.error("查询角色无响应,用户帐号:" + userId);
			return AjaxResult.failed("查询角色无响应");
		}
	}

}
