package com.dome.sdkserver.metadata.dao.mapper.channel;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.dao.IBaseMapper;
import com.dome.sdkserver.metadata.entity.channel.Game;
import com.dome.sdkserver.metadata.entity.channel.PromoteType;


public interface PromoteTypeMapper extends IBaseMapper{

	long add(@Param("pt")PromoteType pt);
	
	int update(@Param("pt")PromoteType pt);
	
	PromoteType select(@Param("typeId")long typeId);
	

	List<PromoteType> selectList(@Param("typeName")String typeName, @Param("p")Paginator paginator);
	
	int selectCount(@Param("typeName")String typeName);
	
	/**
	 * 从应用列表中查询游戏列表app_type=10000000
	 * @param gameName
	 * @param paginator
	 * @return
	 */
	List<Game> selectGameList(@Param("gameName")String gameName, @Param("p")Paginator paginator);
	
	int selectGameCount(@Param("gameName")String gameName);
	
	/**
	 * 插入一条推广分类关联的游戏记录
	 * @param game
	 * @return
	 */
	long addGame(@Param("game")Game game);
	
	void batchAddGames(@Param("gameList")List<Game> gameList);
	
	/**
	 * 删除推广分类管理的游戏列表
	 * @param idList 关联的游戏记录主键id集合
	 * @return
	 */
	int deleteGames(@Param("ids")List<Long> idList);
	
	/**
	 * 修改分类关联的游戏状态  暂停设置del_flag为1，恢复设置为0
	 * @param typeId
	 * @param status
	 * @return
	 */
	int updateGameStatus(@Param("typeId")long typeId, @Param("status")int status);
	
	int deleteGameByTypeid(@Param("typeId")long typeId);
	/**
	 * 查询推广分类下的游戏列表
	 * @param typeId
	 * @return
	 */
	List<Game> selectGList(@Param("typeId")long typeId);
	
	/**
	 * 删除所有关联推广分类的渠道对应关系
	 * @param typeId
	 * @return
	 */
	int deleteChannel(@Param("typeId")long typeId);
	
	/**
	 * 删除渠道已打包的游戏记录
	 * @param typeId
	 * @param gameId
	 * @return
	 */
	int deleteChannelPkg(@Param("typeId")long typeId, @Param("gameIds")List<Long> needDelGameIds);
	
	/**
	 * 根据返回值是否>0来判断渠道是否关联某款游戏
	 * 查询渠道和某款游戏是否有关联
	 * @param channelId
	 * @param gameId
	 * @return
	 */
	int selectChannelGameCount(@Param("channelId")long channelId, @Param("gameId")long gameId);
	
	/**
	 * 查询渠道关联的所有游戏的gameId
	 * @param channelId
	 * @return
	 */
	List<Long> selectChannelGame(@Param("channelId")long channelId);
	
	/**
	 * 查询推广分类关联的渠道channelId列表
	 * @param typeId
	 * @return
	 */
	List<Long> selectChannel(@Param("typeId")long typeId);
}
