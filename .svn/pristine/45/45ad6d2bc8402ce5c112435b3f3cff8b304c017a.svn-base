package com.dome.sdkserver.metadata.dao.mapper.bq.pay;


import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface NewOpenGameInfoMapper {
    int isGameExist(AppInfoEntity record);

    //添加数据
    boolean insertSelective(AppInfoEntity record);

    //查询新开放平台同步数据
    AppInfoEntity selectByAppCode(String appCode);

    //更新数据
    boolean updateByAppCodeSelective(AppInfoEntity record);

//    //更新ogp参数
//    boolean updateOgpParams(AppInfoEntity record);

}