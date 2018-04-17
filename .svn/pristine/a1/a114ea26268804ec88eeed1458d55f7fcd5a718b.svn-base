package com.dome.sdkserver.metadata.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.bo.pkgmanage.Pkg;
import com.dome.sdkserver.metadata.entity.channel.AppPkg;
import com.dome.sdkserver.metadata.entity.channel.BqApp;
import com.dome.sdkserver.metadata.entity.channel.Game;

public interface ChannelPkgMapper {

	int selectGameCount(@Param("gameName")String gameName, @Param("channelId")long channelId);
	
	List<Game> selectGameList(@Param("gameName")String gameName, @Param("channelId")long channelId,
			@Param("p")Paginator paginator);
	
	/**
	 * 只展示打包已完成的，即status为已打包的
	 * @param gameName
	 * @param channelId
	 * @return
	 */
	int selectDlGameCount(@Param("gameName")String gameName, @Param("channelId")long channelId);
	
	
	List<Game> selectDlGameList(@Param("gameName")String gameName, @Param("channelId")long channelId,
			@Param("p")Paginator paginator);
	
	long addAppPkg(@Param("p")AppPkg pkg);
	
	/**
	 * 根据渠道ID查询应用渠道包信息
	 * 目前只有一个冰趣app包，因此一个渠道一条记录
	 * @param channelId
	 * @return
	 */
	AppPkg selectAppPkg(@Param("channelId")long channelId, @Param("appId")long appId);
	
	/**
	 * 根据pid(表主键）查询应用渠道包信息
	 * @param pid
	 * @return
	 */
	AppPkg selectAppPkgByPid(@Param("pid")long pid);
	
	/**
	 * 更新时根据pid，即表主键来查询的
	 * @param pkg
	 * @return
	 */
	int updateAppPkg(@Param("p")AppPkg pkg);
	
	int selectAppPkgCount(@Param("channelId")long channelId,
			@Param("channelName")String channelName);
	
	List<AppPkg> selectAppPkgList(@Param("channelId")long channelId,
			@Param("channelName")String channelName, @Param("p")Paginator paginator);
	
	/**
	 * 查询应用
	 * 应用为上架状态才会被查询到，否则返回null；返回值中包含有应用的更新时间
	 * @param appId 应用ID，应用表主键
	 * @return
	 */
	MerchantAppInfo selectApp(@Param("appId")long appId);
	
	/**
	 * 查询应用最后一次上传的包
	 * 返回值中包含有包的上传时间
	 * @param appCode
	 * @return
	 */
	Pkg selectPkg(@Param("appCode")String appCode);

	
	long addBqApp(@Param("a")BqApp app);
	
	int deleteBqApp(@Param("id")long id);
	
	BqApp selectBqApp(@Param("appId")long appId);
	
	/**
	 * 查询渠道的某款游戏渠道包信息
	 * @param channelId
	 * @param gameId
	 * @return
	 */
	Game selectGamePkg(@Param("channelId")long channelId, @Param("gameId")long gameId);
	
	/**
	 * 渠道包游戏申请打包后创建一条记录
	 * @param game
	 * @return
	 */
	long addGamePkg(@Param("g")Game game);
	
	int updateGamePkg(@Param("g")Game game);
	
	/**
	 * 删除渠道包游戏记录
	 * @param id
	 * @return
	 */
	int deleteGamePkg(@Param("id")long id);
	/**
	 * 更新渠道游戏包状态，对应表domesdk_channelpkg_download
	 * @param channelId
	 * @param gameId
	 * @param status
	 * @return
	 */
	int updateGamePkgStatus(@Param("channelId")long channelId, @Param("gameId")long gameId,
			@Param("status")int status);
	
	/**
	 * 更新渠道游戏包状态
	 * @param id 表主键id
	 * @param status
	 * @return
	 */
//	int updateGamePkgStatus(@Param("id")long id, @Param("status")int status);
	
	/**
	 * 查询渠道某款游戏可能存在的推广分类
	 * @param channelId
	 * @param gameId
	 * @return
	 */
	List<Long> selectChannelTypeId(@Param("channelId")long channelId, @Param("gameId")long gameId); 
}
