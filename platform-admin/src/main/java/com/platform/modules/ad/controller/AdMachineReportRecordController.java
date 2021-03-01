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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonObject;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdMachineOperateEntity;
import com.platform.modules.ad.entity.AdMachinePushEntity;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.service.AdMachineOperateService;
import com.platform.modules.ad.service.AdMachinePushService;
import com.platform.modules.ad.service.AdTissueMachineService;
import com.platform.modules.protocol.entity.TissueProtocol;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdMachineReportRecordEntity;
import com.platform.modules.ad.service.AdMachineReportRecordService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 机柜上报记录表Controller
 *
 * @author zqh
 * @date 2020-12-18 14:48:07
 */
@RestController
@RequestMapping("ad/machinereportrecord")
@Api(tags = "AdMachineReportRecordController")
public class AdMachineReportRecordController extends AbstractController {
    @Autowired
    private AdMachineReportRecordService adMachineReportRecordService;
    @Autowired
    private AdMachinePushService adMachinePushService;
    @Autowired
    private AdMachineOperateService adMachineOperateService;
    @Autowired
    private AdTissueMachineService adTissueMachineService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/queryAll")
    @ApiOperation(value = "queryAll")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sn", value = "sn"),
            @ApiImplicitParam(name = "time", value = "时间")
    })
    public RestResponse queryAll(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {
        System.out.println(params.toString());
        List<AdMachineReportRecordEntity> list = adMachineReportRecordService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询机柜上报记录表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:machinereportrecord:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adMachineReportRecordService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:machinereportrecord:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdMachineReportRecordEntity adMachineReportRecord = adMachineReportRecordService.getById(id);

        return RestResponse.success().put("machinereportrecord", adMachineReportRecord);
    }

    /**
     * 新增机柜上报记录表
     *
     * @param adMachineReportRecord adMachineReportRecord
     * @return RestResponse
     */
    @SysLog("新增机柜上报记录表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:machinereportrecord:save")
    public RestResponse save(@RequestBody AdMachineReportRecordEntity adMachineReportRecord) {

        adMachineReportRecordService.add(adMachineReportRecord);

        return RestResponse.success();
    }

    /**
     * 修改机柜上报记录表
     *
     * @param adMachineReportRecord adMachineReportRecord
     * @return RestResponse
     */
    @SysLog("修改机柜上报记录表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:machinereportrecord:update")
    public RestResponse update(@RequestBody AdMachineReportRecordEntity adMachineReportRecord) {

        adMachineReportRecordService.update(adMachineReportRecord);

        return RestResponse.success();
    }


    /**
     * 测试硬件用
     *
     */
    @SysLog("修改机柜上报记录表")
    @RequestMapping("/machineUpdate/{id}")
    public RestResponse machineUpdate(@PathVariable("id") String str) {
        String id = "123456";
        AdTissueMachineEntity machineEntity = adTissueMachineService.getById(id);
        if(!machineEntity.getSn().equals(str)) {
            machineEntity.setSn(str);
            adTissueMachineService.updateById(machineEntity);
        }
        return RestResponse.success();
    }
    /**
     * 根据主键删除机柜上报记录表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除机柜上报记录表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:machinereportrecord:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adMachineReportRecordService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 硬件测试页面,向设备发送指令
     *
     *
     * @return RestResponse
     */
    @RequestMapping("/receiveData")
    @ApiOperation(value = "receiveData")
    public RestResponse receiveData(@RequestBody String str) {
        System.out.println(str);
        JSONObject jsonObject = JSONObject.parseObject(str);
        TissueProtocol tissueProtocol = jsonObject.toJavaObject(TissueProtocol.class);
        if (tissueProtocol.getCM().equals("04")) {
                AdTissueMachineEntity machineInfo = adTissueMachineService.getBySn(tissueProtocol.getID());
                AdMachineOperateEntity adMachineOperateEntity = new AdMachineOperateEntity();
                adMachineOperateEntity.setSortLevel(adMachineOperateService.count() + 1);
                adMachineOperateEntity.setMachineId(machineInfo.getId());
                adMachineOperateEntity.setStatus(1);
                adMachineOperateEntity.setCreateTime(new Date());
                adMachineOperateEntity.setOperateCode(7);
                adMachineOperateEntity.setContent(tissueProtocol.getDATA());
                adMachineOperateService.add(adMachineOperateEntity);
                String substring = tissueProtocol.getDATA().substring(1, 2);
                machineInfo.setLightStatus(Integer.valueOf(substring));
                adTissueMachineService.updateById(machineInfo);
//            if(Integer.parseInt(tissueProtocol.getDATA().substring(0,1))>0) {
//                AdMachinePushEntity adMachinePushEntity = new AdMachinePushEntity();
//                adMachinePushEntity.setStatus(0);
//                adMachinePushEntity.setCreatedTime(new Date());
//                adMachinePushEntity.setMachineSn(tissueProtocol.getID());
//                adMachinePushEntity.setNumber(Integer.parseInt(tissueProtocol.getDATA().substring(0,1)));
//                adMachinePushService.add(adMachinePushEntity);
//            }
        }
        else {
            AdTissueMachineEntity machineInfo = adTissueMachineService.getBySn(tissueProtocol.getID());
            AdMachineOperateEntity adMachineOperateEntity = new AdMachineOperateEntity();
            adMachineOperateEntity.setSortLevel(adMachineOperateService.count() + 1);
            adMachineOperateEntity.setMachineId(machineInfo.getId());
            adMachineOperateEntity.setStatus(1);
            adMachineOperateEntity.setCreateTime(new Date());
            switch (tissueProtocol.getCM()){
                case "05":
                    adMachineOperateEntity.setOperateCode(1);
                    adMachineOperateEntity.setContent(tissueProtocol.getDATA());
                    break;
                case "06":
                    adMachineOperateEntity.setOperateCode(2);
                    break;
                case "07":
                    adMachineOperateEntity.setOperateCode(3);
                    break;
                case "08":
                    adMachineOperateEntity.setOperateCode(4);
                    break;
                case "09":
                    adMachineOperateEntity.setOperateCode(5);
                    break;
                case "0A":
                    adMachineOperateEntity.setOperateCode(6);
                    break;
            }
            if(adMachineOperateEntity.getOperateCode() != null) {
                adMachineOperateService.add(adMachineOperateEntity);
            }
        }
        return RestResponse.success();
    }
}
