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
import com.platform.modules.ad.entity.AdBulletinEntity;
import com.platform.modules.ad.service.AdBulletinService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 广告机首页公告Controller
 *
 * @author zqh
 * @date 2020-09-22 11:34:23
 */
@RestController
@RequestMapping("ad/bulletin")
public class AdBulletinController extends AbstractController {
    @Autowired
    private AdBulletinService adBulletinService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:bulletin:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdBulletinEntity> list = adBulletinService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询广告机首页公告
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:bulletin:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adBulletinService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:bulletin:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdBulletinEntity adBulletin = adBulletinService.getById(id);

        return RestResponse.success().put("bulletin", adBulletin);
    }

    /**
     * 新增广告机首页公告
     *
     * @param adBulletin adBulletin
     * @return RestResponse
     */
    @SysLog("新增广告机首页公告")
    @RequestMapping("/save")
    @RequiresPermissions("ad:bulletin:save")
    public RestResponse save(@RequestBody AdBulletinEntity adBulletin) {

        adBulletinService.add(adBulletin);

        return RestResponse.success();
    }

    /**
     * 修改广告机首页公告
     *
     * @param adBulletin adBulletin
     * @return RestResponse
     */
    @SysLog("修改广告机首页公告")
    @RequestMapping("/update")
    @RequiresPermissions("ad:bulletin:update")
    public RestResponse update(@RequestBody AdBulletinEntity adBulletin) {

        adBulletinService.update(adBulletin);

        return RestResponse.success();
    }

    /**
     * 根据主键删除广告机首页公告
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除广告机首页公告")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:bulletin:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adBulletinService.deleteBatch(ids);

        return RestResponse.success();
    }
}
