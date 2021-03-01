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
package com.platform.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysWorkerDepartmentEntity;
import com.platform.modules.sys.service.SysWorkerDepartmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 运维部门表Controller
 *
 * @author dxd
 * @date 2020-12-10 14:23:01
 */
@RestController
@RequestMapping("sys/workerdepartment")
public class SysWorkerDepartmentController extends AbstractController {
    @Autowired
    private SysWorkerDepartmentService sysWorkerDepartmentService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("sys:workerdepartment:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<SysWorkerDepartmentEntity> list = sysWorkerDepartmentService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询运维部门表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:workerdepartment:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = sysWorkerDepartmentService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:workerdepartment:info")
    public RestResponse info(@PathVariable("id") String id) {
        SysWorkerDepartmentEntity sysWorkerDepartment = sysWorkerDepartmentService.getById(id);

        return RestResponse.success().put("workerdepartment", sysWorkerDepartment);
    }

    /**
     * 新增运维部门表
     *
     * @param sysWorkerDepartment sysWorkerDepartment
     * @return RestResponse
     */
    @SysLog("新增运维部门表")
    @RequestMapping("/save")
    @RequiresPermissions("sys:workerdepartment:save")
    public RestResponse save(@RequestBody SysWorkerDepartmentEntity sysWorkerDepartment) {

        sysWorkerDepartmentService.add(sysWorkerDepartment);

        return RestResponse.success();
    }

    /**
     * 修改运维部门表
     *
     * @param sysWorkerDepartment sysWorkerDepartment
     * @return RestResponse
     */
    @SysLog("修改运维部门表")
    @RequestMapping("/update")
    @RequiresPermissions("sys:workerdepartment:update")
    public RestResponse update(@RequestBody SysWorkerDepartmentEntity sysWorkerDepartment) {

        sysWorkerDepartmentService.update(sysWorkerDepartment);

        return RestResponse.success();
    }

    /**
     * 根据主键删除运维部门表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除运维部门表")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:workerdepartment:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        sysWorkerDepartmentService.deleteBatch(ids);

        return RestResponse.success();
    }
}
