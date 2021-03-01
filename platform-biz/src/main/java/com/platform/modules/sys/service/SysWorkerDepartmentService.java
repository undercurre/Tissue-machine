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
package com.platform.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.sys.entity.SysWorkerDepartmentEntity;

import java.util.List;
import java.util.Map;

/**
 * 运维部门表Service接口
 *
 * @author dxd
 * @date 2020-12-10 14:23:01
 */
public interface SysWorkerDepartmentService extends IService<SysWorkerDepartmentEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysWorkerDepartmentEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询运维部门表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增运维部门表
     *
     * @param sysWorkerDepartment 运维部门表
     * @return 新增结果
     */
    boolean add(SysWorkerDepartmentEntity sysWorkerDepartment);

    /**
     * 根据主键更新运维部门表
     *
     * @param sysWorkerDepartment 运维部门表
     * @return 更新结果
     */
    boolean update(SysWorkerDepartmentEntity sysWorkerDepartment);

    /**
     * 根据主键删除运维部门表
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);
}
