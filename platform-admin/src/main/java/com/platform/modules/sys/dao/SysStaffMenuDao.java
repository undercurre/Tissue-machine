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
package com.platform.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.sys.entity.SysStaffMenuEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author dxd
 * @date 2020-10-09 16:03:03
 */
@Mapper
public interface SysStaffMenuDao extends BaseMapper<SysStaffMenuEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysStaffMenuEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<SysStaffMenuEntity> selectSysStaffMenuPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 查询员工菜单列表
     *
     * @param params 查询参数
     * @return List
     */
    List<String> queryAllMenuByUserId(@Param("params") Map<String, Object> params);

    /**
     * 删除员工的所有菜单列表
     *
     * @param staff 员工信息
     */
    void deleteAllMenuByStaff(SysUserEntity staff);
}
