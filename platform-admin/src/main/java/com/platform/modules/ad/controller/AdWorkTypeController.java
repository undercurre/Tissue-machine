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
import com.platform.modules.ad.entity.AdWorkTypeEntity;
import com.platform.modules.ad.service.AdWorkTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 工单类型表Controller
 *
 * @author gjw
 * @date 2021-01-09 16:27:28
 */
@RestController
@RequestMapping("ad/worktype")
public class AdWorkTypeController extends AbstractController {
    @Autowired
    private AdWorkTypeService adWorkTypeService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
//    @RequiresPermissions("ad:worktype:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdWorkTypeEntity> list = adWorkTypeService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询工单类型表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
//    @RequiresPermissions("ad:worktype:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adWorkTypeService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:worktype:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdWorkTypeEntity adWorkType = adWorkTypeService.getById(id);

        return RestResponse.success().put("worktype", adWorkType);
    }

    /**
     * 新增工单类型表
     *
     * @param adWorkType adWorkType
     * @return RestResponse
     */
    @SysLog("新增工单类型表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:worktype:save")
    public RestResponse save(@RequestBody AdWorkTypeEntity adWorkType) {

        adWorkTypeService.add(adWorkType);

        return RestResponse.success();
    }

    /**
     * 修改工单类型表
     *
     * @param adWorkType adWorkType
     * @return RestResponse
     */
    @SysLog("修改工单类型表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:worktype:update")
    public RestResponse update(@RequestBody AdWorkTypeEntity adWorkType) {

        adWorkTypeService.update(adWorkType);

        return RestResponse.success();
    }

    /**
     * 根据主键删除工单类型表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除工单类型表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:worktype:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adWorkTypeService.deleteBatch(ids);

        return RestResponse.success();
    }
}
