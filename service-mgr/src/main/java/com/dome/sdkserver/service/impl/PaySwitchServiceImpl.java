package com.dome.sdkserver.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.PaySwitch;
import com.dome.sdkserver.metadata.dao.mapper.PaySwitchMapper;
import com.dome.sdkserver.service.PaySwitchService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.view.AjaxResult;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service()
@Transactional
public class PaySwitchServiceImpl implements PaySwitchService{
    @Autowired
    private PaySwitchMapper paySwitchMapper;

    private static Logger log = LoggerFactory.getLogger(PaySwitchServiceImpl.class);

    @Autowired
    private PropertiesUtil domainConfig;

    /**
     * 查找支付开关信息
     * @param ps
     * @param begin
     * @param pageSize
     * @return
     */
    @Override
    public List<PaySwitch> query(PaySwitch ps, Integer begin, Integer pageSize) {
        return paySwitchMapper.query(ps,begin,pageSize);
    }

    @Override
    public PaySwitch queryByAppCode(String appCode) {
        return paySwitchMapper.queryByAppCode(appCode);
    }


    /**
     * 查询到支付开关的数量
     * @param ps
     * @return
     */
    @Override
    public int count(PaySwitch ps) {
        return paySwitchMapper.count(ps);
    }



    /**
     * 查询所有可以被添加游戏的code
     * @return
     */
    @Override
    public List<String> queryCanAddAppCode() {
        return paySwitchMapper.queryCanAddAppCode();
    }

    /**
     * 编辑支付开关
     * @param ps
     * @return
     */
    @Override
    @Transactional
    public AjaxResult modify(PaySwitch ps) throws Exception{
        try{
            //修改支付开关
            int modify = paySwitchMapper.modify(ps);
            String appCode = ps.getAppCode();
            String payWay = ps.getPayWay();
            Integer sta = ps.getStatus();
            if(modify > 0){  //修改才调用同步接口
                // 调用接口 http://sdkserver.domestore.cn/syncPayOptions/megerPayOptions
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("appCode",appCode));
                //支付方式 1 支付宝 2 钱宝 3 微信, 4银联
                pairs.add(new BasicNameValuePair("payWay",payWay));
                //是否支持宝券 0 支持,1不支持
                if(sta!=null){
                    //接口参数 isSupportBq不能为null
                    pairs.add(new BasicNameValuePair("isSupportBq", String.valueOf(sta)));
                }
                String response = ApiConnector.post(domainConfig.getString("domesdk.payswitch"), pairs);

                log.info("response:"+response);

                JSONObject result = JSONObject.parseObject(response);
                log.info("result:"+result);
                String errorMsg = result.getString("errorMsg");
                Integer responseCode = result.getInteger("responseCode");
                if(result.getInteger("responseCode") == 1000){
                    log.info("调用接口 http://sdkserver.domestore.cn/syncPayOptions/megerPayOptions "+"errorMsg = " + errorMsg + " , responseCode = " + responseCode);
                    return AjaxResult.success("保存成功，支付配置已修改");
                }else{
                    log.info("支付开关同步数据失败,请稍后重试! 调用接口 http://sdkserver.domestore.cn/syncPayOptions/megerPayOptions "+"errorMsg = " + errorMsg + " , responseCode = " + responseCode);
                    throw new Exception("支付开关同步数据失败,请稍后重试");
//                    return AjaxResult.failed("保存失败，请重试");
                }
            }else {
                return AjaxResult.failed();
            }
        }catch (Exception e){
            log.error("调用PaySwitchServiceImpl.modify方法, 修改支付开关失败",e);
            return AjaxResult.failed("修改支付开关失败");
        }
    }

    /**
     * 添加游戏Code
     * @param ps
     * @return
     */
    @Override
    public int insert(PaySwitch ps) {
        return paySwitchMapper.insert(ps);
    }
}
