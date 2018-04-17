package com.dome.sdkserver.controller.channel;

import com.dome.sdkserver.bo.DiscountThreshold;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.service.DiscountThresholdService;
import com.dome.sdkserver.view.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

@Controller
@RequestMapping("/channel/kouliang/discount")
public class DiscountThresholdController extends BaseController{
	@Resource
    private DiscountThresholdService discountThresholdService;


    @ResponseBody
    /*设置CPA*/
    @RequestMapping(value="/setCpaDiscount")
    public AjaxResult setcpa(BigDecimal discount,Integer activeThreshold,String channelCode){
        //  判断请求参数是否合法
        if(discount==null || activeThreshold==null || StringUtils.isBlank(channelCode)){
            return AjaxResult.failed("参数不能为空");
        }

        DiscountThreshold dt = new DiscountThreshold();
        dt.setDiscount(discount);
        dt.setActiveThreshold(activeThreshold);
        dt.setChannelCode(channelCode);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creatTime = sdf.format(date);
        dt.setCreateTime(Timestamp.valueOf(creatTime));
        Map<String,Object> data = new HashMap<String, Object>();
        try{
            discountThresholdService.setCpa(dt);
            //返回设置时间
            DiscountThreshold dtCreatTime = discountThresholdService.searchCreateTime(dt);
            //设置CPA时,creatTime就是更新时间
            data.put("updateTime", dtCreatTime.getCreateTime());
            data.put("status", 1);
            data.put("statusDesc", "已设置");
        }catch (Exception e){
            log.error("设置CPA失败",e);
            return AjaxResult.failed("设置CPA失败");
        }
        return AjaxResult.success(data);
    }


    /*修改CPA*/
    @RequestMapping(value="/changeCpaDiscount")
    @ResponseBody
    public AjaxResult changeCpa(BigDecimal discount,Integer activeThreshold,String channelCode){
        //  判断请求参数是否合法
        if(discount==null || activeThreshold==null || StringUtils.isBlank(channelCode)){
            return AjaxResult.failed("参数不能为空");
        }

        DiscountThreshold dt = new DiscountThreshold();
        dt.setDiscount(discount);
        dt.setActiveThreshold(activeThreshold);
        dt.setChannelCode(channelCode);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateTime = sdf.format(date);
        dt.setUpdateTime(Timestamp.valueOf(updateTime));
        Map<String,Object> data = new HashMap<String, Object>();
        try {
            discountThresholdService.changeCpa(dt);
            // 返回更新时间
            DiscountThreshold dtUpdateTime = discountThresholdService.searchUpdateTime(dt);
            //修改CPA时,updateTime就是更新时间
            data.put("updateTime",dtUpdateTime.getUpdateTime());
        } catch (Exception e) {
            log.error("修改CPA失败", e);;
            return AjaxResult.failed("修改CPA失败");
        }
        return AjaxResult.success(data);
    }


    /*设置CPS*/
    @RequestMapping(value="/setCpsDiscount")
    @ResponseBody
    public AjaxResult setCps(BigDecimal discount,BigDecimal rechargeAmountDiscount,BigDecimal payingUserDiscount,
                             BigDecimal payingThreshold,String channelCode){
        //  判断请求参数是否合法
        if(discount==null || rechargeAmountDiscount==null || payingUserDiscount==null ||
                payingThreshold==null || StringUtils.isBlank(channelCode)){
            return AjaxResult.failed("参数不能为空");
        }

        DiscountThreshold dt = new DiscountThreshold();
        dt.setDiscount(discount);
        dt.setRechargeAmountDiscount(rechargeAmountDiscount);
        dt.setPayingUserDiscount(payingUserDiscount);
        dt.setPayingThreshold(payingThreshold);
        dt.setChannelCode(channelCode);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creatTime = sdf.format(date);
        dt.setCreateTime(Timestamp.valueOf(creatTime));
        Map<String,Object> data = new HashMap<String, Object>();
        try{
            discountThresholdService.setCps(dt);
            // 返回设置时间
            DiscountThreshold dtCreatTime = discountThresholdService.searchCreateTime(dt);
            //设置CPS时,creatTime就是更新时间
            data.put("updateTime", dtCreatTime.getCreateTime());
            data.put("status", 1);
            data.put("statusDesc", "已设置");
        }catch (Exception e){
            log.error("设置CPS失败",e);
            return AjaxResult.failed("设置CPS失败");
        }
        return AjaxResult.success(data);
    }


    /*修改CPS*/
    @RequestMapping(value="/changeCpsDiscount")
    @ResponseBody
    public AjaxResult changeCps(BigDecimal discount,BigDecimal rechargeAmountDiscount,BigDecimal payingUserDiscount,
                                BigDecimal payingThreshold,String channelCode){
        //  判断请求参数是否合法
        if(discount==null || rechargeAmountDiscount==null || payingUserDiscount==null ||
                payingThreshold==null || StringUtils.isBlank(channelCode)){
            return AjaxResult.failed("参数不能为空");
        }
        DiscountThreshold dt = new DiscountThreshold();
        dt.setDiscount(discount);
        dt.setRechargeAmountDiscount(rechargeAmountDiscount);
        dt.setPayingUserDiscount(payingUserDiscount);
        dt.setPayingThreshold(payingThreshold);
        dt.setChannelCode(channelCode);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateTime = sdf.format(date);
        dt.setUpdateTime(Timestamp.valueOf(updateTime));
        Map<String,Object> data = new HashMap<String, Object>();
        try {
            discountThresholdService.changeCps(dt);
            // 返回更新时间
            DiscountThreshold dtUpdateTime = discountThresholdService.searchUpdateTime(dt);
            //修改CPS时,updateTime就是更新时间
            data.put("updateTime",dtUpdateTime.getUpdateTime());
        } catch (Exception e) {
            log.error("修改CPS失败", e);
            return AjaxResult.failed("修改CPS失败");
        }
        return AjaxResult.success(data);
    }



    /*搜索渠道扣量信息*/
    @RequestMapping(value="/search",method=RequestMethod.GET)
    @ResponseBody
    public AjaxResult search(String searchName, Integer searchStatus,Integer pageNo, Integer pageSize){
        //检查必填参数
        if( pageNo==null || pageSize==null){
            return AjaxResult.failed("参数不能为空");
        }
        if(pageNo!=null && pageNo<1){
            return AjaxResult.failed("查询页码不能小于1");
        }

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            //查询dome_second_channel表中的code
            Set<String> secondTableChannelCode = discountThresholdService.selectSecondTable();
            //查询扣量表中的code
            Set<String> discountTableChannelCode = discountThresholdService.selectDiscountTable();
            for (String secondCode : secondTableChannelCode) {
                if (discountTableChannelCode.add(secondCode)) {
                    DiscountThreshold dis = new DiscountThreshold();
                    dis.setChannelCode(secondCode);
                    //向扣量表中添加dome_second_channel表中的code
                    discountThresholdService.insertChannelCode(dis);
                }
            }

            DiscountThreshold dt = new DiscountThreshold();
            dt.setName(searchName);
            dt.setStatus(searchStatus);

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            List<DiscountThreshold> listDate = null;
            //查询数据总条数
            int count = discountThresholdService.countSecond(dt);
            //判断共有多少页数据
            int pageCount = 0;
            if (count % pageSize != 0 && count > pageSize) {
                pageCount = count / pageSize + 1;
            } else if (count % pageSize == 0 && count >= pageSize) {
                pageCount = count / pageSize;
            } else if (count > 0 && count < pageSize) {
                pageCount = 1;
            }
            //判断查询页数是否超过范围,超过范围返回第一页数据
            if (pageNo > pageCount && count > 0) {
                pageNo = 1;
            }
            listDate = discountThresholdService.searchChannel(dt, pageSize, (pageNo - 1) * pageSize);
//            Map<String, Object> data = new HashMap<String, Object>();
            for (DiscountThreshold discountThreshold : listDate) {
                //查到数据
                if (!StringUtils.isEmpty(discountThreshold.getName())) {
                    Map<String, Object> dataMap = new HashMap<String, Object>();
                    dataMap.put("channelCode", discountThreshold.getChannelCode());
                    dataMap.put("name", discountThreshold.getName());
                    //通过parentId判断渠道等级: 一级渠道为0 ,二级渠道不为0
                    Integer ParentId = discountThreshold.getParentId();
                    //判断渠道等级
                    if (ParentId == 0) {
                        dataMap.put("level", "一级");
                    } else {
                        dataMap.put("level", "二级");
                    }
                    //合作方式cooperType为 1表示 CPA ,为2表示 CPS
                    String cooperType = null;
                    if (discountThreshold.getCooperType() == 1) {
                        cooperType = "CPA";
                    } else if (discountThreshold.getCooperType() == 2) {
                        cooperType = "CPS";
                    }
                    dataMap.put("activeThreshold", discountThreshold.getActiveThreshold());
                    dataMap.put("rechargeAmountDiscount", discountThreshold.getRechargeAmountDiscount());
                    dataMap.put("payingUserDiscount", discountThreshold.getPayingUserDiscount());
                    dataMap.put("payingThreshold", discountThreshold.getPayingThreshold());
                    dataMap.put("cooperType", cooperType);
                    dataMap.put("discount", discountThreshold.getDiscount());
                    dataMap.put("status", discountThreshold.getStatus());
                    //添加状态说明及更新时间
                    if (discountThreshold.getStatus() == 0) {
                        dataMap.put("statusDesc", "未设置");
                        dataMap.put("updateTime", discountThreshold.getUpdateTime());
                    } else if (discountThreshold.getStatus() == 1) {
                        dataMap.put("statusDesc", "已设置");
                        if (discountThreshold.getChangeFlag() == 0) {
                            //更新时间就是该记录的创建时间
                            dataMap.put("updateTime", discountThreshold.getCreateTime());
                        } else if (discountThreshold.getChangeFlag() == 1) {
                            //更新时间
                            dataMap.put("updateTime", discountThreshold.getUpdateTime());
                        }
                    }
                    list.add(dataMap);
                    data.put("totalCount", count);
                    //返回查询到数据的页数
                    data.put("list", list);
                }
            }

            if (count == 0) {
                data.put("totalCount", count);
                data.put("list", list);
            }
        }catch (Exception e){
            log.error("查询失败",e);
            return AjaxResult.failed("查询失败");
        }
        return AjaxResult.success(data);
    }



}
