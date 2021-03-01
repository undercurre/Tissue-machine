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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.dao.AdIssueDao;
import com.platform.modules.ad.entity.AdIssueImageEntity;
import com.platform.modules.ad.service.AdIssueImageService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdIssueEntity;
import com.platform.modules.ad.service.AdIssueService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 问题表Controller
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@RestController
@RequestMapping("ad/issue")
public class AdIssueController extends AbstractController {
    @Autowired
    private AdIssueService adIssueService;
    @Autowired
    private AdIssueDao adIssueDao;
    @Autowired
    private AdIssueImageService adIssueImageService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:issue:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdIssueEntity> list = adIssueService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询问题表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:issue:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adIssueService.queryPage(params, getUser());
        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:issue:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdIssueEntity adIssue = adIssueDao.getById(id);

        List<AdIssueImageEntity> adIssueImageEntityList = adIssueImageService.list(new QueryWrapper<AdIssueImageEntity>().eq("ISSUE_ID", id));
        adIssue.setAdIssueImageEntityList(adIssueImageEntityList);

        return RestResponse.success().put("issue", adIssue);
    }

    /**
     * 新增问题表
     *
     * @param adIssue adIssue
     * @return RestResponse
     */
    @SysLog("新增问题表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:issue:save")
    public RestResponse save(@RequestBody AdIssueEntity adIssue) {

        adIssueService.add(adIssue);

        return RestResponse.success();
    }

    /**
     * 修改问题表
     *
     * @param adIssue adIssue
     * @return RestResponse
     */
    @SysLog("修改问题表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:issue:update")
    public RestResponse update(@RequestBody AdIssueEntity adIssue) {

        adIssueService.update(adIssue);

        return RestResponse.success();
    }

    /**
     * 修改问题处理状态
     *
     * @param adIssue adIssue
     * @return RestResponse
     */
    @SysLog("修改问题表")
    @RequestMapping("/updateStatus")
    @RequiresPermissions("ad:issue:update")
    public RestResponse updateStatus(@RequestBody AdIssueEntity adIssue) {

        AdIssueEntity adIssueEntity = adIssueService.getById(adIssue.getId());
        adIssueEntity.setStatus(adIssue.getStatus());
        adIssueService.update(adIssueEntity);

        return RestResponse.success();
    }

    /**
     * 根据主键删除问题表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除问题表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:issue:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adIssueService.deleteBatch(ids);

        return RestResponse.success();
    }
}
