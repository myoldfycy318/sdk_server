package com.dome.sdkserver.metadata.dao.mapper.bq.pay;


import com.dome.sdkserver.metadata.entity.bq.pay.NewOpenChargePoint;
import org.springframework.stereotype.Repository;

@Repository
public interface NewOpenChargePointMapper {
    int isChargeExist(NewOpenChargePoint record);

    int insertSelective(NewOpenChargePoint record);

    //查询新开放平台计费点
    NewOpenChargePoint selectByParam(NewOpenChargePoint record);

    int updateByChargePointCodeSelective(NewOpenChargePoint record);

}