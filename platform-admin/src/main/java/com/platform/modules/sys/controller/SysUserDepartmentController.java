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
import com.platform.modules.sys.entity.SysUserDepartmentEntity;
import com.platform.modules.sys.service.SysUserDepartmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户部门表Controller
 *
 * @author zqh
 * @date 2021-01-09 10:37:49
 */
@RestController
@RequestMapping("sys/userdepartment")
public class SysUserDepartmentController extends AbstractController {
    @Autowired
    private SysUserDepartmentService sysUserDepartmentService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("sys:userdepartment:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<SysUserDepartmentEntity> list = sysUserDepartmentService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询系统用户部门表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:userdepartment:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = sysUserDepartmentService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:userdepartment:info")
    public RestResponse info(@PathVariable("id") String id) {
        SysUserDepartmentEntity sysUserDepartment = sysUserDepartmentService.getById(id);

        return RestResponse.success().put("userdepartment", sysUserDepartment);
    }

    /**
     * 新增系统用户部门表
     *
     * @param sysUserDepartment sysUserDepartment
     * @return RestResponse
     */
    @SysLog("新增系统用户部门表")
    @RequestMapping("/save")
    @RequiresPermissions("sys:userdepartment:save")
    public RestResponse save(@RequestBody SysUserDepartmentEntity sysUserDepartment) {

        sysUserDepartmentService.add(sysUserDepartment);

        return RestResponse.success();
    }

    /**
     * 修改系统用户部门表
     *
     * @param sysUserDepartment sysUserDepartment
     * @return RestResponse
     */
    @SysLog("修改系统用户部门表")
    @RequestMapping("/update")
    @RequiresPermissions("sys:userdepartment:update")
    public RestResponse update(@RequestBody SysUserDepartmentEntity sysUserDepartment) {

        sysUserDepartmentService.update(sysUserDepartment);

        return RestResponse.success();
    }

    /**
     * 根据主键删除系统用户部门表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除系统用户部门表")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:userdepartment:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        sysUserDepartmentService.deleteBatch(ids);

        return RestResponse.success();
    }
}
