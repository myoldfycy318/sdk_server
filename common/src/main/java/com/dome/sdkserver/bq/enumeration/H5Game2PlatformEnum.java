package com.dome.sdkserver.bq.enumeration;

import com.dome.sdkserver.bq.constants.ChannelEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * H5Game2PlatformEnum
 * H5游戏嵌入平台
 *
 * @author Zhang ShanMin
 * @date 2016/11/7
 * @time 16:28
 */
public enum H5Game2PlatformEnum {

    BINGQU,//冰趣
    QBAO,//宝玩
    YOUPIAO,//有票
    KUYA,//酷雅
    ;


    /**
     * 获取H5嵌入平台枚举
     *
     * @param plateformCode
     * @return
     */
    public static H5Game2PlatformEnum getH5Game2Platform(String plateformCode) {
        if (StringUtils.isBlank(plateformCode))
            return null;
        for (H5Game2PlatformEnum gPEnum : H5Game2PlatformEnum.values()) {
            if (gPEnum.name().equals(plateformCode.toUpperCase())) {
                return (gPEnum == KUYA) ? QBAO : gPEnum;
            }
        }
        return null;
    }

    /**
     * H5游戏根据平台获取渠道code
     *
     * @param plateformCode
     * @return
     */
    public static String getChannelByPlatform(String plateformCode) {
        if (StringUtils.isBlank(plateformCode))
            return null;
        if (plateformCode.indexOf("_")> -1)
            return plateformCode;
        H5Game2PlatformEnum h5Game2Platform = getH5Game2Platform(plateformCode);
        if (h5Game2Platform == null)//只有plateformCode=CHA000001(渠道码)，h5Game2Platform=null
            return plateformCode;
        switch (h5Game2Platform) {
            case QBAO:
            case KUYA:
                return ChannelEnum.QBAO.getCode();//宝玩渠道
            case YOUPIAO:
                return ChannelEnum.YOUPIAO.getCode();//有票渠道
            case BINGQU:
                return ChannelEnum.DOME.getCode();//冰趣渠道
            default:
                return null;
        }
    }

    public static Set<String> QbaoChannelSet = new HashSet<String>() {{
        add(ChannelEnum.QBAO.getCode());
        add(ChannelEnum.YOUPIAO.getCode());
    }};

    /**
     * 是否是钱宝账户体系
     * @param channelCode
     * @return
     */
    public static boolean isQbaoChannel(String channelCode) {
       return QbaoChannelSet.contains(channelCode);
    }


}
