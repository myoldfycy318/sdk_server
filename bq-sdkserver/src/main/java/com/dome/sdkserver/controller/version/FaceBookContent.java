package com.dome.sdkserver.controller.version;

import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.view.SdkOauthResult;
import com.dome.sdkserver.controller.BaseController;
import com.dome.sdkserver.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuekuan on 2017/4/14.
 */
@Controller
@RequestMapping("faceBook")
public class FaceBookContent extends BaseController {
    @Autowired
    protected PropertiesUtil switchConfig;

    @RequestMapping("content")
    @ResponseBody
    public SdkOauthResult content(HttpServletResponse response, HttpServletRequest request) {
        try {
            Map<String, String> map = new HashMap<>();
            String title = new String(switchConfig.getString("title").getBytes("ISO-8859-1"), "utf-8");
            String description = new String(switchConfig.getString("description").getBytes("ISO-8859-1"), "utf-8");
            String icon = new String(switchConfig.getString("icon").getBytes("ISO-8859-1"), "utf-8");
            String googleGood = new String(switchConfig.getString("googleGood").getBytes("ISO-8859-1"), "utf-8");
            map.put("title", title);
            map.put("description", description);
            map.put("icon", icon);
            map.put("googleGood", googleGood);
            log.info("解析得到的FaceBook标题、内容、图片：{}", JSONObject.toJSONString(map));
            return SdkOauthResult.success(map);
        } catch (Exception e) {
            log.info("解析FaceBook标题、内容、图片出错", e);
        }
        return null;
    }

}
