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
package com.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.sys.dao.SysStaffMenuDao;
import com.platform.modules.sys.entity.SysStaffMenuEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysStaffMenuService;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author dxd
 * @date 2020-10-09 16:03:03
 */
@Service("sysStaffMenuService")
public class SysStaffMenuServiceImpl extends ServiceImpl<SysStaffMenuDao, SysStaffMenuEntity> implements SysStaffMenuService {

    @Override
    public List<SysStaffMenuEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<SysStaffMenuEntity> page = new Query<SysStaffMenuEntity>(params).getPage();
        return page.setRecords(baseMapper.selectSysStaffMenuPage(page, params));
    }

    @Override
    public List<String> queryAllMenuByUserId(String userId, Integer menuType, Integer whereMenu){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("menuType", menuType);
        params.put("whereMenu", whereMenu);
        return baseMapper.queryAllMenuByUserId(params);
    }

    @Override
    public boolean add(SysStaffMenuEntity sysStaffMenu) {
        return this.save(sysStaffMenu);
    }

    @Override
    public boolean update(SysStaffMenuEntity sysStaffMenu) {
        return this.updateById(sysStaffMenu);
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
    public void deleteAllMenuByStaff(SysUserEntity staff){
        baseMapper.deleteAllMenuByStaff(staff);
    }
}
