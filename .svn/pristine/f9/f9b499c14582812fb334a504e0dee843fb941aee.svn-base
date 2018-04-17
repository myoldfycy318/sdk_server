package com.dome.sdkserver.bq.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * PayConstant
 *
 * @author Zhang ShanMin
 * @date 2016/8/12
 * @time 18:18
 */
public interface PayConstant {

    /**
     * 支付宝支付来源
     */
    enum ALI_PAY_ORIGIN {
        APP(0, "冰穹游戏通行证"),//移动支付
        PC(1, ""),//及时到账
        WAP(2, ""),//手机网站支付
        RC(3, "冰穹游戏通行证"), //手游充值中心
        APPLEPAY(4, "冰穹游戏通行证") //苹果支付
        ;

        private ALI_PAY_ORIGIN(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private Integer code;
        private String desc;

        public Integer getCode() {
            return code;
        }

        public static ALI_PAY_ORIGIN getPayOrigin(String payOrigin) {
            if (StringUtils.isEmpty(payOrigin))
                return null;
            for (ALI_PAY_ORIGIN aliPayOrigin : ALI_PAY_ORIGIN.values()) {
                if (aliPayOrigin.name().equalsIgnoreCase(payOrigin)) {
                    return aliPayOrigin;
                }
            }
            return null;
        }

        public String getDesc() {
            return desc;
        }
    }


    /**
     * 返劵来源
     */
    enum RETURN_TICKETS_ORIGIN {
        BINGQIONG_GAME("BqGame", "", "冰穹游戏"),//冰穹游戏
        NEW_BUSINESS("NewBusi", "jubaopen.rettickets.rsa.public.key", "新业务返劵"), //新业务侧
        FULU("FuLu", "fulu.rettickets.rsa.public.key", "福禄返劵"),//福禄
        ;

        //获取返劵来源枚举
        public static RETURN_TICKETS_ORIGIN getReturnTicketsORIGIN(String code) {
            if (StringUtils.isBlank(code))
                return null;
            for (RETURN_TICKETS_ORIGIN origin : RETURN_TICKETS_ORIGIN.values()) {
                if (origin.getCode().equals(code))
                    return origin;
            }
            return null;
        }


        private RETURN_TICKETS_ORIGIN(String code, String rsaKey, String desc) {
            this.code = code;
            this.rsaKey = rsaKey;
            this.desc = desc;
        }

        private String code;
        private String rsaKey;
        private String desc;
        private String desKey;

        public String getCode() {
            return code;
        }

        public String getRsaKey() {
            return rsaKey;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 支付来源
     */
    enum PAY_ORIGIN {
        NEW_BUSINESS("NewBusi", "jubaopen.pay.rsa.public.key", "jubaopen.pay.dec.key", "新业务支付"), //新业务侧聚宝盆需求
        JB_AU_PAY("JB_AU_PAY", "jubaopen.pay.rsa.public.key", "jubaopen.pay.dec.key", "聚宝签到分自动续费"), //聚宝签到分自动续费
        ;

        //获取返劵来源枚举
        public static PAY_ORIGIN getPayORIGIN(String code) {
            if (StringUtils.isBlank(code))
                return null;
            for (PAY_ORIGIN pay_origin : PAY_ORIGIN.values()) {
                if (pay_origin.getCode().equals(code))
                    return pay_origin;
            }
            return null;
        }

        private PAY_ORIGIN(String code, String rsaKey, String desKey, String desc) {
            this.code = code;
            this.rsaKey = rsaKey;
            this.desc = desc;
            this.desKey = desKey;
        }

        private String code;
        private String rsaKey;
        private String desKey;
        private String desc;

        public String getCode() {
            return code;
        }

        public String getRsaKey() {
            return rsaKey;
        }

        public String getDesKey() {
            return desKey;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 业务大类型(商肃公司分配)
     * 具体业务与商肃业务大类型对应关系
     */
    enum SS_BUSI_TYPE {
        FULU("FuLu", "P0902"),//福禄游戏充值商肃业务大类型
        NEW_BUSINESS("NewBusi", "P0906"),//新业务侧聚宝盆商肃业务大类型
        JB_AU_PAY("JB_AU_PAY", "P0906"),//新业务侧聚宝盆商肃业务大类型
        ;

        private SS_BUSI_TYPE(String code, String busiType) {
            this.code = code;
            this.busiType = busiType;
        }

        public static SS_BUSI_TYPE getBusiType(String code) {
            if (StringUtils.isBlank(code))
                return null;
            for (SS_BUSI_TYPE ssBusiType : SS_BUSI_TYPE.values()) {
                if (code.equalsIgnoreCase(ssBusiType.getCode()))
                    return ssBusiType;
            }
            return null;
        }

        private String code;
        private String busiType;

        public String getCode() {
            return code;
        }

        public String getBusiType() {
            return busiType;
        }
    }

    /**
     * 明细业务类型（由商肃公司分配）
     * 具体业务对应的商肃返劵明细业务类型
     */
    enum RET_TICKETS_BUSI_TYPE {

        FULU("FuLu", "P090209"),//福禄游戏充值返利
        NEW_BUSINESS("NewBusi", "P090603"),//新业务侧聚宝盆宝劵返利
        ;

        public static RET_TICKETS_BUSI_TYPE getRetTicketsBusiType(String code) {
            if (StringUtils.isBlank(code))
                return null;
            for (RET_TICKETS_BUSI_TYPE ticketsBusiType : RET_TICKETS_BUSI_TYPE.values()) {
                if (code.equalsIgnoreCase(ticketsBusiType.getCode()))
                    return ticketsBusiType;
            }
            return null;
        }

        private RET_TICKETS_BUSI_TYPE(String code, String busiType) {
            this.code = code;
            this.busiType = busiType;
        }

        private String code;
        private String busiType;

        public String getCode() {
            return code;
        }

        public String getBusiType() {
            return busiType;
        }
    }

    /**
     * 新业务侧聚宝盆需求支付对应商肃业务明细类型
     */
    enum PAY_TYPE {
        COMBINEPAY(0),//组合支付
        RMBPAY(1), //人民币支付
        BQPAY(2),  //宝券支付
        ;

        private PAY_TYPE(Integer payType) {
            this.payType = payType;
        }

        private PAY_TYPE(Integer payType, String busiType) {
            this.payType = payType;
            this.busiType = busiType;
        }

        public static PAY_TYPE getPayType(Integer payType) {
            if (null == payType)
                return null;
            for (PAY_TYPE pay_type : PAY_TYPE.values()) {
                if (pay_type.getPayType() == payType)
                    return pay_type;
            }
            return null;
        }

        private Integer payType;
        private String busiType;

        public Integer getPayType() {
            return payType;
        }

        public String getBusiType() {
            return busiType;
        }
    }

    /**
     * 聚宝盆项目支付业务子类型
     */
    enum JUBAOPEN_PAY_BUSISUBTYPE {
        RMBPAY("P090601"),//聚宝盆付款：人民币支付
        BQPAY("P090602"),  //聚宝盆付款：宝劵支付
        ;

        private JUBAOPEN_PAY_BUSISUBTYPE(String busiType) {
            this.busiType = busiType;
        }

        private String busiType;

        public String getBusiType() {
            return busiType;
        }
    }

    enum PAY_REQ_ORIGIN {

        FULU_AP_PAY("FULU", "fulu.ali.pay.down.rsa.key", "fulu.ali.pay.down.notify.md5.sign.key"),//福禄ali pc端支付
        JUBAOPEN_AP_PAY("JBP", "jubaopen.ali.pay.down.rsa.key", "jubaopen.ali.pay.down.notify.md5.sign.key"),//聚宝盆ali pc端支付
        TAOBAOER_AP_PAY("TBE", "tbr.ali.pay.down.rsa.key", "tbr.ali.pay.down.notify.md5.sign.key"),//套宝儿ali pc端支付
        MAYCARD("MyCard", "", ""),//MyCard支付
        APPLEPAY("ApplePay", "", ""),//苹果支付
        GOOGLEPAY("GooglePay", "", ""),//谷歌支付
        VR_PAY("VR", "vr.order.create.md5.sign.key", "vr.pay.notify.md5.sign.key"),//VR 平台 pc端支付
        PC("PC","",""),//pc端支付
        ;

        public static PAY_REQ_ORIGIN getPayReqOrigin(String code) {
            if (StringUtils.isBlank(code))
                return null;
            for (PAY_REQ_ORIGIN payReqOrigin : PAY_REQ_ORIGIN.values()) {
                if (payReqOrigin.code.equalsIgnoreCase(code))
                    return payReqOrigin;
            }
            return null;
        }

        private PAY_REQ_ORIGIN(String code, String payKey, String notifyKey) {
            this.code = code;
            this.payKey = payKey;
            this.notifyKey = notifyKey;
        }

        private String code;
        private String payKey;
        private String notifyKey;

        public String getPayKey() {
            return payKey;
        }

        public String getNotifyKey() {
            return notifyKey;
        }
    }

    /**
     * 是否需要密码支付
     */
    enum IS_PAY_PW {
        NEED_PW(1),//需要
        NO_PW(0);

        private IS_PAY_PW(Integer code) {
            this.code = code;
        }

        private Integer code;

        public static IS_PAY_PW getIsPayPw(Integer code) {
            for (IS_PAY_PW is_pay_pw : IS_PAY_PW.values()) {
                if (is_pay_pw.code == code)
                    return is_pay_pw;
            }
            return NEED_PW;
        }
    }


    /**
     * 支付类型
     */
    enum PAY_CH_TYPE {
        MAYCARD(0),//MyCard支付
        GOOGLEPAY(1),//谷歌支付
        APPLEPAY(2),//苹果支付
        ;

        public static PAY_CH_TYPE getPayType(int payType) {
            for (PAY_CH_TYPE pay_ch_type : PAY_CH_TYPE.values()) {
                if (pay_ch_type.code == payType)
                    return pay_ch_type;
            }
            return null;
        }

        private PAY_CH_TYPE(int code) {
            this.code = code;
        }

        private int code;

        public int getCode() {
            return code;
        }
    }

    /**
     * 币种枚举
     */
    enum CURRENCY {
        TWD,//新台币
        USD,//美元
        ;

        public static CURRENCY getCurrency(String currency) {
            if (StringUtils.isBlank(currency))
                return null;
            for (CURRENCY currency1 : CURRENCY.values()) {
                if (currency1.name().equalsIgnoreCase(currency)) {
                    return currency1;
                }
            }
            return null;
        }
    }


    /**
     * 交易模式
     */
    enum TRADE_TYPE {
        app("1"),//手遊適用
        web("2");//WEB端

        public static TRADE_TYPE getTradeType(String code) {
            if (StringUtils.isBlank(code))
                return null;
            for (TRADE_TYPE tradeType : TRADE_TYPE.values()) {
                if (tradeType.code.equalsIgnoreCase(code)) {
                    return tradeType;
                }
            }
            return null;
        }

        private TRADE_TYPE(String code) {
            this.code = code;
        }

        private String code;

        public String getCode() {
            return code;
        }
    }


}
