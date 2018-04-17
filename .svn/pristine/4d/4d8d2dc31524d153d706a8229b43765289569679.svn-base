package com.dome.sdkserver.service.datadict.impl;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.datadict.DataDictInfo;
import com.dome.sdkserver.constants.Constant;
import com.dome.sdkserver.constants.DomeSdkRedisKey;
import com.dome.sdkserver.metadata.dao.mapper.datadictmapper.DatadictMapper;
import com.dome.sdkserver.service.datadict.DataDictService;
import com.dome.sdkserver.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * DataDictServiceImpl
 *
 * @author Zhang ShanMin
 * @date 2016/3/29
 * @time 18:28
 */
@Service
public class DataDictServiceImpl implements DataDictService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DatadictMapper datadictMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<DataDictInfo> getDataDictListByAttrCode(List<String> list) {
        List<DataDictInfo> dataDictInfos = new ArrayList<DataDictInfo>();
        if (list == null || list.size() <= 0) {
            return dataDictInfos;
        }
        logger.info("getDataDictListByAttrCode_list=" + JSONObject.toJSONString(list));
        dataDictInfos.addAll(datadictMapper.getDataDictListByAttrCode(list));
        return dataDictInfos;
    }

    @Override
    public boolean updateDataDictListByAttrCode(DataDictInfo dataDictInfo) {
        if (dataDictInfo == null || StringUtils.isBlank(dataDictInfo.getAttrCode()))
            return false;
        logger.info("updateDataDictListByAttrCode=" + JSONObject.toJSONString(dataDictInfo));
        datadictMapper.updateDataDictListByAttrCode(dataDictInfo);
        return true;
    }

    @Override
	public boolean insertDataDict(DataDictInfo dataDictInfo) {
    	if(dataDictInfo == null ){
    		return false;
    	}
    	logger.info("insertDataDict=" + JSONObject.toJSONString(dataDictInfo));
    	datadictMapper.insertDataDict(dataDictInfo);
		return true;
	}
    @Override
	public boolean deleteDateDictByAttrCode(String attrCode) {
    	datadictMapper.deleteDataDicByAttrCode(attrCode);
		return true;
	}
    @Override
    public DataDictInfo getDataDictFromCache(final String atrrCode) {
        String key = DomeSdkRedisKey.DATA_DICT_PREFIX + atrrCode;
        String value = redisUtil.get(key);
        DataDictInfo dataDictInfo = null;
        if (StringUtils.isEmpty(value) || "null".equals(value)) {
            List<DataDictInfo> list = datadictMapper.getDataDictListByAttrCode(new ArrayList<String>() {
                {
                    add(atrrCode);
                }
            });
            if (list == null || list.size() <= 0)
                return null;
            dataDictInfo =list.get(0);
            redisUtil.setex(key, 24 * 60 * 60, JSONObject.toJSONString(dataDictInfo));
        } else {
            dataDictInfo = JSONObject.parseObject(value, DataDictInfo.class);
        }
        return dataDictInfo;
    }

    /**
     * 批量更新数据字典
     *
     * @param list
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDataDictByBatch(List<DataDictInfo> list) {
        if (list == null || list.size() <= 0)
            return false;
        String key = "";
        for (DataDictInfo dataDictInfo : list) {
            datadictMapper.updateDataDictListByAttrCode(dataDictInfo);
            if (isNeedInsertCache(dataDictInfo.getAttrCode())) {
                key = DomeSdkRedisKey.DATA_DICT_PREFIX + dataDictInfo.getAttrCode();
                redisUtil.setex(key, 24 * 60 * 60, JSONObject.toJSONString(dataDictInfo));
            }
        }
        return true;
    }

    /**
     * 判断属性值是否需要存入缓存
     *
     * @param attrCode
     * @return
     */
    public boolean isNeedInsertCache(String attrCode) {
        //需要添加到缓存的属性code
        List<String> needinsertCache = new ArrayList<String>() {
            {
                add(Constant.DataDictEnum.APPROVE_RESULT_NOTIFY.name());
                add(Constant.DataDictEnum.PARTNER_APPLY_NOTIFY.name());
                add(Constant.DataDictEnum.MSG_NOTIFY_MAIL.name());
            }
        };
        return needinsertCache.contains(attrCode);
    }
}
