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
import com.platform.modules.ad.entity.AdScanEntity;
import com.platform.modules.ad.service.AdScanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 广告扫描Controller
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@RestController
@RequestMapping("ad/scan")
public class AdScanController extends AbstractController {
    @Autowired
    private AdScanService adScanService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:scan:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdScanEntity> list = adScanService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询广告扫描
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:scan:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adScanService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:scan:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdScanEntity adScan = adScanService.getById(id);

        return RestResponse.success().put("scan", adScan);
    }

    /**
     * 新增广告扫描
     *
     * @param adScan adScan
     * @return RestResponse
     */
    @SysLog("新增广告扫描")
    @RequestMapping("/save")
    @RequiresPermissions("ad:scan:save")
    public RestResponse save(@RequestBody AdScanEntity adScan) {

        adScanService.add(adScan);

        return RestResponse.success();
    }

    /**
     * 修改广告扫描
     *
     * @param adScan adScan
     * @return RestResponse
     */
    @SysLog("修改广告扫描")
    @RequestMapping("/update")
    @RequiresPermissions("ad:scan:update")
    public RestResponse update(@RequestBody AdScanEntity adScan) {

        adScanService.update(adScan);

        return RestResponse.success();
    }

    /**
     * 根据主键删除广告扫描
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除广告扫描")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:scan:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adScanService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 更新广告启用状态
     *
     * @param params 请求参数
     * @return RestResponse
     */
    @SysLog("更新广告启用状态")
    @RequestMapping("/changeStatus")
    @RequiresPermissions("ad:scan:update")
    public RestResponse changeStatus(@RequestParam Map<String, Object> params) {
        AdScanEntity adScanEntity = adScanService.getById((String)params.get("id"));
        adScanEntity.setAvail(Integer.parseInt((String)params.get("avail")));
        adScanService.update(adScanEntity);
        return RestResponse.success();
    }
}
