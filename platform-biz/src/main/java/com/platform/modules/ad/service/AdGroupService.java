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
package com.platform.modules.ad.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.ad.entity.AdGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 设备分组(片区)表Service接口
 *
 * @author zqh
 * @date 2021-01-08 16:15:53
 */
public interface AdGroupService extends IService<AdGroupEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdGroupEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询设备分组(片区)表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增设备分组(片区)表
     *
     * @param adGroup 设备分组(片区)表
     * @return 新增结果
     */
    boolean add(AdGroupEntity adGroup);

    /**
     * 根据主键更新设备分组(片区)表
     *
     * @param adGroup 设备分组(片区)表
     * @return 更新结果
     */
    boolean update(AdGroupEntity adGroup);

    /**
     * 根据主键删除设备分组(片区)表
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

    String getGroupOpenIdByMachineId(String machineId);
}
