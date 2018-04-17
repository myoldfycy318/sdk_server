package com.dome.sdkserver.service.impl.award;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dome.sdkserver.bq.constants.SendBqStatusEnum;
import com.dome.sdkserver.bq.util.DateUtils;
import com.dome.sdkserver.bq.util.GenOrderCode;
import com.dome.sdkserver.bq.util.PriceUtil;
import com.dome.sdkserver.metadata.dao.mapper.bq.award.AutoSendBqMapper;
import com.dome.sdkserver.metadata.entity.bq.award.BqAutoSendEntity;
import com.dome.sdkserver.metadata.entity.bq.award.PubMessageReqVo;
import com.dome.sdkserver.metadata.entity.bq.award.StoreAwardConfEntity;
import com.dome.sdkserver.service.award.StoreAwardConfService;
import com.dome.sdkserver.shangsu.PersonTraceResp;
import com.dome.sdkserver.shangsu.PersonTradeReqInfo;
import com.dome.sdkserver.shangsu.TradeItem;
import com.dome.sdkserver.util.ApiConnector;
import com.dome.sdkserver.util.DateUtil;
import com.dome.sdkserver.util.PropertiesUtil;
import com.dome.sdkserver.util.RedisUtil;
import com.dome.sdkserver.util.shangsu.YanSuCommonUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * AutoSendBq
 *
 * @author Zhang ShanMin
 * @date 2016/8/5
 * @time 16:35
 */
public abstract class AutoSendBq {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    public static final int BIZ_TYPE = 63; // 宝券系统业务类型
    public static final String QBAO_PAY = "1";//支付方式 1 钱宝
    public static final String ALI_PAY = "2";//支付方式 2 支付宝
    @Resource
    StoreAwardConfService storeAwardConfService;
    @Resource
    AutoSendBqMapper autoSendBqMapper;
    @Resource
    PropertiesUtil domainConfig;
    @Resource
    RedisUtil redisUtil;


    /**
     * 处理自动发布宝劵逻辑
     *
     * @param appRuleMap
     * @param entityList
     */
    protected void handleSendBq(Map<String, Object> appRuleMap, List<BqAutoSendEntity> entityList) {
        Set<String> appCodeSet = new HashSet<String>();
        for (BqAutoSendEntity bqAutoSendEntity : entityList) {
            appCodeSet.add(bqAutoSendEntity.getAppCode());
        }
        // 将所有游戏的消费流水区分,并保存到map中,map的key为游戏appcode
        Map<String, List<BqAutoSendEntity>> gameMap = new HashMap<String, List<BqAutoSendEntity>>();
        for (BqAutoSendEntity bqAutoSendEntity : entityList) {
            Iterator<String> it = appCodeSet.iterator();
            while (it.hasNext()) {
                if (bqAutoSendEntity.getAppCode().equals(it.next())) {
                    List<BqAutoSendEntity> list = gameMap.get(bqAutoSendEntity.getAppCode());
                    if (list == null) {
                        list = new ArrayList<BqAutoSendEntity>();
                    }
                    list.add(bqAutoSendEntity);
                    gameMap.put(bqAutoSendEntity.getAppCode(), list);
                }
            }
        }

        Iterator<String> it = gameMap.keySet().iterator();
        while (it.hasNext()) {
            String appCode = it.next();
            // 获取应用赠券规则,已按会员等级由高到低排序,非会员活动排最后
            List<StoreAwardConfEntity> awardList = getRule(appRuleMap, appCode);
            if (awardList == null) {
                continue;// 该游戏无活动,直接跳过
            }
            List<BqAutoSendEntity> list = gameMap.get(appCode);
            for (BqAutoSendEntity bqAutoSendEntity : list) {
                String userId = bqAutoSendEntity.getPayUserId();
                // 检查同一游戏同一用户,是否发过奖励
                boolean flag = redisUtil.hexists(getHasSendBqUserKey(), userId + appCode);
                if (flag) {
                    log.info("用户" + userId + "在" + appCode + "已发放过宝券奖励");
                    continue;// 如果这个用户,在这个游戏已经发过,就跳过
                }
                // 获取昨天用户是否是会员
                String memberLevel = queryYesterdayVipFromStore(userId);
                for (StoreAwardConfEntity storeAwardConfEntity : awardList) {
                    if (StringUtils.isEmpty(memberLevel)) {
                        // 用户非会员
                        if (storeAwardConfEntity.getIdMember() == 0) {
                            if (isSendBq(bqAutoSendEntity, storeAwardConfEntity)) {
                                // 发奖
                                doSendBq(bqAutoSendEntity, storeAwardConfEntity);
                                break;
                            }
                        }
                    } else {
                        Integer awardMemberLevelId = storeAwardConfEntity.getMemberLevelId();
                        // 用户是会员
                        log.info("用户:" + userId + "游戏:" + appCode + "||" + storeAwardConfEntity.getIdMember() + "||" + awardMemberLevelId + "||" + memberLevel);
                        if (storeAwardConfEntity.getIdMember() == 0 || (storeAwardConfEntity.getIdMember() == 1 && awardMemberLevelId != null && Integer.valueOf(memberLevel) >= awardMemberLevelId)) {
                            if (isSendBq(bqAutoSendEntity, storeAwardConfEntity)) {
                                // 发奖
                                doSendBq(bqAutoSendEntity, storeAwardConfEntity);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 应用规则Map中获取相应的规则
     *
     * @param appRuleMap
     * @param appCode
     * @return
     */
    protected List<StoreAwardConfEntity> getRule(Map<String, Object> appRuleMap, String appCode) {
        Object ruleObj = appRuleMap.get(appCode);
        List<StoreAwardConfEntity> list = null;
        if (ruleObj == null) { // Map中不存在，则补偿
            String transDate = DateUtil.getCurLastDay().replaceAll("-", "");
            list = storeAwardConfService.queryConf(transDate, appCode, getPayType());//支付方式:1.钱宝,2.支付宝
            appRuleMap.put(appCode, list);
        } else {
            list = (List<StoreAwardConfEntity>) ruleObj;
        }
        // 按vip等级由高到低进行排序
        Comparator<StoreAwardConfEntity> comparator = new Comparator<StoreAwardConfEntity>() {
            public int compare(StoreAwardConfEntity s1, StoreAwardConfEntity s2) {
                if (s1.getMemberLevelId() == null && s2.getMemberLevelId() == null) {
                    return 0;
                } else if (s1.getMemberLevelId() == null && s2.getMemberLevelId() != null) {
                    return 1;
                } else if (s1.getMemberLevelId() != null && s2.getMemberLevelId() == null) {
                    return -1;
                } else if (s1.getMemberLevelId() > s2.getMemberLevelId()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        if (list != null) {
            Collections.sort(list, comparator);
        }
        return list;
    }

    /**
     * 查询昨日应用市场会员等级
     *
     * @param payUserId
     */
    protected String queryYesterdayVipFromStore(String payUserId) {
        try {
            List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            paramsList.add(new BasicNameValuePair("userId", String.valueOf(payUserId)));
            paramsList.add(new BasicNameValuePair("queryDate", DateUtil.getCurDate()));
            String vipLevelUrl = domainConfig.getString("store.yesterday.viplevel.url");
            String responseBody = ApiConnector.post(vipLevelUrl, paramsList);
            log.info("查询用户{}昨日会员等级返回信息:{}", payUserId, responseBody);
            if (StringUtils.isNotEmpty(responseBody)) {
                JSONObject jObj = JSONObject.parseObject(responseBody);
                if (jObj.getInteger("responseCode") == 1000) {
                    JSONObject dataObj = jObj.getJSONObject("data");
                    if (dataObj != null) {
                        String memberLevel = dataObj.getString("memberLevel");
                        return memberLevel;
                    }
                }
            }
        } catch (Exception e) {
            log.info("{}昨日会员等级信息异常:{}", payUserId, e);
        }
        return null;
    }


    /**
     * 发宝券,并记录
     *
     * @param @param entity
     * @param @param awardConf
     * @return void    返回类型
     * @throws
     */
    protected void doSendBq(BqAutoSendEntity entity, StoreAwardConfEntity awardConf) {
        BigDecimal num = awardConf.getBqAward();//返劵数量
        BigDecimal costSum = entity.getAccountAmount();//消费金额
        //发送宝券结果
        boolean result = false;
        // 生成主键id，若id存在，则重新生成
        String id = GenOrderCode.next();
        entity.setId(id); // 生成唯一主键
        entity.setStatus(SendBqStatusEnum.AUTO_SEND_NO.getStatus());
        entity.setBqAward(num);
        entity.setBizType(BIZ_TYPE);
        //设置应用市场配置的活动配置Id
        if (StringUtils.isNotBlank(awardConf.getStoreConfId()) && awardConf.getStoreConfId().matches("\\d+")){
            entity.setActivityConfId(Integer.valueOf(awardConf.getStoreConfId()));
        }
        // 符合赠送宝券条件 同步宝券系统
        if (num.compareTo(new BigDecimal(0)) == 1) {
            // 业务摘要
            StringBuffer bizDesc = new StringBuffer();
            bizDesc.append("<").append(entity.getAppName()).append(">消费").append(PriceUtil.formatDecimal(costSum)).append("元赠送").append(num.intValue()).append("钱宝券");
            entity.setBizDesc(bizDesc.toString());
            result = rechargeFanquan(entity);//返劵
            bizDesc.append("(活动:").append(awardConf.getActivityName()).append(")");
            entity.setBizDesc(bizDesc.toString());
        }
        try {
            if (result) {
                // 储存到redis当前游戏当前用户只发放一次满足活动的奖励,避免重复发放
                redisUtil.hset(getHasSendBqUserKey(), entity.getPayUserId() + entity.getAppCode(), "sended");
                redisUtil.expire(getHasSendBqUserKey(), 3600 * 6);
                PubIMMessage(entity, num);//发送IM消息通知用户
            }
            // 插入数据库
            if (num.compareTo(new BigDecimal(0)) == 1) {
                saveReturnTickets(entity);
            }
        } catch (Exception e) {
            log.error("插入数据失败", e);
        }
    }

    /**
     * 保存返劵记录
     *
     * @param entity
     */
    protected abstract void saveReturnTickets(BqAutoSendEntity entity);

    /**
     * 获取支付方式:1.钱宝,2.支付宝
     * @return
     */
    protected abstract String getPayType();

    /**
     * 获取已发宝劵redis key
     * @return
     */
    protected abstract String getHasSendBqUserKey();


    /**
     * 会员充值返券（P090301)
     */
    protected boolean rechargeFanquan(BqAutoSendEntity entity) {
        log.info("同步数据至商肃支付 start..");
        boolean success = false;
        try {
            PersonTradeReqInfo tradeReqInfo = new PersonTradeReqInfo();
            tradeReqInfo.setSignType("RSA");
            tradeReqInfo.setInputCharset("UTF-8");
            tradeReqInfo.setGroupCode("G009");
            tradeReqInfo.setUserId(entity.getPayUserId());
            tradeReqInfo.setBusiType("P090301"); //会员充值返券
            tradeReqInfo.setTradeTime(DateUtils.getCurDateFormatStr("yyyyMMddHHmmss"));
            tradeReqInfo.setOutTradeNo(entity.getId());
            tradeReqInfo.setFeeAmount(0L);
            tradeReqInfo.setTradeDesc(Base64.encodeBase64URLSafeString(entity.getBizDesc().getBytes("UTF-8")));
            tradeReqInfo.setTerminal("PC");

            List<TradeItem> list = new ArrayList<TradeItem>();
            TradeItem tradeItem1 = new TradeItem();
            tradeItem1.setItemTradeNo(entity.getId());
            tradeItem1.setPayType("2");// 宝劵
            tradeItem1.setAmount(entity.getBqAward().longValue());
            tradeItem1.setItemBusiType("P090301");
            list.add(tradeItem1);
            tradeReqInfo.setTradeItems(list);
            log.info("自动发放宝券调商肃支付参数:{}", JSON.toJSON(tradeReqInfo));
            String requestUrl = domainConfig.getString("shangsu.personal.business");
            String rsaPrivateKey = domainConfig.getString("shangsu.rsa.private.key");
            PersonTraceResp personTraceResp = YanSuCommonUtil.personalBusiness(tradeReqInfo, requestUrl, rsaPrivateKey);
            log.info("自动发放宝券调商肃支付结果:{}" + JSON.toJSONString(personTraceResp));
            if (personTraceResp == null) {
                entity.setStatus(SendBqStatusEnum.AUTO_SEND_FAIL.getStatus());
                return false;
            }
            if ("100000".equals(personTraceResp.getRespCode())) {
                entity.setStatus(SendBqStatusEnum.AUTO_SEND_SUCCESS.getStatus());
                success = true;
            } else {
                entity.setStatus(SendBqStatusEnum.AUTO_SEND_FAIL.getStatus());
            }
            entity.setReturnCode(personTraceResp.getRespCode());
            entity.setReturnMsg(personTraceResp.getRespMsg());
            entity.setPayTradeNo(personTraceResp.getPayTradeNo());
        } catch (Exception e) {
            entity.setStatus(SendBqStatusEnum.AUTO_SEND_ERROR.getStatus());
            log.error("payUserId:{},调用商肃送宝劵异常:{}", entity.getPayUserId(), e);
        }
        log.info("同步数据至宝券系统  end..");
        return success;
    }


    protected void PubIMMessage(BqAutoSendEntity entity, BigDecimal num) {
        //发送IM消息通知用户
        PubMessageReqVo pubMessageReqVo = new PubMessageReqVo();
        try {
            pubMessageReqVo.setAppId("1002");
            pubMessageReqVo.setCtt("1");
            pubMessageReqVo.setMessage("尊敬的钱宝网会员，恭喜您在宝玩通过充值<" + entity.getAppName() + ">游戏获取" + num.intValue() + "宝劵");
            pubMessageReqVo.setSubType("1");
            pubMessageReqVo.setToid(entity.getPayUserId());
            pubMessage(pubMessageReqVo);
        } catch (Exception e) {
            log.error("payUserId:{},发送IM消息失败:{}", entity.getPayUserId(), e);
        }
    }

    /**
     * 发送IM消息
     *
     * @param pubMessageReqVo
     */
    protected void pubMessage(PubMessageReqVo pubMessageReqVo) {
        List<NameValuePair> payPairs = new ArrayList<NameValuePair>();
        payPairs.add(new BasicNameValuePair("appid", pubMessageReqVo.getAppId()));
        payPairs.add(new BasicNameValuePair("token", domainConfig.getString("pub.message.token")));
        payPairs.add(new BasicNameValuePair("fromid", domainConfig.getString("pub.message.fromid")));
        payPairs.add(new BasicNameValuePair("app_token", domainConfig.getString("pub.message.app.token")));
        payPairs.add(new BasicNameValuePair("ctt", pubMessageReqVo.getCtt()));
        payPairs.add(new BasicNameValuePair("subtype", pubMessageReqVo.getSubType()));
        payPairs.add(new BasicNameValuePair("toid", pubMessageReqVo.getToid()));
        payPairs.add(new BasicNameValuePair("message", pubMessageReqVo.getMessage()));
        JSONObject json = null;
        log.info("发送消息接口请求接口参数:" + payPairs.toString());
        try {
            String response = ApiConnector.post(domainConfig.getString("pub.message.url"), payPairs);
            log.info(response);
            json = JSONObject.parseObject(response);
            JSONObject meta = JSONObject.parseObject(json.getString("meta"));
            Integer code = meta.getInteger("code");
            if (code == 200) {
                log.info("发送消息接口请求成功");
            }
        } catch (Exception e) {
            log.error("发送消息接口请求失败", e);
        }
    }

    /**
     * 消费金额是否>=活动赠送金额
     *
     * @param bqAutoSendEntity     冰趣记录的消费金额为元
     * @param storeAwardConfEntity 活动赠送金额为分
     * @return
     */
    protected boolean isSendBq(BqAutoSendEntity bqAutoSendEntity, StoreAwardConfEntity storeAwardConfEntity) {
        //冰趣记录的消费金额为元，需转为分
        return PriceUtil.convertToFenL(bqAutoSendEntity.getAccountAmount()).compareTo(storeAwardConfEntity.getAccountAmount()) == 1 || PriceUtil.convertToFenL(bqAutoSendEntity.getAccountAmount()).compareTo(storeAwardConfEntity.getAccountAmount()) == 0;
    }

}
