package com.dome.sdkserver.controller.collect;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bq.view.Result;
import com.dome.sdkserver.metadata.entity.bq.pay.PayOptions;
import com.dome.sdkserver.service.pay.PayOptionsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * SynchPayOptions
 * 同步冰趣支持支付列表
 *
 * @author Zhang ShanMin
 * @date 2016/10/12
 * @time 18:25
 */
@Controller
@RequestMapping("/syncPayOptions")
public class SynchPayOptions {

    private static Logger logger = LoggerFactory.getLogger(SynchPayOptions.class);

    @Resource(name = "payOptionsService")
    private PayOptionsService payOptionsService;

    @RequestMapping(value = "/megerPayOptions", method = RequestMethod.POST)
    @ResponseBody
    public Result megerPayOptions(PayOptions payOptions, HttpServletRequest request) {
        if (StringUtils.isBlank(payOptions.getAppCode()) || StringUtils.isBlank(payOptions.getPayWay())) {
            logger.error("参数校验失败：{}", JSON.toJSONString(payOptions));
            return Result.error(Result.RESPONSE_CODE_PARAM_ERROR, "参数校验失败");
        }
        try {
            payOptionsService.megerPayOptions(payOptions);
            return Result.success();
        } catch (Exception e) {
            logger.error("同步支持支付选项异常", e);
            return Result.error();
        }
    }

    @RequestMapping(value = "/delPayOptions", method = RequestMethod.POST)
    @ResponseBody
    public Result delPayOptions(PayOptions payOptions, HttpServletRequest request) {
        if (StringUtils.isBlank(payOptions.getAppCode())) {
            logger.error("参数校验失败：{}", JSON.toJSONString(payOptions));
            return Result.error(Result.RESPONSE_CODE_PARAM_ERROR, "参数校验失败");
        }
        try {
            payOptionsService.delPayOptions(payOptions);
            return Result.success();
        } catch (Exception e) {
            logger.error("删除支持支付选项异常", e);
            return Result.error();
        }
    }

//    @RequestMapping("/queryPayOptions")
//    @ResponseBody
//    public Result queryPayOptions(PayOptions payOptions, HttpServletRequest request) {
//        try {
//            payOptionsService.queryPayOptions(payOptions);
//            return Result.success();
//        } catch (Exception e) {
//            logger.error("获取支持支付选项异常", e);
//            return Result.error();
//        }
//    }


}
