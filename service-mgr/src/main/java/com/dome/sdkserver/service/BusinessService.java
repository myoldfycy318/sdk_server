package com.dome.sdkserver.service;


/**
 * BusinessService
 *
 * @author Zhang ShanMin
 * @date 2016/4/7
 * @time 10:46
 */
public interface BusinessService {

    /**
     * 导出商户信息
     *
     * @return
     */
    byte[] exportMerchantInfo() throws Exception;

    /**
     * 导出全量业务信息
     * @return
     */
    byte[] exportBusinessInfo()throws Exception;

    /**
     * 导出计费点信息
     * @return
     */
    byte[] exportChargePointInfo(String appCode)throws Exception;
}
