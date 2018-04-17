package com.dome.sdkserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.AppInfo;
import com.dome.sdkserver.bo.PayAround;
import com.dome.sdkserver.service.PayAroundService;
import com.dome.sdkserver.view.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付绕行
 */
@Controller
@RequestMapping("/payAround")
public class PayAroundController extends BaseController{

    @Resource
    private PayAroundService service;

    /**
     * 查询支付绕行数据, 运营手动添加发行渠道的IOS手游
     * @param appCode
     * @param appName
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public AjaxResult queryArround(@RequestParam (required = false) String appCode,
                                    @RequestParam (required = false) String appName ,
                                    @RequestParam (required = false, defaultValue = "1")Integer pageNo ,
                                    @RequestParam (required = false, defaultValue = "10")Integer pageSize,
                                    HttpServletRequest request)
    {
        try {
            PayAround around = new PayAround(appCode,appName);
            Map<String,Object> data = service.query(around, pageNo, pageSize);
            return AjaxResult.success(data);
        }catch (Exception e){
            log.error("查询支付绕行错误",e);
            return AjaxResult.failed("查询支付绕行错误");
        }
    }


    //TODO 查询游戏信息接口该接口是针对IOS手游下架的应用
    @RequestMapping("/getAppInfo")
    @ResponseBody
    public AjaxResult getAppInfo(@RequestParam (required = false) String appCode,
                                 @RequestParam (required = false) String appName ,
                                 @RequestParam (required = false, defaultValue = "1")Integer pageNo ,
                                 @RequestParam (required = false, defaultValue = "10")Integer pageSize,
                                 HttpServletRequest request)
    {
        try {
            Map<String,Object> data = service.selectAppInfo(appCode, appName, pageNo, pageSize);
            return AjaxResult.success(data);
        }catch (Exception e){
            log.error("获取应用信息失败", e);
            return AjaxResult.failed("获取应用信息失败");
        }
    }


    /**
     * 增加支付绕行, 判断参数是否为空 , 添加记录绕行状态默认为关闭
     * @param payAround
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public AjaxResult queryArround(PayAround payAround)
    {
        try {
            //验证参数
            boolean pass = validateParam(payAround);
            if(!pass){
                return AjaxResult.failed("有必填参数为空");
            }
            //判断该游戏是否已经被添加
            if(isAppCodeExist(payAround)){
                return AjaxResult.failed("该游戏已存在");
            }
            AjaxResult result = service.add(payAround);
            if(!AjaxResult.isSucees(result)){
                return AjaxResult.failed(result.getErrorMsg());
            }
            return AjaxResult.success();
        }catch (Exception e){
            log.error("添加支付绕行错误",e);
            return AjaxResult.failed("添加支付绕行错误");
        }
    }

    public boolean validateParam(PayAround payAround){
        if(StringUtils.isBlank(payAround.getAppCode()) || StringUtils.isBlank(payAround.getAppName())
                || StringUtils.isBlank(payAround.getMerchantFullName()) //||  payAround.getMerchantInfoId() == null
                || StringUtils.isBlank(payAround.getMerchantCode()))
        {
            return false;
        }
        return true;
    }

    /**
     *
     * @param payAround
     * @return true-存在  false-不存在
     */
    public boolean isAppCodeExist(PayAround payAround){
        PayAround selectResult = service.selectByAppCode(payAround.getAppCode());
        if(selectResult != null){
            return true;
        }
        return false;
    }


    /**
     * 保存支付绕行设置, 同步数据到sdk
     * @param payAround
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxResult modifyArround(PayAround payAround){
        try {
            if(payAround.getId() == null || payAround.getIsAround() == null ){
                return AjaxResult.failed("有必填参数为空!");
            }
            if(payAround.getIsAround() != null && payAround.getPayType() == null){
                return AjaxResult.failed("请选择绕行支付方式!");
            }
            Integer isAround = payAround.getIsAround();
            String payType = payAround.getPayType();
            PayAround around = service.selectById(payAround.getId());
            if(around != null){
                payAround = around;
            }else {
                return AjaxResult.failed("记录不存在");
            }
//            System.out.println("xxx:"+ JSONObject.toJSONString(payAround));
            //支付绕行开关关闭时,将支付方式清空
            if(isAround == 0){
                payAround.setIsAround(isAround);
                payAround.setPayType("");
            }else{
                payAround.setPayType(payType);
            }
            payAround.setIsAround(isAround);
            AjaxResult result = service.modify(payAround);
            if(!AjaxResult.isSucees(result)){
                return AjaxResult.failed(result.getErrorMsg());
            }
            return AjaxResult.success();
        }catch (Exception e){
            log.error("支付绕行保存设置错误",e);
            return AjaxResult.failed("支付绕行保存设置错误");
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResult delete(Integer id, HttpServletRequest request){
        try{
            //判断当前记录是否存在
            PayAround around = service.selectById(id);
//            System.out.println("around:"+around);
            if(around == null){
                return AjaxResult.failed("记录不存在");
            }
            AjaxResult result = service.delete(around);
            if(!AjaxResult.isSucees(result)){
                return AjaxResult.failed(result.getErrorMsg());
            }
            return AjaxResult.success();
        }catch (Exception e){
            log.error("删除绕线支付开关错误!",e);
            return AjaxResult.failed("删除绕线支付开关错误!");
        }
    }

}
