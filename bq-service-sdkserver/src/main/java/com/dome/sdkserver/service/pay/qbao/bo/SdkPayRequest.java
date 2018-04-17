/**
 *
 */
package com.dome.sdkserver.service.pay.qbao.bo;

/**
 * 支付获取用户信息请求对象
 */
public class SdkPayRequest {

    /**
     * 交易类型*
     */
    private String transType;
    /**
     * 应用编码*
     */
    private String appCode;
    /**
     * 业务流水*
     */
    private String orderNo;
    /**
     * 交易人id*
     */
    private Long userId;
    /**
     * 计费点编码*
     */
    private String billingCode;
    /**
     * 应用渠道*
     */
    private Integer appSource;
    /**
     * 交易简介*
     */
    private String transIntro;
    /**
     * 异步通知*
     */
    private String payCallbackUrl;
    /**
     * 签文*
     */
    private String signCode;
    /**
     * 签名方式*
     */
    private String signType;
    /**
     * 请求ip*
     */
    private String ip;
    //渠道号
    private String channelCode;
    //支付来源  pc,wap,app,NewBusi:新业务混合支付
    private String payOrigin;
    private String buyerId;
    private String chargePointCode;
    private Double chargePointAmount;
    private String chargePointName;
    //页游区服Id
    private String zoneId;
    //游戏方拓展字段1
    private String p1;
    //游戏方拓展字段2
    private String p2;
    //交易金额(单位为：分)
    private Long transAmount;
    //人民币金额(单位为：分)
    private Long rmbAmount;
    //宝券金额(单位为：分)
    private Long bqAmount;
    //手续费（人民币，单位：分）
    private Long feeAmount;
    //钱宝交易密码
    private String transPassWord;
    //支付方式(0：组合支付，1：人民币，2：宝券)
    private Integer payType;
    //是否需要密码支付(0:不需要密码，1:需要密码，默认：1)
    private Integer isNeedPw = 1;
    //系统类型:IOS|AD|WEB|WAP
    private String sysType;
    //游戏角色id
    private String roleId;
    //外部订单号
    private String outOrderNo = "0000";
    //游戏区服名
    private String zoneName;

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
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

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAppSource() {
        return appSource;
    }

    public void setAppSource(Integer appSource) {
        this.appSource = appSource;
    }

    public String getTransIntro() {
        return transIntro;
    }

    public void setTransIntro(String transIntro) {
        this.transIntro = transIntro;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBillingCode() {
        return billingCode;
    }

    public void setBillingCode(String billingCode) {
        this.billingCode = billingCode;
    }

    public String getPayCallbackUrl() {
        return payCallbackUrl;
    }

    public void setPayCallbackUrl(String payCallbackUrl) {
        this.payCallbackUrl = payCallbackUrl;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getPayOrigin() {
        return payOrigin;
    }

    public void setPayOrigin(String payOrigin) {
        this.payOrigin = payOrigin;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getChargePointCode() {
        return chargePointCode;
    }

    public void setChargePointCode(String chargePointCode) {
        this.chargePointCode = chargePointCode;
    }

    public Double getChargePointAmount() {
        return chargePointAmount;
    }

    public void setChargePointAmount(Double chargePointAmount) {
        this.chargePointAmount = chargePointAmount;
    }

    public String getChargePointName() {
        return chargePointName;
    }

    public void setChargePointName(String chargePointName) {
        this.chargePointName = chargePointName;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public Long getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Long transAmount) {
        this.transAmount = transAmount;
    }

    public Long getRmbAmount() {
        return rmbAmount;
    }

    public void setRmbAmount(Long rmbAmount) {
        this.rmbAmount = rmbAmount;
    }

    public Long getBqAmount() {
        return bqAmount;
    }

    public void setBqAmount(Long bqAmount) {
        this.bqAmount = bqAmount;
    }

    public Long getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Long feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getTransPassWord() {
        return transPassWord;
    }

    public void setTransPassWord(String transPassWord) {
        this.transPassWord = transPassWord;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getIsNeedPw() {
        return isNeedPw;
    }

    public void setIsNeedPw(Integer isNeedPw) {
        this.isNeedPw = isNeedPw;
    }
}
