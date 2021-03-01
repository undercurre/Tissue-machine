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
import com.platform.modules.ad.dao.AdPatchDao;
import com.platform.modules.ad.entity.AdPatchEntity;
import com.platform.modules.ad.service.AdPatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 广告贴片Service实现类
 *
 * @author zqh
 * @date 2020-09-23 09:32:59
 */
@Service("adPatchService")
public class AdPatchServiceImpl extends ServiceImpl<AdPatchDao, AdPatchEntity> implements AdPatchService {

    @Override
    public List<AdPatchEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdPatchEntity> page = new Query<AdPatchEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdPatchPage(page, params));
    }

    @Override
    public boolean add(AdPatchEntity adPatch) {
        return this.save(adPatch);
    }

    @Override
    public boolean update(AdPatchEntity adPatch) {
        return this.updateById(adPatch);
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
