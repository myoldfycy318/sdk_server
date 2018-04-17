package com.dome.sdkserver.metadata.dao.mapper.bq.award;

import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import com.dome.sdkserver.metadata.entity.bq.award.CouponPayConfig;
import com.dome.sdkserver.metadata.entity.bq.award.StoreAwardConfEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 游戏活动奖励规则
 *
 */
@Repository("storeAwardConfMapper")
public interface StoreAwardConfMapper extends IBaseMapperDao {

    int insert(StoreAwardConfEntity entity);

    int updateById(StoreAwardConfEntity entity);

    /**
     * 上下架
     *
     * @param storeConfId 应用市场配置ID
     * @param isPublish   0 下架、1 上架
     * @param type        规则类型
     * @return
     */
    int publish(@Param("storeConfId") String storeConfId, @Param("isPublish") Integer isPublish, @Param("type") Integer type);

    /**
     * 查询配置
     *
     * @param date    日期
     * @param appCode SDK应用编码
     * @return
     */
    List<StoreAwardConfEntity> queryConf(@Param("date") String date,@Param("appCode") String appCode,@Param("payType")String payType);

    /**
     * 根据应用市场配置ID获取配置
     *
     * @param storeConfId 应用市场配置ID
     * @param type        配置类型
     * @return
     */
    List<StoreAwardConfEntity> queryConfByStoreConfId(@Param("storeConfId") String storeConfId, @Param("type") Integer type);

    /**
     * 删除配置
     *
     * @param storeConfId 应用市场配置ID
     * @param type        配置类型
     * @return
     */
    int deleteByStoreConfId(@Param("storeConfId") String storeConfId, @Param("type") Integer type);


    /**
     * 添加冲返配置
     * @param couponPayConfig
     * @return
     */
    int insertCouponPayConfig(CouponPayConfig couponPayConfig);

    /**
     * 删除冲返配置
     * @param storeConfId
     * @return
     */
    int delCouponPayConfig(@Param("storeConfId") Integer storeConfId);


}
