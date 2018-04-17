package com.dome.sdkserver.service.impl;

import com.dome.sdkserver.bo.PassportGame;
import com.dome.sdkserver.metadata.dao.mapper.PassportGameManagerMapper;
import com.dome.sdkserver.service.PassportGameService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heyajun on 2017/5/25.
 */
@Service
public class PassportGameServiceImpl implements PassportGameService {


    @Autowired
    private PassportGameManagerMapper mapper;

    @Override
    public Map<String,Object> selectAllApp(String appCode, String appName,
                                              Integer pageNo, Integer pageSize) throws Exception{
        Page page = PageHelper.startPage(pageNo,pageSize);
        List<PassportGame> list = mapper.selectAllApp(appCode,appName);
        long total = page.getTotal();
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("list",list);
        data.put("totalCount",total);
        return data;
    }

}
