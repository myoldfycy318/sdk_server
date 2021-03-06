package com.dome.sdkserver.metadata.entity.bq.ogp;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bq.util.MapUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hunsy on 2017/8/4.
 */
public class OgpPayEntity {

    private Logger logger = LoggerFactory.getLogger(getClass());
    //uc用户id
    @NotEmpty
    private String userId;
    //appCode，通过appCode，查询ogp游戏的key和gameId
    @NotEmpty
    private String appCode;
    //区服
    private String zoneId;
    //区服名称
    private String zoneName;
    //游戏名称
    private String gameName;
    //商品描述
    @NotEmpty
    private String content;
    //商品详情
    private String detail;
    //附加信息
    private String attach;
    //游戏方生成的订单号,在程序请求时，替代为sdk生成的订单号
    @NotEmpty
    private String cpTradeNo;
    //玩家下单时间
    @NotEmpty
    private String cpTradeTime;
    //目前就支持人民币
    private String currency = "CNY";
    //总金额
    private int totalFee = 0;

    private String sign = "";

    private String ts = "";

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getCpTradeNo() {
        return cpTradeNo;
    }

    public void setCpTradeNo(String cpTradeNo) {
        this.cpTradeNo = cpTradeNo;
    }

    public String getCpTradeTime() {
        return cpTradeTime;
    }

    public void setCpTradeTime(String cpTradeTime) {
        this.cpTradeTime = cpTradeTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }


    public String cpSignMap(boolean needSign) {
        String str = JSON.toJSONString(this);
        Map<String, String> map = JSON.parseObject(str, Map.class);
        if (!needSign) {
            map.put("totalFee", "0");
        }
        map.remove("sign");
        return MapUtil.createLinkString(map);
    }


//    public boolean isNeedSign() {
//        return needSign;
//    }
//
//    public void setNeedSign(boolean needSign) {
//        this.needSign = needSign;
//    }
//
//    public String getCpNotifyUrl() {
//        return cpNotifyUrl;
//    }
//
//    public void setCpNotifyUrl(String cpNotifyUrl) {
//        this.cpNotifyUrl = cpNotifyUrl;
//    }
//
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }


//    public String getGameId() {
//        return gameId;
//    }
//
//    public void setGameId(String gameId) {
//        this.gameId = gameId;
//    }

//    /**
//     * @return
//     */
//    public String generateSign() {
//
//        StringBuilder sb = new StringBuilder();
//
//        if (StringUtils.isNotEmpty(appCode)) {
//            sb.append("&appCode=").append(this.appCode);
//        }
//        if (StringUtils.isNotEmpty(attach)) {
//            sb.append("&attach=").append(this.attach);
//        }
//        if (StringUtils.isNotEmpty(content)) {
//            sb.append("&content=").append(this.content);
//        }
//        if (StringUtils.isNotEmpty(cpNotifyUrl)) {
//            sb.append("&cpNotifyUrl=").append(this.cpNotifyUrl);
//        }
//        if (StringUtils.isNotEmpty(cpTradeTime)) {
//            sb.append("&cpOrderTime=").append(this.cpTradeTime);
//        }
//        if (StringUtils.isNotEmpty(cpTradeNo)) {
//            sb.append("&cpTradeNo=").append(this.cpTradeNo);
//        }
//        if (StringUtils.isNotEmpty(currency)) {
//            sb.append("&currency=").append(this.currency);
//        }
//        if (StringUtils.isNotEmpty(detail)) {
//            sb.append("&detail=").append(this.detail);
//        }
//        if (StringUtils.isNotEmpty(gameName)) {
//            sb.append("&gameName=").append(this.gameName);
//        }
//        if (needSign) {
//            if (totalFee != 0) {
//                sb.append("&totalFee=").append(this.totalFee);
//            }
//        }
//
//        if (StringUtils.isNotEmpty(this.ts)) {
//            sb.append("&ts=").append(this.ts);
//        }
//
//        if (StringUtils.isNotEmpty(this.userId)) {
//            sb.append("&userId=").append(this.userId);
//        }
//
//        if (StringUtils.isNotEmpty(this.zoneId)) {
//            sb.append("&zoneId=").append(this.zoneId);
//        }
//        if (StringUtils.isNotEmpty(this.zoneName)) {
//            sb.append("&zoneName=").append(this.zoneName);
//        }
//
//        String str = sb.substring(1).toString();
//        logger.info("签名前:{}", str);
//        String sign = DigestUtils.md5Hex(str).toUpperCase();
//        logger.info("签名后:{}", sign);
//        return sign;
//    }

    public Map<String, String> getParams() {


//        //通过appCode查询获取
//        private String gameId;
//        //订单完成，回调地址
//        private String cpNotifyUrl;
//        private String key;
//        private boolean needSign = true;
        Map<String, String> map = new HashMap<>();
        map.put("user_id", this.userId);
        map.put("ts", System.currentTimeMillis() + "");
        map.put("content", this.content);
        map.put("detail", this.detail);
        map.put("attach", this.attach);
        map.put("cp_trade_no", this.cpTradeNo);
        map.put("cp_order_time", this.cpTradeTime);
        map.put("currency", this.currency);
        map.put("total_fee", this.totalFee + "");
        return map;
    }


    public static void main(String args[]) {

        System.out.print(DigestUtils.md5Hex("http://testsdkserver.domestore.cn/ogp/pay?appCode=Y0000073&content=xxx&cpOrderTime=2017-10-10 10:20:10&cpTradeNo=12312313asasd&gameName=gameName&userId=bq_000190050&zoneName=zone&sign=730890030DE470D76C3E6102A5C7CCBB").toUpperCase());
    }

}
