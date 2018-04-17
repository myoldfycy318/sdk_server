package com.dome.sdkserver.metadata.dao.mapper.bq.pay;

import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import com.dome.sdkserver.metadata.entity.bq.pay.PayOptions;
import org.springframework.stereotype.Repository;

@Repository("payOptionsMapper")
public interface PayOptionsMapper extends IBaseMapperDao {

    boolean insert(PayOptions payOptions);

    boolean updateByAppCode(PayOptions payOptions);

    boolean delByAppCode(PayOptions payOptions);

    Integer isExistAppCode(PayOptions payOptions);

    PayOptions queryPayOptions(PayOptions payOptions);

}
