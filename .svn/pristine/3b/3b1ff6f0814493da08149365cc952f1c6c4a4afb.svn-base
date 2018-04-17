package com.dome.sdkserver.service.login;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.service.pay.qbao.bo.UserInfo;

public interface ThridRequestService {


    UserInfo loadUserInfo(long userId);

    /**
     * 钱宝用户登录密码校验
     *
     * @param qbUserId
     * @param password
     * @return
     */
    JSONObject getCasUser(String qbUserId, String password);

    JSONObject getUserByToken();

    String queryQBUserName(String userName);

    /**
     * 根据账户获取钱宝username
     *
     * @param account
     * @return
     */
    UserInfo qurUameNameByAccount(String account);


}
