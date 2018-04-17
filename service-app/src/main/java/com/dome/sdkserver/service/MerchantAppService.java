package com.dome.sdkserver.service;

import java.util.List;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bo.ResultBo;

public interface MerchantAppService {
	/**
	 * 新增商户应用
	 * @param merchantAppInfo
	 * @return
	 */
	String doSaveMerchantApp(MerchantAppInfo merchantAppInfo);
	
	/**
	 * 查询应用列表
	 * @param merchantId 商户ID
	 * @param p 
	 */
	List<MerchantAppInfo> getAppListByMertId(Integer merchantId, Paginator p);
	
	/**
	 * 查询应用总数量
	 * @param merchantId
	 * @return
	 */
	Integer getAppCountByMertId(Integer merchantId);
	
	/**
	 * 根据应用ID得到应用对象
	 * @param appId
	 * @param merchantId
	 * @return
	 */
	MerchantAppInfo getAppInfoByIdAndMertId(Integer appId,Integer merchantId);
	
	/**
	 * 删除
	 * @param app
	 * @param merchantId
	 */
	ResultBo doDel(MerchantAppInfo app,Integer merchantId);
	
	/**
	 * 查询应用列表，merchantId=null查询所有应用
	 * @param merchantId 商户ID
	 */
	List<MerchantAppInfo> getNewAppListByMerchantId(Integer merchantId);
	
	/**
	 * 查询应用，运营查询应用不需要提供商户id（merchantId=null）
	 * @param appId
	 * @param merchantId
	 * @return
	 */
	MerchantAppInfo getNewAppInfoByIdAndMertId(String appCode,Integer merchantId);
	
	public MerchantAppInfo queryApp(String appCode);
	
	public String doUpdateAppStatus(MerchantAppInfo app, int status);

	//查询手游业务类型是否需要计费点
	boolean needChargePoint(String appCode);
}
