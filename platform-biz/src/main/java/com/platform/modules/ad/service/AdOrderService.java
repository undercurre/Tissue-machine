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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.common.utils.Constant;
import com.platform.common.utils.DateUtils;
import com.platform.modules.ad.entity.AdOrderEntity;
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.vo.AdOrderAccountVo;
import com.platform.modules.vo.AdUserCountVo;
import com.platform.modules.vo.RegionSortVo;
import com.platform.modules.vo.TissueByDateVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单表Service接口
 *
 * @author zqh
 * @date 2020-09-22 11:34:23
 */
public interface AdOrderService extends IService<AdOrderEntity> {



    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdOrderEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询订单表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params, SysUserEntity user);

    /**
     * 新增订单表
     *
     * @param adOrder 订单表
     * @return 新增结果
     */
    boolean add(AdOrderEntity adOrder);

    /**
     * 根据主键更新订单表
     *
     * @param adOrder 订单表
     * @return 更新结果
     */
    boolean update(AdOrderEntity adOrder);

    /**
     * 根据主键删除订单表
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

    boolean deleteOrderById(String orderId);

    List<AdOrderEntity> getOrderListByUserId(String userId);

    List<AdOrderEntity> getOrderList(QueryWrapper<AdOrderEntity> queryWrapper);

    Map<String, Integer> getUserRemainNumber(MallUserEntity user);

    AdOrderEntity queryById(String id);

    AdOrderAccountVo orderAccount(Integer day,Map<String,Object> level);

    /**
     * 根据主键批量删除
     *
     * @param days 查询的天数
     * @param map 角色权限
     * @return 删除结果
     */
    AdUserCountVo userCount(Integer days, Map<String, Object> map);

    /**
     * @param type=1 本日、type=2 本周、 type=3 本月、type=4 本季度、type=5 本年
     * **/
    Integer queryVipCountByDate (Integer type);
    /**
     * @param type=1 本日、type=2 本周、 type=3 本月、type=4 本季度、type=5 本年
     * @return  纸巾销售总数量、用户数量
     * **/
    TissueByDateVo queryTissueByDate(Integer type);

    /**
     * 市级分组查询派发纸巾数量
     *
     * @return  List<RegionSortVo>
     * **/
    List<RegionSortVo> groupByTissueInCity();

    /**
     * 区级分组查询派发纸巾数量
     *
     * @return  List<RegionSortVo>
     * **/
    List<RegionSortVo> groupByTissueInDistrict();
}
