package com.dome.sdkserver.service;


import com.dome.sdkserver.bo.PaySwitch;
import com.dome.sdkserver.view.AjaxResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaySwitchService {
    public List<PaySwitch> query(PaySwitch ps,Integer begin,Integer pageSize);
    public PaySwitch queryByAppCode(String appCode);
    public int count(PaySwitch ps);

    public List<String> queryCanAddAppCode();
    public AjaxResult modify(PaySwitch ps) throws Exception;
    public int insert (PaySwitch ps);

}
