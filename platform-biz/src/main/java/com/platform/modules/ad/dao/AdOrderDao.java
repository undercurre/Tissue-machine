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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.ad.entity.AdOrderEntity;
import com.platform.modules.vo.AdOrderAccountVo;
import com.platform.modules.vo.AdUserCountVo;
import com.platform.modules.vo.RegionSortVo;
import com.platform.modules.vo.TissueByDateVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单表Dao
 *
 * @author zqh
 * @date 2020-09-22 11:34:23
 */
@Mapper
public interface AdOrderDao extends BaseMapper<AdOrderEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdOrderEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<AdOrderEntity> selectAdOrderPage(IPage page, @Param("params") Map<String, Object> params);



    List<AdOrderEntity> getOrderListByUserId(String userId);

    List<AdOrderEntity> getOrderList(@Param("ew") QueryWrapper<AdOrderEntity> queryWrapper);

    boolean deleteOrderById(String orderId);

    AdOrderEntity queryById(String id);

    AdOrderAccountVo orderAccount(Integer day);

    AdOrderAccountVo orderAccountByProxyAgentId(@Param("day")Integer day, @Param("AgentId") String AgentId);

    AdUserCountVo userCount(@Param("agentId") String agentId);

    Integer registerUserCount(@Param("days") Integer days, @Param("agentId") String agentId);

    Integer queryVipCountByDate(Integer type);

    TissueByDateVo queryTissueByDate(Integer type);

    List<RegionSortVo> groupByTissueInCity();

    List<RegionSortVo> groupByTissueInDistrict();
}
