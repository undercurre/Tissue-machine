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
import com.platform.modules.ad.dao.AdMachinePushDao;
import com.platform.modules.ad.entity.AdMachinePushEntity;
import com.platform.modules.ad.service.AdMachinePushService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 机柜发放纸巾中间表Service实现类
 *
 * @author zqh
 * @date 2020-11-02 13:54:01
 */
@Service("adMachinePushService")
public class AdMachinePushServiceImpl extends ServiceImpl<AdMachinePushDao, AdMachinePushEntity> implements AdMachinePushService {

    @Override
    public List<AdMachinePushEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdMachinePushEntity> page = new Query<AdMachinePushEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdMachinePushPage(page, params));
    }

    @Override
    public boolean add(AdMachinePushEntity adMachinePush) {
        return this.save(adMachinePush);
    }

    @Override
    public boolean update(AdMachinePushEntity adMachinePush) {
        return this.updateById(adMachinePush);
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
