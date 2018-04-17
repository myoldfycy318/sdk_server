package com.dome.sdkserver.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dome.sdkserver.bo.CallbackAudit;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;
import com.dome.sdkserver.metadata.dao.mapper.CallbackAuditMapper;
import com.dome.sdkserver.metadata.dao.mapper.MerchantAppMapper;
import com.dome.sdkserver.metadata.dao.mapper.h5.H5GameMapper;
import com.dome.sdkserver.metadata.dao.mapper.yeyou.YeyouGameMapper;
import com.dome.sdkserver.metadata.entity.h5.H5Game;
import com.dome.sdkserver.metadata.entity.yeyou.YeyouGame;
import com.dome.sdkserver.service.CallbackAuditService;
import com.dome.sdkserver.service.MerchantAppAuditService;
import com.dome.sdkserver.service.OpenSdkSynDataService;
import com.dome.sdkserver.util.business.GameUtils;

@Service
@Transactional
public class CallbackAuditServiceImpl implements CallbackAuditService{
    @Resource
    private CallbackAuditMapper callbackAuditMapper;


    //手游
    @Resource
    private MerchantAppMapper merchantAppMapper;

    //页游
    @Resource
    private YeyouGameMapper yeyouGameMapper;

    //H5
    @Resource
    private H5GameMapper h5GameMapper;

    @Resource
    private MerchantAppAuditService merchantAppAuditService;
    
    @Resource
	private OpenSdkSynDataService openSdkSynDataServiceImpl;

    @Override
    public List<CallbackAudit> queryCallbackAudit(CallbackAudit ca ,Integer begin ,Integer pageSize) {
        return callbackAuditMapper.queryCallbackAudit(ca ,begin ,pageSize);
    }

    @Override
    public int countCallbackAudit(CallbackAudit ca) {
        return callbackAuditMapper.countCallbackAudit(ca);
    }

    @Override
    public CallbackAudit queryById(Integer id) {
        return callbackAuditMapper.queryByIdOrAppCode(null, id);
    }

    @Override
    public CallbackAudit queryByAppCode(String appCode) {
        return callbackAuditMapper.queryByIdOrAppCode(appCode, null);
    }


    @Override
    public void updateStatusAndCallbackUrl(CallbackAudit callbackAudit,boolean status) {
        CallbackAudit ca = new CallbackAudit();
        ca.setAppCode(callbackAudit.getAppCode());
        if(status){
            ca.setStatus(1);//成功
        }else {
            ca.setStatus(0);//失败
        }
        callbackAuditMapper.updateByAppCode(ca);
        //更新回调地址
        updateUrl(callbackAudit);
        synCallback(callbackAudit);//测试环境同步
    }
    
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    private void synCallback(CallbackAudit callbackAudit)
    {
    	openSdkSynDataServiceImpl.synCallback(OpenSdkSynDataService.TEST_DB, callbackAudit);
    }

    @Override
    public void updateDelFlagAndCallbackUrl(CallbackAudit callbackAudit) {
        callbackAudit.setDelFlag(1);
        //更新记录为删除状态
        callbackAuditMapper.updateByAppCode(callbackAudit);
    }

    @Override
    public Object queryAllByAppCode(String appCode) {
        Object app = null;
        //根据appCode查出MerchantAppInfo对象
        GameTypeEnum em = GameUtils.analyseGameType(appCode);
        if(GameTypeEnum.h5game == em){
            //H5  gameUrl是表domesdk_h5_game中game_url字段;
            H5Game h5Game = (H5Game)h5GameMapper.select(appCode);

            app = h5Game;
        }else if(GameTypeEnum.yeyougame == em){
            //页游
            YeyouGame yeyouGame = (YeyouGame)yeyouGameMapper.select(appCode);
            MerchantAppInfo appInfo = new MerchantAppInfo();
            appInfo.setAppCode(appCode);
            appInfo.setAppName(yeyouGame.getAppName());
            //appType为空
            appInfo.setPayCallBackUrl(yeyouGame.getPayCallBackUrl());

            appInfo.setLoginCallBackUrl(yeyouGame.getLoginCallBackUrl());

            appInfo.setStatus(yeyouGame.getStatus());
            appInfo.setMerchantInfoId(yeyouGame.getMerchantInfoId());
            String channelCode =  merchantAppAuditService.getAppChannel(appCode, yeyouGame.getStatus());
            //没有channelCode传空串
            if(StringUtils.isEmpty(channelCode)){
                channelCode = "";
            }
            appInfo.setShelfChannel(channelCode);
            app = appInfo;
        }else{
            //手游
            MerchantAppInfo tApp=merchantAppMapper.queryApp(appCode);
            String channelCode =  merchantAppAuditService.getAppChannel(appCode, tApp.getStatus());
            if(StringUtils.isEmpty(channelCode)){
                channelCode = "";
            }
            tApp.setShelfChannel(channelCode);
            app=tApp;
        }
        return app;
    }

    private Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 根据appCode 判断是手游,页游,还是H5并修改相应的回调地址
     * @param callbackAudit
     * @return
     */
    public void updateUrl(CallbackAudit callbackAudit){
        log.info("callbaclAudit:{}", JSONObject.toJSONString(callbackAudit));
        String appCode = callbackAudit.getAppCode();
        GameTypeEnum en = GameUtils.analyseGameType(appCode);
        int number = 0;
        if(GameTypeEnum.mobilegame == en){
            //手游
            MerchantAppInfo app = new MerchantAppInfo();
            app.setAppCode(appCode);
            app.setPayCallBackUrl(callbackAudit.getPayCallbackUrl());
            app.setLoginCallBackUrl(callbackAudit.getLoginCallbackUrl());
            app.setTestPayCallBackUrl(callbackAudit.getTestPayCallbackUrl());
            app.setTestLoginCallBackUrl(callbackAudit.getTestLoginCallbackUrl());
            app.setRegistCallBackUrl(callbackAudit.getRegistCallbackUrl());
            app.setTestRegistCallBackUrl(callbackAudit.getTestRegistCallbackUrl());
            //增加对appIcon字段的修改
            app.setAppIcon(callbackAudit.getAppIcon());
            number =  merchantAppMapper.modfiyCallbackUrl(app);
            log.info("app:{},手游更新记录number:{}", JSONObject.toJSONString(app), number);
        }else if(GameTypeEnum.h5game == en){
            //H5  登录回调为game_url字段
            CallbackAudit audit = new CallbackAudit();
            audit.setAppCode(callbackAudit.getAppCode());
            audit.setGameUrl(callbackAudit.getGameUrl());
            audit.setPayCallbackUrl(callbackAudit.getPayCallbackUrl());
            
            audit.setAppIcon(callbackAudit.getAppIcon());
            number = callbackAuditMapper.updateH5CallbackUrl(audit);
            log.info("audit:{},H5更新记录number:{}", JSONObject.toJSONString(audit), number);
        }else if(GameTypeEnum.yeyougame == en){
            //页游
//            number = yeyouGameMapper.updatePayCallBackUrl(appCode, callbackAudit.getPayCallbackUrl());
            number = yeyouGameMapper.updatePayCallBackUrl(callbackAudit);
            log.info("callbackAudit:{},页游更新记录number:{}",JSONObject.toJSONString(callbackAudit), number);
        }else {
            throw new RuntimeException("修改回调地址失败");
        }
        if(number != 1){
            throw new RuntimeException("修改回调地址失败");
        }
    }


}
