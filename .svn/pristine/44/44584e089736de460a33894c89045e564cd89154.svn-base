package com.dome.sdkserver.service.datadict;

import com.dome.sdkserver.bo.datadict.DataDictInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DataDictService
 *
 * @author Zhang ShanMin
 * @date 2016/3/29
 * @time 18:27
 */
@Service
public interface DataDictService {

    /**
     * 根据数据字典属性值获取数据字典列表
     *
     * @param list
     * @return
     */
    public List<DataDictInfo> getDataDictListByAttrCode(List<String> list);


    /**
     * 根据数据字典属性值更新数据字典
     *
     * @param dataDictInfo
     */
    public boolean updateDataDictListByAttrCode(DataDictInfo dataDictInfo);

    /**
     * 根据数据字典attrCode获取数据字典
     * @param atrrCode
     * @return
     */
    public DataDictInfo getDataDictFromCache(String atrrCode);

    /**
     *  批量更新数据字典
     * @param list
     */
    public boolean updateDataDictByBatch(List<DataDictInfo> list);

    public boolean insertDataDict(DataDictInfo dataDictInfo);
    public boolean deleteDateDictByAttrCode(String attrCode);
}
