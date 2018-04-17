/**
 *
 */
package com.dome.sdkserver.service.pay.qbao;

import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.metadata.entity.qbao.PayTransEntity;
import com.dome.sdkserver.service.pay.qbao.bo.SdkDopayRequest;
import com.dome.sdkserver.service.pay.qbao.bo.SdkPayRequest;
import com.dome.sdkserver.service.pay.qbao.bo.SdkPayResponse;

/**
 * @author mazhongmin
 */
public interface SdkPayService {

    /**
     * 认证超时时间（秒）
     */
    static final int AUTH_TIME_OUT = 600;

    //SDK支付业务类型
    static final String BIZ_TYPE = "800001";

    //宝券 业务类型
    static final String BQ_BIZ_TYPE = "48";


    /**
     * SDK支付
     *
     * @param sdkDopayBo
     * @return
     */
    SdkPayResponse dopay(SdkDopayRequest sdkDopayBo);

    /**
     * SDK获取用户信息
     *
     * @param payRequest
     * @return
     */
    SdkPayResponse dealWithPayRequest(SdkPayRequest payRequest);

    /**
     * 创建页游订单
     *
     * @param payRequest
     * @param appInfoEntity
     * @return
     */
    SdkPayResponse createWebGameOrder(SdkPayRequest payRequest, AppInfoEntity appInfoEntity);

    /**
     * 更新钱宝支付
     *
     * @param entity
     * @param suffix
     * @return
     */
    boolean updatePayTrans(PayTransEntity entity, String suffix);

    /**
     * 钱宝混合支付
     *
     * @param payReques
     * @return
     */
    public SdkPayResponse qbBlendPay(SdkPayRequest payReques);

    /**
     * 查询用户可用余额
     * @param payReques
     * @return
     */
    public Long queryAvailableBalance(SdkPayRequest payReques);


}
