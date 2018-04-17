package com.dome.sdkserver.metadata.dao.mapper.bq.pay;

import com.dome.sdkserver.metadata.entity.bq.pay.NewOpenChannel;
import org.springframework.stereotype.Repository;

@Repository
public interface NewOpenChannelMapper {
    //查询记录是否存在
    boolean isChannelExist(NewOpenChannel record);

    //新增
    boolean insertSelective(NewOpenChannel record);

    //修改
    boolean updateByChannelCodeSelective(NewOpenChannel record);

    //查询记录
    NewOpenChannel selectByChannelCode(String channelCode);
}
