package com.dome.sdkserver.bq.enumeration;


import com.dome.sdkserver.bq.constants.ChannelEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * 收银平台
 * PayPlatformEnum
 *
 * @author Zhang ShanMin
 * @date 2017/11/1
 * @time 14:43
 */
public enum PayPlatformEnum {

    DOME,//冰穹
    YOUPIAO,//有票
    WX_PUBLIC,//微信公众号支付
    ;


    /**
     * 根据渠道code获取收银平台
     *
     * @param channelCode
     * @return
     */
    public static PayPlatformEnum getPayPlatform(String channelCode) {
        if (StringUtils.isBlank(channelCode))
            return DOME;
        else if (ChannelEnum.YOUPIAO.getCode().equalsIgnoreCase(channelCode))
            return YOUPIAO;
        else if (channelCode.indexOf("_") > -1)
            return WX_PUBLIC;
        return DOME;
    }
}
