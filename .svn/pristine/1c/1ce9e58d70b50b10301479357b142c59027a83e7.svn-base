/**
 * 
 */
package com.dome.sdkserver.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dome.sdkserver.bo.ChargePointInfo;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bo.SearchChargePointBo;
import com.dome.sdkserver.bo.SearchMerchantAppBo;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.constants.AppTypeConstant;
import com.dome.sdkserver.constants.ChargePointStatusEnum;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;
import com.dome.sdkserver.metadata.dao.mapper.ChargePointMapper;
import com.dome.sdkserver.metadata.dao.mapper.MerchantAppMapper;
import com.dome.sdkserver.metadata.dao.mapper.yeyou.YeYouCpMapper;
import com.dome.sdkserver.metadata.dao.mapper.yeyou.YeyouGameMapper;
import com.dome.sdkserver.metadata.entity.AbstractGame;
import com.dome.sdkserver.metadata.entity.yeyou.YeYouCp;
import com.dome.sdkserver.metadata.entity.yeyou.YeyouGame;
import com.dome.sdkserver.service.ChargePointService;
import com.dome.sdkserver.service.OpenSdkSynDataService;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.business.GameUtils;

/**
 * @author liuxingyue
 *
 */
@Service
public class ChargePointServiceImpl implements ChargePointService {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	private ChargePointMapper chargePointMapper;
	
	@Resource
	private PropertiesUtil domainConfig;
	
	@Resource
	private MerchantAppMapper merchantAppMapper;
	
	@Resource
	private OpenSdkSynDataService openSdkSynDataServiceImpl;
	
	@Autowired
	private YeYouCpMapper<YeYouCp> yeYouCpMapper;
	@Autowired
	private YeyouGameMapper<YeyouGame> yeyouGameMapper;
	@Override
	public List<ChargePointInfo> getChargePointInfos(SearchChargePointBo searchChargePointBo) {
		List<ChargePointInfo>  ChargePointInfos = null;
		
		String appCode = searchChargePointBo.getAppCode();
		boolean isAll = false;
		if (StringUtils.isEmpty(appCode)) {
			isAll=true;
		}
		if (isAll||GameUtils.analyseGameType(appCode)==GameTypeEnum.mobilegame){
			ChargePointInfos = chargePointMapper.getChargePontInfosByCondition(searchChargePointBo);
		}
		if (isAll||GameUtils.analyseGameType(appCode)==GameTypeEnum.yeyougame){
			Paginator p = new Paginator();
			if (searchChargePointBo.getStart()!=null && searchChargePointBo.getSize()!=null){
				p.setStart(searchChargePointBo.getStart());
				p.setPageSize(searchChargePointBo.getSize());
			} else {
				p.setStart(-1); // 导出计费点Excel不需要分页
			}
			
			List<YeYouCp> cpList = yeYouCpMapper.selectList(searchChargePointBo, p);
			if (!CollectionUtils.isEmpty(cpList)){
				if (ChargePointInfos==null)	ChargePointInfos=new ArrayList<>(cpList.size());
				for (YeYouCp cp: cpList){
					ChargePointInfo chp = convertChargePoint(cp);
					ChargePointInfos.add(chp);
				}
			}
		}
		if (!CollectionUtils.isEmpty(ChargePointInfos)) {
			for (ChargePointInfo ch : ChargePointInfos) {
				int status = ch.getStatus();
				ch.setStatusDesc(ChargePointStatusEnum.getStatusDesc(status));
			}
		}
		return ChargePointInfos;
	}

	/**
	 * 转换页游计费点对象为老的计费点类型
	 * @param cp
	 * @return
	 */
	private ChargePointInfo convertChargePoint(YeYouCp cp) {
		if (cp==null) return null;
		ChargePointInfo chp = new ChargePointInfo();
		chp.setAppCode(cp.getAppCode());
		chp.setChargePointCode(cp.getChargePointCode());
		chp.setChargePointName(cp.getChargePointName());
		chp.setChargePointAmount(cp.getChargePointAmount());
		chp.setDesc(cp.getDesc());
		chp.setStatus(cp.getStatus());
		chp.setRemark(cp.getRemark());
		chp.setCreateTime(cp.getCreateTime());
		chp.setUpdateTime(cp.getUpdateTime());
		return chp;
	}

	@Override
	public Integer getChargePontCountByCondition(SearchChargePointBo searchChargePointBo) {
		String appCode = searchChargePointBo.getAppCode();
		int count=0;
		if (GameUtils.analyseGameType(appCode)==GameTypeEnum.mobilegame){
			count = chargePointMapper.getChargePontCountByCondition(searchChargePointBo);
		} else if (GameUtils.analyseGameType(appCode)==GameTypeEnum.yeyougame){
			count = yeYouCpMapper.selectCount(searchChargePointBo);
		}
		return count;
	}

	@Override
	@Deprecated
	public ChargePointInfo getChargePointInfoById(String chargePointId) {
		ChargePointInfo chargePointInfo = chargePointMapper.getChargePointInfoById(chargePointId);
		return chargePointInfo;
	}

	/**
	 * 计费点驳回
	 */
	@Override
	public void rejectCp(MerchantAppInfo app, ChargePointInfo chargePointInfo) {
		
		String appCode=chargePointInfo.getAppCode();
		if (GameUtils.analyseGameType(appCode)==GameTypeEnum.mobilegame){
			chargePointMapper.updateChargePointInfo(chargePointInfo);
		} else if (GameUtils.analyseGameType(appCode)==GameTypeEnum.yeyougame){
			YeYouCp cp =new YeYouCp();
			cp.setChargePointCode(chargePointInfo.getChargePointCode());
			cp.setAppCode(appCode);
			cp.setStatus(chargePointInfo.getStatus());
			cp.setRemark(chargePointInfo.getRemark());
			yeYouCpMapper.update(cp);
		}
	}

	@Override
	public ChargePointInfo getChargePontInfoByCode(String appCode, String chargePointCode) {
		ChargePointInfo ch =null;
		if (GameUtils.analyseGameType(appCode)==GameTypeEnum.mobilegame){
			ch = chargePointMapper.getChargePointInfoByCode(chargePointCode);
		} else if (GameUtils.analyseGameType(appCode)==GameTypeEnum.yeyougame){
			YeYouCp cp =yeYouCpMapper.select(chargePointCode);
			ch = convertChargePoint(cp);
		}
		if (ch != null) {
			int status = ch.getStatus();
			ch.setStatusDesc(ChargePointStatusEnum.getStatusDesc(status));
		}
		
		return ch;
	}
	
	@Override
	public boolean passCp(ChargePointInfo chargePoint, MerchantAppInfo app) {
		int line=0;
		String appCode=app.getAppCode();
		if (GameUtils.analyseGameType(appCode)==GameTypeEnum.mobilegame){
			ChargePointInfo charge = chargePointMapper.getChargePointInfoByCode(chargePoint.getChargePointCode());
			charge.setStatus(ChargePointStatusEnum.ENABLED.getStatus());
			updateChargeStatus(app, charge);
			// 计费点同步到测试环境
			openSdkSynDataServiceImpl.synCharge(OpenSdkSynDataService.TEST_DB, charge);
			line=1;
		} else if (GameUtils.analyseGameType(appCode)==GameTypeEnum.yeyougame){
			YeYouCp cp =yeYouCpMapper.select(chargePoint.getChargePointCode());
			line=yeYouCpMapper.updateStatus(cp.getChargePointId(), ChargePointStatusEnum.ENABLED.getStatus());
			// 页游计费点不需要同步到测试环境
			updateYeyouGameStatus(appCode);
		}
		return line==1;
	}
	
	/**
	 * 若有计费点审批通过，将游戏状态重置为已接入，需要重新申请接入。
	 * 若为已接入时不需要处理
	 * @param appCode
	 */
	private void updateYeyouGameStatus(String appCode){
		AbstractGame yyGame = yeyouGameMapper.select(appCode);
		if (yyGame.getStatus()!=AppStatusEnum.access_finish.getStatus()
//				yyGame.getStatus()==AppStatusEnum.shelf_finish.getStatus()
//				||yyGame.getStatus()==AppStatusEnum.wait_shelf.getStatus()
				){
			yeyouGameMapper.updateStatus(yyGame.getAppId(), AppStatusEnum.access_finish.getStatus());
		}
	}
	
	// 仅审批通过计费点 只针对手游
	private void updateChargeStatus(MerchantAppInfo app, ChargePointInfo chargePointInfo){
		chargePointMapper.updateStatus(chargePointInfo.getChargePointId(), chargePointInfo.getStatus());
		
		// 审核通过计费点
		int cpStatus = (chargePointInfo.getStatus() == null ? 0 : chargePointInfo.getStatus());
		if (cpStatus == ChargePointStatusEnum.ENABLED.getStatus()) {
			String cpCode = chargePointInfo.getChargePointCode();
			int cpCount = chargePointMapper.selectChargeCountByCpCode(cpCode);
			// type=1 新增计费点 2变更计费点
			int type = cpCount == 1 ? 1 : 2;
			updateAppStatus(app, type);
		}
	}

	@Override
	public int getAppInfoCountByCondition(
			SearchMerchantAppBo searchMerchantAppBo) {
		
		return chargePointMapper.getAppInfoCountByCondition(searchMerchantAppBo);
	}

	@Override
	public List<MerchantAppInfo> getAppInfoByCondition(
			SearchMerchantAppBo searchMerchantAppBo) {
		
		return chargePointMapper.getAppListByCondition(searchMerchantAppBo);
	}
	
	// 计费点变更后影响到应用状态的修改
	private void updateAppStatus(MerchantAppInfo app, int type){
		
		// 批量审批计费点时，应用状态要取最新值
		app = this.merchantAppMapper.queryApp(app.getAppCode());
		int status = app.getStatus();
		// 已接入状态时新增、修改计费点不修改应用状态
		if (AppStatusEnum.access_finish.getStatus() == status || !AppTypeConstant.APP_TYPE_GAME.equals(app.getAppType())){
			return;
		}
		// 测试审批通过， 待上架， 已上架， 上架驳回，修改计费点，应用状态为计费点变动测试中
		int newStatus = -1;
		if (AppStatusEnum.inStatus(status, AppStatusEnum.test_finish, AppStatusEnum.wait_shelf, AppStatusEnum.shelf_finish)) {
			switch (type){
			case 1:{ // add
				// 已通过测试的应用，当添加新计费点时，应用状态变更为“已接入”状态
				newStatus = AppStatusEnum.access_finish.getStatus();
				break;
			}
			case 2:{ // changed
				// 已通过测试的应用，当对已生效计费点进行变更后，应用状态变更为“测试中”，可进行操作“申请联调、包体管理”
				newStatus = AppStatusEnum.charge_changed.getStatus();
				break;
			}
			default: ;
			}
		} else {
			// 对未完成测试的应用，当新添加计费点或对已生效计费点进行变更时，应用状态变更为“已接入”状态，可进行操作“查看、计费点管理”
			//if (AppStatusEnum.access_finish.getStatus() != status) {
				newStatus = AppStatusEnum.access_finish.getStatus();
			//}
		}
		if (newStatus != -1) {
			merchantAppMapper.updateAppStatus(app.getAppId(), newStatus);
		}
		
	}
}
