package com.dome.sdkserver.service.channel.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bo.datadict.DataDictInfo;
import com.dome.sdkserver.bq.util.AESCoder;
import com.dome.sdkserver.constants.Constant.DataDictEnum;
import com.dome.sdkserver.constants.channel.ChannelStatusEnum;
import com.dome.sdkserver.constants.channel.CooperTypeEnum;
import com.dome.sdkserver.metadata.dao.mapper.channel.FirstChannelMapper;
import com.dome.sdkserver.metadata.dao.mapper.channel.JieSuanConfigMapper;
import com.dome.sdkserver.metadata.dao.mapper.channel.PromoteTypeMapper;
import com.dome.sdkserver.metadata.dao.mapper.channel.SecondChannelMapper;
import com.dome.sdkserver.metadata.dao.mapper.datadictmapper.DatadictMapper;
import com.dome.sdkserver.metadata.entity.channel.Channel;
import com.dome.sdkserver.metadata.entity.channel.ChannelQueryParam;
import com.dome.sdkserver.metadata.entity.channel.ChannelType;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;
import com.dome.sdkserver.metadata.entity.channel.JieSuanConfig;
import com.dome.sdkserver.metadata.entity.channel.SecondChannel;
import com.dome.sdkserver.service.channel.ChannelAuditService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.DesEncryptUtil;
import com.dome.sdkserver.util.MailUtil;
import com.dome.sdkserver.util.PropertiesUtil;
@Service
public class ChannelAuditServiceImpl implements ChannelAuditService {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Resource
	private FirstChannelMapper firstChannelMapper;
	@Resource
	private SecondChannelMapper secondChannelMapper;
	
	@Resource
	private PropertiesUtil domainConfig;
	
	private RegistUserAttentEmail emailUtil;
	
	@Autowired
	private JieSuanConfigMapper jsMapper;
	@Override
	public FirstChannel selectByCode(String channelCode, boolean needTypeIds) {
		FirstChannel cha=secondChannelMapper.selectByCode(channelCode);
		if (cha!=null && needTypeIds){
			initChannelPt(cha);
			initJsConf(cha);
		}
		return cha;
	}

	// 设置结算配置数据，分成比例和激活单价
	private void initJsConf(FirstChannel cha) {
		JieSuanConfig jsConfig =jsMapper.select(cha.getId());
		if (jsConfig!=null){
			if (cha.getCooperType()==CooperTypeEnum.CPA.getCode()){
				cha.setActivityUnitPrice(jsConfig.getActivityUnitPrice());
			} else {
				cha.setDividePercent(jsConfig.getDividePercent());
			}
		}
		
	}

	@Override
	public FirstChannel select(long id, long userId) {
		
		return secondChannelMapper.select(id, userId);
	}

	@Override
	public int selectChannelsCount(ChannelQueryParam channelQueryParam) {
		
		return secondChannelMapper.selectChannelsCount(channelQueryParam);
	}
	
	@Override
	public List<FirstChannel> selectList(ChannelQueryParam channelQueryParam, Paginator paginator) {
		List<FirstChannel> list=secondChannelMapper.selectAuditList(channelQueryParam, paginator);
		for (FirstChannel cha : list){
			initChannelPt(cha);
			initJsConf(cha);
		}
		return list;
	}

	// 设置渠道关联的推广分类
	public void initChannelPt(FirstChannel cha) {
		List<ChannelType> typeList = secondChannelMapper.selectChannelTypeList(cha.getId());
		cha.setTypeIds(typeList);
	}

	/**
	 * 渠道暂停和暂停后启用
	 * 渠道暂停不影响计费、和扣量配置等
	 */
	@Override
	public String updateStatus(long id, int status) {
		// 一级渠道暂停将其下的所有二级渠道都暂停
		if (status==ChannelStatusEnum.渠道暂停.getCode()){
			Channel channel = secondChannelMapper.select(id, 0);
			if (channel.getParentId()==0){
				Paginator paginator = new Paginator();
				paginator.setStart(-1); //设置不需要分页
				List<FirstChannel> list=secondChannelMapper.selectList(channel.getId(), new ChannelQueryParam(), paginator);
				for (Channel ch : list){
					secondChannelMapper.updateStatus(ch.getId(), ChannelStatusEnum.渠道暂停.getCode());
				}
			}
			
		}
		
		secondChannelMapper.updateStatus(id, status);
		
		// 渠道暂停或恢复通知sdkserver
		return null;
	}

	@Value("${channel.registeruser.defaultpasswd}")
	private String defaultPasswd;
	
	@Value("${channel.registeruser}")
	private String registerUserUrl;
	// 注册二级渠道用户
	private String registerUser(FirstChannel firstChannel){
		/**
		 * /open/autoRegister
		 * passport password
		 * code=0 success
		 * OPEN_PASSPORT_EXIST("-51", "该账号已注册")
		 * message
		 */
		List<NameValuePair> pairs = new ArrayList<>();
		// 传递的密码需要加密
		String password = AESCoder.getEncryptResult(defaultPasswd, domainConfig.getString("usercenter.aes.publickey").trim());
		pairs.add(new BasicNameValuePair("passport", firstChannel.getEmail()));
		pairs.add(new BasicNameValuePair("password", password));
		logger.info("注册二级渠道用户，request header-> url:{}, params:{}" , registerUserUrl, JSON.toJSONString(pairs));
		String respContent=ApiConnector.post(registerUserUrl, pairs);
		logger.info("注册二级渠道用户, response content: " + respContent);
		if (StringUtils.isNotEmpty(respContent)){
			JSONObject json = JSON.parseObject(respContent);
			int code = json.getIntValue("code");
			if (0==code){
				// 注册成功给一级渠道邮箱发邮件
				if (emailUtil== null) emailUtil=new RegistUserAttentEmail();
				final long parentChannelId = firstChannel.getParentId();
//				new Thread(){
//					public void run() {
						DataDictInfo dict = emailUtil.getDict();
						FirstChannel parentChannel=secondChannelMapper.select(parentChannelId, 0);
						List<String> receiverList=new ArrayList<>();
						receiverList.add(parentChannel.getEmail());
						
						String content = String.format(emailUtil.getContent(), firstChannel.getEmail(), defaultPasswd);
						try {
							MailUtil.sendPureTextMail(dict.getAttrVal(), dict.getDescribe(),
									receiverList, emailUtil.getTitle(), content);
							
							// 更新二级渠道关联的userId
							//  {"code":"0","data":{"accessToken":"b3";user_id:1000},"message":"成功"}
							String dataText=json.getString("data");
							json = JSON.parseObject(dataText);
							long userId =Long.parseLong(json.getString("user_id"));
							secondChannelMapper.updateSecondUserId(firstChannel.getId(), userId);
							return null;
						} catch (Exception e) {
							logger.error("发送二级渠道注册用户提醒邮件出错, receiver email:" + parentChannel.getEmail(), e);
						}
//					};
//				}.start();
				
			} else {
				return code +":" + json.getString("message");
			}
		}
		return "注册二级渠道用户失败";
	}
	/**
	 * 渠道驳回也调用本方法
	 * 结算配置和推广分类也需要更新
	 */
	@Override
	public int updateChannel(FirstChannel firstChannel) {
		
		JieSuanConfig jsConfig = new JieSuanConfig();
		long channelId = firstChannel.getId();
		jsConfig.setChannelId(channelId);
		if (firstChannel.getStatus()==ChannelStatusEnum.驳回.getCode()){
			SecondChannel secondChannel = new SecondChannel();
			BeanUtils.copyProperties(firstChannel, secondChannel);
			int line =secondChannelMapper.update(secondChannel);
			return line;
		}
		// 先保存结算配置，保证配置没保存成功时，渠道状态不会修改
		if (firstChannel.getActivityUnitPrice()!=null){
			jsConfig.setActivityUnitPrice(firstChannel.getActivityUnitPrice());
		} else {
			jsConfig.setDividePercent(firstChannel.getDividePercent());
		}
		saveJsTypeInfo(jsConfig, channelId, firstChannel.getTypeIdStr(), firstChannel.getChannelCode());
		SecondChannel secondChannel = new SecondChannel();
		BeanUtils.copyProperties(firstChannel, secondChannel);
		int line =secondChannelMapper.update(secondChannel);
		// 如果修改了合作方式
		FirstChannel fc = secondChannelMapper.select(channelId, 0);
		if (fc.getCooperType()!=firstChannel.getCooperType() ){
			Channel c=secondChannelMapper.selectCooperType(channelId);
			if (c!=null){
				secondChannelMapper.deleteCooperType(c.getId());
			}
			
			secondChannelMapper.addCooperType(channelId, firstChannel.getCooperType());
		}
		if (firstChannel.getParentId()!=0) {
			line =firstChannelMapper.update(firstChannel);
		}
		return line;
	}

	@Autowired
	private DatadictMapper datadictMapper;
	
	private class RegistUserAttentEmail{
		private String title = "二级渠道申请成功";
		
		private String content="恭喜您二级渠道申请成功。请使用以下信息进行登陆。\n网址  open.domestore.cn\n账号  %s\n密码  %s";
		
		private DataDictInfo dict =null;
		public RegistUserAttentEmail(){
				init();
		}
		
		private void init(){
			List<String> list=new ArrayList<>();
			list.add(DataDictEnum.MSG_NOTIFY_MAIL.getAttrCode());
			List<DataDictInfo> dictList = datadictMapper.getDataDictListByAttrCode(list);
			if (dictList.size()>0){
				DataDictInfo dict = dictList.get(0);
				// 发件人邮箱解密
				try {
					String mingwen = DesEncryptUtil.decryptPassword(dict.getDescribe(), domainConfig.getString("domesdk.sendermail.passwdsecretkey"));
					dict.setDescribe(mingwen);
					this.dict= dict;
				} catch (Exception e) {
					logger.error("解密发件人邮箱密码出错", e);
				}
		        
			}
			
		}
		
		public String getTitle() {
			return title;
		}

		public String getContent() {
			return content;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public DataDictInfo getDict() {
			return dict;
		}
		
		
	}

	/**
	 * 渠道由待审核到商用，审批通过
	 */
	@Override
	public String pass(JieSuanConfig jsConfig, String typeIds) {
		// 渠道审批通过
		long channelId=jsConfig.getChannelId();
		try {
			FirstChannel firstChannel=secondChannelMapper.select(channelId, 0);
			/**
			 * 第一次审批通过时注册二级渠道用户
			 */
			if (firstChannel.getParentId()!=0){ //  && firstChannel.getStatus()==ChannelStatusEnum.待审核.getCode()
				String msg = registerUser(firstChannel);
				if (msg != null) return msg;
			}
			
			saveJsTypeInfo(jsConfig, channelId, typeIds, firstChannel.getChannelCode());
			
			secondChannelMapper.updateStatus(channelId, ChannelStatusEnum.商用.getCode());
			// 保存合作方式
			secondChannelMapper.addCooperType(channelId, firstChannel.getCooperType());
			return null;
		} catch (Exception e) {
			logger.error("渠道审批失败", e);
		}
		return "渠道审批失败";
	}

	private void saveJsTypeInfo(JieSuanConfig jsConfig, long channelId, String typeIds, String channelCode) {
		// 保存结算参数
		/**
		 * 方便后期查看结算配置，每次修改结算配置都是将之前的删除，重新添加
		 */
		JieSuanConfig pastJsConfig = jsMapper.select(channelId);
		boolean needUpdate = false;
		if (pastJsConfig!=null){
			// 重写了equals，比较结算配置是否修改了
			if (!pastJsConfig.equals(jsConfig)){
				needUpdate = true;
				jsMapper.delete(pastJsConfig.getId());
			}
			
		}
		if (pastJsConfig== null || needUpdate)jsMapper.add(jsConfig);
		// 保存渠道关联的分类
		/**
		 * 删除过去的数据，重新添加
		 */
		String[] idArr= typeIds.split(",");
		List<Long> newIdList= new ArrayList<>(idArr.length);
		for (String id : idArr){
			newIdList.add(Long.valueOf(id));
		}
		
		List<ChannelType> ctList = secondChannelMapper.selectChannelTypeList(channelId);
		List<Long> pastIdList=new ArrayList<>(ctList.size());
		// 根据主键id删除
		List<Long> needDelIdList = new ArrayList<>();
//		// 需要删除的typeId
//		Set<Long> needDelTypeIdSet =new HashSet<>();
		for (ChannelType ct :ctList){
			long typeId = ct.getTypeId();
			pastIdList.add(typeId);
			if (!newIdList.contains(typeId)){
				needDelIdList.add(ct.getId());
//				needDelTypeIdSet.add(typeId);
			}
		}
		List<Long> needAddIdList=new ArrayList<>();
		for (long typeId : newIdList){
			if (!pastIdList.contains(typeId)){
				needAddIdList.add(typeId);
			}
		}
		// 添加和删除渠道已关联的分类前，获取过去关联的游戏gameId列表
		List<Long> beforeGameIdList = promoteTypeMapper.selectChannelGame(channelId);
		if (!needDelIdList.isEmpty()) secondChannelMapper.deleteAddChannelType(needDelIdList);
		if (!needAddIdList.isEmpty()) secondChannelMapper.batchAddChannelType(channelId, needAddIdList);
		
//		Set<Long> addGameIdSet=new HashSet<>();
//		for (long typeId :needAddIdList){
//			List<Game> gameList=promoteTypeMapper.selectGList(typeId);
//			for (Game g :gameList){
//				addGameIdSet.add(g.getGameId());
//			}
//		}
//		Set<Long> delGameIdSet=new HashSet<>();
//		for (long typeId :needDelTypeIdSet){
//			List<Game> gameList=promoteTypeMapper.selectGList(typeId);
//			for (Game g :gameList){
//				delGameIdSet.add(g.getGameId());
//			}
//		}
//		// 判断渠道关联的其他分类是否包含该游戏，若已包含，从删除列表中剔除
//		Iterator<Long> it = delGameIdSet.iterator();
//		while (it.hasNext()){
//			long gameId = it.next();
//			if (promoteTypeMapper.selectChannelGameCount(channelId, gameId)>0){
//				it.remove();
//			}
//		}
		// 同步渠道数据到sdkserver
		String errorMsg = synToSdk(channelCode, channelId, new HashSet<>(beforeGameIdList));
		if (errorMsg!=null){
			throw new RuntimeException(errorMsg);
		}
	}

	@Autowired
	private PromoteTypeMapper promoteTypeMapper;
	private RegistUserAttentEmail delChaEmailUtil = null;
	@Override
	public int delete(FirstChannel channel) {
		int line =secondChannelMapper.deleteSecondChannel(channel.getId());
		if (line==1){
			// 删除成功后，给一级渠道商发邮件
			FirstChannel firstChannel=secondChannelMapper.select(channel.getParentId(), 0);
			if (delChaEmailUtil==null){
				delChaEmailUtil = new RegistUserAttentEmail();
				delChaEmailUtil.setContent("您申请的二级渠道\n渠道名称：%s申请失败。如需要请重新申请");
				delChaEmailUtil.setTitle("二级渠道申请失败通知");
			}
			DataDictInfo dict = delChaEmailUtil.getDict();
			List<String> receiverList=new ArrayList<>();
			receiverList.add(firstChannel.getEmail());
			
			String content = String.format(delChaEmailUtil.getContent(), channel.getName());
			try {
				MailUtil.sendPureTextMail(dict.getAttrVal(), dict.getDescribe(),
						receiverList, delChaEmailUtil.getTitle(), content);
			}
			catch (Exception e) {
				logger.error("删除二级渠道发送通知邮件出错", e);
			}
		}
		return line;
	}
	
	@Value("${channel.synsdk}")
	private String synSdkUrl;
	/**
	 * 会同步两种消息
	 * 1.渠道新增的游戏 2.渠道删除的游戏
	 * 渠道审批通过、渠道商用--所有游戏都是新增
	 * 渠道暂停--所有游戏都是删除
	 * 渠道编辑、分类删除--删除分类关联的游戏，若渠道还关联有其他分类，存在同一款游戏，该游戏不删除
	 * 分类编辑--存在删除和新增游戏
	 * @return
	 */
	@Override
	public String synToSdk(String channelCode, long channelId, Set<Long> beforeGameIdSet){
//		List<Long> afterGameIdList = promoteTypeMapper.selectChannelGame(channelId);
//		Set<Long> afterGameIdSet = new HashSet<>(afterGameIdList);
//		// 求两个集合的交集
//		Set<Long> jjSet = new HashSet<>(beforeGameIdSet);
//		beforeGameIdSet.retainAll(afterGameIdSet);
//		Set<Long> addGameIdSet= new HashSet<>(afterGameIdSet);
//		addGameIdSet.removeAll(jjSet); // 删除交集后的是新增的
//		Set<Long> delGameIdSet= new HashSet<>(beforeGameIdSet);
//		delGameIdSet.removeAll(jjSet); // 删除交集后的删除的
//		/**
//		 * 三个参数
//		 * channelCode
//		 * addSet、delSet
//		 */
//		List<NameValuePair> pairs = new ArrayList<>();
//		pairs.add(new BasicNameValuePair("channelCode", channelCode));
//		pairs.add(new BasicNameValuePair("addSet", JSONArray.toJSONString(addGameIdSet)));
//		pairs.add(new BasicNameValuePair("delSet", JSONArray.toJSONString(delGameIdSet)));
//		logger.info("同步渠道数据到sdkserver，request header-> url:{}, params:{}" , synSdkUrl, JSON.toJSONString(pairs));
//		String respContent=ApiConnector.post(synSdkUrl, pairs);
//		logger.info("同步渠道数据到sdkserver, response content: " + respContent);
//		JSONObject json = JSONObject.parseObject(respContent);
//		if (json.getIntValue("responseCode")==1000){
//			return null;
//		} else {
//			logger.error("同步渠道数据到sdkserver，返回的错误信息：{}", json.getString("errorMsg"));
//		}
//		return "同步渠道数据到sdkserver失败";
		return null;
	}
}
