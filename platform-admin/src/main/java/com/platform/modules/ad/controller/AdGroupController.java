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
import com.platform.modules.ad.entity.AdGroupEntity;
import com.platform.modules.ad.service.AdGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 设备分组(片区)表Controller
 *
 * @author zqh
 * @date 2021-01-08 16:15:53
 */
@RestController
@RequestMapping("ad/group")
@Api(tags = "设备分组接口")
public class AdGroupController extends AbstractController {
    @Autowired
    private AdGroupService adGroupService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:group:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdGroupEntity> list = adGroupService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询设备分组(片区)表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:group:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adGroupService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:group:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdGroupEntity adGroup = adGroupService.getById(id);

        return RestResponse.success().put("group", adGroup);
    }

    /**
     * 新增设备分组(片区)表
     *
     * @param adGroup adGroup
     * @return RestResponse
     */
    @SysLog("新增设备分组(片区)表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:group:save")
    public RestResponse save(@RequestBody AdGroupEntity adGroup) {

        adGroupService.add(adGroup);

        return RestResponse.success();
    }

    /**
     * 修改设备分组(片区)表
     *
     * @param adGroup adGroup
     * @return RestResponse
     */
    @SysLog("修改设备分组(片区)表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:group:update")
    public RestResponse update(@RequestBody AdGroupEntity adGroup) {

        adGroupService.update(adGroup);

        return RestResponse.success();
    }

    /**
     * 根据主键删除设备分组(片区)表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除设备分组(片区)表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:group:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adGroupService.deleteBatch(ids);
        return RestResponse.success();
    }

    /**
     * 查看所有列表
     *
     * @param  machineId
     * @return RestResponse
     */
    @RequestMapping("/getGroupOpenIdByMachineId")
    @ApiOperation(notes = "根据机柜ID查询机柜分组人的OPENID", value = "根据机柜ID查询机柜分组人的OPENID", httpMethod = "GET")
    public RestResponse getGroupOpenIdByMachineId(String machineId) {
        String openId = adGroupService.getGroupOpenIdByMachineId(machineId);
        return RestResponse.success().put("data", openId);
    }
}
