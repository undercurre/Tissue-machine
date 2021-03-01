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
import com.platform.modules.ad.entity.MallUserEntity;
import com.platform.modules.ad.service.MallUserService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 会员Controller
 *
 * @author zqh
 * @date 2019-07-01 14:59:40
 */
@RestController
@RequestMapping("mall/user")
public class MallUserController extends AbstractController {
    @Autowired
    private MallUserService mallUserService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<MallUserEntity> list = mallUserService.queryAll(params);

        return RestResponse.success().put("list", list);
    }
    /**
     * 查看所有列表
     *
     * @return RestResponse
     */
    @RequestMapping("/queryUserIdName")
    public RestResponse queryUserIdName() {
        List<MallUserEntity> list = mallUserService.list(new QueryWrapper<MallUserEntity>().select("ID,USER_NAME"));

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询会员
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("mall:user:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        SysUserEntity sysUser = getUser();
        String sysUserId = sysUser.getUserId();
        params.put("sysUserId", sysUserId);
        Page page = mallUserService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询会员详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("mall:user:info")
    public RestResponse info(@PathVariable("id") String id,@RequestParam Map<String, Object> params) {

        MallUserEntity mallUser = null;
        String openId = (String)params.get("openId");
        System.out.println(openId);
        if (openId == null) {
            mallUser = mallUserService.getById(id);
        } else {
            mallUser = mallUserService.getOne(new QueryWrapper<MallUserEntity>().eq("OPEN_ID", openId));
        }

        return RestResponse.success().put("user", mallUser);
    }

    /**
     * 新增会员
     *
     * @param mallUser mallUser
     * @return RestResponse
     */
    @SysLog("新增会员")
    @RequestMapping("/save")
    @RequiresPermissions("mall:user:save")
    public RestResponse save(@RequestBody MallUserEntity mallUser) {

        mallUserService.add(mallUser);

        return RestResponse.success();
    }

    /**
     * 修改会员
     *
     * @param mallUser mallUser
     * @return RestResponse
     */
    @SysLog("修改会员")
    @RequestMapping("/update")
    @RequiresPermissions("mall:user:update")
    public RestResponse update(@RequestBody MallUserEntity mallUser) {

        mallUserService.update(mallUser);

        return RestResponse.success();
    }

    /**
     * 根据主键删除会员
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除会员")
    @RequestMapping("/delete")
    @RequiresPermissions("mall:user:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        mallUserService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 根据代理商ID查询其用户
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @SysLog("根据代理商ID查询其用户")
    @RequestMapping("/selectMallUserByAgent")
    public RestResponse selectMallUserByAgent(@RequestParam Map<String, Object> params) {
        SysUserEntity sysUser = getUser();
        String sysUserId = sysUser.getUserId();
        params.put("sysUserId", sysUserId);

        List<MallUserEntity> list = mallUserService.selectMallUserByAgent(params);

        return RestResponse.success().put("list", list);
    }
}
