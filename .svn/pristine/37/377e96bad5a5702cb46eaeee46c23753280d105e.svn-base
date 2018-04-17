package com.dome.sdkserver.metadata.dao.mapper;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.PayAround;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by heyajun on 2017/4/11.
 */
public interface PayAroundMapper{

    public List<PayAround> queryPayAround (@Param("payAround") PayAround payAround);

    public PayAround selectByAppCode(@Param("appCode")String appCode);

    public List<MerchantAppInfo> selectAppInfo(@Param("appCode")String appCode, @Param("appName")String appName);

    public PayAround selectById(@Param("id") Integer id);

    public int addPayAround(@Param("payAround") PayAround payAround);

    public int modifyPayAround(@Param("payAround") PayAround payAround);

    public boolean deletePayAround(@Param("id") Integer id);




}
