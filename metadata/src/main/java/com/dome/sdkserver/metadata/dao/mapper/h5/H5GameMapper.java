package com.dome.sdkserver.metadata.dao.mapper.h5;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.metadata.dao.mapper.GameMapper;
import com.dome.sdkserver.metadata.entity.AbstractGame;
import com.dome.sdkserver.metadata.entity.h5.H5Game;
import org.springframework.stereotype.Repository;

@Repository
public interface H5GameMapper<T extends H5Game> extends GameMapper<AbstractGame>{

	void insertKey(@Param("t")T t);
	
	int selectKey(@Param("gameId")int gameId);
	
	int updatePayCallBackUrl(@Param("code")String code, @Param("url")String payCallBackUrl);
	
	int update (@Param("t")T t);
	
	/**
	 * 游戏名称全匹配
	 * 同一个商家冰趣和宝玩两个渠道的游戏名称可以重复
	 * @param name
	 * @return
	 */
	T selectH5ByName(@Param("name")String name, @Param("appCodePrefix")String appCodePrefix);
}
