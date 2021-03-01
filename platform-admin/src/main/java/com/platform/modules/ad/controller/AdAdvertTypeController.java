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
import com.platform.modules.ad.service.AdAdvertAdvertTypeService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdAdvertTypeEntity;
import com.platform.modules.ad.service.AdAdvertTypeService;
import com.platform.modules.vo.AdAdvertTypeVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 广告类型表Controller
 *
 * @author gjw
 * @date 2020-12-19 09:34:35
 */
@RestController
@RequestMapping("ad/adverttype")
public class AdAdvertTypeController extends AbstractController {
    @Autowired
    private AdAdvertTypeService adAdvertTypeService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:adverttype:list")
    @ApiOperation(value = "查看所有广告类型", notes = "查看所有广告类型", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "广告类型", dataType = "String"),
    })
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdAdvertTypeEntity> list = adAdvertTypeService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询广告类型表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:adverttype:list")
    @ApiOperation(value = "分页查询广告类型表", notes = "分页查询广告类型表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "广告类型", dataType = "String"),
    })
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adAdvertTypeService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:adverttype:info")
    @ApiOperation(value = "根据投广告类型ID查询广告类型", notes = "根据投广告类型ID查询广告类型", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "广告类型ID", paramType = "path", dataType = "String")
    public RestResponse info(@PathVariable("id") String id) {
        AdAdvertTypeEntity adAdvertType = adAdvertTypeService.getById(id);

        return RestResponse.success().put("adverttype", adAdvertType);
    }

    /**
     * 新增广告类型表
     *
     * @param adAdvertType adAdvertType
     * @return RestResponse
     */
    @SysLog("新增广告类型表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:adverttype:save")
    @ApiOperation(value = "新增广告类型", httpMethod = "POST")
    public RestResponse save(@RequestBody AdAdvertTypeEntity adAdvertType) {

        adAdvertTypeService.add(adAdvertType);

        return RestResponse.success();
    }

    /**
     * 修改广告类型表
     *
     * @param adAdvertType adAdvertType
     * @return RestResponse
     */
    @SysLog("修改广告类型表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:adverttype:update")
    @ApiOperation(value = "修改广告类型", httpMethod = "POST")
    public RestResponse update(@RequestBody AdAdvertTypeEntity adAdvertType) {

        adAdvertTypeService.update(adAdvertType);

        return RestResponse.success();
    }

    /**
     * 根据主键删除广告类型表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除广告类型表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:adverttype:delete")
    @ApiOperation(value = "删除广告类型", httpMethod = "POST")
    public RestResponse delete(@RequestBody String[] ids) {
        adAdvertTypeService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 查看广告类型
     */
    @RequestMapping("/showAdvert/{id}")
    @RequiresPermissions("ad:advert:showAdvert")
    @ApiOperation(value = "查看广告类型", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "广告ID", dataType = "String"),
    })
    public RestResponse showAdvert(@PathVariable("id") String id) {
        List<AdAdvertTypeVo> adAdvertTypeEntityList = adAdvertTypeService.selectAdvertName(id);

        return RestResponse.success().put("adAdvertTypeEntityList", adAdvertTypeEntityList);
    }
}
