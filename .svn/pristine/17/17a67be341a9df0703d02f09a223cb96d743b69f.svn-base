package com.dome.sdkserver.controller.channel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.constants.channel.JsStatusEnum;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.metadata.entity.channel.JsOrder;
import com.dome.sdkserver.metadata.entity.channel.JsOrderVo;
import com.dome.sdkserver.service.channel.JsAuditService;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.sdkserver.view.RequestParamHandler;

@ResponseBody
@Controller
@RequestMapping("/channel/settle")
public class JsAuditController extends BaseController {

	@Autowired
	private JsAuditService service;
	
	/**
	 * 查询结算审批数据
	 * 目前支持status和name(渠道名称)过滤
	 * @param order
	 * @param paginator
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public AjaxResult list(String status, String name, Paginator paginator, HttpServletRequest request){
		JsOrder order=new JsOrder();
		int statusVal = RequestParamHandler.getStatus(status);
		order.setStatus(statusVal);
		if (StringUtils.isNotBlank(name)) order.setName(name);
		try {
			int totalCount =service.selectCount(order);
			List<JsOrder> list=null;
			if (totalCount >0){
				paginator=Paginator.handlePage(paginator, totalCount, request);
				list=service.selectList(order, paginator);
			} else {
				list=new ArrayList<>();
			}
			Map<String, Object> dataMap = new HashMap<String, Object>(4);
			dataMap.put("totalCount", totalCount);
			List<JsOrderVo> voList = new ArrayList<>(list.size());
			for (JsOrder jo:list){
				JsOrderVo vo = new JsOrderVo(jo);
				voList.add(vo);
			}
			dataMap.put("list", voList);
			return AjaxResult.success(dataMap);
		} catch (Exception e) {
			log.error("查询结算审批数据出粗", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	@RequestMapping("/pass")
	public AjaxResult pass(String orderNo, HttpServletRequest request){
		if (StringUtils.isEmpty(orderNo)){
			return AjaxResult.failed("结算单号orderNo不能为空");
		}
		try {
			JsOrder order=service.select(orderNo);
			if (order ==null){
				return AjaxResult.failed("结算单不存在");
			} else {
				int status = order.getStatus();
				
				if (status!=JsStatusEnum.申请中.getStatus()){
					return AjaxResult.failed("结算单不允许审批，状态为" +JsStatusEnum.getStatusDesc(status));
				}
			}
			JsOrder jo = new JsOrder();
			jo.setId(order.getId());
			jo.setStatus(JsStatusEnum.已结算.getStatus());
			int line =service.update(jo);
			if (line==1) return AjaxResult.success();
			return AjaxResult.failed();
		} catch (Exception e) {
			log.error("审批结算单出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	@RequestMapping("/view")
	public AjaxResult view(String orderNo, HttpServletRequest request){
		if (StringUtils.isEmpty(orderNo)){
			return AjaxResult.failed("结算单号orderNo不能为空");
		}
		try {
			JsOrder order=service.select(orderNo);
			if (order ==null){
				return AjaxResult.failed("结算单不存在");
			}
			JsOrderVo vo = new JsOrderVo(order);
			return AjaxResult.success(vo);
		} catch (Exception e) {
			log.error("查看结算单出粗", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	@RequestMapping("/edit")
	public AjaxResult edit(JsOrder orderParam, HttpServletRequest request){
		
		if (orderParam==null || StringUtils.isEmpty(orderParam.getOrderNo())){
			return AjaxResult.failed("结算单号orderNo不能为空");
		}
		String orderNo=orderParam.getOrderNo();
		BigDecimal amount = orderParam.getAmount();
		// 结算金额
		if (amount==null || amount.scale()>2){
			return AjaxResult.failed("结算金额不合法");
		}
		try {
			JsOrder order=service.select(orderNo);
			if (order ==null){
				return AjaxResult.failed("结算单不存在");
			} else {
				int status = order.getStatus();
				if (status!=JsStatusEnum.申请中.getStatus()){
					return AjaxResult.failed("结算单不允许编辑，状态为" +JsStatusEnum.getStatusDesc(status));
				}
			}
			JsOrder jo = new JsOrder();
			// 目前仅支持编辑结算金额
			jo.setId(order.getId());
			jo.setJsAmount(orderParam.getAmount());
			int line =service.update(jo);
			if (line==1) return AjaxResult.success();
			return AjaxResult.failed();
		} catch (Exception e) {
			log.error("编辑结算单出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	
}
