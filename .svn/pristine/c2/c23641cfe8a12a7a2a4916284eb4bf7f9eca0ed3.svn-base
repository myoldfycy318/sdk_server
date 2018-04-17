package com.dome.sdkserver.controller;

import static com.dome.sdkserver.common.Constants.H5_BINGQU_MARK;
import static com.dome.sdkserver.common.Constants.PATTERN_NUM;
import static com.dome.sdkserver.constants.AppStatusEnum.getStatusDesc;
import static com.dome.sdkserver.constants.AppStatusEnum.wait_access;
import static com.dome.sdkserver.constants.AppStatusEnum.wait_shelf;
import static com.dome.sdkserver.constants.newgame.GameTypeEnum.h5game;
import static com.dome.sdkserver.constants.newgame.GameTypeEnum.yeyougame;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.*;
import com.dome.sdkserver.util.ServletUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dome.sdkserver.aop.AppOperLogger;
import com.dome.sdkserver.bo.pkgmanage.Pkg;
import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.constants.BizParamResponseEnum;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;
import com.dome.sdkserver.metadata.entity.AbstractGame;
import com.dome.sdkserver.metadata.entity.h5.H5Game;
import com.dome.sdkserver.metadata.entity.yeyou.YeyouGame;
import com.dome.sdkserver.service.AppService;
import com.dome.sdkserver.service.AppTypeAttrService;
import com.dome.sdkserver.service.CallbackAuditService;
import com.dome.sdkserver.service.ChargePointService;
import com.dome.sdkserver.service.MerchantAppService;
import com.dome.sdkserver.service.MerchantInfoService;
import com.dome.sdkserver.service.PkgService;
import com.dome.sdkserver.service.newgame.GameService;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.util.ParamValiUtils;
import com.dome.sdkserver.util.UploadUtil;
import com.dome.sdkserver.util.business.GameUtils;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.sdkserver.vo.MerchantAppVo;
import com.dome.sdkserver.web.util.AppUtil;
import com.dome.sdkserver.web.util.IPUtil;
/**
 * 前台商户应用controller
 * @author hexiaoyi
 *
 */
@Controller
@RequestMapping("/merchantapp")
public class MerchantAppController extends BaseController{

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private MerchantAppService merchantAppService;
	
	@Resource
	private MerchantInfoService merchantInfoService;
	
	@Resource
	private AppTypeAttrService appTypeAttrService;
	
	@Resource
	private ChargePointService chargePointServiceImpl;
	
	
	private static final String APP_TYPE_GAME = "10000000";
	
	private static final String APP_TYPE_WEBAPPLICATION = "20000000";
	
	private static final String APP_TYPE_MOBILEAPPLICATION = "30000000";
	
	
    /**
     * 上传图片限制为10M
     */
    private static final int imgMaxFileSize = 10 * 1024 * 1024;
    
    @Resource
    private PkgService pkgServiceImpl;
    
    @Autowired
    private GameService gameService;


    @Autowired
    private CallbackAuditService callbackAuditService;
    
    @Autowired
    private AppService appService;
	
	// 查询h5game,yeyougame 应用列表
	private AjaxResult selectGameAppList(String type, Paginator paginator, HttpServletRequest request){
		try {
			int userId = (int) this.getCurrentUserId(request);
			if (isH5Game(type)) type=h5game.name();
			Map<String, Object> gameDataMap = gameService.selectList(type, userId);
			@SuppressWarnings("unchecked")
			List<AbstractGame> list=(List<AbstractGame>) gameDataMap.get("list");
			// 应用列表过滤，在商家下所有应用基础上过滤，不是sql语句上的过滤
			String statusStr = request.getParameter("status");
			boolean needStatusFilter = false;
			int status = -1;
			if (!StringUtils.isEmpty(statusStr)){
				status = Integer.parseInt(statusStr);
				needStatusFilter = true;
			}
			String typeStr = request.getParameter("type");
			Date startDate = null;
			boolean needDateFilter = false;
			if (!StringUtils.isEmpty(typeStr)){
				int typeFilter = Integer.parseInt(typeStr);
				startDate = getStartDate(typeFilter);
				needDateFilter = true;
			}
			String keyTextStr = request.getParameter("keyText");
			String keyText = null;
			boolean needKeyTextFilter = false;
			if (!StringUtils.isEmpty(keyTextStr)) {
				keyText = keyTextStr.trim();
				needKeyTextFilter = true;
			}
			Iterator<AbstractGame> it = list.iterator();
			
			while (it.hasNext()) {
				AbstractGame app = it.next();
				if (needStatusFilter && app.getStatus() != status) {
					it.remove();
				}
				// 申请时间
				if (needDateFilter && startDate.compareTo(app.getCreateTime()) > 0){
					it.remove();
				}
				if (needKeyTextFilter) {
					// 关键字搜索：应用名称模糊匹配，应用编码全部匹配
					if (!app.getAppName().contains(keyText) && !app.getAppCode().equals(keyText)){
						it.remove();
					}
				}
			}
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("totalCount", list.size());
			list=Paginator.page(list, paginator, request);
			
//			dataMap.put("list", yeyougame.name().equalsIgnoreCase(type) ? TypedCollection.decorate(list, YeyouGame.class):
//				(h5game.name().equalsIgnoreCase(type) ?TypedCollection.decorate(list, H5Game.class):list));
			for (AbstractGame game:list){
				status=game.getStatus();
				game.setStatusDesc(AppStatusEnum.getStatusDesc(status));
			}
			dataMap.put("appList", list); // list集合中元素就为H5Game、YeyouGame
			return AjaxResult.success(dataMap);
		} catch (Exception e) {
			log.error("获取游戏列表出错", e);
		}
		
		return AjaxResult.failedSystemError();
	}
	
	
	/**
	 * 获取应用列表
	 * 应用列表过滤 1、状态过滤   2、日期段过滤  3、搜索
	 * 每个商户下面的应用数量有限，使用假分页
	 * @param request
	 * @return
	 */
	@RequestMapping("/v1/applist")
	@ResponseBody
	public AjaxResult getAppList(String t, Paginator paginator, HttpServletRequest request) {
		//根据当前用户获得商户信息
		MerchantInfo merchantInfo = this.getCurrentMerchant(request);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(merchantInfo == null || isIllegalMerchant(merchantInfo)){ // 审核通过的商户
			
			return AjaxResult.failed("非法商户用户");
		}
		if (t!=null){
			if (yeyougame.name().equalsIgnoreCase(t) || isH5Game(t)){
				return selectGameAppList(t, paginator, request);
			}
		}
		// 开放平台老的应用管理，对应手游
		String pageNum = request.getParameter("pageNum");
		// 兼容老的开放平台使用pageNum入参，表示页码
		if (StringUtils.isNotEmpty(pageNum)
				&& PATTERN_NUM.matcher(pageNum).matches()){
			if (paginator==null){
				paginator = new Paginator();
				
			}
			if (paginator.getPageNo()<=0){
				paginator.setPageNo(Integer.parseInt(pageNum));
			}
		}
		try {
			//根据商户id分页查询应用结果
			List<MerchantAppInfo> appList = merchantAppService.getAppListByMertId(merchantInfo.getMerchantInfoId(), null);
			// 应用列表过滤，在商家下所有应用基础上过滤，不是sql语句上的过滤
			String statusStr = request.getParameter("status");
			boolean needStatusFilter = false;
			int status = -1;
			if (!StringUtils.isEmpty(statusStr)){
				status = Integer.parseInt(statusStr);
				needStatusFilter = true;
			}
			String typeStr = request.getParameter("type");
			Date startDate = null;
			boolean needDateFilter = false;
			if (!StringUtils.isEmpty(typeStr)){
				int type = Integer.parseInt(typeStr);
				startDate = getStartDate(type);
				needDateFilter = true;
			}
			String keyTextStr = request.getParameter("keyText");
			String keyText = null;
			boolean needKeyTextFilter = false;
			if (!StringUtils.isEmpty(keyTextStr)) {
				keyText = keyTextStr.trim();
				needKeyTextFilter = true;
			}
			Iterator<MerchantAppInfo> it = appList.iterator();
			
			while (it.hasNext()) {
				MerchantAppInfo app = it.next();
				if (needStatusFilter && app.getStatus() != status) {
					it.remove();
				}
				// 申请时间
				if (needDateFilter && startDate.compareTo(app.getCreateTime()) > 0){
					it.remove();
				}
				if (needKeyTextFilter) {
					// 关键字搜索：应用名称模糊匹配，应用编码全部匹配
					if (!app.getAppName().contains(keyText) && !app.getAppCode().equals(keyText)){
						it.remove();
					}
				}
			}
			int totalCount = appList.size();
			Paginator p = Paginator.handlePage(paginator, totalCount, request);
			final int pageSize = p.getPageSize();
			final int start=p.getStart();
			List<MerchantAppInfo> dataList = null;
			if (appList != null && totalCount > pageSize) {
				int toIndex = -1;
				if (start + pageSize < appList.size()){
					toIndex = start + pageSize;
				} else {
					toIndex = appList.size();
				}
				dataList = appList.subList(start, toIndex);
			} else if (appList != null){
				totalCount = appList.size();
				dataList = appList;
			}
			List<MerchantAppVo> list=new ArrayList<>(dataList.size());
			for (MerchantAppInfo app:dataList){
				MerchantAppVo appVo = new MerchantAppVo();
				BeanUtils.copyProperties(app, appVo);
				// json数据中写入是否可以包体管理、是否可以测试环境联调申请。已接入状态需要做判断，其他状态不需要这个
				if (app.getStatus() == AppStatusEnum.access_finish.getStatus()){
					String appCode = appVo.getAppCode();
					boolean canPkgManage = chargePointServiceImpl.isAllAvailable(appCode);
					appVo.setCanPkgManage(canPkgManage ? 1 : 0);
					Pkg pkg = pkgServiceImpl.query(appCode);
					appVo.setCanTestAdjust(pkg == null ? 0 : 1);
				}
				list.add(appVo);
			}
			dataMap.put("totalCount", dataList == null ? 0 : totalCount);
			dataMap.put("appList", dataList == null ? new ArrayList<MerchantAppInfo>()
					: list);
			return AjaxResult.success(dataMap);
		} catch (Exception e) {
			log.error("获取应用列表出错", e);
			
		}
		
		return AjaxResult.failedSystemError();
			
		
	}
	
	/**
	 * 获得过滤的开始时间
	 * type 1、最近一周 2、最近 三月 3、最近一年
	 * @param type
	 * @return
	 */
	private Date getStartDate(int type){
		
		Calendar c = Calendar.getInstance();
		switch (type) {
		case 1:{ // 最近一周
			c.add(Calendar.DATE, -6);
			break;
		}
		case 2:{ // 当前月份，1号
			c.add(Calendar.MONTH, -2);
			c.set(Calendar.DAY_OF_MONTH, 1);
			break;
		}	
		case 3:{ // 当前年份,1月1号
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
			break;
		}	

		default:
			break;
		}
		// 去掉时分秒
		Date startDate = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATAFORMAT_STR);
		try {
			startDate = sdf.parse(sdf.format(c.getTime()));
		} catch (ParseException e) {
			log.error("parse date error", e);;
		}
		return startDate;
	}
	
	// 校验游戏参数
	private String checkGameParam(String type, MerchantAppInfo app, HttpServletRequest request){
		if (isH5Game(type)){
			String appUrl =app.getAppUrl();
			if (StringUtils.isEmpty(appUrl) || ParamValiUtils.valiUrl(appUrl).length()>0){
				return "游戏URL不合法";
			}
			String compareSoftwareUrl=request.getParameter("compareSoftwareUrl");
			if (StringUtils.isNotEmpty(compareSoftwareUrl) && ParamValiUtils.valiUrl(compareSoftwareUrl).length()>0){
				return "软件著作权登记证书图片不合法";
			}
			// 游戏类型不能为空
			
			if (StringUtils.isEmpty(app.getGameType()) ||app.getGameType().length()>128){
				return "游戏类型不合法";
			}
			String developers=request.getParameter("developers");// 开发商不允许为空
			if (StringUtils.isEmpty(developers) || developers.length()>256){
				return "开发商不合法";
			}
		}
		return null;
	}
	
	private boolean isH5Game(String type){
		return h5game.name().equalsIgnoreCase(type) || H5_BINGQU_MARK.equalsIgnoreCase(type);
	}
	// 新增和编辑h5game,yeyougame
	private AjaxResult saveGame(String type, MerchantAppInfo app, HttpServletRequest request){
		//参数校验不通过
		Map<String, Object> resultMap = this.valParam(request, "gamesave");
		Boolean isSuccess = (Boolean)resultMap.get("isSuccess");
		if(!isSuccess){
			return AjaxResult.conventMap(resultMap);
		}
		String errorMsg = checkGameParam(type, app, request);
		if (errorMsg!=null) return AjaxResult.failed(errorMsg);
		final String appCode = app.getAppCode();
		boolean  isAdd= StringUtils.isEmpty(appCode);
		if (!isAdd && !isLegalMerchant(appCode, request)){
			return AjaxResult.failed("你没有权限操作该应用，appCode="+appCode);
		}
		AbstractGame game =null;
		if (yeyougame.name().equalsIgnoreCase(type)){

            YeyouGame yeyouGame = new YeyouGame();
            yeyouGame.setLoginCallBackUrl(request.getParameter("loginCallbackUrl"));//页游新增登录回调字段

            game = yeyouGame;
        } else if (isH5Game(type)) {

            H5Game h5Game = new H5Game();
            // 游戏版权标识，1自研 2代理
            String copyRightFlagParam = request.getParameter("copyRightFlag");
            if (StringUtils.isNotEmpty(copyRightFlagParam) && PATTERN_NUM.matcher(copyRightFlagParam).matches()) {
                h5Game.setCopyRightFlag("1".equals(copyRightFlagParam) ? 1 :
                        "2".equals(copyRightFlagParam) ? 2 : 0);
            }
            h5Game.setCompareSoftwareUrl(request.getParameter("compareSoftwareUrl"));
            String developers = request.getParameter("developers");
            if (StringUtils.isNotEmpty(developers)) h5Game.setDevelopers(developers);
            game = h5Game;
        }
        GameUtils.initAppParam(app);
        BeanUtils.copyProperties(app, game);
        try {
            // 在业务方法中做了校验游戏名称是否重复
            if (isAdd) {
                game.setUserId((int) this.getCurrentUserId(request));
                errorMsg = gameService.addGame(type, game);
                if (errorMsg == null) app.setAppCode(game.getAppCode());
            } else {
                String queryType = isH5Game(type) ? GameTypeEnum.h5game.name() : type;
                AbstractGame pastGame = gameService.select(queryType, appCode);
                if (AppStatusEnum.notInStatus(pastGame.getStatus(), AppStatusEnum.unaccess, AppStatusEnum.deny_access)) { // 只有未接入和已驳回时可以编辑应用
                    return AjaxResult.failed("游戏状态为" +
                            getStatusDesc(pastGame.getStatus()) + "，不能编辑");
                }
                game.setAppId(pastGame.getAppId());
                errorMsg = gameService.update(queryType, game);
            }
            if (errorMsg != null) return AjaxResult.failed(errorMsg);

            return AjaxResult.success();
        } catch (Exception e) {
            log.error("新增和编辑游戏出错", e);
        }
        return AjaxResult.failedSystemError();
    }


    /**
     * 新增应用和编辑
     *
     * @param request
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    @AppOperLogger
    public AjaxResult save(String t, MerchantAppInfo app, HttpServletRequest request) {
        //根据当前用户获得商户信息
        MerchantInfo merchantInfo = this.getCurrentMerchant(request);
        //如果此商户没有注册过，则不能添加应用
        if (app == null || merchantInfo == null || isIllegalMerchant(merchantInfo)) {
            return AjaxResult.failed("商户信息不存在或审批没有通过");

        } else {
            request.setAttribute("status", AppStatusEnum.unaccess); // 未接入
            int pastStatus = 0;
            if (StringUtils.isNotEmpty(app.getAppCode())) {
                MerchantAppInfo appTmp = this.appService.selectApp(app.getAppCode());
                pastStatus = appTmp.getStatus();
            }
            request.setAttribute("pastStatus", pastStatus);
            if (t != null) {
                if (yeyougame.name().equalsIgnoreCase(t) || isH5Game(t)) {
                    return saveGame(t, app, request);
                }
            }
            //参数校验不通过
            Map<String, Object> resultMap = this.valParam(request, "appsave");
            Boolean isSuccess = (Boolean) resultMap.get("isSuccess");
            if (!isSuccess) {
                return AjaxResult.conventMap(resultMap);
            }
            //参数校验不通过
            String errorMsg = this.validateAppParam(app);
            if (errorMsg != null) {
                return AjaxResult.failed(errorMsg);
            }

            if (StringUtils.isEmpty(app.getAppCode())) { // 新增应用

                String ip = IPUtil.getIpAddr(request);
                app.setCreateIp(ip);
                app.setDelFlag(0);
            } else {
                // 修改应用
                MerchantAppInfo pastApp = merchantAppService.queryApp(app.getAppCode());
                int status = pastApp.getStatus();
                if (status != AppStatusEnum.unaccess.getStatus() && status != AppStatusEnum.deny_access.getStatus()) {
                    return AjaxResult.failed("只有未接入和接入驳回时可以修改应用");
                }
            }
            app.setStatus(AppStatusEnum.unaccess.getStatus());
            app.setMerchantInfoId(merchantInfo.getMerchantInfoId());
            try {
                merchantAppService.doSaveMerchantApp(app);
                return AjaxResult.success();
            } catch (Exception e) {
                errorMsg = e.getMessage();
                log.error("新增应用出错", e);
                if (e instanceof RuntimeException) {
                    return AjaxResult.failed(errorMsg);
                }
            }
            return AjaxResult.failedSystemError();
        }

    }

    @RequestMapping("/save4DomeManager")
    @ResponseBody
    @AppOperLogger
    public AjaxResult save4DomeManager(String t, MerchantAppInfo app, HttpServletRequest request) {
        //根据当前用户获得商户信息
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerchantInfoId(Constants.DOME_DEFAULT_MERCHANTID);
        merchantInfo.setStatus(2);
        //如果此商户没有注册过，则不能添加应用
        if (app == null || merchantInfo == null || isIllegalMerchant(merchantInfo)) {
            return AjaxResult.failed("商户信息不存在或审批没有通过");

        } else {
            request.setAttribute("status", AppStatusEnum.unaccess); // 未接入
            int pastStatus = 0;
            if (StringUtils.isNotEmpty(app.getAppCode())) {
                MerchantAppInfo appTmp = this.appService.selectApp(app.getAppCode());
                pastStatus = appTmp.getStatus();
            }
            request.setAttribute("pastStatus", pastStatus);
            if (t != null) {
                if (yeyougame.name().equalsIgnoreCase(t) || isH5Game(t)) {
                    return saveGame(t, app, request);
                }
            }
            //参数校验不通过
            Map<String, Object> resultMap = this.valParam(request, "appsave");
            Boolean isSuccess = (Boolean) resultMap.get("isSuccess");
            if (!isSuccess) {
                return AjaxResult.conventMap(resultMap);
            }
            //参数校验不通过
            String errorMsg = this.validateAppParam(app);
            if (errorMsg != null) {
                return AjaxResult.failed(errorMsg);
            }

            if (StringUtils.isEmpty(app.getAppCode())) { // 新增应用

                String ip = IPUtil.getIpAddr(request);
                app.setCreateIp(ip);
                app.setDelFlag(0);
            } else {
                // 修改应用
                MerchantAppInfo pastApp = merchantAppService.queryApp(app.getAppCode());
                int status = pastApp.getStatus();
                if (status != AppStatusEnum.unaccess.getStatus() && status != AppStatusEnum.deny_access.getStatus()) {
                    return AjaxResult.failed("只有未接入和接入驳回时可以修改应用");
                }
            }
            app.setStatus(AppStatusEnum.unaccess.getStatus());
            app.setMerchantInfoId(merchantInfo.getMerchantInfoId());
            try {
                String appCode = merchantAppService.doSaveMerchantApp(app);
                Map<String, String> result = new HashMap<>();
                result.put("appCode", appCode);
                return AjaxResult.success(result);
            } catch (Exception e) {
                errorMsg = e.getMessage();
                log.error("新增应用出错", e);
                if (e instanceof RuntimeException) {
                    return AjaxResult.failed(errorMsg);
                }
            }
            return AjaxResult.failedSystemError();
        }

    }

    private String validateAppParam(MerchantAppInfo app) {
        String msg = null;
        // 游戏必须要提供游戏类型和游戏属性
        if (APP_TYPE_GAME.equals(app.getAppType())) {
            if (StringUtils.isEmpty(app.getGameType())) {
                msg = "游戏类型不能为空";
            }
//			else if (StringUtils.isEmpty(app.getGameAttr())){
//				msg = "游戏属性不能为空";
//			}
			else if (StringUtils.isEmpty(app.getGameDesc())){
				msg = "一句话简介不能为空";
			}
		} else {
			// 移动应用需要提供运行平台
			if (!APP_TYPE_WEBAPPLICATION.equals(app.getAppType()) && StringUtils.isEmpty(app.getRunPlatform())){
				msg = "运行平台不能为空";
			}
			if (APP_TYPE_WEBAPPLICATION.equals(app.getAppType())) {
				app.setRunPlatform("");
			}
			app.setGameType("");
			app.setGameAttr("");
			app.setGameDesc(""); // 游戏一句话描述
		}
		app.setRemark("");
		return msg;
	}
	
	private AjaxResult viewGame(GameTypeEnum em, String appCode){
		AbstractGame game =gameService.select(em.name(), appCode);
		if (game==null){
			return AjaxResult.failed("游戏没有查询到，appCode="+appCode);
		}
		return AjaxResult.success(game);
	}
	/**
	 * 跳转查看页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toview")
	@ResponseBody
	public AjaxResult toView(String appCode, HttpServletRequest request) {
		if (StringUtils.isEmpty(appCode)){
			return AjaxResult.failed("没有提供应用编码");
		}
		if (!isLegalMerchant(appCode, request)){
			return AjaxResult.failed("没有权限查看该应用");
		}
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		switch (em){
		case yeyougame:
		case h5game: {
			return viewGame(em, appCode);
		}
		
		default:;
		}
		MerchantAppInfo merchantAppInfo = merchantAppService.queryApp(appCode);
		return AjaxResult.success(merchantAppInfo);
	}
	
	// 删除游戏
	private AjaxResult deleteGame(GameTypeEnum em, String appCode){
		AbstractGame game =gameService.select(em.name(), appCode);
//		if (game==null){
//			return AjaxResult.failed("游戏没有查询到，appCode="+appCode);
//		}
		if (AppStatusEnum.notInStatus(game.getStatus(), AppStatusEnum.unaccess, AppStatusEnum.deny_access)){ // 只有未接入和已驳回时可以删除应用
			return AjaxResult.failed("游戏不能删除");
		}
		String errorMsg=gameService.delete(em.name(), game.getAppId());
		if (errorMsg!=null) return AjaxResult.failed(errorMsg);
		return AjaxResult.success();
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/dodel")
	@ResponseBody
	@AppOperLogger
	public AjaxResult doDel(String appCode, HttpServletRequest request) {
		
		if(!isLegalMerchant(appCode, request)){
			return AjaxResult.failed("你没有权限操作该应用，appCode="+appCode);
		}
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		MerchantAppInfo appTmp=this.appService.selectApp(appCode);
		int pastStatus=appTmp.getStatus();
		request.setAttribute("pastStatus", pastStatus);
		switch (em){
		case yeyougame:
		case h5game: {
			return deleteGame(em, appCode);
		}
		
		default:;
		}
		MerchantAppInfo app = merchantAppService.queryApp(appCode);
		if (AppStatusEnum.notInStatus(app.getStatus(), AppStatusEnum.unaccess, AppStatusEnum.deny_access)){ // 只有未接入和已驳回时可以删除应用
			return AjaxResult.failed("应用不能删除");
		}
		try{
			//根据当前用户获得商户信息
			MerchantInfo merchantInfo = this.getCurrentMerchant(request);
			merchantAppService.doDel(app, merchantInfo.getMerchantInfoId());
			return AjaxResult.success();
		}catch(Exception e){
			log.error("删除应用异常", e);
		}
		
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 图片上传
	 * @param request
	 * @return
	 */
//    @RequestMapping("/v1/uploadfile")
//    @ResponseBody
    public AjaxResult uploadFile(@RequestParam MultipartFile pic){
        // 上传的图片不能超过10M
        if (pic.getSize() > imgMaxFileSize) {
            return AjaxResult.failed("图片大小限制为10M以下");
        }

        String checkResult = AppUtil.checkPicFormat(pic.getOriginalFilename()); // 图片格式校验
        if (StringUtils.isNotEmpty(checkResult)) {
            return AjaxResult.failed(BizParamResponseEnum.IMAGE_FORMAT_ILLEGAL.getResponeMsg());

        }else{
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            try {
                BufferedImage bufferedFile = ImageIO.read(pic.getInputStream());
                ImageIO.write(bufferedFile, "png", byteArray);
                String imgUrl = UploadUtil.upload(UUID.randomUUID().toString(), byteArray.toByteArray());
                Map<String, Object> dataMap = new HashMap<String, Object>(4);
                dataMap.put("imgUrl", imgUrl);
                return AjaxResult.success(dataMap);
            } catch (IOException e) {
                log.error("上传图片出错", e);

            } finally {
                if (byteArray != null) {
                    try {
                        byteArray.close();
                    } catch (IOException e) {
                        log.error("关闭流出错", e);
                    }
                }
            }
        }
        return AjaxResult.failedSystemError();
    }
	
	@ResponseBody
	@RequestMapping("/updateState")
	@AppOperLogger
	public AjaxResult updateAppState(MerchantAppInfo app, HttpServletRequest request){
		if (app.getStatus() == null || app.getStatus()==0){
			return AjaxResult.failed("缺少参数status");
		}
		if(!isLegalMerchant(app.getAppCode(), request)){
			return AjaxResult.failed("你没有权限操作该应用，appCode="+app.getAppCode());
		}
		MerchantAppInfo pastApp = appService.selectApp(app.getAppCode());
		request.setAttribute("pastStatus", pastApp.getStatus());
		GameTypeEnum em = GameUtils.analyseGameType(app.getAppCode());
		switch (em){
		case yeyougame:{
			if (app.getStatus()==AppStatusEnum.wait_access.getStatus()){
				String payCallBackUrl=app.getPayCallBackUrl();
				if (StringUtils.isEmpty(payCallBackUrl)) return AjaxResult.failed("线上支付回调地址不能为空");
				if (StringUtils.isEmpty(app.getLoginCallBackUrl())) return AjaxResult.failed("线上登录回调地址不能为空");
			}
			return updateYeyouGameStatus(app, request);
		}
		case h5game: {
			// h5申请接入时
			if (app.getStatus()==AppStatusEnum.wait_access.getStatus()){
				String payCallBackUrl=app.getPayCallBackUrl();
				if (StringUtils.isEmpty(payCallBackUrl)) return AjaxResult.failed("线上支付回调地址不能为空");
			}
			
			return updateH5GameStatus(app, request);
		}
		
		default:;
		}
		
		// 仅游戏可以申请接入，非游戏申请接入后直接到待上架，不需要填写回调地址
		if (AppStatusEnum.wait_access.getStatus().equals(app.getStatus())){
			if (APP_TYPE_GAME.equals(pastApp.getAppType())){
				String errorMsg = initApplyAccessParam(app, pastApp);
				if (StringUtils.isNotEmpty(errorMsg)) return AjaxResult.failed(errorMsg);
			}
		}
		try {
			//手游:发行需要申请计费点. 联运不需要申请计费点.
			//查询手游bizType
			boolean needChargePoint = merchantAppService.needChargePoint(app.getAppCode());
			if(AppStatusEnum.test_adjust.getStatus().equals(app.getStatus())){
				//查询计费点
				if(needChargePoint){
					log.info("appCode:{}需要计费点", app.getAppCode());
					List<ChargePointInfo> chargePointInfoList = chargePointServiceImpl.selectPassChargePoint(app.getAppCode());
					log.info("appCode:{},chargePointInfoList:{}",app.getAppCode(), JSONObject.toJSONString(chargePointInfoList));
					if(CollectionUtils.isEmpty(chargePointInfoList)){
						return AjaxResult.failed("请先申请计费点并通过计费点审核");
					}
				}
			}
			merchantAppService.doUpdateAppStatus(pastApp, app.getStatus());
			// 邮件通知
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("更新应用状态失败", e);
			if (e instanceof RuntimeException){
				return AjaxResult.failed(e.getMessage());
			}
		}
		
		return AjaxResult.failed("更新应用状态失败");
	}
	
	private String initApplyAccessParam(MerchantAppInfo app, MerchantAppInfo pastApp){
		String errorMsg = ParamValiUtils.valiUrl(app.getPayCallBackUrl(), app.getLoginCallBackUrl(), app.getTestPayCallBackUrl(), app.getTestLoginCallBackUrl());
		if (StringUtils.isNotEmpty(errorMsg)) return errorMsg;
		if (!StringUtils.isEmpty(app.getPayCallBackUrl())) {
			pastApp.setPayCallBackUrl(app.getPayCallBackUrl());
		}
		if (!StringUtils.isEmpty(app.getLoginCallBackUrl())) {
			pastApp.setLoginCallBackUrl(app.getLoginCallBackUrl());;
		}
		if (!StringUtils.isEmpty(app.getTestPayCallBackUrl())) {
			pastApp.setTestPayCallBackUrl(app.getTestPayCallBackUrl());
		}
		if (!StringUtils.isEmpty(app.getTestLoginCallBackUrl())) {
			pastApp.setTestLoginCallBackUrl(app.getTestLoginCallBackUrl());;
		}
        if(!StringUtils.isEmpty(app.getRegistCallBackUrl())){
            pastApp.setRegistCallBackUrl(app.getRegistCallBackUrl());
        }
        if(!StringUtils.isEmpty(app.getTestRegistCallBackUrl())){
            pastApp.setTestRegistCallBackUrl(app.getTestRegistCallBackUrl());
        }
		return null;
	}
	
	private String checkAppStatus(MerchantAppInfo app){
		String errorMsg = null;
		String appCode = app.getAppCode();
		int status = app.getStatus();
		// 申请测试环境联调 条件是包体已上传，若没有上传，提示先上传包体
		
		if (status == AppStatusEnum.access_finish.getStatus()) {
			Pkg pkg = pkgServiceImpl.query(appCode);
			if (pkg == null) {
				errorMsg = "没有上传包体";
			} else if (pkg.getUploadStatus() != 1){
				errorMsg = "包体上传没有成功，需要重新上传";
			} else if (pkg.getJiaguStatus() == 0){
				errorMsg = "包体加固没有完成，请稍等片刻再来操作";
			} else if (pkg.getJiaguStatus() != 1){
				errorMsg = "系统异常";
			}
		}
		
		return errorMsg;
	}
	
	// h5 流程控制入口
	private AjaxResult updateH5GameStatus(MerchantAppInfo app, HttpServletRequest request){
		int status = app.getStatus();
		try {
			String errorMsg = gameService.updateH5GameStatus(app.getAppCode(), status);
			if (errorMsg!=null) return AjaxResult.failed(errorMsg);
			// 更新支付回调地址
			if (app.getStatus()==AppStatusEnum.wait_access.getStatus()){
				gameService.updateH5PayCallBackUrl(app.getAppCode(), app.getPayCallBackUrl());
			}
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("游戏流程申请出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	// yeyou 流程控制入口
	private AjaxResult updateYeyouGameStatus(MerchantAppInfo app, HttpServletRequest request){
		int status = app.getStatus();
		// yeyou 只能申请接入（待接入状态）和申请上架（待上架）
		if (AppStatusEnum.notInStatus(status, wait_access, wait_shelf)){
			return AjaxResult.failed("游戏状态错误, status=" +getStatusDesc(status));
		}
		try {
			String errorMsg = gameService.updateYeyouGameStatus(app.getAppCode(), status);
			if (errorMsg!=null) return AjaxResult.failed(errorMsg);
			// 更新支付回调地址
			if (app.getStatus()==AppStatusEnum.wait_access.getStatus()){
				//将原来只能修改页游支付回调的方法修改为可以修改支付和登录回调
//				gameService.updateYeyouPayCallBackUrl(app.getAppCode(), app.getPayCallBackUrl());
				gameService.updateYeyouPayCallBackUrl(app);
			}
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("游戏流程申请出错", e);
		}
		return AjaxResult.failedSystemError();
	}




    /**
     * 手游,页游,H5 申请修改回调地址
     * @param request
     * @return
     */
    @RequestMapping("/applyModifyCallbackUrl")
    @ResponseBody
    public AjaxResult addModifyCallbackUrl(MerchantAppInfo app , HttpServletRequest request){
        try{
            String appCode = app.getAppCode();
            if(StringUtils.isEmpty(appCode) ){
                return AjaxResult.failed("缺少参数");
            }
            //H5中的gameUrl 对应 MerchantAppInfo中的appUrl字段
            String gameUrl = request.getParameter("gameUrl");
            app.setAppUrl(gameUrl);

            //判断appCode是否存在
            MerchantAppInfo appInfo = appService.selectApp(appCode);
            if(appInfo == null){
                return AjaxResult.failed("该应用不存在或当前当前应用属性不符合修改条件");
            }
            //判断当前游戏的状态
            if(appInfo.getStatus() == AppStatusEnum.unaccess.getStatus()){
                return AjaxResult.failed("游戏未接入不能修改回调地址");
            }
//            //手游,页游只有上架的时候有channelCode的时候才能修改
//            if(!appCode.startsWith("H")){
//                if(!callbackAuditService.judgeChannelCodeIsNotEmpty(appCode)){
//                    return AjaxResult.failed("该游戏没有渠道Code");
//                }
//            }
            app.setAppName(appInfo.getAppName());
            //查询当前游戏是否已申请修改回调地址且还没有审核通过
            CallbackAudit audit = callbackAuditService.queryByAppCode(appCode);
            boolean isNotAudit = false;
            if(audit != null && audit.getStatus() ==0 ){
                isNotAudit = true;
            }
            if(isNotAudit){
                //当前游戏未审核
                return AjaxResult.failed("该游戏已申请修改回调地址,请等待审核");
            }
            //判断是不是以 http 或https开头 允许有大写的字母
            String errorMsg = judgeModifyUrl(app);
            if(errorMsg!=null){
                return AjaxResult.failed(errorMsg);
            }
            CallbackAudit callbackAudit = new CallbackAudit();
            callbackAudit.setAppCode(appCode);
            callbackAudit.setAppName(app.getAppName());
            callbackAudit.setLoginCallbackUrl(app.getLoginCallBackUrl());
            callbackAudit.setPayCallbackUrl(app.getPayCallBackUrl());
            callbackAudit.setTestLoginCallbackUrl(app.getTestLoginCallBackUrl());
            callbackAudit.setTestPayCallbackUrl(app.getTestPayCallBackUrl());
            //注册回调地址
            callbackAudit.setRegistCallbackUrl(app.getRegistCallBackUrl());
            callbackAudit.setTestRegistCallbackUrl(app.getTestRegistCallBackUrl());
            //H5 gameUrl
            callbackAudit.setGameUrl(app.getAppUrl());
            
			callbackAudit.setAppIcon(app.getAppIcon());

            //没有未审核的游戏可以被添加到审核并且根据appCode判断当前游戏是手游,页游还是H5
            GameTypeEnum em = GameUtils.analyseGameType(app.getAppCode());
			AjaxResult x = validateModifyParams(app, em);
			if (x != null) return x;
			//添加到审核
            callbackAuditService.insert(callbackAudit);
            return AjaxResult.success();
        }catch (RuntimeException e){
            log.error("申请修改回调地址失败",e);
            e.printStackTrace();
        }
        return AjaxResult.failed("申请修改回调地址失败");
    }

	/**
	 * 验证必填参数是否为空
	 * @param app
	 * @param em
	 * @return
	 */
	public AjaxResult validateModifyParams(MerchantAppInfo app, GameTypeEnum em) {
            if(GameTypeEnum.mobilegame == em){
                //手游
                if(StringUtils.isEmpty(app.getLoginCallBackUrl()) && StringUtils.isEmpty(app.getTestLoginCallBackUrl())
                        && StringUtils.isEmpty(app.getPayCallBackUrl()) && StringUtils.isEmpty(app.getTestPayCallBackUrl())
                        && StringUtils.isEmpty(app.getRegistCallBackUrl()) && StringUtils.isEmpty(app.getTestRegistCallBackUrl())){
                    return AjaxResult.failed("没有提供回调地址");
                }
            }else if(GameTypeEnum.yeyougame == em){
                //页游
                if (StringUtils.isEmpty(app.getPayCallBackUrl()) && StringUtils.isEmpty(app.getLoginCallBackUrl())){
                    return AjaxResult.failed("没有提供回调地址");
                }
                //限制其他回调地址的修改
                if(!StringUtils.isEmpty(app.getTestLoginCallBackUrl())
                         || !StringUtils.isEmpty(app.getTestPayCallBackUrl())){
                    return AjaxResult.failed("非法修改操作");
                }
            }else if(GameTypeEnum.h5game == em){
                //H5  gameUrl是表domesdk_h5_game中game_url字段
                if(StringUtils.isEmpty(app.getAppUrl()) && StringUtils.isEmpty(app.getPayCallBackUrl())){
                    return AjaxResult.failed("没有提供回调地址");
                }
                //限制其他回调地址的修改
                if( !StringUtils.isEmpty(app.getTestLoginCallBackUrl()) || !StringUtils.isEmpty(app.getTestPayCallBackUrl()) || !StringUtils.isEmpty(app.getLoginCallBackUrl())){
                    return AjaxResult.failed("非法修改操作");
            }
        }
		return null;
	}


	public String judgeUrl (String url){
        if( StringUtils.isEmpty(url) ){
            return null;
        }
        String[] u = url.split(":");
        String prefix = u[0].toLowerCase();
        if(prefix.length()=="http".length() && "http".equals(prefix)){
            return null;
        }
        if(prefix.length()=="https".length() && "https".equals(prefix)){
            return null;
        }
        return "url必须以http/https开头";
    }

    public String judgeModifyUrl (MerchantAppInfo app){
        boolean hasErrMsg = false;
        if(!StringUtils.isEmpty(app.getLoginCallBackUrl())  ){
            if(!StringUtils.isEmpty(judgeUrl(app.getLoginCallBackUrl()))){
                hasErrMsg = true;
            }
        }
        if(!StringUtils.isEmpty(app.getPayCallBackUrl())){
            if(!StringUtils.isEmpty(judgeUrl(app.getPayCallBackUrl()))){
                hasErrMsg = true;
            }
        }
        if(!StringUtils.isEmpty(app.getTestLoginCallBackUrl())){
            if( !StringUtils.isEmpty(judgeUrl(app.getTestLoginCallBackUrl()))){
                hasErrMsg = true;
            }
        }
        if(!StringUtils.isEmpty(app.getTestPayCallBackUrl())){
            if( !StringUtils.isEmpty(judgeUrl(app.getTestPayCallBackUrl()))){
                hasErrMsg = true;
            }
        }
        if( !StringUtils.isEmpty(app.getAppUrl())){
            //H5 gameUrl 对应 MerchantAppInfo中的appUrl
            if(!StringUtils.isEmpty(judgeUrl(app.getAppUrl()))){
                hasErrMsg = true;
            }
        }
        if(hasErrMsg){
            return "url必须以http/https开头";
        }
        return null;
    }


    /**
     * 查看手游,页游,H5的回调地址
     * @param appCode
     * @return
     */
    @RequestMapping("/toViewCallbackUrl")
    @ResponseBody
    public AjaxResult toViewCallbackUrl(String appCode){
        try{
            if(StringUtils.isEmpty(appCode)){
                return AjaxResult.failed("缺少参数");
            }
            //判断appCode是否存在
            MerchantAppInfo appInfo = appService.selectApp(appCode);
            if(appInfo == null){
                return AjaxResult.failed("该应用不存在");
            }
            Map<String,Object> data = new HashMap<String,Object>();
            GameTypeEnum em = GameUtils.analyseGameType(appCode);
            data.put("loginCallbackUrl",appInfo.getLoginCallBackUrl());
            data.put("payCallbackUrl",appInfo.getPayCallBackUrl());
            data.put("testLoginCallbackUrl",appInfo.getTestLoginCallBackUrl());
            data.put("testPayCallbackUrl",appInfo.getTestPayCallBackUrl());
            data.put("registCallBackUrl", appInfo.getRegistCallBackUrl());
            data.put("testRegistCallBackUrl", appInfo.getTestRegistCallBackUrl());
			//H5 gameUrl
            data.put("gameUrl",appInfo.getAppUrl());
			
			data.put("appIcon", appInfo.getAppIcon());
            return AjaxResult.success(data);
        }catch (Exception e){
            log.error("查看回调地址失败",e);
        }
        return AjaxResult.failed();
    }


}
