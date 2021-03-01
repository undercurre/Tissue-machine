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
import com.platform.modules.ad.dao.AdGoodsBannerDao;
import com.platform.modules.ad.entity.AdGoodsBannerEntity;
import com.platform.modules.ad.service.AdGoodsBannerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 广告表Service实现类
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@Service("adGoodsBannerService")
public class AdGoodsBannerServiceImpl extends ServiceImpl<AdGoodsBannerDao, AdGoodsBannerEntity> implements AdGoodsBannerService {

    @Override
    public List<AdGoodsBannerEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdGoodsBannerEntity> page = new Query<AdGoodsBannerEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdGoodsBannerPage(page, params));
    }

    @Override
    public boolean add(AdGoodsBannerEntity adGoodsBanner) {
        return this.save(adGoodsBanner);
    }

    @Override
    public boolean update(AdGoodsBannerEntity adGoodsBanner) {
        return this.updateById(adGoodsBanner);
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
