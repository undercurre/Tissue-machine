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
import com.platform.modules.ad.entity.MallAccountLogEntity;
import com.platform.modules.ad.service.MallAccountLogService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户账户余额变动记录Controller
 *
 * @author zqh
 * @date 2019-07-11 16:56:08
 */
@RestController
@RequestMapping("mall/accountlog")
public class MallAccountLogController extends AbstractController {
    @Autowired
    private MallAccountLogService mallAccountLogService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("mall:accountlog:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<MallAccountLogEntity> list = mallAccountLogService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询用户账户余额变动记录
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("mall:accountlog:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = mallAccountLogService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("mall:accountlog:info")
    public RestResponse info(@PathVariable("id") String id) {
        MallAccountLogEntity mallAccountLog = mallAccountLogService.getById(id);

        return RestResponse.success().put("accountlog", mallAccountLog);
    }

    /**
     * 新增用户账户余额变动记录
     *
     * @param mallAccountLog mallAccountLog
     * @return RestResponse
     */
    @SysLog("新增用户账户余额变动记录")
    @RequestMapping("/save")
    @RequiresPermissions("mall:accountlog:save")
    public RestResponse save(@RequestBody MallAccountLogEntity mallAccountLog) {

        mallAccountLogService.add(mallAccountLog);

        return RestResponse.success();
    }

    /**
     * 修改用户账户余额变动记录
     *
     * @param mallAccountLog mallAccountLog
     * @return RestResponse
     */
    @SysLog("修改用户账户余额变动记录")
    @RequestMapping("/update")
    @RequiresPermissions("mall:accountlog:update")
    public RestResponse update(@RequestBody MallAccountLogEntity mallAccountLog) {

        mallAccountLogService.update(mallAccountLog);

        return RestResponse.success();
    }

    /**
     * 根据主键删除用户账户余额变动记录
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除用户账户余额变动记录")
    @RequestMapping("/delete")
    @RequiresPermissions("mall:accountlog:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        mallAccountLogService.deleteBatch(ids);

        return RestResponse.success();
    }
}
