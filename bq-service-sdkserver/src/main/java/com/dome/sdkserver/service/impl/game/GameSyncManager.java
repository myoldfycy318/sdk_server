package com.dome.sdkserver.service.impl.game;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.constants.RedisKeyEnum;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GameSyncManager
 *
 * @author Zhang ShanMin
 * @date 2017/10/16
 * @time 16:07
 */
public abstract class GameSyncManager {

    @Autowired
    protected RedisUtil redisUtil;
    //游戏下架
    protected final Integer GAME_DOWN = 0;

    /**
     * 处理游戏缓存
     *
     * @param appInfoEntity
     * @return
     */
    protected boolean handleCache(AppInfoEntity appInfoEntity) {
        RedisKeyEnum redisKeyEnum = RedisKeyEnum.getGameType(appInfoEntity.getAppCode());
        if (redisKeyEnum == null)
            return false;
       /* if (GAME_DOWN == appInfoEntity.getStatus()) {
            redisUtil.del(redisKeyEnum.getKey());
            return true;
        }*/
        redisUtil.setex(redisKeyEnum.getKey() + appInfoEntity.getAppCode(), redisKeyEnum.getExpireTime(), JSONObject.toJSONString(appInfoEntity));
        return true;
    }
}
