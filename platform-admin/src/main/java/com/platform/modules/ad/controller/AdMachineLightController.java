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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.job.task.LightTask;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdMachineLightEntity;
import com.platform.modules.ad.service.AdMachineLightService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 机柜开关灯时间表Controller
 *
 * @author dxd
 * @date 2020-12-22 14:13:54
 */
@RestController
@RequestMapping("ad/machinelight")
public class AdMachineLightController extends AbstractController {
    @Autowired
    private AdMachineLightService adMachineLightService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:machinelight:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdMachineLightEntity> list = adMachineLightService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询机柜开关灯时间表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:machinelight:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adMachineLightService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:machinelight:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdMachineLightEntity adMachineLight = adMachineLightService.getById(id);

        return RestResponse.success().put("machinelight", adMachineLight);
    }

    /**
     * 新增机柜开关灯时间表
     *
     * @param adMachineLight adMachineLight
     * @return RestResponse
     */
    @SysLog("新增机柜开关灯时间表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:machinelight:save")
    public RestResponse save(@RequestBody AdMachineLightEntity adMachineLight) {

        adMachineLightService.add(adMachineLight);

        return RestResponse.success();
    }

    /**
     * 修改机柜开关灯时间表
     *
     * @param adMachineLight adMachineLight
     * @return RestResponse
     */
    @SysLog("修改机柜开关灯时间表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:machinelight:update")
    public RestResponse update(@RequestBody AdMachineLightEntity adMachineLight) {

        adMachineLightService.update(adMachineLight);

        return RestResponse.success();
    }

    /**
     * 根据主键删除机柜开关灯时间表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除机柜开关灯时间表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:machinelight:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adMachineLightService.deleteBatch(ids);

        return RestResponse.success();
    }
}
