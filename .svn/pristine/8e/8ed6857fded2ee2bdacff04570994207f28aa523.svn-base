package com.dome.sdkserver.service.notify;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.constants.Constant.ApprovalMenuEumu;
import com.dome.sdkserver.constants.Constant.NotifyNodeEnum;


/**
 * NotifyService
 *
 * @author Zhang ShanMin
 * @date 2016/3/30
 * @time 10:52
 */
public interface NotifyService {

    /**
     * 审批结果通知
     *
     * @param notifyNodeEnum  消息通知节点枚举
     * @param merchantAppInfo 开发者信息
     * @return
     */
    public boolean approveResultNotify(NotifyNodeEnum notifyNodeEnum, MerchantAppInfo merchantAppInfo);

    /**
     * 合作方申请通知
     *
     * @param notifyNodeEnum 消息通知节点枚举
     * @param approvalMenuEumu 审批菜单枚举
     * @return
     */
    public boolean partnerApplyNotify(NotifyNodeEnum notifyNodeEnum, ApprovalMenuEumu approvalMenuEumu);
}
