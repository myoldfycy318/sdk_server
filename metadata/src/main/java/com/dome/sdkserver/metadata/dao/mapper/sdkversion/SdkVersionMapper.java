package com.dome.sdkserver.metadata.dao.mapper.sdkversion;

import com.dome.sdkserver.bo.sdkversion.SdkVersionInfo;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SdkVersionMapper
 *
 * @author Zhang ShanMin
 * @date 2016/5/10
 * @time 10:52
 */

@Repository("sdkVersionMapper")
public interface SdkVersionMapper extends IBaseMapperDao {

    /**
     * 获取sdk版本列表
     * @param sdkVersionInfo
     * @return
     */
    public List<SdkVersionInfo> querySdkVersionList(SdkVersionInfo sdkVersionInfo);

    /**
     * sdk版本新增
     * @param sdkVersionInfo
     * @return
     */
    public Integer insertSdkVersion(SdkVersionInfo sdkVersionInfo);

    /**
     * sdk版本编辑
     * @param sdkVersionInfo
     * @return
     */
    public Integer updateSdkVersionById(SdkVersionInfo sdkVersionInfo);


}
