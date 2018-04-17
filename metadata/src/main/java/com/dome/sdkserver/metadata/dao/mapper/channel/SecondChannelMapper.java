package com.dome.sdkserver.metadata.dao.mapper.channel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dome.sdkserver.bo.Paginator;
import com.dome.sdkserver.metadata.dao.IBaseMapper;
import com.dome.sdkserver.metadata.entity.channel.Channel;
import com.dome.sdkserver.metadata.entity.channel.ChannelQueryParam;
import com.dome.sdkserver.metadata.entity.channel.ChannelType;
import com.dome.sdkserver.metadata.entity.channel.FirstChannel;
import com.dome.sdkserver.metadata.entity.channel.SecondChannel;

public interface SecondChannelMapper extends IBaseMapper {
	
	long add(@Param("ch")SecondChannel secondChannel);
	
	FirstChannel select(@Param("id")long id, @Param("userId")long userId);
	
	FirstChannel selectByCode(@Param("code")String channelCode);
	
	int updateStatus(@Param("id")long id, @Param("status")int status);
	
	int update(@Param("ch")SecondChannel secondChannel);
	
	int selectCount(@Param("parentId")long parentId, @Param("ch")ChannelQueryParam param);
	/**
	 * 查询一级渠道下面的所有二级渠道
	 * 若指定parentId 查询一级渠道下的所有二级渠道，需要拼上一级渠道
	 * @param secondChannel
	 * @return
	 */
	List<FirstChannel> selectList(@Param("parentId")long parentId, @Param("ch")ChannelQueryParam param,
			@Param("p")Paginator paginator);
	
	/**
	 * 审批页面查询渠道列表
	 * 根据渠道名称模糊查询、联系人、手机号、状态、申请时间过滤。以更新时间降序排列
	 * @param channelQueryParam
	 * @return
	 */
	List<FirstChannel> selectAuditList(@Param("ch")ChannelQueryParam channelQueryParam,
			@Param("p")Paginator paginator);
	
	/**
	 * 根据查询条件过滤获取渠道记录数，使用于分页记录总数
	 * @param channelQueryParam
	 * @return
	 */
	int selectChannelsCount(@Param("ch")ChannelQueryParam channelQueryParam);
	/**
	 * 插入一条渠道数据后，更新渠道编码。
	 * 仅二级渠道需要更改，一级渠道在插入时会带上渠道编码
	 * @param id
	 * @param channelCode
	 * @return
	 */
	int updateChannelCode(@Param("id")long id, @Param("channelCode")String channelCode);
	
	/**
	 * 查询一级渠道下的二级渠道数量，del_flag=1不统计
	 * @param firstChannelId
	 * @return
	 */
	long selectSecondChannelCount(@Param("parentId")long firstChannelId);
	
	/**
	 * 查询渠道关联的所有推广分类
	 * @param channelId
	 * @return
	 */
	List<ChannelType> selectChannelTypeList(@Param("channelId")long channelId);
	
	/**
	 * 批量添加渠道关联推广记录数据
	 * @param cts
	 * @return
	 */
	int batchAddChannelType(@Param("channelId")long channelId, @Param("typeIds")List<Long> typeIdList);
	
	/**
	 * 删除渠道关联的推广分类
	 * @param ids 主键id集合
	 * @return
	 */
	int deleteAddChannelType(@Param("ids")List<Long> ids);
	
	/**
	 * 合作方式单独备份一张变更表
	 */
	void addCooperType(@Param("channelId")long channelId, @Param("cooperType")int cooperType);
	
	Channel selectCooperType(@Param("channelId")long channelId);
	int deleteCooperType(@Param("id")long id);
	
	/**
	 * 根据渠道名查询渠道。不支持模糊查询
	 * @param name
	 * @return
	 */
	Channel selectByName(String name);
	
	/**
	 * 更新二级渠道关联的user_id
	 * @param id
	 * @param userId
	 * @return
	 */
	int updateSecondUserId(@Param("id")long id, @Param("userId")long userId);
	
	/**
	 * 删除二级渠道
	 * @param id
	 * @return
	 */
	int deleteSecondChannel(@Param("id")long id);
	
	int checkEmailUse(@Param("id")long id, @Param("email")String email);
}
