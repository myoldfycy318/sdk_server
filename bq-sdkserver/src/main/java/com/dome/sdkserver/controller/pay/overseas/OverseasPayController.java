package com.dome.sdkserver.controller.pay.overseas;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.annotation.ZhConverter;
import com.dome.sdkserver.bq.constants.PayConstant.PAY_CH_TYPE;
import com.dome.sdkserver.bq.constants.redis.RedisConstants;
import com.dome.sdkserver.bq.util.MapUtil;
import com.dome.sdkserver.bq.util.google.Security;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.controller.pay.basic.PayBaseController;
import com.dome.sdkserver.metadata.entity.bq.pay.PublishOrderEntity;
import com.dome.sdkserver.service.overseas.OverseasService;
import com.dome.sdkserver.util.MD5;
import com.dome.sdkserver.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 海外支付
 */
@Controller
@RequestMapping("/overseas")
public class OverseasPayController extends PayBaseController {

    @Autowired
    protected PropertiesUtil domainConfig;
    @Resource(name = "overseasService")
    private OverseasService overseasService;


    /**
     * 谷歌支付、苹果支付成功通知服务端下发游戏道具
     *
     * @return
     */
    @ZhConverter
    @RequestMapping(value = "/createOrder")
    @ResponseBody
    public SdkOauthResult createOrder(PublishOrderEntity orderEntity) {
        String redisKey = "";
        try {
            log.info("谷歌支付、苹果支付请求参数：{}", JSONObject.toJSONString(orderEntity));
            //验证请求参数
            SdkOauthResult sdkOauthResult = validRecordPayParams(orderEntity);
            if (!sdkOauthResult.isSuccess())
                return sdkOauthResult;
            //验证签名
            sdkOauthResult = validRecordPaySign(orderEntity);
            if (!sdkOauthResult.isSuccess())
                return sdkOauthResult;
            int lockTime = domainConfig.getInt("overseas.pay.user.lock.time", "5");
            redisKey = RedisConstants.OVERSEAS_PAY_LOCK + orderEntity.getPayType() + ":" + orderEntity.getBuyerId();
            //支付锁定
            if (redisUtil.tryLock(redisKey, lockTime, "lock"))
                return SdkOauthResult.failed("操作頻繁，請稍後重試");
            return overseasService.createOrder(orderEntity);
        } catch (Exception e) {
            log.error("sdk手游支付成功流水记录异常，", e);
        } finally {
            redisUtil.del(redisKey);
        }
        return SdkOauthResult.failed("系統異常,請稍後重試");
    }

    /**
     * 谷歌支付、苹果支付成功通知服务端下发游戏道具
     *
     * @return
     */

    @ZhConverter
    @RequestMapping(value = "/notify")
    @ResponseBody
    public SdkOauthResult notify(PublishOrderEntity orderEntity) {
        String redisKey = "";
        try {
            //校验签名
            SdkOauthResult sdkOauthResult = validPayNotifySign(orderEntity);
            if (!sdkOauthResult.isSuccess())
                return sdkOauthResult;
            int lockTime = domainConfig.getInt("overseas.pay.user.lock.time", "5");
            redisKey = RedisConstants.OVERSEAS_PAY_NOTIFY_LOCK + orderEntity.getOrderNo();
            //通知锁定
            if (redisUtil.tryLock(redisKey, lockTime, "lock"))
                return SdkOauthResult.failed("支付處理中，請稍後");
            return overseasService.notify(orderEntity);
        } catch (Exception e) {
            log.error("谷歌支付、苹果支付成功通知服务端异常,", e);
        } finally {
            redisUtil.del(redisKey);
        }
        return SdkOauthResult.failed("支付異常，請聯系客護");
    }

    /**
     * 谷歌商品数据验证失败
     *
     * @param orderEntity
     * @return
     */
    @RequestMapping(value = "/validateBuyData")
    @ResponseBody
    public SdkOauthResult validatePurchaseData(PublishOrderEntity orderEntity) {
        boolean result = false;
        try {
            if (StringUtils.isBlank(orderEntity.getPurchaseData()) || StringUtils.isBlank(orderEntity.getSignature()))
                return SdkOauthResult.failed("谷歌支付通知簽名為空");
            String googlePublicKey = domainConfig.getString("google.pay.verify.public.key");
            //验证谷歌支付通知参数
            result = Security.verify(Security.generatePublicKey(googlePublicKey), orderEntity.getPurchaseData(), orderEntity.getSignature());
        } catch (Exception e) {
            log.error("验证谷歌支付商品信息异常", e);
        }
        return result ? SdkOauthResult.success() : SdkOauthResult.failed("驗證谷歌支付商品失敗");
    }

    /**
     * 验证支付通知签名
     *
     * @param orderEntity
     * @return
     * @throws Exception
     */
    private SdkOauthResult validPayNotifySign(PublishOrderEntity orderEntity) throws Exception {
        if (StringUtils.isBlank(orderEntity.getOrderNo()) || StringUtils.isBlank(orderEntity.getChannelCode())) {
            return SdkOauthResult.failed("必傳參數為空");
        }
        PAY_CH_TYPE pay_ch_type = PAY_CH_TYPE.getPayType(orderEntity.getPayType());
        if (pay_ch_type == null)
            return SdkOauthResult.failed("沒有該支付類型");
        switch (pay_ch_type) {
            case APPLEPAY: {
                if (StringUtils.isBlank(orderEntity.getReceiptData()))
                    return SdkOauthResult.failed("蘋果支付票根為空");
                break;
            }
            case GOOGLEPAY: {
                if (StringUtils.isBlank(orderEntity.getPurchaseData()) || StringUtils.isBlank(orderEntity.getSignature()))
                    return SdkOauthResult.failed("谷歌支付通知簽名為空");
                break;
            }
        }
        Map<String, String> map = new HashMap<String, String>(9);
        map.put("orderNo", orderEntity.getOrderNo());
        map.put("channelCode", orderEntity.getChannelCode());
        map.put("payType", orderEntity.getPayType() + "");
        String str = MapUtil.createLinkString(map) + "&sdkmd5key=" + domainConfig.getString("overseas.pay.md5.key");
        return orderEntity.getSignCode().equals(MD5.md5Encode(str)) ? SdkOauthResult.success() : SdkOauthResult.failed("簽名錯誤");
    }


    /**
     * 验证签名
     *
     * @param orderEntity
     * @return
     * @throws Exception
     */
    private SdkOauthResult validRecordPaySign(PublishOrderEntity orderEntity) throws Exception {
        Map<String, String> map = new HashMap<String, String>(9);
        map.put("gameOrderNo", orderEntity.getGameOrderNo());
        map.put("buyerId", orderEntity.getBuyerId());
        map.put("payType", orderEntity.getPayType() + "");
        map.put("chargePointCode", orderEntity.getChargePointCode());
        map.put("appCode", orderEntity.getAppCode());
        map.put("channelCode", orderEntity.getChannelCode());
        String str = MapUtil.createLinkString(map) + "&sdkmd5key=" + domainConfig.getString("overseas.pay.md5.key");
        return orderEntity.getSignCode().equals(MD5.md5Encode(str)) ? SdkOauthResult.success() : SdkOauthResult.failed("簽名錯誤");
    }

    /**
     * 校验请求参数
     *
     * @param orderEntity
     * @return
     */
    private SdkOauthResult validRecordPayParams(PublishOrderEntity orderEntity) {
        PAY_CH_TYPE pay_ch_type = PAY_CH_TYPE.getPayType(orderEntity.getPayType());
        if (pay_ch_type == null)
            return SdkOauthResult.failed("沒有該支付類型");
        if (StringUtils.isBlank(orderEntity.getAppCode()) || StringUtils.isBlank(orderEntity.getGameOrderNo()) || StringUtils.isBlank(orderEntity.getBuyerId())
                || StringUtils.isBlank(orderEntity.getSignCode())
                || StringUtils.isBlank(orderEntity.getChargePointCode())
                || StringUtils.isBlank(orderEntity.getChannelCode()))
            return SdkOauthResult.failed("必傳參數為空");
        return SdkOauthResult.success();
    }

}
