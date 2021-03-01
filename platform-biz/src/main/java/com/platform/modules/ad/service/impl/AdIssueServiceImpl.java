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
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.modules.ad.dao.AdIssueDao;
import com.platform.modules.ad.dao.AdIssueImageDao;
import com.platform.modules.ad.entity.AdIssueEntity;
import com.platform.modules.ad.entity.AdIssueImageEntity;
import com.platform.modules.ad.service.AdIssueImageService;
import com.platform.modules.ad.service.AdIssueService;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 问题表Service实现类
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@Service("adIssueService")
public class AdIssueServiceImpl extends ServiceImpl<AdIssueDao, AdIssueEntity> implements AdIssueService {

    @Autowired
    private AdIssueImageService adIssueImageService;
    @Autowired
    private SysUserDao sysUserDao;
    @Override
    public List<AdIssueEntity> queryAll(Map<String, Object> params) {

        List<AdIssueEntity> adIssueEntityList = baseMapper.queryAll(params);
        for(AdIssueEntity adIssueEntity: adIssueEntityList){
            List<AdIssueImageEntity> adIssueImageEntityList = adIssueImageService.list(new QueryWrapper<AdIssueImageEntity>().eq("ISSUE_ID", adIssueEntity.getId()));
            adIssueEntity.setAdIssueImageEntityList(adIssueImageEntityList);
        }
        return adIssueEntityList;
    }

    @Override
    public Page queryPage(Map<String, Object> params, SysUserEntity user) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdIssueEntity> page = new Query<AdIssueEntity>(params).getPage();
        List<AdIssueEntity> adIssueEntityList = null;
        Integer level = sysUserDao.getUserLevel(user.getUserId());
        if (user.getIsStaff() == 1) {
            user.setUserId(user.getCreateUserId());
            level = sysUserDao.getUserLevel(user.getCreateUserId());
        }

        if (level == Constant.roleLevel.ZDL.getValue()) {
            adIssueEntityList  = baseMapper.selectAdIssuePage(page, params);
        } else {
            params.put("agentId", user.getUserId());
            adIssueEntityList = baseMapper.selectAdIssuePage(page, params);
        }

        for(AdIssueEntity adIssueEntity: adIssueEntityList){
            List<AdIssueImageEntity> adIssueImageEntityList = adIssueImageService.list(new QueryWrapper<AdIssueImageEntity>().eq("ISSUE_ID", adIssueEntity.getId()));
            adIssueEntity.setAdIssueImageEntityList(adIssueImageEntityList);
        }
        return page.setRecords(adIssueEntityList);
    }

    @Override
    public boolean add(AdIssueEntity adIssue) {
        return this.save(adIssue);
    }

    @Override
    public boolean update(AdIssueEntity adIssue) {
        return this.updateById(adIssue);
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
