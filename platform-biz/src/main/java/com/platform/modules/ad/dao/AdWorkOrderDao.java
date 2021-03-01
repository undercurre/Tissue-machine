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
package com.platform.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.ad.entity.AdWorkOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author zqh
 * @date 2020-10-19 14:05:28
 */
@Mapper
public interface AdWorkOrderDao extends BaseMapper<AdWorkOrderEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdWorkOrderEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<AdWorkOrderEntity> selectAdWorkOrderPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<AdWorkOrderEntity> AppSelectAdWorkOrderPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 根据ID查询详情
     *
     * @param id   id
     * @return AdWorkOrderEntity
     */
    AdWorkOrderEntity getDetailById(String id);

    /**
     * 查询parentId下面有几个孩子
     *
     * @param parentId   id
     * @return AdWorkOrderEntity
     */
    Integer getSonNum(String parentId);

    /**
     * 查询parentId下面的子工单列表
     *
     * @param parentId   id
     * @return AdWorkOrderEntity
     */
    List<AdWorkOrderEntity> getSonList(String parentId);

    /**
     * 获取我创建的和指派我的工单
     *
     * @param params   查询参数
     * @return AdWorkOrderEntity
     */
    List<AdWorkOrderEntity> queryMyWorkOrder(IPage page, @Param("params") Map<String, Object> params);
}
