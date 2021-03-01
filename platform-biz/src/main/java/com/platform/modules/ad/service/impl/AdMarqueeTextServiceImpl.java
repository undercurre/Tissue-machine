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
import com.platform.modules.ad.dao.AdMarqueeTextDao;
import com.platform.modules.ad.entity.AdMarqueeTextEntity;
import com.platform.modules.ad.service.AdMarqueeTextService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 首页走马灯文字公告管理Service实现类
 *
 * @author dxd
 * @date 2020-10-27 09:50:20
 */
@Service("adMarqueeTextService")
public class AdMarqueeTextServiceImpl extends ServiceImpl<AdMarqueeTextDao, AdMarqueeTextEntity> implements AdMarqueeTextService {

    @Override
    public List<AdMarqueeTextEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdMarqueeTextEntity> page = new Query<AdMarqueeTextEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdMarqueeTextPage(page, params));
    }

    @Override
    public boolean add(AdMarqueeTextEntity adMarqueeText) {
        return this.save(adMarqueeText);
    }

    @Override
    public boolean update(AdMarqueeTextEntity adMarqueeText) {
        return this.updateById(adMarqueeText);
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
