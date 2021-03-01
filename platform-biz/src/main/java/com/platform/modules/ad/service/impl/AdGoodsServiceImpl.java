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
import com.platform.common.utils.StringUtils;
import com.platform.modules.ad.dao.AdGoodsDao;
import com.platform.modules.ad.entity.AdGoodsBannerEntity;
import com.platform.modules.ad.entity.AdGoodsEntity;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.service.AdGoodsBannerService;
import com.platform.modules.ad.service.AdGoodsService;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品表Service实现类
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@Service("adGoodsService")
public class AdGoodsServiceImpl extends ServiceImpl<AdGoodsDao, AdGoodsEntity> implements AdGoodsService {
    @Autowired
    private AdGoodsBannerService adGoodsBannerService;
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public List<AdGoodsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params, SysUserEntity user) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdGoodsEntity> page = new Query<AdGoodsEntity>(params).getPage();

        List<AdGoodsEntity> adGoodsEntityList = baseMapper.selectAdGoodsPage(page, params);
        Integer level = sysUserDao.getUserLevel(user.getUserId());
        if (user.getIsStaff() == 1) {
            level = sysUserDao.getUserLevel(user.getCreateUserId());
            user.setUserId(user.getCreateUserId());
        }
        if (level == Constant.roleLevel.ZDL.getValue()) {
            for (AdGoodsEntity adGoodsEntity: adGoodsEntityList){
                adGoodsEntity.setImageUrlList(adGoodsBannerService.list(new QueryWrapper<AdGoodsBannerEntity>().eq("GOODS_ID", adGoodsEntity.getId())));
            }
            return page.setRecords(adGoodsEntityList);
        } else {
            adGoodsEntityList = adGoodsEntityList.stream().filter(e-> StringUtils.isNotBlank(e.getAgentId()))
                    .filter(e->e.getAgentId().equals(user.getUserId())).collect(Collectors.toList());
            System.out.println(adGoodsEntityList.toString());
            for (AdGoodsEntity adGoodsEntity: adGoodsEntityList){
                adGoodsEntity.setImageUrlList(adGoodsBannerService.list(new QueryWrapper<AdGoodsBannerEntity>().eq("GOODS_ID", adGoodsEntity.getId())));
            }
            return page.setRecords(adGoodsEntityList);
        }
    }

    @Override
    public boolean add(AdGoodsEntity adGoods) {
        return this.save(adGoods);
    }

    @Override
    public boolean update(AdGoodsEntity adGoods) {
        return this.updateById(adGoods);
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
