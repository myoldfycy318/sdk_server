package com.dome.sdkserver.bq.enumeration;

/**
 * 支付类型同步给BI
 * PayType2BiEnum
 *
 * @author Zhang ShanMin
 * @date 2017/9/27
 * @time 9:52
 */
public enum PayType2BiEnum {

    ali,//阿里支付
    wx,//微信支付
    qbao,//钱宝支付
    ogp,//ogp平台支付
    ios,//ios支付
    yp//有票平台支付
    ;

    /**
     * 获取Bi报表定义的支付类型
     *
     * @param payType
     * @return
     */
    public static String getBiPayType(Integer payType) {
        if (PayTypeEnum.钱宝支付.getCode() == payType) {
            return qbao.name();
        }
        if (PayTypeEnum.支付宝支付.getCode() == payType) {
            return ali.name();
        }
        if (PayTypeEnum.微信支付.getCode() == payType) {
            return wx.name();
        }
        return "";
    }
}
