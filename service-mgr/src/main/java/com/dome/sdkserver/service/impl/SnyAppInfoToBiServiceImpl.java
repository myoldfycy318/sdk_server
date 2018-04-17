package com.dome.sdkserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.SnyAppInfoToBi;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.SnyAppInfoToBiMapper;
import com.dome.sdkserver.service.SnyAppInfoToBiService;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.util.RedisUtil;
import com.dome.sdkserver.view.AjaxResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyajun on 2017/5/8.
 */
@Service
public class SnyAppInfoToBiServiceImpl implements SnyAppInfoToBiService{
    private Logger log = LoggerFactory.getLogger(SnyAppInfoToBiServiceImpl.class);

    @Autowired
    private SnyAppInfoToBiMapper snyAppInfoToBiMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RedisUtil redisUtil;

    private final String redisKey = "openba:sny:app:bi";


    /**
     * 通过mq来处理应用(手游,页游,H5)信息同步至bi
     * @param appInfo
     * @return
     */
    public void snyAppInfoToBi(MerchantAppInfo appInfo){
        try {
            insertBi(appInfo);
            //通过redis标记判断是否需要检查哪些应用没有同步到Bi
            String redisValue = redisUtil.get(redisKey);
            if(StringUtils.isBlank(redisValue)){
                //近期没有做开放平台是否存在没有将数据同步至bi报表的检查,
                // redis检查标记有效期7天,每次执行定时任务会将有效期刷新
                //检查是否需要同步数据到Bi报表
                log.info("reids失效,通过mq进行同步检查");
                snyAppInfoToBiByMq();
            }
            else {
                log.info("已经通过定mq同步数据至bi");
            }
        }catch (Exception e){
            log.error("开放平台同步应用信息数据到bi错误",e);
            e.printStackTrace();
        }
    }

    /**
     * 通过定时任务或消息队列同步数据至bi, 使用定时任务是基于如果没有应用上下架时,不会主动进行同步检查.
     * 在执行定时任务或有应用上下架时会进行首次同步.
     * 首次同步会从所有应用中检查需要同步的应用,没有需要同步的应用时将不会向mq中添加消息.
     * 首次同步以后该将没有游戏信息需要批量同步到bi,
     * 新上架的应用将会通过
     */
    public void snyAppInfoToBiByMq () throws Exception{
        try {
            //查询dome_app_info,domesdk_h5_game,domesdk_yeyou_game表中所有需要同步的游戏应用数据
            List<SnyAppInfoToBi> allGameInfo = selectAllMobileYeYouH5Info();
            //查询bi表中已经存在的游戏应用信息
            List<SnyAppInfoToBi> allBiInfo = selectAllAppInfoFromBi();
            List<SnyAppInfoToBi> gameList = new ArrayList<SnyAppInfoToBi>();
            gameList.addAll(allGameInfo);
            for(SnyAppInfoToBi gameInfo : allGameInfo){
                for(SnyAppInfoToBi biInfo : allBiInfo){
                    if(gameInfo.getAppCode().equals(biInfo.getAppCode())){
                        gameList.remove(gameInfo);
                        break;
                    }
                }
            }
            log.info("biList:{}:",JSONObject.toJSONString(gameList));
            if(CollectionUtils.isEmpty(gameList)){
                log.info("没有游戏信息需要同步到bi");
            }else {
                //添加消息队列
                amqpTemplate.convertAndSend("openba_sny_app_to_bi_list",gameList);
            }
            // 添加redis标记(是否已经检查没有被同步到bi的应用信息)
            String current = DateUtils.getCurDateFormatStr(DateUtil.DATATIMEF_STR);
            redisUtil.setnx(redisKey, 7*24*60*60,"updateTime:"+ current);
        }catch (Exception e){
            log.error("开放平台检查是否有手游,页游,H5信息数据未同步至bi出错",e);
            //删除缓存
            redisUtil.del(redisKey);
            throw new Exception("开放平台检查是否有手游,页游,H5信息数据未同步至bi出错");
        }
    }

    public void insertBi(MerchantAppInfo appInfo){
        try {
            String appCode = appInfo.getAppCode();
            //判断在bi中是否存在该记录
            SnyAppInfoToBi bi = snyAppInfoToBiMapper.selectAppInfoFromBiByAppCode(appCode);
            if(bi == null && (  appInfo.getStatus() == AppStatusEnum.shelf_finish.getStatus()
                              || appInfo.getStatus() == AppStatusEnum.shelf_off.getStatus()))
            {
                //应用上架或下架并且bi中没有该条记录时,可以对bi表进行添加操作
                SnyAppInfoToBi biInfo = snyAppInfoToBiMapper.selectMobileYeYouH5InfoByAppCode(appCode);//查询上架appCode的应用信息
                int insertCount = snyAppInfoToBiMapper.addAppInfoToBi(biInfo);
                if(insertCount !=1){
                    log.info("开放平台通过appCode同步添加数据至bi失败,应用信息:{}", JSONObject.toJSONString(appInfo));
                }

            }
        }catch (Exception e){
            log.error("开放平台通过appCode同步添加数据至bi错误",e);
            log.error("开放平台通过appCode同步添加数据至bi错误应用信息:{}",JSONObject.toJSONString(appInfo));
        }
    }


    @Override
    public void insertBiList(List<SnyAppInfoToBi> listBi){
        try{
            int successCount = 0;
            int errorCount = 0;
            for(SnyAppInfoToBi bi : listBi){
                //对bi表进行添加操作
                int insertCount = snyAppInfoToBiMapper.addAppInfoToBi(bi);
                if(insertCount != 1){
                    log.error("添加数据至bi失败记录:{}",JSONObject.toJSONString(bi));
                    errorCount ++;
                }
                successCount ++;
            }
            log.info("开放平台同步添加数据至bi,同步数据总数量:{}, 成功数量{}, 失败数量:{}", listBi.size(),successCount,errorCount);
        }catch (Exception e){
            log.error("开放平台同步添加或删除bi数据错误",e);
            e.printStackTrace();
        }
    }



    @Override
    public List<SnyAppInfoToBi> selectAllMobileYeYouH5Info() {
        return snyAppInfoToBiMapper.selectAllMobileYeYouH5Info();
    }

    @Override
    public List<SnyAppInfoToBi> selectAllAppInfoFromBi() {
        return snyAppInfoToBiMapper.selectAllAppInfoFromBi();
    }

    @Override
    public SnyAppInfoToBi selectAppInfoFromBiByAppCode(String appCode) {
        return snyAppInfoToBiMapper.selectAppInfoFromBiByAppCode(appCode);
    }

    @Override
    public int addAppInfoToBi(SnyAppInfoToBi bi) {
        return snyAppInfoToBiMapper.addAppInfoToBi(bi);
    }


}
