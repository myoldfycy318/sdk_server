package com.dome.sdkserver.service.channel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.entity.channel.Game;
import com.dome.sdkserver.metadata.entity.channel.PromoteType;

public interface PromoteTypeService {
	long add(PromoteType pt);
	
	int update(PromoteType pt);
	
	PromoteType select(long typeId);
	
	List<PromoteType> selectList(String typeName, Paginator paginator);
	
	int selectCount(String typeName);
	
	int delete(long typeId);
	
	List<Game> selectGameList(String gameName, Paginator paginator);
	
	int selectGameCount(String gameName);
}
