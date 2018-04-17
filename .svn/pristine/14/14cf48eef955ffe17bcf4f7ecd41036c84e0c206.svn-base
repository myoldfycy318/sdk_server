package com.dome.sdkserver.controller.validate;

import com.dome.sdkserver.bq.view.SdkOauthResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ValidateController
 *
 * @author Zhang ShanMin
 * @date 2016/8/17
 * @time 19:01
 */
@Controller
@RequestMapping("/health")
public class ValidateController {


    @RequestMapping(value = "check")
    @ResponseBody
    public SdkOauthResult checkHealth(){
        return SdkOauthResult.success("ok");
    }
}
