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
import com.platform.modules.ad.entity.AdUserBankEntity;
import com.platform.modules.ad.service.AdUserBankService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户银行卡信息表Controller
 *
 * @author dxd
 * @date 2020-11-11 16:18:10
 */
@RestController
@RequestMapping("ad/userbank")
@Api(tags = "AdUserBankController|用户银行卡相关接口")
public class AdUserBankController extends AbstractController {
    @Autowired
    private AdUserBankService adUserBankService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:userbank:list")
    @ApiOperation(notes = "查询用户的所有银行卡", value = "查询用户的所有银行卡", httpMethod = "GET")
    public RestResponse queryAll(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {
        params.put("userId", getUserId());
        List<AdUserBankEntity> list = adUserBankService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询用户银行卡信息表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:userbank:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        params.put("userId", getUserId());
        Page page = adUserBankService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:userbank:info")
    @ApiOperation(notes = "根据id查询用户银行卡详细信息", value = "根据id查询用户银行卡详细信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path")
    public RestResponse info(@PathVariable("id") String id) {
        AdUserBankEntity adUserBank = adUserBankService.getById(id);

        return RestResponse.success().put("userbank", adUserBank);
    }

    /**
     * 新增用户银行卡信息表
     *
     * @param adUserBank adUserBank
     * @return RestResponse
     */
    @SysLog("新增用户银行卡信息表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:userbank:save")
    @ApiOperation(notes = "新增用户银行卡", value = "新增用户银行卡", httpMethod = "POST")
    public RestResponse save(@RequestBody AdUserBankEntity adUserBank) {

        adUserBankService.add(adUserBank, getUser());

        return RestResponse.success();
    }

    /**
     * 修改用户银行卡信息表
     *
     * @param adUserBank adUserBank
     * @return RestResponse
     */
    @SysLog("修改用户银行卡信息表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:userbank:update")
    @ApiOperation(notes = "根据id修改用户银行卡", value = "根据id修改用户银行卡", httpMethod = "POST")
    public RestResponse update(@RequestBody AdUserBankEntity adUserBank) {

        adUserBankService.update(adUserBank);

        return RestResponse.success();
    }

    /**
     * 根据主键删除用户银行卡信息表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除用户银行卡信息表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:userbank:delete")
    @ApiOperation(notes = "删除用户银行卡", value = "删除用户银行卡", httpMethod = "POST")
    public RestResponse delete(@RequestBody String[] ids) {
        adUserBankService.deleteBatch(ids);

        return RestResponse.success();
    }
}
