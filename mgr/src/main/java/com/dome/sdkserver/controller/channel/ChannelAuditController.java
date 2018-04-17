package com.dome.sdkserver.controller.channel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.constants.channel.ChannelStatusEnum;
import com.dome.sdkserver.constants.channel.CooperTypeEnum;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.metadata.entity.channel.Channel;
import com.dome.sdkserver.metadata.entity.channel.ChannelQueryParam;
import com.dome.sdkserver.metadata.entity.channel.ChannelVo;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;
import com.dome.sdkserver.metadata.entity.channel.JieSuanConfig;
import com.dome.sdkserver.metadata.entity.channel.PromoteType;
import com.dome.sdkserver.service.channel.ChannelAuditService;
import com.dome.sdkserver.service.channel.PromoteTypeService;
import com.dome.sdkserver.util.PaginatorUtils;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.sdkserver.view.RequestParamHandler;

@Controller
@ResponseBody
@RequestMapping("/channel")
public class ChannelAuditController extends BaseController {

	@Autowired
	private ChannelAuditService service;
	/**
	 * 查询渠道列表，包含一级和二级渠道
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public AjaxResult queryList(Paginator paginator, ChannelQueryParam channelQueryParam, HttpServletRequest request){
		int status=RequestParamHandler.getStatus(channelQueryParam.getStatus());
		
		// 获取是渠道审批页面还是渠道管理页面。渠道审批 1  渠道管理2
		String pageType=channelQueryParam.getPageType();
		if (StringUtils.isBlank(pageType) || !("1".equals(pageType) ||"2".equals(pageType))) {
			channelQueryParam.setPageType("1");
		}
		channelQueryParam.setStatusVal(status);
		try {
			int totalCount =service.selectChannelsCount(channelQueryParam);
			if (paginator==null) paginator =new Paginator();
			PaginatorUtils paginatorUtils = new PaginatorUtils(paginator.getPageSize());
			int start = PaginatorUtils.getStart(paginatorUtils.executePage(request, totalCount));
			paginator.setStart(start);
			List<ChannelVo> voList = new ArrayList<>();
			if (totalCount >0){
				List<FirstChannel> channelList=service.selectList(channelQueryParam, paginator);
				for (FirstChannel cha:channelList){
					ChannelVo vo = new ChannelVo(cha);
					voList.add(vo);
				}
			}
			Map<String, Object> dataMap = new HashMap<String, Object>(4);
			dataMap.put("totalCount", totalCount);
			
			dataMap.put("list", voList);
			return AjaxResult.success(dataMap);
		} catch (Exception e) {
			log.error("查询渠道审批列表出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 渠道审批
	 * @param code
	 * @param dividePercent
	 * @param activityUnitPrice
	 * @param typeIds
	 * @param request
	 * @return
	 */
	@RequestMapping("/pass")
	public AjaxResult pass(String channelCode, String activityUnitPrice, String dividePercent,
			String typeIds, HttpServletRequest request){
		String code=channelCode;
		if (StringUtils.isEmpty(code)){
			return AjaxResult.failed("channel code is an empty string");
		}
		
		try {
			Channel channel = service.selectByCode(code, false);
			AjaxResult result=auditCheck(channel);
			if (result!=null) return result;
			int cooperType = channel.getCooperType();
			result=validateHighParam(cooperType, activityUnitPrice, dividePercent, typeIds);
			if (result!=null) return result;
			JieSuanConfig jsConfig = new JieSuanConfig();
			jsConfig.setChannelId(channel.getId());
			if (cooperType==CooperTypeEnum.CPA.getCode()){
				jsConfig.setActivityUnitPrice(new BigDecimal(activityUnitPrice));
			} else if (cooperType==CooperTypeEnum.CPS.getCode()){
				jsConfig.setDividePercent(new BigDecimal(dividePercent));
			}
			String errorMsg=service.pass(jsConfig, typeIds);
			if (errorMsg==null){
				return AjaxResult.success();
			}else {
				return AjaxResult.failed(errorMsg);
			}
		} catch (Exception e) {
			log.error("渠道审批通过出错", e);
		}
		return AjaxResult.failedSystemError();
	}

	private static final Pattern PATTERN_CURRENCY = Constants.PATTERN_CURRENCY;
	
	@Autowired
	private PromoteTypeService ptService;
	
	// 校验结算配置的激活单价和分成比例、推广分类
	private AjaxResult validateHighParam(int cooperType, String activityUnitPrice, String dividePercent,
			String typeIds){
		if (cooperType==CooperTypeEnum.CPA.getCode()){
			 if (StringUtils.isEmpty(activityUnitPrice)) return AjaxResult.failed("激活单价不能为空");
			 if (!PATTERN_CURRENCY.matcher(activityUnitPrice).matches()){
				 return AjaxResult.failed("激活单价值不合法");
			 }
		} else if (cooperType==CooperTypeEnum.CPS.getCode()){
			if (StringUtils.isEmpty(dividePercent)) return AjaxResult.failed("分成比例不能为空");
			if (!PATTERN_CURRENCY.matcher(dividePercent).matches()){
				return AjaxResult.failed("分成比例值不合法");
			 }
		} else {
			return AjaxResult.failed("合作方式不支持，cooperType=" +cooperType);
		}
		
		if (StringUtils.isEmpty(typeIds)){
			return AjaxResult.failed("推广分类不能为空");
		}
		String[] typeIdArr=typeIds.split(",");
		for (String typeId:typeIdArr){
			if (!Constants.PATTERN_NUM.matcher(typeId).matches()){
				return AjaxResult.failed("推广分类值不合法，typeIds=" +typeIds);
			}
			PromoteType pt= ptService.select(Long.parseLong(typeId));
			if (pt==null){
				return AjaxResult.failed("推广分类值不合法，typeId=" +typeId);
			}
		}
		return null;
	}
	// 审批前检查，渠道需要存在和当前状态为待审核
	private AjaxResult auditCheck(Channel channel) {
		if (channel==null){
			return AjaxResult.failed("渠道信息不存在");
		}
		// 待审核状态才可以审批、驳回
		int status =channel.getStatus();
		if (channel.getStatus()!= ChannelStatusEnum.待审核.getCode()){
			return AjaxResult.failed("渠道目前不允许审批，状态为" + ChannelStatusEnum.getStatusDesc(status));
		}
		return null;
	}
	
	/**
	 * 
	 * @param code
	 * @param remark
	 * @param request
	 * @return
	 */
	@RequestMapping("/reject")
	public AjaxResult reject(String channelCode, String remark, HttpServletRequest request){
		String code=channelCode;
		if (StringUtils.isEmpty(code)){
			return AjaxResult.failed("channel code is an empty string");
		}
		if (StringUtils.isEmpty(remark)){
			return AjaxResult.failed("驳回理由不能为空");
		}
		try {
			Channel channel = service.selectByCode(code, false);
			if (channel.getParentId()>0){
				return AjaxResult.failed("二级渠道不需要驳回，可以做删除操作");
			}
			AjaxResult result=auditCheck(channel);
			if (result!=null) return result;
			
			FirstChannel firstChannel = new FirstChannel();
			firstChannel.setStatus(ChannelStatusEnum.驳回.getCode());
			firstChannel.setId(channel.getId());
			firstChannel.setRemark(remark);
			int line=service.updateChannel(firstChannel);
			if (line==1){
				return AjaxResult.success();
			}else {
				return AjaxResult.failed();
			}
		} catch (Exception e) {
			log.error("audit channel error", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 渠道暂停和暂停后恢复商用
	 * @param code
	 * @param status
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateStatus")
	public AjaxResult updateStatus(ChannelQueryParam channelParam, HttpServletRequest request){
		String code=channelParam.getChannelCode();
		if (StringUtils.isEmpty(code)){
			return AjaxResult.failed("channel code is an empty string");
		}
		int status=RequestParamHandler.getStatus(channelParam.getStatus());
		if (status != ChannelStatusEnum.渠道暂停.getCode() && status != ChannelStatusEnum.商用.getCode()){
			return AjaxResult.failed("Illegal operation, status="+status);
		}
		
		try {
			Channel channel = service.selectByCode(code, false);
			if (channel==null){
				return AjaxResult.failed("渠道信息不存在");
			}
			// 商用状态可以暂停，渠道暂停状态可以商用
			int pastStatus=channel.getStatus();
			if ((status == ChannelStatusEnum.商用.getCode() && pastStatus==ChannelStatusEnum.渠道暂停.getCode())
				||(status == ChannelStatusEnum.渠道暂停.getCode() && pastStatus==ChannelStatusEnum.商用.getCode())){
				String errorMsg=service.updateStatus(channel.getId(), status);
				if (errorMsg==null){
					return AjaxResult.success();
				}else {
					return AjaxResult.failed();
				}
			} else {
				return AjaxResult.failed("Illegal operation, pastStatus="+pastStatus +", status="+status);
			}
			
		} catch (Exception e) {
			log.error("audit channel error", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 编辑
	 * 渠道配置项和结算配置、推广分类
	 * @param firstChannel
	 * @param request
	 * @return
	 */
	@RequestMapping("/edit")
	public AjaxResult update(Channel firstChannel, String activityUnitPrice, String dividePercent,
			String typeIds, HttpServletRequest request){
		if (firstChannel==null || StringUtils.isEmpty(firstChannel.getChannelCode())){
			return AjaxResult.failed("渠道编号不能为空");
		}
		String channelCode=firstChannel.getChannelCode();
		try {
			
			FirstChannel channel=service.selectByCode(channelCode, false);
			if (channel==null){
				return AjaxResult.failed("渠道信息不存在");
			}
			int cooperType = firstChannel.getCooperType();
			AjaxResult result=validateHighParam(cooperType, activityUnitPrice, dividePercent, typeIds);
			if (result!=null) return result;
			// 限制能修改的字段
			FirstChannel ch = new FirstChannel();
			ch.setId(channel.getId());
			ch.setChannelCode(channel.getChannelCode());
			if (channel.getParentId() !=0)
				ch.setParentId(channel.getParentId());
			String s= firstChannel.getName();
			if (StringUtils.isNotEmpty(s)){
				ch.setName(s);
			}
			s= firstChannel.getContact();
			if (StringUtils.isNotEmpty(s)){
				ch.setContact(s);
			}
			s= firstChannel.getBankName();
			if (StringUtils.isNotEmpty(s)){
				ch.setBankName(s);
			}
			s= firstChannel.getBankAccount();
			if (StringUtils.isNotEmpty(s)){
				ch.setBankAccount(s);
			}
			s= firstChannel.getMobilePhone();
			if (StringUtils.isNotEmpty(s)){
				ch.setMobilePhone(s);
			}
			s= firstChannel.getQq();
			if (StringUtils.isNotEmpty(s)){
				ch.setQq(s);
			}
			s= firstChannel.getAddress();
			if (StringUtils.isNotEmpty(s)){
				ch.setAddress(s);
			}
//			s= firstChannel.getEmail();
//			if (StringUtils.isNotEmpty(s)){
//				ch.setEmail(s);
//			}
			ch.setCooperType(firstChannel.getCooperType());
			
			// 结算配置和推广分类
			if (cooperType==CooperTypeEnum.CPA.getCode()){
				ch.setActivityUnitPrice(new BigDecimal(activityUnitPrice));
			} else if (cooperType==CooperTypeEnum.CPS.getCode()){
				ch.setDividePercent(new BigDecimal(dividePercent));
			}
			ch.setTypeIdStr(typeIds);
			service.updateChannel(ch);
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("渠道编辑出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	@RequestMapping("/query")
	public AjaxResult queryChannel(String channelCode, HttpServletRequest request){
		String code=channelCode;
		if (StringUtils.isEmpty(code)){
			return AjaxResult.failed("channel code is an empty string");
		}
		
		try {
			FirstChannel channel = service.selectByCode(code, true);
			if (channel==null){
				return AjaxResult.failed("渠道信息不存在");
			}
			return AjaxResult.success(new ChannelVo(channel));
			
		} catch (Exception e) {
			log.error("查询渠道信息出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	@RequestMapping("/delete")
	public AjaxResult delete(String channelCode, HttpServletRequest request){
		if (StringUtils.isEmpty(channelCode)){
			return AjaxResult.failed("channel code is an empty string");
		}
		
		try {
			FirstChannel channel = service.selectByCode(channelCode, false);
			if (channel==null){
				return AjaxResult.failed("渠道信息不存在");
			}
			if (channel.getParentId()==0) return AjaxResult.failed("一级渠道不允许删除，只能删除二级渠道");
			if (channel.getStatus()!=ChannelStatusEnum.待审核.getCode()){
				return AjaxResult.failed("待审核状态下才可以删除二级渠道");
			}
			int line=service.delete(channel);
			if (line==1){
				return AjaxResult.success();
			}
			return AjaxResult.failed();
		} catch (Exception e) {
			log.error("删除二级渠道出错", e);
		}
		return AjaxResult.failedSystemError();
	}
}
