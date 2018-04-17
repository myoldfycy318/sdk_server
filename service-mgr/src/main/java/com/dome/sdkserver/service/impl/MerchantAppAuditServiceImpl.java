package com.dome.sdkserver.service.impl;

import static com.dome.sdkserver.constants.AppStatusEnum.access_finish;
import static com.dome.sdkserver.constants.AppStatusEnum.deny_access;
import static com.dome.sdkserver.constants.AppStatusEnum.deny_test;
import static com.dome.sdkserver.constants.AppStatusEnum.getStatusDesc;
import static com.dome.sdkserver.constants.AppStatusEnum.online_adjust_finish;
import static com.dome.sdkserver.constants.AppStatusEnum.shelf_finish;
import static com.dome.sdkserver.constants.AppStatusEnum.test_adjust_finish;
import static com.dome.sdkserver.constants.AppStatusEnum.test_finish;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import com.dome.sdkserver.bo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.pkgmanage.Pkg;
import com.dome.sdkserver.bo.syn.GameSynPayBase;
import com.dome.sdkserver.bo.syn.H5GameSynPayVo;
import com.dome.sdkserver.bo.syn.MobileGameSynPayVo;
import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.constants.AppAuditMsg;
import com.dome.sdkserver.constants.AppChannelEnum;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.constants.AppTypeConstant;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;
import com.dome.sdkserver.metadata.dao.mapper.AppOperRecordMapper;
import com.dome.sdkserver.metadata.dao.mapper.AppPictureMapper;
import com.dome.sdkserver.metadata.dao.mapper.AppTypeAttrMapper;
import com.dome.sdkserver.metadata.dao.mapper.MerchantAppMapper;
import com.dome.sdkserver.metadata.dao.mapper.MerchantInfoMapper;
import com.dome.sdkserver.metadata.dao.mapper.PkgMapper;
import com.dome.sdkserver.metadata.dao.mapper.h5.H5GameMapper;
import com.dome.sdkserver.metadata.dao.mapper.yeyou.YeYouCpMapper;
import com.dome.sdkserver.metadata.dao.mapper.yeyou.YeyouGameMapper;
import com.dome.sdkserver.metadata.entity.AbstractGame;
import com.dome.sdkserver.metadata.entity.AppOperRecord;
import com.dome.sdkserver.metadata.entity.AppVo;
import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;
import com.dome.sdkserver.metadata.entity.h5.H5Game;
import com.dome.sdkserver.metadata.entity.yeyou.YeYouCp;
import com.dome.sdkserver.metadata.entity.yeyou.YeyouGame;
import com.dome.sdkserver.service.AppService;
import com.dome.sdkserver.service.MerchantAppAuditService;
import com.dome.sdkserver.service.OpenSdkSynDataService;
import com.dome.sdkserver.service.RedisService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RSACoder;
import com.dome.sdkserver.util.business.GameUtils;

/**
 * 商户应用服务实现
 * @author hexiaoyi
 */
@Service
public class MerchantAppAuditServiceImpl implements MerchantAppAuditService {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private MerchantAppMapper merchantAppMapper;
	
	@Resource
	MerchantInfoMapper merchantInfoMapper;
	
	@Resource
	private AppTypeAttrMapper appTypeAttrMapper;
	
	@Resource
	private RedisService redisServiceImpl;
	
	@Resource
	private AppPictureMapper appPictureMapper;
	
	@Resource
	private PkgMapper pkgMapper;
	
	@Autowired
	PropertiesUtil domainConfig;

	@Resource
	private OpenSdkSynDataService openSdkSynDataServiceImpl;
	
	@Autowired
	private YeyouGameMapper<YeyouGame> yeyouGameMapper;

	@Autowired
	private YeYouCpMapper<YeYouCp> yeYouCpMapper;
	@Autowired
	private H5GameMapper<H5Game> h5GameMapper;
	
	@Override
	public Integer getAppInfoCountByCondition(
			SearchMerchantAppBo searchMerchantAppBo) {
		int count = merchantAppMapper.getAppInfoCountByCondition(searchMerchantAppBo);
		return count;
	}
	
	@Override
	public List<AppVo> getAppInfoByCondition(
			SearchMerchantAppBo searchMerchantAppBo) {
		List<AppVo> merchantAppInfoList = merchantAppMapper.getAppListByCondition(searchMerchantAppBo);
		for(AppVo merchantAppInfo : merchantAppInfoList){
			// 上架渠道
			int status = merchantAppInfo.getStatus() == null ? -1 : merchantAppInfo.getStatus();
			merchantAppInfo.setStatusDesc(AppStatusEnum.getStatusDesc(status));
			merchantAppInfo.setShelfChannel(getAppChannel(merchantAppInfo.getAppCode(), status));
			// 只有手游才有游戏截图
			String appCode=merchantAppInfo.getAppCode();
			final GameTypeEnum gameTypeEnum = GameUtils.analyseGameType(appCode);
			if (gameTypeEnum==GameTypeEnum.mobilegame){
				initAppPic(merchantAppInfo);
			} else if (gameTypeEnum==GameTypeEnum.yeyougame){
				merchantAppInfo.setAppTypeName("页游");
				
			} else if (gameTypeEnum==GameTypeEnum.h5game){
				merchantAppInfo.setAppTypeName("H5游戏");
				merchantAppInfo.setGameTypeName(merchantAppInfo.getGameType());
				merchantAppInfo.setGameType("");
			}
			
		}
		return merchantAppInfoList;
	}

    /**
     * 该方法由private修改为public ,用于修改回调地址CallbackAuditServiceImpl类中的方法
     * @param appCode
     * @param status
     * @return
     */
	public String getAppChannel(String appCode, int status){
		if (status != -1 && AppStatusEnum.inStatus(status, AppStatusEnum.shelf_finish, AppStatusEnum.shelf_off, AppStatusEnum.hidden)){
			// 查询应用上架渠道
			List<Map<String, String>> list = merchantAppMapper.queryAppChannel(appCode);
			if (!CollectionUtils.isEmpty(list)) {
				StringBuilder sb = new StringBuilder();
				for (Map<String, String> map : list){
					for (Map.Entry<String, String> entry : map.entrySet()){
						if ("channel_type".equals(entry.getKey())) {
							if (sb.length() == 0) {
								sb.append(entry.getValue());
							} else {
								sb.append(",").append(entry.getValue());
							}
							break;
						}
						
					}
				}
				return sb.toString();
			}
		}
		return null;
	}
	private void initAppPic(MerchantAppInfo appInfo){
		List<AppPicture> picList = appPictureMapper.queryPicList(appInfo.getAppId());
		appInfo.setPicUrlList(picList);
	}
	
	/**
	 * 生成公私钥
	 * 
	 * @return
	 */
	private Map<String,String> createRsaKey(){
		Map<String, String> outKeyMap = null;
		try {
			outKeyMap = RSACoder.generateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outKeyMap;
	}
	
	/**
	 * 查看应用使用
	 * 包括手游的游戏截图、上架渠道，类型的中文名称等
	 */
	@Override
	public MerchantAppInfo queryApp(String appCode) {
		//合作伙伴查询应用信息，应用状态和基本信息会一直变动，保证信息及时、准确，不存入redis
//		String key = DomeSdkRedisKey.APP_PREFIX + appCode;
//		String str = redisUtil.get(key);
//		App app = null;
//		if (StringUtils.isEmpty(str) || "null".equals(str)) {
//			app = appMapper.queryApp(appCode);
//			redisUtil.setex(key, 24 * 60 * 60, JSON.toJSONString(app));
//		} else {
//			app = JSON.parseObject(str, App.class);
//		}
//		return app;
		MerchantAppInfo app = null;
		final GameTypeEnum gameTypeEnum = GameUtils.analyseGameType(appCode);
		if (gameTypeEnum==GameTypeEnum.mobilegame){
			app = merchantAppMapper.queryApp(appCode);
			if (app == null) return app;
			AppTypeAttrInfo appType = null;
			/**
			 * domesdk_app_info中app_type 为类型编码
			 * game_type、game_attr为类型编码
			 */
			if (!StringUtils.isEmpty(app.getAppType())) {
				appType = redisServiceImpl.getAppTypeAttrByCode(app.getAppType());
				app.setAppTypeName(appType== null ? "" : appType.getTypeAttrName());
			}
			if (!StringUtils.isEmpty(app.getGameType())){
				appType = redisServiceImpl.getAppTypeAttrByCode(app.getGameType());
				app.setGameTypeName(appType== null ? "" : appType.getTypeAttrName());
			}
			List<AppPicture> picList = appPictureMapper.queryPicList(app.getAppId());
			if (!CollectionUtils.isEmpty(picList)){
				
				app.setPicUrlList(picList);
			}
		} else if (gameTypeEnum==GameTypeEnum.yeyougame){
			AbstractGame game=yeyouGameMapper.select(appCode);
			app = new MerchantAppInfo();
			app.setStatus(game.getStatus());
			app.setAppId(game.getAppId());
			BeanUtils.copyProperties(game, app, "merchantInfoId", "appId", "status", "delFlag"
					, "userId");
			app.setAppTypeName("页游");
		} else if (gameTypeEnum==GameTypeEnum.h5game){
			AbstractGame game=h5GameMapper.select(appCode);
			app = new AppVo();
			app.setStatus(game.getStatus());
			app.setAppId(game.getAppId());
			BeanUtils.copyProperties(game, app, "merchantInfoId", "appId", "status", "delFlag"
					, "userId");
			app.setAppTypeName("H5游戏");
		}
		
		int status = app.getStatus();
		app.setStatusDesc(AppStatusEnum.getStatusDesc(status));
		
		// 查询应用上架渠道
		app.setShelfChannel(getAppChannel(appCode, status));
		return app;
	}
	
	private String auditCheck(MerchantAppInfo app, int pastStatus){
		int status = app.getStatus();
		boolean flag=false;
		String remark=null;
		switch (AppStatusEnum.getFromKey(pastStatus)){
		case wait_access: {// 应用审批
			if (status==access_finish.getStatus()){
				flag =true;
			}else if (status==deny_access.getStatus()){
				remark=app.getRemark();
				if (StringUtils.isEmpty(remark)) return AppAuditMsg.rejectremark_empty;
				flag =true;
			}
			break;
		}
		case test_adjust:{ // 线上环境联调申请
			if (status==test_adjust_finish.getStatus()){ // 测试联调审批通过
				flag =true;
			}else if (status==access_finish.getStatus()){// 测试联调申请驳回到已接入
				remark=app.getRemark();
				if (StringUtils.isEmpty(remark)) return AppAuditMsg.rejectremark_empty;
				flag =true;
			}
			break;
		}
		case online_adjust:{ // 线上环境联调申请
			if (status==online_adjust_finish.getStatus()){ // 线上联调审批通过
				flag =true;
			}else if (status==access_finish.getStatus()){// 线上联调申请驳回到已接入
				remark=app.getRemark();
				if (StringUtils.isEmpty(remark)) return AppAuditMsg.rejectremark_empty;
				flag =true;
			}
			break;
		}
		case charge_changed: // 计费点变更、包体变更测试中状态
		case pkg_changed:
		case test: {// 测试申请
			if (status==test_finish.getStatus()){//测试审批通过
				flag =true;
			}else if (status==deny_test.getStatus()){// 测试申请驳回
				remark=app.getRemark();
				if (StringUtils.isEmpty(remark)) return AppAuditMsg.rejectremark_empty;
				flag =true;
			}
			break;
		}
		case wait_shelf:
		case hidden:
		case shelf_off:{ // 待上架 、展示下线、下架
			if (status==shelf_finish.getStatus()){
				flag =true;
				doShelf(app);
			}
			break;
		}
		case shelf_finish:{ //已接入 上架申请
			if (AppStatusEnum.inStatus(status, AppStatusEnum.hidden, AppStatusEnum.shelf_off)){
				flag =true;
			}
			break;
		}
		default:;
		}
		if (!flag){
			log.error("应用流程审批异常，变更前状态为{}，变更后状态为{}，判断为非法操作",
					getStatusDesc(pastStatus), getStatusDesc(status));
			return "应用流程审批被拒绝";
		}
		return null;
	}
	/**
	 * 处理后台管理页面修改状态的请求
	 */
	@Override
	@Transactional
	public String updateAppStatus(MerchantAppInfo app) {
		int pastStatus = app.getPastStatus();
		int newStatus = app.getStatus();
		String errorMsg = auditCheck(app, pastStatus);
		if (!StringUtils.isEmpty(errorMsg)) throw new RuntimeException(errorMsg);
		
		log.info("应用状态变更，原状态：{}--{}，新状态：{}--{}", pastStatus, AppStatusEnum.getStatusDesc(pastStatus), newStatus, AppStatusEnum.getStatusDesc(newStatus));
		errorMsg = doUpdateAppStatus(app, pastStatus);
		if (!StringUtils.isEmpty(errorMsg)) throw new RuntimeException(errorMsg);
		// 应用数据同步到测试库,去掉判断条件，所有数据都做同步--有bug先放开
		if(app.getMerchantInfoId() == Constants.DOME_DEFAULT_MERCHANTID)
		{
			return StringUtils.EMPTY;
		}
		if (AppTypeConstant.APP_TYPE_GAME.equals(app.getAppType())){
			try {
				MerchantAppInfo pastApp= merchantAppMapper.getAppByIdAndMertId(app.getAppId(), app.getMerchantInfoId());
				openSdkSynDataServiceImpl.synApp(OpenSdkSynDataService.TEST_DB, pastApp);
			} catch (Exception e) {
				String msg = "应用信息同步到测试环境失败";
				log.error(msg, e);
				throw new RuntimeException(errorMsg);
			}
		}
		return StringUtils.EMPTY;
	}

	private String doUpdateAppStatus(MerchantAppInfo app, int pastStatus) {
		
		// 非游戏，申请接入审批后直接到待上架
		if (!"10000000".equals(app.getAppType()) && AppStatusEnum.inStatus(app.getStatus(), AppStatusEnum.access_finish)) {
			app.setStatus(AppStatusEnum.wait_shelf.getStatus());
		}
		merchantAppMapper.updateMgrAppStatus(app);
		//更新业务类型
		if(app.getStatus() == AppStatusEnum.access_finish.getStatus() && StringUtils.isBlank(app.getRemark())){
			int i = merchantAppMapper.updateAppBizType(app.getAppCode(), app.getBizType());
			log.info("更新业务状态数量i={}, appCode={}, 业务类型={}, 现状态status={}, 原状态pastStatus={}",
					i, app.getAppCode(), app.getBizType(), app.getStatus(), app.getPastStatus());
		}
		
		if (AppStatusEnum.inStatus(app.getStatus(), AppStatusEnum.shelf_finish, AppStatusEnum.shelf_off, AppStatusEnum.hidden)){
			String errorMsg = doSynchronize(app);
			if (StringUtils.isNotEmpty(errorMsg)) return errorMsg;
		}
		
		return null;
	}
	
	/**
	 * 上架操作
	 * 1、保存上架渠道
	 */
	private void doShelf(MerchantAppInfo app){
		if (app.getStatus() != AppStatusEnum.shelf_finish.getStatus()) return;
		merchantAppMapper.delAppChannel(app.getAppCode());
		String shelfChannel = app.getShelfChannel();
		String[] channels = shelfChannel.split(",");
		for (String channel : channels) {
			if (StringUtils.isEmpty(channel)) continue;
			
			if (AppChannelEnum.qb.getType().equals(channel) || AppChannelEnum.baowan.getType().equals(channel)){
				merchantAppMapper.saveAppChannel(app.getAppCode(), channel);
			} 
		}
	}
	
	@Autowired
	private AppService appService;
	/**
	 * 上架、下架、展示下线都会调用该方法
	 * 1、同步数据
	 */
	private String doSynchronize(MerchantAppInfo app){
		
		String errorMsg = null;
		// 带有应用类型和上架渠道信息
		MerchantAppInfo appInfo = appService.selectApp(app.getAppCode());
		String channel = getAppChannel(app.getAppCode(), app.getStatus());
		if (!StringUtils.isEmpty(channel) && channel.contains(AppChannelEnum.baowan.getType())){
			errorMsg = synAppToBaowan(appInfo);
			if (!StringUtils.isEmpty(errorMsg)) {
				return errorMsg;
			}
		}
		if (!StringUtils.isEmpty(channel) && channel.contains(AppChannelEnum.qb.getType())){
			errorMsg = synAppToQb(appInfo);
			if (!StringUtils.isEmpty(errorMsg)) {
				return errorMsg;
			}
		}
		appInfo.setShelfChannel(channel);
		errorMsg = synPay(appInfo);
		if (!StringUtils.isEmpty(errorMsg)) {
			return errorMsg;
		}
		return StringUtils.EMPTY;
	}
	private String synAppToQb(MerchantAppInfo app){
		AppSyncDto dto = new AppSyncDto();
		dto.setMerchantCode(app.getMerchantCode());
		dto.setSdkAppCode(app.getAppCode());
		dto.setName(app.getAppName());
		dto.setIconUrl(app.getAppIcon());
		dto.setDescription(app.getAppDesc());
		// 0游戏1软件
		dto.setType(AppTypeConstant.APP_TYPE_GAME.equals(app.getAppType()) ? "0" : "1");
		
		// 游戏截图
		List<AppPicture> picList = appPictureMapper.queryPicList(app.getAppId());
		if (!CollectionUtils.isEmpty(picList)){
			List<String> appPicList = new ArrayList<String>(picList.size());
			for (AppPicture pic : picList){
				if (pic == null) continue;
				
				appPicList.add(pic.getPicUrl());
			}
			dto.setPicUrls(appPicList);
		}
		
		// 安装包
		Pkg pkg = pkgMapper.queryNew(app.getAppCode());
		if (pkg != null){
			dto.setPackName(pkg.getName());
			dto.setVersion(pkg.getVersion());
			//dto.setRowVersion(pkg.getSdkVersion());
			dto.setSize(pkg.getFileSize());
			dto.setPackUrl(pkg.getUploadPath());
			
		}
		
		
		// 应用状态   下架 0 下线1 上线2
		if (app.getStatus() == AppStatusEnum.shelf_finish.getStatus()){
			dto.setPublish("1"); // 上架
		} else if (app.getStatus() == AppStatusEnum.shelf_off.getStatus()){
			dto.setPublish("0");  // 下架
		} else if (app.getStatus() == AppStatusEnum.hidden.getStatus()){
			dto.setPublish("0");  // 展示下线
		} 
		Object obj = JSON.toJSON(dto);
		JSON json = null;
		if (obj != null && obj instanceof JSON) {
			json = (JSON)obj;
		} else {
			return "系统异常";
		}
		log.info("同步应用信息到钱宝后台：{}", (json == null ? "" : json.toString()));
		String response = ApiConnector.postJson(domainConfig.getString("domesdk.appinfo.synqb"), json);
		log.info("同步应用信息到钱宝后台返回结果：{}", response);
		if (!StringUtils.isEmpty(response)){
			JSONObject jsonObj = JSON.parseObject(response);
			if (jsonObj.getInteger("responseCode") == 1000 ){
				return StringUtils.EMPTY;
			}
			
		}
		return AppAuditMsg.appinfo_synqb_error;
	}
	
	private String synAppToBaowan(MerchantAppInfo app){
		AppInfo info = new AppInfo();
		generateBaowanAppInfo(app, info);
		
		Object obj = JSON.toJSON(info);
		JSON json = null;
		if (obj != null && obj instanceof JSON) {
			json = (JSON)obj;
		} else {
			return "系统异常";
		}
		log.info("同步应用信息到宝玩渠道：{}", (json == null ? "" : json.toString()));
		String response = ApiConnector.postJson(domainConfig.getString("domesdk.appinfo.synbaowan"), json);
		log.info("同步应用信息到宝玩渠道返回结果：{}", response);
		
		if (!StringUtils.isEmpty(response)){
			JSONObject jsonObj = JSON.parseObject(response);
			if (jsonObj.getInteger("responseCode") == 1000 ){
				return StringUtils.EMPTY;
			}
			
		}
		return AppAuditMsg.appinfo_synbaowan_error;
	}

	private void generateBaowanAppInfo(MerchantAppInfo app, AppInfo info) {
		info.setAppCode(app.getAppCode());
		info.setAppName(app.getAppName());
		info.setMerchantInfoId(app.getMerchantInfoId());
		info.setMerchantCode(app.getMerchantCode());
		info.setAppDesc(app.getAppDesc());
		info.setAppIcon(app.getAppIcon());
		info.setAppType0(app.getAppType());
		// 只有游戏才有二级和三级类型
		if ("10000000".equals(app.getAppType())) {
			info.setAppType1(app.getGameType());
			info.setAppType2(app.getGameAttr());
			// 游戏有一句话描述
			info.setIntroduction(app.getGameDesc());
		}
		
		// 游戏截图
		List<AppPicture> picList = appPictureMapper.queryPicList(app.getAppId());
		if (!CollectionUtils.isEmpty(picList)){
			List<AppPic> appPicList = new ArrayList<AppPic>(picList.size());
			for (AppPicture pic : picList){
				if (pic == null) continue;
				AppPic appPic = new AppPic();
				appPic.setPicUrl(pic.getPicUrl());
				appPic.setPicDesc(pic.getDesc());
				
				appPicList.add(appPic);
			}
			info.setAppPics(appPicList);
		}
		
		// 安装包
		Pkg pkg = pkgMapper.queryNew(app.getAppCode());
		info.setAppApkFileName(pkg.getName());
		info.setAppUrl(pkg.getUploadPath()); // 下载地址
		info.setAppApkSize(pkg.getFileSize());
		info.setAppApkVersion(pkg.getVersion());
		info.setAppApkName(pkg.getPackageName()); // 包名
		
		// 应用状态   下架 0 下线1 上线2
		if (app.getStatus() == AppStatusEnum.shelf_finish.getStatus()){
			info.setStatus(2); // 上架
		} else if (app.getStatus() == AppStatusEnum.shelf_off.getStatus()){
			info.setStatus(0); // 下架
		} else if (app.getStatus() == AppStatusEnum.hidden.getStatus()){
			info.setStatus(1); // 展示下线
		} 
	}

	@Autowired
	private AppOperRecordMapper recordMapper;
	@Override
	public int modfiyCallbackUrl(MerchantAppInfo app) {
		log.info("manually modify call back url, app_code:{}, login:{}, pay:{}, test login:{}, test pay:{}",
				app.getAppCode(), app.getLoginCallBackUrl(), app.getPayCallBackUrl(), app.getTestLoginCallBackUrl(), app.getTestPayCallBackUrl());
		int line =merchantAppMapper.modfiyCallbackUrl(app);
		if (line==1){
			// 修改记录备案
			AppOperRecord record = new AppOperRecord();
			record.setRemark(app.getRemark());
			record.setOperDesc("通过接口方式修改登陆和支付回调地址");
			record.setOperUserId(app.getOperUserId());
			record.setOperUser(app.getOperUser());
			MerchantAppInfo pastApp = merchantAppMapper.queryApp(app.getAppCode());
			int appId =0;
			if (pastApp == null) {
				appId = Integer.parseInt(app.getAppCode().substring(1));
			}
			record.setAppId(pastApp ==null ? appId : pastApp.getAppId());
			record.setStatus(pastApp ==null ? 0 : pastApp.getStatus());
			recordMapper.insert(record);
		}
		return line;
	}

	@Override
	public String updateYeyouStatus(MerchantAppInfo app) {
		int pastStatus=app.getPastStatus();
		int status=app.getStatus();
		//页游增加登录key,支付key和signkey
		//app对象是从数据库中查出的结果,如果没有生成appKey,loginKey,payKey则生成key,已经生成则不再生成
		saveYeyouKey(app.getAppCode());

		String remark=null;
		boolean flag = false;
		boolean needSyn=false;
		switch (AppStatusEnum.getFromKey(pastStatus)){
		case wait_access: {// 应用审批
			
			if (status==access_finish.getStatus()){
				flag =true;
			}else if (status==deny_access.getStatus()){
				remark=app.getRemark();
				if (StringUtils.isEmpty(remark)) return "游戏接入驳回，驳回理由不能为空";
				flag =true;
			}
			break;
		}
		case wait_shelf:
		case hidden:
		case shelf_off:{ // 待上架 、展示下线、下架
			if (status==shelf_finish.getStatus()){
				flag =true;
				needSyn=true;
				doShelf(app);
			}
			break;
		}
		case shelf_finish:{ //已接入 上架申请
			if (AppStatusEnum.inStatus(status, AppStatusEnum.hidden, AppStatusEnum.shelf_off)){
				flag =true;
				needSyn=true;
			}
			break;
		}
		default:;
		}
		
		if (flag){
			// 数据同步到页游平台
			if (needSyn){
				String errorMsg= synYeyou(app);
				if (errorMsg!=null) return errorMsg;
			}
			if (StringUtils.isEmpty(remark)){
				yeyouGameMapper.updateStatus(app.getAppId(), status);
			} else {
				YeyouGame t = new YeyouGame();
				t.setAppId(app.getAppId());
				t.setAppCode(app.getAppCode());
				t.setRemark(remark);
				t.setStatus(status);
				t.setLoginCallBackUrl(app.getLoginCallBackUrl());
				yeyouGameMapper.update(t);
			}
		} else {
			log.error("页游游戏流程审批异常，变更前状态为{}，变更后状态为{}，判断为非法操作",
					getStatusDesc(pastStatus), getStatusDesc(status));
			return "游戏流程审批被拒绝";
		}
		return null;
	}
	private class YeyouSynVo{
		private String appCode;
		private String gameName;
		private String gamePicture; // 游戏Icon
		
		/**
		 * 游戏状态(0-游戏展示上线，1-游戏展示下线)
		 */
		private int gameStatus;
		
		/**
		 * 多个渠道用逗号分隔
		 */
		private String channelCode;

		/**
		 * 计费点单位
		 */
		private String unit;
		
		/**
		 * 计费点（计费比例）
		 */
		private String chargePoint;
		
		/**
		 * 计费点编码
		 */
		private String chargePointCode;
		private String loginCallbackUrl;
		private String payCallbackUrl;


		public String getLoginCallbackUrl() {
			return loginCallbackUrl;
		}

		public void setLoginCallbackUrl(String loginCallbackUrl) {
			this.loginCallbackUrl = loginCallbackUrl;
		}

		public String getPayCallbackUrl() {
			return payCallbackUrl;
		}

		public void setPayCallbackUrl(String payCallbackUrl) {
			this.payCallbackUrl = payCallbackUrl;
		}
		public String getAppCode() {
			return appCode;
		}

		public void setAppCode(String appCode) {
			this.appCode = appCode;
		}

		public String getGameName() {
			return gameName;
		}

		public void setGameName(String gameName) {
			this.gameName = gameName;
		}

		public String getGamePicture() {
			return gamePicture;
		}

		public void setGamePicture(String gamePicture) {
			this.gamePicture = gamePicture;
		}


		public int getGameStatus() {
			return gameStatus;
		}

		public void setGameStatus(int gameStatus) {
			this.gameStatus = gameStatus;
		}

		public String getChannelCode() {
			return channelCode;
		}

		public void setChannelCode(String channelCode) {
			this.channelCode = channelCode;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public String getChargePoint() {
			return chargePoint;
		}

		public void setChargePoint(String chargePoint) {
			this.chargePoint = chargePoint;
		}

		public String getChargePointCode() {
			return chargePointCode;
		}

		public void setChargePointCode(String chargePointCode) {
			this.chargePointCode = chargePointCode;
		}
		
		
	}
	private String synYeyou(MerchantAppInfo app){
		YeyouSynVo yeyouSyn = createYeyouSynObj(app);
		Object obj = JSON.toJSON(yeyouSyn);
		JSON json = null;
		if (obj != null && obj instanceof JSON) {
			json = (JSON)obj;
		} else {
			return "系统异常";
		}
		log.info("同步页游到页游平台：{}", (json == null ? "" : json.toString()));
		List<NameValuePair> list = new ArrayList<>();
		list.add(new BasicNameValuePair("appCode", yeyouSyn.getAppCode()));
		list.add(new BasicNameValuePair("gameName", yeyouSyn.getGameName()));
		list.add(new BasicNameValuePair("gamePicture", yeyouSyn.getGamePicture())); // h5平台和页游在处理时会取图片流，图片需要能访问到，要不会返回1005游戏添加失败
		list.add(new BasicNameValuePair("gameStatus", Integer.toString(yeyouSyn.getGameStatus())));
		list.add(new BasicNameValuePair("channelCode", yeyouSyn.getChannelCode()));
		list.add(new BasicNameValuePair("unit", yeyouSyn.getUnit()));
		list.add(new BasicNameValuePair("chargePoint", yeyouSyn.getChargePoint()));
		list.add(new BasicNameValuePair("chargePointCode", yeyouSyn.getChargePointCode()));
		
//		String response = ApiConnector.postJson(domainConfig.getString("domesdk.yeyougame.synapp"), json);

		// 老的页游同步接口不再使用了,php已经重写了该同步接口(修改日期2017-08-10)
//		String response = ApiConnector.get(domainConfig.getString("domesdk.yeyougame.synapp"), list);
//		log.info("同步页游到页游平台返回结果：{}", response);
//
//		if (!StringUtils.isEmpty(response)){
//			JSONObject jsonObj = JSON.parseObject(response);
//			if (jsonObj.getInteger("responseCode") != 1000 ){
//				String errorMsg=jsonObj.getString("errorMsg");
//				return StringUtils.isNotEmpty(errorMsg) ?errorMsg:"同步应用到页游平台失败";
//			}
//
//		}
		//php 页游游戏同步接口(同步字段 appCode, appName, appIcon)
		List<NameValuePair> listParams = new ArrayList<>();
		listParams.add(new BasicNameValuePair("app_code", yeyouSyn.getAppCode()));
		listParams.add(new BasicNameValuePair("game_name", yeyouSyn.getGameName()));
		listParams.add(new BasicNameValuePair("icon", yeyouSyn.getGamePicture()));
		log.info("listParam:"+JSONObject.toJSONString(listParams));
		String response = ApiConnector.post(domainConfig.getString("domesdk.yeyougame.synapp"), listParams);
		log.info("同步页游到页游平台返回结果：{}", response);
		if (!StringUtils.isEmpty(response)) {
			JSONObject jsonObj = JSON.parseObject(response);
			String message = jsonObj.getString("message");
			if(!StringUtils.isBlank(message)){
				if(!"success".equals(message)){
					return "同步应用到页游平台失败";
				}
			}
		}


		app.setShelfChannel(yeyouSyn.getChannelCode());
		String errorMsg = synPay(app);
		
		if (!StringUtils.isEmpty(errorMsg)) {
			return errorMsg;
		}
		return null;
	}
	// 生成页游同步对象
	private YeyouSynVo createYeyouSynObj(MerchantAppInfo app) {
		YeyouSynVo yeyouSyn = new YeyouSynVo();
		yeyouSyn.setAppCode(app.getAppCode());
		yeyouSyn.setGameName(app.getAppName());
		yeyouSyn.setGamePicture(app.getAppIcon());
		//登录url及支付url
		yeyouSyn.setLoginCallbackUrl(app.getLoginCallBackUrl());
		yeyouSyn.setPayCallbackUrl(app.getPayCallBackUrl());
		// 上架为0，下架为1
		yeyouSyn.setGameStatus(app.getStatus()==shelf_finish.getStatus()?0:1);
		if (yeyouSyn.getGameStatus()!=0){ // 不是上架是，需要查询出已上架的渠道
			String shelfChannel=this.getAppChannel(app.getAppCode(), app.getStatus());
			if (StringUtils.isEmpty(shelfChannel)) shelfChannel=StringUtils.EMPTY;
			yeyouSyn.setChannelCode(shelfChannel);
		} else{
			yeyouSyn.setChannelCode(app.getShelfChannel());
		}
		
		
		Paginator paginator=new Paginator();
		// 计费点单位，获取计费点列表，取第一条记录的计费点名称
		List<YeYouCp> cpList=yeYouCpMapper.selectList(new SearchChargePointBo(), paginator);
		if (!CollectionUtils.isEmpty(cpList)){
			YeYouCp cp = cpList.get(0);
			yeyouSyn.setUnit(cp.getChargePointName());
			yeyouSyn.setChargePointCode(cp.getChargePointCode());
			yeyouSyn.setChargePoint(Integer.toString(cp.getChargePointAmount()));
		}
		return yeyouSyn;
	}
	
	private class h5GameSynVo{
		/**
		 * 游戏名称
		 */
		private String name;
		
		/**
		 * 游戏图标
		 */
		private String icon;
		
		/**
		 * 游戏描述
		 */
		private String description;
		
		/**
		 * 游戏链接
		 */
		private String url;
		
		private String appCode;
		
		/**
		 * 游戏状态(0-已下架，1-已上架，2-展示下线)
		 */
		private int status;

		private String developer;
		
		/**
		 * 1-角色扮演，2-休闲娱乐
		 */
		private int type;
		
		private String typeDescr;
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getAppCode() {
			return appCode;
		}

		public void setAppCode(String appCode) {
			this.appCode = appCode;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getDeveloper() {
			return developer;
		}

		public void setDeveloper(String developer) {
			this.developer = developer;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getTypeDescr() {
			return typeDescr;
		}

		public void setTypeDescr(String typeDescr) {
			this.typeDescr = typeDescr;
		}


		
		
	}
	private String synH5(MerchantAppInfo app){
		H5Game h5= (H5Game) h5GameMapper.select(app.getAppCode());
		h5.setStatus(app.getStatus());
		h5GameSynVo yeyouSyn = createH5SynObj(h5);
		Object obj = JSON.toJSON(yeyouSyn);
		JSON json = null;
		if (obj != null && obj instanceof JSON) {
			json = (JSON)obj;
		} else {
			return "系统异常";
		}
		log.info("同步H5游戏到H5平台：{}", (json == null ? "" : json.toString()));
		List<NameValuePair> list = new ArrayList<>();
		list.add(new BasicNameValuePair("appCode", yeyouSyn.getAppCode()));
		list.add(new BasicNameValuePair("name", yeyouSyn.getName()));
		list.add(new BasicNameValuePair("icon", yeyouSyn.getIcon())); // h5平台和页游在处理时会取图片流，图片需要能访问到，要不会返回1005游戏添加失败
		list.add(new BasicNameValuePair("status", Integer.toString(yeyouSyn.getStatus())));
		list.add(new BasicNameValuePair("description", yeyouSyn.getDescription()));
		list.add(new BasicNameValuePair("url", yeyouSyn.getUrl()));
		list.add(new BasicNameValuePair("developer", yeyouSyn.getDeveloper()));
//		list.add(new BasicNameValuePair("typeDescr", yeyouSyn.getTypeDescr()));
//		int type = "角色扮演".equals(yeyouSyn.getTypeDescr()) ? 1:
//			"休闲娱乐".equals(yeyouSyn.getTypeDescr()) ?2:0;
		list.add(new BasicNameValuePair("type", yeyouSyn.getTypeDescr()));
//		String response = ApiConnector.postJson(domainConfig.getString("domesdk.yeyougame.synapp"), json);
		// 根据应用编码分别同步到冰趣或宝玩
		boolean isBq=yeyouSyn.getAppCode().startsWith("HD");
		String response = ApiConnector.get(domainConfig.getString("domesdk.h5game.synapp"
		+(isBq?"_bq":"")), list);
		log.info("同步H5游戏到H5平台返回结果：{}", response);
		
		if (!StringUtils.isEmpty(response)){
			JSONObject jsonObj = JSON.parseObject(response);
			if (jsonObj.getInteger("responseCode") != 1000 ){
				String errorMsg=jsonObj.getString("errorMsg");
				return StringUtils.isNotEmpty(errorMsg) ?errorMsg:"同步应用到H5平台失败";
			}
			
		}
		String errorMsg = synPay(h5);
		if (!StringUtils.isEmpty(errorMsg)) {
			return errorMsg;
		}
		return null;
	}
	// 生成h5同步对象
	private h5GameSynVo createH5SynObj(H5Game h5) {
		h5GameSynVo h5Syn = new h5GameSynVo();
		h5Syn.setAppCode(h5.getAppCode());
		h5Syn.setName(h5.getAppName());
		h5Syn.setIcon(h5.getAppIcon());
		
		h5Syn.setDescription(h5.getAppDesc());
		h5Syn.setUrl(h5.getAppUrl());
		h5Syn.setDeveloper(h5.getDevelopers());
		h5Syn.setTypeDescr(h5.getGameType());
		// 游戏状态(0-已下架，1-已上架，2-展示下线)
		int status=h5.getStatus();
		h5Syn.setStatus(status);
		return h5Syn;
		
	}
	
	@Override
	public String updateH5GameStatus(MerchantAppInfo app) {
		int pastStatus=app.getPastStatus();
		int status=app.getStatus();
		String remark=app.getRemark();
		boolean flag = false;
		boolean needSyn=false;
		switch (AppStatusEnum.getFromKey(pastStatus)){
		case wait_access: {// 应用审批
			if (status==access_finish.getStatus()){
				flag =true;
				saveH5Key(app);
				
				// h5应用审批时将数据同步到sdkserver，保证在联调时可以看到数据
				H5Game h5= (H5Game) h5GameMapper.select(app.getAppCode());
				h5.setStatus(status);
                Integer appId = app.getAppId();//同步测试数据库会对appId有影响,同步到测试库前appId
                openSdkSynDataServiceImpl.synApp(OpenSdkSynDataService.TEST_DB, app);
                //同步到测试数据库会将app对象的appId值修改为测试数据库中的appId值, 后面影响对非测试库中通过appId对数据修改
                app.setAppId(appId);//同步测试库前的appId
				String errorMsg=synPay(h5);
				if (errorMsg!=null) return errorMsg;
			}else if (status==deny_access.getStatus()){
				remark=app.getRemark();
				if (StringUtils.isEmpty(remark)) return "游戏接入驳回，驳回理由不能为空";
				flag =true;
			}
			break;
		}
		case online_adjust:{ // 线上环境联调申请
			if (status==online_adjust_finish.getStatus()){ // 线上联调审批通过
				flag =true;
			}else if (status==access_finish.getStatus()){// 线上联调申请驳回到已接入
				remark=app.getRemark();
				if (StringUtils.isEmpty(remark)) return "游戏线上联调申请驳回，驳回理由不能为空";
				flag =true;
			}
			break;
		}
		case test: {// 测试申请
			if (status==test_finish.getStatus()){//测试审批通过
				flag =true;
			}else if (status==deny_test.getStatus()){// 测试申请驳回
				remark=app.getRemark();
				if (StringUtils.isEmpty(remark)) return "游戏测试申请驳回，驳回理由不能为空";
				flag =true;
			}
			break;
		}
		case wait_shelf:
		case hidden:
		case shelf_off:{ // 待上架 、展示下线、下架
			if (status==shelf_finish.getStatus()){
				flag =true;
				needSyn=true;
				doShelf(app);
			}
			break;
		}
		case shelf_finish:{ //已接入 上架申请
			if (AppStatusEnum.inStatus(status, AppStatusEnum.hidden, AppStatusEnum.shelf_off)){
				flag =true;
				needSyn=true;
			}
			break;
		}
		default:;
		}
		if (flag){
			if (needSyn){
				String error=synH5(app);
				if (error!=null) return error;
			}
			if (StringUtils.isEmpty(remark)){
                h5GameMapper.updateStatus(app.getAppId(), status);
			} else {
				H5Game t = new H5Game();
				t.setAppId(app.getAppId());
				t.setAppCode(app.getAppCode());
				t.setRemark(remark);
				t.setStatus(status);
				h5GameMapper.update(t);
			}
			
		} else {
			log.error("H5游戏流程审批异常，变更前状态为{}，变更后状态为{}，判断为非法操作",
					getStatusDesc(pastStatus), getStatusDesc(status));
			return "游戏流程审批被拒绝";
		}
		return null;
	}

    @Override
    public String syncCallbackUrl(Object obj) {
		String errorMsg = null;
		String appCode = null;
		Integer status = null;
		
		if(obj instanceof MerchantAppInfo){//手游,页游
			appCode = ((MerchantAppInfo) obj).getAppCode();
			status = ((MerchantAppInfo) obj).getStatus();
		}else {//H5
			appCode = ((H5Game) obj).getAppCode();
			status = ((H5Game) obj).getStatus();
		}
		log.info("appCode:{}, 应用状态:{}", appCode, status);
		//只在上下架同步数据
		if (status != null && AppStatusEnum.inStatus(status, AppStatusEnum.shelf_finish, AppStatusEnum.shelf_off)) {
			log.info("修改回调地址同步数据");
			//同步数据至sdkserver
			errorMsg =  synPay(obj);
			if(!StringUtils.isEmpty(errorMsg)){
				log.error("修改回调地址>>>同步数据至sdk失败, 失败信息:{}", errorMsg);
				return errorMsg;
			}
			//开放平台应用接入后,前台修改(原修改回调)时同步修改后的手游,页游,H5游戏信息
			errorMsg = synModify(appCode);
			if(!StringUtils.isEmpty(errorMsg)){
				log.error("修改回调地址>>>同步手游/页游/H5游戏信息失败, 失败信息:{}", errorMsg);
				return errorMsg;
			}
		}else {
			log.info("修改回调地址>>>应用不是上架和下架状态不需要同步数据");
		}
		return null;
	}



	/**
	 * 开放平台应用接入后,前台修改(原修改回调)时同步修改后的手游,页游,H5游戏信息
	 * @param appCode
	 * @return
	 */
	@Override
	public String synModify(String appCode) {
		//为同步数据准备游戏数据
		MerchantAppInfo app = appService.selectApp(appCode);
		//手游,页游,H5
		GameTypeEnum em = GameUtils.analyseGameType(app.getAppCode());
		switch (em) {
			case yeyougame: {
				log.info("同步页游信息");
				return synYeyou(app);
			}
			case h5game: {
				log.info("同步H5信息");
				return synH5(app);
			}
			case mobilegame:{
				log.info("同步手游信息");
				return doSynchronize(app);
			}
		}
		return null;
	}

    private void saveH5Key(MerchantAppInfo app) {
		// 如果已经生成，不需要再生成
		if (h5GameMapper.selectKey(app.getAppId())>0) return;
		// 生成三种key
		H5Game h5=new H5Game();
		h5.setAppId(app.getAppId());
		String loginKey = generateH5Key();
		h5.setLoginKey(loginKey);
		String payKey = generateH5Key();
		h5.setPayKey(payKey);
		String appKey = generateH5Key();
		h5.setAppKey(appKey);
		h5GameMapper.insertKey(h5);
	}

	//保存页游key
	private YeyouGame saveYeyouKey(String appKey) {
		YeyouGame yeyouGame = (YeyouGame)yeyouGameMapper.select(appKey);

		if(StringUtils.isBlank(yeyouGame.getAppKey())) {
			yeyouGame.setAppKey(generateYeyouGameKey());
		}
		if(StringUtils.isBlank(yeyouGame.getLoginKey())) {
			yeyouGame.setLoginKey(generateYeyouGameKey());
		}
		if(StringUtils.isBlank(yeyouGame.getPayKey())){
			yeyouGame.setPayKey(generateYeyouGameKey());
		}
		yeyouGameMapper.update(yeyouGame);

		return yeyouGame;
	}

	/**
	 * h5游戏loginKey,payKey,appKey
	 * 要求16位，字母或数字，小写
	 * @return
	 */
	private String generateH5Key(){
		String uuid =UUID.randomUUID().toString();
		uuid=uuid.replaceAll("-", "");
		return uuid.toLowerCase().substring(0, 16);
	}

	//生成页游loginKey,payKey,appKey
	private String generateYeyouGameKey(){
		//使用也H5相同生成key的方法
		return generateH5Key();
	}
	
	/**
	 * 同步手游、页游、h5到sdkserver
	 * @param obj
	 * @return
	 */
	private String synPay(Object obj){
		GameSynPayBase synVo =null;
		List<NameValuePair> list = new ArrayList<>();
		if (obj instanceof MerchantAppInfo){// 手游 页游传的对象也是MerchantAppInfo
			MerchantAppInfo app=(MerchantAppInfo)obj;
			String appType=app.getAppType();
			if (StringUtils.isNotEmpty(appType) &&!Constants.APP_TYPE_GAME.equals(appType)){
				return null; // 手游中的网站应用和一般应用不需要同步
			}
			MobileGameSynPayVo vo = new MobileGameSynPayVo();
			
			vo.setPayNotifyUrl(app.getPayCallBackUrl());
			vo.setTestPayNotifyUrl(app.getTestPayCallBackUrl());
			// transient字段
			vo.setOutPrivateRsaKey(app.getOutPrivateRsakey());
			vo.setOutPublicRsaKey(app.getOutPublicRsakey());
			vo.setTestOutPrivateRsaKey(app.getTestPrivateRsakey());
			vo.setTestOutPublicRsaKey(app.getTestPublicRsakey());
			vo.setMerchantId(app.getMerchantInfoId());
			vo.setChannelCode(app.getShelfChannel());
			list.add(new BasicNameValuePair("outPrivateRsaKey", app.getOutPrivateRsakey()));
			list.add(new BasicNameValuePair("outPublicRsaKey", app.getOutPublicRsakey()));
			list.add(new BasicNameValuePair("testOutPrivateRsaKey", app.getTestPrivateRsakey()));
			list.add(new BasicNameValuePair("testOutPublicRsaKey", app.getTestPublicRsakey()));
			list.add(new BasicNameValuePair("loginCallBackUrl", app.getLoginCallBackUrl()));
			list.add(new BasicNameValuePair("testLoginCallBackUrl", app.getTestLoginCallBackUrl()));
            //注册回调 (手游)
            if(!app.getAppCode().startsWith("Y")){ //非页游即为手游
                list.add(new BasicNameValuePair("registCallBackUrl",app.getRegistCallBackUrl()));
                list.add(new BasicNameValuePair("testRegistCallBackUrl",app.getTestRegistCallBackUrl()));
            }else {
				//登录key,支付key,appKey
				YeyouGame yeyouGame = (YeyouGame)yeyouGameMapper.select(app.getAppCode());
				if(!StringUtils.isBlank(yeyouGame.getAppKey())){
					list.add(new BasicNameValuePair("appKey", yeyouGame.getAppKey()));
				}
				if(!StringUtils.isBlank(yeyouGame.getLoginKey())){
					list.add(new BasicNameValuePair("loginKey", yeyouGame.getLoginKey()));
				}
				if(!StringUtils.isBlank(yeyouGame.getPayKey())){
					list.add(new BasicNameValuePair("payKey", yeyouGame.getPayKey()));
				}
            }
			synVo=vo;
		} else if (obj instanceof H5Game){// H5游戏
			H5Game h5Game=(H5Game)obj;
			H5GameSynPayVo vo = new H5GameSynPayVo();

			AppInfoEntity appInfoEntity = new AppInfoEntity();
			BeanUtils.copyProperties(h5Game, appInfoEntity);
			appInfoEntity.setMerchantId(h5Game.getMerchantInfoId());
			appInfoEntity.setGameUrl(h5Game.getAppUrl());
			appInfoEntity.setPayNotifyUrl(h5Game.getPayCallBackUrl());
			
			vo.setPayNotifyUrl(h5Game.getPayCallBackUrl());
			
			// 根据应用编码，判断上架渠道
			String appCode = h5Game.getAppCode();
			String channelCode=null;
			if (appCode.startsWith("HD")){
				channelCode=AppChannelEnum.baowan.getType(); // 冰趣
			} else {
				channelCode=AppChannelEnum.qb.getType(); //宝玩
			}
			
			appInfoEntity.setChannelCode(channelCode);
			openSdkSynDataServiceImpl.synH5(OpenSdkSynDataService.TEST_DB, appInfoEntity);
			
			vo.setChannelCode(channelCode);
			vo.setGameUrl(h5Game.getAppUrl());
			list.add(new BasicNameValuePair("loginKey", h5Game.getLoginKey()));
			list.add(new BasicNameValuePair("payKey", h5Game.getPayKey()));
			list.add(new BasicNameValuePair("appKey", h5Game.getAppKey()));
			list.add(new BasicNameValuePair("gameUrl", h5Game.getAppUrl()));
			synVo=vo;
//		} else if (obj instanceof YeyouGame){// 页游
//			YeyouGame yeyouGame=(YeyouGame)obj;
//			GameSynPayBase vo = new GameSynPayBase();
//			vo.setPayNotifyUrl(yeyouGame.getPayCallBackUrl());
//			
//			synVo=vo;
		}
		
		BeanUtils.copyProperties(obj, synVo);
		list.add(new BasicNameValuePair("appCode", synVo.getAppCode()));
		list.add(new BasicNameValuePair("appName", synVo.getAppName()));
		list.add(new BasicNameValuePair("merchantId", synVo.getMerchantId()+"")); // h5平台和页游在处理时会取图片流，图片需要能访问到，要不会返回1005游戏添加失败
		list.add(new BasicNameValuePair("channelCode", synVo.getChannelCode()));
		// 展示下线状态也为1 
		if (synVo.getStatus()==AppStatusEnum.shelf_off.getStatus()){
			synVo.setStatus(0);
		} else {
			synVo.setStatus(1);
		}
		list.add(new BasicNameValuePair("status", synVo.getStatus()+""));
		list.add(new BasicNameValuePair("payNotifyUrl", synVo.getPayNotifyUrl()));

		if (StringUtils.isNotEmpty(synVo.getTestPayNotifyUrl())){
			list.add(new BasicNameValuePair("testPayNotifyUrl", synVo.getTestPayNotifyUrl()));
		}
		log.info("同步应用到sdkserver：{}", (JSONArray.toJSONString(list)));
		
//		String response = ApiConnector.postJson(domainConfig.getString("domesdk.game.synsdkserver"), (JSON)JSON.toJSON(synVo));
		String response = ApiConnector.post(domainConfig.getString("domesdk.game.synsdkserver"), list);
		log.info("同步应用到sdkserver返回结果：{}", response);
		
		if (!StringUtils.isEmpty(response)){
			JSONObject jsonObj = JSON.parseObject(response);
			if (jsonObj.getInteger("responseCode") != 1000 ){
				String errorMsg=jsonObj.getString("errorMsg");
				return StringUtils.isNotEmpty(errorMsg) ?errorMsg:"同步应用到sdkserver失败";
			}
			
		}
		return null;
	}
}
