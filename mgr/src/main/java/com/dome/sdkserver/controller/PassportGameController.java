package com.dome.sdkserver.controller;

import com.dome.sdkserver.service.PassportGameService;
import com.dome.sdkserver.view.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by heyajun on 2017/5/25.
 */
@Controller
@RequestMapping("/passport/game")
public class PassportGameController {
    private Logger log = LoggerFactory.getLogger(PassportGameController.class);

    @Autowired
    private PassportGameService passportGameService;

    @RequestMapping("/selectAll")
    @ResponseBody
    public AjaxResult selectAllApp(@RequestParam(required = false)String appCode,
                                   @RequestParam(required = false)String appName,
                                   @RequestParam(required = false,defaultValue = "1")Integer pageNo,
                                   @RequestParam(required = false,defaultValue = "15")Integer pageSize,
                                   HttpServletRequest request){
        try {
            //TODO 加签
            Map<String,Object> data = passportGameService.selectAllApp(appCode,appName,pageNo,pageSize);
            return AjaxResult.success(data);
        }catch (Exception e){
            log.error("查询游戏列表错误",e);
            return AjaxResult.failed("查询游戏列表错误");
        }
    }

}
