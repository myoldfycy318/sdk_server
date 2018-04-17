package com.dome.sdkserver.service.newgame.impl;

import static com.dome.sdkserver.common.Constants.H5_BINGQU_MARK;
import static com.dome.sdkserver.constants.AppStatusEnum.getStatusDesc;
import static com.dome.sdkserver.constants.AppStatusEnum.online_adjust;
import static com.dome.sdkserver.constants.AppStatusEnum.test;
import static com.dome.sdkserver.constants.AppStatusEnum.wait_access;
import static com.dome.sdkserver.constants.AppStatusEnum.wait_shelf;
import static com.dome.sdkserver.constants.newgame.GameTypeEnum.h5game;
import static com.dome.sdkserver.constants.newgame.GameTypeEnum.yeyougame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dome.sdkserver.bo.CallbackAudit;
import com.dome.sdkserver.bo.MerchantAppInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dome.sdkserver.bo.SearchChargePointBo;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.h5.H5GameMapper;
import com.dome.sdkserver.metadata.dao.mapper.yeyou.YeYouCpMapper;
import com.dome.sdkserver.metadata.dao.mapper.yeyou.YeyouGameMapper;
import com.dome.sdkserver.metadata.entity.AbstractGame;
import com.dome.sdkserver.metadata.entity.h5.H5Game;
import com.dome.sdkserver.metadata.entity.yeyou.YeYouCp;
import com.dome.sdkserver.metadata.entity.yeyou.YeyouGame;
import com.dome.sdkserver.service.newgame.GameService;
@Service
public class GameServiceImpl implements GameService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YeyouGameMapper<YeyouGame> yeyouGameMapper;
	
	@Autowired
	private YeYouCpMapper<YeYouCp> yeYouCpMapper;
	@Autowired
	private H5GameMapper<H5Game> h5GameMapper;
	
	@Override
	public Map<String, Object> selectList(String type, int userId) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<AbstractGame> gameList = null;
		if (yeyougame.name().equalsIgnoreCase(type)){
			gameList = yeyouGameMapper.selectList(userId);
		} else if (h5game.name().equalsIgnoreCase(type)){
			gameList = h5GameMapper.selectList(userId);
		}
		dataMap.put("totalCount", gameList.size());
		dataMap.put("list", gameList);
		return dataMap;
	}

	@Override
	public AbstractGame select(String type, String code) {
		AbstractGame game=null;
		if (yeyougame.name().equalsIgnoreCase(type)){
			game=yeyouGameMapper.select(code);
		} else if (h5game.name().equalsIgnoreCase(type)){
			game=h5GameMapper.select(code);
		}
		return game;
	}

	@Override
	public String update(String type, AbstractGame game) {
		int line=0;
		// 编辑和新增时都设置应用status为未接入
		game.setStatus(AppStatusEnum.unaccess.getStatus());
		if (yeyougame.name().equalsIgnoreCase(type)){
			if (!checkYeyouGameName(game.getAppCode(), game.getAppName())){
				return "游戏名称已被使用，游戏名称："+game.getAppName();
			}
			line=yeyouGameMapper.update(game);
		} else if (h5game.name().equalsIgnoreCase(type)){
			if (!checkH5GameName(game.getAppCode(), game.getAppName())){
				return "游戏名称已被使用，游戏名称："+game.getAppName();
			}
			line=h5GameMapper.update(game);
		}
		if (line!=1) return "修改游戏失败";
		return null;
	}

	@Override
	public String delete(String type, int id) {
		int line=0;
		if (yeyougame.name().equalsIgnoreCase(type)){
			line=yeyouGameMapper.delele(id);
		} else if (h5game.name().equalsIgnoreCase(type)){
			line=h5GameMapper.delele(id);
		}
		if (line!=1) return "删除游戏失败";
		return null;
	}


	@Override
	public String addGame(String type, AbstractGame game) {
		// 编辑和新增时都设置应用status为未接入
		game.setStatus(AppStatusEnum.unaccess.getStatus());
		if (yeyougame.name().equalsIgnoreCase(type)){
			if (!checkYeyouGameName(null, game.getAppName())){
				return "游戏名称已被使用，游戏名称："+game.getAppName();
			}
			yeyouGameMapper.insert(game);
			String appCode = String.format("Y%07d", game.getAppId());
			game.setAppCode(appCode); // 记录操作日志时使用到应用编码
			yeyouGameMapper.updateCode(game.getAppId(), appCode);
		} else if (type.startsWith(h5game.name())){
			/**
			 * 冰趣应用编码为HD，其他的为H
			 */
			String h5AppCodePrefix = null;
			if (H5_BINGQU_MARK.equalsIgnoreCase(type)) {
				h5AppCodePrefix="HD";
			} else {
				h5AppCodePrefix="H";
			}
			if (!checkH5GameName(h5AppCodePrefix, game.getAppName())){
				return "游戏名称已被使用，游戏名称："+game.getAppName();
			}
			h5GameMapper.insert(game);
			
			String appCode = String.format(h5AppCodePrefix+"%07d", game.getAppId());
			game.setAppCode(appCode); // 记录操作日志时使用到应用编码
			h5GameMapper.updateCode(game.getAppId(), appCode);
		}
		return null;
	}
	
	// 检查h5游戏名称是否可用
	private boolean checkH5GameName(String gameCode, String gameName){
		String appCodePrefix=null;
		if (gameCode.length()<=2){ // 添加游戏
			appCodePrefix=gameCode;
			if (appCodePrefix.length()==1) {
				appCodePrefix=appCodePrefix+"0";
			}
			gameCode=null;
		} else {
			// 宝玩前缀为H0  冰趣为HD
			appCodePrefix=gameCode.substring(0, 2);
		}
		AbstractGame game =h5GameMapper.selectH5ByName(gameName, appCodePrefix+"%");
		if (game ==null || (gameCode!=null && gameCode.equals(game.getAppCode()))){
			return true;
		}
		
		return false;
	}
	
	// 检查yeyou游戏名称是否可用
	private boolean checkYeyouGameName(String gameCode, String gameName){
		AbstractGame game =yeyouGameMapper.selectByName(gameName);
		if (game ==null || (gameCode!=null && gameCode.equals(game.getAppCode()))){
			return true;
		}
		
		return false;
	}

	@Override
	public String updateH5GameStatus(String code, int status) {
		AbstractGame game = h5GameMapper.select(code);
		int pastStatus=game.getStatus();
		boolean flag = false;
		switch (AppStatusEnum.getFromKey(pastStatus)){
		case unaccess: // 未接入 申请接入
		case deny_access: {//接入驳回
			if (status==wait_access.getStatus()){//待接入
				flag =true;
			}
			break;
		}
		case access_finish:{ //已接入 线上环境联调申请
			if (status==online_adjust.getStatus()){ // 线上联调中
				flag =true;
			}
			break;
		}
		case online_adjust_finish:
		case deny_test:{// 线上联调通过和测试驳回 测试申请 
			if (status==test.getStatus()){//测试中
				flag =true;
			}
			break;
		}
		case test_finish:{ //测试通过 上架申请
			if (status==wait_shelf.getStatus()){// 待上架
				flag =true;
			}
			break;
		}
		default:;
		}
		if (flag){
			h5GameMapper.updateStatus(game.getAppId(), status);
		} else {
			logger.error("H5游戏流程申请异常，变更前状态为{}，变更后状态为{}，判断为非法操作",
					getStatusDesc(pastStatus), getStatusDesc(status));
			return "游戏流程申请被拒绝";
		}
		return null;
	}

	@Override
	public String updateYeyouGameStatus(String code, int status) {
		AbstractGame game = yeyouGameMapper.select(code);
		int pastStatus=game.getStatus();
		boolean flag = false;
		switch (AppStatusEnum.getFromKey(pastStatus)){
		case unaccess: {// 未接入 申请接入
			if (status==wait_access.getStatus()){
				flag =true;
			}
			break;
		}
		case access_finish:{ //已接入 上架申请
			if (status==wait_shelf.getStatus()){
				flag =true;
				//原来也有申请上架时需要申请计费点,现在不需要.修改日期 2017-08-04
//				// 上架前需要检查计费点都审批通过 要求至少有一个计费点
//
//				final SearchChargePointBo searchChargePointBo = new SearchChargePointBo();
//				searchChargePointBo.setAppCode(code);
//				int count = yeYouCpMapper.selectCount(searchChargePointBo);
//				if (count==0) return "页游申请上架前需要有一个计费点，且审批通过";
//				count = yeYouCpMapper.selectNeedHandleCpCount(code);
//				if (count>0) return "存在有未审批通过的计费点，不能申请上架";
			}
			
			break;
		}
		default:;
		}
		if (flag){
			yeyouGameMapper.updateStatus(game.getAppId(), status);
		} else {
			logger.error("页游游戏流程申请异常，变更前状态为{}，变更后状态为{}，判断为非法操作",
					getStatusDesc(pastStatus), getStatusDesc(status));
			return "游戏流程申请被拒绝";
		}
		return null;
	}

	@Override
	public int updateH5PayCallBackUrl(String code, String payCallBackUrl) {
		
		return h5GameMapper.updatePayCallBackUrl(code, payCallBackUrl);
	}

	@Override
	public int updateYeyouPayCallBackUrl(MerchantAppInfo app) {

		CallbackAudit audit = new CallbackAudit();
		audit.setAppCode(app.getAppCode());
		audit.setLoginCallbackUrl(app.getLoginCallBackUrl());
		audit.setPayCallbackUrl(app.getPayCallBackUrl());
		return yeyouGameMapper.updatePayCallBackUrl(audit);
	}
	
}
