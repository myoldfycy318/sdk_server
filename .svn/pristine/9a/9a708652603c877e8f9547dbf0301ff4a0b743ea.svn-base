package com.dome.sdkserver.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.AppInfo;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.PayAround;
import com.dome.sdkserver.bo.PayAroundVo;
import com.dome.sdkserver.constants.PayAroundEnum;
import com.dome.sdkserver.metadata.dao.mapper.PayAroundMapper;
import com.dome.sdkserver.service.PayAroundService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.view.AjaxResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heyajun on 2017/4/11.
 */
@Service
public class PayAroundServiceImpl implements PayAroundService {


    @Autowired
    private PayAroundMapper payAroundMapper;

    private static Logger log = LoggerFactory.getLogger(PayAroundServiceImpl.class);

    /**
     * 查询绕行记录, 将查询的绕行记录对象属性复制到PayAroundVo ,将其返回.
     * PayAroundVo对象中增加了对绕行状态的说明字段 (isAroundDesc)
     * @param payAround
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Map<String,Object> query(PayAround payAround, Integer pageNo, Integer pageSize) {
        Map<String,Object> data = new HashMap<String,Object>();
        List<PayAroundVo> list = new ArrayList<PayAroundVo>();
        //使用分页插件查询分页
        Page<?> page = PageHelper.startPage(pageNo, pageSize);
        List<PayAround> result = payAroundMapper.queryPayAround(payAround);
        if(!CollectionUtils.isEmpty(result)){
            for(PayAround around : result){
                PayAroundVo vo = new PayAroundVo();
                BeanUtils.copyProperties(around, vo);
                vo.setIsAroundDesc(around.getIsAround() == 0 ? "关闭" : "开启");
                list.add(vo);
            }
        }
        data.put("list",list);
        data.put("totalCount",page.getTotal());
//        System.out.println("data:"+JSONObject.toJSONString(data));
        return data;
    }

    @Override
    public PayAround selectByAppCode(String appCode) {
        return payAroundMapper.selectByAppCode(appCode);
    }

    @Override
    public PayAround selectById(Integer id) {
        return payAroundMapper.selectById(id);
    }

    @Override
    public Map<String,Object> selectAppInfo(String appCode, String appName, Integer pageNo, Integer pageSize) {
        Map<String,Object> data = new HashMap<String,Object>();
        Page<?> page = PageHelper.startPage(pageNo, pageSize);
        List<MerchantAppInfo> list = payAroundMapper.selectAppInfo(appCode, appName);
//        System.out.println("list:"+JSONObject.toJSONString(list));
        data.put("list",list);
        data.put("totalCount",page.getTotal());
        return data;
    }

    /**
     * 添加绕行记录
     * @param payAround
     * @return
     */
    @Override
    public AjaxResult add(PayAround payAround) {
        //添加数据默认绕行状态为关闭
        payAround.setIsAround(PayAroundEnum.关闭.getCode());
        int i = payAroundMapper.addPayAround(payAround);
        if(i == 0){
            return AjaxResult.failed("增加支付绕行失败");
        }
        return AjaxResult.success();
    }

    /**
     * 保存支付绕行支付方式, 该接口调用sdk接口同步数据
     * @param payAround
     * @return
     */
    @Override
    @Transactional
    public AjaxResult modify(PayAround payAround) {
        int i = payAroundMapper.modifyPayAround(payAround);
        if(i == 0){
            return AjaxResult.failed("保存支付绕行失败");
        }
        log.info("调用支付绕行同步接口 start>>>>>>>");
        AjaxResult result = synPayAround(payAround);
        if(!AjaxResult.isSucees(result)){
            return result;
        }
        log.info("调用支付绕行同步接口 end>>>>>>>");
        return AjaxResult.success();
    }

    @Override
    @Transactional
    public AjaxResult delete(PayAround payAround) {
        //删除支付绕行
        boolean success = payAroundMapper.deletePayAround(payAround.getId());
        if(!success){
            return AjaxResult.failed("删除支付绕行记录失败");
        }
        log.info("调用支付绕行同步删除接口 start>>>>>>>");
        AjaxResult result = snyDeletPayAround(payAround.getAppCode());
        if(!AjaxResult.isSucees(result)){
            return result;
        }
        log.info("调用支付绕行同步删除接口 end>>>>>>>");
        return AjaxResult.success();
    }

    private String snyDeletePayAroundUrl = "http://sdkserver.domestore.cn/syncIosSwitch/delByAppCode";
    public AjaxResult snyDeletPayAround(String appCode){
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("appCode",appCode));
        String response = ApiConnector.post(snyDeletePayAroundUrl, pairs);
        log.info("调用支付绕行同步删除接口 {}, 返回结果:{}", "http://sdkserver.domestore.cn/syncIosSwitch/delByAppCode",
                response);
        JSONObject result = JSONObject.parseObject(response);
//        System.out.println("reslut:"+JSONObject.toJSONString(result));
        result.getIntValue("responseCode");
        if(result.getIntValue("responseCode") != AjaxResult.CODE_SUCCESS){
            log.error("调用支付绕行同步删除接口: {} ,返回{}", snyDeletePayAroundUrl, result.getString("errorMsg"));
            return AjaxResult.failed("调用支付绕行同步删除接口失败");
        }
        return AjaxResult.success();

    }



    private String SynPayAroundUrl = "http://sdkserver.domestore.cn/syncIosSwitch/insertOrUpdate";
    public AjaxResult synPayAround (PayAround payAround){
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("appCode",payAround.getAppCode()));
        pairs.add(new BasicNameValuePair("appName",payAround.getAppName()));
        pairs.add(new BasicNameValuePair("isAround",String.valueOf(payAround.getIsAround())));
        pairs.add(new BasicNameValuePair("payType",payAround.getPayType()));
        String response = ApiConnector.post(SynPayAroundUrl, pairs);
        log.info("调用支付绕行同步删除接口 {}, 返回结果:{}", "http://sdkserver.domestore.cn/syncIosSwitch/insertOrUpdate",
                response);
        JSONObject result = JSONObject.parseObject(response);
        result.getIntValue("responseCode");
        if(result.getIntValue("responseCode") != AjaxResult.CODE_SUCCESS){
            log.error("调用支付绕行同步接口: {} ,返回{}", SynPayAroundUrl, result.getString("errorMsg"));
            return AjaxResult.failed("调用支付绕行同步接口失败");
        }
        return AjaxResult.success();
    }



}
