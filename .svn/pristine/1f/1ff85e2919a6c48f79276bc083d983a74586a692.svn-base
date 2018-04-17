package com.dome.sdkserver.controller;

        import com.dome.sdkserver.util.RedisUtil;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.ResponseBody;

        import java.util.Set;

/**
 * Created by admin on 2017/9/6.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 配合开放平台,增加微信支付复选框,sdk redis清空
     * @return
     */
    @ResponseBody
    @RequestMapping("/clearKey")
    public String clearKey() {
        Set<String> set = redisUtil.keys("bqSdkserver:pay:type:game:*");
        for (String str : set) {
            System.out.println(str);
        }
        redisUtil.delPatternKey("bqSdkserver:pay:type:game:*");
        return "ok";
    }
}
