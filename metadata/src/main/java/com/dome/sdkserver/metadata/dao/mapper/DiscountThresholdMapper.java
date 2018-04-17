package com.dome.sdkserver.metadata.dao.mapper;

import com.dome.sdkserver.bo.DiscountThreshold;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;


/**
 * Created by heyajun on 2016/8/4.
 */
@Repository
public interface DiscountThresholdMapper extends IBaseMapperDao<DiscountThreshold, Long>{
    /*查询渠道扣量信息*/
	List<DiscountThreshold> selectChannel(@Param("dt") DiscountThreshold discountThreshold,
                                          @Param("pageSize") Integer pageSize, @Param("begin") Integer begin);

    
    /*渠道扣量总数*/
    int selectCountSecond(@Param("dt") DiscountThreshold discountThreshold);

    
    /*设置CPA/CPS扣量比例*/
    void updateSetDiscount(@Param("dt") DiscountThreshold discountThreshold);
    
    /*查询CPA/CPS扣量创建时间*/
    DiscountThreshold selectCreatTime(@Param("dt") DiscountThreshold discountThreshold);
    
    /*查询CPA/CPS扣量修改时间*/
    DiscountThreshold selectChangeTime(@Param("dt") DiscountThreshold discountThreshold);
    
    /*插入 修改扣量的CPA/CPS*/
    int updateDeleteFlag(@Param("dt") DiscountThreshold discountThreshold);
    
    /*插入 修改扣量的CPA/CPS*/
    int insertChangeDiscount(@Param("dt") DiscountThreshold discountThreshold);

    /*查询扣量表中的channelCode*/
    Set<String> selectDiscountTable();

    /*向domesdk_second_channel表中查询渠道code*/
    Set<String> selectSecondTable();

    /*向表中插入渠道code*/
    int insertChannelCode(@Param("dt") DiscountThreshold discountThreshold);
}
