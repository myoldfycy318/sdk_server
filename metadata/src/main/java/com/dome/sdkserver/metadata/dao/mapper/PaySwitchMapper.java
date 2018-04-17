package com.dome.sdkserver.metadata.dao.mapper;


import com.dome.sdkserver.bo.PaySwitch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaySwitchMapper {

    public List<PaySwitch> query(@Param("ps") PaySwitch ps,@Param("begin")Integer begin,@Param("pageSize") Integer pageSize);
    public PaySwitch queryByAppCode(@Param("appCode")String appCode);
    public int count(@Param("ps") PaySwitch ps);

    public List<String> queryCanAddAppCode();
    public int modify (@Param("ps") PaySwitch ps);
    public int insert (@Param("ps") PaySwitch ps);
}
