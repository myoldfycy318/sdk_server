package com.dome.sdkserver.metadata.entity.bq.ogp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by hunsy on 2017/9/15.
 */
public class OgpReqEntity {

    private int game_id;
    private String user_id;
    private String ts;
    private String content;
    private String detail;
    private String attach;
    private String cp_trade_no;
    private String cp_trade_time;
    private String currency = "CNY";
    private int total_fee = 0;
    private String cp_notify_url;
    private String sign;


    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
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

    public String getCp_trade_no() {
        return cp_trade_no;
    }

    public void setCp_trade_no(String cp_trade_no) {
        this.cp_trade_no = cp_trade_no;
    }

    public String getCp_trade_time() {
        return cp_trade_time;
    }

    public void setCp_trade_time(String cp_trade_time) {
        this.cp_trade_time = cp_trade_time;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getCp_notify_url() {
        return cp_notify_url;
    }

    public void setCp_notify_url(String cp_notify_url) {
        this.cp_notify_url = cp_notify_url;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private Logger logger = LoggerFactory.getLogger(getClass());


    public Map<String, String> getParams(String key) {
        Map<String, String> map = sign(key);
        logger.info("{}", JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty));
        return map;
    }

    private Map<String, String> sign(String key) {


        //attach=12123123&
        // //content=asdasd&
        // cp_notify_url=http://192.168.70.220:8080/ogp/notify&
        // cp_order_time=2017-10-10 10:20:10&
        // cp_trade_no=7DsCwH0KJRlF&
        // currency=CNY&
        // game_id=1048&
        // total_fee=1&
        // ts=1502869641766&
        // key=sAjqYbKWG2c9
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(attach)) {
            sb.append("attach").append("=").append(this.attach).append("&");
        }
        if (StringUtils.isNotEmpty(content)) {
            sb.append("content").append("=").append(this.content).append("&");
        }
        if (StringUtils.isNotEmpty(cp_notify_url)) {
            sb.append("cp_notify_url").append("=").append(this.cp_notify_url).append("&");
        }
        if (StringUtils.isNotEmpty(cp_trade_time)) {
            sb.append("cp_order_time").append("=").append(this.cp_trade_time).append("&");
        }
        if (StringUtils.isNotEmpty(cp_trade_no)) {
            sb.append("cp_trade_no").append("=").append(this.cp_trade_no).append("&");
        }
        if (StringUtils.isNotEmpty(currency)) {
            sb.append("currency").append("=").append(this.currency).append("&");
        }
        if (StringUtils.isNotEmpty(detail)) {
            sb.append("detail").append("=").append(this.detail).append("&");
        }
        sb.append("game_id").append("=").append(this.game_id).append("&");
        sb.append("total_fee").append("=").append(this.total_fee).append("&");
        this.ts = System.currentTimeMillis() + "";
        sb.append("ts").append("=").append(this.ts).append("&");
        sb.append("user_id").append("=").append(this.user_id).append("&");

        Map<String, String> map = new HashMap<>();
        map.put("game_id", this.game_id + "");
        map.put("user_id", this.user_id);
        map.put("ts", this.ts);
        map.put("content", this.content == null ? "" + this.total_fee : this.content);
        map.put("detail", this.detail == null ? "" : this.detail);
        map.put("attach", this.attach == null ? "" : this.attach);
        map.put("cp_trade_no", this.cp_trade_no);
        map.put("cp_order_time", this.cp_trade_time);
        map.put("currency", this.currency);
        map.put("total_fee", this.total_fee + "");
        map.put("cp_notify_url", this.cp_notify_url);

        String signBf = sb.append("key=").append(key).toString();
        logger.info("ogp签名前:{}", signBf);
        String sign = DigestUtils.md5Hex(signBf).toUpperCase();
        logger.info("ogp签名后:{}", sign);
        map.put("sign", sign);
        logger.info("ogp请求参数:{}", JSON.toJSONString(map));
        return map;
    }

}
