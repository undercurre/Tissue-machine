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
package com.platform.modules.ad.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdOrderTissueEntity;
import com.platform.modules.ad.service.AdOrderTissueService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdOrderEntity;
import com.platform.modules.ad.service.AdOrderService;
import com.platform.modules.vo.AdOrderAccountVo;
import com.platform.modules.vo.AdUserCountVo;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单表Controller
 *
 * @author zqh
 * @date 2020-09-22 11:34:23
 */
@RestController
@RequestMapping("ad/order")
@Api(tags = "AdOrderController|订单相关接口")
public class AdOrderController extends AbstractController {
    @Autowired
    private AdOrderService adOrderService;
    @Autowired
    private AdOrderTissueService adOrderTissueService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:order:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdOrderEntity> list = adOrderService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询订单表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:order:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adOrderService.queryPage(params, getUser());

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:order:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdOrderEntity adOrder = adOrderService.queryById(id);

        return RestResponse.success().put("order", adOrder);
    }

    /**
     * 新增订单表
     *
     * @param adOrder adOrder
     * @return RestResponse
     */
    @SysLog("新增订单表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:order:save")
    public RestResponse save(@RequestBody AdOrderEntity adOrder) {

        adOrderService.add(adOrder);

        return RestResponse.success();
    }

    /**
     * 修改订单表
     *
     * @param adOrder adOrder
     * @return RestResponse
     */
    @SysLog("修改订单表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:order:update")
    public RestResponse update(@RequestBody AdOrderEntity adOrder) {

        adOrderService.update(adOrder);

        return RestResponse.success();
    }

    /**
     * 根据主键删除订单表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除订单表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:order:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adOrderService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 查看所有列表
     * @param day 查询天数
     * @return RestResponse
     */
    @RequestMapping("/orderAccount")
    @RequiresPermissions("ad:order:orderAccount")
    @ApiOperation(notes="财务管理" , value = "财务信息", httpMethod = "GET")
    public RestResponse orderAccount(Integer day) {
        Map<String, Object> level = getLevel();
        AdOrderAccountVo data = adOrderService.orderAccount(day,level);
        return RestResponse.success().put("data", data);
    }

    /**
     * 查询会员统计相关数据
     * @param days 查询天数
     * @return RestResponse
     */
    @RequestMapping("/userCount")
    @RequiresPermissions("ad:order:userCount")
    @ApiOperation(notes="查询会员统计相关数据" , value = "会员统计", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "days", value = "查询多少天的数据(null值为查询所有时段的数据)")
    })
    public RestResponse userCount(Integer days) {
        Map<String, Object> map = getLevel();
        AdUserCountVo adUserCountVo = adOrderService.userCount(days, map);
        return RestResponse.success().put("data", adUserCountVo);
    }

    /**
     * 获取订单相关的出纸状态详情
     * @param id 订单ID
     * @return RestResponse
     */
    @RequestMapping("/getShipmentStatusDetail/{id}")
    @ApiOperation(notes="获取订单相关的出纸状态详情" , value = "获取订单相关的出纸状态详情", httpMethod = "GET")
    public RestResponse getShipmentStatusDetail(@PathVariable("id") @ApiParam(value = "订单ID") String id) {
        List<AdOrderTissueEntity> adOrderTissueEntityList = adOrderTissueService.list(new QueryWrapper<AdOrderTissueEntity>()
            .eq("ORDER_ID", id)
            .orderByDesc("SELL_TIME"));
        return RestResponse.success().put("data", adOrderTissueEntityList);
    }
}
