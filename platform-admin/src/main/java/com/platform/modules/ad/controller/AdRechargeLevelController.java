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
import com.platform.modules.ad.entity.AdRechargeLevelEntity;
import com.platform.modules.ad.service.AdRechargeLevelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 余额充值档位表Controller
 *
 * @author zqh
 * @date 2020-12-11 10:53:04
 */
@RestController
@RequestMapping("ad/rechargelevel")
public class AdRechargeLevelController extends AbstractController {
    @Autowired
    private AdRechargeLevelService adRechargeLevelService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:rechargelevel:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdRechargeLevelEntity> list = adRechargeLevelService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询余额充值档位表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:rechargelevel:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adRechargeLevelService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:rechargelevel:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdRechargeLevelEntity adRechargeLevel = adRechargeLevelService.getById(id);

        return RestResponse.success().put("rechargelevel", adRechargeLevel);
    }

    /**
     * 新增余额充值档位表
     *
     * @param adRechargeLevel adRechargeLevel
     * @return RestResponse
     */
    @SysLog("新增余额充值档位表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:rechargelevel:save")
    public RestResponse save(@RequestBody AdRechargeLevelEntity adRechargeLevel) {

        adRechargeLevelService.add(adRechargeLevel);

        return RestResponse.success();
    }

    /**
     * 修改余额充值档位表
     *
     * @param adRechargeLevel adRechargeLevel
     * @return RestResponse
     */
    @SysLog("修改余额充值档位表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:rechargelevel:update")
    public RestResponse update(@RequestBody AdRechargeLevelEntity adRechargeLevel) {

        adRechargeLevelService.update(adRechargeLevel);

        return RestResponse.success();
    }

    /**
     * 根据主键删除余额充值档位表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除余额充值档位表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:rechargelevel:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adRechargeLevelService.deleteBatch(ids);

        return RestResponse.success();
    }
}
