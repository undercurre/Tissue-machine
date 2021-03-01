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
import com.platform.modules.ad.entity.AdChatWorkerEntity;
import com.platform.modules.ad.service.AdChatWorkerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zqh,dxd
 * @date 2020-10-28 14:14:46
 */
@RestController
@RequestMapping("ad/chatworker")
public class AdChatWorkerController extends AbstractController {
    @Autowired
    private AdChatWorkerService adChatWorkerService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:chatworker:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdChatWorkerEntity> list = adChatWorkerService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:chatworker:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adChatWorkerService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:chatworker:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdChatWorkerEntity adChatWorker = adChatWorkerService.getById(id);

        return RestResponse.success().put("chatworker", adChatWorker);
    }

    /**
     * 新增
     *
     * @param adChatWorker adChatWorker
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("ad:chatworker:save")
    public RestResponse save(@RequestBody AdChatWorkerEntity adChatWorker) {

        adChatWorkerService.add(adChatWorker);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param adChatWorker adChatWorker
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("ad:chatworker:update")
    public RestResponse update(@RequestBody AdChatWorkerEntity adChatWorker) {

        adChatWorkerService.update(adChatWorker);

        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:chatworker:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adChatWorkerService.deleteBatch(ids);

        return RestResponse.success();
    }
}
