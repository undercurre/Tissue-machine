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
import com.platform.modules.ad.entity.AdMessageEntity;
import com.platform.modules.ad.service.AdMessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 消息表Controller
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@RestController
@RequestMapping("ad/message")
public class AdMessageController extends AbstractController {
    @Autowired
    private AdMessageService adMessageService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:message:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdMessageEntity> list = adMessageService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询消息表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:message:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adMessageService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:message:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdMessageEntity adMessage = adMessageService.getById(id);

        return RestResponse.success().put("message", adMessage);
    }

    /**
     * 新增消息表
     *
     * @param adMessage adMessage
     * @return RestResponse
     */
    @SysLog("新增消息表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:message:save")
    public RestResponse save(@RequestBody AdMessageEntity adMessage) {

        adMessageService.add(adMessage);

        return RestResponse.success();
    }

    /**
     * 修改消息表
     *
     * @param adMessage adMessage
     * @return RestResponse
     */
    @SysLog("修改消息表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:message:update")
    public RestResponse update(@RequestBody AdMessageEntity adMessage) {

        adMessageService.update(adMessage);

        return RestResponse.success();
    }

    /**
     * 根据主键删除消息表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除消息表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:message:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adMessageService.deleteBatch(ids);

        return RestResponse.success();
    }
}