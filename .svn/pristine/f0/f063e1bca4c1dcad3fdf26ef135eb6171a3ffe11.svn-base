package com.dome.sdkserver.service.sdkversion.impl;

import com.dome.sdkserver.bo.sdkversion.SdkVersionInfo;
import com.dome.sdkserver.metadata.dao.mapper.sdkversion.SdkVersionMapper;
import com.dome.sdkserver.service.sdkversion.SdkVersionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * SdkVersionServiceImpl
 *
 * @author Zhang ShanMin
 * @date 2016/5/10
 * @time 12:26
 */
@Service("sdkVersionService")
public class SdkVersionServiceImpl implements SdkVersionService {

    @Resource(name = "sdkVersionMapper")
    private SdkVersionMapper sdkVersionMapper;

    /**
     * 获取sdk版本列表
     *
     * @param sdkVersionInfo
     * @return
     */
    @Override
    public List<SdkVersionInfo> getSdkVersionList(SdkVersionInfo sdkVersionInfo) {
        return sdkVersionMapper.querySdkVersionList(sdkVersionInfo);
    }

    /**
     * 获取sdk版本列表分页
     *
     * @param sdkVersionInfo
     * @return
     */
    @Override
    public Page<SdkVersionInfo> getSdkVersionByPage(SdkVersionInfo sdkVersionInfo, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        Page<SdkVersionInfo> sdkVersionInfoPage = (Page<SdkVersionInfo>) sdkVersionMapper.querySdkVersionList(sdkVersionInfo);
        return sdkVersionInfoPage;
    }

    /**
     * 新增sdk版本
     *
     * @param sdkVersionInfo
     * @return
     */
    @Override
    public boolean insertSdkVersion(SdkVersionInfo sdkVersionInfo) {
        return sdkVersionMapper.insertSdkVersion(sdkVersionInfo) == 1;
    }

    /**
     * sdk版本编辑
     *
     * @param sdkVersionInfo
     * @return
     */
    @Override
    public boolean updateSdkVersionById(SdkVersionInfo sdkVersionInfo) {
        if (sdkVersionInfo == null)
            return false;
        SdkVersionInfo versionInfo = new SdkVersionInfo();
        versionInfo.setId(sdkVersionInfo.getId());
        List<SdkVersionInfo> list = sdkVersionMapper.querySdkVersionList(versionInfo);
        if (list == null || list.size() <= 0)
            return false;
        if (!list.get(0).getSdkPath().equals(sdkVersionInfo.getSdkPath()))
            sdkVersionInfo.setSdkUploadTime(new Date());
        return sdkVersionMapper.updateSdkVersionById(sdkVersionInfo) == 1;
    }

    /**
     * sdk版本发布
     *
     * @param sdkVersionInfo
     * @return
     */
    @Override
    public boolean delopySdkVersionById(SdkVersionInfo sdkVersionInfo) {
        return sdkVersionMapper.updateSdkVersionById(sdkVersionInfo) == 1;
    }
}
