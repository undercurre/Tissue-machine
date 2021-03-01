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
import com.platform.modules.sys.entity.SysStaffMenuEntity;
import com.platform.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author dxd
 * @date 2020-10-09 16:03:03
 */
public interface SysStaffMenuService extends IService<SysStaffMenuEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysStaffMenuEntity> queryAll(Map<String, Object> params);

    /**
     * 查询员工菜单列表
     *
     * @param userId userId
     * @return List
     */
    List<String> queryAllMenuByUserId(String userId, Integer menuType, Integer whereMenu);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增
     *
     * @param sysStaffMenu
     * @return 新增结果
     */
    boolean add(SysStaffMenuEntity sysStaffMenu);

    /**
     * 根据主键更新
     *
     * @param sysStaffMenu
     * @return 更新结果
     */
    boolean update(SysStaffMenuEntity sysStaffMenu);

    /**
     * 根据主键删除
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
     * 删除员工的所有菜单列表
     *
     * @param staff 员工信息
     */
    void deleteAllMenuByStaff(SysUserEntity staff);
}
