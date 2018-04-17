package com.dome.sdkserver.shangsu;

/**
 * TradeItem
 * 商肃交易行项数据
 *
 * @author Zhang ShanMin
 * @date 2016/5/16
 * @time 0:28
 */
public class TradeItem {

    //行项流水,要求业务侧保证唯一性
    private String itemTradeNo;
    //1：人民币，2：宝券
    private String payType;
    //金额
    private Long amount;
    //明细业务类型
    private String itemBusiType;

    public TradeItem() {

    }

    private TradeItem(Builder b) {
        this.itemTradeNo = b.itemTradeNo;
        this.payType = b.payType;
        this.amount = b.amount;
        this.itemBusiType = b.itemBusiType;
    }

    public static class Builder {

        private String itemTradeNo;
        private String payType;
        private Long amount;
        private String itemBusiType;

        public Builder() {
        }

        public Builder itemTradeNo(String itemTradeNo) {
            this.itemTradeNo = itemTradeNo;
            return this;
        }

        public Builder payType(String payType) {
            this.payType = payType;
            return this;
        }

        public Builder amount(Long amount) {
            this.amount = amount;
            return this;
        }

        public Builder itemBusiType(String itemBusiType) {
            this.itemBusiType = itemBusiType;
            return this;
        }

        public TradeItem build() {
            return new TradeItem(this);
        }
    }


    public String getItemTradeNo() {
        return itemTradeNo;
    }

    public void setItemTradeNo(String itemTradeNo) {
        this.itemTradeNo = itemTradeNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getItemBusiType() {
        return itemBusiType;
    }

    public void setItemBusiType(String itemBusiType) {
        this.itemBusiType = itemBusiType;
    }
}
