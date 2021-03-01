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
import com.platform.modules.ad.dao.AdUserCouponDao;
import com.platform.modules.ad.entity.AdOrderEntity;
import com.platform.modules.ad.entity.AdUserCouponEntity;
import com.platform.modules.ad.service.AdUserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 会员优惠券Service实现类
 *
 * @author gjw
 * @date 2020-12-11 10:51:39
 */
@Service("adUserCouponService")
public class AdUserCouponServiceImpl extends ServiceImpl<AdUserCouponDao, AdUserCouponEntity> implements AdUserCouponService {
    @Autowired
    private AdUserCouponService userCouponService;

    @Override
    public List<AdUserCouponEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.ID");
        params.put("asc", false);
        Page<AdUserCouponEntity> page = new Query<AdUserCouponEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdUserCouponPage(page, params));
    }

    @Override
    public boolean add(AdUserCouponEntity adUserCoupon) {
        return this.save(adUserCoupon);
    }

    @Override
    public boolean update(AdUserCouponEntity adUserCoupon) {
        return this.updateById(adUserCoupon);
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
    public boolean checkUserCouponLimit(String userId, String couponId, Integer limit) {
        List<AdUserCouponEntity> userCouponEntityList = baseMapper.selectList(
                new QueryWrapper<AdUserCouponEntity>().eq("USER_ID", userId).eq("COUPON_ID", couponId));
        if (userCouponEntityList == null || userCouponEntityList.size() == 0 || userCouponEntityList.size() < limit) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean backCoupon(AdOrderEntity order) {
        String userId = order.getUserId();
        Integer orderType = order.getOrderType();
        String couponId = order.getCouponId();
        if(orderType == 1){
            AdUserCouponEntity userCoupon = userCouponService.getOne(new QueryWrapper<AdUserCouponEntity>().eq("COUPON_ID", couponId).eq("USER_ID", userId).eq("ORDER_ID",order.getId()));
            userCoupon.setStatus(0);
            userCoupon.setUsedTime(null);
            userCoupon.setOrderId(null);
            userCouponService.updateById(userCoupon);
        }
        return true;
    }

    @Override
    public List<AdUserCouponEntity> selectAllByStatus(String userId, Integer status) {
        return baseMapper.selectAllByStatus(userId, status);
    }
}
