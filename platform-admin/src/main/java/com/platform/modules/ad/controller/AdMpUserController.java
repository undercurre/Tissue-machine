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
import com.platform.modules.ad.entity.AdMpUserEntity;
import com.platform.modules.ad.service.AdMpUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 公众号关注用户表Controller
 *
 * @author dxd
 * @date 2020-11-20 09:31:11
 */
@RestController
@RequestMapping("ad/mpuser")
public class AdMpUserController extends AbstractController {
    @Autowired
    private AdMpUserService adMpUserService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:mpuser:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdMpUserEntity> list = adMpUserService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询公众号关注用户表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:mpuser:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adMpUserService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:mpuser:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdMpUserEntity adMpUser = adMpUserService.getById(id);

        return RestResponse.success().put("mpuser", adMpUser);
    }

    /**
     * 新增公众号关注用户表
     *
     * @param adMpUser adMpUser
     * @return RestResponse
     */
    @SysLog("新增公众号关注用户表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:mpuser:save")
    public RestResponse save(@RequestBody AdMpUserEntity adMpUser) {

        adMpUserService.add(adMpUser);

        return RestResponse.success();
    }

    /**
     * 修改公众号关注用户表
     *
     * @param adMpUser adMpUser
     * @return RestResponse
     */
    @SysLog("修改公众号关注用户表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:mpuser:update")
    public RestResponse update(@RequestBody AdMpUserEntity adMpUser) {

        adMpUserService.update(adMpUser);

        return RestResponse.success();
    }

    /**
     * 根据主键删除公众号关注用户表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除公众号关注用户表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:mpuser:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adMpUserService.deleteBatch(ids);

        return RestResponse.success();
    }
}
