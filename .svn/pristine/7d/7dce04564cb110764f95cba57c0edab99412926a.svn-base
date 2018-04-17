package com.dome.sdkserver.service.channel.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.dao.mapper.MerchantAppMapper;
import com.dome.sdkserver.metadata.dao.mapper.channel.PromoteTypeMapper;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;
import com.dome.sdkserver.metadata.entity.channel.Game;
import com.dome.sdkserver.metadata.entity.channel.PromoteType;
import com.dome.sdkserver.service.channel.ChannelAuditService;
import com.dome.sdkserver.service.channel.PromoteTypeService;

@Service
public class PromoteTypeServiceImpl implements PromoteTypeService {

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Resource
	private PromoteTypeMapper mapper;
	
	@Autowired
	private MerchantAppMapper merchantAppMapper; 
	@Override
	public long add(PromoteType pt) {
//		pt.setStatus(PromoteTypeStatusEnum.启用.getStatus());
		mapper.add(pt);
		long typeId = pt.getTypeId();
		addGames(pt);
		return typeId;
	}

	/**
	 * 添加推广分类关联的游戏列表
	 * @param pt
	 */
	private void addGames(PromoteType pt) {
		List<Long> gameIds=pt.getGameIdList();
		List<Game> gameList = new ArrayList<>(gameIds.size());
		Set<Long> gameIdsSet=new HashSet<>(gameIds);
		for (long gameId : gameIdsSet){
			Game g =new Game();
			g.setTypeId(pt.getTypeId());
			
			g.setGameId(gameId);
			MerchantAppInfo app = merchantAppMapper.getAppById((int)gameId);
			if (app!=null){
				g.setGameCode(app.getAppCode());
				g.setGameName(app.getAppName());
				int status = pt.getStatus();
				// 启用(1),暂停(2);
				int delFlag = (status==2 ? 1:0);
				g.setDelFlag(delFlag);
				gameList.add(g);
			}
			
		}
		if (!gameList.isEmpty())mapper.batchAddGames(gameList);
		// 创建分类时状态也可能为暂停
//		updateGameStatusMark(pt.getTypeId());
	}

	@Override
	public int update(PromoteType pt) {
		/**
		 * 先分析下哪些是新增的关联游戏，哪些是删除的游戏。已有的仍继续关联的不需要处理。新增的add，删除的delete。避免表中数据过多，直接delete掉
		 * 再更新推广分类
		 */
		long typeId=pt.getTypeId();
		List<Game> gameList =mapper.selectGList(typeId);
		/**
		 * 需要删除的游戏记录主键id集合（快速删除，比使用gameId高效）
		 */
		List<Long> needDelIds=new ArrayList<>();
		List<Long> needDelGameIds=new ArrayList<>();
		List<Long> newGameIds=pt.getGameIdList();
		List<Long> pastGameIds=new ArrayList<>(gameList.size());
		for (Game g : gameList){
			pastGameIds.add(g.getGameId());
			if (!newGameIds.contains(g.getGameId())){
				needDelIds.add(g.getId());
				needDelGameIds.add(g.getGameId());
			}
		}
		/**
		 * 需要添加的游戏ID集合
		 */
		List<Long> needAddGameIds=new ArrayList<>();
		for (long gameId :newGameIds){
			if (!pastGameIds.contains(gameId)){
				needAddGameIds.add(gameId);
			}
		}
		pt.setGameIdList(needAddGameIds);
		// 变更前的处理
		List<Long> channelIdList=mapper.selectChannel(typeId);
		Map<Long, Set<Long>> beforeChannelGameMap = queryTypeChannelMap(channelIdList);
		if (!needDelIds.isEmpty()) {
			mapper.deleteGames(needDelIds);
		}
		// 通过查询时判断渠道是否还关联游戏来处理
//		// 删除渠道已打包记录
//		if (!needDelGameIds.isEmpty()){
//			// 表中type_id没有存值
//			mapper.deleteChannelPkg(0, needDelGameIds);
//		}
		addGames(pt);
		// 删除分类后的处理
		afterHandle(channelIdList, beforeChannelGameMap);
		
		int line=mapper.update(pt);

		updateGameStatusMark(typeId);
		return line;
	}

	private void updateGameStatusMark(long typeId) {
		// 分类恢复或暂停
		/**
		 * 暂停  渠道包申请页面展示不展示，但是下载页面展示
		 * 
		 */
		PromoteType pType=mapper.select(typeId);
		int status = pType.getStatus();
		switch (status)
		{
		case 1://启用，恢复
		{
			mapper.updateGameStatus(typeId, 0);
			break;
		}
		case 2:// 暂停
		{
			mapper.updateGameStatus(typeId, 1);
			break;
		}
		}
	}

	@Override
	public PromoteType select(long typeId) {
		PromoteType pt=mapper.select(typeId);
		if (pt!=null){
			List<Game> gameList = mapper.selectGList(pt.getTypeId());
			List<Map<String, Object>> gameMapList=new ArrayList<>();
			for (Game g: gameList){
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("gameId", g.getGameId());
				m.put("gameName", g.getGameName());
				gameMapList.add(m);
			}
			pt.setGameMapList(gameMapList);
		}
		return pt;
	}

	@Autowired
	private ChannelAuditService channelAuditService;
	@Override
	public int delete(long typeId) {
		
		// 同步渠道数据到sdkserver
				/**
				 * 1.先找到分类关联的渠道列表
				 * 2.查询渠道列表中的每个渠道关联的游戏
				 * 3.删除分类
				 * 4.再查询渠道列表中的每个渠道关联的游戏
				 * 5.分析出渠道列表中的每个渠道删除的游戏和添加的游戏
				 */
		List<Long> channelIdList=mapper.selectChannel(typeId);
		Map<Long, Set<Long>> beforeChannelGameMap = queryTypeChannelMap(channelIdList);
		
		mapper.deleteGameByTypeid(typeId);
		// 渠道关联了推广分类，删除所有关联该推广分类的对应关系
		mapper.deleteChannel(typeId);
		
		// 删除分类后的处理
		afterHandle(channelIdList, beforeChannelGameMap);
		PromoteType pt = new PromoteType();
		pt.setTypeId(typeId);
		pt.setDelFlag(1);
		int line = mapper.update(pt);
		return line;
	}

	private void afterHandle(List<Long> channelIdList,
			Map<Long, Set<Long>> beforeChannelGameMap) {
		if (!CollectionUtils.isEmpty(channelIdList)){
			for (long channelId :channelIdList){
				FirstChannel firstChannel=channelAuditService.select(channelId, 0);
				if (firstChannel==null) continue;
				String errorMsg = channelAuditService.synToSdk(firstChannel.getChannelCode(), channelId,
						beforeChannelGameMap.get(channelId));
				if (errorMsg!=null){
					throw new RuntimeException(errorMsg);
				}
			}
		}
	}

	private Map<Long, Set<Long>> queryTypeChannelMap(List<Long> channelIdList) {
		Map<Long, Set<Long>> beforeChannelGameMap = new HashMap<>();
		if (!CollectionUtils.isEmpty(channelIdList)){
			for (long channelId :channelIdList){
				List<Long> gameIdList = mapper.selectChannelGame(channelId);
				beforeChannelGameMap.put(channelId, new HashSet<>(gameIdList));
			}
		}
		return beforeChannelGameMap;
	}

	@Override
	public List<PromoteType> selectList(String typeName, Paginator paginator) {
		List<PromoteType> list=mapper.selectList(typeName, paginator);
		if (!CollectionUtils.isEmpty(list)){
			// 设置分类关联的游戏列表
			for (PromoteType t: list){
				List<Game> gameList = mapper.selectGList(t.getTypeId());
				//List<Long> gameIdList =new ArrayList<>();
				List<Map<String, Object>> gameMapList=new ArrayList<>();
				for (Game g: gameList){
					//gameIdList.add(g.getGameId());
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("gameId", g.getGameId());
					m.put("gameName", g.getGameName());
					gameMapList.add(m);
				}
				t.setGameMapList(gameMapList);
			}
		}
		return list;
	}

	@Override
	public int selectCount(String typeName) {
		
		return mapper.selectCount(typeName);
	}

	@Override
	public List<Game> selectGameList(String gameName, Paginator paginator) {
		
		return mapper.selectGameList(gameName, paginator);
	}

	@Override
	public int selectGameCount(String gameName) {
		
		return mapper.selectGameCount(gameName);
	}

}
