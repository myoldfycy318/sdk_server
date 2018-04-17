package com.dome.sdkserver.service.impl.login;

import com.dome.sdkserver.bq.login.domain.user.User;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

/**
 * UserCorrelate
 *
 * @author Zhang ShanMin
 * @date 2017/6/13
 * @time 10:33
 */
public abstract class UserCorrelate {

    /**
     * 封装是否需要实名参数
     */
    protected void wrapIsRealName(User user, List<NameValuePair> pairs) {
        if (user.isRealName()) {
            pairs.add(new BasicNameValuePair("needCard", "true"));
            pairs.add(new BasicNameValuePair("name", user.getIdCardName()));
            pairs.add(new BasicNameValuePair("card", user.getIdCardNo()));
        } else {
            pairs.add(new BasicNameValuePair("needCard", "false"));
        }
    }
}
