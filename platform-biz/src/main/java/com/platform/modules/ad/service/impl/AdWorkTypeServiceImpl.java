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
import com.platform.modules.ad.dao.AdWorkTypeDao;
import com.platform.modules.ad.entity.AdWorkTypeEntity;
import com.platform.modules.ad.service.AdWorkTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 工单类型表Service实现类
 *
 * @author gjw
 * @date 2021-01-09 16:27:28
 */
@Service("adWorkTypeService")
public class AdWorkTypeServiceImpl extends ServiceImpl<AdWorkTypeDao, AdWorkTypeEntity> implements AdWorkTypeService {

    @Override
    public List<AdWorkTypeEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdWorkTypeEntity> page = new Query<AdWorkTypeEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdWorkTypePage(page, params));
    }

    @Override
    public boolean add(AdWorkTypeEntity adWorkType) {
        return this.save(adWorkType);
    }

    @Override
    public boolean update(AdWorkTypeEntity adWorkType) {
        return this.updateById(adWorkType);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        if (ids.length > 0){
            for (String id: ids){
                AdWorkTypeEntity adWorkTypeEntity = this.getById(id);
                adWorkTypeEntity.setStatus(1);
                this.update(adWorkTypeEntity);
            }
            return true;
        } else {
            return false;
        }
//        return this.removeByIds(Arrays.asList(ids));
    }
}
