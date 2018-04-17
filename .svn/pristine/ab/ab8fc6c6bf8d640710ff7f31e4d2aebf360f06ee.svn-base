package com.dome.sdkserver.controller;

import com.dome.sdkserver.bo.ChannelManager;
import com.dome.sdkserver.bo.QueryPageEntity;
import com.dome.sdkserver.bq.util.PageDto;
import com.dome.sdkserver.service.ChannelManagerService;
import com.dome.sdkserver.view.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/channelManager")
public class ChannelManagerController extends BaseController {

    @Resource
    private ChannelManagerService service;

    //查询渠道列表
    @RequestMapping("/queryList")
    @ResponseBody
    public AjaxResult getChannelManagerList(HttpServletRequest request, ChannelManager manager) {
        try {
            QueryPageEntity entity = new QueryPageEntity();
            QueryPageEntity<ChannelManager> queryPageEntity = entity.initQueryPageEntity(request, manager);
            PageDto<ChannelManager> data = service.queryList(queryPageEntity);
            return AjaxResult.success(data);
        } catch (Exception e) {
            log.error("查询渠道列表错误", e);
            return AjaxResult.failed("查询渠道列表错误");
        }
    }

    //自动生成渠道号
    @RequestMapping("/autoCreateChanneCode")
    @ResponseBody
    public AjaxResult autoCreateChannelCode(HttpServletRequest request, ChannelManager manager) {
        try {
            Map<String,String> data = service.autoCreateChannelCode();

            return AjaxResult.success(data);
        } catch (Exception e) {
            log.error("自动生成渠道号错误", e);
            return AjaxResult.failed("自动生成渠道号错误");
        }
    }

    @RequestMapping("/addChannel")
    @ResponseBody
    public AjaxResult addChannel(HttpServletRequest request, ChannelManager manager) {
        try {
            if (StringUtils.isBlank(manager.getChannelCode()) || StringUtils.isBlank(manager.getChannelName()) ||
                    StringUtils.isBlank(manager.getNote())) {
                return AjaxResult.failed("缺少必要参数");
            }
            service.addChannle(manager);
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("新建渠道错误", e);
            return AjaxResult.failed("新建渠道错误");
        }
    }


    @RequestMapping("/deleteChannel")
    @ResponseBody
    public AjaxResult deleteChannel(HttpServletRequest request, ChannelManager manager) {
        try {
            if (manager.getId() == null) {
                return AjaxResult.failed("缺少必要参数");
            }
            int count = service.deleteChannelCode(manager.getId());
            if (count == 0) {
                return AjaxResult.failed("记录不存在");
            }
            return AjaxResult.success();
        } catch (Exception e) {
            log.error("删除渠道记录失败", e);
            return AjaxResult.failed("删除渠道记录失败");
        }
    }

}
