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
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdMachineOperateEntity;
import com.platform.modules.ad.service.AdMachineOperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 设备操作表Controller
 *
 * @author zqh
 * @date 2020-11-06 09:49:25
 */
@RestController
@RequestMapping("ad/machineoperate")
@Api(tags = "AdTissueMachineController|后台管理机柜操作记录接口")
public class AdMachineOperateController extends AbstractController {
    @Autowired
    private AdMachineOperateService adMachineOperateService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:machineoperate:list")
    @ApiOperation(value = "查询出所有的机柜操作记录", notes = "查询出所有的机柜操作记录", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "machineId", value = "按机柜ID查找", dataType = "string", example = ""),
    })
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdMachineOperateEntity> list = adMachineOperateService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询设备操作表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:machineoperate:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adMachineOperateService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:tissuemachine:restart")
    @ApiOperation(notes = "根据记录ID获取操作记录详情", value = "根据记录ID获取操作记录详情", httpMethod = "GET")
    public RestResponse info(@PathVariable("id") String id) {
        AdMachineOperateEntity adMachineOperate = adMachineOperateService.getById(id);

        return RestResponse.success().put("machineoperate", adMachineOperate);
    }

    /**
     * 新增设备操作表
     *
     * @param adMachineOperate adMachineOperate
     * @return RestResponse
     */
    @SysLog("新增设备操作表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:machineoperate:save")
    public RestResponse save(@RequestBody AdMachineOperateEntity adMachineOperate) {

        adMachineOperateService.add(adMachineOperate);

        return RestResponse.success();
    }

    /**
     * 修改设备操作表
     *
     * @param adMachineOperate adMachineOperate
     * @return RestResponse
     */
    @SysLog("修改设备操作表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:machineoperate:update")
    public RestResponse update(@RequestBody AdMachineOperateEntity adMachineOperate) {

        adMachineOperateService.update(adMachineOperate);

        return RestResponse.success();
    }

    /**
     * 根据主键删除设备操作表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除设备操作表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:machineoperate:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adMachineOperateService.deleteBatch(ids);

        return RestResponse.success();
    }
}
