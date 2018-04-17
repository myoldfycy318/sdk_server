package com.dome.sdkserver.controller.collect;

import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.view.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * CollectData
 *
 * @author Zhang ShanMin
 * @date 2016/9/29
 * @time 14:22
 */
@Controller
@RequestMapping("/collect")
public class CollectData {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PropertiesUtil domainConfig;

    @RequestMapping("/playTimeRecord")
    @ResponseBody
    public AjaxResult sdkPlayTimeRecord(HttpServletRequest request) {
        try {
            String url = domainConfig.getString("bq.flume.url");
            String serviceName = "sdkPlayTime";
            Map<String, Object> paramsMap = mapConvert(request.getParameterMap());
            paramsMap.put("time", DateUtils.toDateText(new Date(),"yyyy-MM-dd HH:mm:ss"));
            //TransToFlume.postToDataSys(url, serviceName, paramsMap); --flume关闭
        } catch (Exception e) {
            logger.info("记录sdk游戏数据异常", e);
            return AjaxResult.failed();
        }
        return AjaxResult.success();
    }


    private Map<String, Object> mapConvert(Map<String, String[]> requestParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

}
