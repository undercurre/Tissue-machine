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
import com.platform.modules.sys.entity.SysPrinterEntity;
import com.platform.modules.sys.service.SysPrinterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 飞鹅打印机Controller
 *
 * @author zqh
 * @date 2020-06-05 14:25:19
 */
@RestController
@RequestMapping("sys/printer")
public class SysPrinterController extends AbstractController {
    @Autowired
    private SysPrinterService sysPrinterService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("sys:printer:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<SysPrinterEntity> list = sysPrinterService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询飞鹅打印机
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:printer:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = sysPrinterService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:printer:info")
    public RestResponse info(@PathVariable("id") String id) {
        SysPrinterEntity sysPrinter = sysPrinterService.getById(id);

        return RestResponse.success().put("printer", sysPrinter);
    }

    /**
     * 新增飞鹅打印机
     *
     * @param sysPrinter sysPrinter
     * @return RestResponse
     */
    @SysLog("新增飞鹅打印机")
    @RequestMapping("/save")
    @RequiresPermissions("sys:printer:save")
    public RestResponse save(@RequestBody SysPrinterEntity sysPrinter) {

        sysPrinterService.add(sysPrinter);

        return RestResponse.success();
    }

    /**
     * 修改飞鹅打印机
     *
     * @param sysPrinter sysPrinter
     * @return RestResponse
     */
    @SysLog("修改飞鹅打印机")
    @RequestMapping("/update")
    @RequiresPermissions("sys:printer:update")
    public RestResponse update(@RequestBody SysPrinterEntity sysPrinter) {

        sysPrinterService.update(sysPrinter);

        return RestResponse.success();
    }

    /**
     * 根据主键删除飞鹅打印机
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除飞鹅打印机")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:printer:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        sysPrinterService.deleteBatch(ids);

        return RestResponse.success();
    }
}
