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
import com.platform.modules.ad.dao.AdMachineStockDao;
import com.platform.modules.ad.entity.AdGoodsBannerEntity;
import com.platform.modules.ad.entity.AdMachineStockEntity;
import com.platform.modules.ad.service.AdGoodsBannerService;
import com.platform.modules.ad.service.AdMachineStockService;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 机柜库存表Service实现类
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@Service("adMachineStockService")
public class AdMachineStockServiceImpl extends ServiceImpl<AdMachineStockDao, AdMachineStockEntity> implements AdMachineStockService {
    @Autowired
    private AdGoodsBannerService adGoodsBannerService;
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public List<AdMachineStockEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params, SysUserEntity user) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
//        if (sysUserDao.getUserLevel(user.getUserId()) != Constant.roleLevel.ZDL.getValue()) {
//            if (user.getIsStaff() == 1){
//                params.put("agentId", user.getCreateUserId());
//            } else {
//                params.put("agentId", user.getUserId());
//            }
//        }
        if (user.getIsStaff() == 1) {
            Integer level = sysUserDao.getUserLevel(user.getCreateUserId());
            if (level == Constant.roleLevel.DL.getValue()) {
                params.put("agentId", user.getCreateUserId());
            }
        } else {
            if (sysUserDao.getUserLevel(user.getUserId()) == Constant.roleLevel.DL.getValue()) {
                params.put("agentId", user.getUserId());
            }
        }
        Page<AdMachineStockEntity> page = new Query<AdMachineStockEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdMachineStockPage(page, params));
    }

    @Override
    public boolean add(AdMachineStockEntity adMachineStock) {
        return this.save(adMachineStock);
    }

    @Override
    public boolean update(AdMachineStockEntity adMachineStock) {
        return this.updateById(adMachineStock);
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
    public List<AdMachineStockEntity> queryByMachineSn(String sn) {
        List<AdMachineStockEntity> adMachineStockEntities = this.baseMapper.queryByMachineSn(sn);
        adMachineStockEntities.forEach(e->{
            List<AdGoodsBannerEntity> goods_id = adGoodsBannerService.list(new QueryWrapper<AdGoodsBannerEntity>().eq("GOODS_ID", e.getGoodsId()));
            e.setGoodsBanner(goods_id);
        });
        return adMachineStockEntities;
    }
}
