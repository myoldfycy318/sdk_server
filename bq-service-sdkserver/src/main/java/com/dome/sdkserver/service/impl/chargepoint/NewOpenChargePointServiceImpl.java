package com.dome.sdkserver.service.impl.chargepoint;

import com.dome.sdkserver.metadata.dao.mapper.bq.pay.NewOpenChargePointMapper;
import com.dome.sdkserver.metadata.entity.bq.pay.NewOpenChargePoint;
import com.dome.sdkserver.service.chargePoint.NewOpenChargePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewOpenChargePointServiceImpl implements NewOpenChargePointService {
    @Autowired
    private NewOpenChargePointMapper mapper;

    @Override
    public boolean synNewOpenChargePoint(NewOpenChargePoint charge) {
        NewOpenChargePoint queryEntity = new NewOpenChargePoint(charge.getAppCode(), charge.getChargePointCode());
        //判断是新增操作还是修改操作
//        NewOpenChargePoint judeg =mapper.selectByParam(queryEntity);
        int judge = mapper.isChargeExist(queryEntity);
        if (judge >= 1) {
            //修改
            mapper.updateByChargePointCodeSelective(charge);
        } else {
            //新增
            mapper.insertSelective(charge);
        }
        return true;
    }
}
