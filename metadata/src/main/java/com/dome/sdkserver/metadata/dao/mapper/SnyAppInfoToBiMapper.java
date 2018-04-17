package com.dome.sdkserver.metadata.dao.mapper;


import com.dome.sdkserver.bo.SnyAppInfoToBi;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by heyajun on 2017/5/8.
 */
public interface SnyAppInfoToBiMapper {

    public List<SnyAppInfoToBi> selectAllMobileYeYouH5Info();

    public SnyAppInfoToBi selectMobileYeYouH5InfoByAppCode(@Param("appCode")String appCode);

    public List<SnyAppInfoToBi> selectAllAppInfoFromBi();

    public SnyAppInfoToBi selectAppInfoFromBiByAppCode(@Param("appCode") String appCode);

    public int addAppInfoToBi(@Param("bi")SnyAppInfoToBi bi);

}
