package com.dome.sdkserver.controller;

import com.dome.sdkserver.bo.RealName;
import com.dome.sdkserver.service.RealNameService;
import com.dome.sdkserver.view.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heyajun on 2017/5/25.
 */
@Controller
@RequestMapping("/realName")
public class RealNameController extends BaseController{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RealNameService service;

    @RequestMapping("/query")
    @ResponseBody
    public AjaxResult query(){
        try {
            RealName realName = service.query();
            return AjaxResult.success(realName);
        }catch (Exception e){
            log.error("强制实名认证查询出错",e);
            return AjaxResult.failed("强制实名认证查询出错");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public AjaxResult update(Integer status){
        try {
            if(status == null){
                return AjaxResult.failed("缺少必要参数");
            }
            service.update(status);
            return AjaxResult.success();
        }catch (Exception e){
            log.error("修改强制实名认证出错",e);
            return AjaxResult.failed("修改强制实名认证出错");
        }
    }

}
