package com.dome.sdkserver.metadata.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.SearchMerchantInfoBo;
import com.dome.sdkserver.metadata.dao.IBaseMapperDao;

/**
 * 商户信息Mapper
 * @author hexiaoyi
 *
 */
@Repository
public interface MerchantInfoMapper extends IBaseMapperDao<MerchantInfo, Long> {
	
	/**
	 * 新增商户信息
	 * @param merchantInfo
	 */
	void addMerchantInfo(@Param("entity")MerchantInfo merchantInfo);
	
	/**
	 * 编辑商户信息
	 * @param merchantInfo
	 */
	void editMerchantInfo(@Param("entity")MerchantInfo merchantInfo);
	
	/**
	 * 根据用户id查询商户
	 * @param userId 会员系统用户ID
	 * @return
	 */
	MerchantInfo getMerchantInfoByUserId(@Param("userId")Integer userId);
	
	/**
	 * 根据商户编码查询商户
	 * @param merchantCode
	 * @return
	 */
	MerchantInfo getMerchantInfoByCode(@Param("merchantCode")String merchantCode);
	
	/**
	 * 根据条件查询商户信息
	 * @param searchMerchantInfoBo
	 * @return
	 */
	List<MerchantInfo> getMerchantInfoListByCondition(@Param("entity")SearchMerchantInfoBo searchMerchantInfoBo);
	
	/**
	 * 获取总记录数
	 * @param searchMerchantInfoBo
	 * @return
	 */
	int getCountByCondition(@Param("entity")SearchMerchantInfoBo searchMerchantInfoBo);
	
	/**
	 * 根据商户编码修改商户信息
	 * @param merchantInfo
	 */
	void editMerchantInfoByCode(@Param("entity")MerchantInfo merchantInfo);
	
	void updateMerchantCode(@Param("merchantId")int merchantId, @Param("merchantCode")String merchantCode);
	
	/**
	 * 线上联调环境和测试环境会执行先删除再插入
	 * @param merchantCode
	 */
	void deleteMerchant(@Param("merchantCode")String merchantCode);
}
