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
import com.platform.modules.sys.entity.SysUserDepartmentEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户部门表Service接口
 *
 * @author zqh
 * @date 2021-01-09 10:37:49
 */
public interface SysUserDepartmentService extends IService<SysUserDepartmentEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysUserDepartmentEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询系统用户部门表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增系统用户部门表
     *
     * @param sysUserDepartment 系统用户部门表
     * @return 新增结果
     */
    boolean add(SysUserDepartmentEntity sysUserDepartment);

    /**
     * 根据主键更新系统用户部门表
     *
     * @param sysUserDepartment 系统用户部门表
     * @return 更新结果
     */
    boolean update(SysUserDepartmentEntity sysUserDepartment);

    /**
     * 根据主键删除系统用户部门表
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

    /**
     * 根据用户ID查询部门ID
     *
     * @param userId 用户Id
     * @return 部门ID列表
     */
    List<String> queryDepartmentIdList(String userId);

    /**
     * 根据部门ID查询对应的部门经理
     *
     * @param departmentList 部门ID
     * @return 部门经理列表
     */
    List<String> selectManageByDepartment(@Param("departmentList")List<String> departmentList);
}
