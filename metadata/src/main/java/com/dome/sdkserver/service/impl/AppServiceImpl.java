package com.dome.sdkserver.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;
import com.dome.sdkserver.metadata.dao.mapper.MerchantAppMapper;
import com.dome.sdkserver.metadata.dao.mapper.h5.H5GameMapper;
import com.dome.sdkserver.metadata.dao.mapper.yeyou.YeyouGameMapper;
import com.dome.sdkserver.metadata.entity.AbstractGame;
import com.dome.sdkserver.metadata.entity.AppVo;
import com.dome.sdkserver.metadata.entity.h5.H5Game;
import com.dome.sdkserver.metadata.entity.yeyou.YeyouGame;
import com.dome.sdkserver.service.AppService;
import com.dome.sdkserver.util.business.GameUtils;

/**
 * 整合手游、页游和H5，共用的方法
 * 根据应用编码查询应用
 * 除了查看应用外，很多地方不需要手游的游戏截图，上架的渠道，类型中文名称等，使用本类，效率更高
 * @author lilongwei
 *
 */
@Service
public class AppServiceImpl implements AppService {

	@Autowired
	private MerchantAppMapper merchantAppMapper;
	
	@Autowired
	private YeyouGameMapper<YeyouGame> yeyouGameMapper;
	
	@Autowired
	private H5GameMapper<H5Game> h5GameMapper;
	@Override
	public MerchantAppInfo selectApp(String appCode) {
		MerchantAppInfo app=null;
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		switch (em){
		case yeyougame:{
			AbstractGame game=yeyouGameMapper.select(appCode);
			app = new MerchantAppInfo();
			app.setStatus(game.getStatus());
			app.setAppId(game.getAppId());
			app.setUserId(game.getUserId());
			BeanUtils.copyProperties(game, app, "appId", "status", "delFlag"
					, "userId");
			app.setAppTypeName("页游");
			break;
		}
		case h5game: {
			AbstractGame game=h5GameMapper.select(appCode);
			app = new AppVo();
			app.setStatus(game.getStatus());
			app.setAppId(game.getAppId());
			app.setUserId(game.getUserId());
			BeanUtils.copyProperties(game, app, "appId", "status", "delFlag"
					, "userId");
			app.setAppTypeName("H5游戏");
			
			break;
		}
		
		default:{
			app = merchantAppMapper.queryApp(appCode);
		}
		}
		return app;
	}

}
