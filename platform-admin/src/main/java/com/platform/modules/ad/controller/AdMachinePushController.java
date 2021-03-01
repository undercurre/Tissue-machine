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
import com.platform.modules.ad.entity.AdMachinePushEntity;
import com.platform.modules.ad.service.AdMachinePushService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 机柜发放纸巾中间表Controller
 *
 * @author zqh
 * @date 2020-11-02 13:54:01
 */
@RestController
@RequestMapping("ad/machinepush")
public class AdMachinePushController extends AbstractController {
    @Autowired
    private AdMachinePushService adMachinePushService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:machinepush:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdMachinePushEntity> list = adMachinePushService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询机柜发放纸巾中间表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:machinepush:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adMachinePushService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:machinepush:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdMachinePushEntity adMachinePush = adMachinePushService.getById(id);

        return RestResponse.success().put("machinepush", adMachinePush);
    }

    /**
     * 新增机柜发放纸巾中间表
     *
     * @param adMachinePush adMachinePush
     * @return RestResponse
     */
    @SysLog("新增机柜发放纸巾中间表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:machinepush:save")
    public RestResponse save(@RequestBody AdMachinePushEntity adMachinePush) {

        adMachinePushService.add(adMachinePush);

        return RestResponse.success();
    }

    /**
     * 修改机柜发放纸巾中间表
     *
     * @param adMachinePush adMachinePush
     * @return RestResponse
     */
    @SysLog("修改机柜发放纸巾中间表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:machinepush:update")
    public RestResponse update(@RequestBody AdMachinePushEntity adMachinePush) {

        adMachinePushService.update(adMachinePush);

        return RestResponse.success();
    }

    /**
     * 根据主键删除机柜发放纸巾中间表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除机柜发放纸巾中间表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:machinepush:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adMachinePushService.deleteBatch(ids);

        return RestResponse.success();
    }
}
