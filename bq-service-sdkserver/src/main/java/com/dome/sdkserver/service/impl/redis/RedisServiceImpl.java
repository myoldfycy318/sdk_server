/**
 *
 */
package com.dome.sdkserver.service.impl.redis;

import com.dome.sdkserver.metadata.dao.mapper.bq.pay.AppInfoMapper;
import com.dome.sdkserver.metadata.dao.mapper.bq.pay.GlobalVarMapper;
import com.dome.sdkserver.service.redis.RedisService;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author mazhongmin
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Resource(name = "appInfoMapper")
    AppInfoMapper appInfoMapper;

    @Resource
    private GlobalVarMapper globalVarMapper;

    @Resource
    private RedisUtil redisUtil;

    /*@Override
    public AppInfoEntity getAppInfo(String appCode) {
        String merchantJson = redisUtil.get(APPINFO_PREFIX + appCode);
        AppInfoEntity appInfoEntity = null;
        if (StringUtils.isBlank(merchantJson)) {
            appInfoEntity = getAppInfoEntity(appCode);//获取游戏应用信息
            if (appInfoEntity != null) {
                redisUtil.set(APPINFO_PREFIX + appCode, JSON.toJSONString(appInfoEntity));
                redisUtil.expire(APPINFO_PREFIX + appCode, 24 * 60 * 60);
            }
        } else {
            appInfoEntity = JSONObject.parseObject(merchantJson, AppInfoEntity.class);
        }
        return appInfoEntity;
    }*/

   /* *//**
     * 获取游戏应用信息
     *
     * @param
     * @return
     *//*
    private AppInfoEntity getAppInfoEntity(String appCode) {
        AppInfoEntity appInfoEntity = null;
        if (appCode.indexOf("H") > -1) {
            appInfoEntity = appInfoMapper.queryH5GameAppInfo(appCode);
        } else if (appCode.indexOf("Y") > -1) {
            appInfoEntity = appInfoMapper.queryWebGameAppInfo(appCode);
        } else {
            appInfoEntity = appInfoMapper.getAppInfoByAppCode(appCode);
        }
        //todo 区分页游、H5、手游
       // appInfoEntity = appInfoMapper.getAppInfoByAppCode(appCode);
        return appInfoEntity;
    }*/

    @Override
    public String getGlobalVarByType(String varType) {

        String value = redisUtil.get(GLOBAL_VAR + varType);
        if (StringUtils.isBlank(value)) {
            //获取数据库中的信息
            value = globalVarMapper.getGlobalVarByType(varType);
            if (StringUtils.isNotBlank(value)) {
                //放入缓存
                redisUtil.set(GLOBAL_VAR, value);
            }
        }
        return value;

    }

    /**
     * 刷新缓存AppInfo
     *
     * @param appCode
     */
    @Override
    public boolean refreshAppInfo(String appCode) {
        boolean result = false;
        //TODO
        //获取数据库中的商户信息
        /*AppInfoEntity appInfoEntity = getAppInfoEntity(appCode);
        if (appInfoEntity != null) {
            redisUtil.setex(APPINFO_PREFIX + appCode, 24 * 60 * 60, JSON.toJSONString(appInfoEntity));
            result = true;
        }*/
        return result;
    }
}
