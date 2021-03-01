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
import com.platform.modules.sys.entity.SysUserDepartmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户部门表Dao
 *
 * @author zqh
 * @date 2021-01-09 10:37:49
 */
@Mapper
public interface SysUserDepartmentDao extends BaseMapper<SysUserDepartmentEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysUserDepartmentEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<SysUserDepartmentEntity> selectSysUserDepartmentPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 根据用户ID查询部门ID
     *
     * @param userId 用户Id
     * @return 部门ID列表
     */
    List<String> queryDepartmentIdList(@Param("userId")String userId);

    /**
     * 根据部门ID查询对应的部门经理
     *
     * @param departmentList 部门ID
     * @return 部门经理列表
     */
    List<String> selectManageByDepartment(@Param("departmentList")List<String> departmentList);
}
