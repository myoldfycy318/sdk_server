package com.dome.sdkserver.metadata.dao.mapper.channel;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.metadata.entity.channel.JieSuanConfig;

/**
 * 结算配置
 * 渠道审批通过和修改时填写的分成比例和激活单价
 * @author lilongwei
 *
 */
public interface JieSuanConfigMapper {

	JieSuanConfig select(@Param("channelId")long channelId);
	
	int delete(@Param("id")long id);
	
	long add(@Param("js")JieSuanConfig config);
}
