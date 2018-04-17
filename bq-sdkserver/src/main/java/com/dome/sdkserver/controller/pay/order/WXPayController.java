package com.dome.sdkserver.controller.pay.order;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.enums.OrderStatusEnum;
import com.dome.sdkserver.biz.utils.WechatPayUtil;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.util.GenOrderCode;
import com.dome.sdkserver.bq.util.PriceUtil;
import com.dome.sdkserver.metadata.entity.bq.pay.OrderEntity;
import com.dome.sdkserver.metadata.entity.bq.pay.WeChatEntity;
import com.dome.sdkserver.service.order.OrderService;
import com.dome.sdkserver.service.pay.PayConfigService;
import com.dome.store.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * WXPayController
 *
 * @author Zhang ShanMin
 * @date 2017/11/10
 * @time 9:27
 */
@Controller
@RequestMapping("/wx/pay")
public class WXPayController {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PayConfigService payConfigService;
    @Autowired
    protected OrderService orderService;

    @RequestMapping(value = "jsapi")
    public String jspsPay(HttpServletRequest request, ModelAndView modelAndView) throws Exception {
        String code = request.getParameter("code");
        String weixinToken =  getWxAccessTokenByCode(code);
        String opendId = JSONObject.parseObject(weixinToken).getString("openid");
        String userId = "xxx" ;
        Double totalfee = 0.01;
        String orderNo = GenOrderCode.next();
        Map<String, String> payData = payConfigService.getAllConfig(4);
        String ip = WechatPayUtil.getRemoteHost(request);
        OrderEntity entity = new OrderEntity();
        entity.setAppName("111");
        entity.setBuyerId(userId);
        entity.setChargePointName("ccc");
        entity.setChargePointAmount(totalfee);
        entity.setGameOrderNo(orderNo);
        entity.setOrderNo(orderNo);
        entity.setPayNotifyUrl("http://127.0.0.1:8890/data/success.html");
        entity.setOrderStatus(OrderStatusEnum.orderstatus_no_pay.code);
        entity.setCurMonth(DateUtils.getCurDateFormatStr(DateUtils.DEFAULT_CUR_MONTH_FORMAT));
        entity.setChargePointCode("C0000000");//默认
        entity.setAppCode("00");
        entity.setChannelCode("00");
        orderService.createOrder(entity);
        WeChatEntity weChatEntity = new WeChatEntity.Builder().openId(opendId).
                nonceStr(WechatPayUtil.generateNonceStr()).spBillCreateIp(ip).
                outTradeNo(orderNo).tradeType("JSAPI").body("公众号测试支付").
                totalFee(PriceUtil.convertToFen(totalfee.toString())).
              appId("wxa95b0f4f4542a893").mchId("1491521992").signKey("BingQiongHuYuWXPublicPay20171113").build(); //todo signkey
                //appId("wx68a7c85d65c78242").mchId("1491791122").signKey("OpenPlatformWXPublicPay201711201").build(); //todo signkey
        String prepay_id = WechatPayUtil.weixinPayUnifiedorder(weChatEntity, payData);
        String nonceStr = WechatPayUtil.generateNonceStr();
        String timestamp = WechatPayUtil.getTimeStamp();
        Map<String,String> map = new HashMap<>();
        map.put("appId","wxa95b0f4f4542a893");
        //map.put("appId","wx68a7c85d65c78242");
        map.put("timeStamp",timestamp);
        map.put("nonceStr",nonceStr);
        map.put("package","prepay_id="+prepay_id);
        map.put("signType","MD5");
        String paySign = WechatPayUtil.createSign(map, "BingQiongHuYuWXPublicPay20171113");
        //String paySign = WechatPayUtil.createSign(map, "OpenPlatformWXPublicPay201711201");
        map.put("paySign",paySign);
        map.put("userId","bq_000000020");
        map.put("appCode","H0000042");
        map.put("platformCode","CHA000001");
        map.put("sdkdomain","http://myoldfycy318.xicp.io");
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,String> entry:map.entrySet()){
            sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        logger.info(sb.toString());
        return "redirect:/h5GameWxJsJSAPI.jsp?"+sb.toString().substring(1);
    }

    @RequestMapping("/authorize_public")
    @ResponseBody
    public void wxPublicAuthorize(HttpServletRequest request, HttpServletResponse response) {
        try {

            String url = "https://open.weixin.qq.com/connect/oauth2/authorize"
                    + "?appid=wxa95b0f4f4542a893"
                    //+ "?appid=wx68a7c85d65c78242"
                    + "&redirect_uri=" + URLEncoder.encode("http://myoldfycy318.xicp.io/wx/pay/jsapi", "UTF-8")
                    + "&response_type=code&scope=snsapi_userinfo&state=123456"
                    + "#wechat_redirect";
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWxAccessTokenByCode(String code) {

            String appid = "wxa95b0f4f4542a893";
            String secret = "678e4ffc07cbcf54986be476c7d5dbfc";

     /*   String appid = "wx68a7c85d65c78242";
        String secret = "8c207c5bbc06b3a83e4524ddb077f64e";*/
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token"
                + "?appid=" + appid
                + "&secret=" + secret
                + "&code=" + code
                + "&grant_type=authorization_code";

        String backStr = HttpUtil.httpRequest(accessTokenUrl, "GET", null);
        if (StringUtils.isNotBlank(JSONObject.parseObject(backStr).getString("access_token"))) {
            return backStr;
        } else {
            logger.error("微信获取AccessToken失败!errcode:{};errmsg:{}", JSONObject.parseObject(backStr).getString("errcode"), JSONObject.parseObject(backStr).getString("errmsg"));
        }
        return null;
    }
}
