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
import com.platform.modules.ad.entity.AdMarqueeTextEntity;
import com.platform.modules.ad.service.AdMarqueeTextService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 首页走马灯文字公告管理Controller
 *
 * @author dxd
 * @date 2020-10-27 09:50:20
 */
@RestController
@RequestMapping("ad/marqueetext")
public class AdMarqueeTextController extends AbstractController {
    @Autowired
    private AdMarqueeTextService adMarqueeTextService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:marqueetext:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdMarqueeTextEntity> list = adMarqueeTextService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询首页走马灯文字公告管理
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:marqueetext:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adMarqueeTextService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:marqueetext:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdMarqueeTextEntity adMarqueeText = adMarqueeTextService.getById(id);

        return RestResponse.success().put("marqueetext", adMarqueeText);
    }

    /**
     * 新增首页走马灯文字公告管理
     *
     * @param adMarqueeText adMarqueeText
     * @return RestResponse
     */
    @SysLog("新增首页走马灯文字公告管理")
    @RequestMapping("/save")
    @RequiresPermissions("ad:marqueetext:save")
    public RestResponse save(@RequestBody AdMarqueeTextEntity adMarqueeText) {
        adMarqueeText.setCreateTime(new Date());
        adMarqueeTextService.add(adMarqueeText);

        return RestResponse.success();
    }

    /**
     * 修改首页走马灯文字公告管理
     *
     * @param adMarqueeText adMarqueeText
     * @return RestResponse
     */
    @SysLog("修改首页走马灯文字公告管理")
    @RequestMapping("/update")
    @RequiresPermissions("ad:marqueetext:update")
    public RestResponse update(@RequestBody AdMarqueeTextEntity adMarqueeText) {

        adMarqueeTextService.update(adMarqueeText);

        return RestResponse.success();
    }

    /**
     * 根据主键删除首页走马灯文字公告管理
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除首页走马灯文字公告管理")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:marqueetext:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adMarqueeTextService.deleteBatch(ids);

        return RestResponse.success();
    }


    /**
     * 更新启用状态
     *
     * @param params 请求参数
     * @return RestResponse
     */
    @SysLog("更新启用状态")
    @RequestMapping("/changeStatus")
    @RequiresPermissions("ad:marqueetext:update")
    public RestResponse delete(@RequestParam Map<String, Object> params) {

        AdMarqueeTextEntity adMarqueeTextEntity = adMarqueeTextService.getById((String)params.get("id"));
        adMarqueeTextEntity.setStatus(Integer.parseInt((String)params.get("status")));
        adMarqueeTextService.update(adMarqueeTextEntity);
        return RestResponse.success();
    }
}
