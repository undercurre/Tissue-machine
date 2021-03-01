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
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.Query;
import com.platform.modules.ad.dao.AdUserActivityDao;
import com.platform.modules.ad.entity.AdActivityEntity;
import com.platform.modules.ad.entity.AdOrderEntity;
import com.platform.modules.ad.entity.AdUserActivityEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.service.AdActivityService;
import com.platform.modules.ad.service.AdOrderService;
import com.platform.modules.ad.service.AdUserActivityService;
import com.platform.modules.ad.service.MallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用户活动表Service实现类
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@Service("adUserActivityService")
public class AdUserActivityServiceImpl extends ServiceImpl<AdUserActivityDao, AdUserActivityEntity> implements AdUserActivityService {

    @Autowired
    AdActivityService adActivityService;
    @Autowired
    AdOrderService adOrderService;
    @Autowired
    MallUserService mallUserService;

    @Override
    public List<AdUserActivityEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdUserActivityEntity> page = new Query<AdUserActivityEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdUserActivityPage(page, params));
    }

    @Override
    public boolean add(AdUserActivityEntity adUserActivity) {
        return this.save(adUserActivity);
    }

    @Override
    public boolean update(AdUserActivityEntity adUserActivity) {
        return this.updateById(adUserActivity);
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

    @Override
    public List<AdUserActivityEntity> getListByUserId(String userId) {
        return baseMapper.getListByUserId(userId);
    }

//    @Override
//    public Integer getDayCount(String activityId, String userId) {
//        AdActivityEntity activity = adActivityService.getById(activityId);
//        return this.getDayCount(activityId, activity.getFrequency(), userId);
//    }

//    @Override
//    public Integer getDayCount(String activityId, Integer frequency, String userId) {
//        List<AdOrderEntity> orderList = adOrderService.getOrderList(
//                new QueryWrapper<AdOrderEntity>()
//                        .eq("T.USER_ID",userId)
//                        .between("T.CREATE_TIME", DateUtils.getStartDate(), DateUtils.getEndDate())
//                        .eq("OT.STATUS", Constant.ShipmentStatus.SUCCESS.getValue())
//        );
//        System.out.println(orderList);
//        return frequency - orderList.size();
//    }

    @Override
    public AdUserActivityEntity addActivity(AdUserActivityEntity entity) {
        if (entity == null) {
            return null;
        }
        String userId = entity.getUserId();
        String activityId = entity.getActivityId();
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        if (activityId == null) {
            throw new BusinessException("活动ID不能为空");
        }
        AdUserActivityEntity activityEntity = baseMapper.selectOne(
                new QueryWrapper<AdUserActivityEntity>()
                        .eq("USER_ID", userId)
                        .eq("ACTIVITY_ID", activityId)
        );
        if (activityEntity != null) {
            throw new BusinessException("该用户已经参加了这个活动");
        }
        baseMapper.insert(entity);
        return entity;
    }
}
