package com.dome.sdkserver.bq.util;

import com.dome.sdkserver.util.DESUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * PayUtil
 *
 * @author Zhang ShanMin
 * @date 2016/10/12
 * @time 9:51
 */
public class PayUtil {


    /**
     * 调订单页面参数加密
     *
     * @param str
     * @return
     */
    public static String orderDesEncrypt(String str, String secretkey) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(DESUtil.encrypt(str, secretkey).getBytes());
    }

    /**
     * 调订单页面参数解密
     *
     * @param str
     * @param secretkey
     * @return
     */
    public static String orderDesDecrypt(String str, String secretkey) {
        return DESUtil.decrypt(new String(Base64.decodeBase64(str)), secretkey);
    }

    /**
     * 支付宝sdk的订单号为“201608171126071108247”截取前六位对应交易的月份，
     * 找到对应的分表（domesdk_order201608）
     *
     * @param sdkOrderNo
     * @return
     */
    public static String getPayMonth(String sdkOrderNo) {
        try {
            if (StringUtils.isBlank(sdkOrderNo))
                return "";
            return sdkOrderNo.substring(0, 6);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 第三方sdk的订单号为“170324160726420537”截取前四位对应交易的月份，
     * 找到对应的分表（sdk_third_party_order_201709）
     *
     * @param orderNo
     * @return
     */
    public static String getThirdPayMonth(String orderNo){
        return getqBaoPayMonth(orderNo);
    }

    /**
     * 钱宝sdk的订单号为“170324160726420537”截取前四位对应交易的月份，
     * 找到对应的分表（sdk_tp_qbaopay_trans_201612）
     *
     * @param sdkOrderNo
     * @return
     */
    public static String getqBaoPayMonth(String sdkOrderNo) {
        try {
            if (StringUtils.isBlank(sdkOrderNo))
                return "";
            return "20" + sdkOrderNo.substring(0, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String sdkOrderNo = "170324160726420537";
        System.out.println("1:"+getqBaoPayMonth(sdkOrderNo));
        System.out.println("20"+sdkOrderNo.substring(0, 4));
    }

}
