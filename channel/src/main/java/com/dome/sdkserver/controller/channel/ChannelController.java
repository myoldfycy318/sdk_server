package com.dome.sdkserver.controller.channel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bq.login.domain.user.User;
import com.dome.sdkserver.common.Constants;
import com.dome.sdkserver.constants.channel.ChannelStatusEnum;
import com.dome.sdkserver.constants.channel.CooperTypeEnum;
import com.dome.sdkserver.metadata.entity.channel.Channel;
import com.dome.sdkserver.metadata.entity.channel.ChannelQueryParam;
import com.dome.sdkserver.metadata.entity.channel.ChannelVo;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;
import com.dome.sdkserver.metadata.entity.channel.SecondChannel;
import com.dome.sdkserver.service.channel.ChannelService;
import com.dome.sdkserver.util.RedisUtil;
import com.dome.sdkserver.view.AjaxResult;
import com.dome.sdkserver.web.util.IPUtil;

import static com.dome.sdkserver.constants.DomeSdkRedisKey.CHANNEL_PARENTID;
@Controller
@ResponseBody
@RequestMapping("/channel")
public class ChannelController extends BaseController{

	@Autowired
	private ChannelService service;
	
	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * 申请和编辑一级渠道
	 * @param firstChannel
	 * @param request
	 * @return
	 */
	@RequestMapping("/save/1")
	public AjaxResult addFirst(FirstChannel firstChannel, HttpServletRequest request){
		AjaxResult result = validateParam("1", firstChannel, request);
		if (result !=null) return result;
		User user = this.getLoginUser(request);
		if (user == null){
			return AjaxResult.failed("user not login");
		}
		long userId = Long.parseLong(user.getUserId());
		String code =firstChannel.getChannelCode();
		try {
			boolean isAdd = true; // 渠道名称
			
			Channel chPast =service.select(0, userId);
			if (chPast==null){ // 新增
				if (StringUtils.isNotEmpty(code)) return AjaxResult.failed("新增渠道不需要提供了channelCode");
			} else {
				if (chPast.getParentId()>0){
					return AjaxResult.failed("只有一级渠道可以申请二级渠道和编辑渠道信息");
				}
				if (StringUtils.isEmpty(code)){
					return AjaxResult.failed("已注册过一级渠道");
				} else if (code.equals(chPast.getChannelCode())){
					
				} else { // 当前渠道编辑其他渠道，拒绝。二级渠道驳回就是删除记录，需要重新申请
					return AjaxResult.failed("illegal operation");
				}
				
				if (chPast.getStatus() !=ChannelStatusEnum.驳回.getCode()){
					return AjaxResult.failed("渠道不可以编辑");
				}
				isAdd=false;
				
			}
			// 检查邮箱是否已被使用
//			if (hasChangeEmail){
//				String errorMsg = service.checkUserRegister((chPast!=null ?chPast.getId() :0),firstChannel.getEmail());
//				if (errorMsg!=null) return AjaxResult.failed(errorMsg);
//			}
			
			// 检查渠道名是否重复。
			Channel queryChannel= service.selectByName(firstChannel.getName());
			if (queryChannel!=null && (chPast==null || queryChannel.getId()!=chPast.getId())){
				return AjaxResult.failed("渠道名称已被使用，渠道名称：" +firstChannel.getName());
			}
			FirstChannel fChannel = new FirstChannel();
			BeanUtils.copyProperties(firstChannel, fChannel);
			int isListed = firstChannel.getIsListed();
			fChannel.setIsListed((isListed ==0 ? 0 : 1));
			if (isAdd){
				
				String registerIp = IPUtil.getIpAddr(request);
				// 防止用户伪造数据
				fChannel.setRegisterIp(registerIp);
				fChannel.setUserId(userId);
				fChannel.setParentId(0);
				service.addFirstChannel(fChannel);
			} else {
				fChannel.setId(chPast.getId());
				fChannel.setChannelCode(chPast.getChannelCode());
				fChannel.setStatus(ChannelStatusEnum.待审核.getCode());
				service.updateFirstChannel(fChannel);
			}
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("添加或重新提交一级渠道出错", e);
		}
		return AjaxResult.failedSystemError();
	}
	/**
	 * 申请二级渠道，不存在编辑二级渠道情况
	 * @param firstChannel
	 * @param request
	 * @return
	 */
	@RequestMapping("/save/2")
	public AjaxResult addSecond(FirstChannel firstChannel, HttpServletRequest request){
		// 参数校验
		AjaxResult result = validateParam("2", firstChannel, request);
		if (result !=null) return result;
		User user = this.getLoginUser(request);
		if (user == null){
			return AjaxResult.failed("user not login");
		}
		long userId = Long.parseLong(user.getUserId());
		
		try {
			Channel chPast =service.select(0, userId);
			if (chPast==null || chPast.getParentId()>0){
				return AjaxResult.failed("只有一级渠道可以申请二级渠道");
				
			} else if (chPast.getStatus() !=ChannelStatusEnum.商用.getCode()){
				return AjaxResult.failed("一级渠道没有审批通过，不能申请二级渠道");
			}
			
			// 检查邮箱是否已被使用
			String errorMsg = service.checkUserRegister(0, firstChannel.getEmail());
			if (errorMsg!=null) return AjaxResult.failed(errorMsg);
			
			// 检查渠道名是否重复
			Channel queryChannel= service.selectByName(firstChannel.getName());
			if (queryChannel!=null){
				return AjaxResult.failed("渠道名称已被使用，渠道名称：" +firstChannel.getName());
			}
			SecondChannel ch = new SecondChannel();
			BeanUtils.copyProperties(firstChannel, ch);
			String registerIp = IPUtil.getIpAddr(request);
			ch.setRegisterIp(registerIp);
			
			// 二级渠道商由一级渠道商申请
			ch.setParentId(chPast.getId());
			ch.setUserId(0); //二级渠道审核通过后重写user_id
			
			service.addSecondChannel(ch);
			
			return AjaxResult.success();
		} catch (Exception e) {
			log.error("添加二级渠道出错", e);
		}
		return AjaxResult.failedSystemError();
	}

	private AjaxResult validateParam(String channelLevel, FirstChannel firstChannel, HttpServletRequest request){
		int cooperType = firstChannel.getCooperType();
		if (cooperType==0 || !CooperTypeEnum.isLeggal(cooperType)){
			return AjaxResult.failed("渠道合作方式只能为CPA和CPS，cooperType="+cooperType);
		}
		Map<String, Object> resultMap=null;
		Boolean isSuccess =null;
		if ("1".equals(channelLevel)){
			resultMap =this.valParam(request, "channelregister");
			isSuccess = (Boolean)resultMap.get("isSuccess");
			if(!isSuccess){
				return AjaxResult.conventMap(resultMap);
			}
		}
		resultMap =this.valParam(request, "channelregister2");
		isSuccess = (Boolean)resultMap.get("isSuccess");
		if(!isSuccess){
			return AjaxResult.conventMap(resultMap);
		}
		return null;
	}
//	private FirstChannel initFirstChannel(Channel channel, FirstChannel firstChannel){
//		FirstChannel fChannel = new FirstChannel(channel);
//		// 必填字段
//		fChannel.setCompanyName(firstChannel.getCompanyName());
//		fChannel.setCompanyUrl(firstChannel.getCompanyUrl());
//		fChannel.setCompanyIntro(firstChannel.getCompanyIntro());
//		fChannel.setZoneCode(firstChannel.getZoneCode());
//		fChannel.setTelephone(firstChannel.getTelephone());
//		fChannel.setBankName(firstChannel.getBankName());
//		fChannel.setBankAccount(firstChannel.getBankAccount());
//		//fChannel.setContact(firstChannel.getContact());
//		// 非必填字段
//		String s=firstChannel.getIcpRecord();
//		if (StringUtils.isNotEmpty(s)){
//			fChannel.setIcpRecord(s);
//		}
//		s=firstChannel.getLicenceNum();
//		if (StringUtils.isNotEmpty(s)){
//			fChannel.setLicenceNum(s);
//		}
//		s=firstChannel.getLicenceImageUrl();
//		if (StringUtils.isNotEmpty(s)){
//			fChannel.setLicenceImageUrl(s);
//		}
//		s=firstChannel.getLegalName();
//		if (StringUtils.isNotEmpty(s)){
//			fChannel.setLegalName(s);
//		}
//		s=firstChannel.getLegalImageUrl();
//		if (StringUtils.isNotEmpty(s)){
//			fChannel.setLegalImageUrl(s);
//		}
//		s=firstChannel.getNetworkCultureNo();
//		if (StringUtils.isNotEmpty(s)){
//			fChannel.setNetworkCultureNo(s);
//		}
//		s=firstChannel.getNetworkCultureUrl();
//		if (StringUtils.isNotEmpty(s)){
//			fChannel.setNetworkCultureUrl(s);
//		}
//		s=firstChannel.getTaxRegistNo();
//		if (StringUtils.isNotEmpty(s)){
//			fChannel.setTaxRegistNo(s);
//		}
//		s=firstChannel.getTaxRegistUrl();
//		if (StringUtils.isNotEmpty(s)){
//			fChannel.setTaxRegistUrl(s);
//		}
//		s=firstChannel.getVatNo();
//		if (StringUtils.isNotEmpty(s)){
//			fChannel.setVatNo(s);
//		}
//		s=firstChannel.getVatPicUrl();
//		if (StringUtils.isNotEmpty(s)){
//			fChannel.setVatPicUrl(s);
//		}
//		s=firstChannel.getRegistAmount();
//		if (StringUtils.isNotEmpty(s)){
//			fChannel.setRegistAmount(s);
//		}
//		int companyType = firstChannel.getCompanyType();
//		if (companyType!=0){
//			fChannel.setCompanyType(companyType);
//		}
//		int isListed = firstChannel.getIsListed();
//		fChannel.setIsListed((isListed ==0 ? 0 : 1));
//		return fChannel;
//	}
	
	@RequestMapping("/query")
	public AjaxResult query(String channelCode, HttpServletRequest request){
		long userId = this.getCurrentUserId(request);
		FirstChannel channel =service.select(0, userId);
		if (channel ==null){
			// 渠道不存在，返回状态为0，跳转到渠道申请表单页面
			channel = new FirstChannel();
			channel.setStatus(ChannelStatusEnum.未注册.getCode());
			return AjaxResult.success(channel);
		}
		// 查询二级渠道信息
		if (StringUtils.isNotEmpty(channelCode) && !channel.getChannelCode().equals(channelCode)){
			FirstChannel secondChannel=service.selectByCode(channelCode);
			//FirstChannel parentChannel =service.select(channel.getParentId(), 0);
			// 一级渠道查看二级渠道，只能查看其下面的二级渠道
			if (secondChannel!=null &&secondChannel.getParentId() !=0 && secondChannel.getParentId()==channel.getId()){
				return AjaxResult.success(new ChannelVo(secondChannel));
			} else {
				return AjaxResult.failed("unexist");
			}
		}
		// 缓存渠道是一级还是二级，只处理当前登录用户的
		if (StringUtils.isEmpty(channelCode)){
			final String key = CHANNEL_PARENTID+Long.toString(userId);
			redisUtil.setex(key, Constants.REDIS_EXPIRETIME_ONEDAY,
					Long.toString(channel.getParentId()));
		}
		ChannelVo vo = new ChannelVo(channel);
		return AjaxResult.success(vo);
	}
	
	/**
	 * 查询出一级和其添加的二级渠道
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public AjaxResult queryList(ChannelQueryParam param, Paginator paginator, HttpServletRequest request){
		long userId = this.getCurrentUserId(request);
		FirstChannel channel =service.select(0, userId);
		if (channel ==null){
			return AjaxResult.failed("渠道信息不存在");
		}
		if (StringUtils.isBlank(param.getName())){
			param.setName(null);
		}
		if (StringUtils.isEmpty(param.getBeginDate()) || !Constants.PATTERN_DATE.matcher(param.getBeginDate()).matches()){
			param.setBeginDate(null);
		}
		if (StringUtils.isEmpty(param.getEndDate()) || !Constants.PATTERN_DATE.matcher(param.getEndDate()).matches()){
			param.setEndDate(null);
		}
		int totalCount = service.selectCount(channel.getId(), param);
		List<FirstChannel> channelList =null;
		if (totalCount>0){
			paginator=Paginator.handlePage(paginator, totalCount, request);
			channelList = service.selectList(channel.getId(), param, paginator);
		} else {
			channelList =new ArrayList<>();
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>(4);
		dataMap.put("totalCount", totalCount);
		// 添加硬分页
//		List<FirstChannel> dataList= Paginator.page(channelList, paginator, request);
		List<ChannelVo> voList = new ArrayList<>(channelList.size());
		ChannelVo vo =null;
		for (FirstChannel firstChannel : channelList){
			vo=new ChannelVo(firstChannel);
			voList.add(vo);
		}
		dataMap.put("list", voList);
		return AjaxResult.success(dataMap);
	}
	
	@RequestMapping("/export")
	public AjaxResult exportExcel(ChannelQueryParam param, HttpServletRequest request, HttpServletResponse response){
		long userId = this.getCurrentUserId(request);
		FirstChannel channel =service.select(0, userId);
		if (channel ==null){ //  || channel.getParentId()!=0
			return AjaxResult.failed("渠道信息不存在");
		}
		Paginator paginator = new Paginator();
		paginator.setStart(-1);
		List<FirstChannel> dataList = null;
		WritableWorkbook wwk = null;
		try {
			dataList = 	service.selectList(channel.getId(), param, paginator);
			if (CollectionUtils.isEmpty(dataList)) {
				return AjaxResult.failed("没有查询到数据");
			}
			String fileName = "channellist_ " + DateFormatUtils.format(new Date(), "yyyyMMdd") + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=gbk");
			response.setHeader("Content-disposition", "attachment; filename="+ fileName);
			wwk = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = wwk.createSheet("Sheet1", 0);
			writeExcelTitle(titleArray, sheet);
			writeContent(titleArray, dataList, sheet);
			wwk.write();
			
		} catch (Exception e) {
			log.error("导出Excel失败, channelCode: "+ channel.getChannelCode(), e);
			return AjaxResult.failed("导出Excel失败");
		} finally {
			if (wwk != null){
				try {
					wwk.close();
				} catch (Exception e) {
					log.error("导出Excel关闭输入流失败", e);
				}
			}
		}
		
		return AjaxResult.success();
	}
	
	
	private static final Map<String, String> titleMap = new HashMap<String, String>(); 
	static {
		titleMap.put("channelCode", utf8ToGBk("渠道编号"));
		titleMap.put("name", utf8ToGBk("渠道名称"));
		titleMap.put("contact", utf8ToGBk("负责人"));
		titleMap.put("mobilePhone", utf8ToGBk("手机号码"));
		titleMap.put("cooperType", utf8ToGBk("合作方式"));
		titleMap.put("email", utf8ToGBk("邮箱"));
		titleMap.put("address", utf8ToGBk("联系地址"));
	}
	
	private static String[] titleArray = new String[]{"channelCode", "name", "contact", "mobilePhone", "cooperType",
		"email", "address"};
	
	private static String utf8ToGBk(String s){
		
		return s;
	}
	
	private void writeExcelTitle(String[] titleArray, WritableSheet sheet) throws RowsExceededException, WriteException{

		Label label = null;
		for (int i =0; i< titleArray.length; i++){
			String title =titleArray[i];
			if (titleMap.get(title) == null) continue;
			label = new Label(i, 0, titleMap.get(title));
			sheet.addCell(label);
		}
	}
	
	private void writeContent(String[] titleArray, List<FirstChannel> dataList, WritableSheet sheet)
			throws RowsExceededException, WriteException{
		Label label = null;
		for (int i = 0; i<dataList.size(); i++) {
			FirstChannel fc = dataList.get(i);
			for (int j = 0; j<titleArray.length; j++){
				String title =titleArray[j];
				if (titleMap.get(title) == null) continue;
				label = new Label(j, i+1, getPropValue(fc, title));
				sheet.addCell(label);
			}
		}

	}
	
	private String getPropValue(FirstChannel fc, String prop){
		String s = "";
		if ("channelCode".equalsIgnoreCase(prop)){
			s = fc.getChannelCode();
		} else if ("name".equalsIgnoreCase(prop)){
			s = utf8ToGBk(fc.getName());
		} else if ("contact".equalsIgnoreCase(prop)){
			s = utf8ToGBk(fc.getContact());
		} else if ("mobilePhone".equalsIgnoreCase(prop)){
			s = fc.getMobilePhone();
		} else if ("cooperType".equalsIgnoreCase(prop)){
			s = (fc.getCooperType()==1 ? "CPA" : "CPS");
		} else if ("email".equalsIgnoreCase(prop)){
			s = fc.getEmail();
		} else if ("address".equalsIgnoreCase(prop)){
			s = utf8ToGBk(fc.getAddress());
		}
		
		return s;
	}
}
