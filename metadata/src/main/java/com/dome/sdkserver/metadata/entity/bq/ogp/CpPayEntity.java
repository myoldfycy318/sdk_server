package com.dome.sdkserver.metadata.entity.bq.ogp;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by hunsy on 2017/9/15.
 */
public class CpPayEntity {

    /**
     * 应用码
     */
    @NotEmpty
    private String appCode;

    private String gameName;

    @NotEmpty
    private String zoneId;
    //区服名称
    private String zoneName;

    private String roleId;
    @NotEmpty
    private String userId;

    @NotEmpty
    private String content;

    private String detail;

    private String attach;

    @NotEmpty
    private String cpTradeNo;

    @NotEmpty
    private String cpTradeTime;

    private String currency = "CNY";

    @NotEmpty
    private int totalFee = 0;

    private String channelCode;

    private String ts;

    private String sign;

    //1-支付宝，2 -钱宝 3-银联，4-微信
    private int payType;
    //交易金额
    private int tradeFee;

    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getTradeFee() {
        return tradeFee;
    }

    public void setTradeFee(int tradeFee) {
        this.tradeFee = tradeFee;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @param payKey 支付key
     * @return
     */
    public String cpSign(String payKey) {

        Map<String, String> map = new HashMap<>();

        map.put("appCode", this.appCode);
        map.put("zoneId", this.zoneId);
        map.put("userId", this.userId);
        map.put("roleId", this.roleId);
        map.put("cpTradeNo", this.cpTradeNo);
        map.put("totalFee", this.totalFee + "");
        map.put("channelCode", this.channelCode);
        map.put("ts", this.ts);
        List<String> ls = new ArrayList<>(map.keySet());
        Collections.sort(ls);
        StringBuilder sb = new StringBuilder();
        for (String key : ls) {
            sb.append(key).append("=").append(map.get(key)).append("&");
        }

        String signBf = sb.append(payKey).toString();
        logger.info("cp签名前:{}", signBf);
        String sign = DigestUtils.md5Hex(signBf);
        logger.info("cp签名后:{}", sign);
        return sign;
    }

    public boolean validate() {
        if (StringUtils.isEmpty(appCode)) {
            return false;
        }

        if (StringUtils.isEmpty(content)) {
            return false;
        }

        if (StringUtils.isEmpty(cpTradeNo)) {
            return false;
        }

        if (StringUtils.isEmpty(roleId)) {
            return false;
        }

        if (StringUtils.isEmpty(sign)) {
            return false;
        }
        if (totalFee < 0) {
            return false;
        }

        if (StringUtils.isEmpty(ts)) {
            return false;
        }

        if (StringUtils.isEmpty(userId)) {
            return false;
        }
        if (StringUtils.isEmpty(zoneId)) {
            return false;
        }

        if (StringUtils.isEmpty(channelCode)) {
            return false;
        }
        return true;
    }


    public OgpReqEntity getOgpReqEntity() {

        OgpReqEntity reqEntity = new OgpReqEntity();
        reqEntity.setContent(this.content);
        reqEntity.setDetail(this.detail);
        reqEntity.setAttach(this.attach);
        reqEntity.setTotal_fee(this.totalFee);
        reqEntity.setCp_trade_no(this.cpTradeNo);

        reqEntity.setCp_trade_time( new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        reqEntity.setCurrency(this.currency);
        return reqEntity;
    }


    private static List<String> obj2StrIgnoreParams = new ArrayList<String>() {{
        add("serialVersionUID");
        add("tradeFee");
        add("logger");
        add("obj2StrIgnoreParams");
    }};

    public String obj2String() {
        StringBuilder sb = new StringBuilder();
        try {
            Field[] field = this.getClass().getDeclaredFields();
            for (int i = 0; i < field.length; i++) {
                // 单一安全性检查
                field[i].setAccessible(true);
                if (obj2StrIgnoreParams.contains(field[i].getName()) || field[i].get(this) == null) continue;
                sb.append("&").append(field[i].getName()).append("=").append(URLEncoder.encode(field[i].get(this).toString(),"UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString().substring(1);
    }


}
