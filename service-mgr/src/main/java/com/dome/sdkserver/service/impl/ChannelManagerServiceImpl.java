package com.dome.sdkserver.service.impl;

import com.dome.sdkserver.bo.ChannelManager;
import com.dome.sdkserver.bo.QueryPageEntity;
import com.dome.sdkserver.bq.util.PageDto;
import com.dome.sdkserver.metadata.dao.mapper.ChannelManagerMapper;
import com.dome.sdkserver.service.ChannelManagerService;
import com.dome.sdkserver.util.GlobalCodeUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChannelManagerServiceImpl implements ChannelManagerService {

    @Resource
    private ChannelManagerMapper mapper;


    @Override
    public PageDto<ChannelManager> queryList(QueryPageEntity<ChannelManager> entity) {
        PageDto<ChannelManager> data = new PageDto<ChannelManager>();
        Page page = null;
        if (entity.getPage()) {//分页
            page = PageHelper.startPage(entity.getPageNo(), entity.getPageSize());
        }
        List<ChannelManager> list = mapper.queryList(entity.getT());
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList();
        }
        data.setList(list);
        if (entity.getPage()) {
            data.setTotalCount(page.getTotal());
        } else {
            data.setTotalCount(Long.valueOf(list.size()));
        }
        data.setIsPage(entity.getPage());
        data.setPageNo(entity.getPageNo());
        data.setPageSize(entity.getPageSize());
        return data;
    }

    @Override
    public Map<String, String> autoCreateChannelCode() {
        String channelCode = null;
        List<ChannelManager> list = mapper.queryList(null);
        if (CollectionUtils.isEmpty(list)) {
            channelCode = "CHA000005";
        } else {
            ChannelManager manager = list.get(0);
            channelCode = manager.getChannelCode();
            //首次添加channelCode 从CHA000003开始
            //根据上次生成的渠道号自动生成本次渠道号(如:上次保存的渠道号为CHA000003)
            Integer number = Integer.valueOf(channelCode.substring(3, channelCode.length()));
            if (number < 5) {
                channelCode = "CHA000005";
            } else {
                number += 1;
                String f = "CHA" + "%0" + 6 + "d";
                channelCode = String.format(f, number);
            }
        }
        Map<String, String> data = new HashMap<String, String>();
        data.put("channelCode", channelCode);
        return data;
    }

    @Override
    public int deleteChannelCode(Integer id) {
        return mapper.deleteById(id);
    }

    @Override
    public int addChannle(ChannelManager channelManager) {
        return mapper.addChannel(channelManager);
    }


}
