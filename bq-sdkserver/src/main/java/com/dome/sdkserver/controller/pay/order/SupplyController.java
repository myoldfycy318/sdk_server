package com.dome.sdkserver.controller.pay.order;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.enumeration.PaySource2BiEnum;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.service.order.SupplyService;
import com.dome.sdkserver.service.web.requestEntity.HttpRequestOrderInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 游戏充值记录补单
 * SupplyController
 *
 * @author Zhang ShanMin
 * @date 2017/10/9
 * @time 15:49
 */
@Controller
@RequestMapping("/supply")
public class SupplyController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "supplyService")
    private SupplyService supplyService;

    /**
     * 游戏充值补单
     *
     * @param orderInfo
     * @return
     */
    @RequestMapping(value = "property")
    @ResponseBody
    public SdkOauthResult supply(HttpRequestOrderInfo orderInfo) {
        logger.info("游戏充值补单请求参数:{}", JSONObject.toJSONString(orderInfo));
        PaySource2BiEnum paySource2BiEnum = PaySource2BiEnum.getPaySourceTbl(orderInfo.getPaySources());
        if (StringUtils.isBlank(orderInfo.getOrderNo()) || null == paySource2BiEnum)
            return SdkOauthResult.failed("必传参数为空");
        return supplyService.supply(orderInfo);
    }
}
