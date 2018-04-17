package com.dome.sdkserver.controller.pay.ali;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.biz.utils.BizUtil;
import com.dome.sdkserver.bq.constants.redis.RedisConstants;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.controller.pay.basic.PayBaseController;
import com.dome.sdkserver.service.pay.PayService;
import com.dome.sdkserver.service.web.requestEntity.HttpRequestOrderInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * AliPayController
 *
 * @author Zhang ShanMin
 * @date 2016/12/17
 * @time 11:14
 */
@Controller
@RequestMapping("/pay")
public class AliPayController extends PayBaseController {

    @Resource(name = "payService")
    private PayService payService;

    /**
     * 外部调用sdk支付宝即时到帐支付
     */
    @RequestMapping(value = "aliPayDown", method = RequestMethod.POST)
    @ResponseBody
    public SdkOauthResult aliPayDown(HttpRequestOrderInfo orderReqInfo) {
        SdkOauthResult result;
        String redisKey = "";
        try {
            orderReqInfo.setPayType(1);//设置默认的支付类型为支付宝支付
            //验证请求参数
            result = BizUtil.validateOrderCreateParams(orderReqInfo);
            if (!result.isSuccess()) return result;
            //验证签名
            result = BizUtil.validateCreateOrderSign(orderReqInfo,domainConfig);
            if (!result.isSuccess()) return result;
            int lockTime = domainConfig.getInt("other.ali.pay.down.lock.time", "30");//秒
            redisKey = RedisConstants.OTHER_ALI_PAY_DOWN_LOCK + orderReqInfo.getPayOrigin() + ":" + orderReqInfo.getBuyerId() + ":" + orderReqInfo.getReqIp();
            //支付锁定
            if (redisUtil.tryLock(redisKey, lockTime, "lock")) return SdkOauthResult.failed("支付中，请稍后");
            result = payService.createOrder(orderReqInfo);
            if (!result.isSuccess()) return result;
            //返回支付宝响应报文
            Map<String, Object> resultMap = getAliPayDownResp(orderReqInfo, result);
            redisUtil.del(redisKey);
            return SdkOauthResult.success(resultMap);
        } catch (Exception e) {
            log.error("外部调用sdk支付宝即时到帐支付异常", e);
            return SdkOauthResult.failed("系统异常，请稍后再试");
        } finally {
            log.info("外部调用sdk支付宝即时到请求参数:" + JSONObject.toJSONString(orderReqInfo));
        }
    }






}
