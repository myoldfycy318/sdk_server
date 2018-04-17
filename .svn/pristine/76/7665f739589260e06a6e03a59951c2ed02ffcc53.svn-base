package com.dome.sdkserver.service.impl.Version;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.sdkversion.SdkVersionInfo;
import com.dome.sdkserver.metadata.dao.mapper.sdkversion.SdkVersionMapper;
import com.dome.sdkserver.metadata.entity.bq.sdkversion.SdkVersion;
import com.dome.sdkserver.service.BqSdkConstants;
import com.dome.sdkserver.service.version.VersionService;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("versionService")
public class VersionServiceImpl implements VersionService {

    @Autowired
    private SdkVersionMapper sdkVersionMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public JSONObject checkVersion(SdkVersion sdkVersion) {
        SdkVersionInfo sdkVersionInfo = new SdkVersionInfo();
        sdkVersionInfo.setIsRelease(1);

        List<SdkVersionInfo> versions = sdkVersionMapper.querySdkVersionList(sdkVersionInfo);
        if (versions == null || versions.size() == 0) {
            return null;
        }
        return getNeedUpdateVersion(sdkVersion, versions);
    }

    private JSONObject getNeedUpdateVersion(SdkVersion _sdkVersion, List<SdkVersionInfo> versions) {
        JSONObject json = new JSONObject();
        String needUpdate = "0", versionNum = "", download = "", fileName = "";
        String maxVersionNum = versions.get(versions.size() - 1).getVersionNum();
        if (_sdkVersion.getCurrentVersion().equals(maxVersionNum) || BqSdkConstants.channelCodeOverseas.equals(_sdkVersion.getChannelCode())
                || BqSdkConstants.channelCodePublish.equals(_sdkVersion.getChannelCode())) { //发行、海外渠道暂不热更 2017-3-22
            json.put("needUpdate", "0");
            json.put("updateVersion", versionNum);
            json.put("download", download);
            return json;
        }
        int index = versions.size() - 1;
        for (int i = index; i >= 0; i--) {
            SdkVersionInfo sdkVersionInfo = versions.get(i);
            versionNum = sdkVersionInfo.getVersionNum();
            String needUpdateVersion = sdkVersionInfo.getNeedUpdateVersion();
            String needUpdateGame = sdkVersionInfo.getNeedUpdateGame();

            if (StringUtils.isBlank(needUpdateVersion) && StringUtils.isBlank(needUpdateGame)) {
                if (!_sdkVersion.getCurrentVersion().equals(versionNum)) {
                    needUpdate = "1";
                    download = sdkVersionInfo.getSdkPath();
                } else {
                    needUpdate = "0";
                    versionNum = "";
                }

                break;
            }
            if (StringUtils.isBlank(needUpdateVersion) && StringUtils.isNotBlank(needUpdateGame)) {
                if (_sdkVersion.getCurrentVersion().equals(versionNum)) {
                    needUpdate = "0";
                    versionNum = "";
                    break;
                } else {
                    if (needUpdateGame.contains(_sdkVersion.getAppCode())) {
                        needUpdate = "1";
                        download = sdkVersionInfo.getSdkPath();
                        break;
                    }
                    continue;
                }
            }
            if (StringUtils.isNotBlank(needUpdateVersion) && StringUtils.isBlank(needUpdateGame)) {
                if (_sdkVersion.getCurrentVersion().equals(versionNum)) {
                    needUpdate = "0";
                    versionNum = "";
                    break;
                } else {
                    if (needUpdateVersion.contains(_sdkVersion.getCurrentVersion())) {
                        needUpdate = "1";
                        download = sdkVersionInfo.getSdkPath();
                        break;
                    }
                    continue;
                }
            } else {
                if (_sdkVersion.getCurrentVersion().equals(versionNum)) {
                    needUpdate = "0";
                    versionNum = "";
                    break;
                } else {
                    if (needUpdateVersion.contains(_sdkVersion.getCurrentVersion()) && needUpdateGame.contains(_sdkVersion.getAppCode())) {
                        needUpdate = "1";
                        download = sdkVersionInfo.getSdkPath();
                        break;
                    }
                    continue;
                }
            }
        }
        json.put("needUpdate", needUpdate);
        json.put("updateVersion", versionNum);
        json.put("download", download);
        if (StringUtils.isNotBlank(download)) {
            fileName = download.substring(download.lastIndexOf("/") + 1);
        }
        json.put("fileName", fileName);
        return json;
    }

    public static void main(String[] args) {
        String str = "http://domesdk.qbao.com/domesdk/sdk/20160513/1403/com.feelingtouch.strikeforce_1.23_123.apk";
        String fileName = str.substring(str.lastIndexOf("/") + 1);
        System.out.println(fileName);
    }
}
