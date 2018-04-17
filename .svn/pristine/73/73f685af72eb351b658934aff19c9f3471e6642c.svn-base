package com.dome.sdkserver.service.sdkversion;

import com.dome.sdkserver.bo.sdkversion.SdkVersionInfo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * SdkVersionService
 *
 * @author Zhang ShanMin
 * @date 2016/5/10
 * @time 12:25
 */
public interface SdkVersionService {

    /**
     * 获取sdk版本列表
     *
     * @param sdkVersionInfo
     * @return
     */
    public List<SdkVersionInfo> getSdkVersionList(SdkVersionInfo sdkVersionInfo);

    /**
     * 获取sdk版本列表分页
     *
     * @param sdkVersionInfo
     * @return
     */
    public Page<SdkVersionInfo> getSdkVersionByPage(SdkVersionInfo sdkVersionInfo,int pageNumber,int pageSize);

    /**
     * 新增sdk版本
     *
     * @param sdkVersionInfo
     * @return
     */
    public boolean insertSdkVersion(SdkVersionInfo sdkVersionInfo);

    /**
     * sdk版本编辑
     *
     * @param sdkVersionInfo
     * @return
     */
    public boolean updateSdkVersionById(SdkVersionInfo sdkVersionInfo);

    /**
     * sdk版本发布
     *
     * @param sdkVersionInfo
     * @return
     */
    public boolean delopySdkVersionById(SdkVersionInfo sdkVersionInfo);


}
