package com.dome.sdkserver.metadata.dao.mapper.datadictmapper;

import java.util.List;
import com.dome.sdkserver.bo.datadict.DataDictInfo;
import com.dome.sdkserver.metadata.dao.IBaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DatadictMapper
 *
 * @author Zhang ShanMin
 * @date 2016/3/29
 * @time 17:34
 */
@Repository
public interface DatadictMapper extends IBaseMapper {


    /**
     * 根据数据字典属性值获取数据字典列表
     * @param list
     * @return
     */
    public List<DataDictInfo> getDataDictListByAttrCode(List<String> list);


    /**
     * 根据数据字典属性值更新数据字典
     * @param dataDictInfo
     */
    public void updateDataDictListByAttrCode(DataDictInfo dataDictInfo);


    /**
     *  批量更新数据字典
     * @param list
     */
    public void updateDataDictByBatch(List<DataDictInfo> list);


    public void insertDataDict(DataDictInfo dataDictInfo);
    public void deleteDataDicByAttrCode(String attrCode);
}
