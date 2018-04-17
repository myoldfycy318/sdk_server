package com.dome.sdkserver.metadata.dao.mapper;


import com.dome.sdkserver.bo.CallbackAudit;
import com.dome.sdkserver.bo.DiscountThreshold;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;
import com.dome.sdkserver.metadata.entity.h5.H5Game;
import com.dome.sdkserver.metadata.entity.yeyou.YeyouGame;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CallbackAuditMapper extends IBaseMapperDao<CallbackAudit, Long> {
    public List<CallbackAudit> queryCallbackAudit(@Param("ca")CallbackAudit ca ,@Param("begin") Integer begin ,@Param("pageSize") Integer pageSize);
    public int countCallbackAudit(@Param("ca")CallbackAudit ca);
    //通过appCode查询审核通过的和还未审核的游戏 或通过id查询
    public CallbackAudit queryByIdOrAppCode(@Param("appCode")String appCode ,@Param("id")Integer id );
    public int updateStatus (Integer id);
    public int updateDelFlag (Integer id);
    public int updateByAppCode (@Param("ca")CallbackAudit ca);
    public int insert (@Param("ca")CallbackAudit ca);
    public int updateH5CallbackUrl(@Param("ca")CallbackAudit ca);

    public List<String> queryAppChannelCode(@Param("appCode")String appCode);
}
