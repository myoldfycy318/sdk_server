package com.dome.sdkserver.controller.notify;

import com.dome.sdkserver.bo.datadict.DataDictInfo;
import com.dome.sdkserver.constants.Constant;
import com.dome.sdkserver.service.datadict.DataDictService;
import com.dome.sdkserver.service.notify.NotifyService;
import com.dome.sdkserver.util.DesEncryptUtil;
import com.dome.sdkserver.view.AjaxResult;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * NotifyController
 *
 * @author Zhang ShanMin
 * @date 2016/3/29
 * @time 15:28
 */
@Controller
@RequestMapping("/notify")
public class NotifyController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DataDictService dataDictService;
    @Resource
    private NotifyService notifyService;

    @Value("${domesdk.sendermail.passwdsecretkey}")
    private String spkey;
    
    @SuppressWarnings("serial")
	@RequestMapping("/list")
    @ResponseBody
    public AjaxResult getNotifyInfo() {
        List<String> list = new ArrayList<String>() {{
            add(Constant.DataDictEnum.APPROVE_RESULT_NOTIFY.name());
            add(Constant.DataDictEnum.PARTNER_APPLY_NOTIFY.name());
            add(Constant.DataDictEnum.MSG_NOTIFY_MAIL.name());
        }};
        List<DataDictInfo> dataDictInfos = dataDictService.getDataDictListByAttrCode(list);
        ListIterator<DataDictInfo> it = dataDictInfos.listIterator();
        DataDictInfo dataDictInfo = null;
        while (it.hasNext()) {
            dataDictInfo = it.next();
            if (Constant.DataDictEnum.MSG_NOTIFY_MAIL.name().equals(dataDictInfo.getAttrCode()))
                dataDictInfo.setDescribe("******");
        }
        return AjaxResult.success(dataDictInfos);
    }

    @RequestMapping("/edit")
    @ResponseBody
    public AjaxResult updateDataDictInfo(HttpServletRequest request,
                                         @RequestParam(value = "attrCodes") String attrCodes,
                                         @RequestParam(value = "attrVals") String attrVals) {
        try {
            String[] atrrCodeArr = attrCodes.split(",");
            String[] attrValArr = attrVals.split(",");

            int size = Math.min(atrrCodeArr.length, attrValArr.length);
            List<DataDictInfo> list = new ArrayList<DataDictInfo>(size);
            DataDictInfo dataDictInfo = null;
            String attrCode = StringUtils.EMPTY;
            for (int i = 0; i < size; i++) {
                dataDictInfo = new DataDictInfo();
                attrCode = atrrCodeArr[i];
                dataDictInfo.setAttrCode(attrCode);
                dataDictInfo.setAttrVal(attrValArr[i]);
                if (Constant.DataDictEnum.MSG_NOTIFY_MAIL.name().equals(attrCode) && null != getRequestParam(request, "describe")) {
                    //dataDictInfo.setDescribe(getRequestParam(request, "describe").toString());
                    // 保存时对发件人邮箱密码加密
                    
                    dataDictInfo.setDescribe(DesEncryptUtil.encryptPassword(getRequestParam(request, "describe").toString(), spkey));
                }
                list.add(dataDictInfo);
            }
            return dataDictService.updateDataDictByBatch(list) ? AjaxResult.success(true) : AjaxResult.failed();
        } catch (Exception e) {
            logger.error("updateDataDictInfo.error", e);
            return AjaxResult.failed();
        }
    }

    /**
     * 获取请求参数值
     *
     * @param request
     * @param param
     * @return
     */
    public Object getRequestParam(HttpServletRequest request, String param) {
        return request.getParameter(param) != null ? request.getParameter(param) : request.getAttribute(param);
    }

}
