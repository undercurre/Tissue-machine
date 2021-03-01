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
import com.platform.modules.ad.entity.AdPatchEntity;
import com.platform.modules.ad.service.AdPatchService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 广告贴片Controller
 *
 * @author zqh
 * @date 2020-09-23 09:32:59
 */
@RestController
@RequestMapping("ad/patch")
public class AdPatchController extends AbstractController {
    @Autowired
    private AdPatchService adPatchService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:patch:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdPatchEntity> list = adPatchService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询广告贴片
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:patch:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adPatchService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:patch:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdPatchEntity adPatch = adPatchService.getById(id);

        return RestResponse.success().put("patch", adPatch);
    }

    /**
     * 新增广告贴片
     *
     * @param adPatch adPatch
     * @return RestResponse
     */
    @SysLog("新增广告贴片")
    @RequestMapping("/save")
    @RequiresPermissions("ad:patch:save")
    public RestResponse save(@RequestBody AdPatchEntity adPatch) {

        adPatchService.add(adPatch);

        return RestResponse.success();
    }

    /**
     * 修改广告贴片
     *
     * @param adPatch adPatch
     * @return RestResponse
     */
    @SysLog("修改广告贴片")
    @RequestMapping("/update")
    @RequiresPermissions("ad:patch:update")
    public RestResponse update(@RequestBody AdPatchEntity adPatch) {

        adPatchService.update(adPatch);

        return RestResponse.success();
    }

    /**
     * 修改广告贴片启用状态
     *
     * @param adPatch adPatch
     * @return RestResponse
     */
    @SysLog("修改广告贴片启用状态")
    @RequestMapping("/updateAvail")
    @RequiresPermissions("ad:patch:update")
    public RestResponse updateAvail(@RequestBody AdPatchEntity adPatch) {

        AdPatchEntity adPatchEntity = adPatchService.getById(adPatch.getId());
        adPatchEntity.setAvail(adPatch.getAvail());

        adPatchService.update(adPatchEntity);

        return RestResponse.success();
    }

    /**
     * 根据主键删除广告贴片
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除广告贴片")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:patch:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adPatchService.deleteBatch(ids);

        return RestResponse.success();
    }
}
