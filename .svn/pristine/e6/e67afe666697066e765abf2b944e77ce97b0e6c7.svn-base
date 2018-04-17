package com.dome.sdkserver.service.notify.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bo.MerchantAppInfo;
import com.dome.sdkserver.bo.MerchantInfo;
import com.dome.sdkserver.bo.datadict.DataDictInfo;
import com.dome.sdkserver.constants.Constant.*;
import com.dome.sdkserver.constants.newgame.GameTypeEnum;
import com.dome.sdkserver.metadata.dao.mapper.MerchantAppMapper;
import com.dome.sdkserver.metadata.dao.mapper.MerchantInfoMapper;
import com.dome.sdkserver.metadata.dao.mapper.PkgMapper;
import com.dome.sdkserver.metadata.dao.mapper.h5.H5GameMapper;
import com.dome.sdkserver.metadata.dao.mapper.yeyou.YeyouGameMapper;
import com.dome.sdkserver.metadata.entity.AbstractGame;
import com.dome.sdkserver.metadata.entity.h5.H5Game;
import com.dome.sdkserver.metadata.entity.yeyou.YeyouGame;
import com.dome.sdkserver.service.datadict.DataDictService;
import com.dome.sdkserver.service.notify.NotifyService;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.DesEncryptUtil;
import com.dome.sdkserver.util.MailUtil;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RegexpUtil;
import com.dome.sdkserver.util.business.GameUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import java.util.*;
import java.util.regex.Pattern;

/**
 * NotifyServiceImp
 *
 * @author Zhang ShanMin
 * @date 2016/3/30
 * @time 10:56
 */

@Service
public class NotifyServiceImpl implements NotifyService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String TITLE_APPROVE_RESULT_NOTIFY ="审批结果通知";
    
    private static final String TITLE_PARTNER_APPLY_NOTIFY = "合作伙伴申请通知";
    @Resource
    private DataDictService dataDictService;
    @Resource
    private MerchantInfoMapper merchantInfoMapper;

    @Resource
    private PropertiesUtil domainConfig;
    
    @Resource
    private MerchantAppMapper merchantAppMapper;
    
    @Autowired
    private YeyouGameMapper<YeyouGame> yeyouGameMapper;
    
    @Autowired
    private H5GameMapper<H5Game> h5GameMapper;
    
    @Autowired
    private PkgMapper pkgMapper;
//    @Value("${domesdk.sendermail.passwdsecretkey}")
//    private String spkey;
    /**
     * 审批结果通知
     *
     * @param merchantAppInfo
     * @return
     */
    @SuppressWarnings("serial")
	@Override
    public boolean approveResultNotify(NotifyNodeEnum notifyNodeEnum, MerchantAppInfo merchantAppInfo) {
        try {
            Assert.notNull(merchantAppInfo, "merchantAppInfo is null");
            logger.info("approveResultNotify MerchantCode=" + merchantAppInfo.getMerchantCode());
            //获取商户信息，appCode是必传参数
            String appCode = merchantAppInfo.getAppCode();
            MerchantInfo merchantInfo = null;
            GameTypeEnum em = GameUtils.analyseGameType(appCode);
    		switch (em){
    		case yeyougame:{
    			AbstractGame game =yeyouGameMapper.select(appCode);
    			merchantAppInfo =new MerchantAppInfo();
    			merchantAppInfo.setAppCode(game.getAppCode());
    			merchantAppInfo.setAppName(game.getAppName());
    			merchantAppInfo.setRemark(game.getRemark());
    			merchantInfo = merchantInfoMapper.getMerchantInfoByUserId(game.getUserId());
    			break;
    		}
    		case h5game: {
    			AbstractGame game =h5GameMapper.select(appCode);
    			merchantAppInfo =new MerchantAppInfo();
    			merchantAppInfo.setAppCode(game.getAppCode());
    			merchantAppInfo.setAppName(game.getAppName());
    			merchantAppInfo.setRemark(game.getRemark());
    			merchantInfo = merchantInfoMapper.getMerchantInfoByUserId(game.getUserId());
    			break;
    		}
    		
    		default:{
    			merchantAppInfo = merchantAppMapper.queryApp(appCode);
    			merchantInfo = merchantInfoMapper.getMerchantInfoByCode(merchantAppInfo.getMerchantCode());
    		}
    		}
            
            if (merchantInfo == null || StringUtils.isEmpty(merchantInfo.getEmail())) {
                logger.info("approveResultNotify merchantInfo=" + JSONObject.toJSONString(merchantInfo));
                return false;
            }
            String appName = merchantAppInfo.getAppName();
            final String merchantEmail = merchantInfo.getEmail();
            //应用驳回原因
            String remark = merchantAppInfo.getRemark();
            DataDictInfo dataDictInfo = dataDictService.getDataDictFromCache(DataDictEnum.APPROVE_RESULT_NOTIFY.name());
            Map<String, Object> map = new HashMap<String, Object>();
            String notifyMsg = notifyNodeEnum.getNotityMsg();
            // 应用信息(应用名)、商户信息(邮箱、电话)
//            switch (notifyNodeEnum){
//            case SHELF_SUCCESS:{ // 上架成功第二个%s是下载链接不是驳回理由
//            	Pkg pkg =pkgMapper.queryNew(appCode);
//            	if (pkg!=null){
//            		remark=pkg.getJiaguPath();
//            	}
//            	notifyMsg = String.format(notifyMsg, appName, remark);
//            	break;
//            }
//            default:{
//            	notifyMsg = String.format(notifyMsg, appName, remark);
//            }
//            }
            notifyMsg = String.format(notifyMsg, appName, remark);
            map.put(NotifyEnum.MAIL_CONTENT.name(), notifyMsg);
            map.put(NotifyEnum.MAIL_SUBJECT.name(), TITLE_APPROVE_RESULT_NOTIFY);
            //收件人电话
            List<String> telAddrs = new ArrayList<String>() {{
                add(merchantEmail);
            }};
            map.put(NotifyEnum.RECIPIENTS_ADDR.name(), telAddrs);
            return notifyResult(dataDictInfo, map);
        } catch (Exception e) {
            logger.error("approveResultNotify error", e);
            return false;
        }
    }

    /**
     * 合作方申请通知
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
	@Override
    public boolean partnerApplyNotify(NotifyNodeEnum notifyNodeEnum, ApprovalMenuEumu approvalMenuEumn) {
        try {
            String url = domainConfig.getString("query.approval.menu.partner");
            if (StringUtils.isEmpty(url))
                return false;
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("menuName", approvalMenuEumn.getDesc()));
            String result = ApiConnector.post(url, pairs);
            logger.info("requestUrl=" + url + ",response=" + result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String data = jsonObject.getString("data");
            if (StringUtils.isEmpty(data) || Pattern.compile("^(\\[\\])$").matcher(data).matches())
                return false;
            List<Map> list = JSONArray.parseArray(data, Map.class);
            if (list == null || list.size() <= 0)
                return false;
            //收件人电话
            List<String> telAddrs = new ArrayList<String>(list.size());
            String email = null;
            for (Map map : list) {
                email = (String) map.get("email");
                if (StringUtils.isEmpty(email))
                    continue;
                telAddrs.add(email);
            }
            logger.info("telAddrs=" + JSONObject.toJSONString(telAddrs));
            DataDictInfo dataDictInfo = dataDictService.getDataDictFromCache(DataDictEnum.PARTNER_APPLY_NOTIFY.name());
            Map<String, Object> map = new HashMap<String, Object>();
            //通知信息
            String notifyMsg = notifyNodeEnum.getNotityMsg();
            map.put(NotifyEnum.MAIL_CONTENT.name(), notifyMsg);
            map.put(NotifyEnum.MAIL_SUBJECT.name(), TITLE_PARTNER_APPLY_NOTIFY);
            map.put(NotifyEnum.RECIPIENTS_ADDR.name(), telAddrs);
            return notifyResult(dataDictInfo, map);
        } catch (Exception e) {
            logger.error("approveResultNotify error", e);
            return false;
        }
    }

    /**
     * 发送通知
     *
     * @param dataDictInfo
     * @param map
     * @return
     * @throws Exception
     */
    public boolean notifyResult(DataDictInfo dataDictInfo, Map<String, Object> map) throws Exception {
        // 获取发送通知邮件信息
        DataDictInfo mailInfo = dataDictService.getDataDictFromCache(DataDictEnum.MSG_NOTIFY_MAIL.name());
        //校验邮件参数
        if (!validateMailInfo(dataDictInfo, mailInfo, map))
            return false;
        String notifyType = dataDictInfo.getAttrVal();
        //默认是短信通知
        notifyType = StringUtils.isNotBlank(notifyType) ? notifyType : NotifyType.MAIL_NOTIFY.getCode();
        // 发件人邮箱解密
        String mingwen = DesEncryptUtil.decryptPassword(mailInfo.getDescribe(), domainConfig.getString("domesdk.sendermail.passwdsecretkey"));
        mailInfo.setDescribe(mingwen);
        //短信与邮件通知
        if (NotifyType.MAIL_MSG_NOTIFY.getCode().equals(notifyType)) {
            this.sendMsgInfo(map);
            this.sendMailInfo(mailInfo, map);
        } else if (NotifyType.MAIL_NOTIFY.getCode().equals(notifyType)) {
            this.sendMailInfo(mailInfo, map);
        } else if (NotifyType.MSG_NOTIFY.getCode().equals(notifyType)) {
            this.sendMsgInfo(map);
        } else {
            logger.info("approveResultNotify fail,notifyType=" + notifyType);
            return false;
        }
        return true;
    }

    /**
     * 发送短信
     *
     * @param map
     */
    public void sendMsgInfo(Map<String, Object> map) {
        // TODO 暂不支持
    }

    /**
     * 发送邮件
     *
     * @param mailInfo
     * @param map
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public void sendMailInfo(DataDictInfo mailInfo, Map<String, Object> map) throws Exception {
        MailUtil.sendPureTextMail(mailInfo.getAttrVal(), mailInfo.getDescribe(),
                (List<String>) map.get(NotifyEnum.RECIPIENTS_ADDR.name()),
                (String) map.get(NotifyEnum.MAIL_SUBJECT.name()),
                (String) map.get(NotifyEnum.MAIL_CONTENT.name()));
    }

    /**
     * 验证邮件请求参数
     *
     * @param dataDictInfo
     * @param mailInfo
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
	public boolean validateMailInfo(DataDictInfo dataDictInfo, DataDictInfo mailInfo, Map<String, Object> map) {
        if (dataDictInfo == null || StringUtils.isBlank(dataDictInfo.getAttrVal())) {
            logger.info("approveResultNotify getDataDictFromCache is null");
            return false;
        }
        if (mailInfo == null || StringUtils.isBlank(mailInfo.getAttrVal()) || StringUtils.isBlank(mailInfo.getDescribe())) {
            logger.info("approveResultNotify getMailFromCache is null");
            return false;
        }
        if (!RegexpUtil.isMail(mailInfo.getAttrVal())) {
            logger.info("validateMailInfo fail 发送邮件格式有误,sendermail=" + mailInfo.getAttrVal());
            return false;
        }
        //过滤掉收信者邮箱格式有误的邮箱
        List<String> recipientsAddr = (List<String>) map.get(NotifyEnum.RECIPIENTS_ADDR.name());
        if (recipientsAddr == null || recipientsAddr.size() <= 0) {
            logger.info("validateMailInfo fail 收信者邮箱为空");
            return false;
        }
        ListIterator<String> listIterator = recipientsAddr.listIterator();
        String mailAddr = StringUtils.EMPTY;
        while (listIterator.hasNext()) {
            mailAddr = listIterator.next();
            if (!RegexpUtil.isMail(mailAddr))
                listIterator.remove();
        }
        map.put(NotifyEnum.RECIPIENTS_ADDR.name(), recipientsAddr);
        return true;
    }
}
