package com.dome.sdkserver.service.impl.pay;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.enumeration.PayTypeEnum;
import com.dome.sdkserver.bq.enumeration.SysTypeEnum;
import com.dome.sdkserver.bq.enumeration.ThirdPartyEnum;
import com.dome.sdkserver.constants.RedisKeyEnum;
import com.dome.sdkserver.metadata.dao.mapper.bq.pay.PayOptionsMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.PayOptions;
import com.dome.sdkserver.metadata.entity.bq.pay.PayType;
import com.dome.sdkserver.metadata.entity.bq.pay.PayTypeCondition;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.login.UserLoginService;
import com.dome.sdkserver.service.pay.PayOptionsService;
import com.dome.sdkserver.service.pay.qbao.bo.UserInfo;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PayOptionsServiceImpl
 *
 * @author Zhang ShanMin
 * @date 2016/10/13
 * @time 11:50
 */
@Service("payOptionsService")
public class PayOptionsServiceImpl implements PayOptionsService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "payOptionsMapper")
    private PayOptionsMapper payOptionsMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    PropertiesUtil payConfig;
    @Resource(name = "userLoginService")
    private UserLoginService userLoginService;

    /**
     * merge 支付选项
     *
     * @param payOptions
     * @return
     */
    @Override
    public boolean megerPayOptions(PayOptions payOptions) {
        Integer flag = payOptionsMapper.isExistAppCode(payOptions);
        boolean result = false;
        if (flag >= 1) {
            result = payOptionsMapper.updateByAppCode(payOptions);
        } else {
            result = payOptionsMapper.insert(payOptions);
        }
        if (result) {
            //因新开放平台同一个appCode和有多个channelCode,所以redisKey由原来appCode, 修改为appCode:channelCode
            if (!StringUtils.isBlank(payOptions.getChannelCode())) {
                //新开放平台
                redisUtil.setex(RedisKeyEnum.BQ_GAME_PAY_TYPE.getKey() + payOptions.getAppCode() + ":" + payOptions.getChannelCode(),
                        RedisKeyEnum.BQ_GAME_PAY_TYPE.getExpireTime(), JSONObject.toJSONString(payOptions));
            } else {
                //老开放平台
                redisUtil.setex(RedisKeyEnum.BQ_GAME_PAY_TYPE.getKey() + payOptions.getAppCode(),
                        RedisKeyEnum.BQ_GAME_PAY_TYPE.getExpireTime(), JSONObject.toJSONString(payOptions));
            }
        }
        return result;
    }

    @Override
    public boolean delPayOptions(PayOptions payOptions) {
        return payOptionsMapper.delByAppCode(payOptions);
    }

    /**
     * 支付开关选项列表
     *
     * @param payOptions
     * @return
     */
    @Override
    public PayOptions queryPayOptions(PayOptions payOptions) {
        PayOptions _payOptions = null;
        //因新开放平台同一个appCode和有多个channelCode,所以redisKey由原来appCode, 修改为appCode:channelCode
        String json = redisUtil.get(RedisKeyEnum.BQ_GAME_PAY_TYPE.getKey() + payOptions.getAppCode() + ":" + payOptions.getChannelCode());
        //json为空是因为:1.老开放平台查询时由于channelCode为null所以无法查询到. 2.该redisKey(appCode:channelCode)不存在
        if (StringUtils.isNotBlank(json)) {
            _payOptions = JSONObject.parseObject(json, PayOptions.class);
            if (null != _payOptions) {
                return _payOptions;
            }
        }
        _payOptions = payOptionsMapper.queryPayOptions(payOptions);
        //设置默认支持支付列表(若redis、数据中都没有游戏支持的默认支付方式，则走默认的支付方式)
        if (_payOptions == null) {
            _payOptions = new PayOptions();
            _payOptions.setAppCode(payOptions.getAppCode());
            _payOptions.setChannelCode(payOptions.getChannelCode());
            //1:支付宝支付,2:钱宝支付,3:银联支付,4:微信支付
            String gamePayWap = payConfig.getString("sdk.game.paywap", "1,2,4");
            //是否支持宝券 0 支持 1 不支持
            Integer isSupportBq = payConfig.getInt("sdk.qbao.pay.issupportbq", "1");
            _payOptions.setPayWay(gamePayWap);
            _payOptions.setIsSupportBq(isSupportBq);
        }
        redisUtil.setex(RedisKeyEnum.BQ_GAME_PAY_TYPE.getKey() + payOptions.getAppCode() + ":" + payOptions.getChannelCode(),
                RedisKeyEnum.BQ_GAME_PAY_TYPE.getExpireTime(), JSONObject.toJSONString(_payOptions));
        return _payOptions;
    }

    /**
     * 获取支持的支付列表
     *
     * @return
     */
    @Override
    public List<PayType> queryPayTypeList( PayTypeCondition condition) {
        if (condition.getAppCode() == null)
            return Collections.emptyList();
        PayOptions _payOptions = new PayOptions();
        _payOptions.setAppCode(condition.getAppCode());
        //设置channelCode
        _payOptions.setChannelCode(condition.getChannelCode());
        //获取支付开关选项列表
        _payOptions = queryPayOptions(_payOptions);
        if (_payOptions == null)
            return Collections.emptyList();
        String payWay = _payOptions.getPayWay();
        if (StringUtils.isBlank(payWay))
            return Collections.emptyList();
        List<PayType> payTypeList = new ArrayList<>();
        PayType payType = null;
        for (String _payType : payWay.split(",")) {
            if (!_payType.matches("\\d+")) continue;
            PayTypeEnum typeEnum = PayTypeEnum.getTypeEnum(Integer.valueOf(_payType));
            if (typeEnum == null) continue;
            payType = new PayType(typeEnum.getCode(), typeEnum.name());
            if (typeEnum == PayTypeEnum.微信支付 && SysTypeEnum.WEB.name().equals(condition.getSysType())){
                continue; //屏蔽H5游戏 钱宝渠道  pc端 微信支付 2017-11-29
            }
            if (typeEnum != PayTypeEnum.钱宝支付) {
                payTypeList.add(payType);
                continue;
            }
            if (!BqSdkConstants.channelCodeQbao.equals(condition.getChannelCode())) {
                continue;
            }
            if (StringUtils.isBlank(condition.getUserId())) {
                payTypeList.add(payType);
            }
            if (StringUtils.isNotBlank(condition.getUserId()) && condition.getUserId().indexOf("bq_") > -1) {
                UserInfo userInfo = userLoginService.getUserByUserId(condition.getUserId());
                if (userInfo != null && !StringUtils.isBlank(userInfo.getThirdId()) && ThirdPartyEnum.QBAO.getThirdId().equals(userInfo.getThirdId())) {
                    payTypeList.add(payType);
                }
            }
        }
        return payTypeList;
    }

    /**
     * 是否支持钱宝宝劵支付
     *
     * @param appCode
     * @return true；支付，false:不支持
     */
    public boolean isSupportBq(String appCode) {
        if (appCode == null)
            return false;
        PayOptions _payOptions = new PayOptions();
        _payOptions.setAppCode(appCode);
        _payOptions = queryPayOptions(_payOptions);
        if (_payOptions == null)
            return false;
        String payWay = _payOptions.getPayWay();
        if (StringUtils.isBlank(payWay))
            return false;
        for (String _payType : payWay.split(",")) {
            if (!_payType.matches("\\d+"))
                continue;
            PayTypeEnum typeEnum = PayTypeEnum.getTypeEnum(Integer.valueOf(_payType));
            if (typeEnum == null || typeEnum != PayTypeEnum.钱宝支付)
                continue;
            if (_payOptions.getIsSupportBq() == 0)//支付钱宝宝劵支付
                return true;
        }
        return false;
    }

    /**
     * 游戏充值中心获取游戏信息
     *
     * @param appCode
     * @return
     */
    @Override
    public JSONObject queryGameInfo(String appCode) {
        JSONObject jsonObject = null;
        String url = payConfig.getString("passport.recharge.game.url");
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("appCode", appCode));
        String response = ApiConnector.post(url, pairs);
        logger.info("游戏充值中心获取游戏信息,url:{},请求参数:{},响应结果:{}", url, JSONObject.toJSONString(pairs), response);
        if (StringUtils.isNotBlank(response) && (jsonObject = JSONObject.parseObject(response)) != null
                && "1000".equals(jsonObject.getString("responseCode")) && jsonObject.getJSONObject("data") != null) {
            return jsonObject.getJSONObject("data");
        }
        return null;
    }

    /**
     * 游戏充值中心获取cp游戏区服信息
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject queryGameZooServers(JSONObject jsonObject) {
        JSONObject jsonObj = null;
        String url = jsonObject.getString("districtUrl");
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("appCode", jsonObject.getString("appCode")));
        String response = ApiConnector.post(url, pairs);
        logger.info("游戏充值中心获取cp游戏区服信息,url:{},请求参数:{},响应结果:{}", url, JSONObject.toJSONString(pairs), response);
        if (StringUtils.isNotBlank(response) && (jsonObj = JSONObject.parseObject(response)) != null)
            return jsonObj;
        return null;
    }
}
