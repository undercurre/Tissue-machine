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
import com.platform.modules.ad.dao.AdChatWorkerDao;
import com.platform.modules.ad.entity.AdChatWorkerEntity;
import com.platform.modules.ad.service.AdChatWorkerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author zqh,dxd
 * @date 2020-10-28 14:14:46
 */
@Service("adChatWorkerService")
public class AdChatWorkerServiceImpl extends ServiceImpl<AdChatWorkerDao, AdChatWorkerEntity> implements AdChatWorkerService {

    @Override
    public List<AdChatWorkerEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdChatWorkerEntity> page = new Query<AdChatWorkerEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdChatWorkerPage(page, params));
    }

    @Override
    public boolean add(AdChatWorkerEntity adChatWorker) {
        return this.save(adChatWorker);
    }

    @Override
    public boolean update(AdChatWorkerEntity adChatWorker) {
        return this.updateById(adChatWorker);
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
