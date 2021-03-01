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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.ad.dao.AdGroupDao;
import com.platform.modules.ad.entity.AdGroupEntity;
import com.platform.modules.ad.entity.AdMachineGroupEntity;
import com.platform.modules.ad.service.AdGroupService;
import com.platform.modules.ad.service.AdMachineGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 设备分组(片区)表Service实现类
 *
 * @author zqh
 * @date 2021-01-08 16:15:53
 */
@Service("adGroupService")
public class AdGroupServiceImpl extends ServiceImpl<AdGroupDao, AdGroupEntity> implements AdGroupService {
    @Autowired
    private AdMachineGroupService adMachineGroupService;

    @Override
    public List<AdGroupEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdGroupEntity> page = new Query<AdGroupEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdGroupPage(page, params));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(AdGroupEntity adGroup) {
        Date date = new Date();
        adGroup.setCreateTime(date);
        this.save(adGroup);
        for(String machineId :adGroup.getMachineIds()){
            AdMachineGroupEntity adMachineGroupEntity = new AdMachineGroupEntity();
            adMachineGroupEntity.setGroupId(adGroup.getId());
            adMachineGroupEntity.setMachineId(machineId);
            adMachineGroupEntity.setCreateTime(date);
            adMachineGroupService.add(adMachineGroupEntity);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(AdGroupEntity adGroup) {
        Date date = new Date();
        for(String machineId :adGroup.getMachineIds()){
            AdMachineGroupEntity adMachineGroupEntity = new AdMachineGroupEntity();
            adMachineGroupEntity.setGroupId(adGroup.getId());
            adMachineGroupEntity.setMachineId(machineId);
            adMachineGroupEntity.setCreateTime(date);
            adMachineGroupService.add(adMachineGroupEntity);
        }
        return true;
    }

    @Override
    public boolean delete(String id) {
        adMachineGroupService.remove(new QueryWrapper<AdMachineGroupEntity>().eq("GROUP_ID",id));
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        for (String id : ids) {
            adMachineGroupService.remove(new QueryWrapper<AdMachineGroupEntity>().eq("GROUP_ID",id));
        }
        return this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public String getGroupOpenIdByMachineId(String machineId) {
        return this.baseMapper.getGroupOpenIdByMachineId(machineId);
    }
}
