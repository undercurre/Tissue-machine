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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.HttpUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdChatRecordEntity;
import com.platform.modules.ad.service.AdChatRecordService;
import com.platform.modules.vo.AdChatRecordVo;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 客服聊天记录表Controller
 *
 * @author zqh,dxd
 * @date 2020-10-28 14:14:46
 */
@RestController
@RequestMapping("ad/chatrecord")
@Api(tags = "AdChatRecordController|聊天记录相关接口")
public class AdChatRecordController extends AbstractController {
    @Autowired
    private AdChatRecordService adChatRecordService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:chatrecord:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdChatRecordEntity> list = adChatRecordService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询客服聊天记录表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:chatrecord:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adChatRecordService.queryPage(params);
        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:chatrecord:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdChatRecordEntity adChatRecord = adChatRecordService.getById(id);

        return RestResponse.success().put("chatrecord", adChatRecord);
    }

    /**
     * 新增客服聊天记录表
     *
     * @param adChatRecord adChatRecord
     * @return RestResponse
     */
    @SysLog("新增客服聊天记录表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:chatrecord:save")
    public RestResponse save(@RequestBody AdChatRecordEntity adChatRecord) {

        adChatRecordService.add(adChatRecord);

        return RestResponse.success();
    }

    /**
     * 修改客服聊天记录表
     *
     * @param adChatRecord adChatRecord
     * @return RestResponse
     */
    @SysLog("修改客服聊天记录表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:chatrecord:update")
    public RestResponse update(@RequestBody AdChatRecordEntity adChatRecord) {

        adChatRecordService.update(adChatRecord);

        return RestResponse.success();
    }

    /**
     * 根据主键删除客服聊天记录表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除客服聊天记录表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:chatrecord:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adChatRecordService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 查询openId对应客户和workerId对应客服之间的消息
     *
     * @param params （）
     * @return RestResponse
     */
    @RequestMapping("/getMessageList")
    @RequiresPermissions("ad:chatrecord:list")
    @ApiOperation(value = "根据OpenId和WorkerId获取聊天记录", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "用户ID"),
            @ApiImplicitParam(name = "workerId", value = "客服人员ID"),
    })
    public RestResponse getMessageListByOpenIdAndWorkerId(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {

        List<AdChatRecordEntity> adChatRecordEntityList = adChatRecordService.list(new QueryWrapper<AdChatRecordEntity>()
                .eq("OPEN_ID", params.get("openId"))
                .eq("WORKER_ID", params.get("workerId"))
                .last("ORDER BY CREATED_TIME"));

        return RestResponse.success().put("list", adChatRecordEntityList);
    }

}
