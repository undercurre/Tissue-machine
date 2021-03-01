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
import com.platform.modules.ad.entity.AdActivityEntity;
import com.platform.modules.ad.service.AdActivityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 活动表Controller
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@RestController
@RequestMapping("ad/activity")
public class AdActivityController extends AbstractController {
    @Autowired
    private AdActivityService adActivityService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:activity:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdActivityEntity> list = adActivityService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询活动表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:activity:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adActivityService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:activity:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdActivityEntity adActivity = adActivityService.getById(id);

        return RestResponse.success().put("activity", adActivity);
    }

    /**
     * 新增活动表
     *
     * @param adActivity adActivity
     * @return RestResponse
     */
    @SysLog("新增活动表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:activity:save")
    public RestResponse save(@RequestBody AdActivityEntity adActivity) {

        adActivityService.add(adActivity);

        return RestResponse.success();
    }

    /**
     * 修改活动表
     *
     * @param adActivity adActivity
     * @return RestResponse
     */
    @SysLog("修改活动表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:activity:update")
    public RestResponse update(@RequestBody AdActivityEntity adActivity) {

        adActivityService.update(adActivity);

        return RestResponse.success();
    }

    /**
     * 根据主键删除活动表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除活动表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:activity:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adActivityService.deleteBatch(ids);

        return RestResponse.success();
    }
}
