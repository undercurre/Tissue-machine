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
import com.platform.modules.ad.entity.AdAgentBalanceLogEntity;
import com.platform.modules.ad.service.AdAgentBalanceLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 代理流水表Controller
 *
 * @author zqh
 * @date 2020-12-09 10:12:34
 */
@RestController
@RequestMapping("ad/agentbalancelog")
public class AdAgentBalanceLogController extends AbstractController {
    @Autowired
    private AdAgentBalanceLogService adAgentBalanceLogService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:agentbalancelog:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdAgentBalanceLogEntity> list = adAgentBalanceLogService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询代理流水表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:agentbalancelog:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adAgentBalanceLogService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:agentbalancelog:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdAgentBalanceLogEntity adAgentBalanceLog = adAgentBalanceLogService.getById(id);

        return RestResponse.success().put("agentbalancelog", adAgentBalanceLog);
    }

    /**
     * 新增代理流水表
     *
     * @param adAgentBalanceLog adAgentBalanceLog
     * @return RestResponse
     */
    @SysLog("新增代理流水表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:agentbalancelog:save")
    public RestResponse save(@RequestBody AdAgentBalanceLogEntity adAgentBalanceLog) {

        adAgentBalanceLogService.add(adAgentBalanceLog);

        return RestResponse.success();
    }

    /**
     * 修改代理流水表
     *
     * @param adAgentBalanceLog adAgentBalanceLog
     * @return RestResponse
     */
    @SysLog("修改代理流水表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:agentbalancelog:update")
    public RestResponse update(@RequestBody AdAgentBalanceLogEntity adAgentBalanceLog) {

        adAgentBalanceLogService.update(adAgentBalanceLog);

        return RestResponse.success();
    }

    /**
     * 根据主键删除代理流水表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除代理流水表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:agentbalancelog:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adAgentBalanceLogService.deleteBatch(ids);

        return RestResponse.success();
    }
}
