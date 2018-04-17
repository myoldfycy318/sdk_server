package com.dome.sdkserver.controller.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.constants.channel.PromoteTypeStatusEnum;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.metadata.entity.channel.Game;
import com.dome.sdkserver.metadata.entity.channel.PromoteType;
import com.dome.sdkserver.metadata.entity.channel.TypeVo;
import com.dome.sdkserver.service.channel.PromoteTypeService;
import com.dome.sdkserver.util.PaginatorUtils;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.sdkserver.view.RequestParamHandler;

/**
 * 推广分类
 * @author lilongwei
 *
 */
@ResponseBody
@Controller
@RequestMapping("/channel/typeconfig")
public class PromoteTypeController extends BaseController{

	@Autowired
	private PromoteTypeService service;
	@RequestMapping("/list")
	public AjaxResult list(String typeName, Paginator paginator, HttpServletRequest request){
		//int statusVal = RequestParamHandler.getStatus(...);
		try {
			int totalCount =service.selectCount(typeName);
			List<PromoteType> list=null;
			if (totalCount >0){
				paginator=Paginator.handlePage(paginator, totalCount, request);
				list=service.selectList(typeName, paginator);
			} else {
				list=new ArrayList<>();
			}
			Map<String, Object> dataMap = new HashMap<String, Object>(4);
			dataMap.put("totalCount", totalCount);
			List<TypeVo> voList = new ArrayList<>();
			for (PromoteType t:list){
				TypeVo vo =new TypeVo(t);
				voList.add(vo);
			}
			dataMap.put("list", voList);
			return AjaxResult.success(dataMap);
		} catch (Exception e) {
			log.error("query promote type list error", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	@RequestMapping("/save")
	public AjaxResult save(TypeParam param, String gameIds, HttpServletRequest request){
		if (param==null) return AjaxResult.failed("missing parameter");
		long typeId=param.getTypeId();
		int status=RequestParamHandler.getStatus(param.getStatus());
		PromoteType pt = new PromoteType();
		pt.setTypeId(typeId);
		pt.setStatus(status);
		pt.setTypeName(param.getTypeName());
		AjaxResult result = validatePtParam(pt, gameIds);
		if (result !=null) return result;
		String[] ids = gameIds.split(",");
		if (ids==null ||ids.length==0) return AjaxResult.failed("关联游戏不能为空，gameIds="+gameIds);
		List<Long> gameIdList = new ArrayList<>(ids.length);
		for (String id:ids){
			if (Constants.PATTERN_NUM.matcher(id).matches()){
				gameIdList.add(Long.parseLong(id));
			} else {
				return AjaxResult.failed("illegal gameId");
			}
			
		}
		if (CollectionUtils.isEmpty(gameIdList)) return AjaxResult.failed("关联游戏不能为空，gameIds="+gameIds);
		pt.setGameIdList(gameIdList);
		try {
			// 判断分类名称是否重复
			if (checkTypeName(typeId, pt.getTypeName())){
				return AjaxResult.failed("渠道分类名称已被使用");
			}
			if (typeId==0){ // add
				
				service.add(pt);
			} else {// edit
				PromoteType promoteType = service.select(typeId);
				if (promoteType==null) return AjaxResult.failed("渠道分类不存在");
				PromoteType type = new PromoteType();
				type.setTypeId(typeId);
				if (!pt.getTypeName().equals(promoteType.getTypeName())){
					type.setTypeName(pt.getTypeName());
				}
				if (pt.getStatus() !=promoteType.getStatus()){
					type.setStatus(pt.getStatus());
				}
				type.setGameIdList(gameIdList);
				service.update(type);
			}
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("save promote type error", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	// 校验分类名称是否已被使用
	private boolean checkTypeName(long typeId, String typeName){
		boolean hasUseTypeName=false;
		Paginator paginator = new Paginator();
		paginator.setStart(-1);
		List<PromoteType> list =service.selectList(typeName, paginator);
//		while (!CollectionUtils.isEmpty(list)){
			for (PromoteType t:list){
				if (t.getTypeId()!=typeId && t.getTypeName().equals(typeName)){
					hasUseTypeName=true;
					break;
				}
			}
//			if (list.size()==paginator.getPageSize()){
//				paginator.setStart(paginator.getStart() + paginator.getPageSize());
//				list=service.selectList(typeName, paginator);
//			}
//		}
		return hasUseTypeName;
	}
	
	private AjaxResult validatePtParam(PromoteType pt, String gameIds){
		if (pt==null || StringUtils.isEmpty(pt.getTypeName())){
			return AjaxResult.failed("渠道分类名称不能为空");
		}
		if (!PromoteTypeStatusEnum.isLeggal(pt.getStatus())){
			return AjaxResult.failed("illegal status value, status="+pt.getStatus());
		}
		if (StringUtils.isEmpty(gameIds)){
			return AjaxResult.failed("关联游戏不能为空");
		}
		return null;
	}
	
	@RequestMapping("/view")
	public AjaxResult view(TypeVo typeVo, HttpServletRequest request){
		long typeId =typeVo.getTypeId();
		if (typeId==0){
			return AjaxResult.failed("typeId不能为空");
		}
		try {
			PromoteType pt =service.select(typeId);
			if (pt==null){
				return AjaxResult.failed("没有查询到");
			}
			return AjaxResult.success(new TypeVo(pt));
		} catch (Exception e) {
			log.error("view promote type error", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	@RequestMapping("/delete")
	public AjaxResult delete(TypeVo typeVo, HttpServletRequest request){
		long typeId =typeVo.getTypeId();
		if (typeId==0){
			return AjaxResult.failed("typeId不能为空");
		}
		try {
			PromoteType pt =service.select(typeId);
			if (pt==null){
				return AjaxResult.failed("没有查询到");
			}
			int line=service.delete(pt.getTypeId());
			if (line==1){
				return AjaxResult.success();
			}
			return AjaxResult.failed();
		} catch (Exception e) {
			log.error("delete promote type error", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	@RequestMapping("/gamelist")
	public AjaxResult gameList(String gameName, Paginator paginator, HttpServletRequest request){
		try {
			int totalCount =service.selectGameCount(gameName);
			List<Game> list=null;
			if (totalCount >0){
				if (paginator==null) paginator =new Paginator();
				PaginatorUtils paginatorUtils = new PaginatorUtils(paginator.getPageSize());
				int start = PaginatorUtils.getStart(paginatorUtils.executePage(request, totalCount));
				paginator.setStart(start);
				list=service.selectGameList(gameName, paginator);
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
}
