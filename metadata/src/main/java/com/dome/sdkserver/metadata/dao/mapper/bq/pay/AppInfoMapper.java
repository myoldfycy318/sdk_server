package com.dome.sdkserver.metadata.dao.mapper.bq.pay;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;

@Repository("appInfoMapper")
public interface AppInfoMapper extends IBaseMapperDao<AppInfoEntity, Long> {


    /**
     *  获取手游(直接从开放平台表中取手游游戏信息)
     * @param appCode
     * @return
     */
    AppInfoEntity getAppInfoByAppCode(@Param("appCode")String appCode);

	/**
	 * 获取手游(前提手游游戏已从开放平台同步到sdk)
	 * 
	 * @param appCode
	 * @return
	 */
	AppInfoEntity queryAppGame(@Param("appCode")String appCode);

    /**
     * 获取页游游戏应用信息
     * @param appCode
     * @return
     */
    AppInfoEntity queryWebGame(@Param("appCode")String appCode);

    /**
     * 获取宝玩H5游戏应用信息
     * @param appCode
     * @return
     */
    AppInfoEntity queryBwH5Game(@Param("appCode")String appCode);

    /**
     * 获取冰趣H5游戏应用信息
     * @param appCode
     * @return
     */
    AppInfoEntity queryBH5Game(@Param("appCode")String appCode);


    /**
     * 新增页游信息
     * @param appInfoEntity
     * @return
     */
    boolean insertwebgame(AppInfoEntity appInfoEntity);

    /**
     * 更新页游信息
     * @param appInfoEntity
     * @return
     */
    boolean updatewebgame(AppInfoEntity appInfoEntity);

    /**
     * 
     * @param appCode
     * @return
     */
    boolean deleteBwH5game(String appCode);
    
    /**
     * 新增宝玩H5游戏信息
     * @param appInfoEntity
     * @return
     */
    boolean insertBwH5game(AppInfoEntity appInfoEntity);

    /**
     * 更新宝玩H5游戏信息
     * @param appInfoEntity
     * @return
     */
    boolean updateBwH5game(AppInfoEntity appInfoEntity);
    
    /**
     * 删除冰趣h5游戏
     * @param appCode
     * @return
     */
    boolean deleteBqH5game(String appCode);

    /**
     * 新增冰趣H5游戏信息
     * @param appInfoEntity
     * @return
     */
    boolean insertBqH5game(AppInfoEntity appInfoEntity);
    /**
     * 更新冰趣H5游戏信息
     * @param appInfoEntity
     * @return
     */
    boolean updateBq5game(AppInfoEntity appInfoEntity);

    /**
     * 新增冰趣手游
     * @param appInfoEntity
     * @return
     */
    boolean insertAppGame(AppInfoEntity appInfoEntity);

    /**
     * 修改冰趣手游
     * @param appInfoEntity
     * @return
     */
    boolean updateAppGame(AppInfoEntity appInfoEntity);

    /**
     * 是否存在页游
     * @param appInfoEntity
     * @return
     */
    Integer isExistWebGame(AppInfoEntity appInfoEntity);

    /**
     * 是否存在钱宝H5游戏
     * @param appInfoEntity
     * @return
     */
    Integer isExistBwH5Game(AppInfoEntity appInfoEntity);

    /**
     * 是否存在冰趣H5游戏
     * @param appInfoEntity
     * @return
     */
    Integer isExistBQH5Game(AppInfoEntity appInfoEntity);

    /**
     * 是否存在冰趣手游游戏
     * @return
     */
    Integer isExistAppGame(AppInfoEntity appInfoEntity);


}
