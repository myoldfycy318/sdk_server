package com.dome.sdkserver.service;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.SnyAppInfoToBi;

import java.util.List;

/**
 * Created by heyajun on 2017/5/8.
 */
public interface SnyAppInfoToBiService {

    public List<SnyAppInfoToBi> selectAllMobileYeYouH5Info();

    public void snyAppInfoToBi (MerchantAppInfo appInfo);

    public void snyAppInfoToBiByMq () throws Exception;

    public void insertBiList(List<SnyAppInfoToBi> listBi);

    public List<SnyAppInfoToBi> selectAllAppInfoFromBi();

    public SnyAppInfoToBi selectAppInfoFromBiByAppCode(String appCode);

    public int addAppInfoToBi(SnyAppInfoToBi bi);

}
