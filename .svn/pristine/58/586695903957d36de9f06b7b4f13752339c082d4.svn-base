package com.dome.sdkserver.util.business;


import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;

public abstract class GameUtils {
	
	/**
	 * 根据应用编码分析出游戏的类型
	 * D 手游 Y 页游  H h5
	 * @param appCode
	 * @return
	 */
	public static GameTypeEnum analyseGameType(String appCode){
		String appCodeTrim = appCode.trim();
		char ch = appCodeTrim.charAt(0);
		GameTypeEnum em=null;
		switch (ch){
//		case 'D':{
//			em=GameTypeEnum.mobilegame;
//			break;
//		}
		case 'Y':{
			em=GameTypeEnum.yeyougame;
			break;
		}
		case 'H':{
			em=GameTypeEnum.h5game;
			break;
		}
		default:{
			em=GameTypeEnum.mobilegame;
		}
			
		}
		return em;
	}
	
	/**
	 * 设置应用对象的一些基本类型字段
	 * 用于开放平台对象转换为h3和yeyou游戏对象
	 * BeanUtils.copyProperties(app, game);对于基本类型源为Integer，新对象为int，出现null转换为int，会出现NullPointerException 
	 * @param app
	 */
	public static void initAppParam(MerchantAppInfo app){
		app.setMerchantInfoId(0);
		app.setAppId(0);
		app.setStatus(0);
		app.setDelFlag(0);
	}
	
}
