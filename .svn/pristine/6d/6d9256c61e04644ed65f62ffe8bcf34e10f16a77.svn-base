package com.dome.sdkserver.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.security.ras.RSA;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.view.AjaxResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by heyajun on 2017/6/14.
 */
@Component("snyAppInfoListener")
public class SnyAppInfoListener implements MessageListener {

    private Logger log = LoggerFactory.getLogger(SnyAppInfoListener.class);

    private List<String> snyUrlList = new ArrayList<String>();

    //同步应用数据接口RSA公钥
    private final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTcRU3e1bqkUmGO+ZX6lEA5hAZq3i1b1kEgou+l2PCqfbrN3n6EXRZoSUiGbQ526hifos/nd7zXdvNCXeTmHSg4xm7PuqQDzAoau6lSU/MGQCh6A+Oo2q66m6oQ/6AObbHCFbmBb982TbSKbjoGSXzB5cq39bO8Ciah1pHnXEkdQIDAQAB";
    //同步应用数据RSA私钥
    private final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANNxFTd7VuqRSYY75lfqUQDmEBmreLVvWQSCi76XY8Kp9us3efoRdFmhJSIZtDnbqGJ+iz+d3vNd280Jd5OYdKDjGbs+6pAPMChq7qVJT8wZAKHoD46jarrqbqhD/oA5tscIVuYFv3zZNtIpuOgZJfMHlyrf1s7wKJqHWkedcSR1AgMBAAECgYAWlyxnIxxkL2Wlm9y+Y+CHsgziUTmyWNUv8CqyEEauU6537xNvLlI6CUeIXyqsS9WvwElsYJejdNG5FFvcPxumqxec2pwMa10ntbX1IpqAZFicF5XdQJh3dqoau4OHn/Ompd5t7Z3CiSQvoRVOIeGixlHx/0WKBnp0tfFMazFEwQJBAPPsEO0ZFBLiKfl2d2zMD7fQm3PKErW9mfcmHN8ySR/fmpir8GN3KdDqNhYqcDXu59BM40ErM9RqWGNyr/EP8q0CQQDd6UxZiUZ7VURQ8Tz6JGvqG8Egnxna5OsqEUlOMYVJiuKsd0UK7nOvW0dtiRYBydlGeSOsJEhoI1eyWI19z/npAkAUyWxT2ExGo7FyCe95fRZl0sg8oN7hUAwb7MqPFsj2h2a0VgrBXzlYDW2Yi/tODVMCBFtlhVoAYyOvLenigDptAkEA2MgN0/HsrpMvd7sSjHKEEWEnm0yRn2q96IyWhhKOWLealEy3X+RtksKi1nhyzTB4VwYEOSgAq+rKwukK3/sJ8QJARfiKPHFCubVaBzz0Jas+elmGKNytlFN9AA0V8yw94sT/P1G6woywEmttffeNzLHO0fLTtE71d4oo9UOB/dMgoA==";

    @Override
    public void onMessage(Message message) {
        try {
            String body = new String(message.getBody(),"utf-8");
            log.info("消息队列-->通用应用信息同步,body:{}",body);
            //获取同步接口列表
            if(CollectionUtils.isEmpty(snyUrlList)){
                log.info("当前没有需要同步的接口");
                return;
            }
            Map<String, String> data = JSONObject.parseObject(body, Map.class);


            //获取body中的参数,加签
            String sign = RSA.sign(body, privateKey);

            for(String url : snyUrlList){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("body",body));
                if (url.startsWith("http://passportba.domegame.cn") || url.startsWith("https:passportba.domegame.cn")) {
                    pairs.add(new BasicNameValuePair("sign",sign));
                    log.info("同步数据至passportba消息队列sign>>>>"+sign);
                }
                //调用接口返回格式样式{"data":null,"errorCode":0,"errorMsg":"","responseCode":1000}
                String response = ApiConnector.post(url, pairs);
                //防止一个接口没有相应导致其他接口无法进行
                if(!response.startsWith("{") || !response.endsWith("}")){
                    log.info("{}接口返回结果:response:{}", url, response);
                    continue;
                }
                JSONObject jsonObject = JSONObject.parseObject(response);
                int responseCode = jsonObject.getIntValue("responseCode");
                if(responseCode != AjaxResult.CODE_SUCCESS){
                    log.error("同步数据至{}接口失败,接口返回信息为:{}", url, response);
                    continue;
                }
            }

        } catch (Exception e) {
            log.error("开放平台上下架同步应用信息操作处理失败",e);
        }
    }


    public void setSnyUrlList(List<String> snyUrlList) {
        this.snyUrlList = snyUrlList;
    }

}
