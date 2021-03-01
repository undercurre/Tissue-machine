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
import com.platform.modules.sys.dao.SysUserDepartmentDao;
import com.platform.modules.sys.entity.SysUserDepartmentEntity;
import com.platform.modules.sys.service.SysUserDepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户部门表Service实现类
 *
 * @author zqh
 * @date 2021-01-09 10:37:49
 */
@Service("sysUserDepartmentService")
public class SysUserDepartmentServiceImpl extends ServiceImpl<SysUserDepartmentDao, SysUserDepartmentEntity> implements SysUserDepartmentService {

    @Override
    public List<SysUserDepartmentEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<SysUserDepartmentEntity> page = new Query<SysUserDepartmentEntity>(params).getPage();
        return page.setRecords(baseMapper.selectSysUserDepartmentPage(page, params));
    }

    @Override
    public boolean add(SysUserDepartmentEntity sysUserDepartment) {
        return this.save(sysUserDepartment);
    }

    @Override
    public boolean update(SysUserDepartmentEntity sysUserDepartment) {
        return this.updateById(sysUserDepartment);
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
    public List<String> queryDepartmentIdList(String userId) {
        return baseMapper.queryDepartmentIdList(userId);
    }

    @Override
    public List<String> selectManageByDepartment(List<String> departmentList) {
        return baseMapper.selectManageByDepartment(departmentList);
    }
}
