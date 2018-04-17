package com.dome.sdkserver.controller.coupon;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.constants.PayConstant;
import com.dome.sdkserver.bq.constants.PayConstant.RETURN_TICKETS_ORIGIN;
import com.dome.sdkserver.bq.constants.SendBqStatusEnum;
import com.dome.sdkserver.bq.constants.redis.RedisConstants;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.util.GenOrderCode;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.metadata.entity.bq.award.BqAutoSendEntity;
import com.dome.sdkserver.metadata.entity.bq.award.CouponReqVo;
import com.dome.sdkserver.service.coupon.CouponService;
import com.dome.sdkserver.service.login.ThridRequestService;
import com.dome.sdkserver.service.pay.qbao.bo.UserInfo;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RSACoder;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 返劵接口
 * CouponController
 *
 * @author Zhang ShanMin
 * @date 2016/12/7
 * @time 15:16
 */
@Controller
@RequestMapping("/coupon")
public class CouponController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "couponService")
    private CouponService couponService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PropertiesUtil domainConfig;
    @Autowired
    private ThridRequestService thridRequestService;

    @RequestMapping(value = "qbSend")
    @ResponseBody
    public SdkOauthResult sendCoupon(HttpServletRequest request, CouponReqVo couponReqVo) {
        logger.info("请求发劵请求参数：{}", JSONObject.toJSONString(couponReqVo));
        String redisKey = "";
        try {
            //验证请求参数
            SdkOauthResult result = validateReqParams(couponReqVo);
            if (!result.isSuccess())
                return result;
            //验证签名
            result = validateSign(couponReqVo);
            if (!result.isSuccess())
                return result;
            int lockTime = domainConfig.getInt("fanjuan.user.lock.time", "30");//秒
            redisKey = RedisConstants.QBAO_FAN_JUAN_LOCK + couponReqVo.getSource() + ":" + couponReqVo.getUserId();
            //返劵锁定
            if (redisUtil.tryLock(redisKey, lockTime, "lock")) {
                return SdkOauthResult.failed("返劵中，请稍后");
            }
            //判断是否存在该用户
            UserInfo userInfo = thridRequestService.loadUserInfo(Long.valueOf(couponReqVo.getUserId()));
            if (null == userInfo) {
                logger.error("用户中心返回结果：不存在该用户,用户ID：{}", couponReqVo.getUserId());
                return SdkOauthResult.failed("该用户不存在");
            }
            return couponService.sendCoupon(convertObj(couponReqVo));
        } catch (Exception e) {
            logger.error("发劵异常", e);
            return SdkOauthResult.failed("系统异常请稍后重试");
        } finally {
            redisUtil.del(redisKey);
        }
    }


    /**
     * 请求参数转化
     *
     * @param couponReqVo
     * @return
     * @throws Exception
     */
    private BqAutoSendEntity convertObj(CouponReqVo couponReqVo) throws Exception {
        BqAutoSendEntity entity = new BqAutoSendEntity();
        entity.setPayUserId(couponReqVo.getUserId());
        entity.setStatus(SendBqStatusEnum.AUTO_SEND_NO.getStatus());
        entity.setBqAward(new BigDecimal(couponReqVo.getAmount()));
        StringBuilder sb = new StringBuilder();
        sb.append(RETURN_TICKETS_ORIGIN.getReturnTicketsORIGIN(couponReqVo.getSource()).getDesc());
        if (StringUtils.isBlank(couponReqVo.getTradeDesc())) {
            sb.append("返劵:").append(couponReqVo.getAmount()).append(",手续费:").append(couponReqVo.getFeeAmount());
        } else {
            sb.append(new String(Base64.decodeBase64(couponReqVo.getTradeDesc()), "utf-8"));
        }
        entity.setBizDesc(sb.toString());
        entity.setBusiType(couponReqVo.getBusiType());
        entity.setOutTradeNo(couponReqVo.getOutTradeNo());
        entity.setTradeNo(GenOrderCode.next());
        entity.setTerminal(couponReqVo.getTerminal());
        entity.setFeeAmount(new BigDecimal(couponReqVo.getFeeAmount()));
        entity.setBqSource(couponReqVo.getSource());
        entity.setTransDate(DateUtils.getCurDateFormatStr("yyyyMMdd"));
        return entity;
    }


    /**
     * 验证请求参数
     *
     * @param couponReqVo
     * @return
     */
    private SdkOauthResult validateReqParams(CouponReqVo couponReqVo) {
        if (StringUtils.isBlank(couponReqVo.getUserId()) ||
                StringUtils.isBlank(couponReqVo.getSignCode()) || StringUtils.isBlank(couponReqVo.getOutTradeNo()) ||
                couponReqVo.getTerminal() == null || couponReqVo.getFeeAmount() == null)
            return SdkOauthResult.failed("必填参数不能为空");
        if (couponReqVo.getAmount() == null || couponReqVo.getAmount().longValue() == 0)
            return SdkOauthResult.failed("返劵金额为0");
        if (StringUtils.isBlank(couponReqVo.getSignCode()) ||
                null == RETURN_TICKETS_ORIGIN.getReturnTicketsORIGIN(couponReqVo.getSource()) ||
                null == PayConstant.SS_BUSI_TYPE.getBusiType(couponReqVo.getSource()) ||
                null == PayConstant.RET_TICKETS_BUSI_TYPE.getRetTicketsBusiType(couponReqVo.getSource()))
            return SdkOauthResult.failed("没有该返劵来源");
        return SdkOauthResult.success();
    }

    /**
     * 验证签名
     *
     * @param couponReqVo
     * @return
     */
    private SdkOauthResult validateSign(CouponReqVo couponReqVo) throws Exception {
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("userId", couponReqVo.getUserId());
        signMap.put("outTradeNo", couponReqVo.getOutTradeNo());
        signMap.put("terminal", String.valueOf(couponReqVo.getTerminal()));
        signMap.put("amount", String.valueOf(couponReqVo.getAmount()));
        signMap.put("source", String.valueOf(couponReqVo.getSource()));
        signMap.put("feeAmount", String.valueOf(couponReqVo.getFeeAmount()));
        String signParam = MapUtil.createLinkString(signMap);
        RETURN_TICKETS_ORIGIN originEnum = RETURN_TICKETS_ORIGIN.getReturnTicketsORIGIN(couponReqVo.getSource());
        if (originEnum == null)
            return SdkOauthResult.failed("没有该返劵来源");
        String rsaSignKey = domainConfig.getString(originEnum.getRsaKey());
        return RSACoder.verify(signParam.getBytes(), rsaSignKey, couponReqVo.getSignCode()) ? SdkOauthResult.success() : SdkOauthResult.failed("返劵签名错误");
    }


}
