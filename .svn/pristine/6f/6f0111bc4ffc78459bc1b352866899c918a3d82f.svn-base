package com.dome.sdkserver.service;

import com.dome.sdkserver.bo.AppInfo;
import com.dome.sdkserver.bo.PayAround;
import com.dome.sdkserver.view.AjaxResult;

import java.util.List;
import java.util.Map;

/**
 * Created by heyajun on 2017/4/11.
 */
public interface PayAroundService {

    public Map<String,Object> query (PayAround payAround, Integer pageNo, Integer pageSize);

    public PayAround selectByAppCode (String appCode);

    public PayAround selectById (Integer id);

    public Map<String,Object> selectAppInfo(String appCode, String appName, Integer pageNo, Integer pageSize);

    public AjaxResult add(PayAround payAround);

    public AjaxResult modify(PayAround payAround);

    public AjaxResult delete(PayAround payAround);
}
