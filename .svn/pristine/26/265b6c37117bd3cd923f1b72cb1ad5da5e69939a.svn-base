package com.dome.sdkserver.controller.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.constants.channel.PkgStatusEnum;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.metadata.entity.channel.AppPkg;
import com.dome.sdkserver.metadata.entity.channel.BqApp;
import com.dome.sdkserver.metadata.entity.channel.Channel;
import com.dome.sdkserver.service.channel.ChannelAuditService;
import com.dome.sdkserver.service.channel.ChannelPkgAuditService;
import com.dome.sdkserver.view.AjaxResult;

@Controller
@ResponseBody
@RequestMapping("/channel/pkgdl")
public class ChannelPkgAuditController extends BaseController{

	@Autowired
	private ChannelPkgAuditService service;
	
	@Autowired
	private ChannelAuditService channelAuditService;
	@RequestMapping("/list")
	public AjaxResult list(AppPkgQueryParam pkg, Paginator paginator, HttpServletRequest request){
		String channelCode = pkg.getChannelCode();
		long channelId =0l;
		if (StringUtils.isNotBlank(channelCode)){
			Channel channel = channelAuditService.selectByCode(channelCode, false);
			if (channel==null){
				return AjaxResult.failed("渠道信息不存在，channelCode=" +channelCode);
			}
			channelId = channel.getId();
		}
		
		
		String channelName = pkg.getName();
		try{
			int totalCount=service.selectAppPkgCount(channelId, channelName);
			List<AppPkgVo> voList=new ArrayList<>();
			if (totalCount >0){
				paginator=Paginator.handlePage(paginator, totalCount, request);
				List<AppPkg> list=service.selectAppPkgList(channelId, channelName, paginator);
				for (AppPkg ap : list){
					AppPkgVo vo = new AppPkgVo(ap);
					voList.add(vo);
				}
			}
			Map<String, Object> dataMap = new HashMap<String, Object>(4);
			dataMap.put("totalCount", totalCount);
			dataMap.put("list", voList);
			return AjaxResult.success(dataMap);
		} catch (Exception e) {
			log.error("查询APP渠道包打包列表出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	@RequestMapping("/pkg")
	public AjaxResult doPkg(AppPkgQueryParam pkg, HttpServletRequest request){
		if (pkg==null || pkg.getPid()==0){
			return AjaxResult.failed("illegal pid arguments");
		}
		long pid = pkg.getPid();
		try {
			AjaxResult result = auditCheck(pid);
			if (result!=null){
				return result;
			}
			String errorMsg = service.pkg(pid);
			if (errorMsg==null){
				return AjaxResult.success();
			}
			return AjaxResult.failed(errorMsg);
		} catch (Exception e) {
			log.error("APP渠道申请打包出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	@RequestMapping("/batchpkg")
	public AjaxResult doBatchPkg(String pids, HttpServletRequest request){
		try {
			if (StringUtils.isEmpty(pids)) return AjaxResult.failed("pids不能为空");
			String[] pidArr = pids.split(",");
			List<Long> pidList = new ArrayList<>(pidArr.length);
			for (String pid :pidArr){
				if (StringUtils.isEmpty(pid) || !Constants.PATTERN_NUM.matcher(pid).matches()) continue;
				long id = Long.parseLong(pid);
				AjaxResult result = auditCheck(id);
				if (result!=null){
					return result;
				}
				pidList.add(id);
			}
			String errorMsg = service.batchPkg(pidList);
			if (errorMsg==null){
				return AjaxResult.success();
			}
			return AjaxResult.failed(errorMsg);
		} catch (Exception e) {
			log.error("APP渠道申请打包出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	// 检查应用包体当前的状态是否为未打包，否则提示“操作被拒绝”
	private AjaxResult auditCheck(long pid){
		
		AppPkg pkg2=service.selectAppPkg(pid);
		if (pkg2==null || pkg2.getStatus() != PkgStatusEnum.未打包.getStatus()){
			return AjaxResult.failed("pid=" + pid + "不允许打包操作，当前状态：" + 
					(pkg2==null ? null: PkgStatusEnum.getStatusDesc(pkg2.getStatus())));
		}
		return null;
	}
	
	@RequestMapping("/reject")
	public AjaxResult reject(AppPkgQueryParam pkg, HttpServletRequest request){
		
		if (pkg==null || pkg.getPid()==0){
			return AjaxResult.failed("illegal pid arguments");
		}
		long pid = pkg.getPid();
		AjaxResult result = auditCheck(pid);
		if (result!=null){
			return result;
		}
		String remark = pkg.getRemark();
		if (StringUtils.isEmpty(remark)){
			return AjaxResult.failed("驳回理由不能为空");
		}
		try {
			AppPkg ap = new AppPkg();
			ap.setPid(pid);
			ap.setStatus(PkgStatusEnum.驳回.getStatus());
			ap.setRemark(remark);
			int line =service.updateAppPkg(ap);
			if (line==1){
				return AjaxResult.success();
			}
			return AjaxResult.failed();
		} catch (Exception e) {
			log.error("APP渠道打包驳回出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 渠道包app的appid，目前为冰趣
	 */
	@Value("${channel.apppkg.appid}")
	private long appId;
	
	@Value("${channel.apppkg.appName}")
	private String appName;
	@RequestMapping("/receAppInfo")
	public AjaxResult receiveAppInfo(BqApp app, HttpServletRequest request){
		if (app==null){
			return AjaxResult.failed("BqApp null");
		}
		log.info("传递的参数：" + JSON.toJSONString(app));
		// appId取一个固定值，标识冰趣APP
		app.setAppId(this.appId);
		try {
			String errorMsg =service.receBqApp(app);
			if (errorMsg==null){
				return AjaxResult.success();
			}
			return AjaxResult.failed();
		} catch (Exception e) {
			log.error("接收冰趣官网上传APP出错", e);
		}
		return AjaxResult.failedSystemError();
		}
}
