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
import com.platform.modules.ad.entity.AdWorkOrderImageEntity;
import com.platform.modules.ad.service.AdWorkOrderImageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zqh
 * @date 2020-11-23 14:23:31
 */
@RestController
@RequestMapping("ad/workorderimage")
public class AdWorkOrderImageController extends AbstractController {
    @Autowired
    private AdWorkOrderImageService adWorkOrderImageService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:workorderimage:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdWorkOrderImageEntity> list = adWorkOrderImageService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:workorderimage:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adWorkOrderImageService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:workorderimage:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdWorkOrderImageEntity adWorkOrderImage = adWorkOrderImageService.getById(id);

        return RestResponse.success().put("workorderimage", adWorkOrderImage);
    }

    /**
     * 新增
     *
     * @param adWorkOrderImage adWorkOrderImage
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("ad:workorderimage:save")
    public RestResponse save(@RequestBody AdWorkOrderImageEntity adWorkOrderImage) {

        adWorkOrderImageService.add(adWorkOrderImage);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param adWorkOrderImage adWorkOrderImage
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("ad:workorderimage:update")
    public RestResponse update(@RequestBody AdWorkOrderImageEntity adWorkOrderImage) {

        adWorkOrderImageService.update(adWorkOrderImage);

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
    @RequiresPermissions("ad:workorderimage:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adWorkOrderImageService.deleteBatch(ids);

        return RestResponse.success();
    }
}
