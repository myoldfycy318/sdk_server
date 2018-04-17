package com.dome.sdkserver.bo.datadict;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * DataDictInfo
 *
 * @author Zhang ShanMin
 * @date 2016/3/29
 * @time 17:52
 */
public class DataDictInfo {

    @JSONField(serialize = false)
    private Integer id;
    /**
     * 属性码
     */
    private String attrCode;
    /**
     * 属性名
     */
    private String attrName;
    /**
     * 属性值
     */
    private String attrVal;
    /**
     * 属性值描述
     */
    private String describe;
    /**
     * 备注
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrVal() {
        return attrVal;
    }

    public void setAttrVal(String attrVal) {
        this.attrVal = attrVal;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
