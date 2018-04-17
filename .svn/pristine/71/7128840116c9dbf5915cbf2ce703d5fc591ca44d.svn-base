package com.dome.sdkserver.controller.pay.basic;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.service.pay.PayOptionsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PayStretchController
 *
 * @author Zhang ShanMin
 * @date 2017/6/7
 * @time 10:32
 */
@Controller
@RequestMapping("/stretch")
public class PayStretchController extends PayBaseController {

    @Resource(name = "payOptionsService")
    private PayOptionsService payOptionsService;

    /**
     * 游戏充值中心获取cp提供的区服信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getZooServer")
    @ResponseBody
    public SdkOauthResult getGameZooServer(HttpServletRequest request) {
        JSONObject jsonObject = null;
        if (StringUtils.isEmpty(request.getParameter("appCode")))
            return SdkOauthResult.failed("必填参数为空");
        try {
            //获取游戏信息
            jsonObject = payOptionsService.queryGameInfo(request.getParameter("appCode"));
            if (jsonObject == null)
                return SdkOauthResult.failed("获取不到应用信息");
            jsonObject.put("appCode", request.getParameter("appCode"));
            //获取区分信息
            jsonObject = payOptionsService.queryGameZooServers(jsonObject);
            if (jsonObject == null)
                return SdkOauthResult.failed("系统异常，请稍后重试");
            return SdkOauthResult.success(jsonObject);
        } catch (Exception e) {
            log.error("游戏充值中心获取区服信息异常", e);
            return SdkOauthResult.failed("系统异常，请稍后重试");
        }
    }


    @RequestMapping(value = "/testZooServer")
    @ResponseBody
    public Object testGameZooServer(HttpServletRequest request) {
        List list1 = new ArrayList();
        Map<String,String> s1 = new HashMap<String, String>();
        s1.put("serverCode","sc001");
        s1.put("serverName","name001");
        Map<String,String>  s2 = new HashMap<String, String>();
        s2.put("serverCode","sc002");
        s2.put("serverName","name002");
        Map<String,String>  s3 = new HashMap<String, String>();
        s3.put("serverCode","sc003");
        s3.put("serverName","name003");
        list1.add(s1);
        list1.add(s2);
        list1.add(s3);

        List list2 = new ArrayList();
        Map<String,String> s4 = new HashMap<String, String>();
        s4.put("serverCode","sc004");
        s4.put("serverName","name004");
        Map<String,String> s5 = new HashMap<String, String>();
        s5.put("serverCode","sc005");
        s5.put("serverName","name005");
        list2.add(s4);
        list2.add(s5);

        Map<String,Object> z1 = new HashMap<String, Object>();
        z1.put("zooName","区名1");
        z1.put("zooCode","zCode1");
        z1.put("zooServers",list1);

        Map<String,Object> z2 = new HashMap<String, Object>();
        z2.put("zooName","区名2");
        z2.put("zooCode","zCode2");
        z2.put("zooServers",list2);

        List zs = new ArrayList();
        zs.add(z1);
        zs.add(z2);

        Map map = new HashMap();
        map.put("zooServerList",zs);

        Map<String,String> feeCode1 = new HashMap<String, String>();
        feeCode1.put("chargePointCode","C001");
        feeCode1.put("chargePointName","计费点1");
        feeCode1.put("chargePointAmount","1");
        Map<String,String> feeCode2 = new HashMap<String, String>();
        feeCode2.put("chargePointCode","C002");
        feeCode2.put("chargePointName","计费点2");
        feeCode2.put("chargePointAmount","20");
        Map<String,String> feeCode3 = new HashMap<String, String>();
        feeCode3.put("chargePointCode","C003");
        feeCode3.put("chargePointName","计费点3");
        feeCode3.put("chargePointAmount","300");
        Map<String,String> feeCode4 = new HashMap<String, String>();
        feeCode4.put("chargePointCode","C004");
        feeCode4.put("chargePointName","计费点4");
        feeCode4.put("chargePointAmount","4000");

        List feeCodeList = new ArrayList();
        feeCodeList.add(feeCode1);
        feeCodeList.add(feeCode2);
        feeCodeList.add(feeCode3);
        feeCodeList.add(feeCode4);

        map.put("chargePoints",feeCodeList);
        return map;
    }


}
