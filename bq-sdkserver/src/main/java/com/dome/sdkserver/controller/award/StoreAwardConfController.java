package com.dome.sdkserver.controller.award;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.view.Result;
import com.dome.sdkserver.metadata.entity.bq.award.CouponPayConfig;
import com.dome.sdkserver.metadata.entity.bq.award.StoreAwardConfEntity;
import com.dome.sdkserver.service.award.StoreAwardConfService;
import com.dome.sdkserver.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 游戏活动奖励规则
 *
 */
@Controller("storeAwardConfController")
@RequestMapping("/storeaward")
public class StoreAwardConfController {

    private static Logger log = LoggerFactory.getLogger(StoreAwardConfController.class);
    @Resource
    private StoreAwardConfService storeAwardConfService;


    @RequestMapping("/save.html")
    @ResponseBody
    public Result save(StoreAwardConfEntity param,HttpServletRequest request) {
        if (!verifyEntity(param,request)) {
            log.error("参数校验失败：{}", JSON.toJSONString(param));
            return Result.error(Result.RESPONSE_CODE_PARAM_ERROR, "参数校验失败");
        }
        try {
            storeAwardConfService.save(param);
            log.info("保存处理成功！{}" + JSON.toJSONString(param));
            return Result.success();
        } catch (Exception e) {
            log.error("保存StoreAwardConfEntity失败", e);
            return Result.error();
        }
    }

    /**
     * 校验参数
     *
     * @param param
     */
    private boolean verifyEntity(StoreAwardConfEntity param,HttpServletRequest request) {
        if (StringUtils.isEmpty(param.getMerchantCode()) || StringUtils.isEmpty(param.getActivityName())
                || StringUtils.isEmpty(param.getStoreAppId()) || StringUtils.isEmpty(param.getAppCode())
                || StringUtils.isEmpty(param.getAppName())|| null == param.getStartTime()
                || null == param.getEndTime() || null == param.getType()
                || StringUtils.isBlank(request.getParameter("payConfigJson"))) {
            return false;
        }
        if (param.getPublish() != 0 && param.getPublish() != 1 && param.getPublish() != 9) {
            return false;
        }
        if (param.getIdMember() != 0 && param.getIdMember() != 1) {
            return false;
        }
        //处理日期
        try {
            param.setCouponPayConfigs(JSONObject.parseArray(request.getParameter("payConfigJson"), CouponPayConfig.class));
            Date start = DateUtil.getDate(param.getStartTime(), "yyyy-MM-dd HH:mm:ss");
            Date end = DateUtil.getDate(param.getEndTime(), "yyyy-MM-dd HH:mm:ss");
            String startStr = DateUtil.dateToDateString(start, "yyyyMMddHHmmss");
            String endStr = DateUtil.dateToDateString(end, "yyyyMMddHHmmss");
            param.setStartDate(startStr.substring(0, 8));
            param.setStartTime(startStr.substring(8));
            param.setEndDate(endStr.substring(0, 8));
            param.setEndTime(endStr.substring(8));
        } catch (Exception e) {
            log.error("参数校验出错", e);
            return false;
        }
        return true;
    }

    @RequestMapping("/publish.html")
    @ResponseBody
    public Result publish(String storeConfId, Integer publish, Integer type) {
        if (null == storeConfId || null == publish || null == type) {
            log.error("参数校验失败");
            return Result.error(Result.RESPONSE_CODE_PARAM_ERROR, "参数校验失败");
        }
        try {
            storeAwardConfService.publish(storeConfId, publish, type);
            log.info("上下架处理成功！storeConfId={}, publish={}", storeConfId, publish);
            return Result.success();
        } catch (Exception e) {
            log.error("上下架StoreAwardConfEntity失败", e);
            return Result.error();
        }
    }

    @RequestMapping("/delete.html")
    @ResponseBody
    public Result delete(String storeConfId, Integer type) {
        if (null == storeConfId || null == type) {
            log.error("参数校验失败");
            return Result.error(Result.RESPONSE_CODE_PARAM_ERROR, "参数校验失败");
        }
        try {
            storeAwardConfService.deleteByStoreConfId(storeConfId, type);
            log.info("删除处理成功！storeConfId={}", storeConfId);
            return Result.success();
        } catch (Exception e) {
            log.error("删除StoreAwardConf失败", e);
            return Result.error();
        }
    }


}
