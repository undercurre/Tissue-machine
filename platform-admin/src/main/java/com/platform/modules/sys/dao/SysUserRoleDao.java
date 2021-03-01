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
import com.platform.modules.sys.entity.SysRoleEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户与角色对应关系
 *
 * @author zqh
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {

    /**
     * 根据用户ID，获取角色ID列表
     *
     * @param userId 用户ID
     * @return List
     */
    List<String> queryRoleIdList(String userId);

    /**
     * 根据用户ID，获取角色列表
     *
     * @param userId 用户ID
     * @return List
     */
    List<SysRoleEntity> queryRoleList(String userId);


    /**
     * 根据角色ID数组，批量删除
     *
     * @param roleIds 角色ids
     * @return int
     */
    int deleteBatch(String[] roleIds);

    /**
     * 查询所有的运维人员
     *
     * @return List<SysUserEntity>
     */
    List<SysUserEntity> queryAllWorker(String userId);

    /**
     * 查询所有的代理商
     *
     * @return List<SysUserEntity>
     */
    List<SysUserEntity> queryAgentList();

    /**
     * 查询用户是否为运维经理
     *
     * @return Integer
     */
    Integer isWorkerManager(String id);

    /**
     * 查询用户是否为运维人员
     *
     * @return Integer
     */
    Integer isWorker(String id);
}
