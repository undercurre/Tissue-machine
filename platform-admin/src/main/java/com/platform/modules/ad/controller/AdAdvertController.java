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
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdAdvertTypeEntity;
import com.platform.modules.ad.entity.AdGoodsEntity;
import com.platform.modules.ad.service.AdAdvertAdvertTypeService;
import com.platform.modules.ad.service.AdAdvertTypeService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdAdvertEntity;
import com.platform.modules.ad.service.AdAdvertService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 广告表Controller
 *
 * @author gjw
 * @date 2020-12-19 09:34:35
 */
@RestController
@RequestMapping("ad/advert")
public class AdAdvertController extends AbstractController {
    @Autowired
    private AdAdvertService adAdvertService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:advert:list")
    @ApiOperation(value = "查看所有投广告列表", notes = "查看所有投广告列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "广告商姓名", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "广告商手机号", dataType = "String"),
            @ApiImplicitParam(name = "workunitType", value = "单位性质 1：企业，2：商家，3：个人", dataType = "int"),
            @ApiImplicitParam(name = "status", value = "回访状态 0：待回访，1：已回访", dataType = "int")
    })
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdAdvertEntity> list = adAdvertService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询广告表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:advert:list")
    @ApiOperation(value = "分页获取投广告列表", notes = "分页获取投广告列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页页数", dataType = "int", required = true),
            @ApiImplicitParam(name = "limit", value = "单页数据量", dataType = "int", required = true),
            @ApiImplicitParam(name = "name", value = "广告商姓名", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "广告商手机号", dataType = "String"),
            @ApiImplicitParam(name = "workunitType", value = "单位性质 1：企业，2：商家，3：个人", dataType = "int"),
            @ApiImplicitParam(name = "status", value = "回访状态 0：待回访，1：已回访", dataType = "int")
    })
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adAdvertService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:advert:info")
    @ApiOperation(value = "根据投广告ID查询广告", notes = "根据投广告ID查询广告", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "广告ID", paramType = "path", dataType = "String")
    public RestResponse info(@PathVariable("id") String id) {
        AdAdvertEntity adAdvert = adAdvertService.getById(id);

        return RestResponse.success().put("advert", adAdvert);
    }

    /**
     * 新增广告表
     *
     * @param adAdvert adAdvert
     * @return RestResponse
     */
    @SysLog("新增广告表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:advert:save")
    @ApiOperation(value = "新增投广告", httpMethod = "POST")
    public RestResponse save(@RequestBody AdAdvertEntity adAdvert) {

        adAdvertService.add(adAdvert);

        return RestResponse.success();
    }

    /**
     * 修改广告表
     *
     * @param adAdvert adAdvert
     * @return RestResponse
     */
    @SysLog("修改广告表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:advert:update")
    @ApiOperation(value = "修改投广告", httpMethod = "POST")
    public RestResponse update(@RequestBody AdAdvertEntity adAdvert) {

        adAdvertService.update(adAdvert);

        return RestResponse.success();
    }

    /**
     * 根据主键删除广告表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除广告表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:advert:delete")
    @ApiOperation(value = "删除投广告", httpMethod = "POST")
    public RestResponse delete(@RequestBody String[] ids) {
        adAdvertService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 更改广告寻访状态
     */
    @RequestMapping("changeStatus")
    @RequiresPermissions("ad:advert:changeStatus")
    @ApiOperation(value = "更改广告寻访状态", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "广告ID", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "回访状态 0：待回访，1：已回访", dataType = "int")
    })
    public RestResponse changeStatus(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {

        AdAdvertEntity adAdvertEntity = adAdvertService.getById((String) params.get("id"));
        adAdvertEntity.setStatus(Integer.parseInt((String) params.get("status")));
        adAdvertService.update(adAdvertEntity);
        return RestResponse.success();
    }
}
