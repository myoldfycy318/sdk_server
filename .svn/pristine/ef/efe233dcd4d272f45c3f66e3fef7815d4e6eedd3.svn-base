package com.dome.sdkserver.service.newgame;

import java.util.List;
import java.util.Map;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.metadata.entity.AbstractGame;

/**
 * 支持页游和h5
 * @author lilongwei
 *
 */
public interface GameService {

	String addGame(String type, AbstractGame game);
	
	Map<String, Object> selectList(String type, int userId);
	
	AbstractGame select(String type, String code);
	
	String update(String type, AbstractGame game);
	
	String delete(String type, int id);
	
	String updateH5GameStatus(String code, int status);
	
	String updateYeyouGameStatus(String code, int status);
	
	int updateH5PayCallBackUrl(String code, String payCallBackUrl);

	//TODO 原来修改回调只能修改支付回调地址,现在需求新增了登录回调字段,该方法现可以修改登录和支付回调地址
//	int updateYeyouPayCallBackUrl(String code, String payCallBackUrl);
	int updateYeyouPayCallBackUrl(MerchantAppInfo app);

}
