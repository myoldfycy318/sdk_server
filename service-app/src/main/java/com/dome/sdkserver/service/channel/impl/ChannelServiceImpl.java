package com.dome.sdkserver.service.channel.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.constants.channel.ChannelStatusEnum;
import com.dome.sdkserver.metadata.dao.mapper.channel.FirstChannelMapper;
import com.dome.sdkserver.metadata.dao.mapper.channel.SecondChannelMapper;
import com.dome.sdkserver.metadata.entity.channel.Channel;
import com.dome.sdkserver.metadata.entity.channel.ChannelQueryParam;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;
import com.dome.sdkserver.metadata.entity.channel.SecondChannel;
import com.dome.sdkserver.service.channel.ChannelService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.PropertiesUtil;

@Service
public class ChannelServiceImpl implements ChannelService {

	private Logger logger=LoggerFactory.getLogger(this.getClass());
//	private static volatile long firstChannelId;
//	
//	private static Map<Long, Long> secondChannelIdMap = new HashMap<>();
	@Resource
	private FirstChannelMapper firstChannelMapper;
	@Resource
	private SecondChannelMapper secondChannelMapper;
	
	@Autowired
	private PropertiesUtil domainConfig;
//	private synchronized long generateFirstChannelId(){
//		if (firstChannelId==0){
//			firstChannelId = firstChannelMapper.selectFirstChannelCount();
//		}
//		return ++firstChannelId;
//	}
	
//	private synchronized long generateSecondChannelId(long firstChannelId){
//		Long secondChannelId = secondChannelIdMap.get(firstChannelId);
//		if (secondChannelId==null){
//			secondChannelId = secondChannelMapper.selectSecondChannelCount(firstChannelId);
//			
//		}
//		secondChannelIdMap.put(firstChannelId, ++secondChannelId);
//		// 防止map过大，占用不必要的内存
//		if (secondChannelIdMap.size() >100){
//			secondChannelIdMap=new HashMap<>();
//		}
//		long secondChannelId = secondChannelMapper.selectSecondChannelCount(firstChannelId);
//		return ++secondChannelId;
//	}
	
	@Override
	public long addFirstChannel(FirstChannel firstChannel) {
		/**
		 * 先保存二级渠道数据，返回的id作为渠道ID。渠道编号为CHA+id
		 */
		SecondChannel secondChannel = new SecondChannel();
		//secondChannel.setId(parseChannelId(channelCode));
		BeanUtils.copyProperties(firstChannel, secondChannel);
		secondChannel.setStatus(ChannelStatusEnum.待审核.getCode());
		secondChannelMapper.add(secondChannel);
		long sid =secondChannel.getId();
		String channelCode = generateChannelCode(sid, 0, 1);
		secondChannelMapper.updateChannelCode(sid, channelCode);
			
		firstChannel.setChannelCode(channelCode);
		firstChannelMapper.add(firstChannel);
		return sid;
	}

//	private static long parseChannelId(String channelCode){
//		return Long.parseLong(channelCode.replaceAll(CHANNEL_CODE_PREFIX, ""));
//	}
//	
//	private static final String FIRST_CHANNELCODE_FORMAT = CHANNEL_CODE_PREFIX +"%04d000";
//	private static final String SECOND_CHANNELCODE_FORMAT = CHANNEL_CODE_PREFIX +"%04d%03d";
	private String generateChannelCode(long id, long parentId, int level){
		String channelCode=null;
//		/**
//		 * 渠道编码规则
//		 * 一级编码格式为CHA0001000，后面四位都是0
//		 * 二级编码格式为CHA0001001，前面三位是其对应的一级编码
//		 * 后面若二级渠道数过多可以增加位数
//		 */
//		switch (level){
//		case 1:{
//			channelCode =String.format(FIRST_CHANNELCODE_FORMAT, id);
//			break;
//		}
//		case 2:{
//			channelCode =String.format(SECOND_CHANNELCODE_FORMAT, parentId, id);
//			break;
//		}
//		default:break;
//		}
		channelCode =String.format(CHANNEL_CODE_PREFIX + "%06d", id);
		return channelCode;
	}
	@Override
	public long addSecondChannel(SecondChannel secondChannel) {
//		long parentId=secondChannel.getParentId();
//		long id = generateSecondChannelId(parentId);
//		String channelCode=generateChannelCode(id, parentId, 2);
//		secondChannel.setChannelCode(channelCode);
		//secondChannel.setId(parseChannelId(channelCode));
		secondChannel.setStatus(ChannelStatusEnum.待审核.getCode());
		secondChannelMapper.add(secondChannel);
		long id =secondChannel.getId();
		String channelCode=generateChannelCode(id, 0, 2);
		secondChannelMapper.updateChannelCode(id, channelCode);
		return id;
	}

	@Override
	public FirstChannel select(long id, long userId) {
		FirstChannel firstChannel = secondChannelMapper.select(id, userId);
		return firstChannel;
	}

	@Override
	public int updateStatus(long id, int status) {
		
		return secondChannelMapper.updateStatus(id, status);
	}

	/**
	 * 更新一级渠道
	 */
	@Override
	public int updateFirstChannel(FirstChannel firstChannel) {
		// 先更新渠道公有字段
		SecondChannel secondChannel = new SecondChannel();
		//secondChannel.setId(parseChannelId(channelCode));
		BeanUtils.copyProperties(firstChannel, secondChannel);
		
		secondChannel.setRemark("clear"); // 重新提交后重置驳回理由
		secondChannelMapper.update(secondChannel);
			
		return firstChannelMapper.update(firstChannel);
	}

	@Override
	@Deprecated
	public int updateSecondChannel(SecondChannel secondChannel) {
		secondChannel.setRemark("clear"); // 重新提交后重置驳回理由
		return secondChannelMapper.update(secondChannel);
	}

	@Override
	public int selectCount(long parentId, ChannelQueryParam param) {
		
		return secondChannelMapper.selectCount(parentId, param);
	}
	
	@Override
	public List<FirstChannel> selectList(long parentId, ChannelQueryParam param,
			Paginator paginator) {
		// 将一级和二级渠道都查询到
		List<FirstChannel> list=secondChannelMapper.selectList(parentId, param, paginator);
//		FirstChannel ch =secondChannelMapper.select(parentId, 0);
//		if (ch!=null){
//			if (list==null){
//				list=new ArrayList<>();
//			}
//			list.add(0, ch);
//		}
		
		return list;
	}

	@Override
	public FirstChannel selectByCode(String channelCode) {
		
		return secondChannelMapper.selectByCode(channelCode);
	}

	@Override
	public String checkUserRegister(long id, String passport) {
		// 先校验二级渠道表中，邮箱是否被使用。
		int count =secondChannelMapper.checkEmailUse(id, passport);
		if (count>0){
			return "邮箱已被使用";
		}
		String url =domainConfig.getString("channel.checkuserexist");
		List<NameValuePair> pairs = new ArrayList<>();
		pairs.add(new BasicNameValuePair("passport", passport));
		try {
			logger.info("检查填写的邮箱是否被已经注册过，request header-> url:{}, params:{}" , url, JSON.toJSONString(pairs));
			String respContent=ApiConnector.post(url, pairs);
			logger.info("检查填写的邮箱是否被已经注册过, response content: " + respContent);
			// {"code":"0","data":{"isPassportExist":true},"message":"成功"}
			if (StringUtils.isNotEmpty(respContent)){
				JSONObject json = JSON.parseObject(respContent);
				int code = json.getIntValue("code");
				if (0==code){
					String text = json.getString("data");
					json = JSON.parseObject(text);
					if (json.getBooleanValue("isPassportExist")){
						return "邮箱已被使用";
					} else {
						return null;
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查邮箱是否已使用失败", e);
		}
		return "检查邮箱是否已使用失败";
	}

	@Override
	public Channel selectByName(String name) {
		return secondChannelMapper.selectByName(name);
	}


}
