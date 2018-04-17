package com.dome.sdkserver.controller;

import static com.dome.sdkserver.constants.ChargePointStatusEnum.DRAFT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.sdkserver.aop.AppOperLogger;
import com.dome.sdkserver.bo.ChargePointInfo;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bo.SearchChargePointBo;
import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.constants.ChargePointStatusEnum;
import com.dome.sdkserver.constants.SysEnum;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;
import com.dome.sdkserver.metadata.entity.yeyou.YeYouCp;
import com.dome.sdkserver.service.ChargePointService;
import com.dome.sdkserver.service.MerchantAppService;
import com.dome.sdkserver.service.newgame.YeYouCpService;
import com.dome.sdkserver.util.business.GameUtils;
import com.dome.sdkserver.view.AjaxResult;



/**
 * 应用计费点管理
 * @author liuxingyue
 *
 */
@Controller
@RequestMapping("/chargepoint")
public class ChargePointController extends BaseController{
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final int CLICK_POST = 10;//添加计费点信息是点击"提交"按钮
//	private static final int CLICK_SAVE = 40;//添加计费点信息是点击"保存"按钮
	
	private static final int DEL_FLAG = 1;//删除状态
	@Resource
	private ChargePointService chargePointService;
	
	@Resource
	private MerchantAppService merchantAppService;
	
	@Autowired
	private YeYouCpService yeYouCpService;
	// 获取页游计费点列表，支持过滤
	private AjaxResult selectYeyouCpList(SearchChargePointBo searchChargePointBo, Paginator paginator, HttpServletRequest request){
		try {
			int totalCount =yeYouCpService.selectCount(searchChargePointBo);
			Map<String, Object> dataMap = new HashMap<>();
			List<YeYouCp> cpList=null;
			if (totalCount>0){
				paginator=Paginator.handlePage(paginator, totalCount, request);
				cpList=yeYouCpService.selectList(searchChargePointBo, paginator);
			} else {
				cpList=new ArrayList<>();
			}
			dataMap.put("totalCount", totalCount);
			dataMap.put("chargeList", cpList);
			
			return AjaxResult.success(dataMap);
		} catch (Exception e) {
			log.error("获取页游计费点列表出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	/**
	 * 计费点列表
	 * @param request
	 * @param searchChargePointBo
	 * @return
	 */
	@RequestMapping("/toList")
	@ResponseBody
	public AjaxResult getChargePointList(SearchChargePointBo searchChargePointBo, Paginator paginator, HttpServletRequest request){
		// 只显示商家下面的（必须指定appCode）
		String appCode = searchChargePointBo.getAppCode();
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		switch (em){
		case yeyougame: {
			return selectYeyouCpList(searchChargePointBo, paginator, request);
		}
		case h5game:{
			return AjaxResult.failed("H5游戏不需要计费点");
		}
		default:;
		}
		// 开放平台老的应用管理，对应手游
		String pageNum = request.getParameter("pageNum");
		// 兼容老的开放平台使用pageNum入参，表示页码
		if (StringUtils.isNotEmpty(pageNum)
				&& Constants.PATTERN_NUM.matcher(pageNum).matches()){
			if (paginator==null){
				paginator = new Paginator();
				
			}
			if (paginator.getPageNo()<=0){
				paginator.setPageNo(Integer.parseInt(pageNum));
			}
		}
		//查询符合条件的计费点总数
		int totalCount = chargePointService.getChargePontCountByCondition(searchChargePointBo);
		List<ChargePointInfo> chargePointInfos = null;
		//分页查询计费点信息
		if (totalCount > 0) {
			paginator = Paginator.handlePage(paginator, totalCount, request);
			searchChargePointBo.setStart(paginator.getStart());
			searchChargePointBo.setSize(paginator.getPageSize());
			chargePointInfos = chargePointService.getChargePointInfos(searchChargePointBo);
		} else {
			chargePointInfos = new ArrayList<ChargePointInfo>();
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("totalCount", totalCount);
		dataMap.put("chargeList", chargePointInfos);
		
		return AjaxResult.success(dataMap);
	}
	
	/**
	 * 添加计费点信息
	 * @param chargePoint
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	@AppOperLogger
	public AjaxResult doAdd(ChargePointInfo chargePoint, HttpServletRequest request){
		AjaxResult result = validateChargeParam(chargePoint, request);
		if (result != null) return result;
		if(chargePoint.getStatus() != null && chargePoint.getStatus() == CLICK_POST){//提交
			chargePoint.setStatus(ChargePointStatusEnum.WAIT_AUDIT.getStatus());//待审核
		}else{  //保存
			chargePoint.setStatus(ChargePointStatusEnum.DRAFT.getStatus());//草稿
		}
		String appCode = chargePoint.getAppCode();
		MerchantAppInfo app = appService.selectApp(appCode);
		if (app.getStatus() == AppStatusEnum.wait_access.getStatus()){
			return AjaxResult.failed("待接入的应用不能添加计费点");
		}
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		switch (em){
		case yeyougame:{
			return saveYeyouCp(chargePoint);
			
		}
		case h5game: {
			return AjaxResult.failed("H5游戏不需要计费点");
		}
		default:;
		}
		
		if (StringUtils.isEmpty(chargePoint.getPath())){
			return AjaxResult.failed("计费点路径不能为空");
		}
		String errorMsg = checkChargeName(chargePoint);
		if (errorMsg != null) return AjaxResult.failed(errorMsg);
		
		try {
			chargePoint.setChargePointCode(null);
			chargePointService.addChargePointInfo(chargePoint);
			return AjaxResult.success(errorMsg);
		} catch (Exception e) {
			log.error("添加计费点出错", e);
			errorMsg = SysEnum.SYSTEM_ERROR.getResponeMsg();
		}
		
		return AjaxResult.failed(errorMsg);
	}
	
	private AjaxResult delYeyouCp(String appCode, String chargePointCode, HttpServletRequest request){
		try {
			String errorMsg =yeYouCpService.delete(chargePointCode);
			if(errorMsg!=null) return AjaxResult.failed(errorMsg);
			return AjaxResult.success();
			
		} catch (Exception e) {
			log.error("页游删除计费点出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	/**
	 * 删除
	 * @param chargePointId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@AppOperLogger
	public AjaxResult doDel(String appCode, String chargePointCode, HttpServletRequest request){
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		switch (em){
		case h5game:{
			return AjaxResult.failed("H5游戏不需要计费点");
		}
		case yeyougame: {
			return delYeyouCp(appCode, chargePointCode, request);
		}
		
		default:;
		}
		ChargePointInfo pastChp=chargePointService.getChargePointInfoByCode(chargePointCode);
		if (pastChp.getStatus() != DRAFT.getStatus() && pastChp.getStatus() != ChargePointStatusEnum.REJECT.getStatus()){
			return AjaxResult.failed("计费点不能删除，状态为"+ChargePointStatusEnum.getStatusDesc(pastChp.getStatus()));
		}
		ChargePointInfo chargePoint = new ChargePointInfo();
		chargePoint.setChargePointId(pastChp.getChargePointId());
		chargePoint.setAppCode(appCode);
		chargePoint.setChargePointCode(chargePointCode);
		chargePoint.setDelFlag(DEL_FLAG);
		
		try {
			chargePointService.delChargePoint(chargePoint);
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("删除计费点出错", e);
		}
		return AjaxResult.failed(SysEnum.SYSTEM_ERROR.getResponeMsg());
	}
	
	private AjaxResult submitYeyouCps(String appCode, Set<String> cpSet){
		try{
			String errorMsg = yeYouCpService.batchSubmitCps(appCode, cpSet);
			if(errorMsg!=null) return AjaxResult.failed(errorMsg);
			return AjaxResult.success();
		}catch(Exception e){
			log.error("页游批量提交计费点出错,cpSet="+cpSet, e);
		}
		return AjaxResult.failedSystemError();
	}
	/**
	 * 提交
	 * @param chargePointId
	 * @return
	 */
	@RequestMapping("/post")
	@ResponseBody
	@AppOperLogger
	public AjaxResult doPost(String chargePointCode, String appCode, HttpServletRequest request){
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		switch (em){
		case yeyougame:{
			Set<String> cpSet=new HashSet<>();
			cpSet.add(chargePointCode);
			return submitYeyouCps(appCode, cpSet);
			
		}
		case h5game: {
			return AjaxResult.failed("H5游戏不需要计费点");
		}
		default:;
		}
		
		ChargePointInfo pastChargePoint = chargePointService.getChargePointInfoByCode(chargePointCode);
		if (pastChargePoint.getStatus()!=ChargePointStatusEnum.DRAFT.getStatus()){
			return AjaxResult.failed("计费点不能提交，状态为"+ChargePointStatusEnum.getStatusDesc(pastChargePoint.getStatus()));
		}
		ChargePointInfo chargePoint = new ChargePointInfo();
		chargePoint.setChargePointId(pastChargePoint.getChargePointId());
		chargePoint.setAppCode(appCode);
		chargePoint.setChargePointCode(chargePointCode);
		
		try {
			chargePointService.doPost(chargePoint);
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("计费点提交出错", e);
		}
		return AjaxResult.failed(SysEnum.SYSTEM_ERROR.getResponeMsg());
	}
	
	private AjaxResult changeYeyouCp(ChargePointInfo charge){
		YeYouCp cp = new YeYouCp();
		cp.setAppCode(charge.getAppCode());
		cp.setChargePointCode(charge.getChargePointCode());
		cp.setChargePointAmount((int)charge.getChargePointAmount());
		cp.setChargePointName(charge.getChargePointName());
		cp.setDesc(charge.getDesc());
		try {
			
			String errorMsg =yeYouCpService.change(cp);
			if(errorMsg!=null) return AjaxResult.failed(errorMsg);
			return AjaxResult.success();
			
		} catch (Exception e) {
			log.error("页游变更计费点出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	/**
	 * 变更，重新提
	 * 变更后状态变为待审核，过去的是变为变更审核、变更驳回，将问题复杂化了，因此去掉。
	 * @param chargePointId
	 * @return
	 */
	@RequestMapping("/change")
	@ResponseBody
	@AppOperLogger
	public AjaxResult doChange(ChargePointInfo newCharge, HttpServletRequest request){
		String appCode = newCharge.getAppCode();
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		switch (em){
		case yeyougame:{
			return changeYeyouCp(newCharge);
			
		}
		case h5game: {
			return AjaxResult.failed("H5游戏不需要计费点");
		}
		default:;
		}
		AjaxResult result = validateChargeParam(newCharge, request);
		if (result != null) return result;
		ChargePointInfo pastChargePoint = chargePointService.getChargePointInfoByCode(newCharge.getChargePointCode());
		// 变更是针对已生效的计费点
		if (pastChargePoint.getStatus() != ChargePointStatusEnum.ENABLED.getStatus()){
			return AjaxResult.failed("不是已生效的计费点，不需要变更");
		}
		String errorMsg = checkChargeName(newCharge);
		if (errorMsg != null) return AjaxResult.failed(errorMsg);
		try {
			MerchantAppInfo app = appService.selectApp(newCharge.getAppCode());
			newCharge.setChargePointId(pastChargePoint.getChargePointId());
			chargePointService.changeChargePoint(app, newCharge);
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("变更计费点出错", e);
		}
		
		return AjaxResult.failedSystemError();
	}
	
	private AjaxResult validateChargeParam(ChargePointInfo chargePoint, HttpServletRequest request) {
		//参数校验不通过
		Map<String, Object> resultMap = this.valParam(request, "charge");
		Boolean isSuccess = (Boolean)resultMap.get("isSuccess");
		if(!isSuccess){
			return AjaxResult.conventMap(resultMap);
		}
		return null;
	}
	
	// 仅手游调用
	private String checkChargeName(ChargePointInfo chargePoint){
		ChargePointInfo ch = chargePointService.selectCharge(chargePoint.getAppCode(), chargePoint.getChargePointName());
		if (ch != null && (StringUtils.isEmpty(chargePoint.getChargePointCode()) || !ch.getChargePointCode().equals(chargePoint.getChargePointCode()))) {
			return "计费点名称重复";
		}
		return null;
	}
	
	// 页游计费点新增和编辑入口
	private AjaxResult saveYeyouCp(ChargePointInfo chargePoint){
		try {
			YeYouCp cp = new YeYouCp();
			cp.setAppCode(chargePoint.getAppCode());
			cp.setChargePointName(chargePoint.getChargePointName());
			cp.setChargePointAmount((int)chargePoint.getChargePointAmount());
			cp.setDesc(chargePoint.getDesc());
			cp.setStatus(chargePoint.getStatus());
			String errorMsg=null;
			if (StringUtils.isNotEmpty(chargePoint.getChargePointCode())){
				cp.setChargePointCode(chargePoint.getChargePointCode());
				errorMsg=yeYouCpService.update(cp);
			} else {
				errorMsg=yeYouCpService.add(cp);
				// 添加成功，回写计费点编码
				if (errorMsg==null) chargePoint.setChargePointCode(cp.getChargePointCode());
			}
			if(errorMsg!=null) return AjaxResult.failed(errorMsg);
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("添加和编辑页游计费点出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	/**
	 * 编辑
	 * @param chargePointId
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	@AppOperLogger
	public AjaxResult doUpdate(ChargePointInfo chargePoint, HttpServletRequest request){
		AjaxResult result = validateChargeParam(chargePoint, request);
		if (result != null) return result;
		if(chargePoint.getStatus() != null && chargePoint.getStatus() == CLICK_POST){//提交
			chargePoint.setStatus(ChargePointStatusEnum.WAIT_AUDIT.getStatus());//待审核
		}else{  //保存
			chargePoint.setStatus(ChargePointStatusEnum.DRAFT.getStatus());//草稿
		}
		String appCode = chargePoint.getAppCode();
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		switch (em){
		case yeyougame:{
			return saveYeyouCp(chargePoint);
			
		}
		case h5game: {
			return AjaxResult.failed("H5游戏不需要计费点");
		}
		default:;
		}
		ChargePointInfo pastChargePoint = chargePointService.getChargePointInfoByCode(chargePoint.getChargePointCode());
		
		if (pastChargePoint.getStatus() != ChargePointStatusEnum.DRAFT.getStatus() && pastChargePoint.getStatus() != ChargePointStatusEnum.REJECT.getStatus()){
			return AjaxResult.failed("计费点不能编辑");
		}
		String errorMsg = checkChargeName(chargePoint);
		if (errorMsg != null) return AjaxResult.failed(errorMsg);
		
		try {
			MerchantAppInfo app = merchantAppService.queryApp(chargePoint.getAppCode());
			chargePoint.setChargePointId(pastChargePoint.getChargePointId());
			chargePointService.updateChargePointInfo(app, chargePoint);
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("编辑计费点出错", e);
		}
		
		return AjaxResult.failedSystemError();
	}
	/**
	 * 批量提交
	 * @param ids
	 * @return
	 */
	@RequestMapping("/batchPost")
	@ResponseBody
	@AppOperLogger
	public AjaxResult doBatchPost(String chargePointCodes, String appCode, HttpServletRequest request){
		if (StringUtils.isEmpty(chargePointCodes)) return AjaxResult.failed("计费点编码集不能为空");
		String[] codes = chargePointCodes.split(",");
		if (codes!= null && codes.length > 20){
			log.error("批量提交提供的计费点编码过多，提交被拒绝,chargePointCodes={}", chargePointCodes);
			return AjaxResult.failed("refused!");
		}
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		switch (em){
		case yeyougame:{
			Set<String> cpSet=new HashSet<>();
			for (String cpCode: codes){
				cpSet.add(cpCode);
			}
			return submitYeyouCps(appCode, cpSet);
			
		}
		case h5game: {
			return AjaxResult.failed("H5游戏不需要计费点");
		}
		default:;
		}
		try {
			//提交后到待审核状态
			for (String code : codes) {
				ChargePointInfo ch = chargePointService.getChargePointInfoByCode(code);
				if (ch == null || !appCode.equals(ch.getAppCode())) return AjaxResult.failed("计费点不存在,chargePointCode=" + code);
				if (ch.getStatus()!=ChargePointStatusEnum.DRAFT.getStatus()){
					return AjaxResult.failed("计费点不能提交，状态为"+ChargePointStatusEnum.getStatusDesc(ch.getStatus()));
				}
			}
			for (String code : codes) {
				ChargePointInfo ch = new ChargePointInfo();
				ch.setChargePointCode(code);
				chargePointService.doPost(ch);
			}
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("批量提交计费点出错", e);
		}
	
		return AjaxResult.failedSystemError();
	}
	/**
	 * 查看
	 * @param chargePointId
	 * @return
	 */
	@RequestMapping("/toview")
	@ResponseBody
	public AjaxResult toView(String chargePointCode, String appCode, HttpServletRequest request){
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		Object obj=null;
		switch (em){
		case yeyougame:{
			obj=yeYouCpService.select(chargePointCode);
			break;
		}
		case h5game: {
			return AjaxResult.failed("H5游戏不需要计费点");
		}
		case mobilegame:{
			obj = chargePointService.getChargePointInfoByCode(chargePointCode);
			break;
		}
		default: obj=new Object();
		}
		
		return AjaxResult.success(obj);
			
	}
	
	/**
	 * 查看计费点过去最新的历史记录
	 * 变更会将计费点设置为失效，新增一条记录。失效的记录就会是返回结果
	 * 如果有多条失效记录，返回最新的一条记录
	 * @param appCode
	 * @param chargePointCode
	 * @return
	 */
	@RequestMapping("/viewPrevious")
	@ResponseBody
	public AjaxResult viewPreviousChargePoint(String appCode, String chargePointCode, HttpServletRequest request){
		if (!isLegalMerchant(appCode, request)) return null;
		ChargePointInfo chargePointInfo = null;
		try {
			chargePointInfo = 	chargePointService.getRencentPreviousChargePoint(appCode, chargePointCode);
		} catch (Exception e) {
			log.error("查询过去的计费点信息出错", e);
			return AjaxResult.failed(SysEnum.SYSTEM_ERROR.getResponeMsg());
		}
		if (chargePointInfo != null) {
			Map<String, Object> dataMap = new HashMap<String, Object>(4);
			dataMap.put("chargePoint", chargePointInfo);
			return AjaxResult.success(dataMap);
		} else {
			return AjaxResult.failed("没有查询到数据");
		}
		
	}
	
	private static final String amountRegex = "(^[1-9][0-9]{0,9}$)|(^[1-9][0-9]{0,9}\\.[0-9]{1,2}$)|(^0\\.[0-9]{1,2}$)";
	private static Pattern amountPattern = Pattern.compile(amountRegex);
	
	private static boolean checkAmount(String amount){
		if ("0.0".equals(amount) || "0.00".equals(amount)) return false;
		Matcher m = amountPattern.matcher(amount);
		return m.matches();
	}
	
	@RequestMapping("/isAllAvailable")
	@ResponseBody
	public AjaxResult isAllChargePointAvailable(String appCode){
		boolean isAvail = chargePointService.isAllAvailable(appCode);
		if (isAvail) {
			return AjaxResult.success();
		}
		return AjaxResult.failed("存在计费点还没有审核完成");
	}
	
	// 校验计费点是否为应用下面的
	// 计费点页面请求权限控制过滤器使用到
	public boolean checkCp(String appCode, String chargePointCode){
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		switch (em){
		case h5game:{
			return false; // h5没有计费点
		}
		case yeyougame: {
			YeYouCp cp = yeYouCpService.select(chargePointCode);
			if (cp!=null && appCode.equals(cp.getAppCode())){
				return true;
			}
		}
		case mobilegame:{
			ChargePointInfo cp =chargePointService.getChargePointInfoByCode(chargePointCode);
			if (cp!=null && appCode.equals(cp.getAppCode())){
				return true;
			}
		}
		default:;
		}
		return false;
	}
}
