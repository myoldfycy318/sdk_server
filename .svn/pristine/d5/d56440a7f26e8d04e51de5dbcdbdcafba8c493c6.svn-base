package com.dome.sdkserver.controller.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.constants.channel.PkgStatusEnum;
import com.dome.sdkserver.metadata.entity.channel.AppPkg;
import com.dome.sdkserver.metadata.entity.channel.Game;
import com.dome.sdkserver.service.channel.ChannelPkgService;
import com.dome.sdkserver.service.channel.ChannelService;
import com.dome.sdkserver.view.AjaxResult;

@Controller
@ResponseBody
@RequestMapping("/channel/pkgdl")
public class ChannelPkgController extends BaseController{

	@Autowired
	private ChannelPkgService service;
	
	@Autowired
	private ChannelService channelService;
	
	/**
	 * 查询渠道包申请界面游戏列表
	 * @param paginator
	 * @param request
	 * @return
	 */
	@RequestMapping("/gamelist")
	public AjaxResult queryGameList(String gameName, Paginator paginator, HttpServletRequest request){
		long channelId= this.getCurrChannelId(request);
		if (StringUtils.isBlank(gameName)) gameName=null;
		try {
			int totalCount =service.selectGameCount(gameName,channelId);
			List<Game> list=null;
			if (totalCount >0){
				paginator = Paginator.handlePage(paginator, totalCount, request);
				
				list=service.selectGameList(gameName, channelId, paginator);
			} else {
				list=new ArrayList<>();
			}
			Map<String, Object> dataMap = new HashMap<String, Object>(4);
			dataMap.put("totalCount", totalCount);
			
			dataMap.put("list", list);
			return AjaxResult.success(dataMap);
		} catch (Exception e) {
			log.error("query game list error", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 查询渠道包下载界面游戏列表
	 * 处于正在打包的游戏不展示。只展示status为已打包的
	 * @param paginator
	 * @param request
	 * @return
	 */
	@RequestMapping("/gamelist2")
	public AjaxResult queryGameList2(String gameName, Paginator paginator, HttpServletRequest request){
		long channelId= this.getCurrChannelId(request);
		if (StringUtils.isBlank(gameName)) gameName=null;
		try {
			int totalCount =service.selectDlGameCount(gameName,channelId);
			List<Game> list=null;
			if (totalCount >0){
				paginator = Paginator.handlePage(paginator, totalCount, request);
				
				list=service.selectDlGameList(gameName, channelId, paginator);
			} else {
				list=new ArrayList<>();
			}
			Map<String, Object> dataMap = new HashMap<String, Object>(4);
			dataMap.put("totalCount", totalCount);
			
			dataMap.put("list", list);
			return AjaxResult.success(dataMap);
		} catch (Exception e) {
			log.error("query download game list error", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * app渠道包申请
	 * @param request
	 * @return
	 */
	@RequestMapping("/appapply")
	public AjaxResult appApply (HttpServletRequest request){
		try {
			long channelId= this.getCurrChannelId(request);
			AppPkg pkg = new AppPkg();
			pkg.setChannelId(channelId);
			String errorMsg =service.addAppPkg(pkg);
			if (errorMsg!=null) return AjaxResult.failed(errorMsg);
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("APP渠道包申请打包出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 查询app审批结果
	 * @param request
	 * @return
	 */
	@RequestMapping("/applyresult")
	public AjaxResult queryApplyResult(HttpServletRequest request){
		try {
			long channelId= this.getCurrChannelId(request);
			AppPkg pkg = service.selectAppPkg(channelId);
			if (pkg==null){
				pkg = new AppPkg();
				pkg.setStatus(PkgStatusEnum.首次申请打包.getStatus());
			}
			int status=pkg.getStatus();
			String message =null;
			switch(status){
			// 首次申请打包(0), 已打包(1),未打包(2), 正在打包(3), 打包失败(4), 驳回(5), 包有更新(6);
			case 2: {
				message ="提交的打包申请工作人员还没有审批";
				break;
			}
			case 3: {
				message ="系统正在进行打包...";
				break;
			}
			default:;
			}
			pkg.setMessage(message);
			return AjaxResult.success(pkg);
		} catch (Exception e) {
			log.error("query app channel apply result error", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 渠道包游戏申请打包
	 * @param gameIds
	 * @param request
	 * @return
	 */
	@RequestMapping("/gamePkg")
	public AjaxResult applyGamePkg(@RequestParam("gameIds[]")long[] gameIds, HttpServletRequest request){
		try {
			if (gameIds==null || gameIds.length==0) return AjaxResult.failed("游戏ID不能为空");
			if (gameIds.length>50) return AjaxResult.failed("选择的游戏超过了50个");
			long channelId= this.getCurrChannelId(request);
			
			String errorMsg = service.gamePkg(channelId, gameIds);
			if (errorMsg!=null){
				return AjaxResult.failed(errorMsg);
			}
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("渠道包游戏申请打包出错", e);
		}
		return AjaxResult.failedSystemError();
	}
}
