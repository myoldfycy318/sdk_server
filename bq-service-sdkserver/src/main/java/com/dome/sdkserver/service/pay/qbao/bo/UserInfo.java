package com.dome.sdkserver.service.pay.qbao.bo;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = -8065665447875404466L;

    private long id;
    private String username;
    private long hyipUserId;
    private String nickName;
    //0冻结用户，1正常用户
    private Integer enabled;
    private String email;
    private String md5PresenterCode;
    private String mobile;
    private String userId;
    private Date createTime;
    //推荐号
    private String presenterCode;
    //推荐人
    private String presenterId;
    //第三方openId
    private String openId;
    //第三方标识
    private String thirdId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getHyipUserId() {
        return hyipUserId;
    }

    public void setHyipUserId(long hyipUserId) {
        this.hyipUserId = hyipUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMd5PresenterCode() {
        return md5PresenterCode;
    }

    public void setMd5PresenterCode(String md5PresenterCode) {
        this.md5PresenterCode = md5PresenterCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPresenterCode() {
        return presenterCode;
    }

    public void setPresenterCode(String presenterCode) {
        this.presenterCode = presenterCode;
    }

    public String getPresenterId() {
        return presenterId;
    }

    public void setPresenterId(String presenterId) {
        this.presenterId = presenterId;
    }
}
