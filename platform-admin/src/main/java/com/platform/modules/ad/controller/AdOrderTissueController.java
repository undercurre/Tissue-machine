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
import com.platform.modules.ad.entity.AdOrderTissueEntity;
import com.platform.modules.ad.service.AdOrderTissueService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单纸巾表Controller
 *
 * @author zqh
 * @date 2020-09-22 16:27:18
 */
@RestController
@RequestMapping("ad/ordertissue")
public class AdOrderTissueController extends AbstractController {
    @Autowired
    private AdOrderTissueService adOrderTissueService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:ordertissue:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdOrderTissueEntity> list = adOrderTissueService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询订单纸巾表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:ordertissue:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adOrderTissueService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:ordertissue:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdOrderTissueEntity adOrderTissue = adOrderTissueService.getById(id);

        return RestResponse.success().put("ordertissue", adOrderTissue);
    }

    /**
     * 新增订单纸巾表
     *
     * @param adOrderTissue adOrderTissue
     * @return RestResponse
     */
    @SysLog("新增订单纸巾表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:ordertissue:save")
    public RestResponse save(@RequestBody AdOrderTissueEntity adOrderTissue) {

        adOrderTissueService.add(adOrderTissue);

        return RestResponse.success();
    }

    /**
     * 修改订单纸巾表
     *
     * @param adOrderTissue adOrderTissue
     * @return RestResponse
     */
    @SysLog("修改订单纸巾表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:ordertissue:update")
    public RestResponse update(@RequestBody AdOrderTissueEntity adOrderTissue) {

        adOrderTissueService.update(adOrderTissue);

        return RestResponse.success();
    }

    /**
     * 根据主键删除订单纸巾表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除订单纸巾表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:ordertissue:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adOrderTissueService.deleteBatch(ids);

        return RestResponse.success();
    }
}
