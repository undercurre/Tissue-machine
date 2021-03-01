/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.modules.ad.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.ad.dao.AdMachineAgentDao;
import com.platform.modules.ad.entity.AdMachineAgentEntity;
import com.platform.modules.ad.service.AdMachineAgentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用户来源表
Service实现类
 *
 * @author gjw
 * @date 2020-11-02 15:00:40
 */
@Service("adMachineAgentService")
public class AdMachineAgentServiceImpl extends ServiceImpl<AdMachineAgentDao, AdMachineAgentEntity> implements AdMachineAgentService {

    @Override
    public List<AdMachineAgentEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdMachineAgentEntity> page = new Query<AdMachineAgentEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdMachineAgentPage(page, params));
    }

    @Override
    public boolean add(AdMachineAgentEntity adMachineAgent) {
        return this.save(adMachineAgent);
    }

    @Override
    public boolean update(AdMachineAgentEntity adMachineAgent) {
        return this.updateById(adMachineAgent);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
