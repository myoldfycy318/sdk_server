package com.dome.sdkserver.bq.enumeration;


import org.apache.commons.lang3.StringUtils;

/**
 * 用以标识同步给BI的支付数据来自sdkserver哪张表
 * PaySource2BiEnum
 *
 * @author Zhang ShanMin
 * @date 2017/9/27
 * @time 9:52
 */
public enum PaySource2BiEnum {
    domepay("domesdk_order"), //domesdk_order201709
    qbaopay("sdk_qbaopay_trans_"), //sdk_qbaopay_trans_201709
    thirdpay("sdk_third_party_order_");//sdk_third_party_order_201709


    private PaySource2BiEnum(String payTbl) {
        this.payTbl = payTbl;
    }

    /**
     * 根据充值类型获取充值来自哪张表
     *
     * @param str
     * @return
     */
    public static PaySource2BiEnum getPaySourceTbl(String str) {
        if (StringUtils.isBlank(str))
            return null;
        for (PaySource2BiEnum paySource2BiEnum : PaySource2BiEnum.values()) {
            if (paySource2BiEnum.name().equalsIgnoreCase(str))
                return paySource2BiEnum;
        }
        return null;
    }

    private String payTbl;

    public String getPayTbl() {
        return payTbl;
    }

}
