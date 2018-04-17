package com.dome.sdkserver.metadata.entity.bq.award;

/**
 * CouponReqVo
 *
 * @author Zhang ShanMin
 * @date 2016/12/7
 * @time 15:59
 */
public class CouponReqVo {

    //用户ID
    private String userId;
    private String signCode;
    //签名算法，目前必须传递“RSA”
    private String signType;
    //业务类型
    private String busiType;
    private String outTradeNo;
    //手续费,人民币，单位：分
    private Long feeAmount;
    //该参数需要做Base64(字符集UTF-8)转码
    private String tradeDesc;
    //终端 1：PC，2：IOS，3：Android
    private Integer terminal;
    //返劵数量,单位：分
    private Long amount;
    //发劵来源
    private String source;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Long getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Long feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getTradeDesc() {
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
