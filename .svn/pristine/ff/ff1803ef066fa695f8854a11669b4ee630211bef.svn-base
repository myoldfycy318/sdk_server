package com.dome.sdkserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.PaySwitch;
import com.dome.sdkserver.constants.PaySwitchEnum;
import com.dome.sdkserver.service.PaySwitchService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.view.AjaxResult;
import org.apache.commons.lang3.StringUtils;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller()
@RequestMapping("/paySwitch")
public class PaySwitchController extends BaseController{
    @Autowired
    private PaySwitchService paySwitchService;

    @Autowired
    private PropertiesUtil domainConfig;

    /**
     * 查询支付开关信息
     * @param paySwitch
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public AjaxResult query(@ModelAttribute("paySwitch") PaySwitch paySwitch ,Integer pageNo,Integer pageSize){
        Map<String,Object> data = new HashMap<String,Object>();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try{
            log.info("请求接口：/paySwitch/query,请求参数：paySwitch=" + JSONObject.toJSONString(paySwitch)+" 'pageNo:'"+pageNo+" 'pageSize:'"+pageSize);
            if(pageNo==null || pageSize==null){
                return AjaxResult.failed("缺少参数");
            }
            // 添加游戏code到pay_switch表中
            List<String> appCodes = paySwitchService.queryCanAddAppCode();
            for(String appCode : appCodes){
                PaySwitch p = new PaySwitch();
                p.setAppCode(appCode);
                paySwitchService.insert(p);
            }
            int count = paySwitchService.count(paySwitch);
            if(count==0){
                data.put("list",list);
                data.put("count",count);
                return AjaxResult.success(data);
            }
//            查询总数不是pageSize的倍数时,最后一页的最后一条的count值为pageNo*(pageSize+1)
            int lastPageCount = 0 ;
            int totalPage = 0;
            if (count % pageSize != 0) {
                totalPage = count/pageSize + 1;
            }else{
                totalPage = count/pageSize;
            }
            lastPageCount = pageSize*totalPage;
            // 超过查询总数时返回第一页数据
            if(pageNo*pageSize>lastPageCount || pageNo<=0) {
                pageNo = 1;
            }
            int begin = (pageNo-1)*pageSize;
            List<PaySwitch> pss = paySwitchService.query(paySwitch,begin,pageSize);
            for(PaySwitch ps : pss){
                String[] ways = ps.getPayWay().split("\\,");
                StringBuilder payWayDesc = new StringBuilder("");
                StringBuilder payWay = new StringBuilder("");
                for(int i = 0;i<ways.length;i++){
                    payWay.append(ways[i]);
                    payWayDesc.append(PaySwitchEnum.getName(Integer.valueOf(ways[i])));
                    if(ways.length>0 && i+1<ways.length){
                        payWay = payWay.append(",");
                        payWayDesc.append("/");
                    }
                }
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("merchantCode",ps.getMerchantCode());
                map.put("merchantFullName",ps.getMerchantFullName());
                map.put("appCode",ps.getAppCode());
                map.put("appName",ps.getAppName());
                //支付方式
                map.put("payWay",payWay);
                map.put("payWayDesc",payWayDesc.toString());
                map.put("status",ps.getStatus());
                map.put("statusDesc",ps.getStatus()==0?"是":"否");
                list.add(map);
            }
            data.put("count", count);
            data.put("list",list);
        }catch (Exception e){
            log.error("查询支付开关失败",e);
//            e.printStackTrace();
            return AjaxResult.failed("查询支付开关失败");
        }
        return AjaxResult.success(data);
    }


    /**
     * 编辑支付方式开关 接收支付方式为"1,2"形式的字符串
     * @param appCode
     * @param payWay
     * @param status
     * @return
     */
    @RequestMapping("/modify")
    @ResponseBody
    public AjaxResult updateByAppCode(String appCode , String payWay , Integer status){
        try{
            log.info("请求接口：/paySwitch/modify,请求参数： 'appCode':"+appCode+" 'payWay':"+payWay+" 'status':" +status);
            if(StringUtils.isEmpty(appCode)){
                return AjaxResult.failed("缺少应用参数");
            }
            if(StringUtils.isEmpty(payWay) || StringUtils.isEmpty(appCode)){
                return AjaxResult.failed("未选择支付方式");
            }
            //查询是appCode是否存在
            if(paySwitchService.queryByAppCode(appCode)==null){
                return AjaxResult.failed("应用不存在");
            }
            //是否支持宝券限制 0 支持,1不支持
            if(status != null && status>1){
                return AjaxResult.failed("非法参数");
            }
            //检查支付方式 目前只支持 支付宝 钱宝支付
            String[] ways = payWay.split("\\,");
            Integer sta = null;
            //数组中是否包含钱宝的标记
            boolean containFlag = false;
            for(int i = 0;i<ways.length;i++){
                if(!PaySwitchEnum.inStatus(Integer.valueOf(ways[i]))){
                    return AjaxResult.failed("含有未支持的支付方式");
                }
                if(Integer.valueOf(ways[i])==2){
                    containFlag = true;
                    sta = status;
                }
            }
            if(containFlag && status==null){
                return AjaxResult.failed("未选择是否支持宝券支付");
            }
            PaySwitch ps = new PaySwitch();
            ps.setAppCode(appCode);
            ps.setPayWay(payWay.toString());
            ps.setStatus(sta);
            AjaxResult result = paySwitchService.modify(ps);
            if( !AjaxResult.isSucees(result)){
                return AjaxResult.failed(result.getErrorMsg());
            }
            return AjaxResult.success();
        }catch (Exception e){
            log.error("保存支付开关错误",e);
            return AjaxResult.failed("保存支付开关错误");
        }
    }


}
