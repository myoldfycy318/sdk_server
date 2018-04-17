package com.dome.sdkserver.service;


import com.dome.sdkserver.bo.CallbackAudit;
import com.dome.sdkserver.bo.MerchantAppInfo;

import java.util.List;

public interface CallbackAuditService {

    public List<CallbackAudit> queryCallbackAudit(CallbackAudit ca ,Integer begin ,Integer pageSize);
    public int countCallbackAudit(CallbackAudit ca);
    public CallbackAudit queryById(Integer id);
    public CallbackAudit queryByAppCode(String appCode);
    public void updateStatusAndCallbackUrl (CallbackAudit callbackAudit ,boolean status);
    public void updateDelFlagAndCallbackUrl (CallbackAudit callbackAudit);

    public Object queryAllByAppCode (String appCode);


}
