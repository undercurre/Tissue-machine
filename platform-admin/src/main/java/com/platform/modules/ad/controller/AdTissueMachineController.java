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
import com.platform.modules.ad.dao.AdTissueMachineDao;
import com.platform.modules.ad.entity.AdMachineOperateEntity;
import com.platform.modules.ad.service.AdMachineVoiceService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.service.AdTissueMachineService;
import com.platform.modules.sys.service.SysUserRoleService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 纸巾机表Controller
 *
 * @author zqh
 * @date 2020-09-22 11:34:22
 */
@RestController
@RequestMapping("ad/tissuemachine")
@Api(tags = "AdTissueMachineController|后台管理机柜管理接口")
public class AdTissueMachineController extends AbstractController {

    @Autowired
    private AdTissueMachineService adTissueMachineService;
    @Autowired
    private AdMachineVoiceService adMachineVoiceService;
    @Autowired
    private AdTissueMachineDao adTissueMachineDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:tissuemachine:list")
    @ApiOperation(value = "查询出所有的机柜", notes = "查询出所有的机柜", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "按机柜名称查找", dataType = "string", example = "岭南"),
            @ApiImplicitParam(name = "status", value = "按维修状态查询 1:设备正常 2:设备维修中", dataType = "int", example = "1"),
            @ApiImplicitParam(name = "isDelete", value = "按启用状态查询 0:未启用 1:已启用", dataType = "int", example = "0")
    })
    public RestResponse queryAll(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {

        if (sysUserRoleService.isWorker(getUserId()) || sysUserRoleService.isWorkerManager(getUserId())){
            List<AdTissueMachineEntity> list = adTissueMachineService.list();
            return RestResponse.success().put("list", list);
        } else {
            return RestResponse.success().put("list", adTissueMachineService.queryAll(params, getUser()));
        }
    }

    /**
     * 查询出没有语音的机柜
     * @return RestResponse
     */
    @RequestMapping("/queryNoVoiceList")
    @RequiresPermissions("ad:tissuemachine:list")
    @ApiOperation(value = "查询出没有语音的机柜", notes = "查询出没有语音的机柜", httpMethod = "GET")
    public RestResponse queryNoVoiceList() {
        //所有list
        List<AdTissueMachineEntity> list = adTissueMachineService.queryNoVoiceList();
        //语音表中有效的机柜id
        List<String> voiceValidIdList = adMachineVoiceService.queryValidList();
        List<AdTissueMachineEntity> collect = list.stream().filter(item -> !voiceValidIdList.contains(item.getId())).collect(Collectors.toList());
        return RestResponse.success().put("list", collect);
    }

    /**
     * 分页查询纸巾机表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:tissuemachine:list")
    @ApiOperation(value = "分页查询纸巾机", notes = "分页查询纸巾机表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页页数", dataType = "int", example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页数据量", dataType = "int", example = "10"),
            @ApiImplicitParam(name = "name", value = "按机柜名称查找", dataType = "string", example = "岭南"),
            @ApiImplicitParam(name = "status", value = "按维修状态查询 1:设备正常 2:设备维修中", dataType = "int", example = "1"),
            @ApiImplicitParam(name = "isDelete", value = "按启用状态查询 0:已启用 1:未启用", dataType = "int", example = "0")
    })
    public RestResponse list(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {
        Page page = adTissueMachineService.queryPage(params, getUser());

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:tissuemachine:info")
    @ApiOperation(value = "根据ID查询机柜详细信息", notes = "根据ID查询机柜详细信息", httpMethod = "GET")
    @ApiImplicitParam(paramType = "path", name="id", value = "机柜ID", dataType = "string")
    public RestResponse info(@PathVariable("id") @ApiParam(hidden = true) String id) {
        AdTissueMachineEntity adTissueMachine = adTissueMachineService.getById(id);

        return RestResponse.success().put("tissuemachine", adTissueMachine);
    }

    /**
     * 新增纸巾机表
     *
     * @param adTissueMachine adTissueMachine
     * @return RestResponse
     */
    @SysLog("新增纸巾机表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:tissuemachine:save")
    @ApiOperation(notes = "新增机柜", value = "新增机柜", httpMethod = "POST")
    public RestResponse save(@RequestBody AdTissueMachineEntity adTissueMachine) throws Exception{

        adTissueMachineService.add(adTissueMachine);

        return RestResponse.success();
    }

    /**
     * 修改纸巾机表
     *
     * @param adTissueMachine adTissueMachine
     * @return RestResponse
     */
    @SysLog("修改纸巾机表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:tissuemachine:update")
    @ApiOperation(notes = "更新机柜", value = "更新机柜", httpMethod = "POST")
    public RestResponse update(@RequestBody AdTissueMachineEntity adTissueMachine) {

        adTissueMachineService.updateDetailById(adTissueMachine);

        return RestResponse.success();
    }

    /**
     * 修改纸巾机状态
     *
     * @param adTissueMachine adTissueMachine
     * @return RestResponse
     */
    @SysLog("修改纸巾机状态")
    @RequestMapping("/updateStatus")
    @ApiOperation(notes = "更新机柜状态", value = "更新机柜状态", httpMethod = "POST")
    public RestResponse updateStatus(@RequestBody AdTissueMachineEntity adTissueMachine) {

        adTissueMachineService.updateStatus(adTissueMachine);

        return RestResponse.success();
    }
    /**
     * 修改纸巾机设备定位状态
     *
     * @param adTissueMachine adTissueMachine
     * @return RestResponse
     */
    @SysLog("修改纸巾机设备定位状态")
    @RequestMapping("/updateIsOpenLocate")
    @ApiOperation(notes = "修改纸巾机设备定位状态", value = "修改纸巾机设备定位状态", httpMethod = "POST")
    public RestResponse updateIsOpenLocate(@RequestBody AdTissueMachineEntity adTissueMachine) {

        adTissueMachineService.updateIsOpenLocate(adTissueMachine);

        return RestResponse.success();
    }
    /**
     * 修改纸巾机是否删除
     *
     * @param adTissueMachine adTissueMachine
     * @return RestResponse
     */
    @SysLog("修改纸巾机状态")
    @RequestMapping("/updateIsDelete")
    @ApiOperation(notes = "更新机柜是否显示在小程序中", value = "更新机柜是否显示在小程序中", httpMethod = "POST")
    public RestResponse updateIsDelete(@RequestBody AdTissueMachineEntity adTissueMachine) {

        adTissueMachineService.updateIsDelete(adTissueMachine);

        return RestResponse.success();
    }
    /**
     * 修改纸巾机是否删除
     *
     * @param adTissueMachine adTissueMachine
     * @return RestResponse
     */
    @SysLog("修改是否显示Logo")
    @RequestMapping("/updateIsShowLogo")
    @ApiOperation(notes = "更新机柜是否显示LOGO", value = "更新机柜是否显示LOGO", httpMethod = "POST")
    public RestResponse updateIsShowLogo(@RequestBody AdTissueMachineEntity adTissueMachine) {

        adTissueMachineService.updateIsShowLogo(adTissueMachine);

        return RestResponse.success();
    }

    /**
     * 根据主键删除纸巾机表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除纸巾机表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:tissuemachine:delete")
    @ApiOperation(notes = "删除机柜", value = "删除机柜", httpMethod = "POST")
    public RestResponse delete(@RequestBody String[] ids) {
        adTissueMachineService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 根据主键查找纸巾机详细信息
     *
     * @param id id
     * @return RestResponse
     */
    @SysLog("获取纸巾机详细信息")
    @RequestMapping("/getById/{id}")
    @RequiresPermissions("ad:tissuemachine:info")
    @ApiOperation(notes = "根据ID获取纸巾机详情", value = "根据ID获取纸巾机详情", httpMethod = "GET")
    public RestResponse getById(@PathVariable("id") String id) {
        AdTissueMachineEntity adTissueMachineEntity = adTissueMachineService.getById(id);
        return RestResponse.success().put("tissuemachine", adTissueMachineEntity);
    }

    /**
     * 重启机柜
     *
     * @param params 请求参数
     * @return RestResponse
     */
    @SysLog("重启机柜")
    @RequestMapping("restart")
    @RequiresPermissions("ad:tissuemachine:restart")
    @ApiOperation(notes = "重启机柜", value = "重启机柜", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要重启的机柜ID", dataType = "String", required = true)
    })
    public RestResponse restart(@RequestParam @ApiParam(hidden = true) Map<String, String> params) {

        AdMachineOperateEntity operateEntity = adTissueMachineService.restart(params.get("id"));

        return RestResponse.success().put("data",operateEntity);
    }

    /**
     * 根据机柜语音表的父级ID查找对应的机柜
     *
     * @param params 机柜语音表ID
     * @return RestResponse
     */
    @SysLog("根据机柜语音表的父级ID查找对应的机柜")
    @RequestMapping("/queryMachineListBySupId")
    @ApiOperation(notes = "根据机柜语音表的父级ID查找对应的机柜", value = "根据机柜语音表的父级ID查找对应的机柜", httpMethod = "GET")
    public RestResponse queryMachineListBySupId(@RequestParam Map<String, Object> params) {
        Page page = adTissueMachineService.queryMachineListBySupId(params);
        return RestResponse.success().put("page", page);
    }
}
