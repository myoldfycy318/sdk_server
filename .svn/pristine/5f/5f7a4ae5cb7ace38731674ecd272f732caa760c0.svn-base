package com.dome.sdkserver.service.impl.award;

import com.dome.sdkserver.metadata.dao.mapper.bq.award.StoreAwardConfMapper;
import com.dome.sdkserver.metadata.entity.bq.award.CouponPayConfig;
import com.dome.sdkserver.metadata.entity.bq.award.StoreAwardConfEntity;
import com.dome.sdkserver.service.award.StoreAwardConfService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


/**
 * 游戏活动奖励规则
 */
@Service("storeAwardConfService")
public class StoreAwardConfServiceImpl implements StoreAwardConfService {

    @Resource(name = "storeAwardConfMapper")
    private StoreAwardConfMapper storeAwardConfMapper;

    @Override
    public boolean save(StoreAwardConfEntity entity) {
        if (null != entity && null != entity.getStoreConfId()) {
            synchronized (storeAwardConfMapper) {
                List<StoreAwardConfEntity> list = storeAwardConfMapper.queryConfByStoreConfId(entity.getStoreConfId(),
                        entity.getType());
                if (!CollectionUtils.isEmpty(list)) {// 编辑
                    entity.setId(list.get(0).getId());
                    return updateAwardConfEntity(entity);
                } else {// 新增
                    return saveAwardConfEntity(entity);
                }
            }
        }
        return false;
    }

    @Override
    public int publish(String storeConfId, int isPublish, int type) {
        return storeAwardConfMapper.publish(storeConfId, isPublish, type);
    }

    /**
     *
     * @param time
     * @param appCode
     * @param payType 支付方式:1 钱宝 2 支付宝
     * @return
     */
    @Override
    public List<StoreAwardConfEntity> queryConf(String time, String appCode,String payType) {
        List<StoreAwardConfEntity> list = storeAwardConfMapper.queryConf(time.substring(0, 8),appCode,payType);
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        return null;
    }

    @Override
    public boolean deleteByStoreConfId(String storeConfId, Integer type) {
        int operResult1 = storeAwardConfMapper.deleteByStoreConfId(storeConfId, type);
        int operResult2 = storeAwardConfMapper.delCouponPayConfig(Integer.valueOf(storeConfId));
        return (operResult1 == 1 && operResult2 == 1);
    }

    /**
     * 新增活动奖励规则
     *
     * @param entity
     * @return
     */
    private boolean saveAwardConfEntity(StoreAwardConfEntity entity) {
        int operResult = storeAwardConfMapper.insert(entity);
        if (operResult != 1)
            return false;
        return mergeCouponPayConfig(entity);
    }

    /**
     * 更新活动奖励规则
     *
     * @param entity
     * @return
     */
    private boolean updateAwardConfEntity(StoreAwardConfEntity entity) {
        int operResult = storeAwardConfMapper.updateById(entity);
        if (operResult != 1)
            return false;
        return mergeCouponPayConfig(entity);
    }

    /**
     * merge冲返配置
     *
     * @param entity
     * @return
     */
    private boolean mergeCouponPayConfig(StoreAwardConfEntity entity) {
        List<CouponPayConfig> couponPayConfigs = entity.getCouponPayConfigs();
        if (couponPayConfigs == null || couponPayConfigs.size() <= 0)
            return false;
         storeAwardConfMapper.delCouponPayConfig(Integer.valueOf(entity.getStoreConfId()));
        int operResult = 0;
        for (CouponPayConfig couponPayConfig : couponPayConfigs) {
            operResult = storeAwardConfMapper.insertCouponPayConfig(couponPayConfig);
            if (operResult != 1)
                return false;
        }
        return true;
    }


}
