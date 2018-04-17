package com.dome.sdkserver.bq.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * ChannelEnum
 *
 * @author Zhang ShanMin
 * @date 2017/10/17
 * @time 10:19
 */
public enum ChannelEnum {

    QBAO("CHA000001"),//宝玩渠道码
    DOME("CHA000002"),//冰趣渠道码
    PUBLISH("CHA000003"),//发行渠道码
    OVERSEAS("CHA000004"),//海外渠道码
    WEBGAME("CHA000005"),//页游渠道码
    YOUPIAO("CHA000006"),//有票渠道码
    OGP("CHA000007"),//ogp渠道码
    QBAO_H5_WX_PUB("CHA0000047"),//宝玩H5微信公众号
    BQ_H5_WX_PUB("CHA0000048"),//冰穹互娱微信公众号
    ;

    private ChannelEnum(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    /**
     * 微信公众号渠道
     */
    private static List<String> wxPublicChannelCodes = new ArrayList<String>() {
        {
            add(QBAO_H5_WX_PUB.code);
            add(BQ_H5_WX_PUB.code);
        }
    };


    /**
     * 页游独立渠道
     */
    public static List<String> webGameIndependentChannel = new ArrayList<String>() {
        {
            add(QBAO.code);
            add(WEBGAME.code);
        }
    };

    /**
     * 是否是微信公众号支付渠道
     * @param channelCode
     * @return
     */
    public static boolean isWXPublicChannelCode(String channelCode) {
        return wxPublicChannelCodes.contains(channelCode);
    }
}


