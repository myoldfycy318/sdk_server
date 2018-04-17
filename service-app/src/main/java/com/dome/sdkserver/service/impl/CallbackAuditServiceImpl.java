package com.dome.sdkserver.service.impl;


import com.dome.sdkserver.bo.CallbackAudit;
import com.dome.sdkserver.metadata.dao.mapper.CallbackAuditMapper;
import com.dome.sdkserver.service.CallbackAuditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class CallbackAuditServiceImpl implements CallbackAuditService {
    @Resource
    private CallbackAuditMapper callbackAuditMapper;

    /**
     * 通过appCode查询表中有没有还没通过的申请记录
     * @param appCode
     * @return
     */
    @Override
    public CallbackAudit queryByAppCode(String appCode) {
        return callbackAuditMapper.queryByIdOrAppCode(appCode , null );
    }

    /**
     * 申请修改回调地址
     * @param ca
     * @return
     */
    @Override
    public void insert(CallbackAudit ca) {
        //删除应用
        CallbackAudit callbackAudit = new CallbackAudit();
        callbackAudit.setAppCode(ca.getAppCode());
        callbackAudit.setDelFlag(1);
        callbackAuditMapper.updateByAppCode(callbackAudit);
        //添加应用
        callbackAuditMapper.insert(ca);
    }

    @Override
    public boolean judgeChannelCodeIsNotEmpty(String appCode) {
        List<String> channelCodes = callbackAuditMapper.queryAppChannelCode(appCode);
        if(channelCodes.size() == 0){
            return false;
        }
        return true;
    }


}
