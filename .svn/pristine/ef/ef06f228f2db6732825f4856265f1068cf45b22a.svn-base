package com.dome.sdkserver.service.impl.game;

import com.dome.sdkserver.bq.enumeration.OpenSelectEnum;
import com.dome.sdkserver.metadata.dao.mapper.bq.pay.NewOpenGameInfoMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.service.game.NewOpenGameInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewOpenGameInfoServiceImpl extends GameSyncManager implements NewOpenGameInfoService {

    @Autowired
    private NewOpenGameInfoMapper mapper;

    @Override
    public boolean synNewOpenAppInfo(AppInfoEntity entity) {
        //设置数据来源为新开放平台
        entity.setOpenSelect(OpenSelectEnum.新开放平台.getOpenSelect());
        //判断新增还是修改
        int judge = mapper.isGameExist(entity);
        boolean flag = false;
        if (judge >= 1) {
            //修改
            flag = mapper.updateByAppCodeSelective(entity);
        } else {
            //新增
            flag = mapper.insertSelective(entity);
        }
        //更新缓存中数据
        return flag ? handleCache(entity) : flag;
    }

    /**
     * 同步ogp参数
     * @param appInfoEntity
     * @return
     */
    @Override
    public boolean syncOgpParams(AppInfoEntity appInfoEntity) {
        return synNewOpenAppInfo(appInfoEntity);
    }
}
