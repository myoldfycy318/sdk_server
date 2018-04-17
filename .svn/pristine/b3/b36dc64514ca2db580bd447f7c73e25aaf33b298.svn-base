package com.dome.sdkserver.controller.version;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.enumeration.ErrorCodeEnum;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.metadata.entity.bq.sdkversion.SdkVersion;
import com.dome.sdkserver.service.version.VersionService;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/version")
public class VersionUpdateController extends BaseController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private VersionService versionService;

    /**
     * 检查版本信息
     *
     * @param request
     * @param sdkVersion
     * @return
     */
    @RequestMapping(value = "checkVersion")
    @ResponseBody
    public SdkOauthResult checkVersion(HttpServletRequest request, SdkVersion sdkVersion) {
        SdkOauthResult result;
        try {
            result = validateReqParams(sdkVersion);
            if (!result.isSuccess())
                return result;
            result = checkClient(sdkVersion.getAppCode());
            if (!result.isSuccess()) {
                return result;
            }
            JSONObject json = versionService.checkVersion(sdkVersion);
            if (json == null) {
                log.error(">>>>>>>>>版本号无效");
                return SdkOauthResult.failed(ErrorCodeEnum.sdk版本号无效.code, ErrorCodeEnum.sdk版本号无效.name());

            }

            return SdkOauthResult.success(json);
        } catch (Exception e) {
            log.error(">>>>>>>>>非预期错误", e);
            return SdkOauthResult.failed(ErrorCodeEnum.非预期错误.code, ErrorCodeEnum.非预期错误.name());
        }
    }


    /**
     * 热更参数校验
     *
     * @param sdkVersion
     * @return
     */
    private SdkOauthResult validateReqParams(SdkVersion sdkVersion) {
        if (StringUtils.isBlank(sdkVersion.getCurrentVersion()) || StringUtils.isBlank(sdkVersion.getAppCode()) || StringUtils.isBlank(sdkVersion.getChannelCode())) {
            log.error("sdk热更比传参数为空，sdkVersion=" + JSONObject.toJSONString(sdkVersion));
            return SdkOauthResult.failed(ErrorCodeEnum.有必填参数为空.code, ErrorCodeEnum.有必填参数为空.name());
        }
        return SdkOauthResult.success();
    }
}
