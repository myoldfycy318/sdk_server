package com.dome.sdkserver.metadata.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bo.SearchMerchantAppBo;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import com.dome.sdkserver.metadata.entity.AppVo;

/**
 * 应用信息Mapper
 * @author hexiaoyi
 */
@Repository
public interface MerchantAppMapper extends IBaseMapperDao<MerchantAppInfo, Long> {
	
	/**
	 * 根据应用ID修改商户应用
	 * @param merchantAppInfo
	 */
	void editMerchantAppInfoById(@Param("entity")MerchantAppInfo merchantAppInfo);
	/**
	 * 根据商户应用id得到应用列表
	 * @param appId
	 * @return
	 */
	MerchantAppInfo getAppById(@Param("appId")Integer appId);
	
	/**
	 * 根据商户应用id和商户id得到应用列表
	 * @param appId
	 * @param merchantId
	 * @return
	 */
	MerchantAppInfo getAppByIdAndMertId(@Param("appId")Integer appId,@Param("merchantId")Integer merchantId);
	
	/**
	 * 合作方的应用列表
	 * 不需要看到商户编码和商户名称，不需要条件过滤
	 * @param merchantId
	 * @param p
	 * @return
	 */
	List<MerchantAppInfo> getAppListByMerchantId(Integer merchantId);
	
	/**
	 * 根据查询条件得到应用列表
	 * 审批页面的应用列表
	 * @param searchMerchantAppBo
	 * @return
	 */
	List<AppVo> getAppListByCondition(@Param("entity")SearchMerchantAppBo searchMerchantAppBo);
	
	/**
	 * 根据查询条件得到应用数量
	 * @param searchMerchantAppBo
	 * @return
	 */
	int getAppInfoCountByCondition(@Param("entity")SearchMerchantAppBo searchMerchantAppBo);
	
	/**
	 * 根据商户id分页查询应用列表
	 * @param merchantId
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<MerchantAppInfo> getAppListByMerchantId(@Param("merchantId")Integer merchantId
			,@Param("p")Paginator p);
	
	/**
	 * 根据商户id查询应用总数
	 * @param merchantId
	 * @return
	 */
	Integer getAppCountByMertId(@Param("merchantId")Integer merchantId);
	
	/**
	 * 查询商户的所有应用，merchantId=null查询所有应用，只有运营角色可以。数据统计功能页面的下拉列表框使用
	 * @param merchantId
	 * @return
	 */
	List<MerchantAppInfo> getNewAppListByMerchantId(@Param("merchantId")Integer merchantId);
	
	/**
	 * 根据商户应用id和商户id得到应用列表
	 * 运营用户查询应用时不需要提供商户id
	 * @param appId
	 * @param merchantId
	 * @return
	 */
	MerchantAppInfo getNewAppByIdAndMertId(@Param("appCode")String appCode,@Param("merchantId")Integer merchantId);
	
	int addApp(MerchantAppInfo app);
	
	void updateApp(MerchantAppInfo app);
	
	MerchantAppInfo queryApp(@Param("appCode")String appCode);

	
	void updateAppCode(@Param("appId")int appId, @Param("appCode")String appCode);
	
	void updateAppStatus(@Param("appId")int appId, @Param("status")int status);
	
	
	// 以下为应用后台管理接口，面向运营人员审批等
	
	void updateMgrAppStatus(MerchantAppInfo app);
	
	MerchantAppInfo queryAppByName(@Param("name")String name, @Param("merchantId")int merchantId, @Param("gameType")String gameType);
	
	void saveAppChannel(@Param("appCode")String appCode, @Param("channel")String channel);
	
	List<Map<String, String>> queryAppChannel(@Param("appCode")String appCode);
	
	void delAppChannel(@Param("appCode")String appCode);
	
	void deleteApp(@Param("appCode")String appCode);

	/**
     * 修改登录和支付回调地址
     * @param app
     * @return
     */
    int modfiyCallbackUrl(@Param("app")MerchantAppInfo app);

    //查询手游业务类型
    Integer selectAppBizType(@Param("appCode") String appCode);

    int updateAppBizType(@Param("appCode") String appCode, @Param("bizType") Integer bizType);
}
