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
import com.platform.modules.ad.entity.AdIssueImageEntity;
import com.platform.modules.ad.service.AdIssueImageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 问题图片表Controller
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@RestController
@RequestMapping("ad/issueimage")
public class AdIssueImageController extends AbstractController {
    @Autowired
    private AdIssueImageService adIssueImageService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:issueimage:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdIssueImageEntity> list = adIssueImageService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询问题图片表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:issueimage:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adIssueImageService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:issueimage:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdIssueImageEntity adIssueImage = adIssueImageService.getById(id);

        return RestResponse.success().put("issueimage", adIssueImage);
    }

    /**
     * 新增问题图片表
     *
     * @param adIssueImage adIssueImage
     * @return RestResponse
     */
    @SysLog("新增问题图片表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:issueimage:save")
    public RestResponse save(@RequestBody AdIssueImageEntity adIssueImage) {

        adIssueImageService.add(adIssueImage);

        return RestResponse.success();
    }

    /**
     * 修改问题图片表
     *
     * @param adIssueImage adIssueImage
     * @return RestResponse
     */
    @SysLog("修改问题图片表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:issueimage:update")
    public RestResponse update(@RequestBody AdIssueImageEntity adIssueImage) {

        adIssueImageService.update(adIssueImage);

        return RestResponse.success();
    }

    /**
     * 根据主键删除问题图片表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除问题图片表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:issueimage:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adIssueImageService.deleteBatch(ids);

        return RestResponse.success();
    }
}
