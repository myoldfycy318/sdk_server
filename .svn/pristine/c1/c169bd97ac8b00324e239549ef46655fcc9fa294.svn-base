package com.dome.sdkserver.controller.channel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;
import com.dome.sdkserver.metadata.entity.channel.JsOrder;
import com.dome.sdkserver.metadata.entity.channel.JsOrderVo;
import com.dome.sdkserver.service.channel.ChannelService;
import com.dome.sdkserver.service.channel.JsService;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.view.AjaxResult;

@Controller
@ResponseBody
@RequestMapping("/channel/settle")
public class JieSuanController extends BaseController {

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private JsService service;
	
	@Value("${chanel.jiesuan.enddate}")
	private int jdDate;
	/**
	 * 查询结算金额
	 * @return
	 */
	@RequestMapping("/query")
	public AjaxResult query(HttpServletRequest request){
		try {
			long userId = this.getCurrentUserId(request);
			FirstChannel channel =channelService.select(0, userId);
			if (channel.getParentId()!=0){
				return AjaxResult.failed("只有一级渠道可以查询结算金额，channelCode=" + channel.getChannelCode());
			}
			long channelId = channel.getId();
			JsOrder order = service.queryNew(channelId);
			JsOrderVo vo=null;
			if (order !=null){
				String dateformatStr = "yyyyMMdd";
				String jsEndDate = DateFormatUtils.format(order.getToDate(), dateformatStr);
				String endDate = DateFormatUtils.format(DateUtil.getLastMonthEndDate(), dateformatStr);
				if (endDate.compareTo(jsEndDate)<=0){
					String fromDateMonth = DateFormatUtils.format(order.getFromDate(), JsService.MONTH_DESC_FORMAT);
					String endDateMonth = DateFormatUtils.format(order.getToDate(), JsService.MONTH_DESC_FORMAT);
					vo=new JsOrderVo(fromDateMonth.equals(endDateMonth)? fromDateMonth :fromDateMonth +"-"+endDateMonth, order.getJsAmount());
					return AjaxResult.success(vo);
				}
			}
			vo = service.query(channelId);
			return AjaxResult.success(vo);
		} catch (Exception e) {
			log.error("查询结算金额出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 结算申请
	 * @param request
	 * @return
	 */
	@RequestMapping("/apply")
	public AjaxResult apply(HttpServletRequest request){
		Calendar c =Calendar.getInstance();
		int day =c.get(Calendar.DATE);
		if (day>jdDate){
			return AjaxResult.failed("申请结算时间只能为每月的1-" +Integer.toString(jdDate)+"号。");
		}
		try {
			long userId = this.getCurrentUserId(request);
			FirstChannel channel =channelService.select(0, userId);
			if (channel.getParentId()!=0){
				return AjaxResult.failed("只有一级渠道可以申请结算，channelCode=" + channel.getChannelCode());
			}
			long channelId = this.getCurrChannelId(request);
			String msg = service.apply(channelId);
			if (msg!=null){
				return AjaxResult.failed(msg);
			}
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("申请结算出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	
	/**
	 * 查询结算记录
	 * @param paginator
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public AjaxResult list(Paginator paginator, HttpServletRequest request){
		try {
			long userId = this.getCurrentUserId(request);
			FirstChannel channel =channelService.select(0, userId);
			if (channel.getParentId()!=0){
				return AjaxResult.failed("只有一级渠道可以查询结算记录，channelCode=" + channel.getChannelCode());
			}
			long channelId = channel.getId();
			int totalCount = service.selectCount(channelId);
			List<JsOrder> list=null;
			if (totalCount >0){
				paginator = Paginator.handlePage(paginator, totalCount, request);
				
				list=service.selectList(channelId, paginator);
			} else {
				list=new ArrayList<>();
			}
			Map<String, Object> dataMap = new HashMap<String, Object>(4);
			dataMap.put("totalCount", totalCount);
			List<JsOrderVo> voList=new ArrayList<>();
			for (JsOrder order : list){
				JsOrderVo vo = new JsOrderVo(order);
				voList.add(vo);
			}
			dataMap.put("list", voList);
			return AjaxResult.success(dataMap);
		} catch (Exception e) {
			log.error("查询结算记录出错", e);
		}
		return AjaxResult.failedSystemError();
	}
}
