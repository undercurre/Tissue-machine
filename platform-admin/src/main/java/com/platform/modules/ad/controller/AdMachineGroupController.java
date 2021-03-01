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
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.service.AdTissueMachineService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdMachineGroupEntity;
import com.platform.modules.ad.service.AdMachineGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 机柜分组表Controller
 *
 * @author zqh
 * @date 2021-01-08 16:15:53
 */
@RestController
@RequestMapping("ad/machinegroup")
@Api(tags = "设备分组中间表接口")
public class AdMachineGroupController extends AbstractController {
    @Autowired
    private AdTissueMachineService adTissueMachineService;
    @Autowired
    private AdMachineGroupService adMachineGroupService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdMachineGroupEntity> list = adMachineGroupService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询机柜分组表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adMachineGroupService.queryPage(params);

        return RestResponse.success().put("page", page);
    }



    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    public RestResponse info(@PathVariable("id") String id) {
        AdMachineGroupEntity adMachineGroup = adMachineGroupService.getById(id);

        return RestResponse.success().put("machinegroup", adMachineGroup);
    }

    /**
     * 新增机柜分组表
     *
     * @param adMachineGroup adMachineGroup
     * @return RestResponse
     */
    @SysLog("新增机柜分组表")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody AdMachineGroupEntity adMachineGroup) {

        adMachineGroupService.add(adMachineGroup);

        return RestResponse.success();
    }

    /**
     * 修改机柜分组表
     *
     * @param adMachineGroup adMachineGroup
     * @return RestResponse
     */
    @SysLog("修改机柜分组表")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody AdMachineGroupEntity adMachineGroup) {

        adMachineGroupService.update(adMachineGroup);

        return RestResponse.success();
    }

    /**
     * 根据主键删除机柜分组表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除机柜分组表")
    @RequestMapping("/delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adMachineGroupService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 查询出没有语音的机柜
     * @return RestResponse
     */
    @RequestMapping("/queryNoGroupList")
    @ApiOperation(value = "查询出没有分组的机柜", notes = "查询出没有分组的机柜", httpMethod = "GET")
    public RestResponse queryNoGroupList() {
        //所有list
        List<AdTissueMachineEntity> list = adTissueMachineService.list();
        //表中有效的机柜id
        List<AdMachineGroupEntity> machineList = adMachineGroupService.list();
        List<String> machineIdList = new ArrayList<>();
        machineList.stream().forEach(item ->machineIdList.add(item.getMachineId()));
        List<AdTissueMachineEntity> collect = list.stream().filter(item -> !machineIdList.contains(item.getId())).collect(Collectors.toList());
        return RestResponse.success().put("list", collect);
    }

    /**
     * 根据机柜语音表的父级ID查找对应的机柜
     *
     * @param params 机柜语音表ID
     * @return RestResponse
     */
    @SysLog("根据机柜分组表的分组ID查找对应的机柜")
    @RequestMapping("/getMachineByGroupId")
    @ApiOperation(notes = "根据机柜分组表的分组ID查找对应的机柜", value = "根据机柜分组表的分组ID查找对应的机柜", httpMethod = "GET")
    public RestResponse getMachineByGroupId(@RequestParam Map<String, Object> params) {
        Page page = adMachineGroupService.getMachineByGroupId(params);
        return RestResponse.success().put("page", page);
    }
}
