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
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.modules.ad.dao.AdWithdrawalDao;
import com.platform.modules.ad.entity.AdWithdrawalEntity;
import com.platform.modules.ad.service.AdWithdrawalService;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 提现记录表Service实现类
 *
 * @author dxd
 * @date 2020-11-11 16:18:10
 */
@Service("adWithdrawalService")
public class AdWithdrawalServiceImpl extends ServiceImpl<AdWithdrawalDao, AdWithdrawalEntity> implements AdWithdrawalService {

    @Autowired
    private SysUserDao SysUserDao;

    @Override
    public List<AdWithdrawalEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdWithdrawalEntity> page = new Query<AdWithdrawalEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdWithdrawalPage(page, params));
    }

    @Override
    public boolean add(AdWithdrawalEntity adWithdrawal) {

        adWithdrawal.setCreateTime(new Date());
        adWithdrawal.setStatus(Constant.WithdrawalStatus.DSH.getValue());
        return this.save(adWithdrawal);
    }

    @Override
    public boolean update(AdWithdrawalEntity adWithdrawal) {
        return this.updateById(adWithdrawal);
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
    @Transactional(rollbackFor = Exception.class)
    public boolean changeStatus(Map<String, Object> params, SysUserEntity user) {
        String id = (String) params.get("id");
        Integer status = Integer.parseInt((String) params.get("status"));
        AdWithdrawalEntity adWithdrawalEntity = this.getById(id);
        adWithdrawalEntity.setStatus(status);
        adWithdrawalEntity.setHandlerId(user.getUserId());
        adWithdrawalEntity.setFinishTime(new Date());
        this.update(adWithdrawalEntity);
        // 审核不通过，退还金额
        if (status.equals(Constant.WithdrawalStatus.TXSB.getValue())) {
            BigDecimal addBalance = adWithdrawalEntity.getAmount().add(adWithdrawalEntity.getCommission());
            SysUserEntity sysUserEntity = SysUserDao.queryById(user.getUserId());
            sysUserEntity.setBalance(sysUserEntity.getBalance().add(addBalance));
            SysUserDao.updateById(sysUserEntity);
        }
        return true;
    }

    @Override
    public AdWithdrawalEntity getLastestCard(String userId){
        return  baseMapper.getLastestCard(userId);
    }
}
