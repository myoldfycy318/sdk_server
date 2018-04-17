package com.dome.sdkserver.aop;


import static com.dome.sdkserver.constants.AppStatusEnum.access_finish;
import static com.dome.sdkserver.constants.AppStatusEnum.online_adjust;
import static com.dome.sdkserver.constants.AppStatusEnum.wait_shelf;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.dome.sdkserver.bo.ChargePointInfo;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.bq.util.IPUtil;
import com.dome.sdkserver.constants.AppOperEnum;
import com.dome.sdkserver.constants.AppStatusEnum;
import com.dome.sdkserver.constants.ChargePointStatusEnum;
import com.dome.sdkserver.constants.Constant;
import com.dome.sdkserver.constants.Constant.ApprovalMenuEumu;
import com.dome.sdkserver.constants.Constant.NotifyNodeEnum;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;
import com.dome.sdkserver.metadata.dao.mapper.AppOperRecordMapper;
import com.dome.sdkserver.metadata.dao.mapper.ChargePointMapper;
import com.dome.sdkserver.metadata.dao.mapper.MerchantAppMapper;
import com.dome.sdkserver.metadata.dao.mapper.h5.H5GameMapper;
import com.dome.sdkserver.metadata.dao.mapper.yeyou.YeyouGameMapper;
import com.dome.sdkserver.metadata.entity.AppOperRecord;
import com.dome.sdkserver.metadata.entity.h5.H5Game;
import com.dome.sdkserver.metadata.entity.yeyou.YeyouGame;
import com.dome.sdkserver.service.AppService;
import com.dome.sdkserver.service.notify.NotifyService;
import com.dome.sdkserver.util.DomeSdkUtils;
import com.dome.sdkserver.util.RedisUtil;
import com.dome.sdkserver.util.business.GameUtils;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.store.user.BaUser;
@Component
@Aspect
public class AppOperRecordAspect {

	private static final Logger log = LoggerFactory.getLogger(AppOperRecordAspect.class);
	
	@Resource
	private AppOperRecordMapper record;
	
	@Resource
	private NotifyService notifyServiceImpl;
	
	@Autowired
    private MerchantAppMapper merchantAppMapper;
	
	@Autowired
    private H5GameMapper<H5Game> h5GameMapper;
	
	@Autowired
    private YeyouGameMapper<YeyouGame> yeyouGameMapper;
	@Resource
	private ChargePointMapper chargePointMapper;
	
	@Autowired
    private RedisUtil redisUtil;
	
	
	private void recordAppInfo(MerchantAppInfo merchantAppInfo, AppOperRecord record,
			HttpServletRequest request){
		// merchantAppInfo构造的一个临时应用对象，最终会传递给通知业务流程
		int pastStatus = (int)request.getAttribute("pastStatus");
		record.setStatus(pastStatus); // 保存的是应用的上一个状态
		int status = Integer.parseInt(request.getParameter("status"));
		merchantAppInfo.setPastStatus(pastStatus);
		merchantAppInfo.setStatus(status);
		String remark=request.getParameter("remark");
		if (StringUtils.isNotEmpty(remark)){
			record.setRemark(remark);
		}
		String operDesc=AppOperEnum.getStatusDesc(Integer.toString(pastStatus) + Integer.toString(status));
		/**
		 * 只处理几种特殊情况，譬如已接入直接到待上架状态
		 * 其他情况根据上一个状态拼上当前状态，获取操作描述
	
		 * 不需要处理的情况
		 * 1、未接入 接入驳回  ->待接入   待接入 ->已接入、接入驳回
		 * 2、
		 * 需要处理的情况
		 * 1、页游上架申请  已接入->待上架  h5：线上联调环境申请  已接入->线上联调中
		 * 2、手游中应用类型不是游戏的，流程较特殊
		 */
		GameTypeEnum em = GameUtils.analyseGameType(merchantAppInfo.getAppCode());
		switch (em){
		case yeyougame:{
			if (status==wait_shelf.getStatus()
					&& pastStatus==access_finish.getStatus()){
				operDesc="上架申请";
			}
			break;
		}
		case h5game: {
			if (status==online_adjust.getStatus()
					&& pastStatus==access_finish.getStatus()){
				operDesc="线上环境联调申请";
			}
			break;
		}
		case mobilegame:{
			MerchantAppInfo app = merchantAppMapper.queryApp(merchantAppInfo.getAppCode());
			record.setAppId(app.getAppId());
			
			break;
		}
		default:;
		}
		
		if (operDesc!=null) record.setOperDesc(operDesc);
	}
	@Autowired
	private AppService appService;
	private void recordCpInfo(String appCode, AppOperRecord record,
			HttpServletRequest request){
		GameTypeEnum em = GameUtils.analyseGameType(appCode);
		MerchantAppInfo app = appService.selectApp(appCode);
		record.setStatus(app.getStatus());
		switch (em){
		case yeyougame:
		case h5game: {
			return;
		}
		case mobilegame:{
			record.setAppId(app.getAppId());
			
			break;
		}
		default:;
		}
	}
	
	@Pointcut(value = "@annotation(com.dome.sdkserver.aop.AppOperLogger)")
    private void pointcut() {
    }
	/**
	 * 开放平台商户、应用和计费点操作注入。
	 * 返回值AjaxResult 中responseCode=1000
	 * 渠道暂不处理
	 * @param point
	 * @throws Throwable 
	 */
	@Around(value="pointcut()")
	public Object record(ProceedingJoinPoint jp) throws Throwable {
		Object returnObj=null;
		try {
			returnObj = jp.proceed();
			// 处理失败的请求不记录
			if (!(returnObj instanceof AjaxResult) || ((AjaxResult)returnObj).getResponseCode()!=1000){
				return returnObj;
			}
		} catch (Throwable e) {
			log.error("开放平台记录操作日志出错", e);
			throw e;
		}
		Object[] params = jp.getArgs();
		AppOperRecord r = new AppOperRecord();
		MerchantAppInfo app = new MerchantAppInfo();
		
		//log.info("开始记录业务日志，参数：{}", Arrays.toString(params));
		for (int i = 0, len = params.length; i < len; i++) {
			Object param = params[i];
			// 要求每个方法必须有HttpServletRequest对象
			if (param instanceof HttpServletRequest){
				HttpServletRequest httpRequest = (HttpServletRequest)param;
				// 判断是请求来源
				String domainName =httpRequest.getServerName();
				
				String ip = IPUtil.getIpAddr(httpRequest);
				if (domainName.equals("open.domestore.cn")){
					//open
					recordOpenUser(r, httpRequest);
				} else if (domainName.equals("openba.domestore.cn")){
					//openba
					recordOpenbaUser(r, httpRequest);
				}
				String requestUri = httpRequest.getRequestURI();
				String appCode =httpRequest.getParameter("appCode");
				if (StringUtils.isNotEmpty(appCode)) {
					r.setAppCode(appCode);
					app.setAppCode(appCode);
				}
				String operDesc=null;
				if (requestUri.startsWith("/merchant/")){// 商户
					if (requestUri.equals("/merchant/register")){ // 商户信息提交
						Constant.NotifyNodeEnum em = NotifyNodeEnum.MERCHANT_APPLY_SUBMIT;
						notifyServiceImpl.partnerApplyNotify(em, Constant.ApprovalMenuEumu.DEVELOPER_APPROVAL);
						return returnObj;
					}
				} else if (requestUri.startsWith("/merchantapp/")){// 应用
					if (requestUri.equals("/merchantapp/save")){ // 应用提交
						// 根据有没有应用编码，有为新增；没有为编辑
						if (StringUtils.isEmpty(appCode)){
							operDesc="新增应用";
						}else {
							operDesc="编辑应用";
						}
						// 新增应用时获取appId和appCode
						int pastStatus = (int)httpRequest.getAttribute("pastStatus");
						r.setStatus(pastStatus); // 保存的是应用的上一个状态
					} else if (requestUri.equals("/merchantapp/dodel")){ // 应用删除
						operDesc="删除应用";
						int pastStatus = (int)httpRequest.getAttribute("pastStatus");
						r.setStatus(pastStatus); // 保存的是应用的上一个状态
					} else if (requestUri.equals("/merchantapp/updateState")){ // 商户信息提交
						recordAppInfo(app, r, httpRequest);
						appChangeNotify(app);
					}
					if (operDesc!=null) r.setOperDesc(operDesc);
					
				} else if (requestUri.startsWith("/chargepoint/")){// 计费点前台和后台接口前缀相同
					boolean noneedNotify=false;
					ChargePointInfo cp = new ChargePointInfo();
					if (requestUri.equals("/chargepoint/add") ||requestUri.equals("/chargepoint/update")){
						// 区分提交和保存 提交时传10，保存时可能没有传status
						if ("10".equals(httpRequest.getParameter("status"))){
							operDesc=requestUri.equals("/chargepoint/update")?"计费点重新提交":"计费点提交";
							cp.setStatus(ChargePointStatusEnum.WAIT_AUDIT.getStatus());
						} else {
							operDesc=requestUri.equals("/chargepoint/update")?"计费点编辑":"计费点保存";
							noneedNotify=true;
						}
					}else if (requestUri.equals("/chargepoint/delete")){
						cp.setStatus(-1); // -1标识需要判断是否计费点都审批通过，给合作伙伴发邮件
						operDesc="计费点删除";
						noneedNotify=true;
					}else if (requestUri.equals("/chargepoint/post")){
						// 记录的是计费点上一个状态
						cp.setStatus(ChargePointStatusEnum.WAIT_AUDIT.getStatus());
						operDesc="计费点提交";
						
					}else if (requestUri.equals("/chargepoint/batchPost")){
						cp.setStatus(ChargePointStatusEnum.WAIT_AUDIT.getStatus());
						operDesc="计费点批量提交";
						
					}else if(requestUri.equals("/chargepoint/change")){
						cp.setStatus(ChargePointStatusEnum.ENABLED.getStatus());
						operDesc="计费点变更";
					}else if(requestUri.equals("/chargepoint/doreject")){
						//cp.setStatus(-1);
						operDesc="计费点驳回";
					}else if(requestUri.equals("/chargepoint/dopass")){
						cp.setStatus(-1);
						operDesc="计费点审批通过";
					}else if(requestUri.equals("/chargepoint/doBatchPass")){
						cp.setStatus(-1);
						operDesc="计费点批量审批通过";
					}else if(requestUri.equals("/chargepoint/batchReject")){
						//cp.setStatus(-1);
						operDesc="计费点批量驳回";
					}
					String chargePointCode=httpRequest.getParameter("chargePointCode");
					String chargePointCodes=httpRequest.getParameter("chargePointCodes");
					if (StringUtils.isNotEmpty(chargePointCode)&&StringUtils.isEmpty(r.getRemark())){
						r.setRemark(chargePointCode); 
					} else if (StringUtils.isNotEmpty(chargePointCodes)){
						
						r.setRemark(chargePointCodes); // 批量审批备注为计费点编码集合
					}
					String remark=httpRequest.getParameter("remark");
					if (StringUtils.isNotEmpty(remark)){
						r.setRemark(r.getRemark()+";"+remark);
					}
					// 记录计费点状态
					recordCpInfo(appCode, r, httpRequest);
					r.setOperDesc(operDesc);
					if (!noneedNotify)chargePointChangeNotify(app, cp);
				} else if (requestUri.startsWith("/merchantappaudit/")){// 应用后台审批
					if (requestUri.equals("/merchantappaudit/updateState")){ // 应用提交
						recordAppInfo(app, r, httpRequest);
						appChangeNotify(app);
//					} else if (requestUri.equals("/merchantappaudit/modifyUrl")){ // 修改回调地址业务日志不在这里处理
//						
					}
					
				}
			} else if (param instanceof MerchantAppInfo){
				// 新增应用时获取appCode或appId
				MerchantAppInfo appInfo = (MerchantAppInfo)param;
				r.setAppCode(appInfo.getAppCode());
				final Integer appId = appInfo.getAppId();
				if (appId!=null && appId!=0){
					r.setAppId(appId);
				}
			}else if (param instanceof ChargePointInfo){
				// 新增计费点时获取chargePointCode
				ChargePointInfo cpInfo = (ChargePointInfo)param;
				if (StringUtils.isNotEmpty(cpInfo.getChargePointCode())){
					r.setRemark(cpInfo.getChargePointCode());
				}
			}
		}
		GameTypeEnum em = GameUtils.analyseGameType(r.getAppCode());
		if (em==GameTypeEnum.mobilegame){
			record.insert(r);
		} else {
			record.insertGame(r);
		}
		return returnObj;
	}

	// 计费点审批业务通知
	private void chargePointChangeNotify(MerchantAppInfo app, ChargePointInfo cp) {
//		if ("计费点保存".equals(app.getStatusDesc()) || "计费点删除".equals(app.getStatusDesc())) return;
		// 计费点状态取的是上一个状态
		int cpStatus = cp.getStatus() == null ? 0 : cp.getStatus();
		Constant.NotifyNodeEnum em = null;
		if (cpStatus == ChargePointStatusEnum.ENABLED.getStatus()) { // 已生效，对应是变更
			em = NotifyNodeEnum.CHARGING_POINT_CHANGE;
		} else if (cpStatus == ChargePointStatusEnum.WAIT_AUDIT.getStatus()) {
			em = NotifyNodeEnum.ADD_CHARGING_POINT;
		}
		if (em != null){
			notifyServiceImpl.partnerApplyNotify(em, ApprovalMenuEumu.CHARGING_POINT_APPROVAL);
			return;
		}
		
		// 判断是否所有的计费点都已审核完成，不存在已驳回和待审核的
		List<ChargePointInfo> chargeList = chargePointMapper.getWaitAuditCharges(cp.getAppCode());
		if (CollectionUtils.isEmpty(chargeList)) {
			if (cpStatus == -1) { 
				em = NotifyNodeEnum.CHARGING_POINT_EFFECT;
				notifyServiceImpl.approveResultNotify(em, app);
			}
		}
	}

	// 应用变更业务通知
	private void appChangeNotify(MerchantAppInfo app){
		int pastStatus = app.getPastStatus();
		int status = app.getStatus();
		Constant.NotifyNodeEnum em = null;
		// 运营审批结果通知商家
		if (pastStatus == AppStatusEnum.wait_access.getStatus()) {
			// 非游戏接入通过后直接到待上架
			if (AppStatusEnum.inStatus(status, AppStatusEnum.access_finish, AppStatusEnum.wait_shelf)) {
				em = NotifyNodeEnum.ACCESS_SUCCESS;
			} else if (status == AppStatusEnum.deny_access.getStatus()) {
				em = NotifyNodeEnum.ACCESS_DENIED;
			}
		} else if (pastStatus == AppStatusEnum.test_adjust.getStatus()) {
			if (status == AppStatusEnum.test_adjust_finish.getStatus()) {
				em = NotifyNodeEnum.TEST_ADJUST_FINISH;
			} else if (status == AppStatusEnum.access_finish.getStatus()) {
				// 测试联调驳回
				em = NotifyNodeEnum.adjust_deny;
			}
		} else if (pastStatus == AppStatusEnum.online_adjust.getStatus()) {
			if (status == AppStatusEnum.online_adjust_finish.getStatus()) {
				em = NotifyNodeEnum.ONLINE_ADJUST_FINISH;
			} else if (status == AppStatusEnum.access_finish.getStatus()) {
				// 线上联调驳回
				em = NotifyNodeEnum.adjust_deny;
			}
		} else if (pastStatus == AppStatusEnum.test.getStatus()) {
			if (status == AppStatusEnum.test_finish.getStatus()) {
				em = NotifyNodeEnum.TEST_SUCCESS;
			} else if (status == AppStatusEnum.deny_test.getStatus()) {
				em = NotifyNodeEnum.TEST_REJECT;
			}
		} else if (pastStatus == AppStatusEnum.wait_shelf.getStatus()) {
			if (status == AppStatusEnum.shelf_finish.getStatus()) {
				em = NotifyNodeEnum.SHELF_SUCCESS;
			}
		}
		if (em != null){
			notifyServiceImpl.approveResultNotify(em, app);
			return;
		}
		// 商家申请通知运营
		Constant.ApprovalMenuEumu approvalMenuEumu = null;
		if (AppStatusEnum.inStatus(pastStatus, AppStatusEnum.unaccess, AppStatusEnum.deny_access)) {
			if (status == AppStatusEnum.wait_access.getStatus()) {
				em = NotifyNodeEnum.MERCHANT_APPLY_ACCESS;
				approvalMenuEumu=ApprovalMenuEumu.APPLICATION_APPROVAL;
			}
		} else if (AppStatusEnum.inStatus(pastStatus, AppStatusEnum.access_finish, AppStatusEnum.charge_changed, AppStatusEnum.pkg_changed)) {
			if (status == AppStatusEnum.test_adjust.getStatus()) {
				em = NotifyNodeEnum.TEST_ADJUST_APPLY;
				approvalMenuEumu=ApprovalMenuEumu.TEST_ADJUST_APPROVAL;
			}
		} else if (pastStatus == AppStatusEnum.test_adjust_finish.getStatus()
				||pastStatus == AppStatusEnum.access_finish.getStatus()) {// h5从已接入到线上环境联调中
			if (status == AppStatusEnum.online_adjust.getStatus()) {
				em = NotifyNodeEnum.ONLINE_ADJUST_APPLY;
				approvalMenuEumu=ApprovalMenuEumu.ONLINE_ADJUST_APPROVAL;
			}
		} else if (AppStatusEnum.inStatus(pastStatus, AppStatusEnum.online_adjust_finish, AppStatusEnum.deny_test)) {
			if (status == AppStatusEnum.test.getStatus()) {
				em = NotifyNodeEnum.TEST_APPLY;
				approvalMenuEumu=ApprovalMenuEumu.TEST_APPROVAL;
			}
		} else if (pastStatus == AppStatusEnum.test_finish.getStatus() 
				||pastStatus == AppStatusEnum.access_finish.getStatus()) { // 页游从已接入直接到待上架
			if (status == AppStatusEnum.wait_shelf.getStatus()) {
				em = NotifyNodeEnum.SHELF_APPLY;
				approvalMenuEumu=ApprovalMenuEumu.PUTAWAY_APPROVAL;
			}
		}
		if (em != null && approvalMenuEumu != null){
			notifyServiceImpl.partnerApplyNotify(em, approvalMenuEumu);
			
		}
	}
	
	public AppOperRecordMapper getRecord() {
		return record;
	}

	public void setRecord(AppOperRecordMapper record) {
		this.record = record;
	}
	

	private void recordOpenbaUser(AppOperRecord record,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("USER");
		if (obj !=null && obj instanceof BaUser) {
			BaUser user = (BaUser) obj;
			record.setOperUserId(user.getId());
			record.setOperUser(user.getUsername());
		}
	}

	private void recordOpenUser(AppOperRecord record,
			HttpServletRequest request) {
		User user= DomeSdkUtils.getLoginUserStatistic(request);
		record.setOperUserId(Integer.parseInt(user.getUserId()));
		record.setOperUser(user.getLoginName());
			
	}	
}
