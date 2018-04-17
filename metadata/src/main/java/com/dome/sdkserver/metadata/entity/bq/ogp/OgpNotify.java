package com.dome.sdkserver.metadata.entity.bq.ogp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by hunsy on 2017/8/14.
 */
public class OgpNotify {

    private String game_id;         //":"10",
    private String ts;              //":"1488349843492",
    private String sign;            //":"7F8929379BD13AEC5CB0F9C5E54585AF",
    private String ogp_trade_no;    //":73716,
    private String cp_trade_no;     //":"0000001219246853",
    private int total_fee;          //":1,
    private String currency;        //":"CNY",
    private String time_end;        //":"20170228135849", " +
    private String attach;          //":"Hello World",
    private String trade_status;    //":" SUCCESS"


    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
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

    public String getOgp_trade_no() {
        return ogp_trade_no;
    }

    public void setOgp_trade_no(String ogp_trade_no) {
        this.ogp_trade_no = ogp_trade_no;
    }

    public String getCp_trade_no() {
        return cp_trade_no;
    }

    public void setCp_trade_no(String cp_trade_no) {
        this.cp_trade_no = cp_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    /**
     * 生成签名
     *
     * @return
     */
    public String generateSign(String key) {
        String str = JSON.toJSONString(this);
        Map<String, String> map = JSON.parseObject(str, Map.class, Feature.SortFeidFastMatch);
        map.remove("sign");
        List<String> ls = new ArrayList<>(map.keySet());
        Collections.sort(ls);
        StringBuilder sb = new StringBuilder();
        for (String mkey : ls) {
            Object val = map.get(mkey);
            if (val instanceof Integer) {
                val = String.valueOf(val);
                sb.append(mkey).append("=").append(val).append("&");
            } else {
                if (StringUtils.isNotEmpty(String.valueOf(val))) {
                    sb.append(mkey).append("=").append(val).append("&");
                } else {
                    map.remove(mkey);
                }
            }
        }
        return DigestUtils.md5Hex(sb.toString() + "key=" + key).toUpperCase();
    }


}
