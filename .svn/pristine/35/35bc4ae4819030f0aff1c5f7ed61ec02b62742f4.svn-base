package com.dome.sdkserver.metadata.entity.bq.webgame;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.*;

/**
 * 网页支付请求实体
 * Created by hunsy on 2017/11/3.
 */
public class WebPayEntity implements Serializable{


    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 游戏编码，通过游戏编码获取游戏名称等信息。
     * 参与签名
     */
    @NotEmpty(message = "appCode not empty")
    private String appCode;

    /**
     * 区服id。通过游戏编码和区服id获取区服名称。
     * 参与签名
     */
    @NotEmpty(message = "zoneId not empty")
    private String zoneId;

    /**
     * 角色id。
     * 参与签名
     */
    @NotEmpty(message = "roleId not empty")
    private String roleId;

    /**
     * 冰穹uc账号的Id，通过这个id获取ogp用户的id。
     * 参与签名
     */
    @NotEmpty(message = "userId not empty")
    private String userId;

    /**
     * 游戏方订单号.
     * 参与签名
     */
    @NotEmpty(message = "cpTradeNo not empty")
    private String cpTradeNo;

    /**
     * 游戏方订单生成时间
     * 参与签名
     */
    @NotEmpty(message = "cpTradeTime not empty")
    private String cpTradeTime;

    /**
     * 交易金额。正整数。
     * 参与签名
     */
    @NotEmpty(message = "totalFee not empty")
    @Min(value = 0, message = "total must gt 0 int value")
    private int totalFee = 0;

    /**
     * 渠道号。
     * 参与签名
     */
    @NotEmpty(message = "channelCode not empty")
    private String channelCode;

    /**
     * 交易描述内容
     * 不参与签名
     */
    @NotEmpty(message = "content not empty")
    private String content;

    /**
     * 物品详情
     * 不参与签名
     */
    private String detail;

    /**
     * 交易附加信息
     * 不参与签名
     */
    private String attach;

    //单位
    private String currency = "CNY";

    /**
     * 签名时间戳
     * 参与签名
     */
    private String ts;

    /**
     * 签名
     */
    @NotEmpty(message = "sign not empty")
    private String sign;


    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    /**
     * 获取签名信息
     *
     * @return
     */
    public String sign(String appkey) {

        Map<String, String> map = new HashMap<>();
        map.put("appCode", this.appCode);
        map.put("zoneId", this.zoneId);
        map.put("userId", this.userId);
        map.put("roleId", this.roleId);
        map.put("cpTradeNo", this.cpTradeNo);
        map.put("cpTradeTime", this.cpTradeTime);
        map.put("totalFee", this.totalFee + "");
        map.put("channelCode", this.channelCode);
        map.put("ts", this.ts);
        List<String> ls = new ArrayList<>(map.keySet());
        Collections.sort(ls);
        StringBuilder sb = new StringBuilder();
        for (String key : ls) {
            sb.append(key)
                    .append("=")
                    .append(map.get(key))
                    .append("&");
        }

        sb.append("key").append("=").append(appkey);
        logger.info("签名前:{}", sb.toString());
        String newSign = DigestUtils.md5Hex(sb.toString());
        logger.info("签名后:{}", newSign);
        return newSign;
    }


}
